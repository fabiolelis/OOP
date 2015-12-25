//Created by Fabio Lelis

package ie.gmit.sw;

/*
 * QuadGramMap class
 * Represents the 4gram.txt in a ConcurrentHashMap 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class QuadGramMap {
	public static int GRAM_SIZE = 4;
	public static String FILE_NAME = "4grams.txt";
	
	private ConcurrentHashMap<String, Double> content = new ConcurrentHashMap<String, Double>();

	public QuadGramMap() {
		super();
	}

	public QuadGramMap(ConcurrentHashMap<String, Double> content) {
		super();
		this.content = content;
	}

	public ConcurrentHashMap<String, Double> getContent() {
		return content;
	}

	public void setContent(ConcurrentHashMap<String, Double> content) {
		this.content = content;
	}
	
	public boolean importContent(){
		try {
			FileReader in = new FileReader(FILE_NAME);
			BufferedReader reader = new BufferedReader(in);
			String line = "";
			while((line = reader.readLine()) != null) {
				content.put(line.split(" ")[0], Double.valueOf(line.split(" ")[1]));
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	
	
}
