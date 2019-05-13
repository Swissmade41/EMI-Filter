/*
 * Class: Inductor
 * This class represents inductors with its parasitic parameters.
 * It calculates the impedance for a specific frequency.
 */
package team1.model;

import org.apache.commons.math3.complex.Complex;

import team1.util.TraceV4;

public class Inductor implements ElecComponent{	
	private double d_C;
	private double d_L;
	private double d_R;
	
	/*
	 * sets the inductance and the parasitic capacity and resistance
	 * @param d_C the parasitic capacity to set
	 * @param d_L the inductance to set
	 * @param d_R the resistance to set 
	 */
	public Inductor(double d_C, double d_L, double d_R) {
		this.d_C = d_C;
		this.d_L = d_L;
		this.d_R = d_R;
	}
	
	/**
	 * calculates the impedance of the inductor and returns it.
	 * the circuit is a series circuit of R, L, C
	 * @param d_omega the given angular frequency
	 * @return impedance for a given angular frequency
	 */
	public Complex getImpedance(double d_omega) {
		Complex c_Admittance;
		if(d_R == 0 && d_C == 0)
			c_Admittance = new Complex(0.0, -1/(d_omega*d_L));
		else
			c_Admittance = new Complex(1/d_R, d_omega*d_C-1/(d_omega*d_L));
		return c_Admittance.reciprocal();
	}

	/**
	 * sets the parasitic capacity of the inductor
	 * @param d_C capacity to set 
	 **/
	public void setC(double d_C) {
		this.d_C = d_C;
		
	}

	/**
	 * sets the parasitic resistance of the inductor
	 * @param d_R resistance to set
	 */
	public void setR(double d_R) {
		this.d_R = d_R;
		
	}

	/**
	 * sets the inductance of the inductor
	 * @param d_L inductance to set
	 */
	public void setL(double d_L) {
		this.d_L = d_L;
		
	}

}
