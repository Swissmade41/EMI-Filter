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
	
	public void addFilter() {
		view.filtertablePanel.addFilter();
		view.inputPanel.resetInputPanel();
	}
	
	public void removeFilter() {
		view.filtertablePanel.removeFilter();
	}

	
	public void updateParamterValues(){
		//TODO beschreieben:Beim aufstarten entsteht nullpointer weil methode aufgerufen wird ohne das inputpanel fertig konstruiert ist
		try {
			view.filtertablePanel.updateEffectiveParameterValues(view.inputPanel.getEffectiveParameterValues());
			view.filtertablePanel.updateUserInputParameterValues(view.inputPanel.getUserInputParameterValues());
		} catch (NullPointerException e) {
			System.out.println("updateexeption");
		}
		
		calculateInsertionLoss();
	}
	
	
	public void updateInputPanel(double[] userInputFilterParameter, double[] effectiveFilterParameter) {
		view.inputPanel.updateInputPanel(userInputFilterParameter, effectiveFilterParameter);
	}
	
	public void calculateInsertionLoss() {
		model.calculate(view.filtertablePanel.getEffectiveParameterValues());
	}
}
