package dto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import database.SQLTables;

public class Receipt {

	private String id;
	private String reservation;
	private String customer;
	private LocalDate date;
	private LocalTime time;
	private double subtotal;
	private double tax;
	private int tip_percent;
	private double total;

	public Receipt(String id, String reservation, String customer, LocalDate date, LocalTime time, double subtotal,
			double tax, int tip_percent, double tip_amount, double total) {
		super();
		this.id = id;
		this.reservation = reservation;
		this.customer = customer;
		this.date = date;
		this.time = time;
		this.subtotal = subtotal;
		this.tax = tax;
		this.tip_percent = tip_percent;
		this.total = total;
	}

	public Receipt() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the reservation
	 */
	public String getReservation() {
		return reservation;
	}

	/**
	 * @param reservation the reservation to set
	 */
	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return the time
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(LocalTime time) {
		this.time = time;
	}

	/**
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the tip_percent
	 */
	public int getTip_percent() {
		return tip_percent;
	}

	/**
	 * @param tip_percent the tip_percent to set
	 */
	public void setTip_percent(int tip_percent) {
		this.tip_percent = tip_percent;
	}

	/**
	 * @return the tip_amount
	 */

	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", reservation=" + reservation + ", customer=" + customer + ", date=" + date
				+ ", time=" + time + ", subtotal=" + subtotal + ", tax=" + tax + ", tip_percent=" + tip_percent
				+ ", total=" + total + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, date, id, reservation, subtotal, tax, time, tip_percent, total);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(reservation, other.reservation)
				&& Double.doubleToLongBits(subtotal) == Double.doubleToLongBits(other.subtotal)
				&& Double.doubleToLongBits(tax) == Double.doubleToLongBits(other.tax)
				&& Objects.equals(time, other.time) && tip_percent == other.tip_percent
				&& Double.doubleToLongBits(total) == Double.doubleToLongBits(other.total);
	}

	public PreparedStatement upsertRecord(Connection conn, List<String> columns) {
		try {
			String columnNames = String.join(", ", columns);
			String placeholders = String.join(", ", columns.stream().map(column -> "?").toArray(String[]::new));
			String updates = String.join(", ",
					columns.stream().map(column -> column + " = EXCLUDED." + column).toArray(String[]::new));

			String sql = "INSERT INTO " + SQLTables.RECEIPT_TABLE + " (" + columnNames + ") VALUES (" + placeholders
					+ ") ON CONFLICT (id) DO UPDATE SET " + updates + ";";
			PreparedStatement stmt = conn.prepareStatement(sql);

			int index = 1;
			for (String column : columns) {
				switch (column) {
				case "id":
					stmt.setString(index++, this.id);
					break;
				case "reservation":
					stmt.setString(index++, this.reservation);
					break;
				case "customer":
					stmt.setString(index++, this.customer);
					break;
				case "date":
					stmt.setDate(index++, Date.valueOf(this.date));
					break;
				case "time":
					stmt.setTime(index++, Time.valueOf(this.time));
					break;
				case "subtotal":
					stmt.setDouble(index++, this.subtotal);
					break;
				case "tax":
					stmt.setDouble(index++, this.tax);
					break;
				case "tip":
					stmt.setInt(index++, this.tip_percent);
					break;
				case "total":
					stmt.setDouble(index++, this.total);
					break;
				// Add more cases as per your class fields
				}
			}

			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PreparedStatement genSQLInsertStatement(Connection conn, List<String> columns) {
		try {
			String columnNames = String.join(", ", columns);
			String placeholders = String.join(", ", columns.stream().map(column -> "?").toArray(String[]::new));

			// Removed the ON CONFLICT clause to make it an insert-only statement
			String sql = "INSERT INTO " + SQLTables.RECEIPT_TABLE + " (" + columnNames + ") VALUES (" + placeholders + ");";
			PreparedStatement stmt = conn.prepareStatement(sql);

			int index = 1;
			for (String column : columns) {
				switch (column) {
				case "id":
					stmt.setString(index++, this.id);
					break;
				case "reservation":
					stmt.setString(index++, this.reservation);
					break;
				case "customer":
					stmt.setString(index++, this.customer);
					break;
				case "date":
					stmt.setDate(index++, Date.valueOf(this.date));
					break;
				case "time":
					stmt.setTime(index++, Time.valueOf(this.time));
					break;
				case "subtotal":
					stmt.setDouble(index++, this.subtotal);
					break;
				case "tax":
					stmt.setDouble(index++, this.tax);
					break;
				case "tip":
					stmt.setInt(index++, this.tip_percent);
					break;
				case "total":
					stmt.setDouble(index++, this.total);
					break;
				// Add more cases as per your class fields
				}
			}

			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public double calculateTotal() {
		double total = 0;
		total += subtotal;
		total += tax;
		total += (tip_percent / 100.0) * total;
		this.total = Double.parseDouble(String.format("%.2f", total));
		return this.total;


	}

}
