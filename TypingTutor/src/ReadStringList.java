

import java.io.File;
import java.io.FileNotFoundException;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadStringList
{
	private Scanner input;
	
	//enable user to open file
	public void openFile()
	{
		try
		{
			input = new Scanner(new File("stringList.txt"));
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Error opening File.");
			System.exit(1); //terminate program
		}
	}
	//add records to file
	public Scanner getStringList()
	{
		//use end of line to separate strings
		input.useDelimiter("/n");
		
		return input;
		
	}
	
	//close file
	public void closeFile()
	{
		if (input != null)
		{
			input.close();
		}
	}
}
