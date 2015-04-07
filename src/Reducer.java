import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Reducer {
	// Array with paths to all files
	private String[] sourceFiles;
	private String mergedFilePath;
	private File[] files;
	private File mergedFile;
	
	public Reducer(String[] sourcesFiles, String mergedFilePath) {
		this.sourceFiles = sourcesFiles;
		this.mergedFilePath = mergedFilePath;
	}
	
	public void reduceMerge(){
		// Inisalisation for reading and writing files
		files = new File[sourceFiles.length];
		for (int i = 0; i < sourceFiles.length; i++) {
			files[i] = new File(sourceFiles[i]);
		}
		mergedFile = new File(mergedFilePath);
		File pathMergedFile = mergedFile.getParentFile();
		if (!pathMergedFile.exists()){
			pathMergedFile.mkdirs(); // create parent directory and ancestors if necessary
		}
		
		// Prepare to write the mergedFile
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mergedFile), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Read all files
		for (File f : files) {
			// Prepare reader for each file
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
				// Write lines in a mergeFile
				String aLine;
				while((aLine = in.readLine()) != null) {
					out.write(aLine);
					out.newLine();
				}
				// Close reader for this file
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			// Close writer
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
