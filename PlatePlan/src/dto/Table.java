package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Table {

	public Table() {

	}

	public Table(String id, int capacity, String server) {
		super();
		this.id = id;
		this.capacity = capacity;
		this.server = server;
	}

	private String id;

	private int capacity;

	private String server;

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
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}

	@Override
	public int hashCode() {
		return Objects.hash(capacity, id, server);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		return capacity == other.capacity && Objects.equals(id, other.id) && Objects.equals(server, other.server);
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", capacity=" + capacity + ", server=" + server + "]";
	}

	public PreparedStatement getSQLString(Connection connection, String sql) {
		try {
			sql = sql + "(?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, this.getId());
			pstmt.setInt(2, this.getCapacity());
			pstmt.setString(3, this.getServer());

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
			stmt.setInt(1, getCapacity());
			stmt.setString(2, getServer());
			stmt.setString(3, getId());

			return stmt;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
