package controller;

import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JOptionPane;

import data.Database;
import model.BankAccount;
import model.User;
import view.ATM;
import view.LoginView;
import view.HomeView;
import view.InformationView;
import view.DepositView;
import view.EditView;
import view.WithdrawView;
import view.TransferView;

public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	private Database db;					// a reference to the database
	private BankAccount account;			// the user's bank account
	private BankAccount destination;		// an account to which the user can transfer funds
	
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 */
	
	public ViewManager(Container views) {
		this.views = views;
		this.db = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 */
	
	public void login(String accountNumber, char[] pin) {
		switchTo(ATM.HOME_VIEW);
		
				String userPin = new String(pin);
		
		if (accountNumber != null && userPin != null && accountNumber.length() > 0 && userPin.length() > 0) {
			account = db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
			
			if (account == null) {
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("Invalid account number and/or PIN.");
			} else {
				switchTo(ATM.HOME_VIEW);
				HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
				hv.initInfo();
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("");
			}
		}
		
	} 
	
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 */
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
	}
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			
			if (choice == 0) {
				db.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CreateAccount(User user) {
		account = new BankAccount('Y', db.getMaxAccountNumber()+1, 0.00, user);
		db.insertAccount(account);
		login(String.valueOf(account.getAccountNumber()), String.valueOf(account.getUser().getPin()).toCharArray());
	}
	
	public int deposit(double amount) {
		DepositView dv = ((DepositView) views.getComponents()[ATM.DEPOSIT_VIEW_INDEX]);
		switch (account.deposit(amount)) {
		case ATM.INVALID_AMOUNT: 
			dv.updateErrorMessage("Cannot deposit non-positive number.");
			return 0;
		case ATM.SUCCESS:
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.updateBalance();
			return 1;
		}
		return 0;
	}
	
	public int withdraw(double amount) {
		WithdrawView wv = ((WithdrawView) views.getComponents()[ATM.WITHDRAW_VIEW_INDEX]);
		switch (account.withdraw(amount)) {
		case ATM.INVALID_AMOUNT: 
			wv.updateErrorMessage("Cannot withdraw non-positive number.");
			return 0;
		case ATM.INSUFFICIENT_FUNDS:
			wv.updateErrorMessage("Insufficient funds.");
			return 0;
		case ATM.SUCCESS:
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.updateBalance();
			return 1; 
		}
		return 0;
	}
	
	public int transfer(long destination, double amount) {
		TransferView tv = ((TransferView) views.getComponents()[ATM.TRANSFER_VIEW_INDEX]);
		switch (account.transfer(db.getAccount(destination), amount)) {
		case ATM.INVALID_AMOUNT: 
			tv.updateErrorMessage("Cannot withdraw non-positive number.");
			return 0;
		case ATM.INSUFFICIENT_FUNDS:
			tv.updateErrorMessage("Insufficient funds.");
			return 0;
		case ATM.ACCOUNT_NOT_FOUND:
			tv.updateErrorMessage("Account not found.");
			return 0;
		case ATM.SUCCESS:
			HomeView hv = ((HomeView) views.getComponents()[ATM.HOME_VIEW_INDEX]);
			hv.updateBalance();
			return 1; 
		}
		return 0;
	}
	
	public void logout() {
		account = null;
	}
	
	public BankAccount getAcc() {
		return account;
	}
	
	public void getInfo() {
		EditView ev = ((EditView) views.getComponents()[ATM.EDIT_VIEW_INDEX]);
		ev.updateLabels();
	}
	
	public void updateInfo() {
		InformationView iv = ((InformationView) views.getComponents()[ATM.INFORMATION_VIEW_INDEX]);
		iv.updateLabels();
	}
	
	public void updateInfo(String street, String city, String state, String zip, String currentpin, String pin, String phone) {
		account.getUser().setStreetAddress(street);
		account.getUser().setCity(city);
		account.getUser().setState(state);
		account.getUser().setZip(zip);
		account.getUser().setPin(Integer.parseInt(currentpin), Integer.parseInt(pin));
		InformationView iv = ((InformationView) views.getComponents()[ATM.INFORMATION_VIEW_INDEX]);
		iv.updateLabels();
	}
}
