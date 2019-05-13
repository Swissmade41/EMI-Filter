package team1.userinterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import team1.util.TraceV4;

/**
 * The storage manager save/load information to/from a text file
 */
public class StorageManager {
	private TraceV4 trace = new TraceV4(this);

	String[] filtertable;
	double[][] d_effectiveParameterValues = new double[100][14];
	double[][] d_UserInputParameterValues = new double[100][14];

	/**
	 * Constructor of the StorageManager
	 */
	public StorageManager() {
		trace.constructorCall();
	}

	/**
	 * Getter of the user input parameter from the text file
	 * 
	 * @return User input parameter
	 */
	public double[][] getUserInputParameterValues() {
		trace.methodeCall();
		return d_UserInputParameterValues;
	}

	/**
	 * Getter of the effective parameter value from the text file
	 * 
	 * @return Effective parameter value
	 */
	public double[][] getEffectiveParameterValues() {
		trace.methodeCall();
		return d_effectiveParameterValues;
	}

	/**
	 * Getter of the filter table entries
	 * 
	 * @return filter table entries as one string
	 */
	public String[] getFiltertable() {
		trace.methodeCall();
		return filtertable;
	}

	/**
	 * Save the information in a text file
	 * 
	 * @param d_UserInputParameter Values of the user input parameter, which should
	 *                             be saved
	 * @param d_effectiveParameter Values of the effective parameter, which should
	 *                             be saved
	 * @param s_filtertable        Information in the filter table, which should be
	 *                             saved
	 * @param s32_count            Filtercount
	 */
	public void saveFile(double[][] d_UserInputParameter, double[][] d_effectiveParameter, String s_filtertable,
			int s32_count) {
		trace.methodeCall();
		PrintWriter writer = GetWriterFromFileChooser();
		writeTextfile(writer, d_UserInputParameter, d_effectiveParameter, s_filtertable, s32_count);
	}

	/**
	 * Load a textfile
	 * 
	 * @throws UnsupportedEncodingException file not readable
	 */
	public void loadFile() throws UnsupportedEncodingException {
		trace.methodeCall();
		evaluateTextfile(getTextFromFile());
	}

	/**
	 * Choose the text file in the directory and create a writer
	 * 
	 * @return print writer
	 */
	private PrintWriter GetWriterFromFileChooser() {
		trace.methodeCall();
		File file = null;
		PrintWriter writer = null;

		JFileChooser jfcSave = new JFileChooser();
		jfcSave.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		jfcSave.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));

		int rt = jfcSave.showSaveDialog(null);
		if (rt == JFileChooser.APPROVE_OPTION) {
			file = jfcSave.getSelectedFile();
		}

		try {
			writer = new PrintWriter(file.getPath() + ".txt", "UTF-8");
		} catch (FileNotFoundException e) {
			System.err.println("File not found: ");
			e.printStackTrace();
			System.exit(1);
		} catch (UnsupportedEncodingException e) {
			System.err.println("Unknown datatyp: ");
			e.printStackTrace();
			System.exit(1);
		}
		return writer;
	}

	/**
	 * Choose the text file in the directory and read the text file
	 * 
	 * @return Text from text file as a string
	 */
	private String getTextFromFile() {
		trace.methodeCall();
		File file = null;

		JFileChooser jfcLoad = new JFileChooser();
		int rt = jfcLoad.showOpenDialog(null);
		if (rt == JFileChooser.APPROVE_OPTION) {
			file = jfcLoad.getSelectedFile();
		}

		String stringBuffer = "";
		try {
			stringBuffer = new String(Files.readAllBytes(Paths.get(file.getPath())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/**
	 * Write the information in a text file
	 * 
	 * @param writer               writer of the file
	 * @param d_UserInputParameter values of the user input parameter, which should
	 *                             be saved
	 * @param d_effectiveParameter values of the effective parameter, which should
	 *                             be saved
	 * @param s_filterTable        information in the filter table, which should be
	 *                             saved
	 * @param s32_count            filtercount
	 */
	private void writeTextfile(PrintWriter writer, double[][] d_UserInputParameter, double[][] d_effectiveParameter,
			String s_filterTable, int s32_count) {
		trace.methodeCall();
		// Header
		writer.println("Emi-Filter FHNW Gruppe1: Filtertablefile");
		writer.println("User input parameter:");

		// User input parameter: L0,RP,CP,LR,RW,CX1,LX1,Rx1,CX2,LX2,RX2,CY,LY,RY
		for (int i = 0; i < s32_count; i++) {
			String stringBuffer = "";
			for (int j = 0; j < d_UserInputParameter[0].length; j++) {
				stringBuffer += String.valueOf(d_UserInputParameter[i][j]) + ",";
			}
			stringBuffer = stringBuffer.substring(0, stringBuffer.length() - 1) + ";";
			writer.println(stringBuffer);
		}

		// effective parameter
		writer.println("");
		writer.println("Effective parameter value:");
		for (int i = 0; i < s32_count; i++) {
			String stringBuffer = "";
			for (int j = 0; j < d_effectiveParameter[0].length; j++) {
				stringBuffer += String.valueOf(d_effectiveParameter[i][j]) + ",";
			}
			stringBuffer = stringBuffer.substring(0, stringBuffer.length() - 1) + ";";
			writer.println(stringBuffer);
		}

		// filter table
		writer.println("");
		writer.println("Filtertable:");
		writer.println(s_filterTable);

		writer.close();
	}

	/**
	 * Evaluate the text from the text file and seperate the effective parameter
	 * value, user input parameter value and information from the filtertable
	 * 
	 * @param stringBuffer text from text file
	 */
	private void evaluateTextfile(String stringBuffer) {
		trace.methodeCall();
		String[] splitfile = stringBuffer.replaceAll("Effective parameter value", "").replaceAll("Filtertable", "")
				.replace("\n", "").replace("\r", "").split(":");

		// Separate user input parameter
		String[] userinput = splitfile[2].split(";");
		for (int i = 0; i < userinput.length; i++) {
			String[] userInputLines = userinput[i].split(",");
			for (int j = 0; j < userInputLines.length; j++) {
				d_UserInputParameterValues[i][j] = Double.parseDouble(userInputLines[j]);
			}
		}

		// Separate effective parameter value
		String[] effectiveValue = splitfile[3].split(";");
		for (int i = 0; i < effectiveValue.length; i++) {
			String[] sliderPositionLine = effectiveValue[i].split(",");
			for (int j = 0; j < sliderPositionLine.length; j++) {
				d_effectiveParameterValues[i][j] = Double.parseDouble(sliderPositionLine[j]);
			}
		}

		// Seperate filtertable
		filtertable = splitfile[4].replace(";", "").split(",");
	}
}
