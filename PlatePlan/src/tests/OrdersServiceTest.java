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
import database.SQLTables;
import database.StubDataBaseRecords;
import dto.Customer;
import dto.Order;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import main.ServiceFactory;
import service_interfaces.OrdersService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;
import services.OrdersServiceImpl;
import services.ReservationServiceImpl;
import services.ServerServiceImpl;
import services.TablesServiceImpl;

class OrdersServiceTest {
	private OrdersService ordersService;
	private DataBase db;
	private StubDataBaseRecords stubDb;

	@BeforeEach
	void setUp() {
		DataBaseFactory.ENVIRONMENT = "development";
		ServiceFactory.setUpServices();
		ordersService = OrdersServiceImpl.getInstance();
		db = DataBaseFactory.getDatabase();
		stubDb = StubDataBaseRecords.getInstance();
		stubDb.reset();
	}

	@Test
	public void getAllOrdersForReservationTest() {
		Reservation reservation = stubDb.reservations.get(0);
		List<Order> orders = ordersService.getAllOrdersForReservation(reservation);

		assertNotNull(orders);
		assertFalse(orders.isEmpty());

		Order order = orders.get(0);
		order.setQuantity(5);

		assertTrue(db.insertRecord(SQLTables.ORDERS_TABLE, order));
		orders = ordersService.getAllOrdersForReservation(reservation);
		assertTrue(orders.contains(order));

	}

	@Test
	public void getAllOrdersForDateTest() {
		Reservation reservation = stubDb.reservations.get(0);
		List<Order> orders = ordersService.getAllOrdersForReservation(reservation);

		assertNotNull(orders);
		assertFalse(orders.isEmpty());

		Order order = orders.get(0);
		order.setQuantity(5);
		assertTrue(db.insertRecord(SQLTables.ORDERS_TABLE, order));
		assertEquals(ordersService.getAllOrdersForDate(LocalDate.now()).size(), 1);
	}

	@Test
	public void updateOrdersTest() {
		Reservation reservation = stubDb.reservations.get(0);
		List<Order> orders = ordersService.getAllOrdersForReservation(reservation);

		assertNotNull(orders);
		assertFalse(orders.isEmpty());

		Order order = orders.get(0);
		order.setQuantity(5);
		assertTrue(db.insertRecord(SQLTables.ORDERS_TABLE, order));

		order.setQuantity(10);
		assertTrue(ordersService.updateOrder(order));

		assertTrue(stubDb.orders.contains(order));
	}

}
