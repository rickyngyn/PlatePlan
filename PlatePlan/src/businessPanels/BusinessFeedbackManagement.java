package businessPanels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import componentPanels.BusinessFeedbackComponent;
import componentPanels.FeedbackAnalysisPanel;
import customerPanels.Constants;
import dto.Business;
import dto.Feedback;
import main.PlatePlanMain;
import service_interfaces.FeedbackService;
import services.FeedbackServiceImpl;

public class BusinessFeedbackManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<Feedback> feedbacks;
	private FeedbackService feedbackService;
	private Business business;

	/**
	 * Create the panel.
	 */
	public BusinessFeedbackManagement(Business business) {
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
		this.business = business;
		this.feedbackService = FeedbackServiceImpl.getInstance();
		this.feedbacks = feedbackService.getAllFeedbacks();

		JPanel containerPanel = new JPanel();
		containerPanel.setBackground(new Color(255, 255, 255));
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
		containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Adding your components (e.g., menu items) to the container panel
		for (Feedback feedback : this.feedbacks) {
			containerPanel.add(new BusinessFeedbackComponent(feedback));
			containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		// Create a JScrollPane that wraps the container panel
		JScrollPane scrollPane = new JScrollPane(containerPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 10, 600, 600);

		// Add the JScrollPane to the frame instead of the container panel directly
		add(scrollPane);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnBack.setBounds(979, 10, 89, 23);
		add(btnBack);

		// Label for "Current Average Rating"
		JLabel lblAvgRating = new JLabel("Current Average Rating: " + feedbackService.getAverageRating());
		lblAvgRating.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size and make it bold
		// Set position and size directly
		lblAvgRating.setBounds(661, 59, 400, 30); // x, y, width, height
		add(lblAvgRating);

		// Label for "Number Of Reviews"
		JLabel lblNumberOfReviews = new JLabel("Number Of Reviews: " + feedbacks.size());
		lblNumberOfReviews.setFont(new Font("Arial", Font.BOLD, 16)); // Increase font size and make it bold
		// Set position and size directly
		lblNumberOfReviews.setBounds(661, 100, 400, 30); // x, y, width, height
		add(lblNumberOfReviews);

		FeedbackAnalysisPanel feedbackAnalysisPanel = new FeedbackAnalysisPanel(feedbacks);
		feedbackAnalysisPanel.setSize(450, 450);
		feedbackAnalysisPanel.setLocation(620, 160);

		add(feedbackAnalysisPanel);
	}
}
