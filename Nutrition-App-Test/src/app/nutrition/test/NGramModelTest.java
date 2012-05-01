package app.nutrition.test;

import java.util.ArrayList;
import java.util.List;

import foodImaging.NGramModel;

import junit.framework.TestCase;
import app.nutrition.TransitionMatrix;

/**
 * Test NGramModel
 * 
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NGramModelTest extends TestCase {

	/**
	 * Test creation
	 */
	public void testThatNGramModelsAreCreated() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);
		assertNotNull(ngm);
	}

	/**
	 * Test the ability to get an NGramModel's name
	 */
	public void testGetName() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);

		assertEquals("name", ngm.name);
	}

	/**
	 * Test that NGramModels are created with the right TransitionMatrix
	 */
	public void testGetTransitionMatrix() {
		TransitionMatrix t = new TransitionMatrix(3);
		NGramModel ngm = new NGramModel("name", t);

		assertEquals(t, ngm.tmat);
	}

	/**
	 * Test the loglikelihood method
	 * Currently does not test correct value.
	 */
	public void testLogLikelihood() {
		TransitionMatrix t = new TransitionMatrix(2);
		NGramModel ngm = new NGramModel("name", t);
		double expected = 0.0;
		List<Integer> seq = new ArrayList<Integer>();
		seq.add(0);
		seq.add(0);
		
		assertEquals(expected, ngm.logLikelihood(seq));
	}

}
