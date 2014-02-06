package editorInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextEditor {

	private JFrame frmTexteditor;

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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTexteditor.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JEditorPane dtrpnEditorText = new JEditorPane();
		tabbedPane.addTab("New tab", null, dtrpnEditorText, null);
	}
	
	private void openFile() {
		System.out.println("Open File");
	}
	
	private void closeFile() {
		System.out.println("Close File");
	}
	
	private void newFile() {
		System.out.println("New File");
	}
	
	private void saveFile() {
		System.out.println("Save File");
	}
	
	private void exitProgram() {
		System.out.println("Exit Program");
	}
}
