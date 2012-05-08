package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import app.nutrition.NGramModel;
import app.nutrition.NutritionAppActivity;
import app.nutrition.ResultsViewActivity;
import app.nutrition.TransitionMatrix;

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
		TransitionMatrix t = new TransitionMatrix(4);
		NGramModel ngm = new NGramModel("test", t);
		NutritionAppActivity.ngm = ngm;
		try {
			rva.getNGM();
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(ngm, rva.ngm);
	}

	/**
	 * Test exception thrown on null ngm.
	 */
	public void testGetNGMFromNutritionAppActivityWithNullNGM() {
		NutritionAppActivity.ngm = null;
		try {
			rva.getNGM();
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
	}

	/**
	 * Test sending NGM to server.
	 */
	public void testSendingNGMtoServer() {
		TransitionMatrix t = new TransitionMatrix(4);
		rva.ngm = new NGramModel("test", t);
		
	}

	/**
	 * Test receiving info.
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
