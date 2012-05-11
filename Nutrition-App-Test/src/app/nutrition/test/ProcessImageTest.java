package app.nutrition.test;

import junit.framework.TestCase;
import android.graphics.Bitmap;
import app.nutrition.ProcessImage;

/**
 * Test the ProcessImage class
 * 
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class ProcessImageTest extends TestCase {

	/**
	 * Test on a 2x2 bitmap of white pixels.
	 * 
	 */
	public void testThatProcessImageGeneratesACorrectSequence() {
		Bitmap b = null;

		int[] pixels = { 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF };
		b = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.RGB_565);
		
		byte[] expected = new byte[4];
		expected[0] = 2; expected[1] = 2; expected[2] = 2; expected[3] = 2;

		byte[] results = ProcessImage.generateSequence(b);
		
		for (int i = 0; i < 4; i++)
			assertEquals(expected[i], results[i]);
	}

	/**
	 * Test on a 2x2 bitmap of black pixels.
	 * 
	 */
	public void testThatProcessImageGeneratesACorrectSequence2() {
		Bitmap b;
		int[] pixels = { 0x000000, 0x000000, 0x000000, 0x000000 };
		b = Bitmap.createBitmap(pixels, 2, 2, Bitmap.Config.RGB_565);

		byte[] expected = new byte[4];
		expected[0] = 0;
		expected[1] = 0;
		expected[2] = 0;
		expected[3] = 0;

		byte[] result = ProcessImage.generateSequence(b);

		for (int i = 0; i < pixels.length; i++)
			assertEquals(expected[i], result[i]);
	}
}
