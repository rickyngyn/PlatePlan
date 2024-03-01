package dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MenuItem {
	
	private String id;
	private String name;
	private String description;
	private float price;
	
	public MenuItem(String id, String name, String description, float price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
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

	public MenuItem() {

	}
	


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Float.floatToIntBits(price) == Float.floatToIntBits(other.price);
	}

	@Override
	public String toString() {
		return "MenuItem [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
	
    public PreparedStatement generateUpdateCommand(Connection conn, List<String> columns, String tableName) {
    	try {
            columns.remove(0);
            String sql = "UPDATE " + tableName + " SET " + 
                         String.join(", ", columns.stream().map(column -> column + " = ?").toArray(String[]::new)) +
                         " WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,getName());
            stmt.setFloat(2,getPrice());
            stmt.setString(3,getDescription());
            stmt.setString(4,getId());

            // Setting parameter values
//            for (int i = 0; i < columns.size(); i++) {
//                if ("price".equals(columns.get(i))) {
//                    stmt.setFloat(i + 1, getPrice());
//                } else {
//                    stmt.setString(i + 1, (String) Arrays.asList(getName(), getDescription()).get(i));
//                }
//            }
//            
//            // Setting the ID parameter
//            stmt.setString(columns.size() + 1, getId());
            System.out.println(stmt.toString());

            return stmt;
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
        
        
        return null;
    }

}
