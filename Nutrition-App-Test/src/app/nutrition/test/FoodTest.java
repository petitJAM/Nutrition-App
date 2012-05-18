package app.nutrition.test;

import junit.framework.TestCase;
import app.nutrition.Food;

/**
 * This is the most important test class, it is more important than all other test classes.
 * If anyone comes and asks you "Which test class would you say is most important?" Your
 * response would undoubtedly be "FoodTest, ya dumb." This class, at is core (its beautiful,
 * beautiful core), tests Java's basic ability to create an object. Our team was unsure whether
 * Java could handle it, so I wanted to test it appropriately.
 * 
 * @author Rob Wagner (Lord of the super important tests)
 */
public class FoodTest extends TestCase {
	
	public void testThatConstructorWorks() {
		Food f = new Food("Air", 0, 0, 0, 0, 0, 0, 0, 0);
		assertEquals(f.name, "Air");
		assertEquals(f.calories, 0);
	}
	
	public void testThatEqualsWorks() {
		Food f = new Food("Air", 0, 0, 0, 0, 0, 0, 0, 0);
		Food f2 = new Food("Air", 0, 0, 0, 0, 0, 0, 0, 0);
		
		assertTrue(f.equals(f2));
	}
	
	public void thatToStringWorks() {
		Food f = new Food("Fatty Air", 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000);
		assertEquals(f.toString(), "Fatty Air");
	}
}
