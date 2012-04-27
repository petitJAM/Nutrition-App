package app.nutrition;

/**
 * This class represents a transition probability matrix where 
 * TransitionMatrix[i][j] is the probability of transitioning from i to j.
 * 
 * [0, i] and [0, j] are the same range and represent the possible states
 * to transition between.
 *
 * @author Alex Petitjean, Rob Wagner
 *         Created Apr 18, 2012.
 *         Last Updated Apr 26, 2012.
 */
public class TransitionMatrix {
	/**
	 * Size of the TransitionMatrix
	 */
	public int size;
	
	/**
	 * The actual matrix of transitions
	 */
	public double[][] mat;
	
	/**
	 * Create a new TransitionMatrix with size N x N filled with 1.
	 *
	 * @param N
	 */
	public TransitionMatrix(int N) {
		size = N;
		mat = new double[N][N];
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				mat[i][j] = 1;
	}
	
	
	/**
	 * Add i to all elements of the matrix
	 *
	 * @param i - add to all elements
	 */
	public void addToAll(double i) {
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				mat[j][k] += i;
	}
	
	/**
	 * Divide all elements by i.
	 *
	 * @param i - divide all elements by
	 */
	public void divideAllBy(double i) {
		for (int j = 0; j < size; j++)
			for (int k = 0; k < size; k++)
				mat[j][k] /= i;
	}
	
	/**
	 * Average two given TransitionMatrices elementwise
	 *
	 * @param t1 - first matrix
	 * @param t2 - second matrix
	 * @return elementwise average of the two matrices
	 * @throws IllegalArgumentException if t1 and t2 have different sizes
	 */
	public static TransitionMatrix average2Matrices(TransitionMatrix t1, TransitionMatrix t2) throws IllegalArgumentException {
		if (t1.size != t2.size)
			throw new IllegalArgumentException("Matrix size mismatch");
		
		TransitionMatrix tnew = new TransitionMatrix(t1.size);
		
		for (int i = 0; i < t1.size; i++)
			for (int j = 0; j < t1.size; j++)
				tnew.mat[i][j] = (t1.mat[i][j] + t2.mat[i][j]) / 2.0;
		
		return tnew;
	}
}
