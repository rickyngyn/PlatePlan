package tests;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import dto.Customer;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import misc.StubDataBaseRecords;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

class ServiceUtilsTest {
	private ReservationService reservationService;
	private ServerService serverService;
	private TablesService tablesService;
	private DataBase db;

	@BeforeEach
	void setUp() {
		ServiceFactory.setUpServices();
		DataBaseFactory.ENVIRONMENT = "development";
		reservationService = ReservationServiceImpl.getInstance();
		tablesService = TablesServiceImpl.getInstance();
		serverService = ServerServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		StubDataBaseRecords.reset();
	}

	@Test
	void deleteTableTest() {
		tablesService.deleteTable("1");
		assertEquals(5, StubDataBaseRecords.tables.size());
		// Delete table with ID 1. Was 6 tables now should be 5
		boolean result = tablesService.deleteTable("0");
		assertFalse(result);
		// No existing table with ID 0. Therefore should return false
	}

	@Test
	void availableTablesTest() {
		LocalDate date = LocalDate.now();
		int capacity = 8;
		Customer customer = new Customer("johndoe@example.com", "john", "doe", "password");
		TimeSlot slot = new TimeSlot(LocalTime.of(12, 00), LocalTime.of(13, 30));
		String specialNotes = "Near Window";
		reservationService.createCustomerReservation(customer, date, slot, capacity, specialNotes);

		List<TimeSlot> tables = tablesService.getAvailableTables(date, capacity);
		List<TimeSlot> actual = new ArrayList<>(db.getBusinessAccount().getAllTimeSlots());
		assertNotEquals(actual, tables);
		// Time Slot 12-13:30 should be removed therefore not equals to ALL TIME SLOTS
	}

	@Test
	void getTablesMatchingResReq() {
		int capacity = 4;
		List<Table> results1 = tablesService.getTablesMatchingResReq(capacity);
		List<Table> actual1 = new ArrayList<Table>(Arrays.asList(new Table("1", 4, "1"), new Table("4", 6, "2"),
				new Table("5", 8, "3"), new Table("6", 4, "3")));

		System.out.println(results1);
		System.out.println(actual1);

		assertEquals(actual1, results1);
	}

	@Test
	void getAllTables() {

		Map<String, String> result = serverService.getAllServersMap();

		Set<String> ids = new HashSet<>();
		for (Server server : StubDataBaseRecords.servers) {
			ids.add(server.getId());
		}

		assertEquals(ids, result.keySet());
	}

	@Test
	void testRegisterTableSuccess() {

		boolean result = tablesService.registerTable("newid", 10, "1");

		assertTrue(result);
	}

	@Test
	void testRegisterTableFail() {

		boolean result = tablesService.registerTable("1", 10, "1");

		assertFalse(result);
	}
}
