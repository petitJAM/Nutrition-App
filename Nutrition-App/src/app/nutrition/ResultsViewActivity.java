package app.nutrition;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean. Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	private static final int PORT = 12345;
	private static final String IP_ADDRESS = "137.112.115.102";
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
		Log.d("send Seq", "Sending NGM");
		// convert the ngm to a transmitable format
		// send to server
		ProgressDialog progdog = ProgressDialog.show(this, "",
			getString(R.string.wait_dialog), true);
		Log.d("send Seq", "Progress dialog created");

		Connection con = getConnection();
		con.sendInt(0); // 0 means this connection is asking for a list of results
		con.sendByteArray(colorSequence);

		Food f1, f2, f3;
		// get the returned list of foods and somehow store so display results
		// knows about them (or pass them along, i guess)
		// call display results
		try {
			con.recieveInt(); // should be 3 indicating the 3 results
			f1 = con.recieveFood();
			f2 = con.recieveFood();
			f3 = con.recieveFood();
			Log.d("Receive", f1.name);
			Log.d("Receive", f2.name);
			Log.d("Receive", f3.name);
		} catch (IOException e) {
			Log.d("sendSequence ioexception", e.getMessage());
		}
		// wait for response with timeout
		// waitloop
		Log.d("send Seq", "Progress dialog dismissed");
//		progdog.dismiss();

		// show another dialog on fail
//		{
//			AlertDialog.Builder dogbuilder = new AlertDialog.Builder(this);
//			dogbuilder.setMessage(getString(R.string.no_response_server))
//					.setPositiveButton(getString(R.string.ok),
//							new OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									Log.d("Failed to hear server dialog",
//											"Server did not return");
//									finish(); // probably change this to handle
//												// failed server request
//												// quit back to
//												// NutritionAppActivity or try
//												// again maybe?
//								}
//							});
//			AlertDialog alertdog = dogbuilder.create();
//			alertdog.show();
//		}
	}

	private Connection getConnection() {
		Socket sock = null;
		try {
			sock = new Socket(IP_ADDRESS, PORT);
		} catch (UnknownHostException e) {
			Log.d("getConnection", e.getMessage());
		} catch (IOException e) {
			Log.d("getConnection", e.getMessage());
		}
		return new Connection(sock);
	}

//	/**
//	 * This method is called after the ResultsViewActivity.ngm is sent to the
//	 * server and the server returns the results.
//	 * 
//	 * @param foods
//	 *            - list of all the foods in order of most likeliness
//	 */
//	public void displayResults(List<Food> foods) { // TODO change to
//													// ArrayList<Food>
//		ListPopupWindow foodDisplay = new ListPopupWindow(this);
//		ListAdapter la = new ArrayAdapter<Food>(this, 0, foods);
//		foodDisplay.setAdapter(la);
//		foodDisplay.show();
//	}
}
