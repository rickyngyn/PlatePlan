package service_interfaces;

import java.util.List;

import dto.MenuItem;

public interface MenuService {
	
	List<MenuItem> getAllMenuItems();
	
	boolean updateMenuItem (MenuItem menuItem);
	
	MenuItem addMenuItem (String title, float price, String description);

	void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils, AccountService accountService);

}
