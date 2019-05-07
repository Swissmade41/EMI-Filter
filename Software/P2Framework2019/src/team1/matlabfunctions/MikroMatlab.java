package team1.matlabfunctions;

import org.apache.commons.math3.complex.Complex;

public class MikroMatlab {

	/**
	 * creates a list of logarithmic spaced values from 10^start to 10^end
	 * @param start 
	 * @param end
	 * @param points number of values 
	 * @return
	 */
	public double [] logspace(double d_start, double d_end, int d_points) {
        double d_vector [] = new double[(int)d_points];
        d_vector[0] = Math.pow(10,d_start);   //fill first cell
        for (int i = 1; i < d_points; i++) {
            d_vector[i] = d_vector[i-1] * Math.pow(10.0,((d_end-d_start)/(d_points-1)));
        }
        return d_vector;
    }

    /**
     * multiplies two matrices with dimensions 2*2.
     * @param A Matrix 1 (2*2)
     * @param B Matrix 2 (2*2)
     * @return product of A and B
     **/
    public Complex[][] cascade(Complex[][] A, Complex[][] B){
    	Complex[][] res = A;
    	res[0][0] = A[0][0].multiply(B[0][0]).add(A[1][0].multiply(B[0][1]));  
    	res[1][0] = A[0][0].multiply(B[1][0]).add(A[1][0].multiply(B[1][1]));  		
    	res[0][1] = A[0][1].multiply(B[0][0]).add(A[1][1].multiply(B[0][1]));  
		res[1][1] = A[0][1].multiply(B[1][0]).add(A[1][1].multiply(B[1][1]));  
		return res;
    }
}
