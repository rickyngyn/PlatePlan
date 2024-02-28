package service_interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import dto.MenuItem;
import dto.Server;
import dto.Table;
import dto.TimeSlot;

public interface ServerService {

	Map<String, String> getAllServersMap();

	List<Server> getAllServers();

	Server registerServer(String firstName, String lastName);

	boolean deleteServer(String id);

	void initializeDependency(AccountService accountService, ReservationService reservationService,
			TablesService tablesService);

}