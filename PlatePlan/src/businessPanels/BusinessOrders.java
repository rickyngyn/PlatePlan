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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import customerPanels.Constants;
import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Business;
import dto.Order;
import dto.Receipt;
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
import javax.swing.SpinnerNumberModel;
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
	private Reservation currentReservation;
	private JTextField currentReservationView;
	private JLabel lblCustomer;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblTable;
	private BusinessReceiptComponent receiptComponent;

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
		scrollPane_1.setBounds(436, 187, 627, 398);
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
		btnNewButton_1_1.setBounds(692, 596, 150, 40);
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
		lblCustomer.setBounds(64, 127, 193, 34);
		add(lblCustomer);

		lblDate = new JLabel("");
		lblDate.setBounds(322, 127, 193, 34);
		add(lblDate);

		lblTime = new JLabel("");
		lblTime.setBounds(580, 127, 193, 34);
		add(lblTime);

		lblTable = new JLabel("");
		lblTable.setBounds(838, 127, 193, 34);
		add(lblTable);

		if (!reservationList.isEmpty()) {
			loadOrders();
		}

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

	public void loadReceiptComponent() {
		if (receiptComponent != null) {
			remove(receiptComponent);
		}
		receiptComponent = new BusinessReceiptComponent(currentReservation);
		receiptComponent.setLocation(64, 187);
		receiptComponent.setSize(300, 400);
		add(receiptComponent);
		PlatePlanMain.refreshPage();
	}

	public void loadOrders() {
		Reservation currReservation = null;
		tableModel.setRowCount(0);
		for (Reservation reservation : reservationList) {
			if (reservationMap.get(reservation.getId()).equals(currentReservationView.getText())) {
				currReservation = reservation;
			}
		}

		if (currReservation != null) {
			lblCustomer.setText("Customer: " + currReservation.getCustomerId());
			lblDate.setText("Date: " + currReservation.getDate());
			lblTime.setText("Time: " + currReservation.getTime().getFrom() + " - " + currReservation.getTime().getTo());
			lblTable.setText("Table: " + currReservation.getTableId());
			currentReservation = currReservation;
			loadReceiptComponent();
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

		if (currReservation != null) {
			List<Order> orders = ordersService.getAllOrdersForReservation(currReservation);

			try {
				for (int i = 0; i < tableModel.getRowCount(); i++) {

					for (Order order : orders) {
						if (order.getItem().equalsIgnoreCase((String) tableModel.getValueAt(i, 0))) {
							order.setQuantity(Integer.valueOf(tableModel.getValueAt(i, 2).toString()));
							if (order.getQuantity() > 0) {
								if (!ordersService.updateOrder(order)) {
									throw new Exception("Could not update");
								}
							} else if (order.getQuantity() <= 0) {
								ordersService.deleteOrder(order);
							}
						}
					}

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Unable to submit order", "Submission Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		loadOrders();

	}
	
	public class BusinessReceiptComponent extends JPanel {

		private static final long serialVersionUID = 1L;
		private JLabel lblSubTotal_Key;
		private JLabel lblTaxVal;
		private JLabel lblSubTotal_Val;
		private JSpinner tipSpinner;
		private JLabel lblTotalVal;
		private JButton btnNewButton;
		private OrdersService ordersService;
		private Receipt receipt;
		/**
		 * Create the panel.
		 */
		public BusinessReceiptComponent(Reservation reservation) {
			this.setPreferredSize(new Dimension(291, 400));
			this.setMinimumSize(new Dimension(291, 400));
			this.setMaximumSize(new Dimension(291, 400));
			setBackground(new Color(255, 245, 238));
			setLayout(null);
			
			ordersService = OrdersServiceImpl.getInstance();
			
			JLabel lblNewLabel = new JLabel("Receipt");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Arial", Font.BOLD, 24));
			lblNewLabel.setBounds(48, 11, 194, 37);
			add(lblNewLabel);
			
			lblSubTotal_Key = new JLabel("Subtotal:");
			lblSubTotal_Key.setFont(new Font("Arial", Font.PLAIN, 16));
			lblSubTotal_Key.setBounds(10, 91, 101, 24);
			add(lblSubTotal_Key);
			
			JLabel lblTaxKey = new JLabel("Tax (13%): ");
			lblTaxKey.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTaxKey.setBounds(10, 126, 101, 24);
			add(lblTaxKey);
			
			JLabel lblTipKey = new JLabel("Tip (%): ");
			lblTipKey.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTipKey.setBounds(10, 161, 101, 24);
			add(lblTipKey);
			
			JLabel lblNewLabel_1_1_2 = new JLabel("______________________________");
			lblNewLabel_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
			lblNewLabel_1_1_2.setBounds(10, 185, 271, 24);
			add(lblNewLabel_1_1_2);
			
			JLabel lblTotalKey = new JLabel("Total: ");
			lblTotalKey.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTotalKey.setBounds(10, 220, 101, 24);
			add(lblTotalKey);
			
			btnNewButton = new JButton("Submit Payment");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean result = ordersService.saveReceipt(receipt);
					if(result) {
					    // If saveReceipt returns true, display a success message
					    JOptionPane.showMessageDialog(null, "Receipt saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
					} else {
					    // If saveReceipt returns false, display an error message
					    JOptionPane.showMessageDialog(null, "Failed to save the receipt.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					PlatePlanMain.refreshPage();
				}
			});
			btnNewButton.setBounds(81, 329, 128, 37);

			
			lblSubTotal_Val = new JLabel("$0");
			lblSubTotal_Val.setHorizontalAlignment(SwingConstants.TRAILING);
			lblSubTotal_Val.setFont(new Font("Arial", Font.PLAIN, 16));
			lblSubTotal_Val.setBounds(180, 91, 101, 24);
			add(lblSubTotal_Val);
			
			lblTaxVal = new JLabel("$0");
			lblTaxVal.setHorizontalAlignment(SwingConstants.TRAILING);
			lblTaxVal.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTaxVal.setBounds(180, 126, 101, 24);
			add(lblTaxVal);
			
			tipSpinner = new JSpinner();

			tipSpinner.setModel(new SpinnerNumberModel(0, 0, 100, 1));
			tipSpinner.setFont(new Font("Arial", Font.PLAIN, 14));
			tipSpinner.setBounds(219, 165, 62, 20);
			tipSpinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					receipt.setTip_percent(tipSpinner.getValue() != null ? ((Number) tipSpinner.getValue()).intValue() : 0);
					receipt.calculateTotal();
					lblSubTotal_Val.setText("$"+receipt.getSubtotal());
					lblTaxVal.setText("$"+receipt.getTax());
					lblTotalVal.setText("$"+receipt.getTotal());
					PlatePlanMain.refreshPage();
				}
			});


			add(tipSpinner);
			
			lblTotalVal = new JLabel("$0");
			lblTotalVal.setHorizontalAlignment(SwingConstants.TRAILING);
			lblTotalVal.setFont(new Font("Arial", Font.PLAIN, 16));
			lblTotalVal.setBounds(180, 220, 101, 24);
			add(lblTotalVal);
			
			if (reservation != null)
			{
				receipt = ordersService.getReceiptForReservation(reservation);
				
				lblSubTotal_Val.setText("$"+receipt.getSubtotal());
				lblTaxVal.setText("$"+receipt.getTax());
				lblTotalVal.setText("$"+receipt.getTotal());
				tipSpinner.setValue(receipt.getTip_percent());
				PlatePlanMain.refreshPage();
				
			}
			
			System.out.println(receipt.toString());
			if (receipt != null && !receipt.isPaid())
			{
				 add(btnNewButton);
			}



		}
	}


}
