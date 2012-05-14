package network;

import Database.QueryClass;

/**
 * Run the server
 * 
 * @author Ryne Bell. Created May 10, 2012.
 */
public class Serv {

	/**
	 * the global query connection to the Database, which all queries are made
	 * through
	 */
	public static QueryClass qc;
	/**
	 * used to ensure that threads are not started to handle clients when we are
	 * running tests and need direct access to the Server's Connections
	 */
	public static boolean startClientThreads = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startClientThreads = true;
		qc = new QueryClass();
		Server server = new Server(Connection.port);
		server.start();
	}
}
