/**
 * 
 */
package dto;

import java.time.LocalDate;

public class Reservation {

	public Reservation () {
		
	}
	public Reservation(String id, String customerId, LocalDate date, TimeSlot time, String specialNotes, String server, String tableId,
			int partySize) {
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

	public Reservation(String id, String customerId, LocalDate date, TimeSlot time, String specialNotes, String tableId,
			int partySize) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.date = date;
		this.time = time;
		this.specialNotes = specialNotes;
		this.tableId = tableId;
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
				+ ", specialNotes=" + specialNotes + ", tableId=" + tableId + ", partySize="
				+ partySize + "]";
	}


	public String getServerId() {
		return serverId;
	}


	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
