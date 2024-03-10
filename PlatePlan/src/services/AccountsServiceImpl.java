package services;

import java.util.ArrayList;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Business;
import dto.Customer;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class AccountsServiceImpl implements AccountService {

	private static AccountService instance;

	private DataBase db;
	private ReservationService reservationService;
	private TablesService tablesService;
	private ServerService serviceUtils;

	private AccountsServiceImpl() {
	}

	public static AccountService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new AccountsServiceImpl();
		}
		// Return the existing instance
		return instance;

	}

	@Override
	public void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils) {

		db = DataBaseFactory.getDatabase();
		this.reservationService = reservationService;
		this.serviceUtils = serviceUtils;
		this.tablesService = tablesService;
	}

	public Customer registerAccount(String email, String firstName, String lastName, String password) {
		Customer customer = new Customer(email, firstName, lastName, password, new ArrayList<>());
		if (db.insertRecord(SQLTables.ACCOUNTS_TABLE, customer)) {
			return customer;
		}
		return null;
	}

	@Override
	public Customer customerLogin(String email, String password) {

		try {
			Customer customer = db.getCustomerAccount(email);
			if (customer.getPassword().equals(password)) {

				return customer;
			}
			System.out.println("Incorrect password given: expected " + customer.getPassword() + " but was " + password);
		} catch (Exception e) {
			System.out.println("Account with email " + email + " does not exist");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer getCustomerAccountDetails(String email) {
		try {
			return db.getCustomerAccount(email);
		} catch (Exception e) {
			System.out.println("Account with email " + email + " does not exist");
		}
		return null;
	}

	@Override
	public Business businessLogin(String email, String password) {

		Business business = db.getBusinessAccount();

		if (business.getEmail().equals(email) && business.getPassword().equals(password)) {
			return business;
		}
		System.out.println("Incorrect password given: expected " + business.getPassword() + " but was " + password);

		return null;
	}

	@Override
	public boolean updateBusinessAccount(Business business) {
		return db.updateDataBaseEntry(business, SQLTables.BUSINESS_TABLE);
	}

}