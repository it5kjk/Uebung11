package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;

public class Verschluesselung extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7242029770256610089L;

	private JPanel contentPane;
	
	private JButton btnSaveAsFile;
	private JButton btnOpenUnencryptedFile;
	private JButton btnOpenEncryptedFile;
	private JLabel lblEingabe;
	
	private String filename = "." + java.io.File.separator + "encrypted.txt";

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
					Verschluesselung frame = new Verschluesselung();
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
	public Verschluesselung() {
		setTitle("Verschlüsselung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 22, 414, 181);
		contentPane.add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		lblEingabe = new JLabel("Eingabe");
		lblEingabe.setBounds(10, 8, 46, 14);
		contentPane.add(lblEingabe);
		
		btnSaveAsFile = new JButton("Speichern");
		btnSaveAsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EncryptionWriter out = null;
				try {
					out = new EncryptionWriter(new FileWriter(filename));
					out.write(textPane.getText());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "Fehler beim Speichern.", "Fehler" , 0);
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e3) {
							JOptionPane.showMessageDialog(contentPane, "Fehler beim Speichern", "Fehler", 0);
						}
					}
				}
			}
		});
		btnSaveAsFile.setBounds(10, 214, 89, 23);
		contentPane.add(btnSaveAsFile);
		
		btnOpenUnencryptedFile = new JButton("unverschl\u00FCsselt");
		btnOpenUnencryptedFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedReader in = null;
				try {
					in = new BufferedReader(new EncryptionReader(new FileReader(filename)));
					int c;
					StringBuffer zeile = new StringBuffer();
					while ((c= in.read()) >= 0) {
						zeile.append((char) c);
					}
					textPane.setText(zeile.toString());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "Fehler beim Lesen der Datei.", "Fehler", 0);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e3) {
							JOptionPane.showMessageDialog(contentPane, "Fehler beim Lesen der Datei.", "Fehler", 0);
						}
					}
				}
				
			}
		});
		btnOpenUnencryptedFile.setBounds(316, 214, 108, 23);
		contentPane.add(btnOpenUnencryptedFile);
		
		btnOpenEncryptedFile = new JButton("verschl\u00FCsselt");
		btnOpenEncryptedFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(filename));
					int c;
					StringBuffer inhalt = new StringBuffer();
					while ((c= in.read()) >=0) {
						inhalt.append((char) c);
					}
					textPane.setText(inhalt.toString());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, "Fehler beim Lesen der Datei.", "Fehler", 0);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e3) {
							JOptionPane.showMessageDialog(contentPane, "Fehler beim Lesen der Datei.", "Fehler", 0);
						}
					}
				}
			}
		});
		btnOpenEncryptedFile.setBounds(199, 214, 95, 23);
		contentPane.add(btnOpenEncryptedFile);
		
		JLabel lblOpen = new JLabel("\u00F6ffnen:");
		lblOpen.setBounds(153, 218, 36, 14);
		contentPane.add(lblOpen);
	}
}
