package componentPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import customerPanels.CustomerFeedbackScreen;
import dto.Customer;
import main.PlatePlanMain;
import service_interfaces.FeedbackService;
import services.FeedbackServiceImpl;

public class EditableFeedbackComponent extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea txtDescription;
	private JCheckBox anonChckBox;
	private JButton btnNewButton;
	private JSpinner spinner;
	private Customer customer;
	private FeedbackService feedbackService;

	/**
	 * Create the panel.
	 */
	public EditableFeedbackComponent(Customer customer) {
		Dimension dimension = new Dimension(400, 100);
		this.setPreferredSize(new Dimension(451, 300));
		this.setMinimumSize(new Dimension(451, 300));
		this.setMaximumSize(new Dimension(451, 300));
		setBackground(new Color(248, 248, 255));
		setLayout(null);

		this.customer = customer;
		this.feedbackService = FeedbackServiceImpl.getInstance();
		// JTextArea for description
		txtDescription = new JTextArea("");
		txtDescription.setToolTipText("");
		txtDescription.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDescription.setBounds(10, 49, 250, 240);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		txtDescription.setOpaque(true);
		txtDescription.setBackground(new Color(255, 255, 255)); // Match the background with the JPanel
		txtDescription.setBorder(null); // No border to mimic a JLabel

		add(txtDescription);

		JLabel lblNewLabel = new JLabel("Tell us about your recent experience");
		lblNewLabel.setBounds(10, 11, 250, 27);
		add(lblNewLabel);

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(5, 0, 5, 1));
		spinner.setFont(new Font("Arial", Font.BOLD, 16));
		spinner.setBounds(376, 169, 62, 27);
		add(spinner);

		anonChckBox = new JCheckBox("Anonymous Post");
		anonChckBox.setBounds(266, 225, 172, 23);
		add(anonChckBox);

		JLabel lblRateUsOut = new JLabel("Rate us out of 5");
		lblRateUsOut.setBounds(270, 171, 96, 27);
		add(lblRateUsOut);

		btnNewButton = new JButton("Post");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (anonChckBox.isSelected()) {
					feedbackService.addNewAnonymousFeedback(txtDescription.getText(), (int) spinner.getValue());
				} else {
					feedbackService.addNewFeedback(txtDescription.getText(), (int) spinner.getValue(), customer);

				}
				JOptionPane.showMessageDialog(EditableFeedbackComponent.this, "Feedback posted successfully", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				PlatePlanMain.switchPanels(new CustomerFeedbackScreen(customer));
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton.setBounds(304, 255, 105, 34);
		add(btnNewButton);

	}
}
