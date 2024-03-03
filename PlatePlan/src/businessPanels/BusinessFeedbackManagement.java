package businessPanels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import componentPanels.BusinessMenuComponent;
import componentPanels.CustomerMenuComponent;
import componentPanels.EditableFeedbackComponent;
import componentPanels.FeedbackAnalysisPanel;
import componentPanels.StaticFeedbackComponent;
import customerPanels.Constants;
import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import main.PlatePlanMain;
import service_interfaces.FeedbackService;
import service_interfaces.MenuService;
import service_interfaces.ServerService;
import services.FeedbackServiceImpl;
import services.MenuServiceImpl;
import services.ServerServiceImpl;
import javax.swing.JLabel;

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
			containerPanel.add(new StaticFeedbackComponent(feedback));
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
