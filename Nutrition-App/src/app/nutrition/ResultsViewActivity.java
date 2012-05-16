package app.nutrition;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean. Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	private static final int NUMBER_SERVER_CONNECTION_ATTEMPTS = 2;

	/* Dialog constants */
	private static final int DIALOG_SERVER_CONNECTION_FAILED = 0;
	private static final int DIALOG_RESULTS_VIEW = 1;
	private static final int DIALOG_CONTACTING_SERVER = 2;
	private static final int DIALOG_WAITING_FOR_SERVER_RESPONSE = 3;

	/** Color Sequence to work with */
	public byte[] colorSequence = null;
	/** Food to display */
	public static Food displayFood;

	/** Connection to server */
	private Connection con = null;
	/** Foods to display */
	private ArrayList<Food> foods;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);

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

		int count = 0;
		while (con == null && count < NUMBER_SERVER_CONNECTION_ATTEMPTS) {
			Log.d("trying connection...", (count + 1) + "");
			try {
				con = getConnection();
				Log.d("connection success", con.toString());
			} catch (NoRouteToHostException e) {}
			count++;
		}
		// Thread connectionThread = new Thread(new ServerConnectThread());
		// connectionThread.start();
		// try {
		// Log.d("doWait", "");
		// doWait();
		// connectionThread.join();
		// dismissDialog(DIALOG_CONTACTING_SERVER);
		// } catch (InterruptedException e) {
		// Log.d("ConnectionThread", e.getMessage());
		// }

		if (con == null) {
			onCreateDialog(DIALOG_SERVER_CONNECTION_FAILED);
			showDialog(DIALOG_SERVER_CONNECTION_FAILED);
		}
		else {
			Log.d("Sending", "Sending " + colorSequence.toString());
			onCreateDialog(DIALOG_WAITING_FOR_SERVER_RESPONSE);
			showDialog(DIALOG_WAITING_FOR_SERVER_RESPONSE);

			// 0 means this connection is asking for a list of results
			con.sendInt(0);
			con.sendByteArray(colorSequence);

			try {
				// should be 3 indicating the 3 results
				if (con.recieveInt() == 3) {
					foods = new ArrayList<Food>();
					foods.add(con.recieveFood());
					foods.add(con.recieveFood());
					foods.add(con.recieveFood());
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
			con.sendInt(2); // 1 means we are closing the socket and the server
							// should do the same
		}
	}

	/**
	 * Get a connection to the server.
	 * 
	 * @return connection to the server
	 * @throws NoRouteToHostException
	 */
	public Connection getConnection() throws NoRouteToHostException {
		Socket sock = null;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String ip = prefs.getString("IP_ADDRESS", "137.112.136.208");
		int port = Integer.parseInt(prefs.getString("PORT", "12345"));
		Log.d("getConnection", "Socket-- IP: " + ip + " port: " + port);
		try {
			sock = new Socket(ip, port);
			Log.d("made a socket", sock.toString());
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
						displayFood = foods.get(0);
						break;
					case DialogInterface.BUTTON2:
						displayFood = foods.get(1);
						break;
					case DialogInterface.BUTTON3:
						displayFood = foods.get(2);
						break;
					default:
						break;
					}
					Intent displayIntent = new Intent(ResultsViewActivity.this,
							NutritionDisplayActivity.class);
					startActivity(displayIntent);
				}
			});
			dog = resultsBuilder.create();
			break;
		default:
			dog = null;
		}
		return dog;
	}

	// private class ServerConnectThread implements Runnable {
	// public void run() {
	// int count = 0;
	// while (con == null && count < NUMBER_SERVER_CONNECTION_ATTEMPTS) {
	// Log.d("trying connection...", (count + 1) + "");
	// try {
	// con = getConnection();
	// Log.d("connection success", con.toString());
	// } catch (NoRouteToHostException e) {}
	// count++;
	// }
	// }
	// }

	// private class FoodListAdapter extends ArrayAdapter<Food> {
	// private List<Food> noms;
	//
	// public FoodListAdapter(Context context, int textViewResourceId,
	// List<Food> noms) {
	// super(context, textViewResourceId, noms);
	// this.noms = noms;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// return null;
	// }
	// }

	// synchronized private void doWait() {
	// try {
	// wait(1000);
	// } catch (InterruptedException e) {}
	// }
}
