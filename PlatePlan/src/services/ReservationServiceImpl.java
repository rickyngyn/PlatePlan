package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Customer;
import dto.Reservation;
import dto.Table;
import dto.TimeSlot;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class ReservationServiceImpl implements ReservationService {

	private static ReservationService instance;

	private DataBase db;
	private ServerService serviceUtils;
	private TablesService tablesService;
	private AccountService accountService;

	private ReservationServiceImpl() {
	}

	@Override
	public void initializeDependency(AccountService accountService, TablesService tablesService,
			ServerService serviceUtils) {

		db = DataBaseFactory.getDatabase();
		this.accountService = accountService;
		this.serviceUtils = serviceUtils;
		this.tablesService = tablesService;
	}

	// Public static method to get the instance of the class
	public static ReservationService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new ReservationServiceImpl();
		}
		// Return the existing instance
		return instance;

	}

	@Override
	public List<Reservation> getCustomerReservation(String email) {

		try {
			return db.getCustomerReservations(email);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public Reservation createCustomerReservation(Customer customer, LocalDate date, TimeSlot slot, int cap,
			String specialNotes) {

		List<Table> tablesAvailable = tablesService.getTablesMatchingResReq(cap);

		if (tablesAvailable.isEmpty()) {
			return null;
		}

		Reservation reservation = new Reservation(UUID.randomUUID().toString(), customer.getEmail(), date, slot,
				specialNotes, serviceUtils.getAllServersMap().get(tablesAvailable.get(0).getServer()),
				tablesAvailable.get(0).getId(), cap);

		if (customer.getReservations() != null) {
			try {
				for (Reservation cusReservation : db.getCustomerReservations(customer.getEmail())) {
					if (date.equals(cusReservation.getDate())) {
						return null;
					}
				}
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			customer.setReservations(new ArrayList<String>());
		}

		customer.getReservations().add(reservation.getId());

		if (db.insertRecord(SQLTables.RESERVATION_TABLE, reservation)) {
			System.out.println("Reservation successfully submitted " + reservation);
			return reservation;
		}

		return null;
	}
	
	@Override
	public boolean updateReservation (Reservation reservation)
	{
		return db.updateDataBaseEntry(reservation, SQLTables.RESERVATION_TABLE);
	}

	@Override
	public boolean cancelReservation(String reservationId) {
		return db.deleteDataBaseEntry(SQLTables.RESERVATION_TABLE, reservationId);
	}

	@Override
	public List<Reservation> getAllReservations() {
		return db.getAllReservations();
	}

	// Other service methods would go here
}