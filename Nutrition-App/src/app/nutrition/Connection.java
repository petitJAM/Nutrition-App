package app.nutrition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.util.Log;

/**
 * A Connection with the server.
 * 
 * @author Ryne Bell, Alex Petitjean. Created May 10, 2012.
 */
public class Connection {

	/** port number to use */
	public static int port = 12345;
	private DataInputStream in;
	private DataOutputStream out;
	private Socket sock;

	/**
	 * Create a new Connection with the given Socket
	 * 
	 * @param sock
	 */
	public Connection(Socket sock) {
		this.sock = sock;
		try {
			this.in = new DataInputStream(sock.getInputStream());
			this.out = new DataOutputStream(sock.getOutputStream());
			Log.d("Connection", "Streams established");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * closes the streams and socket associated with this connection, should be
	 * called when we are done with this connection, and no more calls to
	 * methods in this instance of connection should be made after calling close
	 */
	public void close() {
		try {
			in.close();
			out.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Receives a food object from the server
	 * 
	 * @return the Food object received
	 * @throws IOException
	 */
	public Food recieveFood() throws IOException {
		String name = recieveString();
		float calories = in.readFloat();
		float calFromFat = in.readFloat();
		float totalFat = in.readFloat();
		float sodium = in.readFloat();
		float carbs = in.readFloat();
		float fiber = in.readFloat();
		float sugar = in.readFloat();
		float protein = in.readFloat();
		return new Food(name, calories, calFromFat, totalFat, sodium, carbs,
				fiber, sugar, protein);
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
	 * @return deserialized object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Object deSerialize(byte[] b) throws ClassNotFoundException,
			IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream oin;
		oin = new ObjectInputStream(bis);
		return oin.readObject();
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
