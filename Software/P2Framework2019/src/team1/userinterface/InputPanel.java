package team1.userinterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import team1.util.JEngineerField;
import team1.util.MyBorderFactory;
import team1.util.TraceV4;
import team1.util.EngineeringUtil;

/**
 * The input panel provides the input of the user and manage them
 */
public class InputPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);
	private Controller controller;

	static final int NUMBER_OF_PARAMETER = 14;
	private static final double DEFAULT_VALUES[] = {0.5e-3, 7.92e3, 6.46e-12, 9.2e-6, 11.3e-3, 146.7e-9, 15.2e-9, 47.4e-3, 146.7e-9, 15.2e-9, 47.4e-3, 3.85e-9, 8e-9, 246e-3};
	
	private String[] parameter = {"<html>L<sub>0</sub></html>", "<html>C<sub>X1</sub></html>", "<html>C<sub>X2</sub></html>", "<html>C<sub>Y1</sub></html>"};

	private String[][] subParameter = {
			{"<html>L<sub>0</sub></html>","<html>R<sub>p</sub></html>","<html>C<sub>p</sub></html>","<html>L<sub>r</sub></html>","<html>R<sub>w</sub></html>"}, 
			{"<html>C<sub>X1</sub></html>","<html>L<sub>X1</sub></html>","<html>R<sub>X1</sub></html>"}, 
			{"<html>C<sub>X2</sub></html>","<html>L<sub>X2</sub></html>","<html>R<sub>X2</sub></html>"},
			{"<html>C<sub>Y</sub></html>","<html>L<sub>Y</sub></html>","<html>R<sub>Y</sub></html>"}}; 
	
	private String[][] units = {{"H", "Ω", "F", "H", "Ω"},
								{"F", "H", "Ω"},
								{"F", "H", "Ω"},
								{"F", "H", "Ω"}};
		
	InputSubPanel[][] inputSubPanel = new InputSubPanel[parameter.length][subParameter[0].length];
	
	/**
	 * Build the Input panel
	 * 
	 * @param controller
	 * 		Controller object
	 */
	public InputPanel(Controller controller) {
		trace.constructorCall();
		this.controller = controller;
		setLayout(new GridBagLayout());
		setBorder(MyBorderFactory.createMyBorder(""));
		
		//create the information panel
		InformationPanel informationPanel= new InformationPanel();
		add(informationPanel,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));
		
		//create the parameter sub panels
		for (int n = 0; n < parameter.length; n++) {	
			JPanel paramter_Panel = new JPanel(new GridLayout());
			paramter_Panel.setBorder(MyBorderFactory.createMyBorder(parameter[n]));		
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m] = new InputSubPanel(this.controller, subParameter[n][m], units[n][m]); 
				paramter_Panel.add(inputSubPanel[n][m]);
			}			
			add(paramter_Panel,new GridBagConstraints(n+1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));			
		}
	}
	
	/**
	 * Inizialize the first filter
	 */
	public void initializeFirstFilter() {
		resetInputPanel();		
	}
	
	/**
	 * Convert the user input parameter values of all sub panels to one array
	 * 
	 * @return
	 * 		User input parameter value
	 */
	public double[] getUserInputParameterValues() {
		double[] d_parameterTextfielValue = new double[NUMBER_OF_PARAMETER];
		int s32_tmpCounter = 0;	
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				d_parameterTextfielValue[s32_tmpCounter]= inputSubPanel[n][m].getUserParameterValue();
				s32_tmpCounter++;
			}
		}	
		return d_parameterTextfielValue;
	}
	
	/**
	 * Convert the effective parameter values of all sub panels to one array
	 * 
	 * @return
	 * 		Effective parameter value
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

	
	/**
	 * Reset all input sub panels with default values
	 */
	public void resetInputPanel() {
		int s32_tmpCounter = 0;
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m].setUserParameterValue(DEFAULT_VALUES[s32_tmpCounter]);
				inputSubPanel[n][m].setEffectiveParameterValues(DEFAULT_VALUES[s32_tmpCounter]);
				inputSubPanel[n][m].resetSlider();
				inputSubPanel[n][m].refreshComponents(); 				
				s32_tmpCounter++;
			}
		}
	}

	/**
	 * Update all input sub panels
	 * 
	 * @param textfieldValues
	 * 		Current values of the textfields/user inputs 
	 * @param parameterValues
	 * 		Current effective parameter values
	 */		
	public void updateInputPanel(double[] textfieldValues, double[] parameterValues) {
		int s32_tmpCounter=0;
		for (int n = 0; n < parameter.length; n++) {
			for (int m = 0; m < subParameter[n].length; m++) {
				inputSubPanel[n][m].setUserParameterValue(textfieldValues[s32_tmpCounter]);
				int sliderValue=(int)(((parameterValues[s32_tmpCounter]-textfieldValues[s32_tmpCounter])*100)/textfieldValues[s32_tmpCounter]); //calculate sliderposition
				inputSubPanel[n][m].setSlider(sliderValue);
				inputSubPanel[n][m].setEffectiveParameterValues(parameterValues[s32_tmpCounter]);
				inputSubPanel[n][m].refreshComponents(); 
				s32_tmpCounter++;
			}
		}
	}
}


/**
 * Input sub panel with parameter label, text field, slider, effective value label
 * with handling the listeners
 *
 */
class InputSubPanel extends JPanel implements ChangeListener, ActionListener{
	private static final long serialVersionUID = 1L;

	private TraceV4 trace = new TraceV4(this);
	
	private Controller controller;
	
	static final int SLIDER_MIN = -30;
	static final int SLIDER_MAX = 30;
	static final int SLIDER_INIT = 0;
	
	private String unit;
	
	private JLabel label_subParameter;
	private JSlider slider_Parameter;
	private JEngineerField textfield_ParameterValues = new JEngineerField();
	
	
	private JLabel label_ParameterValue=new JLabel();
	private double d_effectiveParameterValue, d_userParameterValue;
	
	/**
	 * Builds the sub panel with parameter label, text field, slider, effective value label
	 * 
	 * @param controller
	 * 		Controller object
	 * @param subParameter
	 * 		Name of the sub parameter
	 */
	public InputSubPanel(Controller controller, String subParameter, String unit) {
		super(new GridBagLayout());
		trace.constructorCall();
		this.controller = controller;

		this.label_subParameter = new JLabel(subParameter);
		this.label_subParameter.setHorizontalAlignment(SwingConstants.CENTER);
		this.unit = unit;
		
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
		
		textfield_ParameterValues.addActionListener(this);
	}
	

	/**
	 * Setter of the effective parameter value
	 * 
	 * @param value
	 * 		New value of the user parameter value
	 */
	public void setUserParameterValue(double value) {
		d_userParameterValue= value;
	}
	
	/**
	 * Getter of the effective parameter value
	 * 
	 * @return 
	 * 		User parameter value
	 */
	public double getUserParameterValue() {
		return d_userParameterValue;
	}
	
	/**
	 * Setter of the effective parameter value
	 * 
	 * @param value
	 * 		New value of the effective parameter value
	 */
	public void setEffectiveParameterValues(double value) {
		d_effectiveParameterValue = value;
	}
	
	/**
	 * Getter of the effective parameter value
	 * 
	 * @return 
	 * 		Effective parameter value
	 */
	public double getEffectiveParameterValues() {
		return d_effectiveParameterValue;
	}
	
	/**
	 * Refresh text in textfield and the effective parameter label
	 * and update the parameter values
	 */
	public void refreshComponents() {
        	try {
    			setTextfieldValue();
    		} catch (IllegalStateException e) {
    			//catch thread conflicts caused of the documentlistener 
    			//and the set text in the textfield (Loop)
    		}
        	setLabelEffectiveParameterValue();
			controller.updateParamterValues();
	}
	
	/**
	 * set the value of the slider
	 * 
	 * @param n
	 * 		The new value of the slider
	 */
	public void setSlider(int n) {
		slider_Parameter.setValue(n);
	}
	
	/**
	 * Reset slider value to zero
	 */
	public void resetSlider() {
		setSlider(0);
	}
	
	/**
	 * set as text of the textfield the user parameter value
	 */
	private void setTextfieldValue() {
		//TODO: Textfeld micro usw anzeigen
		textfield_ParameterValues.setText(String.valueOf(d_userParameterValue));
	}
	
	/**
	 * set the label of the effectiv parameter value
	 */
	private void setLabelEffectiveParameterValue() {
		label_ParameterValue.setText((EngineeringUtil.convert(d_effectiveParameterValue, 2))+unit);
	}
	
	/**
	 * Read the text in the textfield and update the components and effective values
	 */
	private void readTextfieldInput() {
		d_userParameterValue= EngineeringUtil.parse(textfield_ParameterValues.getText(1),2);	
		refreshComponents();
		updateEffectiveValue();	
	}
	
	/**
	 * Calculate the effective value with the user input value and the slider value
	 * and update the effective parameter label
	 */
	public void updateEffectiveValue() {
		d_effectiveParameterValue = d_userParameterValue+ d_userParameterValue/100*slider_Parameter.getValue();
		setLabelEffectiveParameterValue();
	}
	
	/**
	 * Event by moving the slider; update effective value and refresh components
	 */
	public void stateChanged(ChangeEvent e) {	
		updateEffectiveValue();	
		refreshComponents();
	}	

	/**
	 * Textfield text changed; read the new content in the textfield and
	 * update the label of effective parameter value  and the  parameter values
	 */
	public void actionPerformed(ActionEvent e) {
		readTextfieldInput();
		setLabelEffectiveParameterValue();
		controller.updateParamterValues();
	}	
}

/**
 * Display the slider percent as a text in a panel
 */
class InformationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * set null layout; modifiability not needed
	 * add percent labels
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