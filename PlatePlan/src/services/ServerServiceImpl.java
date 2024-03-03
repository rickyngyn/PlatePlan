package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.MenuItem;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class ServerServiceImpl implements ServerService {

	// Private static instance of the class
	private static ServerService instance;

	private DataBase db;
	private AccountService accountService;
	private ReservationService reservationService;
	private TablesService tablesService;

	// Private constructor to prevent instantiation from outside the class
	private ServerServiceImpl() {
	}

	// Public static method to get the instance of the class
	public static ServerService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new ServerServiceImpl();
		}
		// Return the existing instance
		return instance;

	}

	@Override
	public void initializeDependency(AccountService accountService, ReservationService reservationService,
			TablesService tablesService) {

		db = DataBaseFactory.getDatabase();
		this.accountService = accountService;
		this.reservationService = reservationService;
		this.tablesService = tablesService;
	}

	@Override
	public Map<String, String> getAllServersMap() {

		Map<String, String> map = new HashMap<>();
		for (Server server : db.getAllServers()) {

			map.put(server.getId(), server.getFirstName() + " " + server.getLastName());
		}

		return map;
	}

	@Override
	public Server registerServer(String firstName, String lastName) {
		Server server = new Server(UUID.randomUUID().toString(), firstName, lastName);

		if (db.insertRecord(SQLTables.SERVERS_TABLE, server)) {
			return server;
		} else {
			return null;
		}

	}

	@Override
	public List<Server> getAllServers() {
		return db.getAllServers();

	}

	@Override
	public boolean deleteServer(String id) {
		return db.deleteDataBaseEntry(SQLTables.SERVERS_TABLE, id);
	}

}
