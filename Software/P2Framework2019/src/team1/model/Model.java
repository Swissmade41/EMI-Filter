package team1.model;

import team1.util.Observable;
import team1.util.TraceV4;

public class Model extends Observable {
	private TraceV4 trace = new TraceV4(this);

	public Model() {
		trace.constructorCall();
	}
	
	public void readTextfile() {
		
	}
	
	public void generateTextfile() {
		
	}
	
	public void calculate() {
		
	}
	
	public double[][][] getCM() {
		// cmData&dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		return null;
	}
	
	public double[][][] getDM() {
		// cmData&dmData [Funktionsnummer],[x-Werte = 0, y-Werte = 1],[Datensätze]
		return null;
	}
}
