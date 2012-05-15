package app.nutrition;

import android.app.Activity;
import android.os.Bundle;

/**
 * Display the results from ResultsViewActivity
 *
 * @author Alex Petitjean.
 *         Created May 14, 2012.
 */
public class NutritionDisplayActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.nutrition_display);
	}
}
