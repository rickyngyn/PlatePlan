package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Reservation;
import dto.Server;
import dto.Table;

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
            String url = "jdbc:postgresql://localhost:5432/yourdatabase";
            String user = "postgres";
            String password = "admin";
            
            // Get a connection to the database
            connection = DriverManager.getConnection(url, user, password);
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
	    String sql = String.format("SELECT email, firstName, lastName, password FROM %s WHERE email = %s", SQLTables.ACCOUNTS_TABLE, email);
	    
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, email);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation getReservationWithId(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
