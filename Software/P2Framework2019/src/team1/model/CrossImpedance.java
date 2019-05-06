package team1.model;

import org.apache.commons.math3.complex.Complex;

public class CrossImpedance {

	public Complex[][] CrossImpedance(Complex Za) {
		final Complex[][] crossImp = new Complex[][] { { new Complex(1, 0), new Complex(0, 0) },
				{ Za.reciprocal(), new Complex(1, 0) } };
		return crossImp;
	}

}
