package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ch.qos.logback.core.net.SyslogOutputStream;
import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;

public class DBService {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			throw new RuntimeException(e);
		}
	}

	// public static void main(String[] argv) {
	//
	// try {
	// log("-------- Simple Crunchify Tutorial on how to make JDBC connection to
	// MySQL DB locally on macOS ------------");
	// makeJDBCConnection();
	//
	// log("\n---------- Adding student to DB ----------");
	// addDataToDB("Cordon Paul", Date.valueOf("1996-10-09"));
	// addDataToDB("LeMechec Valentin", Date.valueOf("1996-04-28"));
	// addDataToDB("Paillier Florentin", Date.valueOf("1995-07-05"));
	//
	// log("\n---------- Let's get Data from DB ----------");
	// getDataFromDB();
	//
	// prepareStat.close();
	// conn.close(); // connection close
	//
	// } catch (SQLException e) {
	//
	// e.printStackTrace();
	// }
	// }

	private static Connection makeJDBCConnection() {
		Connection conn = null;
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
		}
		return conn;
	}

	
	
	/*marche*/
	public static Classe getClasseFromDB(String nom) {
		log("dans le get classe");
		Connection conn = null;
		PreparedStatement prepareStat = null;
		PreparedStatement prepareStat1=null;
		Classe cl=new Classe();
		try {
			conn = makeJDBCConnection();

			String selectQueryStatement = "SELECT * FROM classe WHERE nom = ?";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			prepareStat.setString(1, nom);			
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();
			
			while (rs.next()) {
				cl.setNom(rs.getString("nom"));	
				cl.setListeEleve(new ArrayList<Eleve>());
			}
			
			selectQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT id FROM classe WHERE nom = ?)";
			prepareStat1 = conn.prepareStatement(selectQueryStatement);
			prepareStat1.setString(1, nom);
			rs = prepareStat1.executeQuery();
			while (rs.next()) {
				Eleve el= new Eleve();
				el.setNomPrenom(rs.getString("nomPrenom"));
				el.setDateDeNaissance(rs.getDate("dateDeNaissance"));
				el.setClasseId(rs.getInt("classeId"));
				el.setClasseName(nom);
				cl.getListeEleve().add(el);				
			}
			log(cl.getNom() + " select successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(prepareStat!=null) {
				try {
					prepareStat.close();
					prepareStat1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cl;
	}
	/*marche*/
	public static void addClasseToDB(String nom) {
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT  INTO  classe (nom) VALUES  (?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, nom);

			// execute insert SQL statement
			prepareStat.executeUpdate();
			log(nom + " added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(prepareStat!=null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*marche*/
	public static void addEleveToDB(String nomprenom, Date date, String classeName) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			String insertQueryStatement = "INSERT  INTO  eleve (nomPrenom, dateDeNaissance,classeId) VALUES  (?,?,(SELECT id FROM classe WHERE nom = ?))";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, nomprenom);
			prepareStat.setDate(2, date);
			prepareStat.setString(3, classeName);

			// execute insert SQL statement
			prepareStat.executeUpdate();
			log(nomprenom + " added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(prepareStat!=null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*marche pas encore*/
	public static ArrayList<Eleve> getEleveFromDB(String nomprenom) {
		ArrayList<Eleve> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT id FROM classe WHERE nom = ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomprenom);
		

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				String nomPrenom = rs.getString("nomPrenom");
				Date dateDeNaissance = rs.getDate("dateDeNaissance");
				Eleve ele = new Eleve(nomPrenom, dateDeNaissance);
				el.add(ele);
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return el;

	}

	// Simple log utility
	private static void log(String string) {
		System.out.println(string);

	}
}
