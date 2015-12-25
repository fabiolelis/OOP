//Reutilized by Fabio Lelis

package ie.gmit.sw;

/* Interface FileInterface
 * This interface was created in order to facilitate the implementation and handling of files.
 * The program only work with text files but with this interface is easy to implement an extension
 * of these function to every single file type.
 */

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileInterface {
	
	public void open() throws FileNotFoundException;
	
	public void close() throws IOException;
	
	public void read() throws IOException;
	
	public void parse(String line);
	
	public void write() throws IOException;
	
	public String getFileContent();
}