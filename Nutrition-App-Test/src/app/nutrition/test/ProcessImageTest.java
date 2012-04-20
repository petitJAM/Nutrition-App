package app.nutrition.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import app.nutrition.ProcessImage;

/**
 * Test the ProcessImage class
 *
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class ProcessImageTest {

	/**
	 * Test on a 2x2 bitmap of white pixels.
	 *
	 */
	@Test
	public void testThatProcessImageGeneratesACorrectSequence() {
		Bitmap b;
		b = BitmapFactory.decodeFile("test_images/white.bmp");
		
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(2);expected.add(2);expected.add(2);expected.add(2);
		
		assertEquals(expected, ProcessImage.generateSequence(b));
	}
	
	/**
	 * Test on a 2x2 bitmap of black pixels.
	 *
	 */
	public void testThatProcessImageGeneratesACorrectSequence2() {
		Bitmap b;
		b = BitmapFactory.decodeFile("test_images/black.bmp");
		
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(0);expected.add(0);expected.add(0);expected.add(0);
		
		assertEquals(expected, ProcessImage.generateSequence(b));
	}
}
