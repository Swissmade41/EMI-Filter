package team1.model;

import org.apache.commons.math3.complex.Complex;

/**
 * This interface contains the main methods to describes a electrical component
 * (R, L, C) with its parasitic parameters. Additionally it contains a method to
 * calculate the impedance of the electrical component
 */
public interface ElecComponent {

	/**
	 * Get the total impedance for a specific frequency
	 * 
	 * @param d_omega Angular frequency
	 * @return Impedance
	 */
	public Complex getImpedance(double d_omega);

	/**
	 * Sets the given capacity
	 * 
	 * @param d_C capacity
	 */
	public void setC(double d_C);

	/**
	 * Sets the given resistance
	 * 
	 * @param d_R Resistor
	 */
	public void setR(double d_R);

	/**
	 * Sets the given inductance
	 * 
	 * @param d_L Inductance
	 */
	public void setL(double d_L);
}
