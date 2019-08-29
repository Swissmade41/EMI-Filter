package team1.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import team1.util.BildPanel;
import team1.util.TraceV4;

/**
 * The class MenuBar handles the save and load process and provides displaying
 * the electrical circuit
 *
 */
public class MenuBar extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);

	JMenu menu_File, menu_Help, menu_Circuit;
	JMenuItem menuItemOnTop, submenuItem;
	Controller controller;
	Image img;

	/**
	 * Constructor of the MenuBar
	 * 
	 * @param controller Controller object
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
		menu_Circuit = new JMenu("Circuits");
		menu_Circuit.setMnemonic(KeyEvent.VK_H);

		JMenuItem MenuItem_CMElectricalCircuit = new JMenuItem("CM electrical circuit", KeyEvent.VK_T);
		MenuItem_CMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
		MenuItem_CMElectricalCircuit.setActionCommand("CM electrical circuit");
		MenuItem_CMElectricalCircuit.addActionListener(this);
		menu_Circuit.add(MenuItem_CMElectricalCircuit);

		JMenuItem MenuItem_DMElectricalCircuit = new JMenuItem("DM electrical circuit", KeyEvent.VK_R);
		MenuItem_DMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		MenuItem_DMElectricalCircuit.setActionCommand("DM electrical circuit");
		MenuItem_DMElectricalCircuit.addActionListener(this);
		menu_Circuit.add(MenuItem_DMElectricalCircuit);

		// Menu about
		menu_Help = new JMenu("Help");
		menu_Help.setMnemonic(KeyEvent.VK_H);

		JMenuItem MenuItem_About = new JMenuItem("About", KeyEvent.VK_A);
		MenuItem_About.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		MenuItem_About.setActionCommand("About");
		MenuItem_About.addActionListener(this);
		menu_Help.add(MenuItem_About);

		add(menu_File);
		add(menu_Circuit);
		add(menu_Help);

	}

	/**
	 * Listener for the menu bar.
	 */
	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
		if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
		if (e.getActionCommand().equals("Save filter profile")) {
			controller.saveFile();
		}
		if (e.getActionCommand().equals("Load filter profile")) {
			controller.loadFile();
		}
		if (e.getActionCommand().equals("CM electrical circuit")) {
			MenuFrame menuFrame = new MenuFrame("CM.png", "circuit");
			menuFrame.setTitle("CM circuit");

		}
		if (e.getActionCommand().equals("DM electrical circuit")) {
			MenuFrame menuFrame = new MenuFrame("DM.png", "circuit");
			menuFrame.setTitle("DM circuit");
		}
		if (e.getActionCommand().equals("About")) {
			MenuFrame menuFrame = new MenuFrame("DM.png", "about");
			menuFrame.setTitle("About");
		}

	}

	/**
	 * Class MenuFrame provides the frame to display the electrical circuits and the
	 * about text
	 *
	 */
	class MenuFrame extends JFrame {
		private static final long serialVersionUID = 1L;
		private TraceV4 trace = new TraceV4(this);

		/**
		 * constructor of the class MenuFrame
		 * 
		 * @param name  contains the name of the image
		 * @param panel "about" or "circuit" panel
		 */

		public MenuFrame(String name, String panel) {
			trace.constructorCall();
			getContentPane().setLayout(new BorderLayout());
			if (panel.equals("circuit")) {
				BildPanel bildPanel = new BildPanel(name);
				this.setSize(bildPanel.getWidth(), bildPanel.getHeight());
				getContentPane().add(bildPanel, BorderLayout.CENTER);

			} else if (panel.equals("about")) {
				getContentPane().add(new AboutPanel(), BorderLayout.CENTER);
				this.setSize(270, 150);
			}
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setVisible(true);

		}
	}

	/**
	 * Class AboutPanel Provides the panel in the circuit menu to display the about
	 */
	class AboutPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private TraceV4 trace = new TraceV4(this);

		/**
		 * Constructor of the class AboutPanel
		 */
		public AboutPanel() {
			trace.constructorCall();
		}

		/**
		 * drawing the about text
		 */
		public void paintComponent(Graphics g) {
			trace.methodeCall();
			g.drawString("EMI-Filter Simulator", 20, 20);
			g.drawString("Version 1.0", 20, 35);
			g.drawString("FHNW Poject 2 FS2019 Group 1", 20, 50);
			g.drawString("Package Plot: http://www.jfree.org/jfreechart/", 20, 80);
		}
	}
}