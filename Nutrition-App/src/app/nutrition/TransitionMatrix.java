package app.nutrition;

/**
 * This class represents a transition probability matrix where 
 * TransitionMatrix[i][j] is the probability of transitioning from i to j.
 * 
 * [0, i] and [0, j] are the same range and represent the possible states
 * to transition between.
 *
 * @author Alex Petitjean.
 *         Created Apr 18, 2012.
 */
public class TransitionMatrix {
	/**
	 * Size of the TransitionMatrix
	 */
	public int size;
	
	/**
	 * Create a new TransitionMatrix with size N x N filled with 1.
	 *
	 * @param N
	 */
	public TransitionMatrix(int N) {
	}
	
	/**
	 * Create a new TransitionMatrix with size N x N filled with fill
	 *
	 * @param N
	 * @param fill
	 */
	public TransitionMatrix(int N, int fill) {
	}
}
