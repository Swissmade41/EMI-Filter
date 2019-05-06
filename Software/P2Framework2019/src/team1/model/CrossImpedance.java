package team1.model;

import org.apache.commons.math3.complex.Complex;

/*
 * takes complex Value Za and put it into a 2x2 A-type Matrix
 */
public class CrossImpedance {

	public Complex[][] CrossImpedance(Complex Za) {
		final Complex[][] crossImp = new Complex[][] { { new Complex(1, 0), new Complex(0, 0) },
				{ Za.reciprocal(), new Complex(1, 0) } };
		return crossImp;
	}

}
