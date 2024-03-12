package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import database.SQLTables;

public class Feedback implements QueryGenerator {
	public Feedback(String id, String customer_id, int rating, LocalDateTime timestamp, String feedback) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.rating = rating;
		this.timestamp = timestamp;
		this.feedback = feedback;
	}

	public Feedback() {
		super();
	}

	private String id;
	private String customer_id;
	private int rating;
	private LocalDateTime timestamp;
	private String feedback;

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
	 * @return the customer_id
	 */
	public String getCustomer_id() {
		return customer_id;
	}

	/**
	 * @param customer_id the customer_id to set
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the feedback
	 */
	public String getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback the feedback to set
	 */
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer_id, feedback, id, rating, timestamp);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feedback other = (Feedback) obj;
		return Objects.equals(customer_id, other.customer_id) && Objects.equals(feedback, other.feedback)
				&& Objects.equals(id, other.id) && rating == other.rating && Objects.equals(timestamp, other.timestamp);
	}

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", customer_id=" + customer_id + ", rating=" + rating + ", timestamp=" + timestamp
				+ ", feedback=" + feedback + "]";
	}

	@Override
	public PreparedStatement generateInsertStatement(Connection connection, List<String> columns) {
		try {
			String sql = "INSERT INTO %s %s VALUES ";

			sql = String.format(sql, SQLTables.FEEDBACKS_TABLE, "(" + String.join(",", columns) + ")");
			sql = sql + "(?,?,?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, this.getId());
			pstmt.setString(2, this.getCustomer_id());
			pstmt.setInt(3, this.getRating());
			pstmt.setTimestamp(4, Timestamp.valueOf(this.getTimestamp()));
			pstmt.setString(5, this.getFeedback());

			return pstmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PreparedStatement generateUpdateStatement(Connection conn, List<String> columns) {
		return null;
	}
}
