package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import database.DataBase;
import database.DataBaseFactory;
import database.SQLTables;
import dto.Customer;
import dto.Reservation;
import dto.Table;
import dto.TimeSlot;
import service_interfaces.ReservationService;
import service_interfaces.ServiceUtils;

public class ReservationServiceImpl implements ReservationService {

	DataBase db;
	ServiceUtils serviceUtils;

	public ReservationServiceImpl() {
		this.db = DataBaseFactory.getDatabase();
		serviceUtils = ServiceUtilsImpl.getInstance();
	}

	@Override
	public List<Reservation> getCustomerReservation(String email) {

		try {
			return db.getCustomerReservations(email);
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public Reservation createCustomerReservation(Customer customer, LocalDate date, TimeSlot slot, int cap,
			String specialNotes) {

		List<Table> tablesAvailable = serviceUtils.getTablesMatchingResReq(cap);

		if (tablesAvailable.isEmpty()) {
			return null;
		}

		Reservation reservation = new Reservation(UUID.randomUUID().toString(), customer.getEmail(), date, slot,
				specialNotes, serviceUtils.getAllServersMap().get(tablesAvailable.get(0).getServer()),
				tablesAvailable.get(0).getId(), cap);

		if (customer.getReservations() != null) {
			try {
				for (Reservation cusReservation : db.getCustomerReservations(customer.getEmail())) {
					if (date.equals(cusReservation.getDate())) {
						return null;
					}
				}
			} catch (AccountNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			customer.setReservations(new ArrayList<String>());
		}

		customer.getReservations().add(reservation.getId());

		if (db.insertRecord(SQLTables.RESERVATION_TABLE, reservation)) {
			System.out.println("Reservation successfully submitted " + reservation);
			return reservation;
		}

		return null;
	}

	@Override
	public boolean cancelReservation(String reservationId) {
		return db.deleteReservation(reservationId);
	}

	// Other service methods would go here
}