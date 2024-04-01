package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import database.SQLTables;

public class Business implements QueryGenerator {

	public Business(String email, String password, LocalTime openFrom, LocalTime openUntil, long reservationSlots,
			String phoneNumber, String address) {
		super();
		this.email = email;
		this.password = password;
		this.openFrom = openFrom;
		this.openUntil = openUntil;
		this.reservationSlots = reservationSlots;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Business(String email, String password) {
		this.email = email;
		this.password = password;
	}

	private String email;

	private String password;

	private LocalTime openFrom = LocalTime.of(12, 00);

	private LocalTime openUntil = LocalTime.of(23, 59);

	private long reservationSlots = 90;

	private String phoneNumber;

	private String address;

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
				+ openUntil + ", reservationSlots=" + reservationSlots + ", phoneNumber=" + phoneNumber + ", address="
				+ address + ", allTimeSlots=" + allTimeSlots + "]";
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, allTimeSlots, email, openFrom, openUntil, password, phoneNumber, reservationSlots);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Business other = (Business) obj;
		return Objects.equals(address, other.address) && Objects.equals(allTimeSlots, other.allTimeSlots)
				&& Objects.equals(email, other.email) && Objects.equals(openFrom, other.openFrom)
				&& Objects.equals(openUntil, other.openUntil) && Objects.equals(password, other.password)
				&& Objects.equals(phoneNumber, other.phoneNumber) && reservationSlots == other.reservationSlots;
	}

	public PreparedStatement generateUpdateStatement(Connection conn, List<String> columns) {
		try {
			columns.remove(0);
			String sql = "UPDATE " + SQLTables.BUSINESS_TABLE + " SET "
					+ String.join(", ", columns.stream().map(column -> column + " = ?").toArray(String[]::new))
					+ " WHERE email = ?;";

			System.out.println(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			int index = 1; // Start from the first "?" in SET clause
			// Assuming setters/getters are named in the standard Java convention based on
			// the field names.
			stmt.setString(index++, getPassword());
			stmt.setTime(index++, Time.valueOf(getOpenFrom())); // Convert LocalTime to SQL Time
			stmt.setTime(index++, Time.valueOf(getOpenUntil())); // Convert LocalTime to SQL Time
			stmt.setLong(index++, getReservationSlots());
			stmt.setString(index++, getPhoneNumber());
			stmt.setString(index++, getAddress());
			stmt.setString(index++, getEmail());

			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PreparedStatement generateInsertStatement(Connection conn, List<String> columns) {
		return null;
	}
}
