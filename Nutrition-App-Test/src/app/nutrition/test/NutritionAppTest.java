package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import app.nutrition.NutritionAppActivity;
import app.nutrition.R;

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
		set = (ImageButton) naa.findViewById(R.id.settings_button);
		info = (ImageButton) naa.findViewById(R.id.info_button);
		search = (ImageButton) naa.findViewById(R.id.search_button);
		exit = (ImageButton) naa.findViewById(R.id.exit_button);
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
