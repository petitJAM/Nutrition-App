package app.nutrition;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	/** The NGramModel created from images. */
	public static NGramModel ngm = null;

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

		ImageButton cam, exit;
		cam = (ImageButton) findViewById(R.id.camera_button);
		exit = (ImageButton) findViewById(R.id.exit_button);

		cam.setOnClickListener(new View.OnClickListener() {

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
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
					
			public void onClick(View v) {
				android.os.Process.killProcess(android.os.Process.myPid());
				
			}
		});

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

			List<Byte> pixel_seq = ProcessImage.generateSequence(img);
			ngm = new NGramModel("result", pixel_seq);
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
		Log.d("send n-gram model", ngm.toString());
	}
}
