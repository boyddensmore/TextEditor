package editorInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class OpenTabs {
	private int currentIndex;
	private List<TextTab> openTxtTabs = new ArrayList<TextTab>();

	class TextTab {
		private File file;
		private Document document;
		private JEditorPane editorPane;
		
		public TextTab(File newFile) {
			editorPane = new JEditorPane();
			document = editorPane.getDocument();
			setFile(newFile);
		}
		
		public File getFile(){
			return this.file;
		}
		
		private void setFile(File newFile){
			this.file = newFile;
		}
		
		public JEditorPane getEditorPane() {
			return editorPane;
		}
		
		public Document getDocument(){
			return this.document;
		}
		
		public void appendToDocument(String s) {
		   try {
		      document.insertString(document.getLength(), s, null);
		   } catch(BadLocationException exc) {
		      exc.printStackTrace();
		   }
		}
		
		public void readFromDisk() {
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedReader fileReader = Files.newBufferedReader(file.toPath(), charset)) {
				String line = null;
			    while ((line = fileReader.readLine()) != null) {
			    	appendToDocument(line+"\n");
			    }
			} catch (IOException e) {
				System.err.format("IOExceptionL %s%n", e);
				appendToDocument("There was an error opening the file!" + "\n" + "Please ensure the file you're opening is a Text file.");
			}
		}
		
		public void writeToDisk(){
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedWriter fileWriter = Files.newBufferedWriter(file.toPath(), charset)) {
				fileWriter.write(document.toString());
			}catch (IOException e) {
				System.err.format("IOExceptionL %s%n", e);
			}
			
		}
	}
		
	public void OpenTextTab(File file) {
		openTxtTabs.add(this.new TextTab(file));
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void setCurrentIndex(int newIndex) {
		currentIndex = newIndex;
	}
	
	public TextTab activeTab() {
		return openTxtTabs.get(getCurrentIndex());
	}
	
	public TextTab previousTab() {
		if (getCurrentIndex() > 0) {
			return openTxtTabs.get(getCurrentIndex() - 1);
		} else {
			return openTxtTabs.get(getCurrentIndex());
		}
	}
	
	public TextTab nextTab() {
		if (getCurrentIndex() < openTxtTabs.size()) {
			return openTxtTabs.get(getCurrentIndex() + 1);
		} else {
			return openTxtTabs.get(getCurrentIndex());
		}
	}
	
	public void closeCurrentTab() {
		openTxtTabs.remove(activeTab());
	}
	
	public int getSize() {
		return openTxtTabs.size();
	}
	
	//TODO - Add TextTab destructor handling
}
