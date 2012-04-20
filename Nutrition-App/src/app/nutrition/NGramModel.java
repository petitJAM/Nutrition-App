package app.nutrition;

import java.util.List;

/**
 * Contains a matrix of color transitions and a name to identify it.
 * 
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NGramModel {
	
	/**
	 * Name of this NGramModel
	 */
	public String name;
	/**
	 * TransitionMatrix this NGramModel encases
	 */
	public TransitionMatrix tmat;

	/**
	 * Create a model with the given name and TransitionMatrix
	 * 
	 * @param name name of this NGramModel
	 * @param t TransitionMatrix associated with this NGramModel
	 */
	public NGramModel(String name, TransitionMatrix t) {
		this.name = name;
		tmat = t;
	}

	/**
	 * Returns the log likelihood of the given sequence matching this model
	 * 
	 * @param seq - color transition sequence
	 * @return loglikelihood of the sequence matching this NGramModel
	 */
	public double logLikelihood(List<Integer> seq) {

		return 0;
	}
}
