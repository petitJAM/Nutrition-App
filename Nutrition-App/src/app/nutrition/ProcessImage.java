package app.nutrition;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

/**
 * Process an image
 * 
 * @author Rob Wagner, Alex Petitjean
 */
public class ProcessImage {

	/**
	 * Invalid color.
	 */
	public static final byte BAD = -1;
	/**
	 * Black
	 */
	public static final byte BLACK = 0;
	/**
	 * Gray
	 */
	public static final byte GRAY = 1;
	/**
	 * White
	 */
	public static final byte WHITE = 2;
	/**
	 * Red
	 */
	public static final byte RED = 3;
	/**
	 * Red-orange
	 */
	public static final byte REDORANGE = 4;
	/**
	 * Orange
	 */
	public static final byte ORANGE = 5;
	/**
	 * Yellow-orange
	 */
	public static final byte YELLOWORANGE = 6;
	/**
	 * Yellow
	 */
	public static final byte YELLOW = 7;
	/**
	 * Yellow-green
	 */
	public static final byte YELLOWGREEN = 8;
	/**
	 * Green
	 */
	public static final byte GREEN = 9;
	/**
	 * Blue-green
	 */
	public static final byte BLUEGREEN = 10;
	/**
	 * Blue
	 */
	public static final byte BLUE = 11;
	/**
	 * Blue-violet
	 */
	public static final byte BLUEVIOLET = 12;
	/**
	 * Violet
	 */
	public static final byte VIOLET = 13;
	/**
	 * Violet-red
	 */
	public static final byte VIOLETRED = 14;

	/**
	 * Total number of colors.
	 */
	public static final byte NUM_COLORS = 15;

	/**
	 * Return a sequence of colors found from the given Bitmap image.
	 * 
	 * @param img - image to sequence
	 * @return sequence of colors
	 */
	public static List<Byte> generateSequence(Bitmap img) {
		ArrayList<Byte> ret = new ArrayList<Byte>();

		int width = img.getWidth();
		int height = img.getHeight();
		
		Log.d("ProcessImage", "image width: " + width + "   image height: " + height);

		int[] pixels = new int[width * height];
		img.getPixels(pixels, 0, width, 0, 0, width, height);
		Log.d("ProcessImage", "Pixel array filled");

		for (int i = 0; i < pixels.length; i++) {
			ret.add(classify(getPixelRGB(pixels[i])));
		}
		
		Log.d("ProcessImage", "Processing complete");
		return ret;
	}

	private static byte[] getPixelRGB(int pixel) {
		byte red = (byte) ((pixel >> 16) & 0xff);
		byte green = (byte) ((pixel >> 8) & 0xff);
		byte blue = (byte) ((pixel) & 0xff);
		byte[] rgb = { red, green, blue };
		return rgb;
	}

	private static byte classify(byte[] rgb) {
		float[] hsv = new float[3];
		
		Color.RGBToHSV(rgb[0], rgb[1], rgb[2], hsv);

		if (hsv[1] < .25) {
			if (hsv[2] < .2)
				return BLACK;
			else if (hsv[2] > .8)
				return WHITE;
			else
				return GRAY;
		}
		else {
			if (hsv[0] < 15) return RED;
			if (hsv[0] < 45) return REDORANGE;
			if (hsv[0] < 75) return ORANGE;
			if (hsv[0] < 105) return YELLOWORANGE;
			if (hsv[0] < 135) return YELLOW;
			if (hsv[0] < 165) return YELLOWGREEN;
			if (hsv[0] < 195) return GREEN;
			if (hsv[0] < 225) return BLUEGREEN;
			if (hsv[0] < 255) return BLUE;
			if (hsv[0] < 285) return BLUEVIOLET;
			if (hsv[0] < 315) return VIOLET;
			if (hsv[0] < 345) return VIOLETRED;
			if (hsv[0] < 360) return RED;
			return BAD;
		}
	}
}
