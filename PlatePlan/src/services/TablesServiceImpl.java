package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Reservation;
import dto.Table;
import dto.TimeSlot;
import service_interfaces.AccountService;
import service_interfaces.ReservationService;
import service_interfaces.ServerService;
import service_interfaces.TablesService;

public class TablesServiceImpl implements TablesService {

	private static TablesService instance;

	private DataBase db;
	private AccountService accountService;
	private ReservationService reservationService;
	private ServerService serviceUtils;

	private TablesServiceImpl() {
	}

	@Override
	public void initializeDependency(AccountService accountService, ReservationService reservationService,
			ServerService serviceUtils) {

		db = DataBaseFactory.getDatabase();
		this.accountService = accountService;
		this.reservationService = reservationService;
		this.serviceUtils = serviceUtils;
	}

	public static TablesService getInstance() {
		// Create the instance if it does not exist
		if (instance == null) {
			instance = new TablesServiceImpl();
		}
		// Return the existing instance
		return instance;

	}

	@Override
	public List<Table> getTablesMatchingResReq(int capRequested) {
		List<Table> tables = db.getAllTables().stream().filter(table -> table.getCapacity() >= capRequested)
				.collect(Collectors.toList());

		return tables;
	}

	@Override
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

	@Override
	public boolean deleteTable(String id) {

		return db.deleteDataBaseEntry(SQLTables.TABLES_TABLE, id);

	}

	@Override
	public List<TimeSlot> getAvailableTables(LocalDate givenDate, int capRequested) {
		List<TimeSlot> allSlots = db.getBusinessAccount().getAllTimeSlots();

		List<Table> tables = this.getTablesMatchingResReq(capRequested);

		List<Reservation> reservations = db.getReservationsForDate(givenDate);

		Set<TimeSlot> availableList = new HashSet<>(allSlots);

		for (TimeSlot timeSlot : allSlots) {
			int tablesAvailable = tables.size();
			for (Reservation reservation : reservations) {
				for (Table table : tables) {
					if ((reservation.getTableId().equals(table.getId()) && reservation.getTime().equals(timeSlot))) {
						System.out.println("Removing Time Slot " + timeSlot + " from available slots");
						tablesAvailable--;
					}
				}
			}

			if (tablesAvailable == 0) {
				availableList.remove(timeSlot);
			}
		}

		System.out.println("Tables Available Are: " + tables.toString());
		System.out.println("Reservations In System Are: " + reservations.toString());
		System.out.println("Available Time Slots Are: " + availableList + "\n\n");

		return new ArrayList<>(availableList);

	}

	@Override
	public boolean updateTable(String id, int cap, String server) {
		Table table = new Table(id, cap, server);
		return db.updateDataBaseEntry(table, SQLTables.TABLES_TABLE);
	}

}
