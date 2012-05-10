package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketImpl;

import Database.QueryClass;

public class Serv {

	private static boolean stoping;
	public static QueryClass qc;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		stoping = false;
		ServerSocket sock = null;
		qc = new QueryClass();
		Server server = new Server(Connection.port);
	}
}
