import java.io.*;
import java.util.*;


public class Main {
	
	public List<Variables> variableList = new ArrayList<Variables>(); //Creates a list of Variables objects
	public String fileLocation;
	public BufferedReader br;
	
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
	
	public void readCommands(String line) throws IOException {
		String command, name;
		if(line.startsWith("clear")) {
			command = "clear";
			name = line.substring(6, line.indexOf(';'));
			this.executeCommands(new Commands(command, name));
		} else if (line.startsWith("incr")) {
			command = "incr";
			name = line.substring(5, line.indexOf(';'));
			this.executeCommands(new Commands(command, name));
		} else if (line.startsWith("decr")) {
			command = "decr";
			name = line.substring(5, line.indexOf(';'));
			this.executeCommands(new Commands(command, name));
		}else if (line.startsWith("while")) {
			String variable, targetValue;
			variable = line.substring(6, line.indexOf(" not"));
			targetValue = line.substring(line.indexOf("not") + 4, line.indexOf("do") - 1);
			List<Commands> commandList = new ArrayList<Commands>();
			String whileLine;
	
			while (!(whileLine = this.br.readLine()).equals("end;")) {
				String whileCommand = null, whileName = null;
				if(whileLine.startsWith("clear")) {
					whileCommand = "clear";
					whileName = whileLine.substring(6, whileLine.indexOf(';'));
				} else if (whileLine.startsWith("incr")) {
					whileCommand = "incr";
					whileName = whileLine.substring(5, whileLine.indexOf(';'));
				} else if (whileLine.startsWith("decr")) {
					whileCommand = "decr";
					whileName = whileLine.substring(5, whileLine.indexOf(';'));
				}
				commandList.add(new Commands(whileCommand, whileName));
			}
			
			//Get array index for the variable.
			int indexOfVariable = 1;
			for (int i = 0; i < this.variableList.size(); i++) {
				if (this.variableList.get(i).name.equals(variable)) {
					indexOfVariable = i;
				}
			}
			
			do {
				for (Commands loopCommand : commandList) {
					this.executeCommands(loopCommand);
				}
			} while ((this.variableList.get(indexOfVariable)).value != Integer.parseInt(targetValue));
		}
	}
	
	public void executeCommands(Commands command) {
		this.checkVariableExists(command.operand);
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
		myProgram.br = new BufferedReader(new FileReader(myProgram.fileLocation));
		
		//Read and execute commands.		
		String line;
		while((line = myProgram.br.readLine()) != null) {
			myProgram.readCommands(line);
		}
		myProgram.br.close();
	}
	
}
