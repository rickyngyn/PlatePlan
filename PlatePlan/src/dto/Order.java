package dto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import database.SQLTables;

public class Order {

	public Order(String id, String item, String customer, double price, LocalDate date, String reservation,
			int quantity) {
		super();
		this.id = id;
		this.item = item;
		this.customer = customer;
		this.price = price;
		this.date = date;
		this.reservation = reservation;
		this.quantity = quantity;
	}

	public Order() {

	}

	private String id;
	private String item;
	private String customer;
	private double price;
	private LocalDate date;
	private String reservation;
	private int quantity;

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
	 * @return the item
	 */
	public String getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(String item) {
		this.item = item;
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
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, date, id, item, price, quantity, reservation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(item, other.item)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && quantity == other.quantity
				&& Objects.equals(reservation, other.reservation);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", item=" + item + ", customer=" + customer + ", price=" + price + ", date=" + date
				+ ", reservation=" + reservation + ", quantity=" + quantity + "]";
	}

	public PreparedStatement getSQLString(Connection connection, String sql) {
		try {
			sql = sql + "(?,?,?,?,?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, this.getId());
			pstmt.setString(2, this.getItem());
			pstmt.setString(3, this.getCustomer());
			pstmt.setDouble(4, this.getPrice());
			pstmt.setDate(5, Date.valueOf(this.getDate()));
			pstmt.setString(6, this.getReservation());
			pstmt.setInt(7, this.getQuantity());

			return pstmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public PreparedStatement generateUpdateCommand(Connection conn, List<String> columns, String tableName) {
		try {
			columns.remove(0);
			String sql = "UPDATE " + tableName + " SET "
					+ String.join(", ", columns.stream().map(column -> column + " = ?").toArray(String[]::new))
					+ " WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, this.getItem());
			stmt.setString(2, this.getCustomer());
			stmt.setDouble(3, this.getPrice());
			stmt.setDate(4, Date.valueOf(this.getDate()));
			stmt.setString(5, this.getReservation());
			stmt.setInt(6, this.getQuantity());

			stmt.setString(7, this.id);

			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PreparedStatement upsertRecord(Connection conn, List<String> columns) {
		 // Replace with your actual table name

		try {
			// Assuming 'columns' contains all column names except 'id' which is handled
			// separately
			String columnNames = String.join(", ", columns);
			String placeholders = String.join(", ", columns.stream().map(column -> "?").toArray(String[]::new));
			String updates = String.join(", ",
					columns.stream().map(column -> column + " = EXCLUDED." + column).toArray(String[]::new));

			String sql = "INSERT INTO " + SQLTables.ORDERS_TABLE + " (" + columnNames + ") " + "VALUES (" + placeholders
					+ ") " + "ON CONFLICT (id) " + "DO UPDATE SET " + updates + ";";

			PreparedStatement stmt = conn.prepareStatement(sql);


			int index = 1;
			for (String column : columns) {
				switch (column) {
				case "id":
					stmt.setString(index++, this.id);
					break;
				case "item":
					stmt.setString(index++, this.item);
					break;
				case "customer":
					stmt.setString(index++, this.customer);
					break;
				case "price":
					stmt.setDouble(index++, this.price);
					break;
				case "date":
					stmt.setDate(index++, Date.valueOf(this.date));
					break;
				case "reservation":
					stmt.setString(index++, this.reservation);
					break;
				case "quantity":
					stmt.setInt(index++, this.quantity);
					break;
				// Add more cases as per your class fields
				}
			}

			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
