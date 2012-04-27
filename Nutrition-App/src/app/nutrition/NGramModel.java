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
	 * Trains a NGramModel based on the sequence
	 * 
	 * @param seq
	 * @param name
	 * @return
	 */
	public static NGramModel train(List<Integer> seq, String name) {
        TransitionMatrix t = new TransitionMatrix(ProcessImage.NUM_COLORS);
        
        int s = seq.size();
        for (int i = 1; i < s; i++)
            t.mat[seq.get(i - 1)][seq.get(i)] += 1;
        
        for (int i = 0; i < t.size; i++) {
            double rowsum = 0;
            for (int j = 0; j < t.size; j++)
                rowsum += t.mat[i][j];
            for (int j = 0; j < t.size; j++) 
            	t.mat[i][j] /= (rowsum);
        }   
        
        return new NGramModel(name, t);
    }
}
