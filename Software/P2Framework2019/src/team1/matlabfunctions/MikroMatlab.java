package team1.matlabfunctions;

import org.apache.commons.math3.complex.Complex;

public class MikroMatlab {

//	public static final double[] linspace(double begin, double end, int cnt) {
//		double[] res = new double[cnt];
//		double delta = (end - begin) / (cnt - 1);
//
//		res[0] = begin;
//		for (int i = 1; i < res.length - 1; i++) {
//			res[i] = begin + i * delta;
//		}
//		res[res.length - 1] = end;
//
//		return res;
//	}
    /**
     * generates n logarithmically-spaced points between d1 and d2 using the
     * provided base.
     * 
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @param base the logarithmic base to use
     * @return an array of lineraly space points.
     */
    public static final double[] logspace(double d1, double d2, int n, double base) {
        double[] y = new double[n];
        double[] p = linspace(d1, d2, n);
        for(int i = 0; i < y.length - 1; i++) {
            y[i] = Math.pow(base, p[i]);
        }
        y[y.length - 1] = Math.pow(base, d2);
        return y;
    }

    /**
     * generates n linearly-spaced points between d1 and d2.
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @return an array of lineraly space points.
     */
    public static final double[] linspace(double d1, double d2, int n) {

        double[] y = new double[n];
        double dy = (d2 - d1) / (n - 1);
        for(int i = 0; i < n; i++) {
            y[i] = d1 + (dy * i);
        }

        return y;

    }
    public Complex[][] cascade(Complex[][] A, Complex[][] B){
    	Complex[][] res = A;
    	res[0][0] = A[0][0].multiply(B[0][0]).add(A[1][0].multiply(B[0][1]));  
    	res[1][0] = A[0][0].multiply(B[1][0]).add(A[1][0].multiply(B[1][1]));  		
    	res[0][1] = A[0][1].multiply(B[0][0]).add(A[1][1].multiply(B[0][1]));  
		res[1][1] = A[0][1].multiply(B[1][0]).add(A[1][1].multiply(B[1][1]));  
		return res;
    }
}
