package app.nutrition.test;

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
	 */
	public NutritionAppTest2() {
		super(NutritionAppActivity.class);
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		naa = getActivity();
	}
	
	/**
	 * Test Activity Creation
	 */
	public void testActivityCreation() {
		assertNotNull(naa);
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
