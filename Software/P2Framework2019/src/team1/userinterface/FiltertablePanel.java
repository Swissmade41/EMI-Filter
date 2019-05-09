package team1.userinterface;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import team1.util.TraceV4;

/**
 * In the filter table panel the data of the filters are saved and managed
 */
public class FiltertablePanel extends JPanel implements TableModelListener, ListSelectionListener{
	private static final long serialVersionUID = 1L;
	TraceV4 trace = new TraceV4(this);
	private Controller controller;
	
	private String[] columnNames = {"Enable", "Filtername" };
	private Object[][] data = {{true, "Filter 1"}};

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model);
    
    private static double[][] d_effectiveParameterValues = new double[100][14];
	private static double[][] d_UserInputParameterValues = new double[100][14];
	private static int[][] s32_SliderPosition = new int[100][14];
	
	StorageManager storageManager =  new StorageManager();
	
	/**
	 * Build the filter table
	 * 
	 * @param controller
	 * 		Controller object
	 */
    public FiltertablePanel(Controller controller) {
		super(new GridLayout(1, 0));
		trace.constructorCall();	
		this.controller = controller;
		
		//Set Checkbox in first Column
		TableColumn tc = table.getColumnModel().getColumn(0);   
	    tc.setCellEditor(table.getDefaultEditor(Boolean.class));   
	    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
	    	
		table.setPreferredScrollableViewportSize(new Dimension(200, 250));
		table.setFillsViewportHeight(true);
		table.getColumnModel().getColumn(0).setMaxWidth(45);
		setRowSelection();
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);

		model.addTableModelListener(this);		
		table.getSelectionModel().addListSelectionListener(this);
	}
	
	/**
	 * Getter of the effective parameter values
	 * @return
	 * 		effective parameter values
	 */
	public double[][] getEffectiveParameterValues(){
		return d_effectiveParameterValues;
	}
	
	/**
	 * Getter of the effective parameter values
	 * @return
	 * 		user input parameter values
	 */
	public double[][] getUserInputParameterValues(){
		return d_UserInputParameterValues;
	}

	/**
	 * Getter of the set visibility from the selected row 
	 * @return
	 * 		1 = visible 0 = invisible
	 */
	public boolean getSelectedRowVisibility() {
		if(table.getValueAt(table.getSelectedRow(), 0).toString()=="true") 
			return true;
		else 
			return false;
	}
	
	/**
	 * Getter of the selected row
	 * @return
	 * 		selected row
	 */		
	public int getSelectedRow() {
		return table.getSelectedRow();
	}
    
    /**
     * set row selection at the last entry of the filter table
     */
	private void setRowSelection() {
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}

	
	/**
	 * Getter of the row count of the filter table
	 * 
	 * @return
	 * 		row count of the filter table
	 */
	public int getRowcount() {
		return table.getRowCount();
	}
	
	/**
	 * Add a new row to the filter table
	 * 
	 * @param bool
	 * 		checkbox checked
	 * @param name
	 * 		filter name
	 */
	public void addFilter(String bool, String name) {
		model.addRow(new Object[]{(bool.equals("true")) ?true:false, name});
		setRowSelection();
	}

	
	/**
	 * Remove row from the filter table
	 */
	public void removeFilter() {
		if(model.getRowCount()>1) {
			int selectedRow = table.getSelectedRow();
			try {
				model.removeRow(selectedRow);
			} catch (ArrayIndexOutOfBoundsException e) {
				if(model.getRowCount()==0) {
					JOptionPane.showMessageDialog(null, "Empty filtertable", "Information message", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "Please select the row to removed", "Information message", JOptionPane.INFORMATION_MESSAGE);
				}
			}	
			deleteRowsInFilterData(selectedRow);
			setRowSelection();
			updateInputPanel(table.getSelectedRow());
			controller.calculateInsertionLoss();
		}
		else {
			JOptionPane.showMessageDialog(null, "Forbidden action, unable to delete last remaining filter", "Warning", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Delete row in filter the filter data
	 * 
	 * @param row
	 * 		row which should be deleted
	 */
	private void deleteRowsInFilterData(int row) {
		if(row==-1) {
			System.out.println("no row selected");
			return;
		}
		
		for (int n = row; n < d_effectiveParameterValues.length-row-1; n++) {
			for (int m = 0; m < d_effectiveParameterValues[0].length; m++) {
				d_effectiveParameterValues[n][m]=d_effectiveParameterValues[n+1][m];
				d_UserInputParameterValues[n][m]=d_UserInputParameterValues[n+1][m];
			}
		}		
	}
	
	/**
	 * add rows to the filter data
	 * 
	 * @param rowCount
	 * 		row count of the filter table
	 * @param effectiveValue
	 * 		effective value which should be added to the filter table
	 * @param userInputValue
	 * 		user input value which should be added to the filter table
	 */
	private void addRowsToFilterData(int rowCount, double[][] effectiveValue, double[][] userInputValue) {
		for (int n = 0; n < d_effectiveParameterValues.length-rowCount-1; n++) {
			for (int m = 0; m < d_effectiveParameterValues[0].length; m++) {
				d_effectiveParameterValues[n+rowCount][m]=effectiveValue[n][m];
				d_UserInputParameterValues[n+rowCount][m]=userInputValue[n][m];
			}
		}
		
	}
	
	/**
	 * Update the effective parameter data the selected row
	 * 
	 * @param values
	 * 		data of the row
	 */
	public void updateEffectiveParameterValues(double[] values) {
		for (int i = 0; i < values.length; i++) {
			d_effectiveParameterValues[table.getSelectedRow()][i]=values[i];
		}
	}
	
	/**
	 * Update the user input parameter data the selected row
	 * 
	 * @param values
	 * 		data of the row
	 */
	public void updateUserInputParameterValues(double[] values) {
		for (int i = 0; i < values.length; i++) {
			d_UserInputParameterValues[table.getSelectedRow()][i]=values[i];
		}
	}

	/**
	 * Forward the information from the filter table to save them in a text file
	 */
	public void saveFiltertable() {			
		String s_filtertable ="";
		for (int i = 0; i < table.getRowCount(); i++) {
			s_filtertable+= table.getValueAt(i, 0).toString() +","+table.getValueAt(i, 1).toString()+",";
		}
		s_filtertable = s_filtertable.substring(0, s_filtertable.length()-1) +";";	//Replace last , with ;
		
		storageManager.saveFile(d_UserInputParameterValues, d_effectiveParameterValues, s_filtertable, table.getRowCount());		
	}
	
	/**
	 * Get the information from the text file and add it to the filter table
	 */
	public void loadFiltertable() {
		try {
			storageManager.loadFile();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String[] filtertable=storageManager.getFiltertable();
		int tmpRowCount=table.getRowCount();
		for (int i = 0; i < filtertable.length; i++) {
			addFilter(filtertable[i], filtertable[i+1]);
			i++;
		}
		
		addRowsToFilterData(tmpRowCount,storageManager.getEffectiveParameterValues(),storageManager.getUserInputParameterValues());		
	}
	
	/**
	 * Update the input panel with the data of the filter table of the current selected row
	 */
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) {
			updateInputPanel(table.getSelectedRow());
		}		
	}
	
	/**
	 * Update the input panel with the data of the filter table
	 * 
	 * @param row
	 * 		row of the filter table
	 */
	private void updateInputPanel(int row) {
		double[] tmpEffectiveParameterValues = new double[d_effectiveParameterValues[0].length];
		double[] tmpUserrInputParameterValues = new double[d_UserInputParameterValues[0].length];
		for (int i = 0; i < d_effectiveParameterValues[0].length; i++) {
			tmpEffectiveParameterValues[i]=d_effectiveParameterValues[row][i];
			tmpUserrInputParameterValues[i]=d_UserInputParameterValues[row][i];
		}
	controller.updateInputPanel(tmpUserrInputParameterValues,tmpEffectiveParameterValues);
	}
	
	/**
	 * Changing the filtertable executes the calculation
	 */
	public void tableChanged(TableModelEvent e) {
		controller.calculateInsertionLoss();
	}
}
