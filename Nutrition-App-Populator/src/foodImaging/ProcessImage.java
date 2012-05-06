package foodImaging;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Rob Wagner, Alex Petitjean
 */
public class ProcessImage {
    
    public static final byte BAD          = -1;
    public static final byte BLACK        = 0;
    public static final byte GRAY         = 1;
    public static final byte WHITE        = 2;
    public static final byte RED          = 3;
    public static final byte REDORANGE    = 4;
    public static final byte ORANGE       = 5;
    public static final byte YELLOWORANGE = 6;
    public static final byte YELLOW       = 7;
    public static final byte YELLOWGREEN  = 8;
    public static final byte GREEN        = 9;
    public static final byte BLUEGREEN    = 10;
    public static final byte BLUE         = 11;
    public static final byte BLUEVIOLET   = 12;
    public static final byte VIOLET       = 13;
    public static final byte VIOLETRED    = 14;
    
    public static final byte NUM_COLORS   = 15;
    
    public static ArrayList<Byte> generateSequence(BufferedImage img) {
        ArrayList<Byte> seq = new ArrayList<Byte>();
        
        int[][] pixelArray = getPixelArray(img);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                byte color = classify(getPixelRGB(pixelArray[x][y]));
                seq.add(color);
            }
        }
        return seq;
    }
    
    public static int[][] getColorArray(BufferedImage img) {
        int[][] pixelArray = getPixelArray(img);
        
        for (int i = 0; i < pixelArray.length; i++)
            for (int j = 0; j < pixelArray[i].length; j++)
                pixelArray[i][j] = classify(getPixelRGB(pixelArray[i][j]));
        
        for (int[] row : pixelArray)
            for (int c : row)
                c = classify(getPixelRGB(c));
        
        return pixelArray;
    }
    
    public static int[][] getPixelArray(BufferedImage img) {
        int[][] pixelArray = new int[img.getWidth()][img.getHeight()];
        
        for (int x = 0; x < img.getWidth(); x++)
            for (int y = 0; y < img.getHeight(); y++)
                pixelArray[x][y] = img.getRGB(x, y);
        
        return pixelArray;
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
		
		Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);

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
