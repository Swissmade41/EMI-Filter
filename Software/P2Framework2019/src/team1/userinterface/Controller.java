package team1.userinterface;

import javax.swing.JOptionPane;

import team1.model.Model;
import team1.util.TraceV4;

/**
 * The controller controls the information flow from the view to the model
 *
 */
public class Controller {
	private TraceV4 trace = new TraceV4(this);
	private Model model;
	private View view;

	/**
	 * Constructor of the Controller
	 * @param model
	 * 		Model object
	 */
	public Controller(Model model) {
		trace.constructorCall();
		this.model = model;
	}

	/**
	 * Set view
	 * @param view
	 * 		View object
	 */
	public void setView(View view) {
		trace.methodeCall();
		this.view = view;
	}

	/**
	 * Add a filter
	 */
	public void addFilter() {
		trace.methodeCall();
		if(view.filtertablePanel.getRowcount() >9) {
			JOptionPane.showMessageDialog(null, "The maximal filter count is 10", "Warning",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		view.filtertablePanel.addFilter("true", "Filter " + String.valueOf(view.filtertablePanel.getRowcount() + 1));
		view.inputPanel.resetInputPanel();
	}

	/**
	 * Remove a filter
	 */
	public void removeFilter() {
		trace.methodeCall();
		view.filtertablePanel.removeFilter();
	}

	/**
	 * Save file
	 */
	public void saveFile() {
		trace.methodeCall();
		view.filtertablePanel.saveFiltertable();
	}

	/**
	 * Load file
	 */
	public void loadFile() {
		trace.methodeCall();
		view.filtertablePanel.loadFiltertable();
	}

	/**
	 * Update parameter values
	 */
	public void updateParamterValues() {
		trace.methodeCall();
		try {
			model.updateEffectiveParameterValues(view.inputPanel.getEffectiveParameterValues(), view.filtertablePanel.getSelectedRow());
			model.updateUserInputParameterValues(view.inputPanel.getUserInputParameterValues(), view.filtertablePanel.getSelectedRow());
		} catch (NullPointerException e) {
		}

		calculateInsertionLoss(-1);
	}
	
	/**
	 * Delete row in filter the filter data
	 * 
	 * @param row row which should be deleted
	 */
	public void deleteRowsInFilterData(int row) {
		model.deleteRowsInFilterData(row);
	}
	
	/**
	 * Get the effective parameter values
	 * 
	 * @return effective parameter values
	 */
	public double[][] getEffectiveParameterValues() {
		return model.getEffectiveParameterValues();
	}
	
	/**
	 * Get the user input parameter values
	 * 
	 * @return user input parameter values
	 */
	public double[][] getUserInputParameterValues() {
		return model.getUserInputParameterValues();
	}
	
	/**
	 * add rows to the filter data
	 * 
	 * @param rowCount       row count of the filter table
	 * @param effectiveValue effective value which should be added to the filter
	 *                       table
	 * @param userInputValue user input value which should be added to the filter
	 *                       table
	 */
	public void addRowsToFilterData(int rowCount, double[][] effectiveValue, double[][] userInputValue) {
		model.addRowsToFilterData(rowCount, effectiveValue, userInputValue);
	}


	/**
	 * calculates the insertion loss for either a specific row or the selected
	 * filter
	 * 
	 * @param row -1 = calculates IL for selected filter else it calculates for the
	 *            row
	 */
	public void calculateInsertionLoss(int row) {
		trace.methodeCall();
		if (row == -1) {
			model.calculate(model.getEffectiveParameterValues(), view.filtertablePanel.getSelectedRow(),
					view.filtertablePanel.getSelectedRowVisibility());
		} else {
			model.calculate(model.getEffectiveParameterValues(), row,
					view.filtertablePanel.getSelectedRowVisibility());
		}

	}

	/**
	 * deletes the claculated values for a specific value
	 * 
	 * @param selectedRow row to remove
	 */
	public void deleteRowInCalculationData(int selectedRow) {
		trace.methodeCall();
		model.deleteCalculation(selectedRow);
	}
	
	/**
	 * Get the selected row in the filter tabel panel
	 * @return selcted row in filter tabel panel
	 */
	public int getSelectedRow() {
		return view.filtertablePanel.getSelectedRow();
	}
	
	/**
	 * update the input panel with notifyObserver
	 */
	public void updateInputPanel(){
		model.notifyObservers();
	}
}
