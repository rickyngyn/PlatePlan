package businessPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import customerPanels.Constants;
import customerPanels.InitialView;
import dto.Business;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.ServiceUtils;
import services.ServiceUtilsImpl;

import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import componentPanels.MenuComponent;

import javax.swing.JList;
import java.awt.Rectangle;

public class BusinessMenuMangement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;
	private ServiceUtils serviceUtils;
    private JList<MenuItem> menuList;
    private DefaultListModel<MenuItem> menuListModel;
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

		menuListModel = new DefaultListModel<>();

        for (MenuItem menuItem : serviceUtils.getAllMenuItems()) {
            menuListModel.addElement(menuItem);
        }

        menuList = new JList<>(menuListModel);
        menuList.setBounds(new Rectangle(50, 10, 500, 600));
        menuList.setCellRenderer(new MenuComponentCellRenderer());
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    MenuItem selectedMenuItem = menuList.getSelectedValue();
                    // Handle selection
                    System.out.println("Selected: " + selectedMenuItem.getName());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setSize(420, 600);
        scrollPane.setLocation(50, 10);
        scrollPane.setPreferredSize(new Dimension(500, 600));
        add(scrollPane);
	}
	
	class MenuComponentCellRenderer extends JPanel implements ListCellRenderer<MenuItem> {
	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getListCellRendererComponent(JList<? extends MenuItem> list, MenuItem value, int index, boolean isSelected, boolean cellHasFocus) {
	        MenuComponent component = new MenuComponent(value);
	        
	       
	        
	        
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
