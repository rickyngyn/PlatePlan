package service_interfaces;

import dto.Business;
import dto.Customer;

public interface AccountService {

	public Customer registerAccount(String email, String firstName, String lastName, String password);

	public Customer customerLogin(String email, String password);

	public Business businessLogin(String email, String password);

	public Customer getCustomerAccountDetails(String email);

	public boolean updateBusinessAccount(Business business);

	void initializeDependency(ReservationService reservationService, TablesService tablesService,
			ServerService serviceUtils);

}