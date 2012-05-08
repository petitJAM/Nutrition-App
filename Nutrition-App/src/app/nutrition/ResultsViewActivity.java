package app.nutrition;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean.
 *         Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	/** NGramModel to work with */
	public NGramModel ngm = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
	}
	
	/**
	 * Set ResultsViewActivity.ngm to the NGramModel in NutritionAppActivity
	 * @throws Exception 
	 */
	public void getNGM() throws Exception {
		
	}
	
	
}
