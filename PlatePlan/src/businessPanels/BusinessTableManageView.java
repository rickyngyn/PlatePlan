package businessPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import componentPanels.BusinessMenuComponent;
import customerPanels.Constants;
import dto.Business;
import dto.Table;
import main.PlatePlanMain;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.ServerServiceImpl;
import services.TablesServiceImpl;
import javax.swing.ListSelectionModel;

public class BusinessTableManageView extends JPanel {
	private JTextField textID;
	private JTextField textCapacity;
	private JTable table;
	private JTextField textSearch;
	private ServerService serverService;
	private Business business;
	private TablesService tablesService;
	private DefaultTableModel model;
	private JButton btnAddTable;
	private JButton btnRemove;
	private JButton btnChange;
	private JButton btnReset;
	private JScrollPane scrollPane;
	private JLabel lblSearch;
	private JComboBox serverBox;
	private Map<String, String> serverMap;

	public BusinessTableManageView(Business business) {
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
		tablesService = TablesServiceImpl.getInstance();
		serverService = ServerServiceImpl.getInstance();
		serverMap = serverService.getAllServersMap();
		JLabel welcomeLabel = new JLabel("Table Manager");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 26));
		welcomeLabel.setBounds(694, 111, 266, 62);
		add(welcomeLabel);

		JLabel lblid = new JLabel("ID:");
		lblid.setBounds(709, 236, 46, 14);
		add(lblid);

		JLabel lblCapacity = new JLabel("Capacity: ");
		lblCapacity.setBounds(709, 264, 54, 14);
		add(lblCapacity);

		JLabel lblServer = new JLabel("Server: ");
		lblServer.setBounds(709, 292, 46, 14);
		add(lblServer);

		textID = new JTextField();
		textID.setFont(new Font("Arial", Font.PLAIN, 11));
		textID.setBounds(765, 233, 168, 20);
		add(textID);
		textID.setColumns(10);

		textCapacity = new JTextField();
		textCapacity.setFont(new Font("Arial", Font.PLAIN, 11));
		textCapacity.setBounds(766, 261, 168, 20);
		textCapacity.setColumns(10);
		add(textCapacity);

		serverBox = new JComboBox();
		serverBox.setFont(new Font("Arial", Font.PLAIN, 12));
		serverBox.setModel(new DefaultComboBoxModel(serverMap.values().toArray()));
		serverBox.setBounds(765, 288, 168, 22);
		add(serverBox);

		btnAddTable = new JButton("Add");
		btnAddTable.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAddTable.setBounds(731, 363, 89, 23);
		btnAddTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addTable();
			}
		});
		add(btnAddTable);

		btnChange = new JButton("Change");
		btnChange.setFont(new Font("Arial", Font.PLAIN, 12));
		btnChange.setBounds(859, 371, 89, 23);
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (table.getSelectedRowCount() == 1) {
					Object id = textID.getText();
					Object capacity = textCapacity.getText();
					Object server = serverBox.getSelectedItem();
					if (textID.getText().equals("") || textCapacity.getText().equals("")
							|| serverBox.getSelectedItem().equals("Select Server")) {
						// do nothing
					} else {
						model.setValueAt(id, table.getSelectedRow(), 0);
						model.setValueAt(capacity, table.getSelectedRow(), 1);
						model.setValueAt(server, table.getSelectedRow(), 2);
					}
				}
			}
		});
		// add(btnChange);

		btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Arial", Font.PLAIN, 12));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeTable();

			}
		});
		btnRemove.setBounds(830, 363, 89, 23);
		add(btnRemove);

		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Arial", Font.PLAIN, 12));
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textID.setText("");
				textCapacity.setText("");
				serverBox.setSelectedItem("Select Server");
			}
		});
		btnReset.setBounds(859, 405, 89, 23);
		// add(btnReset);

		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(56, 66, 579, 562);
		add(scrollPane);

		table = new JTable();
		table.setRowHeight(30);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setFont(new Font("Arial", Font.PLAIN, 12));
		model = new DefaultTableModel(new String[] { "ID", "Capacity", "Server" }, 0);
		table.setModel(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(1).setCellEditor(new TextFieldCellEditor());

		table.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(table);

		// Populating the table with data
		for (Table table : tablesService.getTablesMatchingResReq(0)) {
			model.addRow(new Object[] { table.getId(), table.getCapacity(), serverMap.get(table.getServer()) });
		}

		// Assuming serverService.getAllServersMap().values() gives you the list of
		// server names
		// Convert it to an array or collection as needed
		Object[] serversArray = serverMap.values().toArray();

		// Create a JComboBox with the server names
		JComboBox<Object> serversComboBox = new JComboBox<>(serversArray);
		serversComboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (table.getSelectedRow() >= 0) {
							updateTable();
						}

					}
				});
			}

		});

//		add(serversComboBox);
		// Set the JComboBox as the cell editor for the "Server" column
		TableColumn serverColumn = table.getColumnModel().getColumn(2); // Index 2 for "Server" column
		serverColumn.setCellEditor(new DefaultCellEditor(serversComboBox));

		textSearch = new JTextField();
		textSearch.setFont(new Font("Arial", Font.PLAIN, 11));
		textSearch.setToolTipText("");
		textSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				TableRowSorter<DefaultTableModel> model1 = new TableRowSorter<>(model);
				table.setRowSorter(model1);
				model1.setRowFilter(RowFilter.regexFilter(textSearch.getText()));
			}
		});
		textSearch.setColumns(10);
		textSearch.setBounds(143, 35, 168, 20);
		add(textSearch);

		lblSearch = new JLabel("Search:");
		lblSearch.setBounds(87, 38, 46, 14);
		add(lblSearch);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnBack.setBounds(988, 11, 89, 23);
		add(btnBack);

	}

	private void addTable() {
		String tableId = textID.getText();
		int cap = Integer.valueOf(textCapacity.getText());
		String serverId = (String) serverBox.getSelectedItem();

		for (String id : serverMap.keySet()) {
			if (serverMap.get(id).equals(serverId)) {
				serverId = id;
				break;
			}
		}

		tablesService.registerTable(tableId, cap, serverId);
		PlatePlanMain.switchPanels(new BusinessTableManageView(business));
	}

	private void removeTable() {

		String tableId = (String) model.getValueAt(table.getSelectedRow(), 0);

		tablesService.deleteTable(tableId);
		PlatePlanMain.switchPanels(new BusinessTableManageView(business));
	}

	private void updateTable() {

		String tableId = (String) model.getValueAt(table.getSelectedRow(), 0);
		int tableCap = Integer.valueOf((Integer) model.getValueAt(table.getSelectedRow(), 1));
		String server = (String) model.getValueAt(table.getSelectedRow(), 2);

		for (String key : serverMap.keySet()) {
			if (serverMap.get(key).equals(server)) {
				server = key;
				break;
			}
		}

		tablesService.updateTable(tableId, tableCap, server);
//		PlatePlanMain.switchPanels(new BusinessTableManageView(business));
	}
	
	class TextFieldCellEditor extends AbstractCellEditor implements TableCellEditor {
	    JTextField editor = new JTextField();

	    public TextFieldCellEditor() {
	        // Add a KeyListener to the JTextField
	        editor.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	               System.out.println("HERE");
	                if (table.getSelectedRow() >= 0) {
						updateTable();
					}

	            }
	        });
	    }

	    @Override
	    public Object getCellEditorValue() {
	        return editor.getText();
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        editor.setText(value != null ? value.toString() : "");
	        return editor;
	    }
	}
}
