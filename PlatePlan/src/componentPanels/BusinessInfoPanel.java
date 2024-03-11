package componentPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.Business;

public class BusinessInfoPanel extends JPanel {

	private Business business;

	public BusinessInfoPanel(Business business) {
		System.out.println(business);
		this.business = business;

		setPreferredSize(new Dimension(560, 318));
		setMaximumSize(new Dimension(560, 318));
		setMinimumSize(new Dimension(560, 318));

		setBackground(Color.decode("#FFE6E6"));
		setLayout(null);

		// Hours of Operation
		JLabel hoursLabel = new JLabel("Open Hours:");
		hoursLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		hoursLabel.setBounds(20, 60, 143, 30);
		add(hoursLabel);

		// Address
		JLabel addressLabel = new JLabel("Address: " + business.getAddress());
		addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		addressLabel.setBounds(20, 196, 460, 30);
		add(addressLabel);

		// Phone Number
		JLabel phoneLabel = new JLabel("Phone: " + business.getPhoneNumber());
		phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		phoneLabel.setBounds(20, 237, 460, 30);
		add(phoneLabel);

		JLabel lblNewLabel = new JLabel("Sunday");
		lblNewLabel.setBounds(84, 101, 46, 14);
		add(lblNewLabel);

		JLabel lblMonday = new JLabel("Monday");
		lblMonday.setBounds(149, 101, 46, 14);
		add(lblMonday);

		JLabel lblTuesday = new JLabel("Tuesday");
		lblTuesday.setBounds(214, 101, 55, 14);
		add(lblTuesday);

		JLabel lblWednesday = new JLabel("Wednesday");
		lblWednesday.setBounds(279, 101, 71, 14);
		add(lblWednesday);

		JLabel lblThursday = new JLabel("Thursday");
		lblThursday.setBounds(360, 101, 55, 14);
		add(lblThursday);

		JLabel lblFriday = new JLabel("Friday");
		lblFriday.setBounds(425, 101, 40, 14);
		add(lblFriday);

		JLabel lblSaturday = new JLabel("Saturday");
		lblSaturday.setBounds(484, 101, 54, 14);
		add(lblSaturday);

		JLabel lblFrom = new JLabel("From");
		lblFrom.setBounds(21, 127, 46, 14);
		add(lblFrom);

		JLabel lblUntil = new JLabel("Until");
		lblUntil.setBounds(22, 152, 40, 14);
		add(lblUntil);

		JLabel lblFrom_1 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1.setBounds(88, 127, 46, 14);
		add(lblFrom_1);

		JLabel lblFrom_2 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2.setBounds(84, 152, 46, 14);
		add(lblFrom_2);

		JLabel lblFrom_2_1 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_1.setBounds(152, 152, 46, 14);
		add(lblFrom_2_1);

		JLabel lblFrom_1_1 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_1.setBounds(155, 127, 46, 14);
		add(lblFrom_1_1);

		JLabel lblFrom_2_2 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_2.setBounds(220, 151, 46, 14);
		add(lblFrom_2_2);

		JLabel lblFrom_1_2 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_2.setBounds(222, 126, 46, 14);
		add(lblFrom_1_2);

		JLabel lblFrom_2_3 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_3.setBounds(288, 151, 46, 14);
		add(lblFrom_2_3);

		JLabel lblFrom_1_3 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_3.setBounds(289, 126, 46, 14);
		add(lblFrom_1_3);

		JLabel lblFrom_2_4 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_4.setBounds(356, 151, 46, 14);
		add(lblFrom_2_4);

		JLabel lblFrom_1_4 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_4.setBounds(356, 126, 46, 14);
		add(lblFrom_1_4);

		JLabel lblFrom_2_5 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_5.setBounds(424, 151, 46, 14);
		add(lblFrom_2_5);

		JLabel lblFrom_1_5 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_5.setBounds(423, 126, 46, 14);
		add(lblFrom_1_5);

		JLabel lblFrom_2_6 = new JLabel(business.getOpenUntil().toString());
		lblFrom_2_6.setBounds(492, 151, 46, 14);
		add(lblFrom_2_6);

		JLabel lblFrom_1_6 = new JLabel(business.getOpenFrom().toString());
		lblFrom_1_6.setBounds(490, 126, 46, 14);
		add(lblFrom_1_6);
	}

}
