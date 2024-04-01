package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import database.SQLTables;

public class Server implements QueryGenerator {

	public Server() {
	}

	public Server(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private String id;

	private String firstName;

	private String lastName;

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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Server other = (Server) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

	@Override
	public PreparedStatement generateInsertStatement(Connection conn, List<String> columns) {
		try {
			String sql = "INSERT INTO %s %s VALUES ";

			sql = String.format(sql, SQLTables.SERVERS_TABLE, "(" + String.join(",", columns) + ")");
			sql = sql + "(?,?,?);";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, this.getId());
			pstmt.setString(2, this.getFirstName());
			pstmt.setString(3, this.getLastName());

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
