package database;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Order;
import dto.Receipt;
import dto.Reservation;
import dto.Server;
import dto.Table;

public interface DataBase {

	public Business getBusinessAccount();

	public boolean insertRecord(String tableName, Object object);

	public Customer getCustomerAccount(String email) throws AccountNotFoundException;

	public List<Table> getAllTables();

	public List<Server> getAllServers();

	public List<Reservation> getReservationsForDate(LocalDate date);

	public List<Reservation> getAllReservations();

	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException;

	public Reservation getReservationWithId(String id);

	public List<MenuItem> getAllMenuItems(String table);

	void publishCustomerMenu();

	public List<Feedback> getAllFeedbacks();

	public boolean deleteDataBaseEntry(String table, String id);

	boolean updateDataBaseEntry(Object object, String table);

	public List<Order> getAllOrders();

	List<Receipt> getAllReceipts();

}
