package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import Database.Food;
import NGramModel.NGramModel;

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
	/** List of Connections */
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
		@Override
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

						System.out.println("Added connection: "
								+ con.toString());
						if (Serv.startClientThreads)
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
			private boolean stopping;

			public ClientConnection(Connection con) {
				this.con = con;
				this.stopping = false;
			}

			@Override
			public void run() {
				while (!stopping) {
					int type = -1;
					try {
						type = con.recieveInt();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (type == 0) { // this is a request for a list of matches
						byte[] seq = null;
						try {
							seq = con.recieveByteArray();
						} catch (IOException e) {
							e.printStackTrace();
						}
						// get the top 3 food items to send
						ArrayList<Food> possiblitites;
						try {
							possiblitites = getTop3(Serv.qc.getFood(), seq);
							con.sendInt(3);
							con.sendByteArray(Connection
									.serialize(possiblitites.get(0)));
							con.sendByteArray(Connection
									.serialize(possiblitites.get(1)));
							con.sendByteArray(Connection
									.serialize(possiblitites.get(2)));
						} catch (IOException exception) {
							exception.printStackTrace();
						}

					}
				}
			}

			private ArrayList<Food> getTop3(ArrayList<Food> array, byte[] seq) {
				Double a[] = { 0.0, 0.0, 0.0 };
				ArrayList<Food> top3 = new ArrayList<Food>();
				top3.add(null);
				top3.add(null);
				top3.add(null);
				for (Food f : array) {
					Food tempF = f;
					Food tempF2 = f;
					double tempD = logLikelihood(f.ngm, seq);
					double tempD2 = tempD;
					if (top3.get(0) == null || tempD > a[0]) {
						tempD2 = a[0];
						a[0] = tempD;
						tempF = top3.get(0);
						top3.set(0, f);
					}
					if (top3.get(1) == null || tempD2 > a[1]) {
						tempD = a[1];
						a[1] = tempD2;
						tempF2 = top3.get(0);
						top3.set(1, tempF);
					} else {
						tempD = tempD2;
						tempF2 = tempF;
					}
					if (top3.get(2) == null || tempD > a[2]) {
						a[2] = tempD;
						top3.set(2, tempF2);
					}
				}
				return null;
			}

			private Double logLikelihood(NGramModel nGramModel, byte[] ngm) {
				// NGramModel ngm2 = null;
				// try {
				// ngm2 = Connection.deSerialize(nGramModel);
				// } catch (ClassNotFoundException | IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				Double ret = 0.0;
				ret = nGramModel.logLikelihood(ngm);
				return ret;
			}
		}
	}
}
