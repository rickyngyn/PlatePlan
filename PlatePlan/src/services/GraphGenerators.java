package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import componentPanels.BusinessAnalysisChart;
import database.DataBase;
import database.DataBaseFactory;
import dto.Receipt;
import service_interfaces.OrdersService;
import service_interfaces.ReservationService;

public class GraphGenerators {
	private static OrdersService ordersService = OrdersServiceImpl.getInstance();
	private static ReservationService reservationService = ReservationServiceImpl.getInstance();
	private static DataBase database = DataBaseFactory.getDatabase();

	public static BusinessAnalysisChart generateOrdersChart() {
		List<Receipt> receipts = database.getAllReceipts();

		Map<LocalDate, Double> subtotalSumByDate = receipts.stream()
				.collect(Collectors.groupingBy(Receipt::getDate, Collectors.summingDouble(Receipt::getSubtotal)));

		// Extracting the list of dates
		List<LocalDate> dates = subtotalSumByDate.keySet().stream().sorted().collect(Collectors.toList());

		// Extracting the list of subtotal sums
		List<Number> values = dates.stream().map(subtotalSumByDate::get).collect(Collectors.toList());

		BusinessAnalysisChart businessAnalysisChart = new BusinessAnalysisChart(dates, values, "Daily Income",
				"Income Grouped by Date", "Date", "Subtotal Income($)", true, true);

		return businessAnalysisChart;

	}
	
	public static BusinessAnalysisChart generateTipChart() {
		List<Receipt> receipts = database.getAllReceipts();

		Map<LocalDate, Double> subtotalSumByDate = receipts.stream()
				.collect(Collectors.groupingBy(Receipt::getDate, Collectors.summingDouble(Receipt::getTipAmount)));

		// Extracting the list of dates
		List<LocalDate> dates = subtotalSumByDate.keySet().stream().sorted().collect(Collectors.toList());

		// Extracting the list of subtotal sums
		List<Number> values = dates.stream().map(subtotalSumByDate::get).collect(Collectors.toList());

		BusinessAnalysisChart businessAnalysisChart = new BusinessAnalysisChart(dates, values, "Daily Income",
				"Tip Amount Grouped By Day", "Date", "Tip Amount ($)", true, true);

		return businessAnalysisChart;

	}

}
