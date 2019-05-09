package team1.userinterface;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import team1.util.MyBorderFactory;

public class ButtonPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JButton btAdd = new JButton("Add");
	private JButton btRemove = new JButton("Remove");
	private JButton btLoad = new JButton("Load");
	private JButton btSave = new JButton("Save");

	public ButtonPanel(Controller controller) {
		super(null);
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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btAdd) {
			System.out.println("Add");
			controller.addFilter();
		}

		if (e.getSource() == btRemove) {
			System.out.println("Remove");
			controller.removeFilter();
		}
		if(e.getSource() == btSave) {
			controller.saveFile();
		}
		if(e.getSource() == btLoad) {
			controller.loadFile();
		}
	}
}
