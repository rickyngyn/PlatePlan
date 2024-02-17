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

public class CustomerSignIn extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField passwordField;
	private JLabel lblUser;
	private JLabel lblPass;
	private JButton btnSignIn;
	private JButton btnRegister;
	private JButton btnBack;

	// Added reference to PlatePlanMain

	/**
	 * Create the panel.
	 */
	public CustomerSignIn() {

		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================

		JLabel welcomeLabel = new JLabel("Welcome To Alfredos");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 26));
		welcomeLabel.setBounds(417, 36, 266, 127);
		add(welcomeLabel);

		username = new JTextField();
		username.setBounds(425, 200, 250, 30);
		add(username);
		username.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(425, 301, 250, 30);
		add(passwordField);

		lblUser = new JLabel("Username");
		lblUser.setBounds(425, 175, 120, 16);
		add(lblUser);

		lblPass = new JLabel("Password");
		lblPass.setBounds(425, 283, 120, 16);
		add(lblPass);

		btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signInCustomer();
			}
		});
		btnSignIn.setBounds(491, 394, 117, 29);
		add(btnSignIn);

		btnRegister = new JButton("Don't have an account? Register Now!");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlatePlanMain.switchPanels(new CustomerSignUp());
			}
		});
		btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegister.setBorderPainted(false);
		btnRegister.setForeground(Color.BLUE);
		btnRegister.setBackground(new Color(255, 250, 250));
		btnRegister.setBounds(400, 470, 300, 29);
		add(btnRegister);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new InitialView());
			}
		});
		btnBack.setBounds(6, 6, 117, 29);
		add(btnBack);

	}

	private void signInCustomer() {
		AccountService accountService = new AccountsServiceImpl();

		String pass = String.valueOf(passwordField.getPassword());
		String email = username.getText(); // Use email instead of the whole customer object
		Customer customer = accountService.customerLogin(email, pass);

		if (customer != null) {
			PlatePlanMain.switchPanels(new CustomerHomeView(customer));
		} else {
			JOptionPane.showMessageDialog(this, "Login failed. Please check your username and password.", "Login Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
