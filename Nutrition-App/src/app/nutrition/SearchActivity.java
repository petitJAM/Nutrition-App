package app.nutrition;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
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
			String foodName = SearchActivity.this.searchBar.getText().toString();
			try {
				Connection con = getConnection();
				con.sendInt(1);
				con.sendString(foodName);
				
				if(con.recieveInt() == 4) {
					Food f = con.recieveFood();
					Log.d("Name of recieved food", "" + f.name);	
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
		Socket sock = null;
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String ip = prefs.getString("IP_ADDRESS", "137.112.136.208");
		int port = Integer.parseInt(prefs.getString("PORT", "12345"));
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
