package app.nutrition.test;

import junit.framework.TestCase;
import app.nutrition.TransitionMatrix;

/**
 * Test the TransitionMatrix class
 *
 * @author Alex Petitjean.
 *         Created Apr 19, 2012.
 */
public class TransitionMatrixTest extends TestCase {

	private int N = 4;	
	
	/**
	 * Test creation
	 */
	public void testThatTransMatrixIsCreated() {
		TransitionMatrix t = new TransitionMatrix(N);
		assertNotNull(t);
	}
	
	/**
	 * Test creation with values
	 */
	public void testThatTransMatIsCreatedWithCorrectValues() {
		TransitionMatrix t = new TransitionMatrix(N);
		assertEquals(N, t.size);
	}
}
