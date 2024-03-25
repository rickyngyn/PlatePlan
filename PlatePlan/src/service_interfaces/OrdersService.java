package service_interfaces;

import java.time.LocalDate;
import java.util.List;

import dto.MenuItem;
import dto.Order;
import dto.Reservation;

public interface OrdersService {
	public List<Order> getAllOrders();
	
	public List<Order> getAllOrdersForReservation(Reservation reservation);
	
	public List<Order> getAllOrdersForDate(LocalDate date);


	public boolean addOrder(Reservation reservation, MenuItem menuItem);

	public boolean deleteOrder(Order order);

	void initializeDependency(AccountService accountService, TablesService tablesService, ServerService serviceUtils,
			ReservationService reservationService, MenuService menuService);

	public boolean updateOrder(Order order);

}
