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
import android.os.Bundle;
import android.util.Log;

/**
 * Activity to send analyzed image data to a server and wait for the results.
 * 
 * @author Alex Petitjean. Created May 2, 2012.
 */
public class ResultsViewActivity extends Activity {

	private static final int PORT = 12345;
	private static final String IP_ADDRESS = "137.112.115.102";
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
		try {
			getSequence();
			sendSequence();
		} catch (Exception e) {
			Log.d("get seq()", e.toString());
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
		try {
			sock = new Socket(IP_ADDRESS, PORT);
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
			AlertDialog.Builder dogbuilder = new AlertDialog.Builder(this);
			dogbuilder.setMessage(getString(R.string.no_response_server))
					.setPositiveButton(getString(R.string.ok), new OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							dismissDialog(DIALOG_SERVER_CONNECTION_FAILED);
							finish();
						}
					});

			dog = dogbuilder.create();
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
			dog = new Dialog(this);
			dog.setTitle("Implement me!");
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

	synchronized private void doWait() {
		try {
			wait(100);
		} catch (InterruptedException e) {}
	}
}
