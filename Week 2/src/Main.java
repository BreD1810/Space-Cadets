import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		/*
		*while name not 0 do;
		*...
		*...
		*end;
		*		
		*Read from a file, interpret the user's code.
		*
		*Probably want a list of 'Variables' that can then have their values and names stored.
		*Can then use the functions within the Variables class to manipulate them.
		*/
		
		String fileLocation = "C:\\Users\\Brad\\Desktop\\BareBones.txt";
		List<Variables> variableList = new ArrayList<Variables>(); //Creates a list of Variables objects
		
		//Get the user's file location.
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the file location:");
			fileLocation = br.readLine();
		} catch (IOException ioe) {
			System.out.println("Error getting user input");
		}
		fileLocation = fileLocation.replace("\\", "\\" + "\\"); //Has to be done due to literals in strings
		
		//Read from the file.
		try {
			BufferedReader br = new BufferedReader(new FileReader( fileLocation));
			String line;
			while((line = br.readLine()) != null) {
				//TODO: put the stuff to do for each command here.
				//variableList.add(new Variables(
				System.out.println(line);
			}
			br.close();
		} catch (IOException ioe) {
			System.out.println("File not found.");
		}
	}
	
}
