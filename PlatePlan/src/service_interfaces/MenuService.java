package service_interfaces;

import java.util.List;

import dto.MenuItem;

public interface MenuService {

	List<MenuItem> getAllMenuItems(String table);

	boolean updateMenuItem(MenuItem menuItem);

	MenuItem addMenuItem();

	void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils, AccountService accountService);

	boolean deleteMenuItem(MenuItem item);

}
