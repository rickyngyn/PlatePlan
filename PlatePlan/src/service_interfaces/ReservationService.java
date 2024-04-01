package service_interfaces;

import java.time.LocalDate;
import java.util.List;

import dto.Customer;
import dto.Reservation;
import dto.TimeSlot;

public interface ReservationService {

	public Reservation createCustomerReservation(Customer customer, LocalDate date, TimeSlot slot, int cap,
			String specialNotes);

	public List<Reservation> getAllReservations ();
	
	public List<Reservation> getCustomerReservation(String email);

	public boolean cancelReservation(String reservationId);

	void initializeDependency(AccountService accountService, TablesService tablesService, ServerService serviceUtils);

	boolean updateReservation(Reservation reservation);

}
