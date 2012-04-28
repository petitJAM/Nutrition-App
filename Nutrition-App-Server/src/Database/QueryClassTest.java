package Database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class QueryClassTest {

	@Test
	public void testCreateDatabaseConnection() {
		QueryClass qc = new QueryClass();
		assertNotNull(qc);
	}

	@Test
	public void testGetDevice() {
		Device d = QueryClass.getDevice(0);
		assertTrue(d.times_correct==0);
	}

	@Test
	public void testGetNonexistantDevice() {
		Device d = QueryClass.getDevice(-1);
		assertNull(d);
	}

	@Test
	public void testCreateDevice() {
		Device d = QueryClass.newDevice();
		assertNotNull(d);
	}
	
	@Test
	public void testUpdateDevice(){
		Device d = QueryClass.updateDevice(0,1,0);
		assertTrue(d.times_used==1);
	}

	@Test
	public void testGetAllFood() {
		ArrayList<Food> food = QueryClass.getFood();
		assertNotNull(food);
	}
	
	@Test
	public void testFoodEqualsTrue(){
		Food f = new Food(new byte[]{0},"pizza",1001,1002,1003,1004,1005,1006,1007);
		Food g = new Food(new byte[]{0},"pizza",1001,1002,1003,1004,1005,1006,1007);
		assertTrue(f.equals(g));
	}
	
	@Test
	public void testFoodEqualsFalse(){
		Food f = new Food(new byte[]{0},"pizza",1005,1002,1003,1004,1005,1006,1007);
		Food g = new Food(new byte[]{0},"pizza",1001,1002,1003,1004,1005,1006,1007);
		assertTrue(f.equals(g));
	}
	
	@Test
	public void testAddFoodItem() {
		Food f = new Food(new byte[]{0},"pizza",1001,1002,1003,1004,1005,1006,1007);
		QueryClass.addFoodItem(f);
		ArrayList<Food> food = QueryClass.getFood();
		boolean temp = false;
		for(Food fo : food){
			if(fo.equals(f)){
				temp = true;
				break;
			}
			assertTrue(temp);
		}
	}
}