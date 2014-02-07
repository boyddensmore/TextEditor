package editorInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class OpenTabs {
	private int currentIndex;
	private List<TextTab> openTxtTabs = new ArrayList<TextTab>();

	class TextTab {
		private File file;
		private Document document;
		private JEditorPane editorPane;
		private Boolean editedFlag = false;
		
		public TextTab() {
			editorPane = new JEditorPane();
			document = editorPane.getDocument();
			
			document.addDocumentListener(new documentChangeListener());
		}
		
		public TextTab(File newFile) {
			editorPane = new JEditorPane();
			document = editorPane.getDocument();
			setFile(newFile);
			readFromDisk();
			
			document.addDocumentListener(new documentChangeListener());
		}
		
		class documentChangeListener implements DocumentListener {
		    public void insertUpdate(DocumentEvent e) {
		        System.out.println("inserted into: " + e);
		        TextTab.this.setEditedFlag(true);
		    }
		    public void removeUpdate(DocumentEvent e) {
		    	System.out.println("removed from: " + e);
		    	TextTab.this.setEditedFlag(true);
		    }
		    public void changedUpdate(DocumentEvent e) {
		        //Plain text components do not fire these events
		    }
		}
		
		public Boolean getEditedFlag() {
			return editedFlag;
		}
		
		public void setEditedFlag(Boolean newFlag) {
			editedFlag = newFlag;
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
			
			System.out.println("Reading File!");
			try (BufferedReader fileReader = Files.newBufferedReader(file.toPath(), charset)) {
				String line = null;
			    while ((line = fileReader.readLine()) != null) {
			    	appendToDocument(line+"\n");
			    }
			    this.editedFlag = false;
			} catch (IOException e) {
				System.err.format("IOExceptionL %s%n", e);
				appendToDocument("There was an error opening the file!" + "\n" + "Please ensure the file you're opening is a Text file.");
			}
		}
		
		public void writeToDisk() {
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedWriter fileWriter = Files.newBufferedWriter(file.toPath(), charset)) {
				//fileWriter.write(document.getText(0, document.getLength()));
				editorPane.write(fileWriter);
				this.editedFlag = false;
				
			}catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
		}
		
		public void writeToDisk(File outputFile) {
			Charset charset = Charset.forName("US-ASCII");
			try (BufferedWriter fileWriter = Files.newBufferedWriter(outputFile.toPath(), charset)) {
				//fileWriter.write(document.getText(0, document.getLength()));
				editorPane.write(fileWriter);
				this.editedFlag = false;
				
			}catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
			
		}
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	public void setCurrentIndex(int newIndex) {
		currentIndex = newIndex;
	}
	
	public TextTab getActiveTab() {
		return openTxtTabs.get(getCurrentIndex());
	}
	
	public TextTab getPreviousTab() {
		if (getCurrentIndex() > 0) {
			return openTxtTabs.get(getCurrentIndex() - 1);
		} else {
			return openTxtTabs.get(getCurrentIndex());
		}
	}
	
	public TextTab getNextTab() {
		if (getCurrentIndex() < openTxtTabs.size()) {
			return openTxtTabs.get(getCurrentIndex() + 1);
		} else {
			return openTxtTabs.get(getCurrentIndex());
		}
	}
	
	public void closeCurrentTab() {
		openTxtTabs.remove(getActiveTab());
	}
	
	public void OpenTextTab(File file) {
		openTxtTabs.add(this.new TextTab(file));
	}
		
	public void saveTab(int tabNumber) {
		if ((tabNumber >= 0) && (tabNumber < openTxtTabs.size())) {
			openTxtTabs.get(tabNumber).writeToDisk();
		}
	}
	
	public void saveTabAs(int tabNumber, File outputFile) {
		if ((tabNumber >= 0) && (tabNumber < openTxtTabs.size())) {
			openTxtTabs.get(tabNumber).writeToDisk(outputFile);
		}
	}

	public TextTab newTab() {
		return new TextTab();
	}
	
	public int getSize() {
		return openTxtTabs.size();
	}
}
