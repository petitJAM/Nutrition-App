package app.nutrition;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Process an image
 * 
 * @author Rob Wagner, Alex Petitjean
 */
public class ProcessImage {
	
    public static final int BAD          = -1;
    public static final int BLACK        = 0;
    public static final int GRAY         = 1;
    public static final int WHITE        = 2;
    public static final int RED          = 3;
    public static final int REDORANGE    = 4;
    public static final int ORANGE       = 5;
    public static final int YELLOWORANGE = 6;
    public static final int YELLOW       = 7;
    public static final int YELLOWGREEN  = 8;
    public static final int GREEN        = 9;
    public static final int BLUEGREEN    = 10;
    public static final int BLUE         = 11;
    public static final int BLUEVIOLET   = 12;
    public static final int VIOLET       = 13;
    public static final int VIOLETRED    = 14;
    
    public static final int NUM_COLORS   = 15;
    
    /**
     * Return something
     *
     * @param img
     * @return 
     */
    public static List<Integer> generateSequence(Bitmap img) {
    	ArrayList<Integer> ret = new ArrayList<Integer>();
    	
    	int width = img.getWidth();
    	int height = img.getHeight();
    	
    	int[] pixels = new int[width * height];
    	img.getPixels(pixels, 0, width, 0, 0, width, height);
    	
    	for (int i = 0; i < pixels.length; i++) {
    		ret.add(classify(getPixelRGB(pixels[i])));
    	}
    	
    	return ret;
    }
    
    
    
    private static int[] getPixelRGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        int[] rgb = { red, green, blue };
        return rgb;
    }
    
    private static int classify(int[] rgb) {
        float[] hsv = new float[3];
        
        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];
        
        Color.RGBToHSV(red, green, blue, hsv);
        
        float hue = hsv[0];
        float sat = hsv[1];
        float bright = hsv[2];
        
        if (sat < .25) {
            if (bright < .2)
                return BLACK;
            else if (bright > .8)
                return WHITE;
            else
                return GRAY;
        }
        else {
            if (hue < 15) return RED;
            if (hue < 45) return REDORANGE;
            if (hue < 75) return ORANGE;
            if (hue < 105) return YELLOWORANGE;
            if (hue < 135) return YELLOW;
            if (hue < 165) return YELLOWGREEN;
            if (hue < 195) return GREEN;
            if (hue < 225) return BLUEGREEN;
            if (hue < 255) return BLUE;
            if (hue < 285) return BLUEVIOLET;
            if (hue < 315) return VIOLET;
            if (hue < 345) return VIOLETRED;
            if (hue < 360) return RED;
            return BAD;
        }
    }
}



