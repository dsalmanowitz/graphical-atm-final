package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;

@SuppressWarnings("serial")
public class InformationView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton backButton;
	private JButton editButton;
	private JLabel accountNumberLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel streetLabel;
	private JLabel addressLabel;
	private JLabel phoneLabel;
	private JLabel bdayLabel;
	
	
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public InformationView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initButtons();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	public void initLabels() {
		
		this.setLayout(null);
		
		JLabel InformationLabel = new JLabel("Account Information", SwingConstants.CENTER);
		InformationLabel.setBounds(140, 10, 200, 35);
		InformationLabel.setFont(new Font("DialogInput", Font.BOLD, 14));
		this.add(InformationLabel);
		
		accountNumberLabel = new JLabel("Account Number: ");
		accountNumberLabel.setBounds(140, 50, 200, 35);
		this.add(accountNumberLabel);
		firstNameLabel = new JLabel("First Name: ");
		firstNameLabel.setBounds(140, 90, 200, 35);
		this.add(firstNameLabel);
		lastNameLabel = new JLabel("Last Name: ");
		lastNameLabel.setBounds(140, 130, 200, 35);
		this.add(lastNameLabel);
		bdayLabel = new JLabel("Birthday: ");
		bdayLabel.setBounds(140, 170, 200, 35);
		this.add(bdayLabel);
		streetLabel = new JLabel("Address: ");
		streetLabel.setBounds(140, 210, 200, 35);
		this.add(streetLabel);
		addressLabel = new JLabel("");
		addressLabel.setBounds(198, 250, 200, 35);
		this.add(addressLabel);
		phoneLabel = new JLabel("Phone #: ");
		phoneLabel.setBounds(140, 290, 200, 35);
		this.add(phoneLabel);
	}
	
	public void updateLabels() {
		this.setLayout(null);
		
		accountNumberLabel = new JLabel("Account Number: " + manager.getAcc().getAccountNumber());
		accountNumberLabel.setBounds(140, 50, 200, 35);
		this.add(accountNumberLabel);
		firstNameLabel = new JLabel("First Name: " + manager.getAcc().getUser().getFirstName());
		firstNameLabel.setBounds(140, 90, 200, 35);
		this.add(firstNameLabel);
		lastNameLabel = new JLabel("Last Name: " + manager.getAcc().getUser().getLastName());
		lastNameLabel.setBounds(140, 130, 200, 35);
		this.add(lastNameLabel);
		bdayLabel = new JLabel("Birthday: " + manager.getAcc().getUser().getFormattedDob());
		bdayLabel.setBounds(140, 170, 200, 35);
		this.add(bdayLabel);
		streetLabel.setText("Address: " + manager.getAcc().getUser().getStreetAddress());
		this.add(streetLabel);
		addressLabel.setText("" + manager.getAcc().getUser().getFormattedAddress());
		this.add(addressLabel);
		phoneLabel.setText("Phone #: " + manager.getAcc().getUser().getFormattedPhone());
		this.add(phoneLabel);
	}
	
	private void initButtons() {
		
		editButton = new JButton("Edit");
		editButton.setBounds(140, 330, 200, 35);
		editButton.addActionListener(this);
		this.add(editButton);
		backButton = new JButton("Back");
		backButton.setBounds(140, 370, 200, 35);
		backButton.addActionListener(this);
		this.add(backButton);
	}
	
	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The InformationView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source.equals(editButton)) {
			manager.getInfo();
			manager.switchTo(ATM.EDIT_VIEW);
		}
		if (source.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
		}
	}
}
