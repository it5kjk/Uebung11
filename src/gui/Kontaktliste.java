package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Kontaktliste extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326751122929983713L;
	private JPanel contentPane;
	private JTextField tfSurname;
	private JTextField tfForename;
	private JTextField tfPhone;
	private JTextField tfMail;
	
	private JButton btnCreateContact;
	private JButton btnDeleteContact;
	private JButton btnEdit;
	
	private JList<String> listContacts;
	
	private DefaultListModel<String> contactModel = new DefaultListModel<String>();
	private String filename = "." + java.io.File.separator + "contacts.dat";
	
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
					Kontaktliste frame = new Kontaktliste();
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
	public Kontaktliste() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Kontakliste");
		setBounds(100, 100, 450, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSurname = new JLabel("Nachname");
		lblSurname.setBounds(10, 10, 86, 14);
		contentPane.add(lblSurname);
		
		tfSurname = new JTextField();
		tfSurname.setBounds(10, 25, 86, 20);
		contentPane.add(tfSurname);
		tfSurname.setColumns(10);
		
		JLabel lblForename = new JLabel("Vorname");
		lblForename.setBounds(115, 10, 69, 14);
		contentPane.add(lblForename);
		
		tfForename = new JTextField();
		tfForename.setBounds(115, 25, 86, 20);
		contentPane.add(tfForename);
		tfForename.setColumns(10);
		
		JLabel lblPhone = new JLabel("Telephon");
		lblPhone.setBounds(220, 10, 69, 14);
		contentPane.add(lblPhone);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(220, 25, 130, 20);
		contentPane.add(tfPhone);
		tfPhone.setColumns(10);
		
		JLabel lblMail = new JLabel("E-Mail");
		lblMail.setBounds(10, 60, 46, 14);
		contentPane.add(lblMail);
		
		tfMail = new JTextField();
		tfMail.setBounds(10, 75, 191, 20);
		contentPane.add(tfMail);
		tfMail.setColumns(10);
		
		btnCreateContact = new JButton("\u00DCbernehmen >>");
		btnCreateContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveContact();
			}
		});
		btnCreateContact.setBounds(220, 74, 130, 23);
		contentPane.add(btnCreateContact);
		 
		JLabel lblContactList = new JLabel("Kontaktliste");
		lblContactList.setBounds(10, 110, 86, 14);
		contentPane.add(lblContactList);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 135, 414, 115);
		contentPane.add(scrollPane);
		
		listContacts = new JList<String>();
		listContacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listContacts.setModel(contactModel);
		scrollPane.setViewportView(listContacts);
		
		File importfile = new File(filename);
		BufferedReader in = null;
		if (!importfile.exists()) {
			try {
				importfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String entry;
			try {
				in = new BufferedReader(new FileReader(importfile));
				while ((entry = in.readLine()) != null) {
					contactModel.addElement(entry);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		}
		
		btnDeleteContact = new JButton("l\u00F6schen");
		btnDeleteContact.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteContact();
			}
		});
		btnDeleteContact.setBounds(7, 286, 89, 23);
		contentPane.add(btnDeleteContact);
		
		JLabel lblListOptions = new JLabel("markierten Eintrag");
		lblListOptions.setBounds(10, 261, 110, 14);
		contentPane.add(lblListOptions);
		
		btnEdit = new JButton("bearbeiten");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editContact();
			}
		});
		btnEdit.setBounds(150, 286, 110, 23);
		contentPane.add(btnEdit);
		
		JButton btnExit = new JButton("Ende");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedWriter out = null;
				try {
					out = new BufferedWriter(new FileWriter(filename));
					
					for (int i = 0; i < contactModel.size(); i++) {
						out.write(contactModel.get(i).toString());
						out.newLine();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				} finally {
					if (out != null) {
						try {
							out.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				}
				System.exit(0);
			}
		});
		btnExit.setBounds(335, 286, 89, 23);
		contentPane.add(btnExit);
	}
	
	private void saveContact() {
		try {
			String contactInfo = "";
			if (tfSurname.getText().isEmpty() || tfForename.getText().isEmpty()) {
				throw new IOException("Name unvollständig");
			}
			if (!tfSurname.getText().isEmpty() && !tfForename.getText().isEmpty() &&
					 tfPhone.getText().isEmpty() && tfMail.getText().isEmpty()) {
					throw new Exception();
			} 
			
			contactInfo = tfSurname.getText() +";" + tfForename.getText() + ";" + tfPhone.getText() + ";" + tfMail.getText();
			contactModel.addElement(contactInfo);
			tfSurname.setText("");
			tfForename.setText("");
			tfPhone.setText("");
			tfMail.setText("");
			
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(contentPane, ioe.getMessage(), "Fehler", 0);
			if (tfSurname.getText().isEmpty()) {
				if (tfSurname.getText().isEmpty()){
					tfSurname.requestFocus();
				} else {
					tfForename.requestFocus();
				}
			}
		 
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Keine Kontaktinfo angegeben.", "Fehler", 0);
			tfPhone.requestFocus();
		}
	}
	
	private void editContact() {
		if(!contactModel.isEmpty()){
			String[] temp = new String[4];
			
			temp = listContacts.getSelectedValue().split(";");
			contactModel.remove(listContacts.getSelectedIndex());
			contentPane.repaint();
			
			if (temp.length < 4) {
				tfSurname.setText(temp[0]);
				tfForename.setText(temp[1]);
				tfPhone.setText(temp[2]);
				
			} else {
				tfSurname.setText(temp[0]);
				tfForename.setText(temp[1]);
				tfPhone.setText(temp[2]);
				tfMail.setText(temp[3]);
			}
		}
	}
	
	private void deleteContact() {
		if (!contactModel.isEmpty()) {
			contactModel.remove(listContacts.getSelectedIndex());
		}
	}
}
