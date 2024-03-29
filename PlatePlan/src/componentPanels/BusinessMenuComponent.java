package componentPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import businessPanels.BusinessMenuMangement;
import dto.Business;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.MenuService;
import services.MenuServiceImpl;

public class BusinessMenuComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtTitle;
	private JTextField txtPriceField;
	private MenuItem menuItem;
	private MenuService menuService;
	private Business business;

	/**
	 * Create the panel.
	 */
	public BusinessMenuComponent(MenuItem menuItem, Business business) {
		this.menuItem = menuItem;
		this.business = business;
		menuService = MenuServiceImpl.getInstance();
		Dimension dimension = new Dimension(400, 100);
		this.setPreferredSize(new Dimension(600, 100));
		this.setMinimumSize(new Dimension(600, 100));
		this.setMaximumSize(new Dimension(600, 100));
		setBackground(new Color(255, 245, 238));
		setLayout(null);

		// JTextArea for description
		JTextArea txtDescription = new JTextArea(menuItem.getDescription());
		txtDescription.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						BusinessMenuComponent.this.menuItem.setDescription(txtDescription.getText());
						updateMenuItem();
					}
				});

			}
		});
		txtDescription.setEditable(true); // This line makes the JTextArea editable
		txtDescription.setFont(new Font("Arial", Font.ITALIC, 11));
		txtDescription.setBounds(15, 49, 474, 40);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		txtDescription.setOpaque(true);
		txtDescription.setBackground(new Color(255, 245, 238)); // Match the background with the JPanel
		txtDescription.setBorder(null); // No border to mimic a JLabel

		add(txtDescription);

		txtTitle = new JTextField();

		txtTitle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						BusinessMenuComponent.this.menuItem.setName(txtTitle.getText());
						updateMenuItem();
					}
				});
			}

		});
		txtTitle.setFont(new Font("Arial", Font.BOLD, 16));
		txtTitle.setBorder(null);
		txtTitle.setCaretColor(new Color(0, 0, 0));
		txtTitle.setOpaque(false);
		txtTitle.setBackground(new Color(255, 255, 255));
		txtTitle.setText(menuItem.getName());
		txtTitle.setBounds(15, 16, 474, 31);
		add(txtTitle);
		txtTitle.setColumns(10);

		txtPriceField = new JTextField();
		txtPriceField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							float newPrice = Float.parseFloat(txtPriceField.getText());
							BusinessMenuComponent.this.menuItem.setPrice(newPrice);
							updateMenuItem();
						}catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Price provided is not a number", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
			}
		});

		txtPriceField.setSelectedTextColor(Color.WHITE);
		txtPriceField.setFont(new Font("Arial", Font.ITALIC, 16));
		txtPriceField.setText("" + menuItem.getPrice());
		txtPriceField.setBounds(516, 16, 74, 31);
		add(txtPriceField);
		txtPriceField.setColumns(10);

		JLabel lblNewLabel = new JLabel("$");
		lblNewLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		lblNewLabel.setBounds(499, 24, 14, 14);
		add(lblNewLabel);

		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteMenuItem();
			}
		});
		btnNewButton.setBounds(516, 58, 74, 31);
		add(btnNewButton);

	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void updateMenuItem() {
		System.out.println("Updating Menu Item " + menuItem.toString());
		menuService.updateMenuItem(menuItem);
	}

	public void deleteMenuItem() {
		boolean result = menuService.deleteMenuItem(menuItem);

		if (result) {
			PlatePlanMain.switchPanels(new BusinessMenuMangement(this.business));
		} else {
			JOptionPane.showMessageDialog(this, "Unable to delete menu item from database", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
