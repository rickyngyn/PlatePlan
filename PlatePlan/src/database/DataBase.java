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

}
