package service_interfaces;

import java.util.List;
import java.util.Map;

import dto.Server;

public interface ServerService {

	Map<String, String> getAllServersMap();

	List<Server> getAllServers();

	Server registerServer(String firstName, String lastName);

	boolean deleteServer(String id);

	void initializeDependency(AccountService accountService, ReservationService reservationService,
			TablesService tablesService);

}