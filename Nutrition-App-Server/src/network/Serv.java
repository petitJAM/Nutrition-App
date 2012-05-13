package network;

import Database.QueryClass;

/**
 * Run the server
 *
 * @author Ryne Bell.
 *         Created May 10, 2012.
 */
public class Serv {

	/**
	 * the global connection to the SQLServer through which all DB queries are made
	 */
	public static QueryClass qc;
	/**
	 * used to keep threads from starting when we are testing to ensure we can directly access the connection associated with a client
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
