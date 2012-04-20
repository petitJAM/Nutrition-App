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
	 * The actual matrix of transitions
	 */
	public double[][] t;
	
	/**
	 * Create a new TransitionMatrix with size N x N filled with 1.
	 *
	 * @param N
	 */
	public TransitionMatrix(int N) {
	}
	
	/**
	 * Add i to all elements of the matrix
	 *
	 * @param i - add to all elements
	 */
	public void addToAll(int i) {
		
	}
	
	/**
	 * Divide all elements by i.
	 *
	 * @param i - divide all elements by
	 */
	public void divideAllBy(int i) {
		
	}
	
	/**
	 * Average two given TransitionMatrices elementwise
	 *
	 * @param t1 - first matrix
	 * @param t2 - second matrix
	 * @return elementwise average of the two matrices
	 */
	public static TransitionMatrix average2Matrices(TransitionMatrix t1, TransitionMatrix t2) {
		return null;
	}
}
