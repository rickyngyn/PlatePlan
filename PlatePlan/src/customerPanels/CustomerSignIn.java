package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessPanels.BusinessSignIn;
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
	private JButton btnBusinessLogin;

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

		JLabel welcomeLabel = new JLabel("Welcome To Alfredo's");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setVerticalAlignment(SwingConstants.CENTER); // Vertically center in its bounds
		welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30)); // Consider a more elegant font if appropriate
		welcomeLabel.setForeground(new Color(255, 255, 255)); // White color for contrast
		welcomeLabel.setBounds(308, 36, 483, 70); // Adjust bounds as needed for better positioning
		add(welcomeLabel);

		JLabel welcomeLabel_1 = new JLabel("Flavors of Italy, Fresh to Your Table");
		welcomeLabel_1.setVerticalAlignment(SwingConstants.CENTER);
		welcomeLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel_1.setForeground(new Color(0, 0, 0));
		welcomeLabel_1.setFont(new Font("Serif", Font.BOLD, 17));
		welcomeLabel_1.setBounds(308, 92, 483, 47);
		add(welcomeLabel_1);

		username = new JTextField();
		username.setBounds(425, 242, 250, 30);
		add(username);
		username.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(425, 343, 250, 30);
		add(passwordField);

		lblUser = new JLabel("Username");
		lblUser.setBounds(425, 217, 120, 16);
		add(lblUser);

		lblPass = new JLabel("Password");
		lblPass.setBounds(425, 325, 120, 16);
		add(lblPass);

		btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signInCustomer();
			}
		});
		btnSignIn.setBounds(491, 436, 117, 29);
		add(btnSignIn);

		btnRegister = new JButton("Don't have an account? Register Now!");
		btnRegister.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRegister.setOpaque(false);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlatePlanMain.switchPanels(new CustomerSignUp());
			}
		});
		btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegister.setBorderPainted(false);
		btnRegister.setForeground(Color.BLUE);
		btnRegister.setBackground(new Color(255, 250, 250));
		btnRegister.setBounds(400, 512, 300, 29);
		add(btnRegister);

		btnBusinessLogin = new JButton("Business Login");
		btnBusinessLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessSignIn());
			}
		});
		btnBusinessLogin.setBounds(938, 30, 133, 39);
		add(btnBusinessLogin);

		int width = Constants.WINDOW_MAX_WIDTH;
		int height = Constants.WINDOW_MAX_HEIGHT;
		// Load the original ImageIcon
		ImageIcon originalIcon = new ImageIcon(CustomerSignIn.class.getResource("/backgroundimage.jpg"));

		// Resize the image
		Image originalImage = originalIcon.getImage();
		Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon for the JLabel
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		// Create the JLabel with the resized ImageIcon
		JLabel back = new JLabel(resizedIcon);
		back.setLayout(null);
		back.setBounds(0, 0, width, height);

		// Add the JLabel to your container (e.g., a JFrame or JPanel)
		add(back);

	}

	private void signInCustomer() {
		AccountService accountService = AccountsServiceImpl.getInstance();

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
