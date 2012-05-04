package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author bellrj. Created May 2, 2012.
 */
public class Connection {

	private DataInputStream in;
	private DataOutputStream out;

	/**
	 * TODO Put here a description of what this constructor does.
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
			// TODO Auto-generated catch block
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

	private byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) (value) };
	}

	private int byteArrayToInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8)
				+ (b[3] & 0xFF);
	}
}
