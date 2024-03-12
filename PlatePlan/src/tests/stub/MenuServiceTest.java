package tests.stub;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import database.StubDataBaseRecords;
import dto.Customer;
import dto.MenuItem;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import service_interfaces.MenuService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.MenuServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

class MenuServiceTest {
	private MenuService menuService;
	private DataBase db;
	private StubDataBaseRecords stubDb;

	@BeforeEach
	void setUp() {
		DataBaseFactory.ENVIRONMENT = "development";
		ServiceFactory.setUpServices();
		menuService = MenuServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		stubDb = StubDataBaseRecords.getInstance();
		stubDb.reset();
	}

	@Test
	void getMenuItems_Business() {
		List<MenuItem> menuItemsfromService = menuService.getAllMenuItems(SQLTables.MENU_TABLE);
		List<MenuItem> menuItemsStub = stubDb.menus;
		assertEquals(menuItemsfromService, menuItemsStub);
	}

	@Test
	void getMenuItems_Customer() {
		List<MenuItem> menuItemsfromService = menuService.getAllMenuItems(SQLTables.CUSTOMER_MENU_TABLE);
		List<MenuItem> menuItemsStub = stubDb.customer_menu;
		assertEquals(menuItemsfromService, menuItemsStub);
	}

	@Test
	void updateMenuItem_Success() {
		MenuItem menuItem = stubDb.menus.get(0);
		menuItem.setPrice((float) 5.99);

		assertTrue(menuService.updateMenuItem(menuItem));
		assertTrue(stubDb.menus.contains(menuItem));
	}

	@Test
	void updateMenuItem_Fail() {
		MenuItem menuItem = new MenuItem();
		menuItem.setId("fake");

		assertFalse(menuService.updateMenuItem(menuItem));
		assertFalse(stubDb.menus.contains(menuItem));
	}

	@Test
	void addMenuItem() {
		MenuItem newMenuItem = new MenuItem(UUID.randomUUID().toString(), "TITLE", "DESCRIPTION", 0L);

		assertNotNull(menuService.addMenuItem());
		assertEquals(newMenuItem.getName(), stubDb.menus.get(stubDb.menus.size() - 1).getName());
	}
	
	@Test
	void deletMenuItem() {
		MenuItem menuItem = new MenuItem();
		
		menuItem.setId("1");
		assertTrue(menuService.deleteMenuItem(menuItem));
	}
	
	@Test
	void deletMenuItem_fail() {
		MenuItem menuItem = new MenuItem();
		
		menuItem.setId("fake");
		assertFalse(menuService.deleteMenuItem(menuItem));
	}

}
