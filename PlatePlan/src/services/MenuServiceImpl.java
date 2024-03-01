package services;

import java.util.List;

import database.DataBase;
import database.DataBaseFactory;
import dto.MenuItem;
import service_interfaces.AccountService;
import service_interfaces.MenuService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class MenuServiceImpl implements MenuService {
	private static MenuService instance;

	private DataBase db;
	private ReservationService reservationService;
	private TablesService tablesService;
	private ServerService serviceUtils;
	private AccountService accountService;

	private MenuServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public static MenuService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new MenuServiceImpl();
		}
		// Return the existing instance
		return instance;

	}

	@Override
	public void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils, AccountService accountService) {

		db = DataBaseFactory.getDatabase();
		this.reservationService = reservationService;
		this.serviceUtils = serviceUtils;
		this.tablesService = tablesService;
		this.accountService = accountService;
	}
	

	@Override
	public List<MenuItem> getAllMenuItems() {
		return db.getAllMenuItems();
	}
	
	
	@Override
	public boolean updateMenuItem(MenuItem menuItem) {
		return db.updateMenuItem(menuItem);
	}

	@Override
	public MenuItem addMenuItem(String title, float price, String description) {
		// TODO Auto-generated method stub
		return null;
	}

}
