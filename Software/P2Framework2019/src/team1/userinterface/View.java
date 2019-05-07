package team1.userinterface;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import team1.model.Model;
import team1.util.MyBorderFactory;
import team1.util.Observable;
import team1.util.Observer;
import team1.util.TraceV4;

public class View extends JPanel implements Observer {
	private TraceV4 trace = new TraceV4(this);
	private static final long serialVersionUID = 1L;
	public FiltertablePanel filtertablePanel;
	private PlotPanel CMplotPanel = new PlotPanel("CM");
	private PlotPanel DMplotPanel = new PlotPanel("DM");
	public InputPanel inputPanel;
	private ButtonPanel buttonPanel;
	private Controller controller;

	public View(Controller controller) {
		super(new GridBagLayout());
		trace.constructorCall();
		
		buttonPanel = new ButtonPanel(controller);
		filtertablePanel=new FiltertablePanel(controller);
		inputPanel = new InputPanel(controller);
		filtertablePanel.setOpaque(true);

		add(filtertablePanel, new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.PAGE_START,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		add(CMplotPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(DMplotPanel, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
		add(inputPanel, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
	}

	@Override
	public void update(Observable observable, Object obj) {
		trace.methodeCall();
		Model model = (Model) observable;
		CMplotPanel.update(observable, obj);
		DMplotPanel.update(observable, obj);
	}
}
