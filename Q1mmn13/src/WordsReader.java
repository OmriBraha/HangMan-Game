import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Words Reader class
public class WordsReader {

	private ArrayList<String> data = new ArrayList<String>();

	// Constructor 
	public WordsReader() {
		this.data = readFile(); // Reads the file
	}

	// Get list of words
	public ArrayList<String> getData() {
		return data;
	}
	
	// Set list of words
	public void setData(ArrayList<String> data) {
		this.data = data;
	}
	
	// Read file method
	public ArrayList<String> readFile() {
		try {
			Scanner input = new Scanner(new File("MyFile.txt"));
			while (input.hasNext()) {
				data.add(input.next());
			}
			input.close();
		} catch (IOException e) {
			System.out.println("Error: File is not READABLE / MISSING");
		}
		return data;
	}
}
