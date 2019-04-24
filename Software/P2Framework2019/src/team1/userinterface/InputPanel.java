package team1.userinterface;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import team1.util.MyBorderFactory;
import team1.util.TraceV4;

public class InputPanel extends JPanel implements ActionListener{

	private TraceV4 trace = new TraceV4(this);
	private ArrayList<ArrayList<SliderSubPanel>> sliders = new ArrayList<ArrayList<SliderSubPanel>>();
	private Controller controller;

	private String[] parameter = {"<html>L<sub>0</sub></html>", "<html>C<sub>X1</sub></html>", "<html>C<sub>X2</sub></html>", "<html>C<sub>Y1</sub></html>"};

	private String[][] subParameter = {
			{"<html>L<sub>0</sub></html>","<html>R<sub>p</sub></html>","<html>C<sub>p</sub></html>","<html>L<sub>r</sub></html>","<html>R<sub>w</sub></html>"}, 
			{"<html>C<sub>X1</sub></html>","<html>L<sub>X1</sub></html>","<html>R<sub>X1</sub></html>"}, 
			{"<html>C<sub>X2</sub></html>","<html>L<sub>X2</sub></html>","<html>R<sub>X2</sub></html>"},
			{"<html>C<sub>Y</sub></html>","<html>L<sub>Y</sub></html>","<html>R<sub>Y</sub></html>"}}; 
		
	public InputPanel(Controller controller) {
		trace.constructorCall();
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder("Slider Panel"));
		
		for (int n = 0; n < parameter.length; n++) {
			JPanel jPanel = new JPanel(new GridLayout());
			jPanel.setBorder(MyBorderFactory.createMyBorder(parameter[n]));
			ArrayList<SliderSubPanel> sliderSubPanels = new ArrayList<SliderSubPanel>();
			
			for (int m = 0; m < subParameter[n].length; m++) {
				SliderSubPanel sliderSubPanel = new SliderSubPanel(this.controller, subParameter[n][m]); 
				sliderSubPanels.add(sliderSubPanel);
				jPanel.add(sliderSubPanel);
			}

			sliders.add(sliderSubPanels);
			add(jPanel,new GridBagConstraints(n, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(5, 5, 5, 5), 0, 0));			
		}
	}

	public void actionPerformed(ActionEvent arg0) {
	}
}


class SliderSubPanel extends JPanel implements ChangeListener, ActionListener{

	private TraceV4 trace = new TraceV4(this);
	private JLabel sliderSubtitle;
	private JSlider slider;
	private JTextField tfValue;
	private double sliderStartingValue;
	private String inputwert;
	private int value;
	private int sliderval;
	
	static final int SLIDER_MIN = 70;
	static final int SLIDER_MAX = 130;
	static final int SLIDER_INIT = 100;
	private String unit;
	private Controller controller;

	public SliderSubPanel(Controller controller, String subParameter) {
		super(new BorderLayout());
		trace.constructorCall();
		this.controller = controller;

		sliderSubtitle = new JLabel(subParameter);
		sliderSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

		slider = new JSlider(SwingConstants.VERTICAL, SLIDER_MIN,SLIDER_MAX,SLIDER_INIT);
		slider.addChangeListener(this);

		//create % values for sliders
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		for (int i = SLIDER_MIN; i <= SLIDER_MAX; i+=10) {
			labelTable.put(i, new JLabel(i-100 + "%"));
		}

		slider.setLabelTable( labelTable );
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		tfValue = new JTextField();
		
		add(sliderSubtitle,BorderLayout.NORTH);
		add(tfValue, BorderLayout.CENTER);
		add(slider, BorderLayout.SOUTH);	
	}

	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider)e.getSource();
		
		inputwert =  tfValue.getText();
		sliderval = slider.getValue();
		
		value= Integer.parseInt(inputwert)*sliderval/100;
		

		tfValue.setText(Integer.toString(value));
		
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}