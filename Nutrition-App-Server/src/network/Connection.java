package network;

public class Connection {

	private Connection(){}
	
	public Connection(String string, String string2) {
		// TODO Auto-generated constructor stub
	}


	public Connection(String port) {
		// TODO Auto-generated constructor stub
	}

	public boolean connect() {
		// TODO Auto-generated method stub
		return false;
	}

	public Connection getClientConnection(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class Server extends Connection{
		public Server(String port) {
			// TODO Auto-generated method stub
		}
		
	}
	public boolean sendByte(byte b) {
		// TODO Auto-generated method stub
		return false;
	}

	public byte recieveByte() {
		// TODO Auto-generated method stub
		return -1;
	}

	public boolean sendInt(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public int recieveInt() {
		// TODO Auto-generated method stub
		return -1;
	}

	public boolean sendChar(char c) {
		// TODO Auto-generated method stub
		return false;
	}

	public char recieveChar() {
		// TODO Auto-generated method stub
		return '\0';
	}

	public boolean sendString(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	public String recieveString() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean sendByteArray(byte[] bs) {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] recieveByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
