package app.nutrition.test;

import junit.framework.TestCase;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import app.nutrition.NutritionAppActivity;

/**
 * Test the NutritionAppActivity
 *
 * @author Alex Petitjean.
 *         Created May 3, 2012.
 */
public class NutritionAppTest2 extends ActivityUnitTestCase<NutritionAppActivity> {

	private NutritionAppActivity naa;
	
	/**
	 * Constructor
	 *
	 * @param activityClass
	 */
	public NutritionAppTest2(Class<NutritionAppActivity> activityClass) {
		super(activityClass);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		naa = getActivity();
	}

	/**
	 * Test that Camera Intent is opened.
	 */
	public void testCameraIntentCalled() {
		Button cb = (Button) naa.findViewById(app.nutrition.R.id.camera_button);
		cb.setPressed(true);
		int req_code = getStartedActivityRequest();
		Intent intent = getStartedActivityIntent();
		assertEquals(NutritionAppActivity.TAKE_PICTURE, req_code);
		assertEquals("android.media.action.IMAGE_CAPTURE", intent.getAction());
	}
}
