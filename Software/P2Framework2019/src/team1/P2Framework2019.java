package team1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import team1.model.Model;
import team1.userinterface.Controller;
import team1.userinterface.MenuBar;

import team1.userinterface.View;
import team1.util.TraceV4;

/**
 *	Framework of the software
 */
public class P2Framework2019 extends JFrame {
	private static final long serialVersionUID = 1L;
	private TraceV4 trace = new TraceV4(this);

	private enum Mode {
		FIXED, PACKED, FIXEDRESIZABLE, PACKEDRESIZABLE
	};

	private Mode mode = Mode.PACKEDRESIZABLE;
	private int width = 1200, height = 800;
	private Model model = new Model();
	private Controller controller = new Controller(model);
	private View view = new View(controller);
	private MenuBar menuBar = new MenuBar(controller);

	public P2Framework2019() {
		trace.constructorCall();
		controller.setView(view);

		model.addObserver(view);
		trace.registerObserver(model, view);
		// kann erst wenn alles inizialisiert ist aufgerufen werden
		view.inputPanel.initializeFirstFilter();

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		setJMenuBar(menuBar);

		pack();

		switch (mode) {
		case FIXED:
			setMinimumSize(getPreferredSize());
			setSize(width, height);
			setResizable(false);
			validate();
			break;
		case FIXEDRESIZABLE:
			setMinimumSize(getPreferredSize());
			setSize(width, height);
			setResizable(true);
			validate();
			break;
		case PACKED:
			setMinimumSize(getPreferredSize());
			setResizable(false);
			break;
		case PACKEDRESIZABLE:
			setMinimumSize(getPreferredSize());
			setResizable(true);
			break;
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public static void main(String args[]) {
		TraceV4.mainCall(true, true, true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				} catch (Exception exception) {
					exception.printStackTrace();
				}
				P2Framework2019 frame = new P2Framework2019();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("EMI-Filter Simulator");
				frame.setVisible(true);
			}
		});
	}
}
