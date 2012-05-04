package network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * tests for the Connection networking component
 * 
 * @author bellrj
 * 
 */
public class ConnectionTest {

	String ip = "127.0.0.1";
	int port = 12345;

	/**
	 * the client can create a connection which is not null
	 */
	@Test
	public void testCreateClientConnectionSucceeds() {
		Connection connection = new Connection(new Socket());
		assertNotNull(connection);
	}

	/**
	 * the server can create a connection
	 */
	@Test
	public void testCreateServerConnectionSucceeds() {
		Server server = new Server(port);
		assertNotNull(server);
		server.stop();
	}

	/**
	 * the client can connect to the server
	 */
	@Test
	public void testOpenConnectionFromClientToServer() {
		Server server = new Server(port);
		@SuppressWarnings("unused")
		Connection client = new Connection(connection(ip, port));
		// if there is no exception we pass this test
		assertTrue(true);
		server.stop();
	}

	/**
	 * checks to see that the server creates a new thread to handle the client
	 */
	@Test
	public void testServerCreatesNewThreadToHandleClient() {
		Server server = new Server(port);
		@SuppressWarnings("unused")
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertNotNull(server.getClientConnection(0));
		server.stop();
	}

	/**
	 * tests sending a byte from the client to the server
	 */
	@Test
	public void testClientSendsServerByteProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client.sendByte((byte) 1));
		try {
			assertEquals(server.getClientConnection(0).recieveByte(), (byte) 1);
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a byte from the server to the client
	 */
	@Test
	public void testServerSendsClientByteProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(server.getClientConnection(0).sendByte((byte) 1));
		try {
			assertEquals(client.recieveByte(), (byte) 1);
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending and integer from the client to the server
	 */
	@Test
	public void testClientSendsServerIntProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client.sendInt(1234567));
		try {
			assertEquals(server.getClientConnection(0).recieveInt(), 1234567);
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending an integer from the server to the client
	 */
	@Test
	public void testServerSendsClientIntProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(server.getClientConnection(0).sendInt(1234567));
		try {
			assertEquals(client.recieveInt(), 1234567);
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a character from the client to the server
	 */
	@Test
	public void testClientSendsServerCharProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client.sendChar('a'));
		try {
			assertEquals(server.getClientConnection(0).recieveChar(), 'a');
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a character from the server to the client
	 */
	@Test
	public void testServerSendsClientCharProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(server.getClientConnection(0).sendChar('a'));
		try {
			assertEquals(client.recieveChar(), 'a');
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a string from the client to the server
	 */
	@Test
	public void testClientSendsServerStringProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client.sendString("hello man, what's going on yo?"));
		try {
			assertEquals(server.getClientConnection(0).recieveString(),
					"hello man, what's going on yo?");
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a string from the server to the client
	 */
	@Test
	public void testServerSendsClientStringProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(server.getClientConnection(0).sendString(
				"hello man, what's going on yo?"));
		try {
			assertEquals(client.recieveString(),
					"hello man, what's going on yo?");
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a byteArray from the client to the server
	 */
	@Test
	public void testClientSendsServerByteArrayProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client.sendByteArray(new byte[] { (byte) 0, (byte) 1 }));
		try {
			assertTrue(byteArrayEquals(server.getClientConnection(0)
					.recieveByteArray(), new byte[] { (byte) 0, (byte) 1 }));
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests sending a byteArray from the server to the client
	 */
	@Test
	public void testServerSendsClientByteArrayProperly() {
		Server server = new Server(port);
		Connection client = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(server.getClientConnection(0).sendByteArray(
				new byte[] { (byte) 0, (byte) 1 }));
		try {
			assertTrue(byteArrayEquals(client.recieveByteArray(), new byte[] {
					(byte) 0, (byte) 1 }));
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	/**
	 * tests creating two connections from clients to the server
	 */
	@Test
	public void testServerAllowsTwoClients() {
		Server server = new Server(port);
		@SuppressWarnings("unused")
		Connection client1 = new Connection(connection(ip, port));
		@SuppressWarnings("unused")
		Connection client2 = new Connection(connection(ip, port));
		doWait(100);
		// there will be an exception if either fails
		assertTrue(true);
		server.stop();
	}

	/**
	 * tests connecting multiple clients to the server, and managing their
	 * messages properly
	 */
	@Test
	public void testServerHandlesTwoClients() {
		Server server = new Server(port);
		Connection client1 = new Connection(connection(ip, port));
		doWait(100); // to ensure client ordering is correct
		Connection client2 = new Connection(connection(ip, port));
		doWait(100);
		assertTrue(client1.sendInt(123465));
		assertTrue(client2.sendInt(654321));
		try {
			assertEquals(server.getClientConnection(0).recieveInt(), 123465);
			assertEquals(server.getClientConnection(1).recieveInt(), 654321);
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		}
		server.stop();
	}

	private Socket connection(String ip, int port) {
		Socket sock = null;
		try {
			sock = new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sock;
	}

	synchronized private void doWait(int i) {
		try {
			// wait for the client to connect to the server
			wait(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean byteArrayEquals(byte[] a, byte[] b) {
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++)
			if (a[i] != b[i])
				return false;
		return true;
	}
}
