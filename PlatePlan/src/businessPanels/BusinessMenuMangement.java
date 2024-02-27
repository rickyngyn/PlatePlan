package businessPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import service_interfaces.ServiceUtils;
import services.ServiceUtilsImpl;

public class BusinessMenuMangement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;
	private ServiceUtils serviceUtils;
    private JList<MenuItem> menuList;
    private DefaultListModel<MenuItem> menuListModel;
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
		this.serviceUtils = ServiceUtilsImpl.getInstance();
		this.menuItemsLoaded = serviceUtils.getAllMenuItems();

//		menuListModel = new DefaultListModel<>();

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(255, 255, 255));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        // Adding your components (e.g., menu items) to the container panel
        for (MenuItem menuItem : menuItemsLoaded) {
            containerPanel.add(new BusinessMenuComponent(menuItem));
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
	
	class MenuComponentCellRenderer extends JPanel implements ListCellRenderer<MenuItem> {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getListCellRendererComponent(JList<? extends MenuItem> list, MenuItem value, int index, boolean isSelected, boolean cellHasFocus) {
	        BusinessMenuComponent component = new BusinessMenuComponent(value);
	        
	       
	        
	        
	        if (isSelected) {
	            component.setBackground(list.getSelectionBackground());
	            component.setForeground(list.getSelectionForeground());
	        } else {
	            component.setBackground(list.getBackground()); // Make sure to set background for non-selected items too
	            component.setForeground(list.getForeground());
	        }
	        
	        // Ensure the component is opaque to allow background coloring
	        component.setOpaque(true);
	        
	        return component;
	    }
	}
}
