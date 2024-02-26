package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DataBaseFactory;
import database.DataBaseStubImpl;
import dto.Customer;
import dto.Reservation;
import dto.TimeSlot;
import misc.StubDataBaseRecords;
import service_interfaces.AccountService;
import services.AccountsServiceImpl;
import services.ReservationServiceImpl;

class ReservationServiceTest {
	private ReservationServiceImpl reservationService;
	private DataBaseStubImpl dataBase;
	private AccountService accountService;

	@BeforeEach
	void setUp() {
		DataBaseFactory.ENVIRONMENT = "development";
		dataBase = DataBaseStubImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		accountService = new AccountsServiceImpl();
		StubDataBaseRecords.reset();
	}

	@Test
	void createCustomerReservation_Successful() {
		Customer customer = new Customer("johndoe@example.com", "john", "doe", "password");
		LocalDate date = LocalDate.now();
		TimeSlot slot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));
		int capacity = 4;
		String specialNotes = "Near window";

		Reservation result = reservationService.createCustomerReservation(customer, date, slot, capacity, specialNotes);

		assertNotNull(result);
		assertEquals(customer.getEmail(), result.getCustomerId());
	}

	@Test
	void createCustomerReservation_NoTableAvailable() {
		Customer customer = new Customer("johndoe@example.com", "john", "doe", "password");
		LocalDate date = LocalDate.now();
		TimeSlot slot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));
		int capacity = 100; // Assuming no table can accommodate 100 people
		String specialNotes = "Large group";

		Reservation result = reservationService.createCustomerReservation(customer, date, slot, capacity, specialNotes);

		assertNull(result);
	}

	@Test
	void getCustomerReservation_ExistingReservations() {
		dataBase = DataBaseStubImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		StubDataBaseRecords.reservations.clear(); // clearing any previous reservations

		// setting up a fake reservation
		String customerEmail = "max@email.com";
		Reservation fakeReservation = new Reservation("fakeReservationId", customerEmail, LocalDate.now(),
				new TimeSlot(LocalTime.of(18, 0), LocalTime.of(20, 0)), "Test reservation", "table1", 4);
		StubDataBaseRecords.reservations.add(fakeReservation);

		List<Reservation> results = reservationService.getCustomerReservation(customerEmail);

		assertNotNull(results);
		assertFalse(results.isEmpty());
		assertEquals(1, results.size());
	}

	@Test
	void getCustomerReservation_NoReservations() {
		String customerEmail = "newemail@example.com";

		List<Reservation> results = reservationService.getCustomerReservation(customerEmail);

		assertTrue(results.isEmpty());
	}

	@Test
	void createCustomerReservation_NonOverlappingTimeSlotsSameDay() {
		dataBase = DataBaseStubImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		StubDataBaseRecords.reservations.clear();

		dataBase = DataBaseStubImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		StubDataBaseRecords.reservations.clear();
		String customerEmail = "johndoe@example.com";
		Customer customer = new Customer("johndoe@example.com", "john", "doe", "password");
		LocalDate reservationDate = LocalDate.now();
		TimeSlot existingSlot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));

		// adding an existing reservation for the customer
		Reservation existingReservation = new Reservation("resId1", customerEmail, reservationDate, existingSlot,
				"Existing reservation", "table1", 4);
		StubDataBaseRecords.reservations.add(existingReservation); // Add to the fake database

		// attempt to create a new reservation for the same day but different time slot
		TimeSlot newSlot = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(18, 0));
		String specialNotes = "New reservation";

		Reservation newReservation = reservationService.createCustomerReservation(customer, reservationDate, newSlot, 4,
				specialNotes);

		assertNotNull(newReservation, "Expected a new reservation to be created as the time slots do not overlap");
	}

	@Test
	void createCustomerReservation_AccountNotFoundException() {
		dataBase = DataBaseStubImpl.getInstance();
		reservationService = new ReservationServiceImpl();
		StubDataBaseRecords.reservations.clear();

		String customerEmail = "johndoe@example.com";
		Customer customer = new Customer("johndoe@example.com", "john", "doe", "password");

		// ensure no reservations for this customer in StubDataBaseRecords
		StubDataBaseRecords.reservations.removeIf(res -> res.getCustomerId().equals(customerEmail));

		// create a new reservation for a date
		LocalDate reservationDate = LocalDate.now();
		TimeSlot slot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));
		String specialNotes = "Test reservation";

		Reservation result = reservationService.createCustomerReservation(customer, reservationDate, slot, 4,
				specialNotes);

		assertNotNull(result, "Reservation should be created as there are no existing reservations for this customer");
	}

	@Test
	void createCustomerReservation_sameDay() {

		Customer customer = accountService.getCustomerAccountDetails("john");
		LocalDate reservationDate = LocalDate.now();
		TimeSlot existingSlot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));

		Reservation reservation = reservationService.createCustomerReservation(customer, reservationDate, existingSlot,
				0, "");

		Reservation reservationExisting = reservationService.createCustomerReservation(customer, reservationDate,
				existingSlot, 0, "");

		assertNull(reservationExisting);
	}

	@Test
	void createCustomerReservation_sameDayWrongCustomer() {

		Customer customer = new Customer("idonotexist", "john", "doe", "pass", new ArrayList<>());
		LocalDate reservationDate = LocalDate.now();
		TimeSlot existingSlot = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(14, 0));

		Reservation reservation = reservationService.createCustomerReservation(customer, reservationDate, existingSlot,
				0, "");

		Reservation reservationExisting = reservationService.createCustomerReservation(customer, reservationDate,
				existingSlot, 0, "");

		assertNotNull(reservationExisting);
	}

}
