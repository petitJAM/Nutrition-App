package app.nutrition;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import Database.Food;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean. Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	/** Color Sequence to work with */
	public byte[] colorSequence = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		Log.d("ResultsViewActivity", "opened");
		try {
			getSequence();
			sendSequence();
		} catch (Exception e) {
			if (e.getMessage() != null)
				Log.d("getNGM()", e.getMessage());
			else
				Log.d("getNGM()", "No message");
			finish();
		}
	}

	/**
	 * Set ResultsViewActivity.colorSequence to the colorSequence in NutritionAppActivity
	 * 
	 * @throws Exception
	 */
	public void getSequence() throws Exception {
		colorSequence = NutritionAppActivity.colorSequence;
		if (colorSequence == null) { 
			Log.e("getNGM", "Sequence to retrieve is null!");
			throw new NullPointerException("No Color Sequence available!");
		}
	}

	// you don't really want to run the code in the state it's in
	// it needs some work with connecting
	// and i honestly have no idea why all the dialogs are named with '*dog'
	/**
	 * This method sends ResultsViewActivity.ngm to the server and waits for
	 * results.
	 * 
	 */
	public void sendSequence() {
		Log.d("send NGM", "Sending NGM");
		// convert the ngm to a transmitable format
		// send to server

		Connection con = getConnection();
		con.sendInt(0); // 0 means this connection is asking for a list of results
		con.sendByteArray(colorSequence);

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
					.setPositiveButton(getString(R.string.ok),
							new OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									Log.d("Failed to hear server dialog",
											"Server did not return");
									finish(); // probably change this to handle
												// failed server request
												// quit back to
												// NutritionAppActivity or try
												// again maybe?
								}
							});
			AlertDialog alertdog = dogbuilder.create();
			alertdog.show();
		}

		// get the returned list of foods and somehow store so display results
		// knows about them (or pass them along, i guess)
		// call display results
		try {
			con.recieveInt(); // should be 3 indicating the 3 results
			Connection.deSerialize(con.recieveByteArray());
			Connection.deSerialize(con.recieveByteArray());
			Connection.deSerialize(con.recieveByteArray());
		} catch (IOException e) {
			Log.d("sendSequence", e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.d("sendSequence", e.getMessage());
		}

	}

	private Connection getConnection() {
		Socket sock = null;
		try {
			sock = new Socket("127.0.0.1", 12345);
		} catch (UnknownHostException e) {
			Log.d("getConnection", e.getMessage());
		} catch (IOException e) {
			Log.d("getConnection", e.getMessage());
		}
		return new Connection(sock);
	}

	/**
	 * This method is called after the ResultsViewActivity.ngm is sent to the
	 * server and the server returns the results.
	 * 
	 * @param foods
	 *            - list of all the foods in order of most likeliness
	 */
	public void displayResults(List<Food> foods) { // TODO change to
													// ArrayList<Food>
		// might have to pull this out to a class that extends ListView
		// idk.
		// ListView results = (ListView) findViewById(R.id.results_list);
		// results.setAdapter(new ArrayAdapter<Object>(this, foods, null));
		// //not correct
	}
}
