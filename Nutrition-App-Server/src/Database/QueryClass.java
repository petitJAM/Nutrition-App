package Database;

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
	 * gets the device with the specified id from the database
	 * 
	 * @param i
	 *            the id of the Device to retrieve from the Database
	 * @return object describing the Device gotten from the database, null if
	 *         there was an error
	 */
	public Device getDevice(int i) {
		Device result = null;
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call getDevice(?) }");
			proc.setInt(1, i);
			proc.execute();

			ResultSet rs = null;
			// rows were returned
			rs = proc.getResultSet();
			if (rs != null && rs.next()) {
				int ID = rs.getInt(1);
				int times_used = rs.getInt(2);
				int times_correct = rs.getInt(3);
				result = new Device(ID, times_used, times_correct);
			}

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * updates a Device in the database and returns it
	 * 
	 * @param i
	 *            the id of the device to update
	 * @param j
	 *            the number of times this device has used our service
	 * @param k
	 *            the number of times we correctly identified a food for this
	 *            device
	 * @return a new Device object representing the modified device we created,
	 *         null if error
	 */
	public Device updateDevice(int i, int j, int k) {
		if (j < 0 || k < 0 || k > j) {
			return null;
		}
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call updateDevice(?,?,?) }");
			proc.setInt(1, i);
			proc.setInt(2, j);
			proc.setInt(3, k);
			int affected = proc.executeUpdate();
			if (affected <= 0) {
				return null;
			}

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return new Device(i, j, k);
	}

	/**
	 * s a new food item to the database
	 * 
	 * @param f
	 *            the food to add to the database
	 */
	public void addFoodItem(Food f) {
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn
					.prepareCall("{ call addFoodItem(?,?,?,?,?,?,?,?,?,?) }");
			proc.setBytes(1, f.Transition_Matrix);
			proc.setString(2, f.name);
			proc.setFloat(3, f.calories);
			proc.setFloat(4, f.calFromFat);
			proc.setFloat(5, f.totalFat);
			proc.setFloat(6, f.sodium);
			proc.setFloat(7, f.carbs);
			proc.setFloat(8, f.fiber);
			proc.setFloat(9, f.sugar);
			proc.setFloat(10, f.protein);
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
	 */
	public ArrayList<Food> getFood() {
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
							.getFloat(3), rs.getFloat(4), rs.getFloat(5), rs
							.getFloat(6), rs.getFloat(7), rs.getFloat(8), rs
							.getFloat(9), rs.getFloat(10)));
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
	 * creates a new device in the database, and returns the new id for it
	 * 
	 * @return the unique identifier for this device
	 */
	public Device newDevice() {
		Device result = null;
		try {
			Connection conn = null;
			conn = getConnection(URL);

			CallableStatement proc;
			proc = conn.prepareCall("{ call addDevice() }");
			proc.execute();

			ResultSet rs = null;

			rs = proc.getResultSet();

			if (rs != null && rs.next()) {
				int ID = rs.getInt(1);
				result = new Device(ID, 0, 0);
			}

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private boolean registerSQLServerDriver() throws SQLException {
		// Register JDBC driver
		DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
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
