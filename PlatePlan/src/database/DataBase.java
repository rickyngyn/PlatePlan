package database;

import java.time.LocalDate;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import dto.Business;
import dto.Customer;
import dto.Reservation;
import dto.Server;
import dto.Table;

public interface DataBase {

	public Business getBusinessAccount();

	public boolean insertRecord(String tableName, Object object);

	public Customer getCustomerAccount(String email) throws AccountNotFoundException;

	public List<Table> getAllTables();

	public boolean deleteTable(String id);

	public List<Server> getAllServers();

	public List<Reservation> getReservationsForDate(LocalDate date);

	public List<Reservation> getAllReservations();

	public boolean deleteReservation(String id);

	public List<Reservation> getCustomerReservations(String email) throws AccountNotFoundException;

	public Reservation getReservationWithId(String id);
	
	public boolean deleteServer(String id);

}
