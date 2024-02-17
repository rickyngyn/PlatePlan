package misc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import service_interfaces.AccountService;
import services.AccountsServiceImpl;

public class ServiceUtils {

	// Private static instance of the class
	private static ServiceUtils instance;
	private DataBase db;
	private AccountService accountService;

	// Private constructor to prevent instantiation from outside the class
	private ServiceUtils() {
		db = DataBaseFactory.getDatabase();
		accountService = new AccountsServiceImpl();
	}

	// Public static method to get the instance of the class
	public static ServiceUtils getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new ServiceUtils();
		}
		// Return the existing instance
		return instance;

	}

	public List<Table> getTablesMatchingResReq(int capRequested) {
		List<Table> tables = db.getAllTables().stream().filter(table -> table.getCapacity() >= capRequested)
				.collect(Collectors.toList());

		return tables;
	}

	public boolean registerTable(String id, int cap, String server) {
		Table table = new Table(id, cap, server);

		for (Table table2 : db.getAllTables()) {
			if (table.getId().equals(table2.getId())) {
				return false;
			}
		}

		db.insertRecord(SQLTables.TABLES_TABLE, table);

		return true;
	}

	public boolean deleteTable(String id) {

		return db.deleteTable(id);

	}

}
