package app.nutrition;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Starting point and main screen of the app
 * 
 * @author Alex Petitjean.
 *         Created Apr 19, 2012.
 */
public class NutritionAppActivity extends Activity {
	/** Called when the activity is first created. */

	private final static int TAKE_PICTURE = 0;
	private static Uri imageUri;

	@Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button cam, about;
        cam = (Button) findViewById(R.id.camera_button);
        about = (Button) findViewById(R.id.about_us);
        
        cam.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent camera_intent = new Intent("android.media.action.IMAGE_CAPTURE");
				File image_file = new File(getFilesDir(), "image.png");
				imageUri = Uri.fromFile(image_file);

				camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

				startActivityForResult(camera_intent, TAKE_PICTURE);
			}
		});
        
        // Needs Implementing Still.
        about.setOnClickListener(new View.OnClickListener() {
        	
        	public void onClick(View v) {
        		
        	}
        });
        
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case TAKE_PICTURE:
			Log.d("Picture taken", imageUri.toString());
			analyzeImage();
			break;
		}
	}

	private void analyzeImage() {
		ContentResolver cr = getContentResolver();
		Bitmap img;
		try {
			img = android.provider.MediaStore.Images.Media.getBitmap(cr, imageUri);
			List<Integer> pixel_seq = ProcessImage.generateSequence(img);
			NGramModel ngm = new NGramModel("result", pixel_seq);
			sendNGramModel(ngm);

		} catch (Exception e) {
			Log.e("Analyze", e.getMessage());
		}
	}

	private void sendNGramModel(NGramModel ngm) {
		Log.d("send n-gram model", ngm.toString());
	}
}
