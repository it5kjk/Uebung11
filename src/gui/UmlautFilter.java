package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.scene.shape.Line;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class UmlautFilter extends JFrame {
	private JTextPane textPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UmlautFilter frame = new UmlautFilter();
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
	public UmlautFilter() {
		setTitle("Umlaut Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 0,grow");
		
		
		JButton btnOpenFile = new JButton("Open");
		btnOpenFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO open file method, load file content into text pane
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					String loadfile = fc.getSelectedFile().getAbsolutePath();
					try {
						BufferedReader bfr = new BufferedReader(
								new FileReader(loadfile));
						String content = null;
						while ((content = bfr.readLine()) != null) {
							textPane.setText(textPane.getText() + content
									+ "\r\n");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(
								null, "Error while reading file.",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		getContentPane().add(btnOpenFile, "flowx,cell 0 1,alignx center");
		
		JButton btnSaveToFile = new JButton("Save");
		btnSaveToFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
				
				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String outfile = fc.getSelectedFile().getAbsolutePath()
							+ ".txt";
					try {
						FilterWriter fw = new FilterWriter(
								new FileWriter(outfile)) {
						};
						String content = textPane.getText();
						if (content.toLowerCase().matches(".*[üäöß].*")) {
							content = content.replaceAll("\\u00fc","ue");
							content = content.replaceAll("\\u00dc","Ue");
							content = content.replaceAll("\\u00f6","oe");
							content = content.replaceAll("\\u00d6","Oe");
							content = content.replaceAll("\\u00e4","ae");
							content = content.replaceAll("\\u00c4","Ae");
							content = content.replaceAll("\u00df","ss");
						}
						System.out.println(content);
						fw.append(content);
						fw.flush();
						fw.close();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(
								null, "Error while writing data.",
								"Critical error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSaveToFile.setEnabled(false);
		getContentPane().add(btnSaveToFile, "cell 0 1");
		
		textPane = new JTextPane();
		textPane.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				if (textPane.getDocument().getLength() == 0) {
					btnSaveToFile.setEnabled(false);
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
					btnSaveToFile.setEnabled(true);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				if (textPane.getDocument().getLength() == 0) {
					btnSaveToFile.setEnabled(false);
				} else {
					btnSaveToFile.setEnabled(true);
				}
			}
		});
		scrollPane.setViewportView(textPane);
		
	}

}
