import java.io.*;
import java.net.*;

public class IDtoName {
	
	public String emailID;
	public URL pageUrl;
	public String name;
	
	public void getID() throws IOException { //Gets the email ID from the user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the email ID:");
		emailID = br.readLine();
	}
	
	public String getNameFromID() throws IOException { 
		//Gets the name from the webpage with URL containing the ID
		pageUrl = new URL("http://www.ecs.soton.ac.uk/people/" + emailID);
		BufferedReader br = new BufferedReader(new InputStreamReader(pageUrl.openStream()));
		String currentLine = "";
		
		for(int i = 1; i < 9; i++) { //Skip unnecessary lines.
			br.readLine();
		}
		
		currentLine = br.readLine();
		currentLine = currentLine.substring(11, currentLine.indexOf("|")-1);
		br.close();
		return currentLine;
	}
	
	public void relatedPeople() throws IOException {
		//Finds the related people to the person search for.
		System.out.println("Here are the people related to this person:");
		
		//TODO: Needs to be authenticated with login to see information.
		
		//Authenticator.setDefault (new Authenticator() {
		//    protected PasswordAuthentication getPasswordAuthentication() {
		//        return new PasswordAuthentication ("username", "password".toCharArray());
		//    }
		//});
		
		pageUrl = new URL("https://secure.ecs.soton.ac.uk/people/" + emailID);
		URLConnection con = pageUrl.openConnection();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String currentLine = "";
		
		while(!(currentLine = br.readLine()).contains("<h1>")) {
			continue;
		}
		
		currentLine = currentLine.substring(3, currentLine.indexOf("</h1>") - 1);
		System.out.println(currentLine);
	}

	public void googleSearch() throws IOException {
		//Gets the first google result for the persons name
		System.out.println("Here is the first google result for " + name + ":");
		pageUrl = new URL("https://www.google.co.uk/search?q=" + name.replace(" ", "+"));
		URLConnection con = pageUrl.openConnection();
		con.addRequestProperty("User-Agent", "Google Chrome/36");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String currentLine = "";
		
		while(!(currentLine = br.readLine()).contains("class=\"r\"")) {
			continue;
		}
		
		currentLine = currentLine.substring(currentLine.indexOf("class=\"r\""), currentLine.length()); //remove unwanted text before
		System.out.println(currentLine.substring(currentLine.indexOf("q=")+2, currentLine.indexOf("&amp;sa"))); //extract url
		
		br.close();
	}
	
	public void anagrams() throws IOException {
		//This method generates anagrams from the name found.
		System.out.println("Here are some anagrams for " + name + ":");
		
		pageUrl = new URL("http://new.wordsmith.org/anagram/anagram.cgi?anagram=" + name.replace(" ", "+") + "&t=500&a=n");
		BufferedReader br = new BufferedReader(new InputStreamReader(pageUrl.openStream()));
		String currentLine = "";
		
		for(int i = 1; i < 127; i++) { //Skip unnecessary lines.
			br.readLine();
		}
		
		//Print the strangely formatted first result 
		currentLine = br.readLine();
		System.out.println(currentLine.substring(8, currentLine.length()-4));
		
		//Print the rest
		while(!(currentLine = br.readLine()).contains("<script>")) {
			System.out.println(currentLine.replace("<br>", ""));
		}
		
		br.close();
	}
	
	public static void main(String[] args) throws IOException {
		IDtoName myProgram = new IDtoName();
		//Get the user's name from the ID
		myProgram.getID();
		myProgram.name = myProgram.getNameFromID();
		System.out.println(myProgram.name);
		
		//myProgram.relatedPeople();
		myProgram.googleSearch();
		myProgram.anagrams();
		//TODO: Add anagrams, google search of name found, etc.
	}
}
