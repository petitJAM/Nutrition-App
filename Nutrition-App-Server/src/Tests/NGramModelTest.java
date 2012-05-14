package Tests;

import NGramModel.NGramModel;
import NGramModel.TransitionMatrix;

import junit.framework.TestCase;

/**
 * Test NGramModel
 * 
 * @author Alex Petitjean. Created Apr 13, 2012.
 */
public class NGramModelTest extends TestCase {

	/**
	 * Test creation
	 */
	public void testThatNGramModelsAreCreated() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", "name", "name", "name", t);
		assertNotNull(ngm);
	}

	/**
	 * Test the ability to get an NGramModel's name
	 */
	public void testGetName() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", "name", "name", "name", t);

		assertEquals("name", ngm.name);
	}

	/**
	 * Test that NGramModels are created with the right TransitionMatrix
	 */
	public void testGetTransitionMatrix() {
		TransitionMatrix t = new TransitionMatrix(3);
		NGramModel ngm = new NGramModel("name", "name", "name", "name", t);

		assertEquals(t, ngm.tmat);
	}

	/**
	 * Test the loglikelihood method Currently does not test correct value.
	 */
	public void testLogLikelihood() {
		TransitionMatrix t = new TransitionMatrix(2);
		NGramModel ngm = new NGramModel("name", "name", "name", "name", t);
		double expected = 0.0;

		byte[] seq = new byte[2];
		seq[0] = 0;
		seq[1] = 0;

		assertEquals(expected, ngm.logLikelihood(seq));
	}

}
