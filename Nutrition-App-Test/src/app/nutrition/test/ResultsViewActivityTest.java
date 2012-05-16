package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
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
		
	}
}
