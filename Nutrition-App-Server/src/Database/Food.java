package Database;

import java.io.IOException;

import network.Connection;
import NGramModel.NGramModel;

/**
 * a class to hold both the transition matrix, and nutrition facts for a given
 * food item
 * 
 * @author bellrj
 * 
 */
public class Food {

	/**
	 * the matrix describing the image of this food item
	 */
	public NGramModel ngm;
	/**
	 * the name of this food item
	 */
	public String name;
	/**
	 * the total number of calories contained in one serving of this food item
	 */
	public float calories;
	/**
	 * the number of calories from fat contained in one serving of this food
	 * item
	 */
	public float calFromFat;
	/**
	 * the total amount of fat (//TODO in grams?) contained in one serving of
	 * this food item
	 */
	public float totalFat;
	/**
	 * the total amount of sodium (in mg) in one serving of this food item
	 */
	public float sodium;
	/**
	 * the number of carbohydrates in one serving of this food item
	 */
	public float carbs;
	/**
	 * the amount of fiber (//TODO in grams?) contained in one serving of this
	 * food item
	 */
	public float fiber;
	/**
	 * the amount of sugar (in grams) contained in one serving of this food item
	 */
	public float sugar;
	/**
	 * the amount of protein (//TODO in grams?) contained in one serving of this
	 * food item
	 */
	public float protein;

	/**
	 * constructor for a food object
	 * @param bs 
	 *            NGramModel for this food object
	 * @param string
	 *            the name of this food object
	 * @param calories
	 *            the number of calories in one serving of this food
	 * @param calFromFat
	 *            the number of calories from fat in one serving of this food
	 * @param totalFat
	 *            the total amount of fat in one serving of this food item
	 * @param sodium
	 *            the total amount of sodium in one serving of this food item
	 * @param carbs
	 *            the total amount of carbohydrates in one serving of this food
	 *            item
	 * @param fiber
	 *            the amount of fiber in one serving of this food
	 * @param sugar
	 *            the amount of sugar in one serving of this food
	 * @param protein
	 *            the amount of protein in one serving of this food
	 * @throws IOException 
	 */
	public Food(byte[] bs, String string, float calories, float calFromFat,
			float totalFat, float sodium, float carbs, float fiber, float sugar,
			float protein) {
		try {
			this.ngm = (NGramModel) Connection.deSerialize(bs);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		this.name = string;
		this.calories = calories;
		this.calFromFat = calFromFat;
		this.totalFat = totalFat;
		this.sodium = sodium;
		this.carbs = carbs;
		this.fiber = fiber;
		this.sugar = sugar;
		this.protein = protein;
	}

	/**
	 * compares this to another food object, and returns true if all fields are
	 * the same, false otherwise
	 * 
	 * @param f
	 *            the food to compare this to
	 * @return true if they are the same, false otherwise
	 */
	public boolean equals(Food f) {
		return f.ngm.equals(this.ngm) && f.name.equals(this.name)
				&& f.calories == this.calories && f.calFromFat == this.calFromFat
				&& this.totalFat == f.totalFat && f.sodium == this.sodium
				&& f.carbs == this.carbs && f.fiber == this.fiber
				&& f.sugar == this.sugar && f.protein == this.protein;
	}

//	private boolean compareMatrix(byte[] a, byte[] b) {
//		if (a.length == b.length) {
//			for (int i = 0; i < a.length; i++)
//				if (a[i] != b[i]) return false;
//			return true;
//		}
//		return false;
//	}
}
