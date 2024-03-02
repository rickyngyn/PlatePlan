package main;

import service_interfaces.AccountService;
import service_interfaces.FeedbackService;
import service_interfaces.MenuService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.AccountsServiceImpl;
import services.FeedbackServiceImpl;
import services.MenuServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

public class ServiceFactory {

	private static AccountService accountService;
	private static ReservationService reservationService;
	private static TablesService tablesService;
	private static ServerService serviceUtils;
	private static MenuService menuService;
	private static FeedbackService feedbackService;

	public static void setUpServices() {
		System.out.println("Setting Up Accounts Service");
		accountService = AccountsServiceImpl.getInstance();

		System.out.println("Setting Up Reservation Service");
		reservationService = ReservationServiceImpl.getInstance();

		System.out.println("Setting Up Tables Service");
		tablesService = TablesServiceImpl.getInstance();

		System.out.println("Setting Up Service Utils");
		serviceUtils = ServerServiceImpl.getInstance();

		System.out.println("Setting Up Menu Service");
		menuService = MenuServiceImpl.getInstance();

		System.out.println("Setting Up Feedback Service");
		feedbackService = FeedbackServiceImpl.getInstance();
		
		System.out.println("Initializing Dependencies");
		accountService.initializeDependency(reservationService, tablesService, serviceUtils);
		reservationService.initializeDependency(accountService, tablesService, serviceUtils);
		tablesService.initializeDependency(accountService, reservationService, serviceUtils);
		serviceUtils.initializeDependency(accountService, reservationService, tablesService);
		menuService.initializeDependency(reservationService, tablesService, serviceUtils, accountService);
		feedbackService.initializeDependency(reservationService, tablesService, serviceUtils, accountService, menuService);

	}

}
