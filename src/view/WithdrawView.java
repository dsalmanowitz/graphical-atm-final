package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;

@SuppressWarnings("serial")
public class WithdrawView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton quitButton;
	private JButton submitButton;
	private JTextField amountField;
	private JLabel errorMessageLabel;
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public WithdrawView(ViewManager manager) {
		super();
		
		this.manager = manager;
		this.errorMessageLabel = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		
		this.setLayout(null);
		
		JLabel withdrawLabel = new JLabel("Withdraw", SwingConstants.CENTER);
		withdrawLabel.setBounds(140, 10, 200, 35);
		this.add(withdrawLabel);
		
		JLabel amountLabel = new JLabel("Amount", SwingConstants.RIGHT);
		amountLabel.setBounds(30, 50, 95, 35);
		amountLabel.setLabelFor(amountField);
		this.add(amountLabel);
		amountField = new JTextField();
		amountField.setBounds(140, 50, 200, 35);
		this.add(amountField);
		submitButton = new JButton("Submit");
		submitButton.setBounds(140, 90, 200, 35);
		submitButton.addActionListener(this);
		this.add(submitButton);
		quitButton = new JButton("Quit");
		quitButton.setBounds(140, 130, 200, 35);
		quitButton.addActionListener(this);
		this.add(quitButton);

		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		this.add(errorMessageLabel);
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
	
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
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
		if (source.equals(submitButton)) {
			double amount = Double.parseDouble(amountField.getText());
			if (manager.withdraw(amount) == 1) {
				manager.switchTo(ATM.HOME_VIEW);
			}
		}
		if (source.equals(quitButton)) {
			manager.switchTo(ATM.HOME_VIEW);

		}
	}
}
