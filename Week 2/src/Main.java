import java.io.*;
import java.util.*;


public class Main {
	
	public List<Variables> variableList = new ArrayList<Variables>(); //Creates a list of Variables objects
	public List<Commands> commandList = new ArrayList<Commands>();
	public String fileLocation;
	
	public String getFile() {
		//Get the user's file location.
		boolean complete = false;
		String tempLocation = null;
		
		while (complete == false) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Enter the file location:");
				tempLocation = br.readLine();
			} catch (IOException ioe) {
				System.out.println("Please enter a correct filepath.");
			}
			
			//Test if the file exists.
			File testFile = new File(tempLocation);
			if (testFile.exists()) {
				System.out.println("File verified");
				complete = true;
			}
			else {
				System.out.println("File doesn't exist");
			}
		}
		return tempLocation.replace("\\", "\\" + "\\");
	}
	
	public boolean checkVariableExists(String name) {
		
		for (Variables var : this.variableList) {
			if (var.name.equals(name)) {
				return true;
			}
		}
		variableList.add(new Variables(name, 0));
		return false;
		
	}
	
	public void readCommands() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(this.fileLocation));
		String line, command = null, name = null;
		while((line = br.readLine()) != null) {
			//Seperate operators and operands
			if(line.startsWith("clear")) {
				command = "clear";
				name = line.substring(6, line.indexOf(';'));
			} else if (line.startsWith("incr")) {
				command = "incr";
				name = line.substring(5, line.indexOf(';'));
			} else if (line.startsWith("decr")) {
				command = "decr";
				name = line.substring(5, line.indexOf(';'));
			} else if (line.startsWith("end")) {
				command = "end";
				name = null;
			}
			
			//Check if the variable exists, and add to the list if not, then add the command to the list.
			if (name != null) {
				this.checkVariableExists(name);
			}
			this.commandList.add(new Commands(command, name));
		}
		br.close();
	}
	
	public void executeCommands() {
		for (Commands command : this.commandList) {
			if (command.command.equals("end")) {
				System.out.println("Program has been exited. Final variable values:");
				this.printVariables();
				System.exit(0);
			}
			System.out.println(command.operand + " was " + command.command + "ed");
			for (Variables var : this.variableList) {
				if (var.name.equals(command.operand)) {
					if (command.command.equals("clear")) {
						var.clear();
					} else if (command.command.equals("incr")) {
						var.incr();
					} else if (command.command.equals("decr")) {
						var.decr();
					}
					this.printVariables();
				}
			}
		}
	}
	
	public void printVariables() {
		//Print the current state of the variables
		for (Variables var : this.variableList) {
				System.out.println(var.name + " = " + var.value);
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException{
		Main myProgram = new Main();
		
		//Get a file from the user.
		myProgram.fileLocation = myProgram.getFile();
		
		//Read from the file.
		System.out.println("Reading Commands");
		myProgram.readCommands();
		
		//Execute the commands.
		System.out.println("Executing Commands");
		myProgram.executeCommands();
	}
	
}
