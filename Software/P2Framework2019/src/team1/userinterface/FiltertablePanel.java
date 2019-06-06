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
public class FiltertablePanel extends JPanel implements TableModelListener, ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);

	private Controller controller;

	private String[] columnNames = { "Enable", "Filtername" };
	private Object[][] data = { { true, "Filter 1" } };

	DefaultTableModel model = new DefaultTableModel(data, columnNames);
	JTable table = new JTable(model);

	StorageManager storageManager = new StorageManager();
	
	private boolean filterRemoved=false;

	/**
	 * Build the filter table
	 * 
	 * @param controller Controller object
	 */
	public FiltertablePanel(Controller controller) {
		super(new GridLayout(1, 0));
		trace.constructorCall();

		this.controller = controller;

		// Set Checkbox in first Column
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
	 * Get visibility from the selected row
	 * 
	 * @return 1 = visible 0 = invisible
	 */
	public boolean getSelectedRowVisibility() {
		trace.methodeCall();
		if (table.getValueAt(table.getSelectedRow(), 0).toString() == "true")
			return true;
		else
			return false;
	}

	/**
	 * Get the selected row
	 * 
	 * @return selected row
	 */
	public int getSelectedRow() {
		trace.methodeCall();
		return table.getSelectedRow();
	}

	/**
	 * set row selection at the last entry of the filter table
	 */
	private void setRowSelection() {
		trace.methodeCall();
		
		table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
	}

	/**
	 * Get the row count of the filter table
	 * 
	 * @return row count of the filter table
	 */
	public int getRowcount() {
		trace.methodeCall();
		return table.getRowCount();
	}

	/**
	 * Add a new row to the filter table
	 * 
	 * @param bool checkbox checked
	 * @param name filter name
	 */
	public void addFilter(String bool, String name) {
		trace.methodeCall();
		model.addRow(new Object[] { (bool.equals("true")) ? true : false, name });
		setRowSelection();
	}

	/**
	 * Remove row from the filter table
	 */
	public void removeFilter() {
		trace.methodeCall();
		
		
		if (model.getRowCount() > 1) {
			int selectedRow = getSelectedRow();
			
			if(selectedRow+1==getRowcount()) {
				//return;
			}

			try {
				filterRemoved=true;
				model.removeRow(selectedRow);
								
			} catch (ArrayIndexOutOfBoundsException e) {
				if (model.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "Empty filtertable", "Information message",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Please select the row to removed", "Information message",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} 
			controller.deleteRowsInFilterData(selectedRow);
			controller.deleteRowInCalculationData(selectedRow);
			setRowSelection();
			controller.updateInputPanel();
			for (int filteriterator = 0; filteriterator < table.getRowCount(); filteriterator++) {
				controller.calculateInsertionLoss(filteriterator);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Forbidden action, unable to delete last remaining filter", "Warning",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Forward the information from the filter table to save them in a text file
	 */
	public void saveFiltertable() {
		trace.methodeCall();
		String s_filtertable = "";
		for (int i = 0; i < table.getRowCount(); i++) {
			s_filtertable += table.getValueAt(i, 0).toString() + "," + table.getValueAt(i, 1).toString() + ",";
		}
		s_filtertable = s_filtertable.substring(0, s_filtertable.length() - 1) + ";"; // Replace last , with ;

		storageManager.saveFile(controller.getUserInputParameterValues(), controller.getEffectiveParameterValues(), s_filtertable,
				table.getRowCount());
	}

	/**
	 * Get the information from the text file and add it to the filter table
	 */
	public void loadFiltertable() {
		trace.methodeCall();
		try {
			storageManager.loadFile();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String[] filtertable = storageManager.getFiltertable();
		int oldRowCount=table.getRowCount();
		//Max filter count: 10
		int rowCount = filtertable.length + table.getRowCount()*2;
		if(rowCount >20) {
			JOptionPane.showMessageDialog(null, "The maximal filter count is 10. The last " + (rowCount-20)/2 +" filters are not loaded.", "Warning",
					JOptionPane.INFORMATION_MESSAGE);
			rowCount=20 - table.getRowCount()*2+2;
		}
		else {
			rowCount-= table.getRowCount()*2-2;
		}
		
		for (int i = 0; i < rowCount-2; i++) {
			addFilter(filtertable[i], filtertable[i + 1]);
			i++;
		}
		controller.addRowsToFilterData(oldRowCount, storageManager.getEffectiveParameterValues(), storageManager.getUserInputParameterValues());
		
		for (int i = 0; i < 10; i++) {
			controller.calculateInsertionLoss(i);
			
		}	
		controller.updateInputPanel();
	}

	/**
	 * Update the input panel with the data of the filter table of the current
	 * selected row
	 */
	public void valueChanged(ListSelectionEvent e) {
		trace.methodeCall();		
		if (e.getValueIsAdjusting()) {
			controller.updateInputPanel();
		}
	}

	/**
	 * Changing the filtertable executes the calculation
	 */
	public void tableChanged(TableModelEvent e) {
		trace.methodeCall();
		//By removing a filter in the table this Thread (Methode: calculationInsertionLoss) want access to a row, which not longer exist
		if(filterRemoved) {
			filterRemoved=false;
			return;
		}
		controller.calculateInsertionLoss(e.getFirstRow());
	}
}
