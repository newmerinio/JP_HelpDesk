package com.Over2Cloud.Rnd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Test2 {
	public static void main(String[] args) {
		try{
		      FileOutputStream fs = new FileOutputStream("d:/TextToWord.doc");
		      OutputStreamWriter out = new OutputStreamWriter(fs); 
		      out.write("Welcome to RoseIndia!");
		      out.close();
		    }
		    catch (IOException e){
		      System.err.println(e);
		    }

	}
}
