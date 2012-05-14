package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Database.Food;
import Database.QueryClass;
import NGramModel.NGramModel;
import NGramModel.TransitionMatrix;

/**
 * tests the functionality of the Database.QueryClass
 * 
 * @author bellrj
 * 
 */
public class QueryClassTest {

	/**
	 * Checks to make sure that all of the foods have a non-null transition
	 * matrix
	 */
	@Test
	public void testGetAllFood() {
		QueryClass qc = new QueryClass();
		ArrayList<Food> food;
		try {
			food = qc.getFood();
			assertNotNull(food);
			for (Food f : food) {
				assertNotNull(f.ngm);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Checks to see if two foods with all the same parameters are equal
	 */
	@Test
	public void testFoodEqualsTrue() {
		Food f, g;
		NGramModel ngm = new NGramModel("dummy", "dummy", "dummy", "dummy",
				new TransitionMatrix(5));
		try {
			f = new Food(ngm.getByteArray(), "dummy", "dummy", "dummy",
					"dummy", 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008);
			g = new Food(ngm.getByteArray(), "dummy", "dummy", "dummy",
					"dummy", 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008);
			assertTrue(f.equals(g));
		} catch (IOException exception) {
			fail();
		}
	}

	/**
	 * Checks to see that two foods that are slightly different are not equal
	 */
	@Test
	public void testFoodEqualsFalse() {
		Food f, g;
		NGramModel ngm = new NGramModel("dummy", "dummy", "dummy", "dummy",
				new TransitionMatrix(5));
		NGramModel ngm2 = new NGramModel("sauce", "dummy", "dummy", "dummy",
				new TransitionMatrix(5));
		try {
			f = new Food(ngm.getByteArray(), "dummy", "dummy", "dummy",
					"dummy", 1005, 1002, 1003, 1004, 1005, 1006, 1007, 1008);
			g = new Food(ngm2.getByteArray(), "dummy", "dummy", "dummy",
					"dummy", 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008);
			assertTrue(!f.equals(g));
		} catch (IOException exception) {
			fail();
		}
	}

	/**
	 * Tries to add a new food item, then gets all of the foods out of the
	 * database and checks that the one it just added is there
	 */
	@Test
	public void testAddAndDeleteFoodItem() {
		QueryClass qc = new QueryClass();
		Food f;
		NGramModel ngm = new NGramModel("dummy", "dummy", "dummy", "dummy",
				new TransitionMatrix(20));
		try {
			f = new Food(ngm.getByteArray(), "dummy", "dummy", "dummy",
					"dummy", 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008);
			qc.addFoodItem(f);
			f = qc.getFoodItem("dummy");
			assertNotNull(f);
			qc.deleteFoodItem("dummy");
			f = qc.getFoodItem("dummy");
			assertNull(f);
		} catch (IOException exception) {
			exception.printStackTrace();
			fail();
		}
	}

	/**
	 * Checks to see that we get all the correct information for grabbing a
	 * single item.
	 */
	@Test
	public void testGetFoodItem() {
		QueryClass qc = new QueryClass();
		Food f = null;
		try {
			f = qc.getFoodItem("Banana");
		} catch (IOException exception) {
			exception.printStackTrace();
			fail();
		}
		assertEquals(f.name, "Banana");
		assertTrue(f.calories == 89);
		assertTrue(f.totalFat == (float) 0.33);
		assertTrue(f.calFromFat == 3);
		assertTrue(f.protein == (float) 1.09);
		assertTrue(f.sugar == (float) 12.23);
		assertTrue(f.fiber == (float) 2.60);
		assertTrue(f.carbs == (float) 22.84);
		assertTrue(f.sodium == 1);
	}
}
