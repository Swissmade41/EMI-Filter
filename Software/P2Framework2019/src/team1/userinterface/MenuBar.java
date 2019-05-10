package team1.userinterface;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import team1.util.Observable;
import team1.util.Observer;
import team1.util.TraceV4;

/**
 * The class MenuBar handles the save and load process and provides displaying the electrical circuit
 *
 */
public class MenuBar extends JMenuBar implements Observer, ActionListener {
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);
	JMenu menu_File, menu_Window, menu_Simulation, menu_Help;
	JMenuItem menuItemOnTop, submenuItem;
	Controller controller;
	Image img;
	/**
	 * Constructor of the MenuBar
	 * @param controller
	 * 		Controller object
	 */
	public MenuBar(Controller controller) {
		trace.constructorCall();
		this.controller = controller;


		// Menu file
		menu_File = new JMenu("File");
		menu_File.setMnemonic(KeyEvent.VK_F);

		JMenuItem MenuItem_Save = new JMenuItem("Save filter profile ", KeyEvent.VK_S);
		MenuItem_Save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		MenuItem_Save.setActionCommand("Save filter profile");
		MenuItem_Save.addActionListener(this);
		menu_File.add(MenuItem_Save);

		JMenuItem MenuItem_Load = new JMenuItem("Load filter profile ", KeyEvent.VK_L);
		MenuItem_Load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		MenuItem_Load.setActionCommand("Load filter profile");
		MenuItem_Load.addActionListener(this);
		menu_File.add(MenuItem_Load);

		JMenuItem MenuItem_Exit = new JMenuItem("Exit ", KeyEvent.VK_E);
		MenuItem_Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		MenuItem_Exit.setActionCommand("Exit");
		MenuItem_Exit.addActionListener(this);
		menu_File.add(MenuItem_Exit);

		// Menu Help
		menu_Help = new JMenu("Circuits");
		menu_Help.setMnemonic(KeyEvent.VK_H);

		JMenuItem MenuItem_CMElectricalCircuit = new JMenuItem("CM electrical circuit", KeyEvent.VK_T);
		MenuItem_CMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		MenuItem_CMElectricalCircuit.setActionCommand("CM electrical circuit");
		MenuItem_CMElectricalCircuit.addActionListener(this);
		menu_Help.add(MenuItem_CMElectricalCircuit);

		JMenuItem MenuItem_DMElectricalCircuit = new JMenuItem("DM electrical circuit", KeyEvent.VK_R);
		MenuItem_DMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		MenuItem_DMElectricalCircuit.setActionCommand("DM electrical circuit");
		MenuItem_DMElectricalCircuit.addActionListener(this);
		menu_Help.add(MenuItem_DMElectricalCircuit);
		
		// Menu about
		//TODO

		add(menu_File);
		add(menu_Help);

	}

	/**
	 * Listener for the menu bar.
	 */
	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
		JFrame frame = (JFrame) getTopLevelAncestor();
		if (e.getActionCommand().equals("Exit")) {
			System.out.println("Exit");
		}
		if (e.getActionCommand().equals("Save filter profile")) {
			System.out.println("Save");
			controller.saveFile();
		}
		if (e.getActionCommand().equals("Load filter profile")) {
			System.out.println("Load");
			controller.loadFile();
		}
		if (e.getActionCommand().equals("CM electrical circuit")) {
			CircuitFrame circuitFrame = new CircuitFrame("CM.png");
			System.out.println("CM");

		}
		if (e.getActionCommand().equals("DM electrical circuit")) {
			CircuitFrame circuitFrame = new CircuitFrame("DM.png");
			System.out.println("DM");
		}
	}


	public void update(Observable observable, Object obj) {
		trace.methodeCall();

	}

	/**
	 * Class CircuitFrame provides the frame to display the electrical circuits
	 *
	 */
	class CircuitFrame extends JFrame {

		/**
		 * constructor of the class CircuitFrame
		 * @param name
		 * 		contains the name of the image
		 */	
		public CircuitFrame(String name) {
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(new CircuitPanel(name), BorderLayout.CENTER);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setSize(742, 356);
			setResizable(true);
			setVisible(true);
		}
	}

	/**
	 * Class CircuitPanel 
	 * Provides the panel in the circuit frame to display the electrical circuit
	 *
	 */
	class CircuitPanel extends JPanel {
		String title;
		
		/**
		 * Constructor of the class CircuitPanel
		 * @param title
		 * 		contains the name of the picture
		 */
		public CircuitPanel(String title) {
			this.title = title;
		}
		/**
		 * drawing the suitable circuit
		 */
		public void paintComponent(Graphics g) {
			Image img = Utility.loadResourceImage(title);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // draw the image

		}
	}
}