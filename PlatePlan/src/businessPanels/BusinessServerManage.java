package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import customerPanels.Constants;
import dto.Business;
import dto.Server;
import main.PlatePlanMain;
import service_interfaces.ServerService;
import services.ServerServiceImpl;

public class BusinessServerManage extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JButton btnNewButton;
	private ServerService serviceUtils;
	private Business business;
	private DefaultTableModel tableModel;
	private JTable table;
	private JButton btnBack;

	/**
	 * Create the panel.
	 */
	public BusinessServerManage(Business business) {
		// ========================Setting Default Dimensions========================
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null);
		setBackground(new Color(255, 250, 250));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// ===========================================================================
		this.business = business;
		serviceUtils = ServerServiceImpl.getInstance();

		txtFirstName = new JTextField();
		txtFirstName.setBounds(657, 191, 213, 30);
		add(txtFirstName);
		txtFirstName.setColumns(10);

		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(657, 278, 213, 30);
		add(txtLastName);

		btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewServer();
			}
		});
		btnNewButton.setBounds(657, 391, 89, 23);
		add(btnNewButton);

		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeServer();
			}
		});
		btnRemove.setBounds(781, 391, 89, 23);
		add(btnRemove);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(657, 166, 115, 14);
		add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(657, 253, 135, 14);
		add(lblLastName);

		JScrollPane scrollPane = new JScrollPane();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(48, 114, 560, 450);
		add(scrollPane_1);
		table = new JTable();
		scrollPane_1.setViewportView(table);

		tableModel = new DefaultTableModel(new String[] { "ID", "First Name", "Last Name" }, 0);
		table.setModel(tableModel);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnBack.setBounds(1001, 11, 89, 23);
		add(btnBack);

		for (Server s : serviceUtils.getAllServers()) {
			tableModel.addRow(new Object[] { s.getId(), s.getFirstName(), s.getLastName() });
		}
		PlatePlanMain.refreshPage();

	}

	private void addNewServer() {
		if (txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "first name or last name is empty for new server", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		Server server = serviceUtils.registerServer(txtFirstName.getText(), txtLastName.getText());

		if (server != null) {
			tableModel.addRow(new Object[] { server.getId(), server.getFirstName(), server.getLastName() });
			txtFirstName.setText("");
			txtLastName.setText("");
			JOptionPane.showMessageDialog(null, "Added New Server", "Success", JOptionPane.PLAIN_MESSAGE);
			PlatePlanMain.refreshPage();

		} else {
			JOptionPane.showMessageDialog(null, "An error occurred adding new server!", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void removeServer() {
		try {
			Object serverId = tableModel.getValueAt(table.getSelectedRow(), 0);
			if (serviceUtils.deleteServer((String) serverId)) {
				tableModel.removeRow(table.getSelectedRow());
				JOptionPane.showMessageDialog(null, "Server removed from restaurant", "Success",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "Error removing server from DataBase", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, "Server not selected", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
