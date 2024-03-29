/**
 * 
 */
package dto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class Reservation {

	public Reservation() {

	}

	public Reservation(String id, String customerId, LocalDate date, TimeSlot time, String specialNotes, String server,
			String tableId, int partySize) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.time = time;
		this.specialNotes = specialNotes;
		this.tableId = tableId;
		this.serverId = server;
		this.partySize = partySize;
	}

	

	private String id;

	private String customerId;

	private LocalDate date;

	private TimeSlot time;

	private String specialNotes;

	private String tableId;

	private String serverId;

	private int partySize;

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
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public TimeSlot getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(TimeSlot time) {
		this.time = time;
	}

	/**
	 * @return the specialNotes
	 */
	public String getSpecialNotes() {
		return specialNotes;
	}

	/**
	 * @param specialNotes the specialNotes to set
	 */
	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}

	/**
	 * @return the tableId
	 */
	public String getTableId() {
		return tableId;
	}

	/**
	 * @param tableId the tableId to set
	 */
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	/**
	 * @return the partySize
	 */
	public int getPartySize() {
		return partySize;
	}

	/**
	 * @param partySize the partySize to set
	 */
	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", customerId=" + customerId + ", date=" + date + ", time=" + time
				+ ", specialNotes=" + specialNotes + ", tableId=" + tableId + ", partySize=" + partySize + "]";
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public PreparedStatement getSQLString(Connection connection, String sql) {
		try {
			sql = sql + "(?,?,?,?,?,?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, this.getId());
			pstmt.setString(2, this.getCustomerId());
			pstmt.setDate(3, Date.valueOf(this.date.toString()));
			pstmt.setTime(4, Time.valueOf(this.getTime().getFrom())); // Assuming TimeSlot can be converted to String
																		// directly or has a toString method
			pstmt.setString(5, this.getSpecialNotes());
			pstmt.setString(6, this.getTableId());
			pstmt.setInt(7, this.getPartySize());
			pstmt.setString(8, this.getServerId());

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

			stmt.setString(1, this.customerId);
			stmt.setDate(2, Date.valueOf(this.date));
			stmt.setTime(3, Time.valueOf(this.time.getFrom()));
			stmt.setString(4, this.specialNotes);
			stmt.setString(5, this.tableId);
			stmt.setInt(6, this.partySize);

			stmt.setString(7, this.serverId);

			stmt.setString(8, this.id);

			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
