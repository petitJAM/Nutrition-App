package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Database.Food;
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
						// get the top 3 food items to send
						ArrayList<Food> possiblitites = getTop3(
								Serv.qc.getFood(), ngm);

						con.sendInt(3);
						try {
							con.sendByteArray(Connection
									.serialize(possiblitites.get(0)));
							con.sendByteArray(Connection
									.serialize(possiblitites.get(1)));
							con.sendByteArray(Connection
									.serialize(possiblitites.get(2)));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			private ArrayList<Food> getTop3(ArrayList<Food> array,
					NGramModel ngm) {
				Double a[] = { 0.0, 0.0, 0.0 };
				ArrayList<Food> top3 = new ArrayList<Food>();
				top3.add(null);
				top3.add(null);
				top3.add(null);
				for (Food f : array) {
					Food tempF = f;
					Food tempF2 = f;
					double tempD = logLikelihood(f.NGramModel, ngm);
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

			private Double logLikelihood(byte[] nGramModel, NGramModel ngm) {
				NGramModel ngm2 = null;
				try {
					ngm2 = (NGramModel) Connection.deSerialize(nGramModel);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Double ret = 0.0;
				try {
					ret = ngm.logLikelihood(ngm2.getByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ret;
			}
		}
	}
}
