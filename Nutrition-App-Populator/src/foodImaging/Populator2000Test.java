package foodImaging;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import app.nutrition.NGramModel;

public class Populator2000Test {

	private static String testImgDir = "test-imgs/";
	private static NGramModel[] ngrms;
	
	@BeforeClass
	public static void setUp() {
		ngrms = Populator2000.trainAll(testImgDir);
	}
	
	@Test
	public void testThatNamesAreGrabbed() {
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < ngrms.length; i++) {
			names.add(ngrms[i].name);
		}
		
		assertEquals(names.get(0), "Apple (with skin)");
		assertEquals(names.get(1), "Avocado");
		assertEquals(names.get(2), "Banana");
		assertEquals(names.get(3), "Egg (raw)");
		assertEquals(names.get(4), "Orange (with peel)");
	}
	
	@Test
	public void testThatByteArraysAreCreated() throws IOException {
		for (int i = 0; i < ngrms.length; i++) {
			assertNotNull("A byte array was not created", ngrms[i].getByteArray());
		}
	}
	
	@Test
	public void testCorrectNutInfo1() {
		NutritionFacts n = new NutritionFacts(ngrms[0].name);
		
		assertTrue(n.calories == 52);
		assertTrue(n.totalFat == .17);
		assertTrue(n.calFromFat == 2);
		assertTrue(n.protein == .26);
		assertTrue(n.sugar == 10.39);
		assertTrue(n.fiber == 2.4);
		assertTrue(n.carbs == 13.81);
		assertTrue(n.sodium == 1);
	}
	
	@Test
	public void testCorrectNutInfo2() {
		NutritionFacts n = new NutritionFacts(ngrms[2].name);
		
		assertTrue(n.calories == 89);
		assertTrue(n.totalFat == .33);
		assertTrue(n.calFromFat == 3);
		assertTrue(n.protein == 1.09);
		assertTrue(n.sugar == 12.23);
		assertTrue(n.fiber == 2.6);
		assertTrue(n.carbs == 22.84);
		assertTrue(n.sodium == 1);
	}
	
	@Test
	public void testCorrectNutInfo3() {
		NutritionFacts n = new NutritionFacts(ngrms[4].name);
		
		assertTrue(n.calories == 63);
		assertTrue(n.totalFat == .3);
		assertTrue(n.calFromFat == 3);
		assertTrue(n.protein == 1.3);
		assertTrue(n.sugar == 8.6);
		assertTrue(n.fiber == 4.5);
		assertTrue(n.carbs == 15.5);
		assertTrue(n.sodium == 2);
	}
}
