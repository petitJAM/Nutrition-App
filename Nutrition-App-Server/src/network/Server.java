package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import Database.Food;

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
	private int port;

	/**
	 * creates a serversocket and listens for connections from clients
	 * 
	 * @param port
	 *            the port to listen on
	 */
	public Server(int port) {
		this.port = port;
		running = false;
		stop = true;
		clientConnections = new ArrayList<Connection>();
	}

	/**
	 * starts a server listening on the port this server was created on
	 * subsequent calls to start should not be made, unless stop is called
	 */
	public void start() {
		running = true;
		stop = false;
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
							con.sendFood(possiblitites.get(0));
							con.sendFood(possiblitites.get(1));
							con.sendFood(possiblitites.get(2));
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					} else if (type == 1) { // close the socket and end this
											// thread
						con.close();
						stopping = true;
						break;
					}
				}
			}

			private ArrayList<Food> getTop3(ArrayList<Food> array, byte[] seq) {
				PriorityQueue<FoodItem> foods = new PriorityQueue<FoodItem>(3);
				for (Food f : array) {
					foods.add(new FoodItem(f, f.ngm.logLikelihood(seq)));
				}

				ArrayList<Food> ret = new ArrayList<Food>();
				ret.add(foods.poll().f);
				ret.add(foods.poll().f);
				ret.add(foods.poll().f);
				return ret;
			}

			private class FoodItem implements Comparable<FoodItem> {
				public Food f;
				public double val;

				public FoodItem(Food f, double val) {
					this.f = f;
					this.val = val;
				}

				@Override
				public int compareTo(FoodItem o) {
					return (int) ((o.val - this.val) * 1000);
				}
			}
		}
	}
}
