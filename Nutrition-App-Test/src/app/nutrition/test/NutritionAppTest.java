package app.nutrition.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import app.nutrition.NutritionAppActivity;

/**
 * Test the NutritionAppActivity class
 * 
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NutritionAppTest extends
		ActivityInstrumentationTestCase2<NutritionAppActivity> {

	private NutritionAppActivity naa;
	private String appName;

	/**
	 * Constructor
	 * 
	 * @param activityClass
	 */
	public NutritionAppTest(Class<NutritionAppActivity> activityClass) {
		super(activityClass);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		naa = getActivity();
		appName = naa.getString(app.nutrition.R.string.app_name);
	}

	/**
	 * Test creation
	 */
	public void testSuccessfulNutritionAppActivityCreation() {
		assertNotNull(naa);
	}

	/**
	 * Test app has a name
	 */
	public void testAppName() {
		assertEquals("Nutrition-App", appName);
	}
	
	/**
	 * Test camera calls
	 */
	@UiThreadTest
	public void testCameraButton() {
		Instrumentation instr = getInstrumentation();
		
	}
}
