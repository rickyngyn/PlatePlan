package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Business;
import dto.Customer;
import dto.Feedback;
import dto.MenuItem;
import dto.Order;
import dto.Receipt;
import dto.Reservation;
import dto.Server;
import dto.Table;
import dto.TimeSlot;

public class DataBaseConverters {

	public static List<Reservation> convertReservationList(ResultSet rs, Business business) {
		List<Reservation> reservations = new ArrayList<>();
		try {
			while (rs.next()) {
				Reservation reservation = convertReservation(rs, business);
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

	public static Reservation convertReservation(ResultSet rs, Business business) {
		try {
			Reservation reservation = new Reservation();

			reservation.setId(rs.getString("id"));
			reservation.setCustomerId(rs.getString("customer_id"));
			reservation.setDate(rs.getDate("date").toLocalDate());
			reservation.setTime(new TimeSlot(rs.getTime("time").toLocalTime(),
					rs.getTime("time").toLocalTime().plusMinutes(business.getReservationSlots())));
			reservation.setSpecialNotes(rs.getString("special_notes"));
			reservation.setTableId(rs.getString("table_id"));
			reservation.setPartySize(rs.getInt("party_size"));
			reservation.setServerId(rs.getString("server"));
			return reservation;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

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

	public static Feedback convertFeedbackItem(ResultSet rs) {
		try {
			Feedback feedback = new Feedback();
			feedback.setId(rs.getString("id"));
			feedback.setCustomer_id(rs.getString("customer_id"));
			feedback.setFeedback(rs.getString("feedback"));
			feedback.setRating(rs.getInt("rating"));
			feedback.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
			return feedback;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Feedback> convertFeedbackItemList(ResultSet rs) {
		List<Feedback> feedbacks = new ArrayList<>();
		try {
			while (rs.next()) {
				Feedback feedback = convertFeedbackItem(rs);
				if (feedback != null) {
					feedbacks.add(feedback);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return feedbacks;
	}

	public static Order convertOrderItem(ResultSet rs) {
		try {
			Order order = new Order();
			order.setId(rs.getString("id"));
			order.setCustomer(rs.getString("customer"));
			order.setItem(rs.getString("item"));
			order.setPrice(rs.getDouble("price"));
			order.setQuantity(rs.getInt("quantity"));
			order.setDate(rs.getDate("date").toLocalDate());
			order.setReservation(rs.getString("reservation"));

			return order;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Order> convertOrderItemList(ResultSet rs) {
		List<Order> orders = new ArrayList<>();
		try {
			while (rs.next()) {
				Order order = convertOrderItem(rs);
				if (order != null) {
					orders.add(order);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orders;
	}

	public static Receipt convertReceiptItem(ResultSet rs) {
		try {
			Receipt receipt = new Receipt();

			receipt.setId(rs.getString("id"));
			receipt.setCustomer(rs.getString("customer"));
			receipt.setReservation(rs.getString("reservation"));
			receipt.setDate(rs.getDate("date").toLocalDate());
			receipt.setTime(rs.getTime("time").toLocalTime());
			receipt.setSubtotal(rs.getDouble("subtotal"));
			receipt.setTax(rs.getDouble("tax"));
			receipt.setSubtotal(rs.getDouble("subtotal"));
			receipt.setTip_percent(rs.getInt("tip"));
			receipt.setTotal(rs.getDouble("total"));
			receipt.setPaid(rs.getBoolean("paid"));

			return receipt;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Receipt> convertReceiptItemList(ResultSet rs) {
		List<Receipt> receipts = new ArrayList<>();
		try {
			while (rs.next()) {
				Receipt receipt = convertReceiptItem(rs);
				if (receipt != null) {
					receipts.add(receipt);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return receipts;
	}

}
