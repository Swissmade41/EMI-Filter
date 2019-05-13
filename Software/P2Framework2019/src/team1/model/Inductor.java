package team1.model;

import org.apache.commons.math3.complex.Complex;

/**
 * This class represents inductors with its parasitic parameters. It calculates
 * the impedance for a specific frequency.
 */
public class Inductor implements ElecComponent {
	private double d_C;
	private double d_L;
	private double d_R;

	/**
	 * Sets the inductance and the parasitic capacity and resistance
	 * 
	 * @param d_C The parasitic capacity to set
	 * 
	 * @param d_L The inductance to set
	 * 
	 * @param d_R The resistance to set
	 */
	public Inductor(double d_C, double d_L, double d_R) {
		this.d_C = d_C;
		this.d_L = d_L;
		this.d_R = d_R;
	}

	/**
	 * Calculates the impedance of the inductor and returns it. the circuit is a
	 * series circuit of R, L, C
	 * 
	 * @param d_omega Angular frequency
	 * @return Impedance for a given angular frequency
	 */
	public Complex getImpedance(double d_omega) {
		Complex c_Admittance;
		if (d_R == 0 && d_C == 0)
			c_Admittance = new Complex(0.0, -1 / (d_omega * d_L));
		else
			c_Admittance = new Complex(1 / d_R, d_omega * d_C - 1 / (d_omega * d_L));
		return c_Admittance.reciprocal();
	}

	/**
	 * Sets the parasitic capacity of the inductor
	 * 
	 * @param d_C capacity to set
	 **/
	public void setC(double d_C) {
		this.d_C = d_C;

	}

	/**
	 * Sets the parasitic resistance of the inductor
	 * 
	 * @param d_R Resistance to set
	 */
	public void setR(double d_R) {
		this.d_R = d_R;

	}

	/**
	 * Sets the inductance of the inductor
	 * 
	 * @param d_L Inductance to set
	 */
	public void setL(double d_L) {
		this.d_L = d_L;

	}

}
