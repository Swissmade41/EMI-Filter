/*
 * Class: Capacitor
 * This class represents capacitors with its parasitic parameters.
 * It calculates the impedance for a specific frequency.
 */
package team1.model;

import org.apache.commons.math3.complex.Complex;


public class Capacitor implements ElecComponent{
    private double d_C;
	private double d_L;
	private double d_R;
	
	/*
	 * sets the capacity and the parasitic impedance and resistance
	 */
	public Capacitor(double d_C, double d_L, double d_R) {
		this.d_C = d_C;
		this.d_L = d_L;
		this.d_R = d_R;
	}
	
	/*
	 * calculates the impedance of the capacitor and returns it.
	 * the circuit is a series circuit of R, L, C
	 */
	public Complex getImpedance(double d_omega) {
		return new Complex(d_R, d_omega*d_L + (-1/(d_omega*d_C)));
	}

	/*
	 * sets the capacity of the capacitor
	 */
	public void setC(double d_C) {
		this.d_C = d_C;
		
	}

	/*
	 * sets the parasitic resistance of the capacitor
	 */
	public void setR(double d_R) {
		this.d_R = d_R;
		
	}

	/*
	 * sets the parasitic inductance of the capacitor
	 */
	public void setL(double d_L) {
		this.d_L = d_L;
		
	}
	
}
