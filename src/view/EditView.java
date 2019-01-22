package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.text.MaskFormatter;
import javax.swing.*;
import controller.ViewManager;
import model.BankAccount;
import model.User;

@SuppressWarnings("serial")
public class EditView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton backButton;
	private JButton saveButton;
	private JLabel nameLabel;
	private JLabel bdayLabel;
	private JFormattedTextField phoneField;
	private JTextField addressField;
	private JComboBox stateBox;
	private JTextField cityField;
	private JFormattedTextField zipField;
	private JFormattedTextField pinField;
	private JFormattedTextField currentpinField;

	
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public EditView(ViewManager manager) {
		super();
		
		this.manager = manager;
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		
		this.setLayout(null);
		
		JLabel editLabel = new JLabel("Edit", SwingConstants.CENTER);
		editLabel.setBounds(140, 10, 200, 35);
		editLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		this.add(editLabel);

		nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(140, 50, 200, 35);
		this.add(nameLabel);
		
		bdayLabel = new JLabel("Birthday: ");
		bdayLabel.setBounds(140, 90, 200, 35);
		this.add(bdayLabel);
		
		JLabel phoneLabel = new JLabel("Phone #", SwingConstants.RIGHT);
		phoneLabel.setBounds(30, 130, 95, 35);
		phoneLabel.setLabelFor(phoneField);
		phoneLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		this.add(phoneLabel);
		try {
			MaskFormatter phoneFormat = new MaskFormatter("(###) ###-####");
			phoneFormat.setPlaceholderCharacter('_');
			phoneField = new JFormattedTextField(phoneFormat);
		} catch (ParseException e) {
			phoneField.setText("");
		}
		phoneField.setBounds(140, 130, 200, 35);
		this.add(phoneField);
		
		JLabel addressLabel = new JLabel("Address", SwingConstants.RIGHT);
		addressLabel.setBounds(30, 170, 95, 35);
		addressLabel.setLabelFor(addressField);
		addressLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		this.add(addressLabel);
		addressField = new JTextField("");
		addressField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (addressField.getText().length() >= 30) {
					e.consume();
				}
			}
		});
		addressField.setBounds(140, 170, 200, 35);
		this.add(addressField);
		
		cityField = new JTextField("");
		cityField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (cityField.getText().length() >= 30) {
					e.consume();
				}
			}
		});
		JLabel cityLabel = new JLabel("City", SwingConstants.RIGHT);
		cityLabel.setLabelFor(cityField);
		cityLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		cityLabel.setBounds(30, 210, 95 ,35);
		cityField.setBounds(140, 210, 150, 35);
		this.add(cityLabel);
		this.add(cityField);
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "WA", "WV", "WI", "WY"};
		stateBox = new JComboBox(states);
		stateBox.setBounds(290, 210, 50, 34);
		this.add(stateBox);
		JLabel zipLabel = new JLabel("ZIP", SwingConstants.CENTER);
		zipLabel.setBounds(345, 210, 25, 35);
		zipLabel.setLabelFor(zipField);
		zipLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		this.add(zipLabel);
		try {
			MaskFormatter zipFormat = new MaskFormatter("#####");
			zipField = new JFormattedTextField(zipFormat);
		} catch (ParseException e) {
			zipField.setText("");
		}
		zipField.setBounds(375, 210, 50, 35);
		this.add(zipLabel);
		this.add(zipField);
		
		JLabel pinLabel = new JLabel("New PIN", SwingConstants.RIGHT);
		pinLabel.setBounds(30, 250, 95, 35);
		pinLabel.setLabelFor(pinField);
		pinLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		try {
			MaskFormatter pinFormat = new MaskFormatter("####");
			pinField = new JFormattedTextField(pinFormat);
		} catch (ParseException e) {
			pinField.setText("");
		}		
		pinField.setBounds(140, 250, 200, 35);
		this.add(pinLabel); 
		this.add(pinField);
		
		JLabel currentpinLabel = new JLabel("Current PIN", SwingConstants.RIGHT);
		currentpinLabel.setBounds(30, 290, 95, 35);
		currentpinLabel.setLabelFor(currentpinField);
		currentpinLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		try {
			MaskFormatter pinFormat = new MaskFormatter("####");
			currentpinField = new JFormattedTextField(pinFormat);
		} catch (ParseException e) {
			currentpinField.setText("");
		}		
		currentpinField.setBounds(140, 290, 200, 35);
		this.add(currentpinLabel); 
		this.add(currentpinField);
		
		saveButton = new JButton("Save");
		saveButton.setFont(new Font("DialogInput", Font.BOLD, 14));
		saveButton.setBounds(30, 330, 200, 35);
		saveButton.addActionListener(this);
		this.add(saveButton);
		
		backButton = new JButton("Back");
		backButton.setFont(new Font("DialogInput", Font.BOLD, 14));
		backButton.setBounds(240, 330, 200, 35);
		backButton.addActionListener(this);
		this.add(backButton);
		
	}
	
public void updateLabels() {
		
		this.setLayout(null);
		
		nameLabel.setText("Name: " + manager.getAcc().getUser().getName());
		
		bdayLabel.setText("Birthday: " + manager.getAcc().getUser().getFormattedDob());
		
		phoneField.setText("" + manager.getAcc().getUser().getPhone());
		
		addressField.setText("" + manager.getAcc().getUser().getStreetAddress());
		
		cityField.setText("" + manager.getAcc().getUser().getCity());
		this.remove(stateBox);
		String[] states = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "WA", "WV", "WI", "WY"};
		ArrayList<String> stateList = new ArrayList<String>();
		for (String state : states) {
			stateList.add(state);
		}
		stateList.remove(stateList.indexOf(manager.getAcc().getUser().getState()));
		stateList.add(0,manager.getAcc().getUser().getState());
		stateBox = new JComboBox(stateList.toArray());
		this.add(stateBox);

		zipField.setText(manager.getAcc().getUser().getZip());
	}
	
	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		if (source.equals(backButton)) {
			manager.switchTo(ATM.INFORMATION_VIEW);
		}
		else if (source.equals(saveButton)) {
			if (Integer.parseInt(currentpinField.getText()) == manager.getAcc().getUser().getPin()) {
				if (pinField.getText().equals("")) {
					manager.updateInfo(addressField.getText(), cityField.getText(), String.valueOf(stateBox.getSelectedItem()), zipField.getText(), pinField.getText(), pinField.getText(), phoneField.getText());
				} else {
					manager.updateInfo(addressField.getText(), cityField.getText(), String.valueOf(stateBox.getSelectedItem()), zipField.getText(), currentpinField.getText(), pinField.getText(), phoneField.getText());
				}
			}
			manager.switchTo(ATM.INFORMATION_VIEW);
		}
	}
}
