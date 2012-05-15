package app.nutrition;

import android.app.Activity;
import android.os.Bundle;
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
		}
	}
}
