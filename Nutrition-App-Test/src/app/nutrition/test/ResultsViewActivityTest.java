package app.nutrition.test;

import java.net.NoRouteToHostException;

import android.test.ActivityInstrumentationTestCase2;
import app.nutrition.Connection;
import app.nutrition.ResultsViewActivity;

/**
 * Test the ResultsViewActivity class
 *
 * @author Alex Petitjean.
 *         Created May 16, 2012.
 */
public class ResultsViewActivityTest extends ActivityInstrumentationTestCase2<ResultsViewActivity> {
	
	private ResultsViewActivity rva;
	
	/**
	 * Constructor
	 */
	public ResultsViewActivityTest() {
		super(ResultsViewActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rva = getActivity();
	}
	
	/**
	 * Test creation
	 */
	public void testCreation() {
		assertNotNull(rva);
	}
	
	/**
	 * Test that a Connection is made correctly.
	 */
	public void testGetConnection() {
		Connection c = null;
		String ip = "137.112.115.138";
		int port = 12345;
		
		try {
			c = rva.getConnection(ip, port);
		} catch (NoRouteToHostException e) {
			fail();
		}
		
		assertNotNull(c);
		c.sendInt(2);
		c.close();
	}
}
