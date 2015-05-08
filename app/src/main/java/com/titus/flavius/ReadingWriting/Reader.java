package com.titus.flavius.ReadingWriting;

import android.location.Address;

import com.titus.flavius.Contacts.ContactList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader
{
    public static ArrayList<String> readFile(File file)
    {
    	ArrayList<String> returner = new ArrayList<String>();
    	
    	try{
	    	FileReader fileReader = new FileReader(file);
	    	Scanner scanner = new Scanner(file);
	    	
	    	while (scanner.hasNextLine()) 
			    	returner.add(scanner.nextLine());
			    
			scanner.close(); fileReader.close();
    	} catch (IOException e) {}
		
    	return returner;
    }

	public static ContactList readContactList(File file){
		ContactList list;
		try{
			FileInputStream fIn = new FileInputStream(file);
			ObjectInputStream oIS = new ObjectInputStream(fIn);
			list = (ContactList) oIS.readObject();
			oIS.close();
			fIn.close();
			return list;
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}
	}
}