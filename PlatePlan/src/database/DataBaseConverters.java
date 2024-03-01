package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Customer;
import dto.MenuItem;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;

public class DataBaseConverters {

	public static List<Reservation> convertReservationList(ResultSet rs) {
		List<Reservation> reservations = new ArrayList<>();
		try {
			while (rs.next()) {
				Reservation reservation = convertReservation(rs);
				if (reservation != null) {
					reservations.add(reservation);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reservations;
	}

	public static Reservation convertReservation(ResultSet rs) {
		try {
			Reservation reservation = new Reservation();
			reservation.setId(rs.getString("id"));
			reservation.setCustomerId(rs.getString("customer_id"));
			reservation.setDate(rs.getDate("date").toLocalDate());
			reservation.setTime(
					new TimeSlot(rs.getTime("time").toLocalTime(), rs.getTime("time").toLocalTime().plusMinutes(90)));
			reservation.setSpecialNotes(rs.getString("special_notes"));
			reservation.setTableId(rs.getString("table_id"));
			reservation.setPartySize(rs.getInt("party_size"));
			reservation.setServerId(rs.getString("server"));
			return reservation;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Customer convertCustomer(ResultSet rs) {
		try {
			Customer customer = new Customer();
			customer = new Customer();
			customer.setEmail(rs.getString("email"));
			customer.setFirstName(rs.getString("firstName"));
			customer.setLastName(rs.getString("lastName"));
			customer.setPassword(rs.getString("password"));
			return customer;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Table convertTable(ResultSet rs) {
		try {
			Table table = new Table();
			table.setCapacity(rs.getInt("capacity"));
			table.setId(rs.getString("id"));
			table.setServer(rs.getString("server"));
			return table;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Table> convertTableList(ResultSet rs) {
		List<Table> tables = new ArrayList<>();
		try {
			while (rs.next()) {
				Table table = convertTable(rs);
				if (table != null) {
					tables.add(table);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tables;
	}

	public static Server convertServer(ResultSet rs) {
		try {
			Server server = new Server();
			server.setFirstName(rs.getString("firstName"));
			server.setLastName(rs.getString("lastName"));
			server.setId(rs.getString("id"));
			return server;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Server> convertServerList(ResultSet rs) {
		List<Server> servers = new ArrayList<>();
		try {
			while (rs.next()) {
				Server server = convertServer(rs);
				if (server != null) {
					servers.add(server);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servers;
	}

	public static MenuItem convertMenuItem(ResultSet rs) {
		try {
			MenuItem menuItem = new MenuItem();
			menuItem.setId(rs.getString("id"));
			menuItem.setName(rs.getString("title"));
			menuItem.setPrice(rs.getFloat("price"));
			menuItem.setDescription(rs.getString("description"));
			return menuItem;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<MenuItem> convertMenuItemList(ResultSet rs) {
		List<MenuItem> menuItems = new ArrayList<>();
		try {
			while (rs.next()) {
				MenuItem menuItem = convertMenuItem(rs);
				if (menuItem != null) {
					menuItems.add(menuItem);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuItems;
	}

}
