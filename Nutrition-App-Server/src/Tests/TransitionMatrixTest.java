package Tests;

import NGramModel.TransitionMatrix;
import junit.framework.TestCase;

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
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				assertEquals(1.0, t.mat[i][j]);
	}
	
	/**
	 * Test adding to all elements
	 */
	public void testAddToAll() {
		TransitionMatrix t = new TransitionMatrix(N);
		t.addToAll(1.0);
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				assertEquals(2.0, t.mat[i][j]);
	}
	
	/**
	 * Test dividing all elements by
	 */
	public void testDivideAllBy() {
		TransitionMatrix t = new TransitionMatrix(N);
		t.divideAllBy(2);
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				assertEquals(0.5, t.mat[i][j]);
	}
	
	/**
	 * Test exception on different size matrices for averaging
	 */
	public void testAverage2MatricesExceptionThrown() {
		TransitionMatrix t1 = new TransitionMatrix(N);
		TransitionMatrix t2 = new TransitionMatrix(N+1);
		
		try {
			TransitionMatrix.average2Matrices(t1, t2);
			fail("Exception not thrown");
		} catch (Exception e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("Matrix size mismatch", e.getMessage());
		}
	}
	
	/**
	 * Test averaging two TransitionMatrices
	 */
	public void testAverage2Matrices() {
		TransitionMatrix t1 = new TransitionMatrix(N);
		TransitionMatrix t2 = new TransitionMatrix(N);
		
		// init to 1, add 1 = 2. avg should be 1.5
		double expected_avg = 1.5;
		t1.addToAll(1);
		
		TransitionMatrix t3;
		t3 = TransitionMatrix.average2Matrices(t1, t2);
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				assertEquals(expected_avg, t3.mat[i][j]);
	}
}
