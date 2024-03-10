package businessPanels;

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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import customerPanels.Constants;
import customerPanels.CustomerSignIn;
import dto.Business;
import main.PlatePlanMain;
import service_interfaces.AccountService;
import services.AccountsServiceImpl;

public class BusinessSignIn extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField passwordField;
	private JLabel signInErrorLbl;

	/**
	 * Create the panel.
	 */
	public BusinessSignIn() {
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================

		JLabel welcomeLabel = new JLabel("Sign In");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 26));
		welcomeLabel.setBounds(417, 36, 266, 75);
		add(welcomeLabel);

		username = new JTextField();
		username.setBounds(425, 200, 250, 30);
		add(username);
		username.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(425, 301, 250, 30);
		add(passwordField);

		JLabel lblUser = new JLabel("Username");
		lblUser.setBounds(425, 175, 120, 16);
		add(lblUser);

		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(425, 283, 120, 16);
		add(lblPass);

		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				businessSignIn();
			}
		});
		btnSignIn.setBounds(491, 394, 117, 29);
		add(btnSignIn);

		JButton btnBackToInitialView = new JButton("Back");
		btnBackToInitialView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerSignIn());
			}
		});
		btnBackToInitialView.setBounds(10, 11, 89, 23);
		add(btnBackToInitialView);

		signInErrorLbl = new JLabel("Inccorrect Email or Password. Please Try Again!");
		signInErrorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		signInErrorLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		signInErrorLbl.setForeground(Color.RED);
		signInErrorLbl.setBounds(372, 118, 356, 30);
		
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

	private void businessSignIn() {
		remove(signInErrorLbl);
		AccountService accountService = AccountsServiceImpl.getInstance();

		String pass = String.valueOf(passwordField.getPassword());
		Business business = accountService.businessLogin(username.getText(), pass);

		if (business != null) {
			PlatePlanMain.switchPanels(new BusinessHomeView(business));
		} else {
			add(signInErrorLbl);
			PlatePlanMain.refreshPage();
		}

	}
}
