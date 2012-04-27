package app.nutrition;

import java.util.List;

/**
 * Contains a matrix of color transitions and a name to identify it.
 * 
 * @author Alex Petitjean, Rob Wagner
 *         Created Apr 13, 2012.
 *         Last Updated Apr 26, 2012
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
	 * @param name - name of this NGramModel
	 * @param t - TransitionMatrix associated with this NGramModel
	 */
	public NGramModel(String name, TransitionMatrix t) {
		this.name = name;
		tmat = t;
	}

	/**
	 * Create a model with the given name and train it based on the given
	 * sequence.
	 * 
	 * @param name - name of this NGramModel
	 * @param seq - the sequence of colors
	 */
	public NGramModel(String name, List<Integer> seq) {
		this.name = name;
		train(seq);
	}

	/**
	 * Returns the log likelihood of the given sequence matching this model
	 * 
	 * @param seq - color transition sequence
	 * @return loglikelihood of the sequence matching this NGramModel
	 */
	public double logLikelihood(List<Integer> seq) {
		double ll = 0.0;
		for (int i = 1; i < seq.size(); i++) {
			int x = seq.get(i - 1);
			int y = seq.get(i);
			if (tmat.mat[x][y] != 0)
				ll += Math.log10(tmat.mat[seq.get(i - 1)][seq.get(i)]);
		}
		return ll;
	}

	/**
	 * Trains a NGramModel based on the sequence. Counts the transitions between
	 * colors in the sequence.
	 * 
	 * @param seq - color transition sequence
	 * @param name - name of the model
	 * @return a new trained NGramModel based on the given sequence
	 */
	public void train(List<Integer> seq) {
		int s = seq.size();
		for (int i = 1; i < s; i++)
			tmat.mat[seq.get(i - 1)][seq.get(i)] += 1;

		for (int i = 0; i < tmat.size; i++) {
			double rowsum = 0;
			for (int j = 0; j < tmat.size; j++)
				rowsum += tmat.mat[i][j];
			for (int j = 0; j < tmat.size; j++)
				tmat.mat[i][j] /= (rowsum);
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
