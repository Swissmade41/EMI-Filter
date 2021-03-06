package team1.model;

import org.apache.commons.math3.complex.Complex;

/**
 * This class represents resistor with its parasitic parameters. It calculates
 * the impedance for a specific frequency.
 *
 */
public class Resistor implements ElecComponent {
	private double d_R;

	/**
	 * Sets the resistance and the parasitic capacity and inductance
	 * 
	 * @param d_R Resistor
	 */
	public Resistor(double d_R) {
		this.d_R = d_R;
	}

	/**
	 * Calculates the impedance of the resistor and returns it
	 * 
	 * @param d_omega Angular frequency
	 * @return Impedance for a given angular frequency
	 */
	public Complex getImpedance(double d_omega) {
		return new Complex(d_R, 0.0);
	}

	/**
	 * this method isn't needed because the resistor doesn't need parasitic
	 * components. In further version of the software it is still possible to add
	 * them.
	 */
	public void setC(double C) {
	}

	/**
	 * Sets the parasitic resistance
	 * 
	 * @param d_R Resistor
	 */
	public void setR(double d_R) {
		this.d_R = d_R;
	}

	/**
	 * this method isn't needed because the resistor doesn't need parasitic
	 * components. In further version of the software it is still possible to add
	 * them.
	 */
	public void setL(double L) {
	}

}
