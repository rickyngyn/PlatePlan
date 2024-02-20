package dto;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Business {

	public Business(String email, String password) {
		this.email = email;
		this.password = password;
	}

	private String email;

	private String password;

	private LocalTime openFrom = LocalTime.of(12, 00);

	private LocalTime openUntil = LocalTime.of(23, 59);

	private long reservationSlots = 90;

	private List<TimeSlot> allTimeSlots = calculateTimeSlots();

	private List<TimeSlot> calculateTimeSlots() {
		LocalTime time = openFrom;
		List<TimeSlot> allSlots = new ArrayList<TimeSlot>();
		for (int i = 1; i <= Duration.between(openFrom, openUntil).toMinutes() / 90; i++) {
			allSlots.add(new TimeSlot(time, time.plusMinutes(reservationSlots)));
			time = time.plusMinutes(reservationSlots);
		}

		return allSlots;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the openFrom
	 */
	public LocalTime getOpenFrom() {
		return openFrom;
	}

	/**
	 * @param openFrom the openFrom to set
	 */
	public void setOpenFrom(LocalTime openFrom) {
		this.openFrom = openFrom;
	}

	/**
	 * @return the openUntil
	 */
	public LocalTime getOpenUntil() {
		return openUntil;
	}

	/**
	 * @param openUntil the openUntil to set
	 */
	public void setOpenUntil(LocalTime openUntil) {
		this.openUntil = openUntil;
	}

	/**
	 * @return the reservationSlots
	 */
	public long getReservationSlots() {
		return reservationSlots;
	}

	/**
	 * @param reservationSlots the reservationSlots to set
	 */
	public void setReservationSlots(long reservationSlots) {
		this.reservationSlots = reservationSlots;
	}

	/**
	 * @return the allTimeSlots
	 */
	public List<TimeSlot> getAllTimeSlots() {
		return allTimeSlots;
	}

	/**
	 * @param allTimeSlots the allTimeSlots to set
	 */
	public void setAllTimeSlots(List<TimeSlot> allTimeSlots) {
		this.allTimeSlots = allTimeSlots;
	}

	@Override
	public String toString() {
		return "Business [email=" + email + ", password=" + password + ", openFrom=" + openFrom + ", openUntil="
				+ openUntil + ", reservationSlots=" + reservationSlots + ", allTimeSlots=" + allTimeSlots + "]";
	}
}
