package team1.model;

import org.apache.commons.math3.complex.Complex;

import team1.util.Observable;
import team1.util.TraceV4;
import team1.matlabfunctions.MikroMatlab;
/**
 * the model provides the calculations of the insertion loss
 */
public class Model extends Observable {
	private TraceV4 trace = new TraceV4(this);
	
	private int s32_nbrOfCalculations = 400;
	private double[][][] cmData = new double[100][2][s32_nbrOfCalculations];
	private double[][][] dmData = new double[100][2][s32_nbrOfCalculations];
	
	private static final int L0 = 0;
	private static final int Rp = 1;
	private static final int Cp = 2;
	private static final int Lr = 3;
	private static final int Rw = 4;
	private static final int Cx1 = 5;
	private static final int Lx1 = 6;
	private static final int Rx1 = 7;
	private static final int Cx2 = 8;
	private static final int Lx2 = 9;
	private static final int Rx2 = 10;
	private static final int Cy = 11;
	private static final int Ly = 12;
	private static final int Ry = 13;
	
	/**
	 * Constructor of the call model
	 */
	public Model() {
		trace.constructorCall();
	}
	
	/** this method returns the calculated insertion loss for common mode
	 * @return 
	 * 		cmData  [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
	 */		
	public double[][][] getCM() {
		trace.methodeCall();
		return cmData;
	}
	
	/** This method returns the calculated insertion loss for differential mode
	 * @return 
	 * 		dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
	 */		
	public double[][][] getDM() {
		trace.methodeCall();
		return dmData;
	}
	
	/**
	 * This method notifys the observer
	 */
	public void notifyObservers() {
		trace.methodeCall();
        setChanged();
        super.notifyObservers();
    }
	
	/**
	 * This method calculates the insertion loss for the given components for common and differential mode
	 * @param 
	 * 		elecComponents first dimensions specifies the number of the filter 
	 * 		the second dimensions store the values of the electric components
	 */
	public void calculate(double[][] elecComponents, int s32_filter, boolean b_visibility) {
		trace.methodeCall();
		if(b_visibility) {
			double[] d_freq = MikroMatlab.logspace(0, Math.log10(3e7), s32_nbrOfCalculations);
			// Common mode
			Inductor cm_L0 = new Inductor(2*elecComponents[s32_filter][Cp], elecComponents[s32_filter][L0], 0.5*elecComponents[s32_filter][Rp]);
			Inductor cm_Lr = new Inductor(0, 0.5*elecComponents[s32_filter][Lr], 0);
			Resistor cm_Rw = new Resistor(0.5*elecComponents[s32_filter][Rw]);
			Capacitor cm_Cy = new Capacitor(2*elecComponents[s32_filter][Cy], 0.5*elecComponents[s32_filter][Ly], 0.5*elecComponents[s32_filter][Ry]);
			double d_Rb = 50;
			Complex[][] cm_A1;
			Complex[][] cm_A2;
			Complex[][] cm_A;
			Complex cm_s21;
			for(int i=0; i<d_freq.length; i++) {
				cm_A1 = MikroMatlab.getShuntImpedanceMatrix(cm_Cy.getImpedance(d_freq[i]*2*Math.PI));
				cm_A2 = MikroMatlab.getSeriesImpedanceMatrix(cm_L0.getImpedance(d_freq[i]*2*Math.PI).add(cm_Lr.getImpedance(d_freq[i]*2*Math.PI)).add(cm_Rw.getImpedance(d_freq[i]*2*Math.PI)));			
				cm_A = MikroMatlab.cascade(cm_A1, cm_A2);
				cm_s21 =new Complex(2,0).divide( cm_A[0][0].add(cm_A[0][1].divide(d_Rb).add(cm_A[1][0].multiply(d_Rb)).add(cm_A[1][1])));
				cmData[s32_filter][0][i] = d_freq[i];
				cmData[s32_filter][1][i] = -20*Math.log10(cm_s21.abs());
			}
			//Differential mode
			Capacitor dm_Cy = new Capacitor(elecComponents[s32_filter][Cy], elecComponents[s32_filter][Ly], elecComponents[s32_filter][Ry]);
			Capacitor dm_Cx1 = new Capacitor(2*elecComponents[s32_filter][Cx1], 0.5*elecComponents[s32_filter][Lx1], 0.5*elecComponents[s32_filter][Rx1]);
			Capacitor dm_Cx2 = new Capacitor(2*elecComponents[s32_filter][Cx2], 0.5*elecComponents[s32_filter][Lx2], 0.5*elecComponents[s32_filter][Rx2]);
			Inductor dm_Lr = new Inductor(0, elecComponents[s32_filter][Lr], 0);
			Resistor dm_Rw = new Resistor(elecComponents[s32_filter][Rw]);
			Complex[][] dm_A1;
			Complex[][] dm_A2;
			Complex[][] dm_A3;
			Complex[][] dm_A4;
			Complex[][] dm_A;
			Complex dm_s21;	
			for(int i=0; i<d_freq.length; i++) {
				dm_A1 = MikroMatlab.getShuntImpedanceMatrix(dm_Cy.getImpedance(d_freq[i]*2*Math.PI));
				dm_A2 = MikroMatlab.getShuntImpedanceMatrix(dm_Cx1.getImpedance(d_freq[i]*2*Math.PI));
				dm_A3 = MikroMatlab.getSeriesImpedanceMatrix(dm_Lr.getImpedance(d_freq[i]*2*Math.PI).add(dm_Rw.getImpedance(d_freq[i]*2*Math.PI)));
				dm_A4 = MikroMatlab.getShuntImpedanceMatrix(dm_Cx2.getImpedance(d_freq[i]*2*Math.PI));
				dm_A = MikroMatlab.cascade(MikroMatlab.cascade(MikroMatlab.cascade(dm_A1, dm_A2), dm_A3), dm_A4);
				d_Rb = 25;
				dm_s21 =new Complex(2,0).divide( dm_A[0][0].add(dm_A[0][1].divide(d_Rb).add(dm_A[1][0].multiply(d_Rb)).add(dm_A[1][1])));
				dmData[s32_filter][0][i] = d_freq[i];
				dmData[s32_filter][1][i] = -20*Math.log10(dm_s21.abs());;
			}
		}
		else 
		{
			cmData[s32_filter][0][0] = -1;
			dmData[s32_filter][0][0] = -1;
		}
		notifyObservers();
	}
	
	/**
	 * deletes a dataset for a specific filter
	 * @param row
	 */
    public void deleteCalculation(int row) {
    	trace.methodeCall();
       	for (int n = row; n < dmData.length-row-1; n++) {
       		for (int m = 0; m < dmData[0].length; m++) {
       			for(int o = 0; o < dmData[0][0].length; o++) {
       				dmData[n][m][o]=dmData[n+1][m][o];
       				cmData[n][m][o]=cmData[n+1][m][o];
       			}	
       		}
       	}		
   	}
}