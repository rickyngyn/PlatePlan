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

import componentPanels.BusinessAnalysisChart;
import componentPanels.BusinessFeedbackComponent;
import componentPanels.FeedbackAnalysisPanel;
import customerPanels.Constants;
import dto.Business;
import dto.Feedback;
import main.PlatePlanMain;
import service_interfaces.FeedbackService;
import service_interfaces.OrdersService;
import services.FeedbackServiceImpl;
import services.GraphGenerators;
import services.OrdersServiceImpl;

public class BusinessAnalytics extends JPanel {

	private static final long serialVersionUID = 1L;
	private Business business;
	private OrdersService ordersService;
	private FeedbackService feedbackService;

	/**
	 * Create the panel.
	 */
	public BusinessAnalytics(Business business) {
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
		this.ordersService = OrdersServiceImpl.getInstance();
		this.feedbackService = FeedbackServiceImpl.getInstance();

		List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

		FeedbackAnalysisPanel feedbackAnalysisPanel = new FeedbackAnalysisPanel(feedbacks);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 1044, 591);
		add(scrollPane);


		JPanel scrollablePanel = new JPanel();
		scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
		scrollablePanel.setBackground(new Color(255, 250, 250)); // Optional, to match your frame background

		BusinessAnalysisChart receiptAnalytics = GraphGenerators.generateOrdersChart();
		BusinessAnalysisChart tipAnalytics = GraphGenerators.generateTipChart();

		scrollablePanel.add(receiptAnalytics);
		scrollablePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds space between components
		scrollablePanel.add(tipAnalytics);
		scrollablePanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds space between components
		scrollablePanel.add(feedbackAnalysisPanel);

//		feedbackAnalysisPanel.setSize(450, 450);
//		feedbackAnalysisPanel.setLocation(620, 160);

		// Finally, set the scrollable panel as the view for the scrollPane
		scrollPane.setViewportView(scrollablePanel);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlatePlanMain.switchPanels(new BusinessHomeView(business));
			}
		});
		btnBack.setBounds(979, 10, 89, 23);
		add(btnBack);

	}
}
