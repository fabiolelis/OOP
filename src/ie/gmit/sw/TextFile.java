//Modified by Fabio Lelis
package ie.gmit.sw;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextFile implements FileInterface {
	
	private String fileName;
	private FileReader fileReader;
	private BufferedReader in;
	private String filecontent;
	
	public TextFile(String fileName) {
		super();
		this.fileName = fileName;
	    filecontent = new String();
	}

	@Override
	public void open() throws FileNotFoundException {
		this.fileReader = new FileReader(fileName);
		this.in = new BufferedReader (this.fileReader);
	}

	@Override
	public void close() throws IOException {
			in.close();
	}

	@Override
	public void read() throws IOException {
		String line;
		while((line = in.readLine()) != null) {
			this.parse(line);
		}
	}

	@Override
	public void parse(String line) {
		filecontent = line;
	}

	@Override
	public void write() throws IOException {
		FileWriter fileWriter = null;
		fileWriter = new FileWriter (this.fileName);
		BufferedWriter out = new BufferedWriter(fileWriter);
		out.flush();
		out.write(filecontent);
		out.close();

	}
	
	public String getFileContent() {
		return filecontent;
	}

	public void setFileContent(String filecontent) {
		this.filecontent = filecontent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}
	

}
