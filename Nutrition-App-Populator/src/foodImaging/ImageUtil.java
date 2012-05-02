package foodImaging;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Rob Wagner, Alex Petitjean
 */
public class ImageUtil {
    
    /**
     * Read an image from the given filepath and filename.
     * 
     * @param filepath - path of the directory containing the file
     * @param filename - the actual filename
     * @return BufferedImage from the specificed file
     */
    public static BufferedImage readImage(String filepath, String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filepath + filename));
            
        } catch (IOException e) {
            IOException("readImage  " + filepath + " " + filename);
            return null;
        }
        return img;
    }
    
    /**
     * Normalize an image to width 500 and write out the new image to a file.
     * 
     * @param filename
     * @param filepath
     * @param outpath
     */
    public static void normalizeImage(String filepath, String filename) {
        BufferedImage img = readImage(filepath, filename);
        
        BufferedImage scaledImg = toBufferedImage(img.getScaledInstance(500,
            -1, 0));
        
        try {
            File outputfile = new File(filepath + "Normalized\\" + filename);
            ImageIO.write(scaledImg, "jpg", outputfile);
        } catch (IOException e) {
            IOException("normalizeImage  " + filepath + " " + filename);
        }
    }
    
    /**
     * This method returns a buffered image with the contents of an image
     * 
     * @param image
     * @return input Image "casted" to BufferedImage
     */
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) { return (BufferedImage) image; }
        
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        
        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image
                    .getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image
                    .getHeight(null), type);
        }
        
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return bimage;
    }
    
    private static void IOException(String info) {
        System.out.println("Image IO Exception");
        System.out.println(info);
    }
}
