package team1.userinterface;

import team1.util.TraceV4;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

public class StorageManager {
	TraceV4 trace = new TraceV4(this);
	private PrintWriter inputVal;

	
	/**
	 * 
	 */
	public StorageManager() {
		
	}
	public static void main(String[] args) throws Exception {  
	//Data to write in File using PrintWriter       
    PrintWriter writer1 =null;      
       writer1 = new PrintWriter(new File("testout.txt"));  
       writer1.write("Like Java, Spring, Hibernate, Android, PHP etc.");                                                   
                       writer1.flush();  
       writer1.close();  
  }  
	
	public void Load(){
		JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        
        int returnVal = chooser.showOpenDialog(null);
        
        
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Die zu öffnende Datei ist: " +
                  chooser.getSelectedFile().getName());
        }
    }
	
        
        
	
	public void Save() {
		JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        
//        if(inputVal == JFileChooser.APPROVE_OPTION)
//        {
//            System.out.println("Die zu speichernde Datei ist: " +
//                  chooser.getSelectedFile().getName());
//        }
	}
	
}
