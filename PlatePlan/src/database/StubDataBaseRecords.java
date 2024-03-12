package database;

import java.awt.Menu;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Reservation;
import dto.Server;
import dto.Table;

public class StubDataBaseRecords {

	public List<Customer> customers;
	public Business business;
	public List<Table> tables;
	public List<Server> servers;
	public List<Reservation> reservations;
	public List<Feedback> feedbacks;
	public List<MenuItem> menus;
	public List<MenuItem> customer_menu;

	private static StubDataBaseRecords instance;

	private StubDataBaseRecords() {
		reset();
	}

	// Public method to get the instance of the class
	public static synchronized StubDataBaseRecords getInstance() {
		if (instance == null) {
			instance = new StubDataBaseRecords();
		}
		return instance;
	}

	public void reset() {
		customers = new ArrayList<Customer>(Arrays.asList(new Customer("john", "john", "doe", "password"),
				new Customer("janedoe@email.com", "jane", "doe", "password"),
				new Customer("max@email.com", "max", "payne", "password")));
		
		business = new Business("alfredo", "password");
		
		tables = new ArrayList<Table>(Arrays.asList(new Table("1", 4, "1"), new Table("2", 2, "1"),
				new Table("3", 2, "2"), new Table("4", 6, "2"), new Table("5", 8, "3"), new Table("6", 4, "3")));

		servers = new ArrayList<Server>(Arrays.asList(
				new Server("1", "robert", "downey"), new Server("2", "peter", "parker"),
				new Server("3", "chris", "evans")));
		
		feedbacks = new ArrayList<Feedback>(Arrays.asList(new Feedback("1", "John doe", 4, LocalDateTime.now(), "excellent"), 
				new Feedback("2", "Jane doe", 3, LocalDateTime.now(), "terrible"), new Feedback("3", "Max Payne", 5, LocalDateTime.now(), "the best")));
		
		menus = new ArrayList<MenuItem>(Arrays.asList(new MenuItem("1", "pizza", "peperoni", (float) 9.99),
				new MenuItem("2", "burger", "cheese", (float) 10.99),
				new MenuItem("3", "pasta", "alfredo", (float) 8.99)));
		customer_menu = new ArrayList<MenuItem>(Arrays.asList(new MenuItem("1", "pizza", "peperoni", (float) 9.99),
				new MenuItem("2", "burger", "cheese", (float) 10.99),
				new MenuItem("3", "pasta", "alfredo", (float) 8.99)));
	}

}
