package businessPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import componentPanels.BusinessInfoPanel;
import customerPanels.Constants;
import dto.Business;
import main.PlatePlanMain;
import service_interfaces.AccountService;
import services.AccountsServiceImpl;

public class BusinessStoreManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;

	private JSpinner spinnerOpenFrom;
	private JSpinner spinnerOpenUntil;
	private JSpinner spinnerSittingDuration;
	private JTextField textFieldPhoneNumber;
	private JTextPane textPane;
	private BusinessInfoPanel businessInfoPanel;
	private AccountService accountService;

	public BusinessStoreManagement(Business bussiness) {
		accountService = AccountsServiceImpl.getInstance();
		setFont(new Font("Calibri", Font.PLAIN, 18));
		Dimension windowDim = new Dimension(Constants.WINDOW_MAX_WIDTH, Constants.WINDOW_MAX_HEIGHT);
		this.setPreferredSize(windowDim);
		this.setMinimumSize(windowDim);
		this.setMaximumSize(windowDim);
		setLayout(null); // Consider using a more flexible layout
		setBackground(Color.decode("#E7F6F2"));
		this.business = bussiness;

		initComponents();
	}

	private void initComponents() {
		// Open From
		JLabel lblOpenFrom = new JLabel("Open From:");
		lblOpenFrom.setBounds(113, 118, 120, 25);
		add(lblOpenFrom);

		spinnerOpenFrom = new JSpinner(new SpinnerDateModel(
				Date.from(business.getOpenFrom().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()),
				null, null, Calendar.MINUTE));
		JSpinner.DateEditor timeEditorFrom = new JSpinner.DateEditor(spinnerOpenFrom, "HH:mm");
		spinnerOpenFrom.setEditor(timeEditorFrom);
		spinnerOpenFrom.setBounds(243, 118, 200, 25);
		add(spinnerOpenFrom);

		// Open Until
		JLabel lblOpenUntil = new JLabel("Open Until:");
		lblOpenUntil.setBounds(113, 153, 120, 25);
		add(lblOpenUntil);

		spinnerOpenUntil = new JSpinner(new SpinnerDateModel(
				Date.from(business.getOpenUntil().atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant()),
				null, null, Calendar.MINUTE));
		JSpinner.DateEditor timeEditorUntil = new JSpinner.DateEditor(spinnerOpenUntil, "HH:mm");
		spinnerOpenUntil.setEditor(timeEditorUntil);
		spinnerOpenUntil.setBounds(243, 153, 200, 25);
		add(spinnerOpenUntil);

		// Sitting Duration
		JLabel lblSittingDuration = new JLabel("Sitting Duration:");
		lblSittingDuration.setBounds(113, 188, 120, 25);
		add(lblSittingDuration);

		spinnerSittingDuration = new JSpinner(new SpinnerNumberModel(business.getReservationSlots(), 60, 120, 15));
		spinnerSittingDuration.setBounds(243, 188, 200, 25);
		add(spinnerSittingDuration);

		// Address
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(113, 223, 120, 25);
		add(lblAddress);

		// Phone Number
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setBounds(113, 326, 120, 25);
		add(lblPhoneNumber);

		textFieldPhoneNumber = new JTextField(business.getPhoneNumber());
		textFieldPhoneNumber.setBounds(243, 326, 200, 25);
		add(textFieldPhoneNumber);

		// Back Button
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(979, 10, 89, 23);
		btnBack.addActionListener(e -> PlatePlanMain.switchPanels(new BusinessHomeView(business)));
		add(btnBack);

		// Save Button
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				business.setAddress(textPane.getText().trim());
				business.setPhoneNumber(textFieldPhoneNumber.getText());
				business.setReservationSlots(((Double) spinnerSittingDuration.getValue()).longValue());
				business.setOpenFrom(
						((Date) spinnerOpenFrom.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
				business.setOpenUntil(
						((Date) spinnerOpenUntil.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime());
				accountService.updateBusinessAccount(business);
				PlatePlanMain.switchPanels(new BusinessStoreManagement(business));
			}
		});

		btnSave.setBounds(243, 361, 89, 23);
		add(btnSave);

		// Reset Button
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessStoreManagement(business));
			}
		});
		btnReset.setBounds(343, 361, 89, 23);
		add(btnReset);

		textPane = new JTextPane();
		textPane.setText(business.getAddress());
		textPane.setBounds(243, 228, 200, 87);
		add(textPane);

		businessInfoPanel = new BusinessInfoPanel(business);
		businessInfoPanel.setSize(560, 318);
		businessInfoPanel.setLocation(500, 118);

		add(businessInfoPanel);
	}
}
