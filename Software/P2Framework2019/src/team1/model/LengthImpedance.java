package team1.model;

import org.apache.commons.math3.complex.Complex;

/*
 * takes complex Value Zb and put it into a 2x2 A-type Matrix
 */

public class LengthImpedance {

	public Complex[][] LengthImpedance(Complex Zb) {
		final Complex[][] lengthImp = new Complex[][] { { new Complex(1, 0), Zb },
				{ new Complex(0, 0), new Complex(1, 0) } };
		return lengthImp;
	}

}
