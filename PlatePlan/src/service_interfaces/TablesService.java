package service_interfaces;

import java.time.LocalDate;
import java.util.List;

import dto.Table;
import dto.TimeSlot;

public interface TablesService {

	boolean registerTable(String id, int cap, String server);
	
	boolean updateTable(String id, int cap, String server);

	boolean deleteTable(String id);

	List<Table> getTablesMatchingResReq(int capRequested);

	List<TimeSlot> getAvailableTables(LocalDate givenDate, int capRequested);

	void initializeDependency(AccountService accountService, ReservationService reservationService,
			ServerService serviceUtils);

	boolean combineTables(List<Table> selectedRowDetails);

}
