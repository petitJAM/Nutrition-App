package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import app.nutrition.ResultsViewActivity;

/**
 * Test the ResultsViewActivity class.
 *
 * @author Alex Petitjean.
 *         Created May 3, 2012.
 */
public class ResultsViewActivityTest extends ActivityInstrumentationTestCase2<ResultsViewActivity> {

	private ResultsViewActivity rva;
	
	/**
	 * Constructor
	 *
	 * @param activityClass
	 */
	public ResultsViewActivityTest(Class<ResultsViewActivity> activityClass) {
		super(activityClass);
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
	
	
}
