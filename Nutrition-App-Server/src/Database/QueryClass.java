package Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryClass {

	private static final String CONNECTION_PROTOCOL = "jdbc:sqlserver";
	private static final String SERVER = "whale.cs.rose-hulman.edu";
	private static final String USER = "";
	private static final String PASSWORD = "";
	private static final String DATABASE = "Nutrition-App";

	public QueryClass() {
		try {
			registerSQLServerDriver();
			System.out.println("Driver Registered");
			this.testConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Device getDevice(int i) {
		Device result = null;
		try {
			String url = CONNECTION_PROTOCOL + "://" + SERVER
					+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";
			Connection conn = null;
			conn = getConnection(url);

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

	public Device updateDevice(int i, int j, int k) {
		if (j < 0 || k < 0 || k > j) {
			return null;
		}
		try {
			String url = CONNECTION_PROTOCOL + "://" + SERVER
					+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";
			Connection conn = null;
			conn = getConnection(url);

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

	public void addFoodItem(Food f) {
		try {
			String url = CONNECTION_PROTOCOL + "://" + SERVER
					+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";
			Connection conn = null;
			conn = getConnection(url);

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

			ResultSet rs = null;

			proc.close();
			proc = null;
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Food> getFood() {
		ArrayList<Food> food = new ArrayList<Food>();
		try {
			String url = CONNECTION_PROTOCOL + "://" + SERVER
					+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";
			Connection conn = null;
			conn = getConnection(url);

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

	public Device newDevice() {
		Device result = null;
		try {
			String url = CONNECTION_PROTOCOL + "://" + SERVER
					+ ";DatabaseName=" + DATABASE + ";SelectMethod=cursor;";
			Connection conn = null;
			conn = getConnection(url);

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
