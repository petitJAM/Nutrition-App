package app.nutrition;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
		Log.d("hello!", "");
		super.onCreate(savedInstanceBundle);
		
		Log.d("super called", "");
		setContentView(R.layout.nutrition_display);
		
//		displayFood = ResultsViewActivity.displayFood;
		Log.d("set view", "");
		displayFood = new Food("Nom", 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f);
		Log.d("created food", "");
		
		TextView foodname = (TextView) findViewById(R.id.food_name);
		foodname.setText(displayFood.name);
	}
}
