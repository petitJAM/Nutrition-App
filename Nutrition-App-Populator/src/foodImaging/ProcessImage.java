package foodImaging;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
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
    
    public static ArrayList<Integer> generateSequence(BufferedImage img) {
        ArrayList<Integer> seq = new ArrayList<Integer>();
        
        int[][] pixelArray = getPixelArray(img);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int color = classify(getPixelRGB(pixelArray[x][y]));
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
    
    public static int[] getPixelRGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        int[] rgb = { red, green, blue };
        return rgb;
    }
    
    public static int classify(int[] rgb) {
        float[] hsbvals = new float[3];
        
        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];
        
        Color.RGBtoHSB(red, green, blue, hsbvals);
        
        float hue = hsbvals[0] * 360;
        float sat = hsbvals[1];
        float bright = hsbvals[2];
        
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
