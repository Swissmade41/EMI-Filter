package team1.userinterface;

import team1.P2Framework2019;
import team1.model.Model;
import team1.util.TraceV4;

public class Controller {
	private TraceV4 trace = new TraceV4(this);
	private Model model;
	private View view;

	public Controller(Model model) {
		trace.constructorCall();
		this.model = model;
	}
	
	public void setView(View view) {
		this.view = view;
	}

}
