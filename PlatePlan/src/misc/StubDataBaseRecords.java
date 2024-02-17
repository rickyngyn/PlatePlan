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
		
	}

	public static List<Customer> customers = new ArrayList<Customer>(
			Arrays.asList(new Customer("john", "john", "doe", "password"),
					new Customer("janedoe@email.com", "jane", "doe", "password"),
					new Customer("max@email.com", "max", "payne", "password")));

	public static Business business = new Business("alfredo", "password");

	
}
