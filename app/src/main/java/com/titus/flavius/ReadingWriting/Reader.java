package com.titus.flavius.ReadingWriting;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
}