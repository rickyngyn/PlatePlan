package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public interface QueryGenerator {
	public PreparedStatement generateInsertStatement(Connection conn, List<String> columns);

	public PreparedStatement generateUpdateStatement(Connection conn, List<String> columns);

}
