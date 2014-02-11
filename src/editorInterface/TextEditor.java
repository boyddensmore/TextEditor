package editorInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class TextEditor {

	private JFrame frmTexteditor;
	//private JEditorPane defaultEditorPane = new JEditorPane();
	private OpenTabs activeTabs = new OpenTabs();
	private JTabbedPane tabbedPane;
	final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextEditor window = new TextEditor();
					window.frmTexteditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTexteditor = new JFrame();
		frmTexteditor.setTitle("TextEditor");
		frmTexteditor.setBounds(100, 100, 450, 300);
		frmTexteditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmTexteditor.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New", KeyEvent.VK_N);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newFile();
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open", KeyEvent.VK_O);
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save", KeyEvent.VK_S);
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmClose = new JMenuItem("Close", KeyEvent.VK_W);
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFile();
			}
		});
		mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnFile.add(mntmClose);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitProgram();
			}
		});
		mnFile.add(mntmExit);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(tabChangeListener);
		frmTexteditor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("New tab", null, activeTabs.newTab().getEditorPane(), null);
	}

	ChangeListener tabChangeListener = new ChangeListener() {
		public void stateChanged(ChangeEvent changeEvent) {
			JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
			int index = sourceTabbedPane.getSelectedIndex();

			if ((index >= 0) && (index <= activeTabs.getSize())) {
				activeTabs.setCurrentIndex(index);
			} else {
				activeTabs.setCurrentIndex(0);
			}
		}
	};
	
	private void openFile() {
		
		int returnVal = fileChooser.showOpenDialog(frmTexteditor);

		// Select the file to open
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//Select, open, and read file
			File file = fileChooser.getSelectedFile();
			activeTabs.OpenTextTab(file);
			
			// If there is only one tab, and that tab is New Tab, remove it.
			if ((tabbedPane.getTabCount() == 1) && (tabbedPane.getTitleAt(0) ==  "New tab")) {
				tabbedPane.remove(0);
			}
			
			// Add the new tab to the tab pane
			tabbedPane.addTab(file.getName(), null, activeTabs.getActiveTab().getEditorPane(), null);
			tabbedPane.setSelectedComponent(activeTabs.getActiveTab().getEditorPane());
			
		} else {
			System.out.println("File Open prompt cancelled by user.");
		}
		
		// Open the file
		
		
		// Read file contents into editor window
		
	}
	
	private void closeFile() {
		System.out.println("Close File");
	}
	
	private void newFile() {
		System.out.println("New File");
	}
	
	private void saveFile() {
		
		
		if ((tabbedPane.getTabCount() == 1) && (tabbedPane.getTitleAt(0) ==  "New tab")) {
			System.out.println("Selected tab is New Tab, saving as New");
			
			int returnVal = fileChooser.showSaveDialog(frmTexteditor);

			// Select the file to open
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//Select, open, and read file
				File file = fileChooser.getSelectedFile();
				
				tabbedPane.setTitleAt(0, file.getName());
				
				activeTabs.saveTabAs(0, file);
			} else {
				System.out.println("File Open prompt cancelled by user.");
			}
		} else {
			activeTabs.saveTab(activeTabs.getCurrentIndex());
		}
		
		System.out.println("Save File");
	}
	
	private void saveFileAs() {
		System.out.println("Save File As");
	}
	
	private void exitProgram() {
		System.out.println("Exit Program");
		System.exit(0);
	}
}
