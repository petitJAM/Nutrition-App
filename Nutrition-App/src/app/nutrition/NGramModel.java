package app.nutrition;

import java.util.List;

/**
 * TODO Put here a description of what this class does.
 *
 * @author Alex Petitjean.
 *         Created Apr 13, 2012.
 */
public class NGramModel {
	public String name;
	public TransitionMatrix tmat;

	/**
	 * TODO Put here a description of what this constructor does.
	 * 
	 * @param name name of this NGramModel
	 * @param t TransitionMatrix associated with this NGramModel
	 */
	public NGramModel(String name, TransitionMatrix t) {
		this.name = name;
		tmat = t;
	}

	/**
	 * TODO Put here a description of what this method does.
	 * 
	 * @param seq
	 * @return
	 */
	public double logLikelihood(List<Integer> seq) {
		
		return 0;
	}
}
