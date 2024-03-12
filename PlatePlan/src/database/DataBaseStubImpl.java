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
			}else if (tableName.equals(SQLTables.MENU_TABLE)) {
				MenuItem menuItem = (MenuItem) object;
				db.menus.add(menuItem);
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
		return db.reservations != null ? db.reservations : new ArrayList<Reservation>();
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
		return db.reservations.stream().filter(reservation -> reservation.getId().equals(id)).findFirst().orElse(null);

	}

	@Override
	public List<MenuItem> getAllMenuItems(String table) {
		if (table.equals(SQLTables.CUSTOMER_MENU_TABLE)) {
			return db.customer_menu;
		} else {
			return db.menus;
		}
	}

	@Override
	public void publishCustomerMenu() {
		db.customer_menu.clear();
		db.customer_menu.addAll(db.menus);

	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		return db.feedbacks;
	}

	@Override
	public boolean deleteDataBaseEntry(String table, String id) {
		if (table.equals(SQLTables.RESERVATION_TABLE)) {
			for (Reservation object : db.reservations) {
				if (id.equals(object.getId())) {
					db.reservations.remove(object);
					return true;
				}
			}
		} else if (table.equals(SQLTables.TABLES_TABLE)) {
			for (Table object : db.tables) {
				if (id.equals(object.getId())) {
					db.tables.remove(object);
					return true;
				}
			}
		} else if (table.equals(SQLTables.SERVERS_TABLE)) {
			for (Server object : db.servers) {
				if (id.equals(object.getId())) {
					db.servers.remove(object);
					return true;
				}
			}
		} else if (table.equals(SQLTables.MENU_TABLE)) {
			for (MenuItem object : db.menus) {
				if (id.equals(object.getId())) {
					db.menus.remove(object);
					return true;
				}
			}
		} else if (table.equals(SQLTables.FEEDBACKS_TABLE)) {
			for (Feedback object : db.feedbacks) {
				if (id.equals(object.getId())) {
					db.feedbacks.remove(object);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean updateDataBaseEntry(Object object, String table) {
		if (table.equals(SQLTables.RESERVATION_TABLE)) {
			for (Reservation tempObj : db.reservations) {
				if (((Reservation) object).getId().equals(tempObj.getId())) {
					db.reservations.remove(tempObj);
					db.reservations.add(((Reservation) object));
					return true;
				}
			}
		} else if (table.equals(SQLTables.TABLES_TABLE)) {
			for (Table tempObj : db.tables) {
				if (((Table) object).getId().equals(tempObj.getId())) {
					db.tables.remove(tempObj);
					db.tables.add(((Table) object));
					return true;
				}
			}
		} else if (table.equals(SQLTables.SERVERS_TABLE)) {
			for (Server tempObj : db.servers) {
				if (((Server) object).getId().equals(tempObj.getId())) {
					db.servers.remove(tempObj);
					db.servers.add(((Server) object));
					return true;
				}
			}
		} else if (table.equals(SQLTables.MENU_TABLE)) {
			for (MenuItem tempObj : db.menus) {
				if (((MenuItem) object).getId().equals(tempObj.getId())) {
					db.menus.remove(tempObj);
					db.menus.add(((MenuItem) object));
					return true;
				}
			}
		} else if (table.equals(SQLTables.FEEDBACKS_TABLE)) {
			for (Feedback tempObj : db.feedbacks) {
				if (((Feedback) object).getId().equals(tempObj.getId())) {
					db.feedbacks.remove(tempObj);
					db.feedbacks.add(((Feedback) object));
					return true;
				}
			}
		}
		return false;
	}

}
