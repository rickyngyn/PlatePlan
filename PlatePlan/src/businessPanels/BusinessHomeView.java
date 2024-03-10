package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import customerPanels.Constants;
import customerPanels.CustomerSignIn;
import dto.Business;
import main.PlatePlanMain;

public class BusinessHomeView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private Business business;
	private JButton btnReservations;
	private JButton btnManageTables;
	private JButton btnManageServers;
	private JButton btnManageMenu;
	private JButton btnManageFeedback;

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
		setBackground(Color.decode("#E7F6F2"));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================
		
		this.business = bussiness;
		lblNewLabel = new JLabel("BUSINESS MANAGEMENT");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Calibri", Font.ITALIC, 30));
		lblNewLabel.setLabelFor(this);
		lblNewLabel.setBounds(375, 122, 349, 38);
		add(lblNewLabel);

		btnReservations = new JButton("Reservations");
		btnManageTables = new JButton("Tables");
		btnManageServers = new JButton("Servers");
		btnManageMenu = new JButton("Menu");
		btnManageFeedback = new JButton("Feedbacks");

		Color backgroundColor = Color.decode("#FAF0E6");

		btnReservations.setBackground(backgroundColor);
		btnManageTables.setBackground(backgroundColor);
		btnManageServers.setBackground(backgroundColor);
		btnManageMenu.setBackground(backgroundColor);
		btnManageFeedback.setBackground(backgroundColor);

		// Add a mouse listener to the button to change the icon on hover
		btnManageTables.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/tableIcon.png"))
					.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnManageTables.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnManageTables.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnReservations.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/reservationsIcon.png"))
					.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnReservations.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnReservations.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnManageServers.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/serverIcon.png"))
					.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnManageServers.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnManageServers.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnManageMenu.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/menuIcon.png")).getImage()
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnManageMenu.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnManageMenu.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnManageFeedback.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/feedbackIcon.png"))
					.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnManageFeedback.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnManageFeedback.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		// Button to open another panel(reservationHomeView)
		btnReservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessReservations(business));
			}
		});
		btnReservations.setBounds(245, 206, 200, 50);
		add(btnReservations);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PlatePlanMain.switchPanels(new CustomerSignIn());
			}
		});
		btnLogOut.setBounds(6, 6, 97, 28);
		add(btnLogOut);

		btnManageTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessTableManageView(bussiness));
			}
		});
		btnManageTables.setBounds(455, 206, 200, 50);
		add(btnManageTables);

		btnManageServers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessServerManage(bussiness));

			}
		});
		btnManageServers.setBounds(665, 206, 200, 50);
		add(btnManageServers);

		btnManageMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessMenuMangement(bussiness));
			}
		});
		btnManageMenu.setBounds(245, 317, 200, 50);
		add(btnManageMenu);

		btnManageFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessFeedbackManagement(bussiness));
			}
		});
		btnManageFeedback.setBounds(455, 317, 200, 50);
		add(btnManageFeedback);
	}
}
