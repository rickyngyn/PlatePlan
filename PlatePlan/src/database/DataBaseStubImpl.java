package database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Reservation;
import dto.Server;
import dto.Table;

public class DataBaseStubImpl implements DataBase {

	private static DataBaseStubImpl dataBaseInstance;
	private StubDataBaseRecords db;
	private DataBaseStubImpl() {
		db = StubDataBaseRecords.getInstance();
	}

	public static synchronized DataBaseStubImpl getInstance() {
		if (dataBaseInstance == null) {
			dataBaseInstance = new DataBaseStubImpl();
		}
		return dataBaseInstance;
	}

	@Override
	public boolean insertRecord(String tableName, Object object) {
		// Simulate inserting a record into the database
		// (in a real database, you would execute SQL insert statements)
		try {
			if (tableName.equals(SQLTables.RESERVATION_TABLE)) {
				Reservation reservation = (Reservation) object;

				db.reservations.add(reservation);

			} else if (tableName.equals(SQLTables.TABLES_TABLE)) {
				Table table = (Table) object;
				db.tables.add(table);
			} else if (tableName.equals(SQLTables.ACCOUNTS_TABLE)) {
				Customer customer = (Customer) object;
				try {
					getCustomerAccount(customer.getEmail());
					return false;
				} catch (Exception e) {
					db.customers.add(customer);

				}
			}

			System.out.println("Inserting record into " + tableName + ": " + object.toString());

			return true; // Return true to indicate success

		} catch (Exception e) {
			System.out.println("Error inserting Record + " + object.toString());

		}
		return false;

	}

	@Override
	public Customer getCustomerAccount(String email) throws AccountNotFoundException {
		List<Customer> customers = db.customers;

		try {

			Customer customerFound = customers.stream().filter(customer -> customer.getEmail().equalsIgnoreCase(email))
					.findFirst()
					.orElseThrow(() -> new AccountNotFoundException("No customer with the given email " + email));
			return customerFound;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccountNotFoundException("No customer with the given email " + email);
		}
	}

	@Override
	public Business getBusinessAccount() {
		return db.business;
	}

	@Override
	public List<Table> getAllTables() {
		return db.tables;
	}

	@Override
	public List<Server> getAllServers() {
		return db.servers;
	}

	@Override
	public List<Reservation> getAllReservations() {
		return db.reservations != null ? db.reservations
				: new ArrayList<Reservation>();
	}

	@Override
	public List<Reservation> getReservationsForDate(LocalDate date) {

		System.out.println(date + " All Reservations: " + db.reservations);
		return db.reservations.stream().filter(reservation -> reservation.getDate().equals(date))
				.collect(Collectors.toList());

	}

	@Override
	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException {
		Customer customer = this.getCustomerAccount(email);

		if (customer != null) {

			List<Reservation> reservations = new ArrayList<Reservation>();
			for (Reservation resId : db.reservations) {
				reservations.add(getReservationWithId(resId.getId()));
			}
			reservations.removeAll(Collections.singleton(null));
			return reservations;
		}
		throw new AccountNotFoundException("No customer with the given email " + email);
	}

	@Override
	public Reservation getReservationWithId(String id) {
		return db.reservations.stream().filter(reservation -> reservation.getId().equals(id))
				.findFirst().orElse(null);

	}

	@Override
	public List<MenuItem> getAllMenuItems(String table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void publishCustomerMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteDataBaseEntry(String table, String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDataBaseEntry(Object object, String table) {
		// TODO Auto-generated method stub
		return false;
	}

}
