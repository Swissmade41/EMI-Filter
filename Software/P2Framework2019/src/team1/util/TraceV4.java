package team1.util;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TraceV4 {
	private static boolean show = true; // On / Off aller Meldungen
	private static boolean showBuild = true; // Zeigt nur Attribute & Konstruktor
	private static boolean showMethod = true; // Zeigt Methodenaufrufe
	private String className;
	private String hashCode;
	private boolean showObject = false; // Zeigt alle Aufrufe eines Objekts
	private HashMap<Object, Color> eventMap = new HashMap<Object, Color>();
	private static float eventHue = 0.0f;
	private static float deltaHue = 1.0f / 16.0f;
	private static JTextPane eventTextPane = new JTextPane();
	public static JScrollPane eventScrollPane = new JScrollPane(eventTextPane);
	private static Color eventFarbe = Color.BLACK;

	// Some virtual machines may, under some circumstances, omit one or more
	// stack frames from the stack trace. In the extreme case, a virtual machine
	// that has no stack trace information concerning this thread is permitted
	// to return a zero-length array from this method.

	public TraceV4() {
	}

	public TraceV4(Object obj) {
		this.className = obj.getClass().getName();
		this.hashCode = "" + obj.hashCode();
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public TraceV4(Object obj, boolean showObject) {
		this.className = obj.getClass().getName();
		this.hashCode = "" + obj.hashCode();
		this.showObject = showObject;
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public TraceV4(String className, int hashCode) {
		this.className = className;
		this.hashCode = "" + hashCode;
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public TraceV4(String className, int hashCode, boolean showObject) {
		this.className = className;
		this.hashCode = "" + hashCode;
		this.showObject = showObject;
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public TraceV4(String className, Object obj) {
		this.className = className;
		this.hashCode = "" + obj.hashCode();
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public TraceV4(String className, Object obj, boolean showObject) {
		this.className = className;
		this.hashCode = "" + obj.hashCode();
		this.showObject = showObject;
		if (show && (showBuild || showObject)) {
			System.out.println("Attribute von " + className + "@" + hashCode + " werden initialisiert ...");
			appendToPane("Attribute von " + className + "@" + hashCode + " werden initialisiert ...", Color.BLACK);
		}
	}

	public void constructorCall() {
		if (show && (showBuild || showObject)) {
			System.out
					.println("Konstruktor " + className + "():" + className + "@" + hashCode + " wird ausgeführt ...");
			appendToPane("Konstruktor " + className + "():" + className + "@" + hashCode + " wird ausgeführt ...",
					Color.BLACK);
		}
	}

	public void constructorCall(String constructor) {
		if (show && (showBuild || showObject)) {
			System.out
					.println("Konstruktor " + constructor + ":" + className + "@" + hashCode + " wird ausgeführt ...");
			appendToPane("Konstruktor " + className + "():" + className + "@" + hashCode + " wird ausgeführt ...",
					Color.BLACK);
		}
	}

	public void eventCall() {
		String methode = getMethodName(3);
		if (!eventMap.containsKey(className + "@" + hashCode + "." + methode + "()")) {
			Color color = Color.getHSBColor(eventHue, 1.0f, 1.0f);
			eventHue += deltaHue;
			eventMap.put(className + "@" + hashCode + "." + methode + "()", color);
		}
		if (show && (showMethod || showObject)) {
			eventFarbe = eventMap.get(className + "@" + hashCode + "." + methode + "()");
			System.out.println("\nEvent: " + "\n" + "Methode " + className + "@" + hashCode + "." + methode + "()"
					+ " wird durch Ereignis ausgelöst ...");
			appendToPane("\nEvent:\n" + "Methode " + className + "@" + hashCode + "." + methode + "()"
					+ " wird durch Ereignis ausgelöst ...", eventFarbe);
		}
	}

	public void eventCall(String methode) {
		if (!eventMap.containsKey(className + "@" + hashCode + "." + methode + "()")) {
			Color color = Color.getHSBColor(eventHue, 1.0f, 1.0f);
			eventHue += deltaHue;
			eventMap.put(className + "@" + hashCode + "." + methode + "()", color);
		}
		if (show && (showMethod || showObject)) {
			eventFarbe = eventMap.get(className + "@" + hashCode + "." + methode + "()");
			System.out.println("\nEvent: " + "\n" + "Methode " + className + "@" + hashCode + "." + methode + "()"
					+ " wird durch Ereignis ausgelöst ...");
			appendToPane("\nEvent:\n" + "Methode " + className + "@" + hashCode + "." + methode + "()"
					+ " wird durch Ereignis ausgelöst ...", eventFarbe);
		}
	}

	public void methodeCall() {
		String methode = getMethodName(3);
		if (show && (showMethod || showObject)) {
			System.out.println("Methode " + className + "@" + hashCode + "." + methode + "()" + " wird ausgeführt ...");
			appendToPane("Methode " + className + "@" + hashCode + "." + methode + "()" + " wird ausgeführt ...",
					eventFarbe);
		}
	}

	public void methodeCall(String methode) {
		if (show && (showMethod || showObject)) {
			System.out.println("Methode " + className + "@" + hashCode + "." + methode + " wird ausgeführt ...");
			appendToPane("Methode " + className + "@" + hashCode + "." + methode + " wird ausgeführt ...", eventFarbe);
		}
	}

	private int paintCount = 0;

	public void paintCall() {
		String methode = getMethodName(3);
		if (paintCount == 0 || paintCount % 1 == 0) {
			if (show && (showMethod || showObject)) {
				System.out.println("Methode " + className + "@" + hashCode + "." + methode + "()" + "(paintCount: "
						+ paintCount + " ) wird ausgeführt ...");
				appendToPane("Methode " + className + "@" + hashCode + "." + methode + "()" + "(paintCount: "
						+ paintCount + " ) wird ausgeführt ...", eventFarbe);
			}
		}
		paintCount++;
	}

	public void registerObserver(Object observable, Object observer) {
		String classNameObservable = observable.getClass().getName();
		String hashCodeObservable = "" + observable.hashCode();
		String classNameObserver = observer.getClass().getName();
		String hashCodeObserver = "" + observer.hashCode();
		if (show && (showMethod || showObject)) {
			System.out.println("Objekt " + classNameObserver + "@" + hashCodeObserver + " wird als Observer von "
					+ "Objekt " + classNameObservable + "@" + hashCodeObservable + " registriert!");
		}
	}

	public static String getMethodName(int depth) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();

		// for (int i = 0; i < ste.length; i++) {
		// System.out.println(ste[i].getMethodName());
		// }

		if (ste != null)
			return ste[depth].getMethodName();

		return "VM does not provied a Stack-Trace";
	}

	public static void mainCall() {
		System.out.println("Start via main(String args[])");
	}

	public static void mainCall(boolean show, boolean showBuild, boolean showMethode) {
		new TraceV4();
		TraceV4.show = show;
		TraceV4.showBuild = showBuild;
		TraceV4.showMethod = showMethode;
		System.out.println("Start via main(String args[])");
		appendToPane("Start via main(String args[])", Color.BLACK);
	}

	private static void appendToPane(String msg, Color c) {
//		if (eventScrollPane.getParent() == null)
//			return;
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		// aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida
		// Console");
		// aset = sc.addAttribute(aset, StyleConstants.Alignment,
		// StyleConstants.ALIGN_JUSTIFIED);

		int len = eventTextPane.getDocument().getLength();
		eventTextPane.setCaretPosition(len);
		eventTextPane.setCharacterAttributes(aset, false);
		eventTextPane.replaceSelection(msg + "\n");
	}
}