package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import database.StubDataBaseRecords;
import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;

class DataBaseIntegrationTests {
	AccountService accountService;
	ReservationService reservationService;
	DataBase db;
	@BeforeEach
	void setUp() throws Exception {
		DataBaseFactory.ENVIRONMENT = "production";
		ServiceFactory.setUpServices();
		accountService = AccountsServiceImpl.getInstance();
		reservationService = ReservationServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		
	}
	
	@Test
	void testGetBusinessAccount ()
	{
		Business business = db.getBusinessAccount();
		
		assertNotNull(business);
		assertEquals(business.getEmail(), "alfredo");
	}
	
	@Test
	void testInsertRecord ()
	{
		Customer customer = new Customer("idontexist", "john", "doe", "pass");
		assertTrue(db.insertRecord(SQLTables.ACCOUNTS_TABLE, customer));
		assertTrue(db.deleteDataBaseEntry(SQLTables.ACCOUNTS_TABLE, "idontexist"));
	}
	
	@Test
	void testGetCustomerAccount ()
	{
		Customer customer;
		try {
			customer = db.getCustomerAccount("john");
			assertNotNull(customer);
			assertEquals("john", customer.getFirstName());
			assertEquals("doe", customer.getLastName());

		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@Test
	void testGetCustomerAccount_fail ()
	{
		Customer customer;
		assertThrows(AccountNotFoundException.class, () -> {
            db.getCustomerAccount("idontexist@example.com");
        });			
	}
	
	@Test
	void testGetAllTables ()
	{
		List<Table> tables = db.getAllTables();
		assertNotNull(tables);
		assertTrue(tables.size()>0);
	}
	
	@Test
	void testGetAllServers ()
	{
		List<Server> servers = db.getAllServers();
		assertNotNull(servers);
		assertTrue(servers.size()>0);
	}
	
	@Test
	void testGetReservationForDate ()
	{
		LocalDate date = LocalDate.parse("2024-03-02");
		List<Reservation> reservations = db.getReservationsForDate(date);
		assertNotNull(reservations);
	}
	
	@Test
	void testGetAllReservations ()
	{
		List<Reservation> reservations = db.getAllReservations();
		assertNotNull(reservations);
		assertTrue(reservations.size()>0);
	}
	
	@Test
	void testGetResForCustomer () throws AccountNotFoundException
	{
		Reservation reservation = new Reservation();
        reservation.setId("temp");
        reservation.setDate(LocalDate.now());
        reservation.setPartySize(0);
        reservation.setServerId("");
        reservation.setTableId("");
        reservation.setTime(new TimeSlot(LocalTime.now(), LocalTime.now()));
        reservation.setCustomerId("john");
        reservation.setSpecialNotes("");
        assertTrue(db.insertRecord(SQLTables.RESERVATION_TABLE, reservation));
        
        List<Reservation> reservations = db.getCustomerReservations("john");
        assertNotNull(reservations);
        assertTrue(reservations.size() > 0);
        assertTrue(db.deleteDataBaseEntry(SQLTables.RESERVATION_TABLE, "temp"));
	}
	
	@Test
	void testGetResWithId () throws AccountNotFoundException
	{
		Reservation reservation = new Reservation();
		reservation.setId("temp");
		reservation.setDate(LocalDate.now());
		reservation.setPartySize(0);
		reservation.setServerId("");
		reservation.setTableId("");
		reservation.setTime(new TimeSlot(LocalTime.now(), LocalTime.now()));
		reservation.setCustomerId("");
		reservation.setSpecialNotes("");
		assertTrue(db.insertRecord(SQLTables.RESERVATION_TABLE, reservation));

		Reservation reservations = db.getReservationWithId("temp");
		assertNotNull(reservations);
		assertTrue(db.deleteDataBaseEntry(SQLTables.RESERVATION_TABLE, "temp"));
	}
	
	@Test
	void testGetAllMenuItems ()
	{
		List<MenuItem> menuItems = db.getAllMenuItems(SQLTables.MENU_TABLE);
		assertNotNull(menuItems);
		assertTrue(menuItems.size()>0);
	}
	
	@Test
	void testUpdateDataBase ()
	{
		List<MenuItem> menuItems = db.getAllMenuItems(SQLTables.MENU_TABLE);
		
		assertTrue(db.updateDataBaseEntry(menuItems.get(0), SQLTables.MENU_TABLE));
	}
	
	@Test
	void testGetAllFeedback()
	{
		List<Feedback> feedbacks = db.getAllFeedbacks();
		assertNotNull(feedbacks);
		assertTrue(feedbacks.size()>0);
	}

	
}
