package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
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
	private ImageButton camera_button;
	
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
		camera_button = (ImageButton) naa.findViewById(app.nutrition.R.id.camera_button);
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
		assertEquals("Nutrition App", appName);
	}
	
}
