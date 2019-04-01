package team1.userinterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;

import team1.util.TraceV4;

public class FiltertablePanel extends JPanel{
	TraceV4 trace = new TraceV4(this);
	private Controller controller;

	public FiltertablePanel(Controller controller) {
		super(new GridLayout(1, 0));
		trace.constructorCall();
		
		this.controller = controller;

		JTable table = new JTable(new MyTableModel());
		table.setPreferredScrollableViewportSize(new Dimension(200, 250));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);

		table.getColumnModel().getColumn(1).setCellEditor(new DoubleEditor(0, 100));

		add(scrollPane);
	}

	class MyTableModel extends AbstractTableModel {
		TraceV4 trace = new TraceV4(this);
		private String[] columnNames = { "Filtername", "Check"};
		private Object[][] data = { { "Filter1", false,"Smith", "Snowboarding", 5}};


		public int getColumnCount() {
			trace.methodeCall();
			return columnNames.length;
		}

		public int getRowCount() {
			trace.methodeCall();
			return data.length;
		}

		public String getColumnName(int col) {
			trace.methodeCall();
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			trace.methodeCall();
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/ editor for each
		 * cell. If we didn't implement this method, then the last column would contain
		 * text ("true"/"false"), rather than a check box.
		 */
		@Override
		public Class getColumnClass(int c) {
			trace.methodeCall();
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			trace.methodeCall();
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		public void setValueAt(Object value, int row, int col) {
			trace.methodeCall();
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}
}

/**
 * Implements a cell editor that uses a formatted text field to edit Integer
 * values.
 */
class DoubleEditor extends DefaultCellEditor {
	TraceV4 trace = new TraceV4(this);

	private JDoubleTextField ftf;

	public DoubleEditor(int min, int max) {
		super(new JDoubleTextField("", 30, false));
		trace.constructorCall();
		ftf = (JDoubleTextField) getComponent();
		ftf.setHorizontalAlignment(JDoubleTextField.TRAILING);
		ftf.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
	}

	// Override to invoke setValue on the formatted text field.
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		trace.methodeCall();
		JDoubleTextField ftf = (JDoubleTextField) super.getTableCellEditorComponent(table, value, isSelected, row,
				column);
		return ftf;
	}

	// Override to ensure that the value remains an Integer.
	public Object getCellEditorValue() {
		trace.methodeCall();
		JDoubleTextField ftf = (JDoubleTextField) getComponent();
		return new Double(Double.parseDouble(ftf.getText()));
	}

	public boolean stopCellEditing() {
		trace.methodeCall();
		return super.stopCellEditing();
	}
}
