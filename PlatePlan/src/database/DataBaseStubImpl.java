package database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Reservation;
import dto.Server;
import dto.Table;
import misc.StubDataBaseRecords;

public class DataBaseStubImpl implements DataBase {

	private static DataBaseStubImpl dataBaseInstance;

	private DataBaseStubImpl() {
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

				StubDataBaseRecords.reservations.add(reservation);

			} else if (tableName.equals(SQLTables.TABLES_TABLE)) {
				Table table = (Table) object;
				StubDataBaseRecords.tables.add(table);
			} else if (tableName.equals(SQLTables.ACCOUNTS_TABLE)) {
				Customer customer = (Customer) object;
				try {
					getCustomerAccount(customer.getEmail());
					return false;
				} catch (Exception e) {
					StubDataBaseRecords.customers.add(customer);

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
		List<Customer> customers = StubDataBaseRecords.customers;

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
		return StubDataBaseRecords.business;
	}

	@Override
	public boolean deleteTable(String id) {

		for (Table table : StubDataBaseRecords.tables) {
			if (table.getId().equals(id)) {
				StubDataBaseRecords.tables.remove(table);
				return true;
			}
		}

		return false;
	}
	
	@Override
	public List<Table> getAllTables() {
		return StubDataBaseRecords.tables;
	}
	
	@Override
	public List<Server> getAllServers() {
		return StubDataBaseRecords.servers;
	}
}
