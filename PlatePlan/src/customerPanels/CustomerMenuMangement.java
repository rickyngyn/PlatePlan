package customerPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import componentPanels.CustomerMenuComponent;
import database.SQLTables;
import dto.Customer;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.MenuService;
import service_interfaces.OrdersService;
import services.MenuServiceImpl;
import services.OrdersServiceImpl;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CustomerMenuMangement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Customer customer;
	private MenuService menuService;
	private List<MenuItem> menuItemsLoaded;
	private JTextField bluebackground;
	private OrdersService ordersService;
    private String itemsToShow = "Enjoy Our Popular Items: "; // Class member variable
	/**
	 * Create the panel.
	 */
	public CustomerMenuMangement(Customer customer) {
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
		this.customer = customer;
		this.menuService = MenuServiceImpl.getInstance();
		this.menuItemsLoaded = menuService.getAllMenuItems(SQLTables.CUSTOMER_MENU_TABLE);
		this.ordersService = OrdersServiceImpl.getInstance();
//		menuListModel = new DefaultListModel<>();

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Adding your components (e.g., menu items) to the container panel
		for (MenuItem menuItem : menuItemsLoaded) {
			containerPanel.add(new CustomerMenuComponent(menuItem));
			containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		// Create a JScrollPane that wraps the container panel
		JScrollPane scrollPane = new JScrollPane(containerPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(197, 149, 700, 482);

		// Add the JScrollPane to the frame instead of the container panel directly
		add(scrollPane);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new CustomerHomeView(customer));
			}
		});
		btnBack.setBounds(977, 10, 89, 23);
		add(btnBack);

		for (MenuItem item : ordersService.getMostPopularItems(3)) {
			itemsToShow += item.getName() + ", ";
		}
		itemsToShow = itemsToShow.trim();
		if (itemsToShow.endsWith(",")) {
			itemsToShow = itemsToShow.substring(0, itemsToShow.length() - 1); // Remove trailing comma
		}

		JLabel lblRecommended = new JLabel(itemsToShow);
		lblRecommended.setFont(new Font("Arial", Font.PLAIN, 18));
		lblRecommended.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecommended.setBounds(197, 87, 700, 51);
		lblRecommended.setOpaque(true);
		lblRecommended.setBackground(new Color(245, 222, 179)); // Light blue color
		add(lblRecommended);

		// Scroll text in lblRecommended
		Timer scrollTimer = new Timer(200, new ActionListener() {
			private String displayText = itemsToShow + "    "; // Padding for smooth looping
			private int position = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				lblRecommended.setText(displayText.substring(position) + displayText.substring(0, position));
				position = (position + 1) % displayText.length();
			}
		});
		scrollTimer.start();

	}
}
