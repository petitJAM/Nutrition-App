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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
	}
}
