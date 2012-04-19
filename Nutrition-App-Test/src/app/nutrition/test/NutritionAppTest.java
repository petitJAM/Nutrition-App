package app.nutrition.test;

import android.test.ActivityInstrumentationTestCase2;
import app.nutrition.NutritionAppActivity;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NutritionAppTest extends
		ActivityInstrumentationTestCase2<NutritionAppActivity> {
	private NutritionAppActivity naa;
	private String appName;

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param activityClass
	 */
	public NutritionAppTest(Class<NutritionAppActivity> activityClass) {
		super(activityClass);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		naa = new NutritionAppActivity();
		appName = naa.getString(app.nutrition.R.string.app_name);
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void testSuccessfulNutritionAppActivityCreation() {
		assertNotNull(naa);
	}
	
	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	public void testAppName() {
		assertEquals("Nutrition-App", appName);
	}
}
