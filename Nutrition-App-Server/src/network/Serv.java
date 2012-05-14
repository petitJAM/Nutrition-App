package network;

import java.net.ServerSocket;

import Database.QueryClass;

/**
 * Run the server
 *
 * @author Ryne Bell.
 *         Created May 10, 2012.
 */
public class Serv {

	private static boolean stopping;
	public static QueryClass qc;
	public static boolean startClientThreads = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startClientThreads = true;
		stopping = false;

		ServerSocket sock = null;
		qc = new QueryClass();
		Server server = new Server(Connection.port);
		server.start();
	}
}
