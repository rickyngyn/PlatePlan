package businessPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import componentPanels.BusinessMenuComponent;
import customerPanels.Constants;
import dto.Business;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.MenuService;
import service_interfaces.ServerService;
import services.MenuServiceImpl;
import services.ServerServiceImpl;

public class BusinessMenuMangement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;
	private MenuService menuService;
	private List<String> menuItemsHash;
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
		this.menuItemsLoaded = menuService.getAllMenuItems();
		this.menuItemsHash = new ArrayList<>();
//		menuListModel = new DefaultListModel<>();

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Adding your components (e.g., menu items) to the container panel
		for (MenuItem menuItem : menuItemsLoaded) {
			containerPanel.add(new BusinessMenuComponent(menuItem));
			menuItemsHash.add(menuItem.hashCode() + "");
			containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		// Create a JScrollPane that wraps the container panel
		JScrollPane scrollPane = new JScrollPane(containerPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(200, 62, 700, 500);

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

		JButton btnSaveChanges = new JButton("Save");
		btnSaveChanges.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
//		        System.out.println(menuItemsLoaded);

//		        menuItemsLoaded.clear();
//				for (Component comp : containerPanel.getComponents()) {
//				    if (comp instanceof BusinessMenuComponent) {
//				        menuItemsLoaded.add(((BusinessMenuComponent) comp).getMenuItem());
//				    }
//				}
//		        System.out.println(menuItemsLoaded);

				for (int i = 0; i < menuItemsLoaded.size(); i++) {
					
					if (!menuItemsHash.get(i).equals(menuItemsLoaded.get(i).hashCode() + "")) {
						System.out.println("Updating Menu item with id " + menuItemsLoaded.get(i).getId()
								+ " and values" + menuItemsLoaded.get(i).toString());
						if (menuService.updateMenuItem(menuItemsLoaded.get(i))) {
							menuItemsHash.set(i, menuItemsLoaded.get(i).hashCode() + "");
						}

					}

				}
			}
		});
		btnSaveChanges.setFont(new Font("Arial", Font.BOLD, 16));
		btnSaveChanges.setBounds(200, 586, 150, 40);
		add(btnSaveChanges);

		JButton btnCancelChanges = new JButton("Cancel Changes");
		btnCancelChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessMenuMangement(bussiness));
			}
		});
		btnCancelChanges.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelChanges.setBounds(750, 586, 150, 40);
		add(btnCancelChanges);
	}


}
