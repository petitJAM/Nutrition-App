package foodImaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Database.Food;
import Database.QueryClass;


/**
 * @author Rob Wagner!
 */

public class Populator2000 {

    private static String filepath = "img/";
    
    /**
     * The magic happens here!
     * 
     * @throws IOException 
     */
    
    public static void main(String[] args) throws IOException {
        NGramModel[] ngms = trainAll();
        for (int i = 0; i < ngms.length; i++) {
        	byte[] b = ngms[i].getByteArray();
        	NutritionFacts n = new NutritionFacts(ngms[i].name);
			QueryClass.addFoodItem(new Food(b, ngms[i].name, n.calories, n.calFromFat, n.totalFat, n.sodium, n.carbs, n.fiber, n.sugar, n.protein));
		}
    }
      
    public static NGramModel[] trainAll() {
        String[] training = getFileNames(filepath);
        NGramModel[] ngms = new NGramModel[training.length];
        
        for (int i = 0; i < training.length; i++) {
            BufferedImage img = ImageUtil.readImage(filepath, training[i]);
            ArrayList<Integer> seq = ProcessImage.generateSequence(img);
            NGramModel ngm = new NGramModel(training[i].substring(0, training[i].lastIndexOf('.')), new TransitionMatrix(ProcessImage.NUM_COLORS));
            ngm.train(seq);
            ngms[i] = ngm;
        }     
        return ngms;
    }
    
    /**
     * Loop over the given directory and return a String array of all the file
     * names in it.
     * 
     * @param filepath - file path to get file names from
     * @return String[] of file names.
     */
    public static String[] getFileNames(String filepath) {
        ArrayList<String> sal = new ArrayList<String>();
        
        File dir = new File(filepath);
        File[] files = dir.listFiles();
        
        for (File f : files)
            if (!f.isDirectory()) sal.add(f.getName());
        
        String[] s = new String[sal.size()];
        for (int i = 0; i < sal.size(); i++)
            s[i] = sal.get(i);
        
        return s;
    }

}
