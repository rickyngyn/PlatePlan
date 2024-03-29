package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import componentPanels.BusinessMenuComponent;
import customerPanels.Constants;
import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Business;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.MenuService;
import services.MenuServiceImpl;

public class BusinessMenuMangement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;
	private MenuService menuService;
	private List<MenuItem> menuItemsLoaded;

	/**
	 * Create the panel.
	 */
	public BusinessMenuMangement(Business bussiness) {
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
		this.menuService = MenuServiceImpl.getInstance();
		this.menuItemsLoaded = menuService.getAllMenuItems(SQLTables.MENU_TABLE);
//		menuListModel = new DefaultListModel<>();

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Adding your components (e.g., menu items) to the container panel
		for (MenuItem menuItem : menuItemsLoaded) {
			containerPanel.add(new BusinessMenuComponent(menuItem, bussiness));
			containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		// Create a JScrollPane that wraps the container panel
		JScrollPane scrollPane = new JScrollPane(containerPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(197, 44, 700, 550);

		// Add the JScrollPane to the frame instead of the container panel directly
		add(scrollPane);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(bussiness));
			}
		});
		btnBack.setBounds(977, 10, 89, 23);
		add(btnBack);

		JButton btnNewMenuItem = new JButton("Add New Menu Item");
		btnNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuItem menuItem = menuService.addMenuItem();
				if (menuItem == null) {
					JOptionPane.showMessageDialog(BusinessMenuMangement.this, "Unable to add new Menu Item", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					menuItemsLoaded.add(menuItem);
					containerPanel.add(new BusinessMenuComponent(menuItem, bussiness));
					containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
					PlatePlanMain.refreshPage();
				}
			}
		});
		btnNewMenuItem.setBounds(197, 605, 150, 35);
		add(btnNewMenuItem);

		JButton btnPublishMenu = new JButton("Publish Menu");
		btnPublishMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean result = menuService.publishCustomerMenu();
				if (result)
				{
					JOptionPane.showMessageDialog(BusinessMenuMangement.this, "Menu successfully published", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(BusinessMenuMangement.this, "Menu could be published", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		btnPublishMenu.setActionCommand("");
		btnPublishMenu.setBounds(747, 605, 150, 35);
		add(btnPublishMenu);
	}
}
