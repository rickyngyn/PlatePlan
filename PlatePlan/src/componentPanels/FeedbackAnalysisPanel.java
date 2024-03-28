package componentPanels;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import dto.Feedback;

public class FeedbackAnalysisPanel extends JPanel {
	private List<Feedback> feedbacks;

	public FeedbackAnalysisPanel(List<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializePanel();
	}

	private void initializePanel() {
	    // Set the layout of the container panel to BoxLayout, aligning components along the Y-axis
	    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	    // Create the first chart for reviews count
	    JFreeChart reviewsChart = createChart(true);
	    ChartPanel reviewsChartPanel = new ChartPanel(reviewsChart);
	    reviewsChartPanel.setPreferredSize(new Dimension(800, 300));
	    reviewsChartPanel.setMinimumSize(new Dimension(800, 300));
	    reviewsChartPanel.setMaximumSize(new Dimension(800, 300));
	    // Create the second chart for average rating
	    JFreeChart averageRatingChart = createChart(false);
	    ChartPanel averageRatingChartPanel = new ChartPanel(averageRatingChart);
	    averageRatingChartPanel.setPreferredSize(new Dimension(800, 300));
	    averageRatingChartPanel.setMinimumSize(new Dimension(800, 300));
	    averageRatingChartPanel.setMaximumSize(new Dimension(800, 300));
//	    averageRatingChartPanel.setAlignmentX(LEFT_ALIGNMENT); // Align the panel to the left

	    // Adding the first chart panel to the container
	    this.add(reviewsChartPanel);

	    // Adding a 20px vertical space between the two charts
	    this.add(Box.createRigidArea(new Dimension(0, 20)));

	    // Adding the second chart panel to the container
	    this.add(averageRatingChartPanel);
	}


	private JFreeChart createChart(boolean isReviewsCount) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

		Map<LocalDate, List<Feedback>> groupedByDate = feedbacks.stream()
				.filter(f -> f.getTimestamp().isAfter(thirtyDaysAgo))
				.collect(Collectors.groupingBy(f -> f.getTimestamp().toLocalDate()));

		if (isReviewsCount) {
			TimeSeries series = new TimeSeries("Reviews Count");
			groupedByDate.forEach((date, dailyFeedbacks) -> series.add(
					new Day(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())), dailyFeedbacks.size()));
			dataset.addSeries(series);

			return ChartFactory.createTimeSeriesChart("Reviews Count per Day", // Chart title
					"Date", // X-Axis Label
					"Count", // Y-Axis Label
					dataset, // Dataset
					true, // Show Legend
					true, // Use tooltips
					false // Configure URLs
			);
		} else {
			TimeSeries series = new TimeSeries("Average Rating");
			groupedByDate.forEach((date, dailyFeedbacks) -> series.add(
					new Day(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())),
					dailyFeedbacks.stream().mapToInt(Feedback::getRating).average().orElse(0)));
			dataset.addSeries(series);

			return ChartFactory.createTimeSeriesChart("Average Rating per Day", // Chart title
					"Date", // X-Axis Label
					"Rating", // Y-Axis Label
					dataset, // Dataset
					true, // Show Legend
					true, // Use tooltips
					false // Configure URLs
			);
		}
	}
}
