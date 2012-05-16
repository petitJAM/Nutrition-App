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
	private ImageButton camera, set, info, search, exit;
	
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
		camera = (ImageButton) naa.findViewById(app.nutrition.R.id.camera_button);
		set = (ImageButton) naa.findViewById(app.nutrition.R.id.settings_button);
		info = (ImageButton) naa.findViewById(app.nutrition.R.id.info_button);
		search = (ImageButton) naa.findViewById(app.nutrition.R.id.search_button);
		exit = (ImageButton) naa.findViewById(app.nutrition.R.id.exit_button);
	}

	/**
	 * Test creation
	 */
	public void testSuccessfulNutritionAppActivityCreation() {
		assertNotNull(naa);
	}

	/**
	 * Test app has a name
	 * Moved to L10NTest
	 */
//	public void testAppName() {
//		assertEquals("Nutrition App", appName);
//	}
	
	/**
	 * Test that camera button created
	 */
	public void testCameraCreation() {
		assertNotNull(camera);
	}
	
	/**
	 * Test that settings button created
	 */
	public void testSettingsCreation() {
		assertNotNull(set);
	}
	
	/**
	 * Test that info button created
	 */
	public void testInfoCreation() {
		assertNotNull(info);
	}
	
	/**
	 * Test that search button created
	 */
	public void testSearchCreation() {
		assertNotNull(search);
	}
	
	/**
	 * Test that exit button created
	 */
	public void testExitCreation() {
		assertNotNull(exit);
	}
	

	
}
