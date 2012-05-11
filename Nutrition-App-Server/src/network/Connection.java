package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Database.Food;

/**
 * A Connection with the server.
 * 
 * @author Ryne Bell, Alex Petitjean. Created May 10, 2012.
 */
public class Connection {

	public static int port = 12345;
	private DataInputStream in;
	private DataOutputStream out;

	/**
	 * Create a new Connection with the given Socket
	 * 
	 * @param sock
	 */
	public Connection(Socket sock) {
		try {
			this.in = new DataInputStream(sock.getInputStream());
			this.out = new DataOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	/**
	 * sends a byte over the socket this connection was created with
	 * 
	 * @param b
	 *            the byte to send
	 * @return true on success, false on failure
	 */
	public boolean sendByte(byte b) {
		return sendByteArray(new byte[] { b });
	}

	public boolean sendFood(Food f) {
		sendString(f.name);
		try {
			out.writeFloat(f.calories);
			out.writeFloat(f.calFromFat);
			out.writeFloat(f.totalFat);
			out.writeFloat(f.sodium);
			out.writeFloat(f.carbs);
			out.writeFloat(f.fiber);
			out.writeFloat(f.sugar);
			out.writeFloat(f.protein);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * receives a byte from the other end of the socket this connection was
	 * created with
	 * 
	 * @return the byte that was sent
	 * @throws IOException
	 */
	public byte recieveByte() throws IOException {
		return recieveByteArray()[0];
	}

	/**
	 * sends an integer over the socket this connection was created with
	 * 
	 * @param i
	 *            the integer to send
	 * @return true on success, false on failure
	 */
	public boolean sendInt(int i) {
		return sendByteArray(intToByteArray(i));
	}

	/**
	 * receives an integer from the other end of the socket this connection was
	 * created with
	 * 
	 * @return the integer that was sent
	 * @throws IOException
	 */
	public int recieveInt() throws IOException {
		return byteArrayToInt(recieveByteArray());
	}

	/**
	 * sends a character over the socket this connection was created with
	 * 
	 * @param c
	 *            the character to send
	 * @return true on success, false on failure
	 */
	public boolean sendChar(char c) {
		return sendString(Character.toString(c));
	}

	/**
	 * receives a character from the other end of the socket this connection was
	 * created with
	 * 
	 * @return the character that was sents
	 * @throws IOException
	 */
	public char recieveChar() throws IOException {
		return recieveString().charAt(0);
	}

	/**
	 * sends a string over the socket this connection was created with
	 * 
	 * @param string
	 *            the string to send
	 * @return true on success, false on failure
	 */
	public boolean sendString(String string) {
		return sendByteArray(string.getBytes());
	}

	/**
	 * receives a string from the other end of the socket this connection was
	 * created with
	 * 
	 * @return the string that was sent
	 * @throws IOException
	 */
	public String recieveString() throws IOException {
		return new String(recieveByteArray());
	}

	/**
	 * sends a byte array over the socket this connection was created with
	 * 
	 * @param bs
	 *            the byteArray to send
	 * @return true on success, false on failure
	 */
	public boolean sendByteArray(byte[] bs) {
		try {
			out.writeInt(bs.length);
			out.write(bs);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * receives a byteArray from the other end of the socket this connection was
	 * created with
	 * 
	 * @return the byteArray that was sent
	 * @throws IOException
	 */
	public byte[] recieveByteArray() throws IOException {
		byte[] array = null;
		int length = in.readInt();
		array = new byte[length];
		for (int i = 0; i < length;)
			i += in.read(array, i, length - i);
		return array;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param obj
	 * @return serialized byte[]
	 * @throws IOException
	 */
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oout;
		oout = new ObjectOutputStream(bos);
		oout.writeObject(obj);
		return bos.toByteArray();
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param b
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object deSerialize(byte[] b) throws ClassNotFoundException,
			IOException {
		System.out.println("deserializing:" + getString(b));
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream oin;
		oin = new ObjectInputStream(bis);
		return oin.readObject();
	}

	private static String getString(byte[] b) {
		String s = "";
		for (byte a : b) {
			s += a;
		}
		return s;
	}

	private byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) (value) };
	}

	private int byteArrayToInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	}
}
