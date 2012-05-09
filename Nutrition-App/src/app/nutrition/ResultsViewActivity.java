package app.nutrition;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		sendNGM();
	}

	/**
	 * Set ResultsViewActivity.ngm to the NGramModel in NutritionAppActivity
	 * 
	 * @throws Exception
	 */
	public void getNGM() throws Exception {

	}

	
	// you don't really want to run the code in the state it's in
	// it needs some work with connecting
	// and i honestly have no idea why all the dialogs are named with '*dog'
	/**
	 * This method sends ResultsViewActivity.ngm to the server and waits for
	 * results.
	 * 
	 */
	public void sendNGM() {
		Log.d("send NGM", "Sending NGM");
		// convert the ngm to a transmitable format
		// send to server
		
		ProgressDialog progdog = ProgressDialog.show(this, "",
			getString(R.string.wait_dialog), true);
		
		Log.d("send NGM", "Progress dialog created");
		
		// wait for response with timeout
		// waitloop
		progdog.dismiss();

		// show another dialog on fail
		{
			AlertDialog.Builder dogbuilder = new AlertDialog.Builder(this);
			dogbuilder.setMessage(getString(R.string.no_response_server))
					.setPositiveButton(getString(R.string.ok), new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							Log.d("Failed to hear server dialog", "Server did not return");
							dialog.cancel(); // probably change this to handle
												// failed server request
												// quit back to NutritionAppActivity or try again maybe?
						}
					});
			AlertDialog alertdog = dogbuilder.create();
			alertdog.show();
		}

		// get the returned list of foods and somehow store so display results
		// knows about them (or pass them along, i guess)
		// call display results
	}

	/**
	 * This method is called after the ResultsViewActivity.ngm is sent to the
	 * server
	 * and the server returns the results.
	 * 
	 * @param foods - list of all the foods in order of most likeliness
	 */
	public void displayResults(ArrayList<Object> foods) { //TODO change to ArrayList<Food>
		// might have to pull this out to a class that extends ListView
		// idk.
	}
}
