package app.nutrition;

import java.util.ArrayList;

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
	
	/**
	 * This method sends ResultsViewActivity.ngm to the server and waits for results.
	 *
	 */
	public void sendNGM() {
		// convert the ngm to a transmitable format
		// send to server
		// wait for response with timeout
		// get the returned list of foods and somehow store so display results knows about them
		// call display results
	}
	
	/**
	 * This method is called after the ResultsViewActivity.ngm is sent to the server
	 * and the server returns the results.
	 * 
	 * @param foods - list of all the foods in order of most likeliness
	 */
	public void displayResults(ArrayList<Object> foods) {
		
	}
}
