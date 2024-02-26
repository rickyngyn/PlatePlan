package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.DataBaseImpl;
import dto.Customer;
import dto.Table;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import service_interfaces.ServiceUtils;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;
import services.ServiceUtilsImpl;

class DataBaseIntegrationTest {
	private DataBase db;
	private ReservationService reservationService;
	private AccountService accountService;
	private ServiceUtils serviceUtils;
	
	@BeforeEach
	void setup()
	{
		DataBaseFactory.ENVIRONMENT = "production";
		db = DataBaseImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		accountService = new AccountsServiceImpl();
		serviceUtils = ServiceUtilsImpl.getInstance();
	}
	
	@Test
	void testInsertWithTables() {
		Table table = new Table ("randomID", 10, "Peter Parker");
		serviceUtils.registerTable("randomID", 10, "Peter Parker");
		System.out.println(db.getAllTables());
		assertTrue(db.getAllTables().contains(table));
		serviceUtils.deleteTable("randomID");
	}
	
	@Test
	void testDeleteWithTabls() {
		serviceUtils.registerTable("randomID", 10, "Peter Parker");
		
		assertTrue(serviceUtils.deleteTable("randomID"));
	}
	
	@Test
	void testGetAllTables() {
		
		assertNotNull(db.getAllTables());
		assertFalse(db.getAllTables().isEmpty());

	}
	
	

}
