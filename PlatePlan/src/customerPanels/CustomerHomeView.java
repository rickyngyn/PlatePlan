package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dto.Customer;
import dto.Reservation;
import main.PlatePlanMain;
import misc.ServiceUtils;
import service_interfaces.ReservationService;
import services.ReservationServiceImpl;

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
	private ServiceUtils serviceUtils;
	private JLabel lblSpecialInstVal;

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
		Map<String, String> resToIdMap = new HashMap<>();
		this.reservationService = new ReservationServiceImpl();
		this.serviceUtils = ServiceUtils.getInstance();
		btnMakeReservation = new JButton("Reserve Table");
		btnMakeReservation.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		btnMakeReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerReservations(customer));
			}
		});
		btnMakeReservation.setBounds(660, 105, 152, 29);
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
				PlatePlanMain.switchPanels(new InitialView());
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

		reservationList.setBounds(114, 105, 461, 186);

		for (Reservation reservation : reservationService.getCustomerReservation(customer.getEmail())) {
			resToIdMap.put(convertResToText(reservation), reservation.getId());
			reservationList.add(convertResToText(reservation));
		}

		add(reservationList);

		currentReservationView = new Panel();
		currentReservationView.setBackground(new Color(250, 240, 230));
		currentReservationView.setBounds(113, 315, 462, 186);
		// add(currentReservationView);
		currentReservationView.setLayout(null);

		Label lblWhen = new Label("When");
		lblWhen.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhen.setBounds(10, 10, 62, 22);
		currentReservationView.add(lblWhen);

		Label lblWhatTime = new Label("What Time");
		lblWhatTime.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhatTime.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		lblWhatTime.setBounds(178, 10, 83, 22);
		currentReservationView.add(lblWhatTime);

		Label lblCap = new Label("For How Many");
		lblCap.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCap.setBounds(344, 10, 108, 22);
		currentReservationView.add(lblCap);

		Label lblCap_1 = new Label("Special Instructions:");
		lblCap_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCap_1.setBounds(10, 101, 125, 30);
		currentReservationView.add(lblCap_1);

		lblWhenVal = new Label("When");
		lblWhenVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhenVal.setBounds(10, 38, 108, 22);
		currentReservationView.add(lblWhenVal);

		lblWhatTimeVal = new Label("What Time");
		lblWhatTimeVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblWhatTimeVal.setBounds(178, 38, 83, 22);
		currentReservationView.add(lblWhatTimeVal);

		lblCapVal = new Label("For How Many");
		lblCapVal.setFont(new Font("Arial", Font.PLAIN, 14));
		lblCapVal.setBounds(344, 38, 108, 22);
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
		btnCancelRes.setBounds(10, 152, 138, 23);
		currentReservationView.add(btnCancelRes);

		lblSpecialInstVal = new JLabel("Value");
		lblSpecialInstVal.setFont(new Font("Arial", Font.PLAIN, 12));
		lblSpecialInstVal.setBounds(146, 101, 306, 30);
		currentReservationView.add(lblSpecialInstVal);

		JLabel lblUpcomingRes = new JLabel("Upcoming Reservations");
		lblUpcomingRes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUpcomingRes.setBounds(114, 82, 180, 17);
		add(lblUpcomingRes);

	}

	private String convertResToText(Reservation reservation) {
		return String.format("Booked Reservation on %s at %s", reservation.getDate(), reservation.getTime().getFrom());
	}
}
