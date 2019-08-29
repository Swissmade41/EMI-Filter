package team1.userinterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import team1.util.MyBorderFactory;
import team1.util.TraceV4;

/**
 * This class provides a panel with Buttons
 *
 */
public class ButtonPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);

	private Controller controller;
	private JButton btAdd = new JButton("Add");
	private JButton btRemove = new JButton("Remove");
	private JButton btLoad = new JButton("Load");
	private JButton btSave = new JButton("Save");

	/**
	 * Place the Buttons on the panel
	 * 
	 * @param controller Controller object
	 */
	public ButtonPanel(Controller controller) {
		super(null);
		trace.constructorCall();

		this.controller = controller;
		setPreferredSize(new Dimension(200, 250));
		setMinimumSize(new Dimension(200, 250));
		setBorder(MyBorderFactory.createMyBorder(""));

		add(new JLabel("Filter:")).setBounds(10, 20, 50, 20);
		add(btAdd).setBounds(10, 45, 80, 20);
		add(btRemove).setBounds(10, 70, 80, 20);
		add(new JLabel("Storage:")).setBounds(10, 100, 60, 20);
		add(btSave).setBounds(10, 125, 80, 20);
		add(btLoad).setBounds(10, 150, 80, 20);
		btAdd.addActionListener(this);
		btRemove.addActionListener(this);
		btSave.addActionListener(this);
		btLoad.addActionListener(this);
	}

	/**
	 * Get the button source and forward the event to the controller
	 */
	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
		if (e.getSource() == btAdd) {
			controller.addFilter();
		}

		if (e.getSource() == btRemove) {
			controller.removeFilter();
		}
		if (e.getSource() == btSave) {
			controller.saveFile();
		}
		if (e.getSource() == btLoad) {
			controller.loadFile();
		}
	}
}
