package team1.userinterface;

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
		trace.methodeCall();
		this.view = view;
	}
	
	public void addFilter() {
		trace.methodeCall();
		view.filtertablePanel.addFilter("true","Filter "+ String.valueOf(view.filtertablePanel.getRowcount()+1));
		view.inputPanel.resetInputPanel();
	}
	
	public void removeFilter() {
		trace.methodeCall();
		view.filtertablePanel.removeFilter();
	}
	
	public void saveFile() {
		trace.methodeCall();
		view.filtertablePanel.saveFiltertable();
	}
	
	public void loadFile() {
		trace.methodeCall();
		view.filtertablePanel.loadFiltertable();
	}

	
	public void updateParamterValues(){
		trace.methodeCall();
		try {
			view.filtertablePanel.updateEffectiveParameterValues(view.inputPanel.getEffectiveParameterValues());
			view.filtertablePanel.updateUserInputParameterValues(view.inputPanel.getUserInputParameterValues());
		} catch (NullPointerException e) {
		}
		
		calculateInsertionLoss(-1);
	}
	
	
	public void updateInputPanel(double[] userInputFilterParameter, double[] effectiveFilterParameter) {
		trace.methodeCall();
		view.inputPanel.updateInputPanel(userInputFilterParameter, effectiveFilterParameter);
	}
	
	/**
	 * calculates the insertion loss for either a specific row or the selected filter
	 * @param row
	 * 		-1 = calculates IL for selected filter else it calculates for the row
	 */
	public void calculateInsertionLoss(int row) {
		trace.methodeCall();
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
		trace.methodeCall();
		model.deleteCalculation(selectedRow);
	}
}
