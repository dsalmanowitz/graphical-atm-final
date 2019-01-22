package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.ViewManager;
import model.BankAccount;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton logoutButton;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton transferButton;
	private JButton infoButton;
	private JLabel accountNumberLabel;
	private JLabel balanceLabel;
	private JLabel nameLabel;
	
	private long accountNumber;
	private double balance;
	private String fname;
	private String lname;
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public HomeView(ViewManager manager) {
		super();
		
		this.manager = manager;
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		
		this.setLayout(null);

		depositButton = new JButton("Deposit");
		depositButton.setBounds(140, 50, 200, 35);
		depositButton.addActionListener(this);
		this.add(depositButton);
		withdrawButton = new JButton("Withdraw");
		withdrawButton.setBounds(140, 90, 200, 35);
		withdrawButton.addActionListener(this);
		this.add(withdrawButton);
		transferButton = new JButton("Transfer");
		transferButton.setBounds(140, 130, 200, 35);
		transferButton.addActionListener(this);
		this.add(transferButton);
		infoButton = new JButton("View Info");
		infoButton.setBounds(140, 170, 200, 35);
		infoButton.addActionListener(this);
		this.add(infoButton);
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(140, 210, 200, 35);
		logoutButton.addActionListener(this);
		this.add(logoutButton);
	}
	
	public void initInfo() {
		accountNumber = manager.getAcc().getAccountNumber();
		balance = manager.getAcc().getBalance();
		fname = manager.getAcc().getUser().getFirstName().trim();
		lname = manager.getAcc().getUser().getLastName().trim();
		
		accountNumberLabel = new JLabel("Account Number: " + accountNumber);
		accountNumberLabel.setBounds(140, 250, 200, 25);
		this.add(accountNumberLabel);
		nameLabel = new JLabel("Name: " + lname + ", " + fname);
		nameLabel.setBounds(140, 290, 290, 25);
		this.add(nameLabel);
		balanceLabel = new JLabel("Current Balance: " + balance);
		balanceLabel.setBounds(140, 330, 200, 25);
		this.add(balanceLabel);
	}
	
	public void updateBalance() {
		balanceLabel.setText("Current Balance: " + manager.getAcc().getBalance());
	}
	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The HomeView class is not serializable.");
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
		if (source.equals(logoutButton)) {
			manager.logout();
			manager.switchTo(ATM.LOGIN_VIEW);
		}
		if (source.equals(depositButton)) {
			manager.switchTo(ATM.DEPOSIT_VIEW);
		}
		if (source.equals(withdrawButton)) {
			manager.switchTo(ATM.WITHDRAW_VIEW);
		}
		if (source.equals(transferButton)) {
			manager.switchTo(ATM.TRANSFER_VIEW);
		}
		if (source.equals(infoButton)) {
			manager.updateInfo();
			manager.switchTo(ATM.INFORMATION_VIEW);
		}
	}
}
