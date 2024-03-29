package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import customerPanels.Constants;
import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Business;
import dto.Reservation;
import dto.TimeSlot;
import main.PlatePlanMain;
import service_interfaces.ReservationService;
import services.ReservationServiceImpl;

public class BusinessReservations extends JPanel {
	private JTable table;
	private Business business;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private DefaultTableModel tableModel;
	private ReservationService reservationService;

	public BusinessReservations(Business business) {
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// ===========================================================================

		this.reservationService = ReservationServiceImpl.getInstance();
		this.business = business;

		btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		add(btnNewButton);

		lblNewLabel = new JLabel("Current Reservations");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(318, 69, 464, 34);
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(76, 114, 947, 450);
		add(scrollPane_1);
		table = new JTable();
		scrollPane_1.setViewportView(table);

		tableModel = new DefaultTableModel(new String[] { "ID", "Date", "Time", "Customer ID", "Party Size",
				"Special Notes", "Server", "Table ID", }, 0);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.setRowHeight(30);

		JButton btnNewButton_1 = new JButton("Cancel Reservation");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) { // Check if a row is actually selected
					int modelRow = table.convertRowIndexToModel(selectedRow);
					String idValue = tableModel.getValueAt(modelRow, 0).toString();
					reservationService.cancelReservation(idValue);
					PlatePlanMain.switchPanels(new BusinessReservations(business));
				}

			}
		});
		btnNewButton_1.setBounds(283, 575, 155, 40);
		add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Save Changes");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					String id = tableModel.getValueAt(i, 0).toString();

					// Cast the Date and Time objects to their respective types
					LocalDate date = LocalDate.parse((tableModel.getValueAt(i, 1).toString()));
					String time = tableModel.getValueAt(i, 2).toString();
					String[] parts = time.split(" - ");
					TimeSlot timeSlot = new TimeSlot(LocalTime.parse(parts[0]), LocalTime.parse(parts[1]));

					// Converting each part to LocalTime
					LocalTime startTime = LocalTime.parse(parts[0]);
					LocalTime endTime = LocalTime.parse(parts[1]);

					String customerId = tableModel.getValueAt(i, 3).toString();
					int partySize = Integer.valueOf(tableModel.getValueAt(i, 4).toString());
					String specialNotes = tableModel.getValueAt(i, 5).toString();
					String server = tableModel.getValueAt(i, 6).toString();
					String tableId = tableModel.getValueAt(i, 7).toString();

					Reservation reservation = new Reservation(id, customerId, date, timeSlot, specialNotes, server,
							tableId, partySize);
					reservationService.updateReservation(reservation);
				}

			}
		});
		btnNewButton_1_1.setBounds(691, 575, 125, 40);
		add(btnNewButton_1_1);

		for (Reservation r : reservationService.getAllReservations()) {
			if (r.getDate().isAfter(LocalDate.now()) || r.getDate().isEqual(LocalDate.now())) {
				tableModel.addRow(new Object[] { r.getId(), r.getDate(),
						String.format("%s - %s", r.getTime().getFrom(), r.getTime().getTo()), r.getCustomerId(),
						r.getPartySize(), r.getSpecialNotes(),
						r.getServerId() == null || r.getServerId().isEmpty() ? "Unassigned" : r.getServerId(),
						r.getTableId() });
			}

		}
		PlatePlanMain.refreshPage();

	}
}
