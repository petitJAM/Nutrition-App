package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import Database.Device;
import Database.Food;
import Database.QueryClass;

/**
 * tests the functionality of the Database.QueryClass
 * 
 * @author bellrj
 * 
 */
public class QueryClassTest {

	/**
	 * inserts a device, and tries to get it back from the database
	 */
	@Test
	public void testGetDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		d = qc.getDevice(d.id);
		assertTrue(d.times_correct == 0);
	}

	/**
	 * tries to get a device that does not exist
	 */
	@Test
	public void testGetNonexistantDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.getDevice(-1);
		assertNull(d);
	}

	/**
	 * tries to create a new device, and ensures it is not null after the
	 * creation
	 */
	@Test
	public void testCreateDeviceNotNull() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		assertNotNull(d);
	}

	/**
	 * creates a new device, and checks that the initial times_correct
	 */
	@Test
	public void testCreateDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		assertTrue(d.times_correct == 0);
		assertTrue(d.times_used == 0);
	}

	/**
	 * tries to insert a new device, modifies it, then checks that it has the
	 * correct changed parameters
	 */
	@Test
	public void testUpdateDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		d = qc.updateDevice(d.id, 1, 0);
		assertTrue(d.times_used == 1);
		assertTrue(d.times_correct == 0);
	}

	/**
	 * Tries to update a device which does not exist in the database
	 */
	@Test
	public void testUpdateNonexistantDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(-1, 1, 0);
		assertNull(d);
	}

	/**
	 * tries to update a device to have a negative number of uses
	 */
	@Test
	public void testUpdateDeviceInvalidNumberOfUses() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		d = qc.updateDevice(d.id, -1, 0);
		assertNull(d);
	}

	/**
	 * tries to update a device to have a negative number of uses and successes
	 */
	@Test
	public void testUpdateDeviceInvalidNumberOfUsesAndSuccesses() {
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, -1, -2);
		assertNull(d);
	}

	/**
	 * tries to update a device to have a negative number of successes
	 */
	@Test
	public void testUpdateDeviceInvalidNumberOfSuccesses() {
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, 0, -1);
		assertNull(d);
	}

	/**
	 * tries to update a device to have a number of successes less than the
	 * number of uses
	 */
	@Test
	public void testUpdateDeviceSuccessesGreaterThanUses() {
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, 5, 7);
		assertNull(d);
	}

	/**
	 * adds two devices, modifies one of them, and checks to ensure that only to
	 * correct device was modified
	 */
	@Test
	public void testUpdateCorrectDevice() {
		QueryClass qc = new QueryClass();
		Device a = qc.newDevice();
		Device b = qc.newDevice();

		Device newA = qc.updateDevice(a.id, 5, 2);
		Device getA = qc.getDevice(a.id);
		Device getB = qc.getDevice(b.id);

		assertTrue(newA.id == a.id);
		assertTrue(getA.id == a.id);
		assertTrue(getA.times_correct == 2);
		assertTrue(getA.times_used == 5);
		assertTrue(getB.times_correct == 0);
		assertTrue(getB.times_used == 0);
	}

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
		f = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004, 1005, 1006,
				1007, 1008);
		g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004, 1005, 1006,
				1007, 1008);
		assertTrue(f.equals(g));
	}

	/**
	 * Checks to see that two foods that are slightly different are not equal
	 */
	@Test
	public void testFoodEqualsFalse() {
		Food f, g;
		f = new Food(new byte[] { 0 }, "pizza", 1005, 1002, 1003, 1004, 1005, 1006,
				1007, 1008);
		g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004, 1005, 1006,
				1007, 1008);
		assertTrue(!f.equals(g));
	}

	/**
	 * Tries to add a new food item, then gets all of the foods out of the
	 * database and checks that the one it just added is there
	 */
	@Test
	public void testAddAndDeleteFoodItem() {
		QueryClass qc = new QueryClass();
		Food f;
		try {
			f = new Food(new byte[] { 0 }, "dummy", 1001, 1002, 1003, 1004, 1005, 1006,
					1007, 1008);
			qc.addFoodItem(f);
			f = qc.getFoodItem("dummy");
			assertNotNull(f);
			qc.deleteFoodItem("dummy");
			f = qc.getFoodItem("dummy");
			assertNull(f);
		} catch (IOException exception) {
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
