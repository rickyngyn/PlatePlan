package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customerPanels.Constants;
import customerPanels.InitialView;
import dto.Business;
import main.PlatePlanMain;

public class BusinessHomeView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private Business business;
	private JButton btnNewButton;
	private JButton btnManageTables;
	private JButton btnManageServers;
	private JButton btnViewManage;

	/**
	 * Create the panel.
	 */
	public BusinessHomeView(Business bussiness) {
		setFont(new Font("Calibri", Font.PLAIN, 18));
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================
		this.business = bussiness;
		lblNewLabel = new JLabel("BUSINESS MANAGEMENT");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 22));
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setBounds(429, 105, 370, 28);
		add(lblNewLabel);

		// Button to open another panel(reservationHomeView)
		btnNewButton = new JButton("View & Manage Reservations");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessReservations(business));
			}
		});
		btnNewButton.setBounds(429, 144, 231, 23);
		add(btnNewButton);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlatePlanMain.switchPanels(new InitialView());
			}
		});
		btnLogOut.setBounds(6, 6, 117, 29);
		add(btnLogOut);

		btnManageTables = new JButton("View & Manage Tables");
		btnManageTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessTableManageView(bussiness));
			}
		});
		btnManageTables.setBounds(429, 184, 231, 23);
		add(btnManageTables);
		
		btnManageServers = new JButton("View & Manage Servers");
		btnManageServers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessServerManage(bussiness));
				
			}
		});
		btnManageServers.setBounds(429, 223, 231, 23);
		add(btnManageServers);
		
		btnViewManage = new JButton("View & Manage Menu");
		btnViewManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessMenuMangement(bussiness));
			}
		});
		btnViewManage.setBounds(429, 263, 231, 23);
		add(btnViewManage);
	}
}
