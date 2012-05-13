package app.nutrition;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

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
	private static final int DIALOG_SERVER_CONNECTION_FAILED = 0;
	private static final int DIALOG_RESULTS_VIEW = 1;
	private static final int NUMBER_SERVER_CONNECTION_ATTEMPTS = 2;
	private static final int DIALOG_CONTACTING_SERVER = 3;
	/** Color Sequence to work with */
	public byte[] colorSequence = null;
	private Connection con = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		Log.d("ResultsViewActivity", "opened");
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
	 * @throws Exception
	 */
	public void getSequence() throws Exception {
		colorSequence = NutritionAppActivity.colorSequence;
		if (colorSequence == null) {
			Log.e("get Sequence", "Sequence to retrieve is null!");
			throw new NullPointerException("No Color Sequence available!");
		}
	}

	/**
	 * This method sends ResultsViewActivity.ngm to the server and waits for
	 * results.
	 * 
	 */
	public void sendSequence() {
		Log.d("send Seq", "Sending NGM");

		onCreateDialog(DIALOG_CONTACTING_SERVER);
		showDialog(DIALOG_CONTACTING_SERVER);

		Thread connectionThread = new Thread(new ServerConnectThread());
		connectionThread.start();
		try {
			connectionThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		dismissDialog(DIALOG_CONTACTING_SERVER);
		if (con == null) {
			onCreateDialog(DIALOG_SERVER_CONNECTION_FAILED);
			showDialog(DIALOG_SERVER_CONNECTION_FAILED);
		}

		con.sendInt(0); // 0 means this connection is asking for a list of
						// results
		con.sendByteArray(colorSequence);

		Food f1, f2, f3;
		try {
			if (con.recieveInt() == 3) { // should be 3 indicating the 3 results
				f1 = con.recieveFood();
				f2 = con.recieveFood();
				f3 = con.recieveFood();
				Log.d("Receive", f1.name);
				Log.d("Receive", f2.name);
				Log.d("Receive", f3.name);
			}
		} catch (IOException e) {
			Log.d("sendSequence ioexception", e.getMessage());
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
		if (sock == null)
			throw new NoRouteToHostException();
		return new Connection(sock);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dog = null;
		switch (id) {
		case DIALOG_SERVER_CONNECTION_FAILED:
			AlertDialog.Builder dogbuilder = new AlertDialog.Builder(this);
			dogbuilder.setMessage(getString(R.string.no_response_server))
					.setPositiveButton(getString(R.string.ok),
							new OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									Log.d("Failed to hear server dialog",
											"Server did not return");
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
		case DIALOG_RESULTS_VIEW:
			// create it again
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
				} catch (NoRouteToHostException e) {
					Log.d("NoRouteToHost", "No connection made");
				}
				count++;
			}
		}

	}
}
