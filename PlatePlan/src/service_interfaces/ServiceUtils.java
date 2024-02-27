package service_interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import dto.MenuItem;
import dto.Server;
import dto.Table;
import dto.TimeSlot;

public interface ServiceUtils {

	boolean registerTable(String id, int cap, String server);

	boolean deleteTable(String id);

	List<Table> getTablesMatchingResReq(int capRequested);
	
	List<TimeSlot> getAvailableTables(LocalDate givenDate, int capRequested);

	Map<String, String> getAllServersMap();

	List<Server> getAllServers();

	Server registerServer(String firstName, String lastName);

	boolean deleteServer(String id);

	List<MenuItem> getAllMenuItems();

}