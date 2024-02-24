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
		String sql = "INSERT INTO %s %s VALUES ";
		sql = String.format(sql, tableName, getColumnNames(tableName));

		PreparedStatement pstmt = null;

		if (tableName.equals(SQLTables.RESERVATION_TABLE)) {
			Reservation reservation = (Reservation) object;
			pstmt = reservation.getSQLString(connection, sql);
			
		}else if (tableName.equals(SQLTables.TABLES_TABLE)) {
			Table table = (Table) object;
			pstmt = table.getSQLString(connection, sql);
		}
		else if (tableName.equals(SQLTables.ACCOUNTS_TABLE)) {
			Customer customer = (Customer) object;
			pstmt = customer.getSQLString(connection, sql);
		}
		else if (tableName.equals(SQLTables.SERVERS_TABLE)) {
			Server server = (Server) object;
			pstmt = server.getSQLString(connection, sql);
		}

		System.out.println("Executing Command: " + pstmt.toString());
		try {
			if (pstmt != null && pstmt.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
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
					customer = DataBaseConverters.convertCustomer(rs);
				} else {
					throw new AccountNotFoundException("No account found with email: " + email);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	@Override
	public List<Table> getAllTables() {
		List<Table> tables = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", SQLTables.TABLES_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				tables = DataBaseConverters.convertTableList(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tables;
	}

	@Override
	public boolean deleteTable(String id) {
		int affectedRows = 0;
		// SQL command to delete rows with the specific ID
        String sql = "DELETE FROM " + SQLTables.TABLES_TABLE + " WHERE id = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the ID in the prepared statement to avoid SQL injection
            pstmt.setString(1, id);

            // Execute the delete command
             affectedRows = pstmt.executeUpdate();
            System.out.println("Deleted " + affectedRows + " rows.");
            
            
        } catch (SQLException e) {
            System.out.println("Error occurred during delete operation: " + e.getMessage());
        }
        return affectedRows <= 0? false : true;
	}

	@Override
	public List<Server> getAllServers() {
		List<Server> servers = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", SQLTables.SERVERS_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			servers = DataBaseConverters.convertServerList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return servers;
	}

	@Override
	public List<Reservation> getReservationsForDate(LocalDate date) {
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE 'date' = '%s'", SQLTables.RESERVATION_TABLE,
				date.toString());
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			reservations = DataBaseConverters.convertReservationList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	@Override
	public List<Reservation> getAllReservations() {
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s", SQLTables.RESERVATION_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			reservations = DataBaseConverters.convertReservationList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	@Override
	public boolean deleteReservation(String id) {
		int affectedRows = 0;
		// SQL command to delete rows with the specific ID
        String sql = "DELETE FROM " + SQLTables.RESERVATION_TABLE + " WHERE id = ?;";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the ID in the prepared statement to avoid SQL injection
            pstmt.setString(1, id);

            // Execute the delete command
             affectedRows = pstmt.executeUpdate();
            System.out.println("Deleted " + affectedRows + " rows.");
            
            
        } catch (SQLException e) {
            System.out.println("Error occurred during delete operation: " + e.getMessage());
        }
        return affectedRows <= 0? false : true;
	}

	@Override
	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException {
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE customer_id = '%s'",
				SQLTables.RESERVATION_TABLE, email);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			reservations = DataBaseConverters.convertReservationList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservations;
	}

	@Override
	public Reservation getReservationWithId(String id) {
		Reservation reservation = null;
		String sql = String.format("SELECT * FROM %s WHERE id = '%s'",
				SQLTables.RESERVATION_TABLE, id);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			reservation = DataBaseConverters.convertReservation(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservation;
	}

}
