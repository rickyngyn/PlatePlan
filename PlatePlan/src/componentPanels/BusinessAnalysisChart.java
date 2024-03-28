package componentPanels;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

public class BusinessAnalysisChart extends JPanel {
	private TimeSeriesCollection dataset;
	private String chartTitle;
	private String xAxisLabel;
	private String yAxisLabel;
	private boolean showLegend;
	private boolean useTooltips;
	private boolean showLineOfBestFit;

	public BusinessAnalysisChart(List<LocalDate> dates, List<Number> values, String seriesName, String chartTitle,
			String xAxisLabel, String yAxisLabel, boolean showLegend, boolean showLineOfBestFit) {

		this.chartTitle = chartTitle;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
		this.showLegend = showLegend;
		this.useTooltips = true;
		this.showLineOfBestFit = showLineOfBestFit;

		dataset = new TimeSeriesCollection();
		TimeSeries series = new TimeSeries(seriesName);

		for (int i = 0; i < dates.size(); i++) {
			series.add(new Day(Date.from(dates.get(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())),
					values.get(i));
		}
		dataset.addSeries(series);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initializePanel();
	}

	private void initializePanel() {
		JFreeChart chart = createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 300));
		chartPanel.setMinimumSize(new Dimension(800, 300));
		chartPanel.setMaximumSize(new Dimension(800, 300));

		if (this.showLineOfBestFit)
		{
			addLineOfBestFit(dataset, 15);

		}
		add(chartPanel);
	}

	private JFreeChart createChart() {
		return ChartFactory.createTimeSeriesChart(chartTitle, // Chart title
				xAxisLabel, // X-Axis Label
				yAxisLabel, // Y-Axis Label
				dataset, // Dataset
				showLegend, // Show Legend
				useTooltips, // Use tooltips
				false // Configure URLs
		);
	}

	public void addLineOfBestFit(TimeSeriesCollection dataset, int extraDays) {
	    TimeSeries dataSeries = dataset.getSeries(0);

	    double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
	    int n = dataSeries.getItemCount();
	    long firstX = dataSeries.getTimePeriod(0).getFirstMillisecond();
	    long lastX = dataSeries.getTimePeriod(n - 1).getFirstMillisecond();

	    for (int i = 0; i < n; i++) {
	        TimeSeriesDataItem item = dataSeries.getDataItem(i);
	        double x = item.getPeriod().getFirstMillisecond();
	        double y = item.getValue().doubleValue();

	        sumX += x;
	        sumY += y;
	        sumXY += x * y;
	        sumXX += x * x;
	    }

	    double slope = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
	    double intercept = (sumY - slope * sumX) / n;

	    TimeSeries lineOfBestFitSeries = new TimeSeries("Projected Earnings");

	    // Add existing data points projected on the line of best fit
	    for (int i = 0; i < n; i++) {
	        TimeSeriesDataItem item = dataSeries.getDataItem(i);
	        double x = item.getPeriod().getFirstMillisecond();
	        double y = slope * x + intercept;
	        lineOfBestFitSeries.addOrUpdate(item.getPeriod(), y);
	    }

	    // Predict extra days
	    for (int i = 1; i <= extraDays; i++) {
	        // Assuming daily data, adjust the period accordingly if using different time units
	        long newX = lastX + i * 24 * 60 * 60 * 1000; // Add i days in milliseconds
	        double newY = slope * newX + intercept;
	        lineOfBestFitSeries.addOrUpdate(new Day(new java.util.Date(newX)), newY);
	    }

	    dataset.addSeries(lineOfBestFitSeries);
	}
}
