package app.nutrition.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.nutrition.NGramModel;
import app.nutrition.TransitionMatrix;

/**
 * TODO Put here a description of what this class does.
 * 
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NGramModelTest {

	@Test
	public void testThatNGramModelsAreCreated() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);
		assertNotNull(ngm);
	}

	@Test
	public void testGetName() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);

		assertEquals("name", ngm.name);
	}

	@Test
	public void testGetTransitionMatrix() {
		TransitionMatrix t = new TransitionMatrix(3, 3);
		NGramModel ngm = new NGramModel("name", t);

		assertEquals(t, ngm.tmat);
	}

	/**
	 * TODO Put here a description of what this method does.
	 *
	 */
	@Test
	public void testLogLikelihood() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);
		double expected = 0.0;
		List<Integer> seq = new ArrayList<Integer>();
		seq.add(0);
		seq.add(0);
		
		assertEquals(expected, ngm.logLikelihood(seq));
	}

}
