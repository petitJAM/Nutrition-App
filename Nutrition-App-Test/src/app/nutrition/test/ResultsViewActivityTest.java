package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import app.nutrition.ResultsViewActivity;

/**
 * Test the ResultsViewActivity class.
 * 
 * @author Alex Petitjean.
 *         Created May 3, 2012.
 */
public class ResultsViewActivityTest extends
		ActivityInstrumentationTestCase2<ResultsViewActivity> {

	private ResultsViewActivity rva;

	/**
	 * Constructor
	 * 
	 * @param activityClass
	 */
	public ResultsViewActivityTest() {
		super(ResultsViewActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		rva = getActivity();
	}

	/**
	 * Test creation of activity
	 */
	public void testSuccessfulCreation() {
		assertNotNull(rva);
	}

	/**
	 * Test that the NGM from NutritionAppActivity is successfully accessed.
	 */
	public void testGetNGMFromNutritionAppActivity() {
		fail();
	}
	
	/**
	 * Test sending NGM to server.
	 */
	public void testSendingNGMtoServer() {
		fail();
	}
	
	/**
	 * Test receiving info
	 */
	public void testReceiveInfoFromServer() {
		fail();
	}
	
	/**
	 * Test displaying results
	 * 
	 * ~~may not really be testable.
	 */
	public void testDisplayResults() {
		fail();
	}
}
