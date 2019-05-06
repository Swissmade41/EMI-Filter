/*
 * Interface : ElecComponent
 * This interface contains the main methods to describes a electrical component (R, L, C)
 * with its parasitic parameters.
 * Additionally it contains a method to calculate the impedance of the electrical component
 */
package team1.model;
import org.apache.commons.math3.complex.Complex;

public interface ElecComponent {
	/*
	 * returns the total impedance for a specific frequency
	 */
	public Complex getImpedance(double d_omega);
	/*
	 * sets the given capacity
	 */
	public void setC(double d_C);
	/*
	 * sets the given resistance
	 */
	public void setR(double d_R);
	/*
	 * sets the given inductance
	 */
	public void setL(double d_L);
}
