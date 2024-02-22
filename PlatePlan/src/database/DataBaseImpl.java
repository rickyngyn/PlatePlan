package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;

public class DataBaseImpl implements DataBase {

	// The single instance of the database class
	private static DataBaseImpl instance;

	// The database connection
	private Connection connection;

	// Private constructor for singleton pattern
	private DataBaseImpl() {
		// Set up the database connection here
		try {
			// Example connection setup
			String url = "jdbc:postgresql://localhost:5432/PlatePlan";
			String user = "postgres";
			String password = "admin";

			// Get a connection to the database
			connection = DriverManager.getConnection(url, user, password);
			try {
				getCustomerAccount("john");
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// Handle any SQL exceptions here (e.g., log them)
			e.printStackTrace();
		}
	}

	// Public method to get the instance of the class
	public static synchronized DataBaseImpl getInstance() {
		if (instance == null) {
			instance = new DataBaseImpl();
		}
		return instance;
	}

	@Override
	public Business getBusinessAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insertRecord(String tableName, Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer getCustomerAccount(String email) throws AccountNotFoundException {
		Customer customer = null;
		String sql = String.format("SELECT * FROM %s WHERE email = '%s'", SQLTables.ACCOUNTS_TABLE, email);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					customer = new Customer();
					customer.setEmail(rs.getString("email"));
					customer.setFirstName(rs.getString("firstName"));
					customer.setLastName(rs.getString("lastName"));
					customer.setPassword(rs.getString("password"));

					// Set other fields of the customer object as needed
				} else {
					throw new AccountNotFoundException("No account found with email: " + email);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Depending on how you want to handle SQL exceptions,
			// you might want to throw a different exception or handle it differently
		}

		return customer;
	}

	@Override
	public List<Table> getAllTables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTable(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Server> getAllServers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> getReservationsForDate(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteReservation(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException {
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE 'reservations.customerId' = '%s'",
				SQLTables.RESERVATION_TABLE, email);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					Reservation reservation = new Reservation();
					reservation.setId(rs.getString("id"));
					reservation.setCustomerId(rs.getString("customerId"));
					reservation.setDate(rs.getDate("date").toLocalDate());
					reservation.setTime(new TimeSlot(rs.getTime("time").toLocalTime(),
							rs.getTime("time").toLocalTime().plusMinutes(90)));
					reservation.setSpecialNotes(rs.getString("specialNotes"));
					reservation.setTableId(rs.getString("tableId"));
					reservation.setPartySize(rs.getInt("partySize"));
					reservation.setServerId(rs.getString("serverId"));
					reservations.add(reservation);
					// Set other fields of the customer object as needed
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Depending on how you want to handle SQL exceptions,
			// you might want to throw a different exception or handle it differently
		}

		return reservations;
	}

	@Override
	public Reservation getReservationWithId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
