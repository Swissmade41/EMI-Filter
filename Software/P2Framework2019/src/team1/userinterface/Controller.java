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
		view.filtertablePanel.addFilter("true","Filter "+ String.valueOf(view.filtertablePanel.getRowcount()+1));
		view.inputPanel.resetInputPanel();
	}
	
	public void removeFilter() {
		view.filtertablePanel.removeFilter();
	}
	
	public void saveFile() {
		view.filtertablePanel.saveFiltertable();
	}
	
	public void loadFile() {
		view.filtertablePanel.loadFiltertable();
	}

	
	public void updateParamterValues(){
		//TODO beschreieben:Beim aufstarten entsteht nullpointer weil methode aufgerufen wird ohne das inputpanel fertig konstruiert ist
		try {
			view.filtertablePanel.updateEffectiveParameterValues(view.inputPanel.getEffectiveParameterValues());
			view.filtertablePanel.updateUserInputParameterValues(view.inputPanel.getUserInputParameterValues());
		} catch (NullPointerException e) {
		}
		
		calculateInsertionLoss(-1);
	}
	
	
	public void updateInputPanel(double[] userInputFilterParameter, double[] effectiveFilterParameter) {
		view.inputPanel.updateInputPanel(userInputFilterParameter, effectiveFilterParameter);
	}
	
	/**
	 * calculates the insertion loss for either a specific row or the selected filter
	 * @param row
	 * 		-1 = calculates IL for selected filter else it calculates for the row
	 */
	public void calculateInsertionLoss(int row) {
		if(row == -1) {
			model.calculate(view.filtertablePanel.getEffectiveParameterValues(), view.filtertablePanel.getSelectedRow(), view.filtertablePanel.getSelectedRowVisibility());
		}
		else {
			model.calculate(view.filtertablePanel.getEffectiveParameterValues(), row, view.filtertablePanel.getSelectedRowVisibility());
		}
		
	}

	/**
	 * deletes the claculated values for a specific value
	 * @param selectedRow
	 * 		row to remove
	 */	
	public void deleteRowInCalculationData(int selectedRow) {
		model.deleteCalculation(selectedRow);
	}
}
