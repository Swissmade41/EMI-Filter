/*
 * Class: Resistor
 * This class represents resistor with its parasitic parameters.
 * It calculates the impedance for a specific frequency.
 */
package team1.model;

import org.apache.commons.math3.complex.Complex;

public class Resistor implements ElecComponent{
	private double d_R;
	
	/*
	 * sets the resistance and the parasitic capacity and inductance
	 */
	public Resistor(double d_R) {
		this.d_R = d_R;
	}
	
	public Complex getImpedance(double d_omega) {
		return new Complex(d_R, 0.0);
	}

	/*
	 * this method isn't needed because the resistor doesn't need parasitic components.
	 * In further version of the software it is still possible to add them.
	 */
	public void setC(double C) {
	}

	/*
	 * sets the parasitic resistance of the capacitor
	 */
	public void setR(double d_R) {
		this.d_R = d_R;
	}

	/*
	 * this method isn't needed because the resistor doesn't need parasitic components.
	 * In further version of the software it is still possible to add them.
	 */
	public void setL(double L) {
	}

}
