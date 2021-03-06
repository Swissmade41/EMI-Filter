package team1.matlabfunctions;

import org.apache.commons.math3.complex.Complex;

/**
 * This class provides some math methods which are needed for the calculations
 */
public class MikroMatlab {

	/**
	 * Creates a list of logarithmic spaced values from 10^start to 10^end
	 * 
	 * @param d_start  Start pow
	 * @param d_end    End pow
	 * @param d_points Number of values
	 * @return Logarithmic spaced list
	 */
	public static double[] logspace(double d_start, double d_end, int d_points) {
		double d_vector[] = new double[(int) d_points];
		d_vector[0] = Math.pow(10, d_start); // fill first cell
		for (int i = 1; i < d_points; i++) {
			d_vector[i] = d_vector[i - 1] * Math.pow(10.0, ((d_end - d_start) / (d_points - 1)));
		}
		return d_vector;
	}

	/**
	 * Multiplies two matrices with dimensions 2*2.
	 * 
	 * @param A Matrix 1 (2*2)
	 * @param B Matrix 2 (2*2)
	 * @return Product of Matrix 1 and Matrix 2
	 **/
	public static Complex[][] cascade(Complex[][] A, Complex[][] B) {
		Complex[][] res = A;
		res[0][0] = A[0][0].multiply(B[0][0]).add(A[1][0].multiply(B[0][1]));
		res[1][0] = A[0][0].multiply(B[1][0]).add(A[1][0].multiply(B[1][1]));
		res[0][1] = A[0][1].multiply(B[0][0]).add(A[1][1].multiply(B[0][1]));
		res[1][1] = A[0][1].multiply(B[1][0]).add(A[1][1].multiply(B[1][1]));
		return res;
	}

	/**
	 * Creats a shunt impedance matrix for a given impedance.
	 * 
	 * @param c_impedance Shunt impedance
	 * @return Shunt impedance matrix
	 **/
	public static Complex[][] getShuntImpedanceMatrix(Complex c_impedance) {
		Complex[][] shuntImp = new Complex[][] { { new Complex(1, 0), new Complex(0, 0) },
				{ c_impedance.reciprocal(), new Complex(1, 0) } };
		return shuntImp;

	}

	/**
	 * Creats a series impedance matrix for a given impedance.
	 * 
	 * @param c_impedance Series impedance
	 * @return Series impedance matrix
	 **/
	public static Complex[][] getSeriesImpedanceMatrix(Complex c_impedance) {
		Complex[][] seriesImp = new Complex[][] { { new Complex(1, 0), c_impedance },
				{ new Complex(0, 0), new Complex(1, 0) } };
		return seriesImp;
	}
}
