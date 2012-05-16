package app.nutrition;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This class handles directly search for a food by text input rather than image
 * analysis.
 * 
 * @author Alex Petitjean.
 *         Created May 14, 2012.
 */
public class SearchActivity extends Activity {
	
	private EditText searchBar;

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.search);
		
		Button search_go = (Button) findViewById(R.id.search_go);
		this.searchBar = (EditText) findViewById(R.id.searchbar);
		
		search_go.setOnClickListener(new SearchOnClickListener());
	}
	
	private class SearchOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			Log.d("SearchGo", "clicked!");
			String foodName = SearchActivity.this.searchBar.getText().toString();
			Log.d("about to try connection", "");
			try {
				Connection con = getConnection();
				con.sendInt(1);
				con.sendString(foodName);
				
				int receivedint = con.recieveInt();
				if (receivedint == 4) {
					Food f = con.recieveFood();
					ResultsViewActivity.displayFood = f;
					Intent displayIntent = new Intent(SearchActivity.this, NutritionDisplayActivity.class);
					SearchActivity.this.startActivity(displayIntent);
					Log.d("Name of recieved food", "" + f.name);
				} else if (receivedint == 5) {
					// close
					con.sendInt(2);
					// magic that tells the user food not found
				}
				
			} catch (NoRouteToHostException e) {
				Log.d("Error", "Can't connect");
			} catch (IOException e) {
				Log.d("sendName ioexception", ""+ e.getMessage());
			}
		}
	}
	
	/**
	 * Get a connection to the server.
	 * 
	 * @return connection to the server
	 * @throws NoRouteToHostException
	 */
	public Connection getConnection() throws NoRouteToHostException {
		Log.d("getting connection...", "");
		Socket sock = null;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String ip = prefs.getString("IP_ADDRESS", "137.112.136.208");
		int port = Integer.parseInt(prefs.getString("PORT", "12345"));
		Log.d("trying connection...", "");
		try {
			sock = new Socket(ip, port);
		} catch (UnknownHostException e) {
			Log.d("getConnection", e.getMessage());
		} catch (IOException e) {
			Log.d("getConnection", e.getMessage());
		}
		if (sock == null) throw new NoRouteToHostException();
		return new Connection(sock);
	}
}
