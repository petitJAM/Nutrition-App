package app.nutrition;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Starting point and main screen of the app
 * 
 * @author Alex Petitjean.
 *         Created Apr 19, 2012.
 */
public class NutritionAppActivity extends Activity {
	/** The color sequence read from images. */
	public static byte[] colorSequence = null;

	/** Code for opening Camera Intent */
	public final static int TAKE_PICTURE = 0;
	/** Code for opening ResultsViewActivity */
	public final static int RESULTS = 1;
	private static Uri imageUri;
	private static int SCALED_HEIGHT = 368;
	private static int SCALED_WIDTH = 640;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ImageButton cam, set, info, search, exit;
		cam = (ImageButton) findViewById(R.id.camera_button);
		set = (ImageButton) findViewById(R.id.settings_button);
		info = (ImageButton) findViewById(R.id.info_button);
		search = (ImageButton) findViewById(R.id.search_button);
		exit = (ImageButton) findViewById(R.id.exit_button);

		cam.setOnClickListener(new CameraOnClickListener());
		set.setOnClickListener(new SettingsOnClickListener());
		info.setOnClickListener(new InfoOnClickListener());
		search.setOnClickListener(new SearchOnClickListener());
		exit.setOnClickListener(new ExitOnClickListener());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == RESULT_OK) {
				Log.i("Picture taken", imageUri.toString());
				analyzeImage();
			}
			else if (resultCode == RESULT_CANCELED) {
				Log.d("on result", "result canceled!");
			}
			else {
				Log.d("on result", "result not ok!");
			}
			break;
		case RESULTS:
			Log.d("on result", "ResultsViewActivity ended");
			break;
		}
	}

	private void analyzeImage() {
		Bitmap img = null;
		try {
			Log.d("Analyze", imageUri.getPath());
			img = BitmapFactory.decodeFile(imageUri.getPath());
			img = scaleImage(img);
			colorSequence = ProcessImage.generateSequence(img);
			sendNGramModel();
		} catch (Exception e) {
			Log.e("Analyze", e.toString());
		}
	}

	private Bitmap scaleImage(Bitmap b) {
		Bitmap newb; // lol
		if (b.getWidth() > SCALED_WIDTH && b.getHeight() > SCALED_HEIGHT) {
			newb = Bitmap.createScaledBitmap(b, SCALED_WIDTH, SCALED_HEIGHT, false);
		}
		else {
			newb = b;
		}
		return newb;
	}

	private void sendNGramModel() {
		Log.d("send color sequence", "");

		Intent resultsIntent = new Intent(this, ResultsViewActivity.class);
		startActivityForResult(resultsIntent, RESULTS);
	}

	private class CameraOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			Intent camera_intent = new Intent("android.media.action.IMAGE_CAPTURE");
			File filesdir = new File(Environment.getExternalStorageDirectory(),
					"nutrition-app");
			if (!filesdir.exists()) {
				Log.d("File", filesdir.toString() + " does not exist. Creating...");
				if (!filesdir.mkdirs()) {
					Log.d("File", filesdir.toString() + " could not be created!");
				}
			}

			File image_file = new File(filesdir, "image.png");
			if (!image_file.exists()) {
				Log.d("File", "Created image file does not exist. Creating...");
				try {
					if (!image_file.createNewFile()) {
						Log.d("File", "Creation failed!");
					}
				} catch (IOException e) {
					Log.e("File", e.getMessage());
				}
			}
			imageUri = Uri.fromFile(image_file);
			camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

			Log.d("Camera", "Starting camera intent");
			startActivityForResult(camera_intent, TAKE_PICTURE);
		}
	}
	
	private class SettingsOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			//TODO implement onClick
		}
	}
	
	private class InfoOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			setContentView(R.layout.info);
		}
	}
	
	private class SearchOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			//TODO implement onClick
		}
	}

	private class ExitOnClickListener implements View.OnClickListener {
		public void onClick(View v) {
			Log.d("Exit", "exit");
			NutritionAppActivity.this.finish();
		}
	}
}
