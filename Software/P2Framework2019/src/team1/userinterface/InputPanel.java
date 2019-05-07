package team1.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import team1.util.MyBorderFactory;
import team1.util.TraceV4;


public class InputPanel extends JPanel{

	private TraceV4 trace = new TraceV4(this);
	private Controller controller;

	static final int NUMBER_OF_PARAMETER = 14;
	
	private String[] parameter = {"<html>L<sub>0</sub></html>", "<html>C<sub>X1</sub></html>", "<html>C<sub>X2</sub></html>", "<html>C<sub>Y1</sub></html>"};

	private String[][] subParameter = {
			{"<html>L<sub>0</sub></html>","<html>R<sub>p</sub></html>","<html>C<sub>p</sub></html>","<html>L<sub>r</sub></html>","<html>R<sub>w</sub></html>"}, 
			{"<html>C<sub>X1</sub></html>","<html>L<sub>X1</sub></html>","<html>R<sub>X1</sub></html>"}, 
			{"<html>C<sub>X2</sub></html>","<html>L<sub>X2</sub></html>","<html>R<sub>X2</sub></html>"},
			{"<html>C<sub>Y</sub></html>","<html>L<sub>Y</sub></html>","<html>R<sub>Y</sub></html>"}}; 
		
	InputSubPanel[][] inputSubPanel = new InputSubPanel[parameter.length][subParameter[0].length];
	
	/**
	 * add the information panel
	 * add all parameter panels with their subpanels
	 */
	public InputPanel(Controller controller) {
		trace.constructorCall();
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder("Slider Panel"));
		
		InformationPanel informationPanel= new InformationPanel();
		add(informationPanel,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		//create the parameterPanels
		for (int n = 0; n < parameter.length; n++) {	
			JPanel paramter_Panel = new JPanel(new GridLayout());
			paramter_Panel.setBorder(MyBorderFactory.createMyBorder(parameter[n]));
			
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m] = new InputSubPanel(this.controller, subParameter[n][m]); 
				paramter_Panel.add(inputSubPanel[n][m]);
			}
			
			add(paramter_Panel,new GridBagConstraints(n+1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));			
		}
	}
	
	/**
	 * convert value from subpanels textfields to one array
	 * return the array
	 */
	public double[] getUserInputParameterValues() {
		double[] d_parameterTextfielValue = new double[NUMBER_OF_PARAMETER];
		int s32_tmpCounter = 0;
		
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				d_parameterTextfielValue[s32_tmpCounter]= inputSubPanel[n][m].getTextfieldParameterValues();
				s32_tmpCounter++;
			}
		}
		
		return d_parameterTextfielValue;
	}

	/**
	 * convert value from subpanels parameter values to one array
	 * return the array
	 */
	public double[] getEffectiveParameterValues() {
		double[] d_parameterValue = new double[NUMBER_OF_PARAMETER];
		int s32_tmpCounter = 0;
		
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				d_parameterValue[s32_tmpCounter]= inputSubPanel[n][m].getEffectiveParameterValues();
				s32_tmpCounter++;
			}
		}
		
		return d_parameterValue;
	}

	public void resetInputPanel() {
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m].resetTextfield();
				inputSubPanel[n][m].resetSliders();
			}
		}
	}

	public void updateInputPanel(double[] textfieldValues, double[] parameterValues) {
		int tmpCounter=0;
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m].setTextfieldValue(textfieldValues[m]);
				//calculate sliderposition
				int sliderValue=(int)(((parameterValues[tmpCounter]-textfieldValues[tmpCounter])*100)/textfieldValues[tmpCounter]);
				inputSubPanel[n][m].setSlider(sliderValue);
				tmpCounter++;
			}
		}
	}
}


class InputSubPanel extends JPanel implements ChangeListener{
	private TraceV4 trace = new TraceV4(this);
	
	private Controller controller;
	
	static final int SLIDER_MIN = -30;
	static final int SLIDER_MAX = 30;
	static final int SLIDER_INIT = 0;
	
	
	private JLabel label_subParameter;
	private JSlider slider_Parameter;
	private JTextField textfield_ParameterValues = new JTextField();
	
	private JLabel label_ParameterValue=new JLabel();
	private double effectiveParameterValue;
	
	/**
	 * add Textfield
	 * add Slider
	 * add value label
	 */
	public InputSubPanel(Controller controller, String subParameter) {
		super(new GridBagLayout());
		trace.constructorCall();
		this.controller = controller;
		
		//TODO:Startwert einfügen
		textfield_ParameterValues.setText("30");
		label_ParameterValue.setText("30");
		effectiveParameterValue=30;

		this.label_subParameter = new JLabel(subParameter);
		this.label_subParameter.setHorizontalAlignment(SwingConstants.CENTER);

		slider_Parameter = new JSlider(SwingConstants.VERTICAL,SLIDER_MIN,SLIDER_MAX,SLIDER_INIT);
		
		
		slider_Parameter.setMajorTickSpacing(10);
		slider_Parameter.setMinorTickSpacing(5);
		
		slider_Parameter.setLabelTable(null);
		slider_Parameter.setPaintTicks(true);
		slider_Parameter.setPaintLabels(false);
		
		slider_Parameter.addChangeListener(this);

		add(label_subParameter, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 10, 10));
		add(textfield_ParameterValues, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(5, 5, 5, 5), 10, 10));
		add(slider_Parameter, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(5, 5, 5, 5), 10, 50));
		add(label_ParameterValue, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 10, 10));
	}

	
	/**
	 * Check the input of textfield and return the value
	 */
	public double getTextfieldParameterValues() {
		//TODO: Textfeld auf Fehleingaben prüfen!!!! 
		return Double.parseDouble(textfield_ParameterValues.getText());
	}
	
	/**
	 * return the parametervalue
	 */
	public double getEffectiveParameterValues() {
		return effectiveParameterValue;
	}
	
	/**
	 * Reset textfield to standardvalues
	 */
	public void resetTextfield() {
		//TODO: Standardwert
		setTextfieldValue(20);
	}
	
	/**
	 * Reset Sliders to standardvalues
	 */
	public void resetSliders() {
		setSlider(0);
	}
	
	public void setTextfieldValue(double value) {
		textfield_ParameterValues.setText(Double.toString(value));
	}
	
	public void setSlider(int n) {
		slider_Parameter.setValue(n);
	}

	/**
	 * calculate the parameter value with the slider percent value
	 * call the methode controller.input to forward
	 * the event to inputpanel
	 */
	public void stateChanged(ChangeEvent e) {	
		//TODO:Imput Textfield überprüfen
		//Berechnungen mit VariableLengthInstruction besser coden
		double d_inputwert;
		JSlider slider = (JSlider)e.getSource();
		d_inputwert =  Double.parseDouble(textfield_ParameterValues.getText());
		double d_slidervalue = slider.getValue();
		
		d_slidervalue= d_inputwert+d_inputwert*d_slidervalue/100;		
		effectiveParameterValue=Math.round(Math.pow(10.0, 2) * d_slidervalue) / Math.pow(10.0, 2);
		label_ParameterValue.setText(Double.toString(effectiveParameterValue));
		
		controller.updateParamterValues();		
	}
}

class InformationPanel extends JPanel {
		
	/**
	 * set nulllayout; modifiability not needed
	 * add percernt labels
	 */
	public InformationPanel() {
		setLayout(null);
		setPreferredSize(new Dimension(60, 60));
		
		int add= 38;
		int tmp_n=98;
		add(new JLabel("30%")).setBounds(30, tmp_n, 60, 25); tmp_n+=add;
		add(new JLabel("20%")).setBounds(30, tmp_n, 60, 25); tmp_n+=add;
		add(new JLabel("10%")).setBounds(30, tmp_n, 60, 25); tmp_n+=add;
		add(new JLabel("0%")).setBounds(30, tmp_n, 60, 25); tmp_n+=add;
		add(new JLabel("-10%")).setBounds(30, tmp_n, 60, 25); tmp_n+=add;
		add(new JLabel("-20%")).setBounds(30, tmp_n, 60, 25);tmp_n+=add;		
		add(new JLabel("-30%")).setBounds(30, tmp_n, 60, 25);			
	}
}