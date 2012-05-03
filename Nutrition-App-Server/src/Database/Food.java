package Database;

public class Food {

	public byte[] Transition_Matrix;
	public String name;
	public float calories;
	public float calFromFat;
	public float totalFat;
	public float sodium;
	public float carbs;
	public float fiber;
	public float sugar;
	public float protein;

	public Food(byte[] bs, String string, float calories, float calFromFat,
			float totalFat, float sodium, float carbs, float fiber,
			float sugar, float protein) {
		this.Transition_Matrix = bs;
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

	public boolean equals(Food f) {
		return compareMatrix(f.Transition_Matrix, this.Transition_Matrix)
				&& f.name.equals(this.name) && f.calories == this.calories
				&& f.calFromFat == this.calFromFat
				&& this.totalFat == f.totalFat && f.sodium == this.sodium
				&& f.carbs == this.carbs && f.fiber == this.fiber
				&& f.sugar == this.sugar && f.protein == this.protein;
	}

	private boolean compareMatrix(byte[] a, byte[] b) {
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++)
				if (a[i] != b[i])
					return false;
			return true;
		}
		return false;
	}

}
