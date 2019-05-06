/*
 * Class: Inductor
 * This class represents inductors with its parasitic parameters.
 * It calculates the impedance for a specific frequency.
 */
package team1.model;

import org.apache.commons.math3.complex.Complex;

public class Inductor implements ElecComponent{
	private double d_C;
	private double d_L;
	private double d_R;
	
	/*
	 * sets the inductance and the parasitic capacity and resistance
	 */
	public Inductor(double d_C, double d_L, double d_R) {
		this.d_C = d_C;
		this.d_L = d_L;
		this.d_R = d_R;
	}
	public Complex getImpedance(double d_omega) {
		Complex c_Admittance;
		if(d_R == 0 && d_C == 0)
			c_Admittance = new Complex(0.0, -1/(d_omega*d_L));
		else
			c_Admittance = new Complex(1/d_R, d_omega*d_C-1/(d_omega*d_L));
		return c_Admittance.reciprocal();
	}

	/*
	 * sets the parasitic capacity of the inductor
	 */
	public void setC(double d_C) {
		this.d_C = d_C;
		
	}

	/*
	 * sets the parasitic resistance of the inductor
	 */
	public void setR(double d_R) {
		this.d_R = d_R;
		
	}

	/*
	 * sets the inductance of the inductor
	 */
	public void setL(double d_L) {
		this.d_L = d_L;
		
	}

}
