package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {

	static Connection conn = null;
	static PreparedStatement prepareStat = null;
 
	public static void main(String[] argv) {
 
		try {
			log("-------- Simple Crunchify Tutorial on how to make JDBC connection to MySQL DB locally on macOS ------------");
			makeJDBCConnection();
 
			log("\n---------- Adding student to DB ----------");
			addDataToDB("Cordon Paul", Date.valueOf("1996-10-09"));
			addDataToDB("LeMechec Valentin", Date.valueOf("1996-04-28"));
			addDataToDB("Paillier Florentin", Date.valueOf("1995-07-05"));
 
			log("\n---------- Let's get Data from DB ----------");
			getDataFromDB();
 
			prepareStat.close();
			conn.close(); // connection close
 
		} catch (SQLException e) {
 
			e.printStackTrace();
		}
	}
 
	private static void makeJDBCConnection() {
 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}
 
		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projet4a", "root", "");
			if (conn != null) {
				log("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}
 
	}
 
	private static void addDataToDB(String nomprenom, Date date) {
 
		try {
			String insertQueryStatement = "INSERT  INTO  eleve (nomPrenom, dateDeNaissance) VALUES  (?,?)";
 
			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, nomprenom);
			prepareStat.setDate(2, date);

 
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log(nomprenom + " added successfully");
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
	}
 
	private static void getDataFromDB() {
 
		try {
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve";
 
			prepareStat = conn.prepareStatement(getQueryStatement);
 
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
 
			// Let's iterate through the java ResultSet
			while (rs.next()) {
				String nomPrenom = rs.getString("nomPrenom");
				Date dateDeNaissance = rs.getDate("dateDeNaissance");
			
 
				// Simply Print the results
				System.out.format("%s, %s\n",dateDeNaissance, nomPrenom);
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
 
	// Simple log utility
	private static void log(String string) {
		System.out.println(string);
 
	}
}
