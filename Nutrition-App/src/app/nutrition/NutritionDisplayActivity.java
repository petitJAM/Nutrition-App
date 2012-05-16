package app.nutrition;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Display the results from ResultsViewActivity
 *
 * @author Alex Petitjean.
 *         Created May 14, 2012.
 */
public class NutritionDisplayActivity extends Activity {
	private Food displayFood;

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.nutrition_display);
		displayFood = ResultsViewActivity.displayFood;
		setTexts();
	}
	
	/**
	 * Set all the TextViews to the appropriate values.
	 *
	 */
	public void setTexts() {
		((TextView) findViewById(R.id.food_name)).setText(displayFood.name);
		((TextView) findViewById(R.id.cal_text)).setText(getString(R.string.calories) + "  " + displayFood.calories);
		((TextView) findViewById(R.id.carbs_text)).setText(getString(R.string.carbs) + "  " + displayFood.carbs);
		((TextView) findViewById(R.id.calfromfat_text)).setText(getString(R.string.calfromfat) + "  " + displayFood.calFromFat);
		((TextView) findViewById(R.id.totalfat_text)).setText(getString(R.string.totalfat) + "  " + displayFood.totalFat);
		((TextView) findViewById(R.id.fiber_text)).setText(getString(R.string.fiber) + "  " + displayFood.fiber);
		((TextView) findViewById(R.id.protein_text)).setText(getString(R.string.protein) + "  " + displayFood.protein);
		((TextView) findViewById(R.id.sodium_text)).setText(getString(R.string.sodium) + "  " + displayFood.sodium);
		((TextView) findViewById(R.id.sugar_text)).setText(getString(R.string.sugar) + "  " + displayFood.sugar);
	}
}
