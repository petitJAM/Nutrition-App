package foodImaging;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import network.Connection;

import Database.Food;
import Database.QueryClass;
import NGramModel.NGramModel;
import NGramModel.TransitionMatrix;

/**
 * This class automatically populates our database by cycling through the new
 * images we wish to add. The only drawback to this method is that the images
 * must be properly named and match a row in the database. *
 * 
 * @author Rob Wagner Last Updated 5/2/2012
 */

public class Populator2000 {

	private static String newImgDir = "imgs-to-add/";

	// private static String completeImgDir = "old-imgs";

	/**
	 * -Sends images to trainer, and gets all their NGramModels -Cycles through
	 * each model -- Grabs byte array -- Creates NutritionFacts object that
	 * collects the information for the food -- Send the information to the
	 * query class where it will be added to our database.
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		NGramModel[] ngms = trainAll(newImgDir);
		QueryClass q = new QueryClass();
		for (int i = 0; i < ngms.length; i++) {
			byte[] b = ngms[i].getByteArray();
			NutritionFacts n = new NutritionFacts(ngms[i].name);
			q.addFoodItem(new Food(b, ngms[i].name, ngms[i].nameSpanish,
					ngms[i].nameFrench, ngms[i].nameGerman, n.calories,
					n.calFromFat, n.totalFat, n.sodium, n.carbs, n.fiber,
					n.sugar, n.protein));
		}
		System.out.println("Upload Complete");

	}

	/**
	 * Trains each image in in the image directory.
	 * 
	 * @param imgDir
	 * @return array of NGramModels
	 */
	public static NGramModel[] trainAll(String imgDir) {
		String[] training = getFileNames(imgDir);
		NGramModel[] ngms = new NGramModel[training.length];

		for (int i = 0; i < training.length; i++) {
			BufferedImage img = ImageUtil.readImage(newImgDir, training[i]);
			ArrayList<Byte> seq = ProcessImage.generateSequence(img);
			String name = training[i]
					.substring(0, training[i].lastIndexOf('.'));
			NGramModel ngm = new NGramModel(name, name, name, name,
					new TransitionMatrix(ProcessImage.NUM_COLORS));
			ngm.train(seq);
			ngms[i] = ngm;
		}
		return ngms;
	}

	/**
	 * Loop over the given directory and return a String array of all the file
	 * names in it.
	 * 
	 * @param filepath
	 *            - file path to get file names from
	 * @return String[] of file names.
	 */
	public static String[] getFileNames(String filepath) {
		ArrayList<String> sal = new ArrayList<String>();

		File dir = new File(filepath);
		File[] files = dir.listFiles();

		for (File f : files)
			if (!f.isDirectory())
				sal.add(f.getName());

		String[] s = new String[sal.size()];
		for (int i = 0; i < sal.size(); i++)
			s[i] = sal.get(i);

		return s;
	}

}
