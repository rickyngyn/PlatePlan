package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dto.Customer;
import main.PlatePlanMain;
import service_interfaces.AccountService;
import services.AccountsServiceImpl;

public class CustomerSignUp extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField passwordField;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtEmail;
	private JTextField txtPass;
	private static int TEXT_FIELD_LENGTH = 250;
	private static int TEXT_FIELD_WIDTH = 40;
	private JButton btnSignUp;
	private JLabel lblFName;
	private JLabel lblLName;
	private JLabel lblEmail;
	private JLabel lblPass;
	private JLabel lblSignUp;
	private JButton btnSignInLink;
	private AccountService accountService;

	/**
	 * Create the panel.
	 */
	public CustomerSignUp() {
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================

		accountService = AccountsServiceImpl.getInstance();

		lblSignUp = new JLabel("Let's Get You Started");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Arial", Font.PLAIN, 26));
		lblSignUp.setBounds(329, 36, 441, 127);
		add(lblSignUp);

		txtFirstName = new JTextField();
		txtFirstName.setBounds(425, 171, 250, 35);
		add(txtFirstName);
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setBounds(425, 241, 250, 35);
		add(txtLastName);
		txtLastName.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(425, 311, 250, 35);
		add(txtEmail);
		txtEmail.setColumns(10);

		txtPass = new JTextField();
		txtPass.setBounds(425, 381, 250, 35);
		add(txtPass);
		txtPass.setColumns(10);

		btnSignUp = new JButton("SignUp");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (signUpCustomer()) {
					txtEmail.setText("");
					txtFirstName.setText("");
					txtLastName.setText("");
					txtPass.setText("");
				}

			}
		});
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSignUp.setBounds(471, 432, 158, 45);
		add(btnSignUp);

		lblFName = new JLabel("First Name");
		lblFName.setBounds(425, 152, 100, 16);
		add(lblFName);

		lblLName = new JLabel("Last Name");
		lblLName.setBounds(425, 223, 100, 16);
		add(lblLName);

		lblEmail = new JLabel("Email");
		lblEmail.setBounds(425, 293, 100, 16);
		add(lblEmail);

		lblPass = new JLabel("Password");
		lblPass.setBounds(425, 363, 100, 16);
		add(lblPass);

		btnSignInLink = new JButton("Back To Sign In");
		btnSignInLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerSignIn());
			}
		});
		btnSignInLink.setBounds(471, 509, 158, 23);
		add(btnSignInLink);

	}

	private boolean signUpCustomer() {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		if (txtEmail.getText().isEmpty() || txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()
				|| txtPass.getText().isEmpty() || !txtFirstName.getText().matches("^[a-zA-Z ]+$")
				|| !txtLastName.getText().matches("^[a-zA-Z ]+$") || !txtEmail.getText().matches(emailRegex)) {
			JOptionPane.showMessageDialog(null, "Invalid Field Entry, Please Try again", // Message to display
					"Error", // Title of the dialog
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		Customer customer = accountService.registerAccount(txtEmail.getText(), txtFirstName.getText(),
				txtLastName.getText(), txtPass.getText());

		if (customer == null) {
			JOptionPane.showMessageDialog(null, "Account with email already exists!", // Message to display
					"Error", // Title of the dialog
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			JOptionPane.showMessageDialog(null, "Account successfully registered", // Message to display
					"Success", // Title of the dialog
					JOptionPane.INFORMATION_MESSAGE);
		}

		return true;
	}
}
