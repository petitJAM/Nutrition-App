package app.nutrition;

import android.app.Activity;
import android.os.Bundle;

/**
 * Displays the "about us" page.
 * 
 * @author Alex Petitjean.
 *         Created May 14, 2012.
 */
public class InfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceBundle) {
		super.onCreate(savedInstanceBundle);
		setContentView(R.layout.info);
	}
}
