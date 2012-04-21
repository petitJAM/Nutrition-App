package network;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConnectionTest {

	String ip = "127.0.0.1";
	String port = "12345";

	@Test
	public void testCreateClientConnectionSucceeds() {
		Connection connection = new Connection(ip, port);
		assertNotNull(connection);
	}

	@Test
	public void testCreateServerConnectionSucceeds() {
		Connection connection = new Connection(port);
		assertNotNull(connection);
	}

	@Test
	public void testOpenConnectionFromClientToServer() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		assertTrue(client.connect());
	}

	@Test
	public void testServerCreatesNewThreadToHandleClient() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertNotNull(server.getClientConnection(0));
	}

	@Test
	public void testClientSendsServerByteProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(client.sendByte((byte) 1));
		assertEquals(server.getClientConnection(0).recieveByte(), (byte) 1);
	}

	@Test
	public void testServerSendsClientByteProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(server.sendByte((byte) 1));
		assertEquals(client.getClientConnection(0).recieveByte(), (byte) 1);
	}

	@Test
	public void testClientSendsServerIntProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(client.sendInt(1234567));
		assertEquals(server.getClientConnection(0).recieveInt(), 1234567);
	}

	@Test
	public void testServerSendsClientIntProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(server.sendInt(1234567));
		assertEquals(client.getClientConnection(0).recieveInt(), 1234567);
	}

	@Test
	public void testClientSendsServerCharProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(client.sendChar('a'));
		assertEquals(server.getClientConnection(0).recieveChar(), 'a');
	}

	@Test
	public void testServerSendsClientCharProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(server.sendChar('a'));
		assertEquals(client.getClientConnection(0).recieveChar(), 'a');
	}

	@Test
	public void testClientSendsServerStringProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(client.sendString("hello man, what's going on yo?"));
		assertEquals(server.getClientConnection(0).recieveString(),
				"hello man, what's going on yo?");
	}

	@Test
	public void testServerSendsClientStringProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(server.sendString("hello man, what's going on yo?"));
		assertEquals(client.getClientConnection(0).recieveString(),
				"hello man, what's going on yo?");
	}

	@Test
	public void testClientSendsServerByteArrayProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(client.sendByteArray(new byte[] { (byte) 0, (byte) 1 }));
		assertEquals(server.getClientConnection(0).recieveByteArray(),
				new byte[] { (byte) 0, (byte) 1 });
	}

	@Test
	public void testServerSendsClientByteArrayProperly() {
		Connection server = new Connection(port);
		Connection client = new Connection(ip, port);
		client.connect();
		assertTrue(server.sendByteArray(new byte[] { (byte) 0, (byte) 1 }));
		assertEquals(client.getClientConnection(0).recieveByteArray(),
				new byte[] { (byte) 0, (byte) 1 });
	}

	@Test
	public void testServerAllowsTwoClients() {
		Connection server = new Connection(port);
		Connection client1 = new Connection(ip, port);
		Connection client2 = new Connection(ip, port);
		assertTrue(client1.connect());
		assertTrue(client2.connect());
	}

	@Test
	public void testServerHandlesTwoClients() {
		Connection server = new Connection(port);
		Connection client1 = new Connection(ip, port);
		Connection client2 = new Connection(ip, port);
		assertTrue(client1.connect());
		try {
			wait(10); // to ensure the correct ordering of the clients
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(client2.connect());
		assertTrue(client1.sendInt(123465));
		assertTrue(client2.sendInt(654321));
		assertEquals(server.getClientConnection(0).recieveInt(), 123465);
		assertEquals(server.getClientConnection(1).recieveInt(), 654321);
	}
}
