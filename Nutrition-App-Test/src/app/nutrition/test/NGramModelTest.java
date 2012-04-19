package app.nutrition.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		assertEqual("name", ngm.getName());
	}

	@Test
	public void testGetTransitionMatrix() {
		TransitionMatrix t = new TransitionMatrix(3, 3);
		NGramModel ngm = new NGramModel("name", t);

		assertEqual(t, ngm.getTransitionMatrix());
	}

	@Test
	public void testLogLikelihood() {
		TransitionMatrix t = null;
		NGramModel ngm = new NGramModel("name", t);
		double expected = 0.0;
		List<Integer> seq = new ArrayList<Integer>();
		seq.add(0);
		seq.add(0);

		assertEqual(expected, ngm.logLikelihood());
	}

}
