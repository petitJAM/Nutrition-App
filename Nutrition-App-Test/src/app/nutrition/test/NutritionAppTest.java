package app.nutrition.test;

import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
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
	 * Constructor.
	 * 
	 * (Shut up Rob)
	 */
	public NutritionAppTest() {
		super(NutritionAppActivity.class);
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
	 * Test that Camera Intent is opened.
	 */
	public void testCameraIntentCalled() {
		Instrumentation instr = getInstrumentation();
		Button cb = (Button) naa.findViewById(app.nutrition.R.id.camera_button);
		cb.setPressed(true);
//		int req_code = getStartedActivityRequest();
//		Intent intent = getStartedActivityIntent();
//		assertEquals(NutritionAppActivity.TAKE_PICTURE, req_code);
//		assertEquals("android.media.action.IMAGE_CAPTURE", intent.getAction());
	}
	
	/**
	 * Test camera calls
	 */
	@UiThreadTest
	public void testCameraButton() {
//		Instrumentation instr = getInstrumentation();
		fail("test not implemented");
	}
}
