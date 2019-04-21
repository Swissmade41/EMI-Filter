package team1.userinterface;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


import team1.model.Model;
import team1.util.TraceV4;


public class FiltertablePanel extends JPanel implements TableModelListener{
	TraceV4 trace = new TraceV4(this);
	private Controller controller;
	
	private String[] columnNames = {"Enable", "Filtername" };
	private Object[][] data = {{true, "Filter 1"}};

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model);

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
	}
	
    /**
     * set row selection at the last entry
     */
	private void setRowSelection() {
		table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
	}
	
	/**
	 * add new row to the table
	 */
	public void addFilter() {
		model.addRow(new Object[]{true, "Filter "+ (model.getRowCount()+1)});
		setRowSelection();
	}
	
	
	/**
	 * remove row from the table
	 * Information message if no entry in table or no row is selected
	 */
	public void removeFilter() {
		int row = table.getSelectedRow();
		try {
			model.removeRow(row);
		} catch (ArrayIndexOutOfBoundsException e) {
			if(model.getRowCount()==0) {
				JOptionPane.showMessageDialog(null, "Empty filtertable", "Information message", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, "Please select the row to removed", "Information message", JOptionPane.INFORMATION_MESSAGE);
			}
		}	
		setRowSelection();
	}


	/**
	 * update chart
	 */
	public void tableChanged(TableModelEvent e) {
		//TODO: Daten weitergeben, Neuberechnung ausführen
	}
	
	/**
	 * call method to to load a textfile
	 * update the filtertable
	 */
	public void loadFiltertable() {
		//TODO: loadTextfile aufrufen und in tabelle schreiben
	}
	
	/**
	 * call method to to write a textfile and hand over the entries
	 */
	public void saveFiltertable() {
		//TODO: writeTextfile aufrufen und tabelle übergeben
	}
}
