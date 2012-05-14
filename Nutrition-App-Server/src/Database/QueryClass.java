package Database;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * handles connection to the database and running queries
 * 
 * @author bellrj, Rob Wagner
 * 
 */
public class QueryClass {

	private static final String CONNECTION_PROTOCOL = "jdbc:sqlserver";
	private static final String SERVER = "whale.cs.rose-hulman.edu";
	private static final String USER = "376nutritionApp";
	private static final String PASSWORD = "nutrition";
	private static final String DATABASE = "Nutrition-App";
	private static final String URL = CONNECTION_PROTOCOL + "://" + SERVER
			+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";

	/**
	 * default constructor, registers the Driver, and tests the connection
	 */
	public QueryClass() {
		try {
			registerSQLServerDriver();
			System.out.println("Driver Registered");
			this.testConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * s a new food item to the database
	 * 
	 * @param f
	 *            the food to add to the database
	 * @throws IOException
	 */
	public void addFoodItem(Food f) throws IOException {
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn
					.prepareCall("{ call addFoodItem(?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			proc.setBytes(1, f.ngm.getByteArray());
			proc.setString(2, f.name);
			proc.setString(3, f.nameSpanish);
			proc.setString(4, f.nameFrench);
			proc.setString(5, f.nameGerman);
			proc.setFloat(6, f.calories);
			proc.setFloat(7, f.calFromFat);
			proc.setFloat(8, f.totalFat);
			proc.setFloat(9, f.sodium);
			proc.setFloat(10, f.carbs);
			proc.setFloat(11, f.fiber);
			proc.setFloat(12, f.sugar);
			proc.setFloat(13, f.protein);
			proc.execute();

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes the row of a given food item.
	 * 
	 * @param name
	 */
	public void deleteFoodItem(String name) {
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call deleteFoodItem(?) }");
			proc.setString(1, name);
			proc.execute();

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets all of the Food objects in the database
	 * 
	 * @return an ArrayList<Food> of all the foods in the database
	 * @throws IOException
	 */
	public ArrayList<Food> getFood() throws IOException {
		ArrayList<Food> food = new ArrayList<Food>();
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call getFood() }");
			proc.execute();

			ResultSet rs = null;
			// rows were returned
			rs = proc.getResultSet();
			if (rs != null) {
				while (rs.next()) {
					food.add(new Food(rs.getBytes(1), rs.getString(2), rs
							.getString(3), rs.getString(4), rs.getString(5), rs
							.getFloat(6), rs.getFloat(7), rs.getFloat(8), rs
							.getFloat(9), rs.getFloat(10), rs.getFloat(11), rs
							.getFloat(12), rs.getFloat(13)));
				}
			}

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return food;
	}

	/**
	 * gets a Food object in the database.
	 * 
	 * @param name
	 * @return Food from the database
	 * @throws IOException
	 */
	public Food getFoodItem(String name) throws IOException {
		Food food = null;
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call getFoodItem(?) }");
			proc.setString(1, name);
			proc.execute();

			ResultSet rs = proc.getResultSet();

			// Should return some sort of error if result set is null

			if (rs != null) {
				while (rs.next()) {
					food = new Food(rs.getBytes(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getFloat(6), rs.getFloat(7), rs.getFloat(8),
							rs.getFloat(9), rs.getFloat(10), rs.getFloat(11),
							rs.getFloat(12), rs.getFloat(13));
				}
			}
			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return food;
	}

	private boolean registerSQLServerDriver() throws SQLException {
		// Register JDBC driver
		DriverManager
				.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
		return true;
	}

	private void testConnection() throws SQLException {
		// Set connection parameters (change servername, username, and password
		String url = CONNECTION_PROTOCOL + "://" + SERVER + ";DatabaseName="
				+ DATABASE + ";SelectMethod=cursor;";
		Connection conn = getConnection(url);
		if (conn == null) {
			throw new SQLException("Null connection returned.");
		}
		System.out.println("Connected to the Database");
		conn.close();
		conn = null;
	}

	private Connection getConnection(String url) throws SQLException {
		Connection connection = DriverManager
				.getConnection(url, USER, PASSWORD);
		return connection;
	}
}
