package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileReader
{
	public FileReader()
	{
		// nothing needed for construction
	}
	public ArrayList<String> readFile(String name) throws IOException 
	{
		FileInputStream fis = new FileInputStream(name);
		
		// BufferedReader is constructed with an InputStreamReader
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
	 
		String line = null;
		ArrayList<String> text = new ArrayList<String>();
		// reads a line, then checks if null
		while ((line = br.readLine()) != null) 
		{ // adds if there is a line
			text.add(line);
		}
		// close the buffered reader
		br.close();
		
		return text;
	}
}