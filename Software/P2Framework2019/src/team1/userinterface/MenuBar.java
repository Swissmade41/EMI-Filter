package team1.userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import javafx.scene.layout.BackgroundImage;
import team1.util.Observable;
import team1.util.Observer;
import team1.util.TraceV4;

public class MenuBar extends JMenuBar implements Observer, ActionListener {
	private TraceV4 trace = new TraceV4(this);
	JMenu menu_File, menu_Window, menu_Simulation, menu_Help;
	JMenuItem menuItemOnTop, submenuItem;
	Controller controller;
	Image img;
	public MenuBar(Controller controller) {
		trace.constructorCall();
		this.controller = controller;
		
		img = Utility.loadResourceImage("EMI_CM.png");
		
		//Menu file
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
		
		add(menu_File);
		
		//Menu simulation
		menu_Simulation= new JMenu("Simulation");
		menu_Simulation.setMnemonic(KeyEvent.VK_A);
		
		JMenuItem MenuItem_MonteCarlo = new JMenuItem("Monte Carlo ", KeyEvent.VK_M);
		MenuItem_MonteCarlo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		MenuItem_MonteCarlo.setActionCommand("Monte Carlo");
		MenuItem_MonteCarlo.addActionListener(this);
		menu_Simulation.add(MenuItem_MonteCarlo);
		
		add(menu_Simulation);
		
		//Menu Help
		menu_Help= new JMenu("Help");
		menu_Help.setMnemonic(KeyEvent.VK_X);
		
		JMenuItem MenuItem_CMElectricalCircuit = new JMenuItem("CM electrical circuit", KeyEvent.VK_X);
		MenuItem_CMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		MenuItem_CMElectricalCircuit.setActionCommand("CM electrical circuit");
		MenuItem_CMElectricalCircuit.addActionListener(this);
		menu_Help.add(MenuItem_CMElectricalCircuit);
		
		JMenuItem MenuItem_DMElectricalCircuit = new JMenuItem("DM electrical circuit", KeyEvent.VK_D);
		MenuItem_DMElectricalCircuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		MenuItem_DMElectricalCircuit.setActionCommand("DM electrical circuit");
		MenuItem_DMElectricalCircuit.addActionListener(this);
		menu_Help.add(MenuItem_DMElectricalCircuit);
		
		add(menu_Help);
		
	}
	

	

public  void panel() {
	
}

	
	
	public void CMfenster() {
		
//		JFrame CMf = new JFrame("CM");
//		CMf.setLayout (null);
//		CMf.setSize(400, 400);
//		CMf.setTitle("CM");
//		CMf.setDefaultCloseOperation(CMf.EXIT_ON_CLOSE);
//		CMf.setResizable(true);
//		CMf.setVisible(true);
		
		CircuitFrame circuitFrame = new CircuitFrame("CM");
		
	}
	


	
	public void DMfenster() {
		
		JFrame CMd = new JFrame("DM");
	//	CMd.setBackground();
		CMd.setLayout (null);
		CMd.setSize(400, 400);
		CMd.setTitle("DM");
		CMd.setDefaultCloseOperation(CMd.EXIT_ON_CLOSE);
		CMd.setResizable(true);
		CMd.setVisible(true);
		
		
	}

	public void actionPerformed(ActionEvent e) {
		trace.eventCall();
		JFrame frame = (JFrame) getTopLevelAncestor();
		if (e.getActionCommand().equals("Exit")) {
			System.out.println("Exit");
		}
		if (e.getActionCommand().equals("Save filter profile")) {
			System.out.println("Save");
		}
		if (e.getActionCommand().equals("Load filter profile")) {
			System.out.println("Load");
		}
	
		if (e.getActionCommand().equals("Monte Carlo")) {
			System.out.println("Monte Carlo");
		}
		if (e.getActionCommand().equals("CM electrical circuit")) {
		this.CMfenster();
	
		}
		if (e.getActionCommand().equals("DM electrical circuit")) {
		this.DMfenster();
		}
	}

	@Override
	public void update(Observable observable, Object obj) {
		trace.methodeCall();

	}
}

class CircuitFrame extends JFrame{
	
	public CircuitFrame(String name) {
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(new CircuitPanel(), BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setResizable(true);
		setVisible(true);
	}
}

class CircuitPanel extends JPanel{
	
	public CircuitPanel() {
		
	}
	
	public void paintComponent(Graphics g) {
		Image img = Utility.loadResourceImage("EMI_CM.png");
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // draw the image
		}
}






