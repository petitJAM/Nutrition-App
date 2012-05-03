package Database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class QueryClassTest {

	
	@Test
	public void testGetDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		d = qc.getDevice(d.id);
		assertTrue(d.times_correct == 0);
	}

	@Test
	public void testGetNonexistantDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.getDevice(-1);
		assertNull(d);
	}

	@Test
	public void testCreateDeviceNotNull() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		assertNotNull(d);
	}

	@Test
	public void testCreateDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		assertTrue(d.times_correct == 0);
		assertTrue(d.times_used == 0);
	}

	@Test
	public void testUpdateDevice() {
		QueryClass qc = new QueryClass();
		Device d = qc.newDevice();
		if(d==null){
			System.out.println("null 1");
		}
		d = qc.updateDevice(d.id, 1, 0);
		if(d==null){
			System.out.println("null 2");
		}
		assertTrue(d.times_used == 1);
		assertTrue(d.times_correct == 0);
	}

	@Test
	public void testUpdateNonexistantDevice(){
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(-1, 1, 0);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfUses(){
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, -1, 0);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfUsesAndSuccesses(){
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, -1, -2);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceInvalidNumberOfSuccesses(){
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, 0, -1);
		assertNull(d);
	}
	
	@Test
	public void testUpdateDeviceSuccessesGreaterThanUses(){
		QueryClass qc = new QueryClass();
		Device d = qc.updateDevice(0, 5, 7);
		assertNull(d);
	}
	
	@Test
	public void testUpdateCorrectDevice(){
		QueryClass qc = new QueryClass();
		Device a = qc.newDevice();
		Device b = qc.newDevice();

		Device newA = qc.updateDevice(a.id, 5, 2);
		Device getA = qc.getDevice(a.id);
		Device getB = qc.getDevice(b.id);
		
		assertTrue(newA.id==a.id);
		assertTrue(getA.id==a.id);
		assertTrue(getA.times_correct==2);
		assertTrue(getA.times_used==5);
		assertTrue(getB.times_correct==0);
		assertTrue(getB.times_used==0);
	}
	
	
	@Test
	public void testGetAllFood() {
		QueryClass qc = new QueryClass();
		ArrayList<Food> food = qc.getFood();
		assertNotNull(food);
		for (Food f : food) {
			assertNotNull(f.Transition_Matrix);
		}
	}

	@Test
	public void testFoodEqualsTrue() {
		Food f = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007, 1008);
		Food g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007, 1008);
		assertTrue(f.equals(g));
	}

	@Test
	public void testFoodEqualsFalse() {
		Food f = new Food(new byte[] { 0 }, "pizza", 1005, 1002, 1003, 1004,
				1005, 1006, 1007, 1008);
		Food g = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007, 1008);
		assertTrue(!f.equals(g));
	}

	@Test
	public void testAddFoodItem() {
		QueryClass qc = new QueryClass();
		Food f = new Food(new byte[] { 0 }, "pizza", 1001, 1002, 1003, 1004,
				1005, 1006, 1007, 1008);
		qc.addFoodItem(f);
		ArrayList<Food> food = qc.getFood();
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