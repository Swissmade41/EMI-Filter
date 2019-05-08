package team1.userinterface;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


import team1.model.Model;
import team1.util.TraceV4;


public class FiltertablePanel extends JPanel implements TableModelListener, ListSelectionListener{
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
	 * set Layout to Gridlayout
	 * set first column to a checkbox cell
	 * initialize JTable table and add to scrollpanel
	 * add listener to DefaultTableModel model
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
     * set row selection at the last entry
     */
	private void setRowSelection() {
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}

	
	public int getRowcount() {
		return table.getRowCount();
	}
	
	/**
	 * add new row to the table
	 */
	public void addFilter(String bool, String name) {
		model.addRow(new Object[]{(bool.equals("true")) ?true:false, name});
		setRowSelection();
	}

	
	/**
	 * remove row from the table
	 * Information message if no entry in table or no row is selected
	 */
	public void removeFilter() {
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
		deleteRowsInFilterData(selectedRow,1);
		setRowSelection();
		updateInputPanel(table.getSelectedRow());
	}

	private void deleteRowsInFilterData(int row, int difference) {
		if(row==-1) {
			System.out.println("no row selected");
			return;
		}
		
		for (int n = row; n < d_effectiveParameterValues.length-row-1; n++) {
			for (int m = 0; m < d_effectiveParameterValues[0].length; m++) {
				d_effectiveParameterValues[n][m]=d_effectiveParameterValues[n+difference][m];
				d_UserInputParameterValues[n][m]=d_UserInputParameterValues[n+difference][m];
			}
		}		
	}
	
	private void addRowsInFilterData(int rowCount, double[][] effectiveValue, double[][] userInputValue) {
		for (int n = 0; n < d_effectiveParameterValues.length-rowCount-1; n++) {
			for (int m = 0; m < d_effectiveParameterValues[0].length; m++) {
				d_effectiveParameterValues[n+rowCount][m]=effectiveValue[n][m];
				d_UserInputParameterValues[n+rowCount][m]=userInputValue[n][m];
			}
		}
		
	}
	
	public void updateEffectiveParameterValues(double[] values) {
		for (int i = 0; i < values.length; i++) {
			d_effectiveParameterValues[table.getSelectedRow()][i]=values[i];
		}
	}
	
	public void updateUserInputParameterValues(double[] values) {
		for (int i = 0; i < values.length; i++) {
			d_UserInputParameterValues[table.getSelectedRow()][i]=values[i];
		}
	}

	public void loadFiltertable() {
		//TODO: loadTextfile aufrufen und in tabelle schreiben
		try {
			storageManager.loadFile();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] filtertable=storageManager.getFiltertable();
		
		int tmpRowCount=table.getRowCount();
		for (int i = 0; i < filtertable.length; i++) {
			addFilter(filtertable[i], filtertable[i+1]);
			i++;
		}
		
		addRowsInFilterData(tmpRowCount,storageManager.getEffectiveParameterValues(),storageManager.getUserInputParameterValues());		
	}
	
	/**
	 * call method to to write a textfile and hand over the entries
	 */
	public void saveFiltertable() {			
		//filter enable and filter name
		String s_filtertable ="";
		for (int i = 0; i < table.getRowCount(); i++) {
			s_filtertable+= table.getValueAt(i, 0).toString() +","+table.getValueAt(i, 1).toString()+",";
		}
		s_filtertable = s_filtertable.substring(0, s_filtertable.length()-1) +";";	//Replace last , with ;
		
		storageManager.saveFile(d_UserInputParameterValues, d_effectiveParameterValues, s_filtertable, table.getRowCount());		
	}

	public double[][] getEffectiveParameterValues(){
		return d_effectiveParameterValues;
	}
	
	public double[][] getUserInputParameterValues(){
		return d_UserInputParameterValues;
	}
	
	public void tableChanged(TableModelEvent e) {
		//TODO: Daten weitergeben, plot anpassen
	}
	
	public void valueChanged(ListSelectionEvent e) {
		//TODO beschreiben. if abfrage das code nur einmal ausgef�hft wird
		if(e.getValueIsAdjusting()) {
			updateInputPanel(table.getSelectedRow());
		}		
	}
	
	private void updateInputPanel(int row) {
		double[] tmpEffectiveParameterValues = new double[d_effectiveParameterValues[0].length];
		double[] tmpUserrInputParameterValues = new double[d_UserInputParameterValues[0].length];
		for (int i = 0; i < d_effectiveParameterValues[0].length; i++) {
			tmpEffectiveParameterValues[i]=d_effectiveParameterValues[row][i];
			tmpUserrInputParameterValues[i]=d_UserInputParameterValues[row][i];
		}
		controller.updateInputPanel(tmpUserrInputParameterValues,tmpEffectiveParameterValues);
	}
	
}
