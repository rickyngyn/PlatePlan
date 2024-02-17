package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.Business;
import dto.Customer;
import dto.Reservation;
import dto.Server;
import dto.Table;

public class StubDataBaseRecords {
	public static void reset() {
		customers = new ArrayList<Customer>(Arrays.asList(new Customer("john", "john", "doe", "password"),
				new Customer("janedoe@email.com", "jane", "doe", "password"),
				new Customer("max@email.com", "max", "payne", "password")));
		business = new Business("alfredo", "password");
		tables = new ArrayList<Table>(Arrays.asList(new Table("1", 4, "1"), new Table("2", 2, "1"),
				new Table("3", 2, "2"), new Table("4", 6, "2"), new Table("5", 8, "3"), new Table("6", 4, "3")));

		servers = new ArrayList<Server>(Arrays.asList(

				new Server("1", "robert", "downey"), new Server("2", "peter", "parker"),
				new Server("3", "chris", "evans")

		));
	}

	public static List<Customer> customers = new ArrayList<Customer>(
			Arrays.asList(new Customer("john", "john", "doe", "password"),
					new Customer("janedoe@email.com", "jane", "doe", "password"),
					new Customer("max@email.com", "max", "payne", "password")));

	public static Business business = new Business("alfredo", "password");

	
	public static List<Table> tables = new ArrayList<Table>(
			Arrays.asList(new Table("1", 4, "1"), new Table("2", 2, "1"), new Table("3", 2, "2"),
					new Table("4", 6, "2"), new Table("5", 8, "3"), new Table("6", 4, "3")));

	public static List<Server> servers = new ArrayList<Server>(Arrays.asList(

			new Server("1", "robert", "downey"), new Server("2", "peter", "parker"), new Server("3", "chris", "evans")

	));
	public static List<Reservation> reservations = new ArrayList<Reservation>();

}
