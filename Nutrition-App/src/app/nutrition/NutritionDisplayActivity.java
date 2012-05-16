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
		displayFood = ResultsViewActivity.displayFood;
		setContentView(R.layout.nutrition_display);
		
		displayFood = ResultsViewActivity.displayFood;
		
		TextView foodname = (TextView) findViewById(R.id.food_name);
		foodname.setText(displayFood.name);
	}
}
