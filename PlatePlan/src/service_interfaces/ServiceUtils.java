package service_interfaces;

import java.awt.MenuItem;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import dto.Server;
import dto.Table;
import dto.TimeSlot;

public interface ServiceUtils {

	List<Table> getTablesMatchingResReq(int capRequested);

	boolean registerTable(String id, int cap, String server);

	boolean deleteTable(String id);

	Map<String, String> getAllServersMap();
	
	List<Server> getAllServers();

	List<TimeSlot> getAvailableTables(LocalDate givenDate, int capRequested);

	Server registerServer(String firstName, String lastName);
	
	boolean deleteServer (String id);


}