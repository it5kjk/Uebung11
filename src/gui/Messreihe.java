package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;

public class Messreihe extends JFrame {

	private JPanel contentPane;
	private JTextField tfEnterValue;
	private DefaultListModel<Double> valListModel = new DefaultListModel<Double>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
					Messreihe frame = new Messreihe();
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
	public Messreihe() {
		setTitle("Messreihe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTextFieldTitle = new JLabel("Messwert");
		lblTextFieldTitle.setBounds(10, 11, 70, 14);
		contentPane.add(lblTextFieldTitle);
		
		tfEnterValue = new JTextField();
		tfEnterValue.setBounds(10, 27, 119, 20);
		contentPane.add(tfEnterValue);
		tfEnterValue.setColumns(10);
		
		JButton btnAddValueToList = new JButton("\u00DCbernehmen");
		btnAddValueToList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					double value = Double.parseDouble(
						tfEnterValue.getText().replace(',', '.'));
					valListModel.addElement(value);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane,
						"Bitte einen Integer- oder Dezimalwert eingeben!");
				}
			}
		});
		btnAddValueToList.setBounds(10, 52, 119, 23);
		contentPane.add(btnAddValueToList);
		
		JList<Double> listValueList = new JList<Double>();
		listValueList.setBorder(new LineBorder(Color.gray));
		listValueList.setBounds(183, 28, 200, 168);
		listValueList.setModel(valListModel);
		contentPane.add(listValueList);
		
		JButton btnLoadFile = new JButton("Aus Datei...");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileFilter(new FileNameExtensionFilter("*.mwd", "mwd"));
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						String loadFile = fc.getSelectedFile().getAbsolutePath(); 
						BufferedReader bfR = new BufferedReader(
								new FileReader(loadFile));
						String read;
						while ((read = bfR.readLine()) != null) {
							valListModel.addElement(Double.parseDouble(read));
						}
						bfR.close();
					} catch (FileNotFoundException fnf) {
						JOptionPane.showMessageDialog(
							contentPane,"Die Datei wurde nicht gefunden!",
							"Fehler", JOptionPane.ERROR_MESSAGE);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(
						contentPane,"Fehler beim Lesen der Datei!", "Fehler",
						JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnLoadFile.setBounds(10, 138, 119, 23);
		contentPane.add(btnLoadFile);
		
		JButton btnSaveToFile = new JButton("Speichern");
		btnSaveToFile.setEnabled(false);
		btnSaveToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.setFileFilter(new FileNameExtensionFilter("*.mwd", "mwd"));
				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String saveFileName = 
						fc.getSelectedFile().getAbsolutePath();
					int index = saveFileName.indexOf('.');
					if (index >= 0) {
						saveFileName = 
							saveFileName.substring(0, index).concat(".mwd");
					} else {
						saveFileName = saveFileName.concat(".mwd");
					}
					try {
						BufferedWriter bfW = new BufferedWriter(
							new FileWriter(new File(saveFileName)));
						Object[] listContent = valListModel.toArray();
						for (Object value : listContent) {
							bfW.append(value.toString() + "\r\n");
						}
						bfW.flush();
						bfW.close();
						JOptionPane.showMessageDialog(
							contentPane, "Speichern erfolgreich.",
							"Hinweis", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(
							contentPane, "Fehler beim Schreiben der Datei",
							"Fehler",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnSaveToFile.setBounds(10, 172, 119, 23);
		contentPane.add(btnSaveToFile);
		
		JLabel lblOptions = new JLabel("Optionen");
		lblOptions.setBounds(10, 121, 60, 14);
		contentPane.add(lblOptions);
		
		JLabel lblListTitle = new JLabel("Messreihe");
		lblListTitle.setBounds(183, 11, 60, 14);
		contentPane.add(lblListTitle);
		
		valListModel.addListDataListener(new ListDataListener() {
			@Override
			public void intervalRemoved(ListDataEvent e) {
				if (valListModel.isEmpty()){
					btnSaveToFile.setEnabled(false);
				}
			}
			@Override
			public void intervalAdded(ListDataEvent e) {
				btnSaveToFile.setEnabled(true);
			}
			@Override
			public void contentsChanged(ListDataEvent e) {
				if (valListModel.isEmpty()){
					btnSaveToFile.setEnabled(false);
				}
			}
		});
	}
}
