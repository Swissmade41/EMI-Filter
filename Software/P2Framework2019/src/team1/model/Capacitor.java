package team1.model;

import org.apache.commons.math3.complex.Complex;

/**
 * This class represents capacitors with its parasitic parameters. It calculates
 * the impedance for a specific frequency.
 */
public class Capacitor implements ElecComponent {
	private double d_C;
	private double d_L;
	private double d_R;

	/**
	 * Sets the capacity and the parasitic impedance and resistance
	 * 
	 * @param d_C Capacity
	 * @param d_L Impedance
	 * @param d_R Resistance
	 */
	public Capacitor(double d_C, double d_L, double d_R) {
		this.d_C = d_C;
		this.d_L = d_L;
		this.d_R = d_R;
	}

	/**
	 * Calculates the impedance of the capacitor and returns it. the circuit is a
	 * series circuit of R, L, C
	 * 
	 * @param d_omega Angular frequency
	 */
	public Complex getImpedance(double d_omega) {
		return new Complex(d_R, d_omega * d_L + (-1 / (d_omega * d_C)));
	}

	/**
	 * Sets the capacity of the capacitor
	 * 
	 * @param d_C Capacity to set
	 */
	public void setC(double d_C) {
		this.d_C = d_C;

	}

	/**
	 * Sets the parasitic resistance of the capacitor
	 * 
	 * @param d_R Parasitic resistance to set
	 */
	public void setR(double d_R) {
		this.d_R = d_R;

	}

	/**
	 * Sets the parasitic inductance of the capacitor
	 * 
	 * @param d_L Inductance to set
	 */
	public void setL(double d_L) {
		this.d_L = d_L;

	}

}
