package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import app.nutrition.NGramModel;

/**
 * the class which handles connections from clients to the server spinning off
 * threads to communicate with them
 * 
 * @author bellrj
 * 
 */
public class Server {

	private boolean running;
	private Thread serverThread;
	private boolean stop;
	private ServerSocket sSock;
	ArrayList<Connection> clientConnections;

	/**
	 * creates a serversocket and listens for connections from clients
	 * 
	 * @param port
	 *            the port to listen on
	 */
	public Server(int port) {
		running = true;
		stop = false;
		clientConnections = new ArrayList<Connection>();
		try {
			sSock = new ServerSocket(port);
		} catch (IOException e) {
			// TODO fix this
			e.printStackTrace();
		}
		serverThread = new Thread(new ServerThread());
		serverThread.start();
	}

	/**
	 * gets a numbered clientConnection from the array of them (primarily used
	 * for debugging)
	 * 
	 * @param i
	 *            the identifier for the client
	 * @return the Connection connected to the given client
	 */
	public Connection getClientConnection(int i) {
		if (clientConnections.size() > i)
			return clientConnections.get(i);
		return null;
	}

	/**
	 * stops the server and closes the listening socket
	 */
	public void stop() {
		running = false;
		stop = true;
		try {
			sSock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class ServerThread implements Runnable {
		public void run() {
			while (!stop) {
				while (running) {
					if (sSock.isClosed()) {
						try {
							sSock.bind(sSock.getLocalSocketAddress());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Socket sock = sSock.accept();
						Connection con = new Connection(sock);
						clientConnections.add(con);
						(new Thread(new ClientConnection(con))).start();
					} catch (SocketException e) {
						if (!running) {
							// this was probably caused by closing the
							// ServerSocket in stop
							break;
						}
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		private class ClientConnection implements Runnable {
			private Connection con;
			private boolean stoping;

			public ClientConnection(Connection con) {
				this.con = con;
				this.stoping = false;
			}

			public void run() {
				while (!stoping) {
					int type = -1;
					try {
						type = con.recieveInt();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (type == 0) { // this is a request for a list of matches
						NGramModel ngm = null;
						try {
							ngm = (NGramModel) Connection.deSerialize(con
									.recieveByteArray());
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
