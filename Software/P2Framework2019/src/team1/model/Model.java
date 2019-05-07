package team1.model;

import org.apache.commons.math3.complex.Complex;

import team1.util.Observable;
import team1.util.TraceV4;
import team1.matlabfunctions.MikroMatlab;

public class Model extends Observable {
	private TraceV4 trace = new TraceV4(this);
	
	private double [][][]cmData;
	private double [][][]dmData;
	private int s32_nbrOfCalculations = 200;
	
	public Model() {
		trace.constructorCall();
	}
	
	public void readTextfile() {
	}
	
	public void generateTextfile() {
		
	}
	
	public void calculate() {
		double d_omega = 5e6*2*Math.PI;
		
		double d_L0 = 0.5e-3;
		double d_Rp = 7.92e3;
		double d_Cp = 6.46e-12;
		double d_Rw = 11.3e-3;
		double d_Lr = 9.2e-6;
		
		double d_Cx1 = 146.7e-9;
		double d_Rx1 = 47.4e-3;
		double d_Lx1 = 15.2e-9;
		
		double d_Cx2 = 146.7e-9;
		double d_Rx2 = 47.4e-3;
		double d_Lx2 = 15.2e-9;
		
		double d_Cy = 3.85e-9;
		double d_Ry = 246e-3;
		double d_Ly = 8e-9;
		
		// CM
		Capacitor cm_Cy = new Capacitor(2*d_Cy, 0.5*d_Ly, 0.5*d_Ry);
		Inductor cm_L0 = new Inductor(2*d_Cp, 0.5*d_L0, 0.5*d_Rp);
		Inductor cm_Lr = new Inductor(0, 0.5*d_Lr, 0);
		Resistor cm_Rw = new Resistor(0.5*d_Rw);
		
		Complex[][] cm_A1 = getACross(cm_Cy.getImpedance(d_omega));
		Complex[][] cm_A2 = getALength(cm_L0.getImpedance(d_omega).add(cm_Lr.getImpedance(d_omega)).add(cm_Rw.getImpedance(d_omega)));
		
		MikroMatlab math = new MikroMatlab();
		Complex[][] cm_A = math.cascade(cm_A1, cm_A2);
		
		double d_Rb = 50;
		Complex cm_s21 =new Complex(2,0).divide( cm_A[0][0].add(cm_A[0][1].divide(d_Rb).add(cm_A[1][0].multiply(d_Rb)).add(cm_A[1][1])));
		double cm_IL = -20*Math.log10(cm_s21.abs());

		System.out.println(cm_IL);
		
		// DM
		Capacitor dm_Cy = new Capacitor(d_Cy, d_Ly, d_Ry);
		Capacitor dm_Cx1 = new Capacitor(2*d_Cx1, 0.5*d_Lx1, 0.5*d_Rx1);
		Capacitor dm_Cx2 = new Capacitor(2*d_Cx2, 0.5*d_Lx2, 0.5*d_Rx2);
		Inductor dm_Lr = new Inductor(0, d_Lr, 0);
		Resistor dm_Rw = new Resistor(d_Rw);
		
		Complex[][] dm_A1 = getACross(dm_Cy.getImpedance(d_omega));
		Complex[][] dm_A2 = getACross(dm_Cx1.getImpedance(d_omega));
		Complex[][] dm_A3 = getALength(dm_Lr.getImpedance(d_omega).add(dm_Rw.getImpedance(d_omega)));
		Complex[][] dm_A4 = getACross(dm_Cx2.getImpedance(d_omega));
		Complex[][] dm_A = math.cascade(dm_A1, math.cascade(dm_A2, math.cascade(dm_A3, dm_A4)));
		d_Rb = 25;
		Complex dm_s21 =new Complex(2,0).divide( dm_A[0][0].add(dm_A[0][1].divide(d_Rb).add(dm_A[1][0].multiply(d_Rb)).add(dm_A[1][1])));
		double dm_IL = -20*Math.log10(dm_s21.abs());
		}

	
	public double[][][] getCM() {
		// cmData  [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		
		return null;
	}
	
	public double[][][] getDM() {
		// dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		return null;
	}
	
	private Complex[][]getACross(Complex c_impedance) {
		Complex[][] crossImp = new Complex[][] { { new Complex(1, 0), new Complex(0, 0) },
			{ c_impedance.reciprocal(), new Complex(1, 0) } };
		return crossImp;
		
	}
	
	private Complex[][]getALength(Complex c_impedance) {
		Complex[][] lengthImp = new Complex[][] { { new Complex(1, 0), c_impedance},
			{ new Complex(0, 0), new Complex(1, 0) } };
		return lengthImp;
	}
//	public static void main(String args[]) {
//		Model model = new Model();
//		model.calculate();
//	}
}


