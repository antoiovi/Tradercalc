package com.antoiovi.tradecalc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
	File file;
	private String filename="tradecalc_stocks";
	
	
	ArrayList<Stock> OpenFile() {
		  // read the object from file
        // save the object to file
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(filename);
            in = new ObjectInputStream(fis);
            ArrayList<Stock>  list = (ArrayList<Stock>) in.readObject();
            in.close();
            return list;
        } catch (Exception ex) {
           // ex.printStackTrace();
            return null;
        }
      // System.out.println(p);
    }
 
	void SaveFile(List list) {
		 // List<MyBean> beans comes from somewhere earlier in your code.
		ArrayList<Stock> alist=new ArrayList(list);
        // save the object to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(filename);
            log(fos.toString());
            out = new ObjectOutputStream(fos);
            out.writeObject(alist);

            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
      
		
		
	}

	void log(String s) {
		//System.out.println("[FileManager]"+s);
	}
	
	
}
