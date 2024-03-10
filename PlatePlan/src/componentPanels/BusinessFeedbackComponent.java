package componentPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import dto.Feedback;
import main.PlatePlanMain;

public class BusinessFeedbackComponent extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public BusinessFeedbackComponent(Feedback feedback) {
		Dimension dimension = new Dimension(400, 100);
		this.setPreferredSize(new Dimension(540, 150));
		this.setMinimumSize(new Dimension(600, 150));
		this.setMaximumSize(new Dimension(600, 150));
		setBackground(new Color(248, 248, 255));
		setLayout(null);

		// JTextArea for description
		JTextArea txtDescription = new JTextArea("FEEDBACK");

		txtDescription.setEditable(false); // This line makes the JTextArea editable
		txtDescription.setFont(new Font("Arial", Font.PLAIN, 12));
		txtDescription.setBounds(10, 58, 500, 81);
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		txtDescription.setOpaque(true);
		txtDescription.setBackground(new Color(248, 248, 255)); // Match the background with the JPanel
		txtDescription.setBorder(null); // No border to mimic a JLabel

		add(txtDescription);
		
		JLabel lblTimestamp = new JLabel("TIMESTAMP");
		lblTimestamp.setFont(new Font("Arial", Font.ITALIC, 14));
		lblTimestamp.setBounds(214, 24, 143, 24);
		add(lblTimestamp);
		
		JLabel lblRating = new JLabel("RATING");
		lblRating.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
		lblRating.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating.setBounds(440, 24, 70, 24);
		add(lblRating);
		
		JLabel lblLeftby = new JLabel("LEFT BY");
		lblLeftby.setFont(new Font("Arial", Font.PLAIN, 14));
		lblLeftby.setBounds(103, 23, 122, 24);
		add(lblLeftby);
		
		JLabel lblPostedBy = new JLabel("Posted By: ");
		lblPostedBy.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPostedBy.setBounds(10, 23, 84, 24);
		add(lblPostedBy);
		
		JLabel lblRating_2 = new JLabel("Rating: ");
		lblRating_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblRating_2.setFont(new Font("Arial", Font.BOLD, 14));
		lblRating_2.setBounds(357, 23, 84, 24);
		add(lblRating_2);
		
		if (feedback != null)
		{
			lblTimestamp.setText(feedback.getTimestamp().toString());
			lblRating.setText(feedback.getRating() + "/5");
			lblLeftby.setText(feedback.getCustomer_id());
			txtDescription.setText(feedback.getFeedback());
			PlatePlanMain.refreshPage();
		}

	}

}
