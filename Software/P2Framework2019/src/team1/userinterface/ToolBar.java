package team1.userinterface;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import team1.util.MyBorderFactory;
import team1.util.Observable;
import team1.util.Observer;
import team1.util.TraceV4;

public class ToolBar extends JToolBar implements Observer, ActionListener {
	private TraceV4 trace = new TraceV4(this);
	private static final long serialVersionUID = 1L;
	private JButton btOk = new JButton("", Utility.loadResourceIcon("axialis-blue/24x24/Save.png"));
	private JButton btCancel = new JButton(null, Utility.loadResourceIcon("axialis-blue/24x24/Printer.png"));
	private Controller controller;

	public ToolBar(Controller controller) {
		trace.constructorCall();
		setBorder(MyBorderFactory.createMyBorder(" ToolBar "));
		this.controller = controller;
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(btOk);
		btOk.addActionListener(this);
		add(btCancel);
		this.setFloatable(true);
	}

	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
	}

	@Override
	public void update(Observable observable, Object obj) {
		trace.methodeCall();
	}

}
