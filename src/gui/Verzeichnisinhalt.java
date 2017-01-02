package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Verzeichnisinhalt extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 30070936672978955L;
	private JPanel contentPane;
	private JTextField tfDirectory;
	
	private JButton btnShowContent;
	private JButton btnChooseDir; 

	private JList<String> listSubdirs = new JList<String>();
	private JList<String> listFiles = new JList<String>();
	
	private DefaultListModel<String> dirModel = new DefaultListModel<String>();
	private DefaultListModel<String> fileModel = new DefaultListModel<String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Verzeichnisinhalt frame = new Verzeichnisinhalt();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Verzeichnisinhalt() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Verzeichnisinhalt");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDirectory = new JLabel("Verzeichnis");
		lblDirectory.setBounds(10, 11, 65, 14);
		contentPane.add(lblDirectory);
		
		tfDirectory = new JTextField();
		tfDirectory.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showContent();
				}
			}
		});
		tfDirectory.setBounds(10, 27, 414, 20);
		contentPane.add(tfDirectory);
		tfDirectory.setColumns(10);
		
		btnShowContent = new JButton("Inhalt anzeigen");
		btnShowContent.setBounds(10, 58, 130, 23);
		btnShowContent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showContent();
			}
		});
		contentPane.add(btnShowContent);
		
		JLabel lblSubDirs = new JLabel("Verzeichnisse");
		lblSubDirs.setBounds(10, 92, 76, 14);
		contentPane.add(lblSubDirs);
		
		JScrollPane scrollPane_Dirs = new JScrollPane();
		scrollPane_Dirs.setBounds(10, 107, 191, 143);
		contentPane.add(scrollPane_Dirs);
		
		scrollPane_Dirs.setViewportView(listSubdirs);
		
		JLabel lblFiles = new JLabel("Dateien");
		lblFiles.setBounds(233, 92, 46, 14);
		contentPane.add(lblFiles);
		
		JScrollPane scrollPane_Files = new JScrollPane();
		scrollPane_Files.setBounds(233, 107, 191, 143);
		contentPane.add(scrollPane_Files);
		
		
		scrollPane_Files.setViewportView(listFiles);
		listSubdirs.setModel(dirModel);
		listFiles.setModel(fileModel);
		
		btnChooseDir = new JButton("Ausw\u00E4hlen ...");
		btnChooseDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					tfDirectory.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		btnChooseDir.setBounds(314, 58, 110, 23);
		contentPane.add(btnChooseDir);
	}
	
	public void showContent() {
		File input_dir = new File(tfDirectory.getText());
		
		if (input_dir.exists() && input_dir.isDirectory()){
			dirModel.clear();
			fileModel.clear();
			String[] content = input_dir.list();
			for (String entry : content) {
				File f = new File(input_dir, entry);
				if (f.isDirectory()) {dirModel.addElement(entry);}
				else {fileModel.addElement(entry);}
			}
		} else {
			JOptionPane.showMessageDialog(contentPane, "Kein gültiges Verzeichnis.", "Fehler", 0);
			tfDirectory.requestFocus();
			tfDirectory.selectAll();
		}
	}
}
