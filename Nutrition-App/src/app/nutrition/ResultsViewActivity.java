package app.nutrition;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean. Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	private static final int PORT = 12345;

	private static final int NUMBER_SERVER_CONNECTION_ATTEMPTS = 2;

	/* Dialog constants */
	private static final int DIALOG_SERVER_CONNECTION_FAILED = 0;
	private static final int DIALOG_RESULTS_VIEW = 1;
	private static final int DIALOG_CONTACTING_SERVER = 2;
	private static final int DIALOG_WAITING_FOR_SERVER_RESPONSE = 3;

	/** Color Sequence to work with */
	public byte[] colorSequence = null;

	/** Connection to server */
	private Connection con = null;
	/** Foods to display */
	private ArrayList<Food> foods;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
//		PreferenceManager.setDefaultValues(this, )
//		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//		Editor e = prefs.edit();
//		e.putString("IP_ADDRESS", "137.112.136.208");
//		e.commit();
		try {
			getSequence();
			sendSequence();
		} catch (Exception ex) {
			Log.d("get seq()", ex.toString());
			// finish();
		}
	}

	/**
	 * Set ResultsViewActivity.colorSequence to the colorSequence in
	 * NutritionAppActivity
	 * 
	 * @throws NullPointerException
	 */
	public void getSequence() throws NullPointerException {
		colorSequence = NutritionAppActivity.colorSequence;
		if (colorSequence == null) {
			Log.e("get Sequence", "Sequence to retrieve is null!");
			throw new NullPointerException("No Color Sequence available!");
		}
	}

	/**
	 * This method sends ResultsViewActivity.ngm to the server and waits for
	 * results.
	 */
	public void sendSequence() {
		onCreateDialog(DIALOG_CONTACTING_SERVER);
		showDialog(DIALOG_CONTACTING_SERVER);

		Thread connectionThread = new Thread(new ServerConnectThread());
		connectionThread.start();
		try {
			Log.d("doWait", "");
			doWait();
			connectionThread.join();
			dismissDialog(DIALOG_CONTACTING_SERVER);
		} catch (InterruptedException e) {
			Log.d("ConnectionThread", e.getMessage());
		}
		// Log.d("After join", "");
		// dismissDialog(DIALOG_CONTACTING_SERVER);

		if (con == null) {
			onCreateDialog(DIALOG_SERVER_CONNECTION_FAILED);
			showDialog(DIALOG_SERVER_CONNECTION_FAILED);
		}
		else {
			Log.d("Sending", "Sending " + colorSequence.toString());
			onCreateDialog(DIALOG_WAITING_FOR_SERVER_RESPONSE);
			showDialog(DIALOG_WAITING_FOR_SERVER_RESPONSE);

			con.sendInt(0); // 0 means this connection is asking for a list of
							// results
			con.sendByteArray(colorSequence);

			try {
				if (con.recieveInt() == 3) { // should be 3 indicating the 3
												// results
					foods = new ArrayList<Food>();
					foods.add(con.recieveFood());
					foods.add(con.recieveFood());
					foods.add(con.recieveFood());
					Log.d("Receive", foods.get(0).name);
					Log.d("Receive", foods.get(1).name);
					Log.d("Receive", foods.get(2).name);
					onCreateDialog(DIALOG_RESULTS_VIEW);
					showDialog(DIALOG_RESULTS_VIEW);
				}
				else {
					Log.d("Receive", "Error in receiving results");
				}
				dismissDialog(DIALOG_WAITING_FOR_SERVER_RESPONSE);
			} catch (IOException e) {
				Log.d("sendSequence ioexception", e.getMessage());
			}
		}
		con.sendInt(1); // 1 means we are closing the socket and the server
						// should do the same
	}

	/**
	 * Get a connection to the server.
	 * 
	 * @return connection to the server
	 * @throws NoRouteToHostException
	 */
	public Connection getConnection() throws NoRouteToHostException {
		Socket sock = null;
		// The default value is whatever the IP was at the time of this change.
		// Not necessarily an appropriate value. (Though it probably doesn't matter)
		String ip = getPreferences(MODE_PRIVATE).getString("IP_ADDRESS", "137.112.136.208");
		try {
			sock = new Socket(ip, PORT);
		} catch (UnknownHostException e) {
			Log.d("getConnection", e.getMessage());
		} catch (IOException e) {
			Log.d("getConnection", e.getMessage());
		}
		if (sock == null) throw new NoRouteToHostException();
		return new Connection(sock);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dog = null;
		switch (id) {
		case DIALOG_SERVER_CONNECTION_FAILED:
			AlertDialog.Builder failedBuilder = new AlertDialog.Builder(this);
			failedBuilder.setMessage(getString(R.string.no_response_server))
					.setPositiveButton(getString(R.string.ok), new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dismissDialog(DIALOG_SERVER_CONNECTION_FAILED);
							finish();
						}
					});

			dog = failedBuilder.create();
			break;
		case DIALOG_CONTACTING_SERVER:
			dog = new ProgressDialog(this);
			dog.setTitle(getString(R.string.wait_dialog));
			break;
		case DIALOG_WAITING_FOR_SERVER_RESPONSE:
			dog = new ProgressDialog(this);
			dog.setTitle(getString(R.string.wait_dialog));
		case DIALOG_RESULTS_VIEW:
			// create it again
			AlertDialog.Builder resultsBuilder = new AlertDialog.Builder(this);
			ListAdapter resultsAdapter = null;
			if (foods != null)
				resultsAdapter = new ArrayAdapter<Food>(this, R.layout.results_list,
						foods);
			else
				Log.d("Null list", "");

			resultsBuilder.setAdapter(resultsAdapter, new OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case DialogInterface.BUTTON1:
						// TODO
						break;
					case DialogInterface.BUTTON2:
						// TODO
						break;
					case DialogInterface.BUTTON3:
						// TODO
						break;
					default:
						break;
					}
				}
			});
			dog = resultsBuilder.create();
			break;
		default:
			dog = null;
		}
		return dog;
	}

	private class ServerConnectThread implements Runnable {
		public void run() {
			int count = 0;
			while (con == null && count < NUMBER_SERVER_CONNECTION_ATTEMPTS) {
				Log.d("trying connection...", (count + 1) + "");
				try {
					con = getConnection();
					Log.d("connection success", con.toString());
				} catch (NoRouteToHostException e) {}
				count++;
			}
		}
	}

	private class FoodListAdapter extends ArrayAdapter<Food> {
		private List<Food> noms;

		public FoodListAdapter(Context context, int textViewResourceId, List<Food> noms) {
			super(context, textViewResourceId, noms);
			this.noms = noms;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			return null;
		}
	}

	synchronized private void doWait() {
		try {
			wait(100);
		} catch (InterruptedException e) {}
	}
}
