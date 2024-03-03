package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
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
		sql = String.format(sql, tableName, getColumnNamesString(tableName));

		PreparedStatement pstmt = null;

		if (tableName.equals(SQLTables.RESERVATION_TABLE)) {
			Reservation reservation = (Reservation) object;
			pstmt = reservation.getSQLString(connection, sql);
		} else if (tableName.equals(SQLTables.TABLES_TABLE)) {
			Table table = (Table) object;
			pstmt = table.getSQLString(connection, sql);
		} else if (tableName.equals(SQLTables.ACCOUNTS_TABLE)) {
			Customer customer = (Customer) object;
			pstmt = customer.getSQLString(connection, sql);
		} else if (tableName.equals(SQLTables.SERVERS_TABLE)) {
			Server server = (Server) object;
			pstmt = server.getSQLString(connection, sql);
		} else if (tableName.equals(SQLTables.MENU_TABLE)) {
			MenuItem menuItem = (MenuItem) object;
			pstmt = menuItem.getSQLString(connection, sql);
		} else if (tableName.equals(SQLTables.FEEDBACKS_TABLE)) {
			Feedback feedback = (Feedback) object;
			pstmt = feedback.getSQLString(connection, sql);
		}

		System.out.println("Executing Command: " + pstmt.toString());
		try {
			if (pstmt != null && pstmt.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getColumnNamesString(String tableName) {

		return "(" + String.join(",", getColumnNamesList(tableName)) + ")";
	}

	private List<String> getColumnNamesList(String tableName) {
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

		return columnNames;
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
	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException {
		List<Reservation> reservations = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s WHERE customer_id = '%s' and date >= '%s'",
				SQLTables.RESERVATION_TABLE, email, LocalDate.now());
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
		String sql = String.format("SELECT * FROM %s WHERE id = '%s'", SQLTables.RESERVATION_TABLE, id);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			reservation = DataBaseConverters.convertReservation(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservation;
	}

	@Override
	public List<MenuItem> getAllMenuItems(String table) {
		List<MenuItem> menuItems = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s ;", table);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			menuItems = DataBaseConverters.convertMenuItemList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return menuItems;
	}

	@Override
	public boolean updateMenuItem(MenuItem menuItem) {

		try {
			PreparedStatement preparedStatement = menuItem.generateUpdateCommand(connection,
					getColumnNamesList(SQLTables.MENU_TABLE), SQLTables.MENU_TABLE);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void publishCustomerMenu() {
		// SQL statement to delete everything from customer_menu
		String deleteSQL = String.format("DELETE FROM %s", SQLTables.CUSTOMER_MENU_TABLE);

		// SQL statement to copy everything from menu to customer_menu
		// Assuming both tables have the same schema and column names
		String copySQL = String.format("INSERT INTO %s SELECT * FROM %s", SQLTables.CUSTOMER_MENU_TABLE,
				SQLTables.MENU_TABLE);

		Statement statement = null;

		try {
			// Create a Statement object for sending SQL statements to the database
			statement = connection.createStatement();

			// Execute delete statement to clear customer_menu
			statement.executeUpdate(deleteSQL);

			// Execute copy statement to populate customer_menu with data from menu
			statement.executeUpdate(copySQL);

			System.out.println("Successfully updated customer_menu from menu.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error updating customer_menu: " + e.getMessage());
		}
	}

	@Override
	public List<Feedback> getAllFeedbacks() {
		List<Feedback> feedbacks = new ArrayList<>();
		String sql = String.format("SELECT * FROM %s ORDER BY timestamp;", SQLTables.FEEDBACKS_TABLE);
		System.out.println("Executing Query: " + sql);

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			feedbacks = DataBaseConverters.convertFeedbackItemList(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return feedbacks;
	}

	@Override
	public boolean deleteDataBaseEntry(String table, String id) {
		int affectedRows = 0;
		// SQL command to delete rows with the specific ID
		String sql = "DELETE FROM " + table + " WHERE id = ?;";

		try {
			// Set the ID in the prepared statement to avoid SQL injection
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, id);
			System.out.println("Delete Query Executed: " + pstmt.toString());
			// Execute the delete command
			affectedRows = pstmt.executeUpdate();
			System.out.println("Deleted " + affectedRows + " rows.");

		} catch (SQLException e) {
			System.out.println("Error occurred during delete operation: " + e.getMessage());
		}
		return affectedRows <= 0 ? false : true;

	}
}
