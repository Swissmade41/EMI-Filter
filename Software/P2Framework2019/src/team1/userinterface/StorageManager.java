package team1.userinterface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;

import javax.swing.JTextArea;

import org.jfree.io.IOUtils;

public class StorageManager {

	private File file;
	private PrintWriter writer;
	
	 String[] filtertable;
	 double [][] d_effectiveParameterValues = new double[100][14];
	 double [][] d_UserInputParameterValues = new double[100][14];

    public void saveFile(double[][] d_UserInputParameterValues, double[][] d_effectiveParameterValues, String s_filtertable, int s32_count) {
    	//
    	JFileChooser jfcSave = new JFileChooser();
    	int rt = jfcSave.showSaveDialog(null);
        if (rt == JFileChooser.APPROVE_OPTION){
            file = jfcSave.getSelectedFile(); 
        }

        try {
            writer = new PrintWriter(file.getPath()+".txt", "UTF-8");
        } catch (FileNotFoundException e) {
        	System.err.println("File not found: ");
            e.printStackTrace();
            System.exit(1);
        } catch (UnsupportedEncodingException e) {
        	System.err.println("Unknown datatyp: ");
            e.printStackTrace();
            System.exit(1);
        }
        
        
        
        writer.println("Emi-Filter FHNW Gruppe1: Filtertablefile");
        writer.println("User input parameter:");
        //L0,RP,CP,LR,RW,CX1,LX1,Rx1,CX2,LX2,RX2,CY,LY,RY
        for (int i = 0; i < s32_count; i++) {
        	String  stringBuffer = "";
			for (int j = 0; j < d_UserInputParameterValues[0].length; j++) {				
				stringBuffer+=String.valueOf(d_UserInputParameterValues[i][j])+ ",";
			}
			stringBuffer=stringBuffer.substring(0, stringBuffer.length()-1) +";";
			writer.println(stringBuffer);
		}
           
        writer.println("");
        writer.println("Effective parameter value:"); 
        for (int i = 0; i < s32_count; i++) {
        	String  stringBuffer = "";
			for (int j = 0; j < d_UserInputParameterValues[0].length; j++) {				
				stringBuffer+=String.valueOf(d_effectiveParameterValues[i][j])+ ",";
			}
			stringBuffer=stringBuffer.substring(0, stringBuffer.length()-1) +";";
			writer.println(stringBuffer);
		}
        
        writer.println("");
        writer.println("Filtertable:");
        writer.println(s_filtertable); 
        
        writer.close();
    }
    
    public void loadFile() throws UnsupportedEncodingException { 	
        JFileChooser jfcLoad = new JFileChooser();
        int rt = jfcLoad.showOpenDialog(null);
        if (rt == JFileChooser.APPROVE_OPTION){
            file = jfcLoad.getSelectedFile(); 
        }

        String stringBuffer = ""; 
        try {
        	stringBuffer = new String(Files.readAllBytes(Paths.get(file.getPath())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        String[] splitfile = stringBuffer.replaceAll("Effective parameter value","").replaceAll("Filtertable","").replace("\n", "").replace("\r", "").split(":");
        
        //Separate user input parameter
        String[] userinput = splitfile[2].split(";");   
        for (int i = 0; i < userinput.length; i++) {
        	String[] userInputLines = userinput[i].split(",");
        	for (int j = 0; j < userInputLines.length; j++) {
				d_UserInputParameterValues[i][j]=Double.parseDouble(userInputLines[j]);			
			}
		}
        
        //Separate slider position
        String[] effectiveValue = splitfile[3].split(";");         
        for (int i = 0; i < effectiveValue.length; i++) {
        	String[] sliderPositionLine = effectiveValue[i].split(",");
        	for (int j = 0; j < sliderPositionLine.length; j++) {
        		d_effectiveParameterValues[i][j]=Double.parseDouble(sliderPositionLine[j]);			
			}
		}
        
        //Seperate filtertable
        filtertable = splitfile[4].replace(";", "").split(",");
    }
    
    public double[][] getUserInputParameterValues() {
    	return d_UserInputParameterValues;
    }
    
    public double[][] getEffectiveParameterValues() {
    	return d_effectiveParameterValues;
    }
    
    public String[] getFiltertable() {
    	return filtertable;
    }
}

