package com.titus.flavius.ReadingWriting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.location.Location;
import android.os.Environment;
import android.util.Log;

import com.titus.flavius.Contacts.ContactList;
import com.titus.flavius.MainActivity;

public class Writer
{
    public static void writeToTextFile(File file, String message)
    {
    	try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write(message);
            out.close(); fileWriter.close();
            
        } catch (IOException e) {}
    }
    public static void writeToTextFile(File root, String fileName, String message)
    {
    	if (!root.exists())
    		root.mkdirs();
    	File file = new File(root, fileName+(fileName.contains(".txt")?"":".txt"));
        writeToTextFile(file, message);
    }

    public static void writeContactList(File file, ContactList list){
        try{
            FileOutputStream fOut = new FileOutputStream(file);
            ObjectOutputStream oOS = new ObjectOutputStream(fOut);
            oOS.writeObject(list);
            oOS.close();
            fOut.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        Log.d("XC","written");
    }
}