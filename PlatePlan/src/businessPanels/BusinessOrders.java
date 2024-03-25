package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import dto.Order;
import dto.Reservation;
import dto.TimeSlot;
import main.PlatePlanMain;
import service_interfaces.MenuService;
import service_interfaces.OrdersService;
import service_interfaces.ReservationService;
import services.MenuServiceImpl;
import services.OrdersServiceImpl;
import services.ReservationServiceImpl;
import javax.swing.JToolBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class BusinessOrders extends JPanel {
	private JTable table;
	private Business business;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private DefaultTableModel tableModel;
	private ReservationService reservationService;
	private MenuService menuService;
	private OrdersService ordersService;

	private Map<String, String> reservationMap;
	private List<Reservation> reservationList;

	private JTextField currentReservationView;
	private JLabel lblCustomer;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblTable;

	public BusinessOrders(Business business) {
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
		this.menuService = MenuServiceImpl.getInstance();
		this.ordersService = OrdersServiceImpl.getInstance();
		this.business = business;
		loadReservations();
		System.out.println(reservationList);
		System.out.println(reservationMap);

		btnNewButton = new JButton("Back");
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnNewButton.setBounds(10, 11, 89, 23);
		add(btnNewButton);

		lblNewLabel = new JLabel("Orders");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(382, 11, 336, 34);
		add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(59, 202, 982, 398);
		add(scrollPane_1);
		table = new JTable();
		scrollPane_1.setViewportView(table);

		tableModel = new DefaultTableModel(new String[] { "Menu Item", "Price", "Quantity" }, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        // Make the first and second column uneditable
		        return column > 1; // Only the third column (Quantity) is editable
		    }
		};
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.setRowHeight(30);


		JButton btnNewButton_1_1 = new JButton("Save Changes");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveOrders();
			}
		});
		btnNewButton_1_1.setBounds(475, 611, 150, 40);
		add(btnNewButton_1_1);

		JButton btnNewButton_1_2 = new JButton("<");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				for (Reservation reservation : reservationList) {
					if (reservationMap.get(reservation.getId()).equals(currentReservationView.getText())) {
						i = reservationList.indexOf(reservation);
					}
				}
				System.out.println(i);
				i = (i - 1) % reservationList.size();
				System.out.println(i);
				currentReservationView.setText(reservationMap.get(reservationList.get(i).getId()));
				loadOrders();
//				PlatePlanMain.refreshPage();
			}
		});
		btnNewButton_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 10));
		btnNewButton_1_2.setBounds(350, 76, 40, 40);
		add(btnNewButton_1_2);

		currentReservationView = new JTextField();
		currentReservationView.setHorizontalAlignment(SwingConstants.CENTER);
		currentReservationView.setFont(new Font("Arial", Font.PLAIN, 12));
		currentReservationView.setEnabled(false);
		currentReservationView.setBounds(400, 76, 300, 40);
		add(currentReservationView);
		currentReservationView.setColumns(10);
		currentReservationView.setText("");

//		if (!reservationMap.isEmpty()) {
//
//			currentReservationView.setText(reservationMap.get(reservationList.get(0).getId()));
//		}

		JButton btnNewButton_1_2_1 = new JButton(">");
		btnNewButton_1_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				for (Reservation reservation : reservationList) {
					if (reservationMap.get(reservation.getId()).equals(currentReservationView.getText())) {
						i = reservationList.indexOf(reservation);
					}
				}
				System.out.println(i);
				i = (i + 1) % reservationList.size();
				System.out.println(i);
				currentReservationView.setText(reservationMap.get(reservationList.get(i).getId()));
				loadOrders();
//				PlatePlanMain.refreshPage();

			}
		});
		btnNewButton_1_2_1.setFont(new Font("Segoe UI Black", Font.BOLD, 10));
		btnNewButton_1_2_1.setBounds(710, 76, 40, 40);
		add(btnNewButton_1_2_1);

		lblCustomer = new JLabel("");
		lblCustomer.setBounds(65, 149, 193, 34);
		add(lblCustomer);

		lblDate = new JLabel("");
		lblDate.setBounds(323, 149, 193, 34);
		add(lblDate);

		lblTime = new JLabel("");
		lblTime.setBounds(581, 149, 193, 34);
		add(lblTime);

		lblTable = new JLabel("");
		lblTable.setBounds(839, 149, 193, 34);
		add(lblTable);

		if (!reservationList.isEmpty()) {
			loadOrders();
		}

//		PlatePlanMain.refreshPage();

	}

	public void loadReservations() {
		reservationMap = new HashMap<>();
		reservationList = new ArrayList<>();

		for (Reservation reservation : reservationService.getAllReservations()) {
			if (reservation.getDate().isEqual(LocalDate.now())) {
				String str = String.format("Table %s party of %s", reservation.getTableId(),
						reservation.getPartySize());
				reservationMap.put(reservation.getId(), str);
				reservationList.add(reservation);
			}

		}

	}

	public void loadOrders() {
		Reservation currReservation = null;
		tableModel.setRowCount(0);
		for (Reservation reservation : reservationList) {
			if (reservationMap.get(reservation.getId()).equals(currentReservationView.getText())) {
				currReservation = reservation;
			}
		}

		if (currReservation != null)
		{
			lblCustomer.setText("Customer: " + currReservation.getCustomerId());
			lblDate.setText("Date: " + currReservation.getDate());
			lblTime.setText("Time: " + currReservation.getTime().getFrom() + " - " + currReservation.getTime().getTo());
			lblTable.setText("Table: " + currReservation.getTableId());
			
			for (Order r : ordersService.getAllOrdersForReservation(currReservation)) {
				tableModel.addRow(new Object[] { r.getItem(), r.getPrice(), r.getQuantity() });
			}
		}
	}
	
	public void saveOrders() {
		
		Reservation currReservation = null;
		for (Reservation reservation : reservationList) {
			if (reservationMap.get(reservation.getId()).equals(currentReservationView.getText())) {
				currReservation = reservation;
			}
		}
		
		
		if (currReservation != null)
		{
			List<Order> orders = ordersService.getAllOrdersForReservation(currReservation);
			
			try {
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					
					for (Order order: orders)
					{
						if (order.getItem().equalsIgnoreCase((String)tableModel.getValueAt(i, 0)))
						{
							order.setQuantity(Integer.valueOf(tableModel.getValueAt(i, 2).toString()));
							if (order.getQuantity()> 0)
							{
								if (!ordersService.updateOrder(order))
								{
									throw new Exception("Could not update");
								}
							}
						}
					}
				    
				}
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to submit order", "Submission Error", JOptionPane.ERROR_MESSAGE);
			}

					

		}
		loadOrders();
		

		
	}
		
}
