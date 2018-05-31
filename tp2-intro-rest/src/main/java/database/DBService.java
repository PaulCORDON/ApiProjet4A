package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;
import fr.ensim.projet4a.model.ParamEl1;
import fr.ensim.projet4a.model.ParamEm1;
import fr.ensim.projet4a.model.ParamEm2;
import fr.ensim.projet4a.model.SousCompetence;

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

	/* marche */
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

	/* marche */
	public static ArrayList<Classe> getAllClasseFromDB() {
		ArrayList<Classe> TabClasse = new ArrayList<Classe>();
		log("dans le get  all classe");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM classe";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				Classe cl = new Classe();
				cl = getClasseFromDB(rs.getString("nom"));
				TabClasse.add(cl);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return TabClasse;

	}

	/* marche */
	public static Classe getClasseFromDB(String nom) {
		log("dans le get classe");
		Connection conn = null;
		PreparedStatement prepareStat = null;
		PreparedStatement prepareStat1 = null;
		Classe cl = new Classe();
		try {
			conn = makeJDBCConnection();

			String selectQueryStatement = "SELECT * FROM classe WHERE nom = ?";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			prepareStat.setString(1, nom);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				cl.setNom(rs.getString("nom"));
				cl.setId(rs.getInt("id"));
				cl.setListeEleve(new ArrayList<Eleve>());
			}

			selectQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT id FROM classe WHERE nom = ?)";
			prepareStat1 = conn.prepareStatement(selectQueryStatement);
			prepareStat1.setString(1, nom);
			rs = prepareStat1.executeQuery();
			while (rs.next()) {
				Eleve el = new Eleve();
				el.setNomPrenom(rs.getString("nomPrenom"));
				el.setDateDeNaissance(rs.getDate("dateDeNaissance"));
				el.setClasseId(rs.getInt("classeId"));
				el.setClasseName(nom);
				cl.getListeEleve().add(el);
			}
			log(cl.getNom() + " select successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
					prepareStat1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
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

	/* marche */
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
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/* marche */
	public static void deleteClasseFromDB(@NotNull String nom) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement prepareStat = null;
		PreparedStatement prepareStat1 = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "DELETE FROM eleve WHERE classeId = (SELECT id FROM classe WHERE nom = ?)";
			String getQueryStatement1 = "DELETE FROM classe WHERE nom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat1 = conn.prepareStatement(getQueryStatement1);
			prepareStat1.setString(1, nom);

			// Execute the Query, and get a java ResultSet
			prepareStat.execute();
			prepareStat1.execute();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null || prepareStat1 != null) {
				try {
					prepareStat.close();
					prepareStat1.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/* marche */
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
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/* marche */
	public static ArrayList<Eleve> getEleveFromDB(@NotNull String nomPrenom, @NotNull String nom) {
		ArrayList<Eleve> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT id FROM classe WHERE nom = ?) && nomPrenom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Eleve ele = new Eleve(rs.getString("nomPrenom"), rs.getDate("dateDeNaissance"), nom,
						rs.getInt("classeId"));
				el.add(ele);
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return el;

	}

	/* marche */
	public static void deleteEleveFromDB(@NotNull String nomPrenom, @NotNull String nom) {

		Connection conn = null;
		PreparedStatement prepareStat = null;
		PreparedStatement prepareStat1 = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "DELETE FROM souscompetenceeleve WHERE idEleve = (SELECT idEleve FROM eleve WHERE nomPrenom = ? && classeId=(SELECT id FROM classe WHERE nom = ?))";
			String getQueryStatement1 = "DELETE FROM eleve WHERE nomPrenom = ? && classeId=(SELECT id FROM classe WHERE nom = ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomPrenom);
			prepareStat.setString(2, nom);
			prepareStat1 = conn.prepareStatement(getQueryStatement1);
			prepareStat1.setString(1, nomPrenom);
			prepareStat1.setString(2, nom);

			// Execute the Query, and get a java ResultSet
			prepareStat.execute();
			prepareStat1.execute();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null || prepareStat1 != null) {
				try {
					prepareStat.close();
					prepareStat1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/* marche */
	public static void updateEleveFromDB(@NotNull String nomPrenom, @NotNull String nom, Eleve elmod) {

		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			log("coucou");
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "UPDATE eleve SET `nomPrenom`=?,`dateDeNaissance`=?,`classeId`=(SELECT id FROM classe WHERE nom=?) WHERE idEleve = (SELECT idEleve FROM (SELECT * FROM eleve) AS el WHERE nomPrenom = ? && classeId=(SELECT id FROM classe WHERE nom = ?))";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, elmod.getNomPrenom());
			prepareStat.setDate(2, elmod.getDateDeNaissance());
			prepareStat.setString(3, elmod.getClasseName());
			prepareStat.setString(4, nomPrenom);
			prepareStat.setString(5, nom);

			// Execute the Query, and get a java ResultSet
			prepareStat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();

				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}

	/* marche */
	public static ArrayList<ParamEm1> getAllParamEm1FromDB() {
		ArrayList<ParamEm1> tabParam = new ArrayList<ParamEm1>();
		log("dans le get  all paramEm1");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramEm1";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				tabParam.add(new ParamEm1(rs.getString("nom"), rs.getBoolean("frise"), rs.getLong("tempsRep"),
						rs.getBoolean("pairOnly"), tabOpp, rs.getInt("nbBornes"), rs.getInt("nbQuestions"),
						rs.getBoolean("disparition"), rs.getLong("tempsRestantApparant"),
						rs.getBoolean("ordreApparition"), rs.getBoolean("borneSelectionnable"),
						rs.getBoolean("borneEqualsOp"), rs.getInt("valMax")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return tabParam;
	}

	/* marche */
	public static ArrayList<ParamEm2> getAllParamEm2FromDB() {
		ArrayList<ParamEm2> tabParam = new ArrayList<ParamEm2>();
		log("dans le get  all paramEm2");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramEm2";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				tabParam.add(new ParamEm2(rs.getString("nom"), rs.getLong("tempsRep"), rs.getBoolean("pairOnly"),
						tabOpp, rs.getInt("typeRep"), rs.getInt("nbCalcul"), rs.getInt("valMaxOperande"),
						rs.getBoolean("nombreImpair"), rs.getBoolean("nombrePair"), rs.getBoolean("repDeuxBornes"),
						rs.getBoolean("repPaveNum"), rs.getBoolean("repQuatreBornes"), rs.getBoolean("calcChaine")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return tabParam;
	}

	/* marche */
	public static ArrayList<ParamEl1> getAllParamEl1FromDB() {
		ArrayList<ParamEl1> tabParam = new ArrayList<ParamEl1>();
		log("dans le get  all paramEl1");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramEl1";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				tabParam.add(new ParamEl1(rs.getString("nom"), rs.getInt("nbEnonce"), rs.getLong("tempsApparution"),
						rs.getInt("nbApparition"), rs.getBoolean("multipleApparution"),
						rs.getBoolean("enonceDisparait"), rs.getInt("nbAparitionSimultanee")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return tabParam;
	}

	/* marche */
	public static ArrayList<ParamEl1> getEleveParamEl1FromBD(@NotNull String nomPrenom, @NotNull String nom) {
		ArrayList<ParamEl1> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramel1 WHERE classeId = (SELECT id FROM classe WHERE nom = ?) && nomPrenom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				el.add(new ParamEl1(rs.getString("nom"), rs.getInt("nbEnonce"), rs.getLong("tempsApparution"),
						rs.getInt("nbApparition"), rs.getBoolean("multipleApparution"),
						rs.getBoolean("enonceDisparait"), rs.getInt("nbAparitionSimultanee")));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return el;

	}

	/* marche */
	public static ArrayList<ParamEm1> getEleveParamEm1FromBD(@NotNull String nomPrenom, @NotNull String nom) {
		ArrayList<ParamEm1> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramem1 WHERE classeId = (SELECT id FROM classe WHERE nom = ?) && nomPrenom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				el.add(new ParamEm1(rs.getString("nom"), rs.getBoolean("frise"), rs.getLong("tempsRep"),
						rs.getBoolean("pairOnly"), tabOpp, rs.getInt("nbBornes"), rs.getInt("nbQuestions"),
						rs.getBoolean("disparition"), rs.getLong("tempsRestantApparant"),
						rs.getBoolean("ordreApparition"), rs.getBoolean("borneSelectionnable"),
						rs.getBoolean("borneEqualsOp"), rs.getInt("valMax")));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return el;

	}

	/* marche */
	public static ArrayList<ParamEm2> getEleveParamEm2FromBD(@NotNull String nomPrenom, @NotNull String nom) {
		ArrayList<ParamEm2> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramem2 WHERE classeId = (SELECT id FROM classe WHERE nom = ?) && nomPrenom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				el.add(new ParamEm2(rs.getString("nom"), rs.getLong("tempsRep"), rs.getBoolean("pairOnly"), tabOpp,
						rs.getInt("typeRep"), rs.getInt("nbCalcul"), rs.getInt("valMaxOperande"),
						rs.getBoolean("nombreImpair"), rs.getBoolean("nombrePair"), rs.getBoolean("repDeuxBornes"),
						rs.getBoolean("repPaveNum"), rs.getBoolean("repQuatreBornes"), rs.getBoolean("calcChaine")));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return el;

	}

	/* a tester */
	public static ArrayList<SousCompetence> getEleveSousCompetencesFromBD(@NotNull String nomPrenom,
			@NotNull String nom) {
		ArrayList<SousCompetence> tabSousComp = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM souscompetenceeleve NATURAL JOIN eleve NATURAL JOIN souscompetence NATURAL JOIN competence WHERE classeId = (SELECT id FROM classe WHERE nom = ?) && nomPrenom = ? ORDER BY `competence`.`nomCompetence` ASC";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {

				tabSousComp.add(new SousCompetence(rs.getString("nomSousCompetence"), rs.getFloat("progression"),
						rs.getInt("nbTest"), rs.getString("descriptionSousCompetence"), rs.getString("nomCompetence")));
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return tabSousComp;

	}

	public static void addParamEl1ToDB(@Valid ParamEl1 param) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			String insertQueryStatement = "INSERT INTO `paramel1`(`nom`, `nbEnonce`, `tempsApparution`, `nbApparition`, `multipleApparution`, `nbAparitionSimultanee`, `enonceDisparait`, `tempsEnonce`) VALUES (?,?,?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, param.nom);
			prepareStat.setInt(2, param.getNbEnonce());
			prepareStat.setLong(3, param.getTempsApparution());
			prepareStat.setInt(4, param.getNbApparution());
			prepareStat.setBoolean(5, param.getMultipleApparution());
			prepareStat.setInt(6, param.getNbAparitionSimultanee());
			prepareStat.setBoolean(7, param.getEnonceDisparait());
			prepareStat.setLong(8, param.getTempsEnonce());
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log(param.nom + " added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	/*pas fini*/
	public static void addParamEm1ToDB(@Valid ParamEm1 param) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			String insertQueryStatement = "INSERT INTO `paramem1`(`nom`, `nbBornes`, `nbQuestions`, `disparition`, `tempsRestantApparant`, `ordreApparition`, `borneSelectionnable`, `borneEqualsOp`, `valMax`, `frise`, `tempsRep`, `pairOnly`, `operateur1`, `operateur2`, `operateur3`, `operateur4`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, param.nom);
			prepareStat.setInt(2, param.getNbBornes());
			prepareStat.setInt(3, param.getNbQuestions());
			prepareStat.setBoolean(4, param.getDisparition());
			prepareStat.setLong(5, param.getTempsRestantApparant());
			prepareStat.setBoolean(6, param.getOrdreApparition());
			prepareStat.setBoolean(7, param.getBorneSelectionnable());
			prepareStat.setBoolean(8, param.getBorneEqualsOp());
			prepareStat.setInt(9,param.getValMax());
			//TODO la suite//
			
			prepareStat.executeUpdate();
			log(param.nom + " added successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void addParamEm2ToDB(@Valid ParamEm2 param) {
			
		}


	public static ArrayList<String> getEnonceFromBD(@NotNull String mot) {
		ArrayList<String> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM mots WHERE mot = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			
			
			prepareStat.setString(1, mot);
			

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();


		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return el;
		
	}
	
	
	// Simple log utility
	private static void log(String string) {
		System.out.println(string);

	}
	

}
