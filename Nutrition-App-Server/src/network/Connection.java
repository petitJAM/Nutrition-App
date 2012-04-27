package network;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

	private Socket sock;
	private DataInputStream in;
	private DataOutputStream out;

	public Connection(Socket sock) {
		this.sock = sock;
		try {
			this.in = new DataInputStream(sock.getInputStream());
			this.out = new DataOutputStream(sock.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public boolean sendByte(byte b) {
		return sendByteArray(new byte[] { b });
	}

	public byte recieveByte() throws IOException {
		return recieveByteArray()[0];
	}

	public boolean sendInt(int i) {
		return sendByteArray(intToByteArray(i));
	}

	public int recieveInt() throws IOException {
		return byteArrayToInt(recieveByteArray());
	}

	public boolean sendChar(char c) {
		return sendString(Character.toString(c));
	}

	public char recieveChar() throws IOException {
		return recieveString().charAt(0);
	}

	public boolean sendString(String string) {
		return sendByteArray(string.getBytes());
	}

	public String recieveString() throws IOException {
		return new String(recieveByteArray());
	}

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
