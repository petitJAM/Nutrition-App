package Database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class QueryClassTest {

	@Test
	public void testGetDevice() {
		Device d = QueryClass.getDevice(0);
		assertTrue(d.times_correct == 0);
	}

	@Test
	public void testGetNonexistantDevice() {
		Device d = QueryClass.getDevice(-1);
		assertNull(d);
	}

	@Test
	public void testCreateDeviceNotNull() {
		Device d = QueryClass.newDevice();
		assertNotNull(d);
	}

	@Test
	public void testCreateDevice() {
		Device d = QueryClass.newDevice();
		assertTrue(d.times_correct == 0);
		assertTrue(d.times_used == 0);
	}

	@Test
	public void testUpdateDevice() {
		Device d = QueryClass.updateDevice(0, 1, 0);
		assertTrue(d.times_used == 1);
		assertTrue(d.times_correct == 0);
	}

	@Test
	public void testUpdateNonexistantDevice(){
		Device d = QueryClass.updateDevice(-1, 1, 0);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfUses(){
		Device d = QueryClass.updateDevice(0, -1, 0);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfUsesAndSuccesses(){
		Device d = QueryClass.updateDevice(0, -1, -2);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfSuccesses(){
		Device d = QueryClass.updateDevice(0, 0, -1);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceSuccessesGreaterThanUses(){
		Device d = QueryClass.updateDevice(0, 5, 7);
		assertNull(d);
	}
	
	@Test
	public void testUpdateCorrectDevice(){
		Device a = QueryClass.newDevice();
		Device b = QueryClass.newDevice();

		Device newA = QueryClass.updateDevice(a.id, 5, 2);
		Device getA = QueryClass.getDevice(a.id);
		Device getB = QueryClass.getDevice(b.id);
		
		assertTrue(newA.id==a.id);
		assertTrue(getA.id==a.id);
		assertTrue(getA.times_correct==2);
		assertTrue(getA.times_used==5);
		assertTrue(getB.times_correct==0);
		assertTrue(getB.times_used==0);
	}
	
	
	@Test
	public void testGetAllFood() {
		ArrayList<Food> food = QueryClass.getFood();
		assertNotNull(food);
		for (Food f : food) {
			assertNotNull(f.Transition_Matrix);
		}
	}

	@Test
	public void testFoodEqualsTrue() {
		Food f = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007);
		Food g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007);
		assertTrue(f.equals(g));
	}

	@Test
	public void testFoodEqualsFalse() {
		Food f = new Food(new byte[] { 0 }, "pizza", 1005, 1002, 1003, 1004,
				1005, 1006, 1007);
		Food g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007);
		assertTrue(f.equals(g));
	}

	@Test
	public void testAddFoodItem() {
		Food f = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007);
		QueryClass.addFoodItem(f);
		ArrayList<Food> food = QueryClass.getFood();
		boolean temp = false;
		for (Food fo : food) {
			if (fo.equals(f)) {
				temp = true;
				break;
			}
			assertTrue(temp);
		}
	}
}