package team1.userinterface;

import team1.P2Framework2019;
import team1.util.TraceV4;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class StorageManager {


	File file;
    PrintWriter writer;
    BufferedReader reader;
	JTextArea anzeigeTextFeld;
	
    private void saveFile() {
        //Prepare browse dialog
        JFileChooser jfcSave = new JFileChooser();
       int rt = jfcSave.showSaveDialog(null);
//        fc.setFileHidingEnabled(false);
//        fc.setAcceptAllFileFilterUsed(false);
//        fc.setApproveButtonText("Open");


        //Show dialog
//        int rt = jfcSave.showOpenDialog(null); //someframe is  JFrame
        if (rt == JFileChooser.APPROVE_OPTION){
            file = jfcSave.getSelectedFile(); //Do anything u want with this file
        }

        System.out.println("write");


        try {
            writer = new PrintWriter(file.getPath()+".txt", "UTF-8");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        writer.println("The first line");
        writer.println("The second line");
        writer.close();

    }
    
    private void loadFile() throws UnsupportedEncodingException {
    	
    	anzeigeTextFeld = new JTextArea(5, 20);
//    	JScrollPane scrollPane = new JScrollPane(anzeigeTextFeld); 
    	anzeigeTextFeld.setEditable(false);

    	
        //Prepare browse dialog
        JFileChooser jfcLoad = new JFileChooser();
       int rt = jfcLoad.showOpenDialog(null);
//        fc.setFileHidingEnabled(false);
//        fc.setAcceptAllFileFilterUsed(false);
//        fc.setApproveButtonText("Open");


        //Show dialog
//        int rt = jfcSave.showOpenDialog(null); //someframe is  JFrame
        if (rt == JFileChooser.APPROVE_OPTION){
            file = jfcLoad.getSelectedFile(); //Do anything u want with this file
        }

        System.out.println("reade");


        try {
            reader = new BufferedReader(new FileReader(file));
            anzeigeTextFeld.setText("");
            String zeile;
            while ((zeile = reader.readLine()) != null){
                anzeigeTextFeld.append(zeile + "\n");
            }
            reader.close();
        } catch (IOException exe) {
            // TODO Auto-generated catch block
            System.err.println("Datenfehler: ");
         // TODO Datenfehlerausgeben
            System.exit(1);
        }



    }
	
//    public static void main(String args[]) throws IOException {
//    	StorageManager storageManager = new StorageManager();
//    	storageManager.loadFile();;
//	}
   
	
//	TraceV4 trace = new TraceV4(this);
//	private final JFileChooser jfcLoad = new JFileChooser(new File(".//"));
//	private final JFileChooser jfcSave = new JFileChooser(new File(".//"));
//	
//	private static PrintWriter ausgabeDatei;
//	
//	
//	/**
//	 * 
//	 */
//	public StorageManager() {
//	
//	}
//	
//	public static void print(String[] textWrite) throws Exception {
//		PrintWriter ausgabeDatei =null;      
//	       ausgabeDatei = new PrintWriter(new File("testout.txt"));  
//	       ausgabeDatei.write("HALLO12");                                                   
//	       		ausgabeDatei.flush();  
//	       		ausgabeDatei.close();  
//	}
//	
//	public void Load(){
//	JFileChooser chooser = new JFileChooser();
//    chooser.showOpenDialog(null);
//    
//    int returnVal = chooser.showOpenDialog(null);
//    
//    
//    if(returnVal == JFileChooser.APPROVE_OPTION)
//    {
//        System.out.println("Die zu öffnende Datei ist: " +
//              chooser.getSelectedFile().getName());
//    }
//}

    
    

//	public void Save() {
//		JFileChooser chooser = new JFileChooser();
//		chooser.showSaveDialog(null);
//    
//		if(inputVal == JFileChooser.APPROVE_OPTION){
//			System.out.println("Die zu speichernde Datei ist: " +
//              chooser.getSelectedFile().getName());
//		}
//	}


	
//	public void actionPerformed(ActionEvent e) {
//		if(e.getActionCommand().equals("Load filter profile")){
//			System.out.println("hallo");
//			int returnVal = jfcLoad.showOpenDialog(this);
//			if(returnVal == JFileChooser.APPROVE_OPTION){
//				File file = jfcLoad.getSelectedFile();
//				System.out.println(""+file.isDirectory());
//				System.out.println(""+file.getName());
//				System.out.println(""+file.getAbsolutePath());
//				System.out.println(textRead(file.getAbsolutePath())[0]);
//			}
//		}
//		if(e.getActionCommand().equals("Save filter profile")){
//			int returnVal = jfcSave.showSaveDialog(this);
//			if(returnVal == JFileChooser.APPROVE_OPTION){
//				File file = jfcLoad.getSelectedFile();
//				System.out.println(""+file.isDirectory());
//				System.out.println(""+file.getName());
//				System.out.println(""+file.getAbsolutePath());
//				System.out.println(textRead(file.getAbsolutePath())[0]);
//			}
//		}

//	}

	
}

