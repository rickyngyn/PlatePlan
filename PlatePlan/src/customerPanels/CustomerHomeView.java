package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import businessPanels.BusinessHomeView;
import componentPanels.BusinessInfoPanel;
import database.DataBaseFactory;
import dto.Business;
import dto.Customer;
import dto.Reservation;
import main.PlatePlanMain;
import service_interfaces.FeedbackService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import services.FeedbackServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;

public class CustomerHomeView extends JPanel {

	private static final long serialVersionUID = 1L;
	private Panel currentReservationView;
	private Label lblWhenVal;
	private Label lblWhatTimeVal;
	private Label lblCapVal;
	private JButton btnLogOut;
	private List reservationList;
	private JButton btnMakeReservation;
	private ReservationService reservationService;
	private ServerService serviceUtils;
	private JLabel lblSpecialInstVal;
	private JButton btnViewMenu;
	private JButton btnViewFeedback;
	private JLabel lblOpenLabel;
	private Business business;
	private JLabel lblAverageRatingOf;
	private JButton btnViewResHistory;

	/**
	 * Create the panel.
	 */
	public CustomerHomeView(Customer customer) {
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================
		FeedbackService feedbackService = FeedbackServiceImpl.getInstance();

		Map<String, String> resToIdMap = new HashMap<>();
		business = DataBaseFactory.getDatabase().getBusinessAccount();
		this.reservationService = ReservationServiceImpl.getInstance();
		this.serviceUtils = ServerServiceImpl.getInstance();
		btnMakeReservation = new JButton("Reserve Table");
		btnMakeReservation.setFont(new Font("Arial", Font.PLAIN, 12));
		btnMakeReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerReservations(customer));
			}
		});
		btnMakeReservation.setBounds(555, 161, 170, 70);
		add(btnMakeReservation);

		JLabel lblWelcome = new JLabel(
				String.format("Welcome to Alfredo's Reservation Service %s", customer.getFirstName()));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Arial", Font.PLAIN, 20));
		lblWelcome.setBounds(150, 23, 800, 48);
		add(lblWelcome);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerSignIn());
			}
		});
		btnLogOut.setBounds(6, 6, 117, 29);
		add(btnLogOut);

		reservationList = new List();
		reservationList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String row = (String) reservationList.getSelectedItem();

				if (row != null && !row.isEmpty()) {
					for (Reservation reservation : reservationService.getCustomerReservation(customer.getEmail())) {
						if (convertResToText(reservation).equals(row)) {
							lblCapVal.setText("" + reservation.getPartySize());
							lblWhatTimeVal.setText(reservation.getTime().getFrom().toString());
							lblWhenVal.setText(reservation.getDate().toString());
							lblSpecialInstVal.setText(reservation.getSpecialNotes());

							add(currentReservationView);
							PlatePlanMain.refreshPage();
						}
					}
				}
			}
		});

		reservationList.setBounds(11, 120, 400, 186);

		for (Reservation reservation : reservationService.getCustomerReservation(customer.getEmail())) {
			resToIdMap.put(convertResToText(reservation), reservation.getId());
			reservationList.add(convertResToText(reservation));
		}

		add(reservationList);

		currentReservationView = new Panel();

		currentReservationView.setBackground(new Color(250, 240, 230));
		currentReservationView.setBounds(10, 419, 400, 186);
		// ==============================
		// add(currentReservationView);
		// ==============================

		currentReservationView.setLayout(null);

		Label lblWhen = new Label("When");
		lblWhen.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhen.setBounds(25, 10, 62, 22);
		currentReservationView.add(lblWhen);

		Label lblWhatTime = new Label("What Time");
		lblWhatTime.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhatTime.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		lblWhatTime.setBounds(158, 10, 83, 22);
		currentReservationView.add(lblWhatTime);

		Label lblCap = new Label("For How Many");
		lblCap.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCap.setBounds(253, 10, 108, 22);
		currentReservationView.add(lblCap);

		Label lblCap_1 = new Label("Special Instructions:");
		lblCap_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCap_1.setBounds(10, 101, 125, 30);
		currentReservationView.add(lblCap_1);

		lblWhenVal = new Label("When");
		lblWhenVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhenVal.setBounds(25, 38, 108, 22);
		currentReservationView.add(lblWhenVal);

		lblWhatTimeVal = new Label("What Time");
		lblWhatTimeVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhatTimeVal.setBounds(158, 38, 83, 22);
		currentReservationView.add(lblWhatTimeVal);

		lblCapVal = new Label("For How Many");
		lblCapVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCapVal.setBounds(253, 38, 108, 22);
		currentReservationView.add(lblCapVal);

		JButton btnCancelRes = new JButton("Cancel Reservation");
		btnCancelRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String row = (String) reservationList.getSelectedItem();

				if (reservationService.cancelReservation(resToIdMap.get(row))) {
					PlatePlanMain.switchPanels(new CustomerHomeView(customer));
				} else {
					JOptionPane.showMessageDialog(null, "Could not cancel reservation with ID " + resToIdMap.get(row), // Message
																														// to
																														// display
							"Error", // Title of the dialog
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCancelRes.setBounds(10, 152, 171, 23);
		currentReservationView.add(btnCancelRes);

		lblSpecialInstVal = new JLabel("Value");
		lblSpecialInstVal.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSpecialInstVal.setBounds(146, 101, 306, 30);
		currentReservationView.add(lblSpecialInstVal);

		JLabel lblUpcomingRes = new JLabel("Upcoming Reservations");
		lblUpcomingRes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUpcomingRes.setBounds(11, 97, 180, 17);
		add(lblUpcomingRes);

		btnViewMenu = new JButton("View Menu");
		btnViewMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerMenuMangement(customer));
			}
		});
		btnViewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewMenu.setBounds(806, 161, 170, 70);
		add(btnViewMenu);

		btnViewFeedback = new JButton("Feedbacks");
		btnViewFeedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerFeedbackScreen(customer));
			}
		});
		btnViewFeedback.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewFeedback.setBounds(555, 242, 170, 70);
		add(btnViewFeedback);

		// Assuming business.getOpenFrom() and business.getOpenUntil() return strings
		// like "9:00 AM" and "5:00 PM"
		String openFrom = business.getOpenFrom().toString();
		String openUntil = business.getOpenUntil().toString();
		lblOpenLabel = new JLabel("<html><div style='text-align: center;'>Open Today From <b>" + openFrom
				+ "</b> To <b>" + openUntil + "</b></div></html>");
		lblOpenLabel.setFont(new Font("SansSerif", Font.PLAIN, 18)); // Using SansSerif for a clean look, and increasing
																		// the size for better readability
		lblOpenLabel.setForeground(new Color(34, 139, 34)); // Setting the text color to a green for a friendly,
															// inviting look
		lblOpenLabel.setBounds(555, 82, 347, 30); // Adjusting the width to ensure the text fits, especially for longer
													// times
		add(lblOpenLabel);

		// Assuming feedbackService.getAverageRating() returns a formatted string, for
		// example, "4.3"
		String averageRating = String.format("%.1f", feedbackService.getAverageRating()); // Formats to one decimal
																							// place
		lblAverageRatingOf = new JLabel("Average Rating Of " + averageRating + " Out of 5");
		lblAverageRatingOf.setHorizontalAlignment(SwingConstants.LEFT);
		lblAverageRatingOf.setFont(new Font("Arial", Font.BOLD, 18)); // Increased font size and made it bold for
																		// emphasis
		lblAverageRatingOf.setForeground(new Color(0, 102, 204)); // Set the text color to a soft blue for a pleasant
																	// look
		lblAverageRatingOf.setBounds(553, 120, 325, 30); // Adjusted width to 350 in case the text is longer
		add(lblAverageRatingOf);

		BusinessInfoPanel businessInfoPanel = new BusinessInfoPanel(DataBaseFactory.getDatabase().getBusinessAccount());
		businessInfoPanel.setLocation(483, 329);
		businessInfoPanel.setSize(560, 318);
		add(businessInfoPanel);

		btnViewResHistory = new JButton("Reservation History");
		btnViewResHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
//		btnViewResHistory.setFont(new Font("Arial", Font.PLAIN, 12));
//		btnViewResHistory.setBounds(806, 242, 170, 70);
//		add(btnViewResHistory);

		btnMakeReservation.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/reservationsIcon.png"))
					.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnMakeReservation.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnMakeReservation.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnViewFeedback.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/feedbackIcon.png"))
					.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnViewFeedback.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnViewFeedback.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});

		btnViewMenu.addMouseListener(new MouseAdapter() {
			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/menuIcon.png")).getImage()
					.getScaledInstance(20, 20, Image.SCALE_SMOOTH));

			@Override
			public void mouseEntered(MouseEvent e) {
				btnViewMenu.setIcon(hoverIcon);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnViewMenu.setIcon(null); // Remove the icon when the mouse exits the button
			}
		});
//		btnViewResHistory.addMouseListener(new MouseAdapter() {
//			Icon hoverIcon = new ImageIcon(new ImageIcon(BusinessHomeView.class.getResource("/historyIcon.png"))
//					.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				btnViewResHistory.setIcon(hoverIcon);
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				btnViewResHistory.setIcon(null); // Remove the icon when the mouse exits the button
//			}
//		});
	}

	private String convertResToText(Reservation reservation) {
		return String.format("Booked Reservation on %s at %s", reservation.getDate(), reservation.getTime().getFrom());
	}
}
