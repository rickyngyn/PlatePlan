package services;

import java.util.List;
import java.util.UUID;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
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
	public List<MenuItem> getAllMenuItems(String table) {
		return db.getAllMenuItems(table);
	}

	@Override
	public boolean updateMenuItem(MenuItem menuItem) {
		return db.updateMenuItem(menuItem);
	}

	@Override
	public MenuItem addMenuItem() {
		MenuItem menuItem = new MenuItem(UUID.randomUUID().toString(), "TITLE", "DESCRIPTION", 0L);
		if (db.insertRecord(SQLTables.MENU_TABLE, menuItem)) {
			return menuItem;
		}
		return null;
	}

	@Override
	public boolean deleteMenuItem(MenuItem item) {
		return db.deleteDataBaseEntry(SQLTables.MENU_TABLE, item.getId());
	}

}
