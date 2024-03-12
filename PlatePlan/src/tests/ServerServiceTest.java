package tests;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBase;
import database.DataBaseFactory;
import database.StubDataBaseRecords;
import dto.Customer;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

class ServerServiceTest {
	private ServerService serverService;
	private DataBase db;
	private StubDataBaseRecords stubDb;

	@BeforeEach
	void setUp() {
		DataBaseFactory.ENVIRONMENT = "development";
		ServiceFactory.setUpServices();
		serverService = ServerServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		stubDb = StubDataBaseRecords.getInstance();
		stubDb.reset();
	}

	@Test
	void testRegisterServer() {
		Server server = serverService.registerServer("bob", "the builder");

		assertNotNull(server);
		assertEquals("bob", server.getFirstName());
		assertEquals("the builder", server.getLastName());

	}

	@Test
	void testGetAllServers() {
		List<Server> servers = new ArrayList<Server>(Arrays.asList(new Server("1", "robert", "downey"),
				new Server("2", "peter", "parker"), new Server("3", "chris", "evans")));
		assertEquals(servers, serverService.getAllServers());

	}
	
	@Test
	void testDeleteServer() {
		
		assertTrue(serverService.deleteServer("1"));

	}

}
