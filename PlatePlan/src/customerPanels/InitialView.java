package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import businessPanels.BusinessSignIn;
import main.PlatePlanMain;

public class InitialView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton customerView;
	private JButton btnBusinessLogin;

	/**
	 * Create the panel.
	 */
	public InitialView() {

		// Setting Default Dimensions
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		customerView = new JButton("Customer Sign In");
		customerView.setForeground(new Color(255, 0, 0));
		customerView.setBackground(new Color(255, 182, 193));
		customerView.setFont(new Font("Monotype Corsiva", Font.ITALIC, 25));
		customerView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerSignIn());
			}
		});
		customerView.setBorderPainted(false);
		customerView.setBounds(350, 322, 400, 55);
		add(customerView);

		btnBusinessLogin = new JButton("Business Login");
		btnBusinessLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessSignIn());
			}
		});
		btnBusinessLogin.setBounds(938, 30, 133, 39);
		add(btnBusinessLogin);
	}
}