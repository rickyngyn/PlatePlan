package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
		Business business = null;
		String sql = String.format("SELECT * FROM %s", SQLTables.BUSINESS_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					business = new Business(null, null);
					business.setEmail(rs.getString("email"));
					business.setPassword(rs.getString("password"));
					
					
					// Set other fields of the customer object as needed
				} 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Depending on how you want to handle SQL exceptions,
			// you might want to throw a different exception or handle it differently
		}

		return business;
	}

	@Override
	public boolean insertRecord(String tableName, Object object) {
		String sql = "INSERT INTO %s (%s) VALUES %s;";
		if (tableName.equals(SQLTables.RESERVATION_TABLE))
		{
			Reservation reservation = (Reservation)object;
			sql = String.format(sql, SQLTables.RESERVATION_TABLE, getColumnNames(tableName), 
					reservation.getSQLString());
		}
		
		System.out.println("Executing Command: " + sql);
		try {
			runInsertCommand(sql);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	private void runInsertCommand (String sql) throws SQLException
	{
		PreparedStatement pstmt = connection.prepareStatement(sql);
		 // Execute the INSERT command
        int affectedRows = pstmt.executeUpdate();
	}
	private String getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            
            // Retrieves a ResultSet where each row is a column description
            try (ResultSet columns = metaData.getColumns(null, null, tableName, null)) {
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    columnNames.add(columnName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        
        return "(" + String.join(",", columnNames) + ")";
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
		List<Table> tables = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s",
				SQLTables.TABLES_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {

					Table table = new Table();
					table.setCapacity(rs.getInt("capacity"));
					table.setId(rs.getString("id"));
					table.setServer(rs.getString("server"));
					tables.add(table);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Depending on how you want to handle SQL exceptions,
			// you might want to throw a different exception or handle it differently
		}

		return tables;
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
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE 'date' = '%s'",
				SQLTables.RESERVATION_TABLE, date.toString());
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
