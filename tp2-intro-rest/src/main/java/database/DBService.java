package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import fr.ensim.projet4a.model.Calcul;
import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;
import fr.ensim.projet4a.model.Enonce;
import fr.ensim.projet4a.model.Exo1Lecture;
import fr.ensim.projet4a.model.Exo1Math;
import fr.ensim.projet4a.model.Exo2Math;
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
				cl = getClasseFromDB(rs.getString("nomClasse"));
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

			String selectQueryStatement = "SELECT * FROM classe WHERE nomClasse = ?";
			prepareStat = conn.prepareStatement(selectQueryStatement);
			prepareStat.setString(1, nom);
			// execute select SQL statement
			ResultSet rs = prepareStat.executeQuery();

			while (rs.next()) {
				cl.setNom(rs.getString("nomClasse"));
				cl.setId(rs.getInt("classeId"));
				cl.setListeEleve(new ArrayList<Eleve>());
			}

			selectQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?)";
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

			String insertQueryStatement = "INSERT  INTO  classe (nomClasse) VALUES  (?)";

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

	public static void deleteAllEleveFromClasse(@NotNull String nom) {
		Classe c = getClasseFromDB(nom);
		for (Eleve el : c.getListeEleve()) {
			deleteEleveFromDB(el.getNomPrenom(), nom);
		}
	}

	/* marche */
	public static void deleteClasseFromDB(@NotNull String nom) {
		Connection conn = null;
		PreparedStatement prepareStat1 = null;
		deleteAllEleveFromClasse(nom);
		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement1 = "DELETE FROM classe WHERE nnomClasseom = ?";
			prepareStat1 = conn.prepareStatement(getQueryStatement1);
			prepareStat1.setString(1, nom);

			// Execute the Query, and get a java ResultSet
			prepareStat1.execute();

		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat1 != null) {
				try {
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
			String insertQueryStatement = "INSERT  INTO  eleve (nomPrenom, dateDeNaissance,classeId) VALUES  (?,?,(SELECT classeId FROM classe WHERE nomClasse = ?))";

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
			String getQueryStatement = "SELECT * FROM eleve WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?) && nomPrenom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.setString(2, nomPrenom);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Eleve ele = new Eleve(rs.getString("nomPrenom"), rs.getDate("dateDeNaissance"), nom,
						rs.getInt("classeId"), rs.getInt("idEleve"));
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
		PreparedStatement prepareStat2 = null;

		try {
			conn = makeJDBCConnection();
			String getQueryStatement2 = "DELETE FROM parameleve WHERE idEleve=(SELECT idEleve FROM eleve WHERE nomPrenom = ?)";
			prepareStat2 = conn.prepareStatement(getQueryStatement2);
			prepareStat2.setString(1, nomPrenom);
			prepareStat2.execute();

			String getQueryStatement = "DELETE FROM souscompetenceeleve WHERE idEleve = (SELECT idEleve FROM eleve WHERE nomPrenom = ? && classeId=(SELECT classeId FROM classe WHERE nomClasse = ?))";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomPrenom);
			prepareStat.setString(2, nom);
			prepareStat.execute();

			String getQueryStatement1 = "DELETE FROM eleve WHERE nomPrenom = ? && classeId=(SELECT classeId FROM classe WHERE nomClasse = ?)";
			prepareStat1 = conn.prepareStatement(getQueryStatement1);
			prepareStat1.setString(1, nomPrenom);
			prepareStat1.setString(2, nom);
			prepareStat1.execute();

			log(nomPrenom + "supprimé de la BDD");
		} catch (

		SQLException e) {
			e.printStackTrace();
		} finally {
			if (prepareStat != null || prepareStat1 != null) {
				try {
					prepareStat.close();
					prepareStat1.close();
					prepareStat2.close();
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
			String getQueryStatement = "UPDATE eleve SET `nomPrenom`=?,`dateDeNaissance`=?,`classeId`=(SELECT classeId FROM classe WHERE nomClasse=?) WHERE idEleve = (SELECT idEleve FROM (SELECT * FROM eleve) AS el WHERE nomPrenom = ? && classeId=(SELECT classeId FROM classe WHERE nomClasse = ?))";
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
		log("dans le get  all paramem1");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramem1";
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
		log("dans le get  all paramem2");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramem2";
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
		log("dans le get  all paramel1");
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			String selectQueryStatement = "SELECT * FROM paramel1";
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
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramel1 WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?) && nomPrenom = ?";
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
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramem1 WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?) && nomPrenom = ?";
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
			String getQueryStatement = "SELECT * FROM eleve NATURAL JOIN paramem2 WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?) && nomPrenom = ?";
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

	/* marche */
	public static ArrayList<SousCompetence> getEleveSousCompetencesFromBD(@NotNull String nomPrenom,
			@NotNull String nom) {
		ArrayList<SousCompetence> tabSousComp = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM souscompetenceeleve NATURAL JOIN eleve NATURAL JOIN souscompetence NATURAL JOIN competence WHERE classeId = (SELECT classeId FROM classe WHERE nomClasse = ?) && nomPrenom = ? ORDER BY `competence`.`nomCompetence` ASC";
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

	/* marche */
	public static void addParamEl1ToDB(@Valid ParamEl1 param) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			String insertQueryStatement = "INSERT INTO `paramel1`(`nom`, `nbEnonce`, `tempsApparution`, `nbApparition`, `multipleApparution`, `nbAparitionSimultanee`, `enonceDisparait`) VALUES (?,?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, param.nom);
			prepareStat.setInt(2, param.getNbEnonce());
			prepareStat.setLong(3, param.getTempsApparution());
			prepareStat.setInt(4, param.getNbApparution());
			prepareStat.setBoolean(5, param.getMultipleApparution());
			prepareStat.setInt(6, param.getNbAparitionSimultanee());
			prepareStat.setBoolean(7, param.getEnonceDisparait());

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

	/* marche */
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
			prepareStat.setInt(9, param.getValMax());
			prepareStat.setBoolean(10, param.getFrise());
			prepareStat.setLong(11, param.getTempsRep());
			prepareStat.setBoolean(12, param.getPairOnly());
			prepareStat.setBoolean(13, param.getOperateur()[0]);
			prepareStat.setBoolean(14, param.getOperateur()[1]);
			prepareStat.setBoolean(15, param.getOperateur()[2]);
			prepareStat.setBoolean(16, param.getOperateur()[3]);
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

	/* marche */
	public static void addParamEm2ToDB(@Valid ParamEm2 param) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			String insertQueryStatement = "INSERT INTO `paramem2`(`nom`, `typeRep`, `nbCalcul`, `valMaxOperande`, `nombrePair`, `nombreImpair`, `typeNombre`, `repDeuxBornes`, `repQuatreBornes`, `repPaveNum`, `tempsRep`, `pairOnly`, `operateur1`, `operateur2`, `operateur3`, `operateur4`, `calcChaine`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, param.nom);
			prepareStat.setInt(2, param.gettypeRep());
			prepareStat.setInt(3, param.getNbCalcul());
			prepareStat.setInt(4, param.getValMaxOperande());
			prepareStat.setBoolean(5, param.getNombrePair());
			prepareStat.setBoolean(6, param.getNombreImpair());
			prepareStat.setInt(7, param.getTypeNombre());
			prepareStat.setBoolean(8, param.getRepDeuxBornes());
			prepareStat.setBoolean(9, param.getRepQuatreBornes());
			prepareStat.setBoolean(10, param.getRepPaveNum());
			prepareStat.setLong(11, param.getTempsRep());
			prepareStat.setBoolean(12, param.getPairOnly());
			prepareStat.setBoolean(13, param.getOperateur()[0]);
			prepareStat.setBoolean(14, param.getOperateur()[1]);
			prepareStat.setBoolean(15, param.getOperateur()[2]);
			prepareStat.setBoolean(16, param.getOperateur()[3]);
			prepareStat.setBoolean(17, param.getCalcChaine());
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

	/* marche */
	public static ArrayList<String> getEnonceFromBD(@NotNull String mot) {
		ArrayList<String> el = new ArrayList<>();
		el.add(mot);
		el.add(getMotCommençantParLesQuatresPremieresLettresDe(mot));
		el.add(getMotFinissantParLesQuatresPremieresLettresDe(mot));
		el.add(getMotCommancantEtFinnissantComme(mot));
		el.add(getMotDeMemeLongueurQue(mot));
		log(el.toString());

		return el;

	}

	/* marche */
	private static String getMotCommancantEtFinnissantComme(@NotNull String mot) {
		String el = "";
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT mot FROM mots WHERE mot LIKE ? && mot !=? && LENGTH(mot)<? LIMIT 1";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, mot.substring(0, 2) + "%" + mot.substring(mot.length() - 2, mot.length()));
			prepareStat.setString(2, mot);
			prepareStat.setInt(3, mot.length() + 3);
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getString("mot");
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

	/* marche */
	public static String getMotCommençantParLesQuatresPremieresLettresDe(@NotNull String mot) {
		String el = "";
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT mot FROM mots WHERE mot LIKE ? && mot != ? && LENGTH(mot)<? LIMIT 1";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, mot.substring(0, 4) + "%");
			prepareStat.setString(2, mot);
			prepareStat.setInt(3, mot.length() + 3);
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getString("mot");
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

	/* marche */
	public static String getMotFinissantParLesQuatresPremieresLettresDe(@NotNull String mot) {
		String el = "";
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT mot FROM mots WHERE mot LIKE ? && mot !=? && LENGTH(mot)<? LIMIT 1";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, "%" + mot.substring(mot.length() - 4, mot.length()));
			prepareStat.setString(2, mot);
			prepareStat.setInt(3, mot.length() + 3);
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getString("mot");
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

	/* marche */
	private static String getMotDeMemeLongueurQue(@NotNull String mot) {
		String el = "";
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT mot FROM mots WHERE mot LIKE ? && mot !=?  && LENGTH(mot)=? LIMIT 1";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, mot.substring(0, 2) + "%" + mot.substring(mot.length() - 1, mot.length()));
			prepareStat.setString(2, mot);
			prepareStat.setInt(3, mot.length());
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getString("mot");
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

	/* marche */
	public static boolean verifieSiEleveADesParametres(int id) {
		Boolean haveParam = false;

		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT COUNT(*) FROM parameleve WHERE idEleve = ?";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1, id);
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				if (rs.getInt("COUNT(*)") > 0) {
					haveParam = true;
				}

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
		return haveParam;
	}

	/* marche */
	public static void appliqueParamEm1ToEleve(@Valid String param, @NotNull String nomPrenom, @NotNull String nom) {
		int idEleve = getEleveFromDB(nomPrenom, nom).get(0).getIdEleve();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		if (verifieSiEleveADesParametres(idEleve)) {
			try {
				conn = makeJDBCConnection();
				// MySQL Select Query Tutorial
				String getQueryStatement = "UPDATE parameleve SET `idParamEm1`=(SELECT idParamEm1 FROM paramem1 WHERE nom= ?) WHERE idEleve = ?";
				prepareStat = conn.prepareStatement(getQueryStatement);
				prepareStat.setString(1, param);
				prepareStat.setInt(2, idEleve);

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
		} else {
			try {
				conn = makeJDBCConnection();

				String insertQueryStatement = "INSERT INTO `parameleve`(`idEleve`, `idParamEm1`) VALUES (?,(SELECT idParamEm1 FROM paramem1 WHERE nom= ?) )";

				prepareStat = conn.prepareStatement(insertQueryStatement);
				prepareStat.setInt(1, idEleve);
				prepareStat.setString(2, param);

				// execute insert SQL statement
				prepareStat.executeUpdate();
				log(param + " added successfully");
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
	}

	/* marche */
	public static void appliqueParamEm2ToEleve(@Valid String param, @NotNull String nomPrenom, @NotNull String nom) {
		int idEleve = getEleveFromDB(nomPrenom, nom).get(0).getIdEleve();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		if (verifieSiEleveADesParametres(idEleve)) {
			try {
				conn = makeJDBCConnection();
				// MySQL Select Query Tutorial
				String getQueryStatement = "UPDATE parameleve SET `idParamEm2`=(SELECT idParamEm2 FROM paramem2 WHERE nom= ?) WHERE idEleve = ?";
				prepareStat = conn.prepareStatement(getQueryStatement);
				prepareStat.setString(1, param);
				prepareStat.setInt(2, idEleve);

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
		} else {
			try {
				conn = makeJDBCConnection();

				String insertQueryStatement = "INSERT INTO `parameleve`(`idEleve`, `idParamEm2`) VALUES (?,(SELECT idParamEm2 FROM paramem2 WHERE nom= ?) )";

				prepareStat = conn.prepareStatement(insertQueryStatement);
				prepareStat.setInt(1, idEleve);
				prepareStat.setString(2, param);

				// execute insert SQL statement
				prepareStat.executeUpdate();
				log(param + " added successfully");
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
	}

	/* marche */
	public static void appliqueParamEl1ToEleve(@Valid String param, @NotNull String nomPrenom, @NotNull String nom) {
		int idEleve = getEleveFromDB(nomPrenom, nom).get(0).getIdEleve();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		if (verifieSiEleveADesParametres(idEleve)) {
			try {
				conn = makeJDBCConnection();
				// MySQL Select Query Tutorial
				String getQueryStatement = "UPDATE parameleve SET `idParamEl1`=(SELECT idParamEl1 FROM paramel1 WHERE nom= ?) WHERE idEleve = ?";
				prepareStat = conn.prepareStatement(getQueryStatement);
				prepareStat.setString(1, param);
				prepareStat.setInt(2, idEleve);

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
		} else {
			try {
				conn = makeJDBCConnection();

				String insertQueryStatement = "INSERT INTO parameleve (`idEleve`, `idParamEl1`) VALUES (?,(SELECT idParamEl1 FROM paramel1 WHERE nom= ?) )";

				prepareStat = conn.prepareStatement(insertQueryStatement);
				prepareStat.setInt(1, idEleve);
				prepareStat.setString(2, param);

				// execute insert SQL statement
				prepareStat.executeUpdate();
				log(param + " added successfully");
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
	}

	/* marche */
	public static ArrayList<Eleve> getElevesAvecParamEm1FromDB(@NotNull String nomparam) {
		ArrayList<Eleve> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN eleve NATURAL JOIN classe WHERE idParamEm1=(SELECT idParamEm1 FROM paramem1 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomparam);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Eleve ele = new Eleve(rs.getString("nomPrenom"), rs.getDate("dateDeNaissance"),
						rs.getString("nomClasse"), rs.getInt("classeId"), rs.getInt("idEleve"));
				el.add(ele);
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
		return el;
	}

	/* marche */
	public static ArrayList<Eleve> getElevesAvecParamEm2FromDB(@NotNull String nomparam) {
		ArrayList<Eleve> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN eleve NATURAL JOIN classe WHERE idParamEm2=(SELECT idParamEm2 FROM paramem2 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomparam);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Eleve ele = new Eleve(rs.getString("nomPrenom"), rs.getDate("dateDeNaissance"),
						rs.getString("nomClasse"), rs.getInt("classeId"), rs.getInt("idEleve"));
				el.add(ele);
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
		return el;
	}

	/* marche */
	public static ArrayList<Eleve> getElevesAvecParamEl1FromDB(@NotNull String nomparam) {
		ArrayList<Eleve> el = new ArrayList<>();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN eleve NATURAL JOIN classe WHERE idParamEl1=(SELECT idParamEl1 FROM paramel1 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nomparam);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Eleve ele = new Eleve(rs.getString("nomPrenom"), rs.getDate("dateDeNaissance"),
						rs.getString("nomClasse"), rs.getInt("classeId"), rs.getInt("idEleve"));
				el.add(ele);
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
		return el;
	}

	/* marche */
	public static void desappliqueParamEm1FromDB(@NotNull String nom) {

		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "UPDATE parameleve SET `idParamEm1`=NULL WHERE idParamEm1=(SELECT idParamEm1 FROM paramem1 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);

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
	public static void desappliqueParamEm2FromDB(@NotNull String nom) {

		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "UPDATE parameleve SET `idParamEm2`=NULL WHERE idParamEm2=(SELECT idParamEm2 FROM paramem2 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);

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
	public static void desappliqueParamEl1FromDB(@NotNull String nom) {

		Connection conn = null;
		PreparedStatement prepareStat = null;
		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "UPDATE parameleve SET `idParamEl1`=NULL WHERE idParamEl1=(SELECT idParamEl1 FROM paramel1 WHERE nom= ?)";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);

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
	public static void supprParamEl1fromBD(@NotNull String nom) {
		desappliqueParamEl1FromDB(nom);
		desappliqueEnonceToParamEl1(nom);
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "DELETE FROM paramel1 WHERE nom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.execute();
			log(nom + " a bien été supprimer");
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
	}

	/* marche */
	public static void supprParamEm1fromBD(@NotNull String nom) {
		desappliqueParamEm1FromDB(nom);
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "DELETE FROM paramem1 WHERE nom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.execute();
			log(nom + " a bien été supprimer");
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
	}

	/* marche */
	public static void supprParamEm2fromBD(@NotNull String nom) {
		desappliqueParamEm2FromDB(nom);
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "DELETE FROM paramem2 WHERE nom = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, nom);
			prepareStat.execute();
			log(nom + " a bien été supprimer");
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

	}

	/* a tester */
	public static ParamEm2 getEleveParamEm2FromBD(@NotNull int id) {
		ParamEm2 el = null;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN paramem2 WHERE idEleve = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1, id);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				el = new ParamEm2(rs.getString("nom"), rs.getLong("tempsRep"), rs.getBoolean("pairOnly"), tabOpp,
						rs.getInt("typeRep"), rs.getInt("nbCalcul"), rs.getInt("valMaxOperande"),
						rs.getBoolean("nombreImpair"), rs.getBoolean("nombrePair"), rs.getBoolean("repDeuxBornes"),
						rs.getBoolean("repPaveNum"), rs.getBoolean("repQuatreBornes"), rs.getBoolean("calcChaine"));
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
	public static ParamEm1 getEleveParamEm1FromBD(@NotNull int id) {
		ParamEm1 el = new ParamEm1();
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN paramem1 WHERE idEleve = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1, id);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				Boolean[] tabOpp = { rs.getBoolean("operateur1"), rs.getBoolean("operateur2"),
						rs.getBoolean("operateur3"), rs.getBoolean("operateur4") };
				el = new ParamEm1(rs.getString("nom"), rs.getBoolean("frise"), rs.getLong("tempsRep"),
						rs.getBoolean("pairOnly"), tabOpp, rs.getInt("nbBornes"), rs.getInt("nbQuestions"),
						rs.getBoolean("disparition"), rs.getLong("tempsRestantApparant"),
						rs.getBoolean("ordreApparition"), rs.getBoolean("borneSelectionnable"),
						rs.getBoolean("borneEqualsOp"), rs.getInt("valMax"));
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

	/* a tester */
	public static ParamEl1 getEleveParamEl1FromBD(@NotNull int id) {
		ParamEl1 el = null;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM parameleve NATURAL JOIN paramel1 WHERE idEleve = ?";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1, id);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				el = new ParamEl1(rs.getString("nom"), rs.getInt("nbEnonce"), rs.getLong("tempsApparution"),
						rs.getInt("nbApparition"), rs.getBoolean("multipleApparution"),
						rs.getBoolean("enonceDisparait"), rs.getInt("nbAparitionSimultanee"));
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

	public static void appliqueEnonceToParamEl1(@NotNull String nomParam, @Valid Enonce enonce) {
		if(!verifIfEnonceExiste(enonce)) {
			addEnonceToDB(enonce);
		}
		
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			if (enonce.getMot3() == null) {
				String insertQueryStatement = "INSERT INTO `enonceparam`(`idParamEl1`, `idEnonce`) VALUES ((SELECT idParamEl1 FROM paramel1 WHERE nom = ? ),(SELECT `idEnonce` FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 IS NULL && mot4 IS NULL && mot5 IS NULL && mot6 IS NULL LIMIT 1))";

				prepareStat = conn.prepareStatement(insertQueryStatement);
				prepareStat.setString(1, nomParam);
				prepareStat.setString(2, enonce.getMot1());
				prepareStat.setString(3, enonce.getMot2());
			}

			else {
				if (enonce.getMot4() == null) {
					String insertQueryStatement = "INSERT INTO `enonceparam`(`idParamEl1`, `idEnonce`) VALUES ((SELECT idParamEl1 FROM paramel1 WHERE nom = ? ),(SELECT `idEnonce` FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 IS NULL && mot5 IS NULL && mot6 IS NULL LIMIT 1))";

					prepareStat = conn.prepareStatement(insertQueryStatement);
					prepareStat.setString(1, nomParam);
					prepareStat.setString(2, enonce.getMot1());
					prepareStat.setString(3, enonce.getMot2());
					prepareStat.setString(4, enonce.getMot3());
				} else {
					if (enonce.getMot5() == null) {
						String insertQueryStatement = "INSERT INTO `enonceparam`(`idParamEl1`, `idEnonce`) VALUES ((SELECT idParamEl1 FROM paramel1 WHERE nom = ? ),(SELECT `idEnonce` FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 IS NULL && mot6 IS NULL LIMIT 1))";

						prepareStat = conn.prepareStatement(insertQueryStatement);
						prepareStat.setString(1, nomParam);
						prepareStat.setString(2, enonce.getMot1());
						prepareStat.setString(3, enonce.getMot2());
						prepareStat.setString(4, enonce.getMot3());
						prepareStat.setString(5, enonce.getMot4());
					} else {
						if (enonce.getMot6() == null) {
							String insertQueryStatement = "INSERT INTO `enonceparam`(`idParamEl1`, `idEnonce`) VALUES ((SELECT idParamEl1 FROM paramel1 WHERE nom = ? ),(SELECT `idEnonce` FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 = ? && mot6 IS NULL LIMIT 1))";

							prepareStat = conn.prepareStatement(insertQueryStatement);
							prepareStat.setString(1, nomParam);
							prepareStat.setString(2, enonce.getMot1());
							prepareStat.setString(3, enonce.getMot2());
							prepareStat.setString(4, enonce.getMot3());
							prepareStat.setString(5, enonce.getMot4());
							prepareStat.setString(6, enonce.getMot5());
						} else {
							String insertQueryStatement = "INSERT INTO `enonceparam`(`idParamEl1`, `idEnonce`) VALUES ((SELECT idParamEl1 FROM paramel1 WHERE nom = ? ),(SELECT `idEnonce` FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 = ? && mot6 = ? LIMIT 1))";

							prepareStat = conn.prepareStatement(insertQueryStatement);
							prepareStat.setString(1, nomParam);
							prepareStat.setString(2, enonce.getMot1());
							prepareStat.setString(3, enonce.getMot2());
							prepareStat.setString(4, enonce.getMot3());
							prepareStat.setString(5, enonce.getMot4());
							prepareStat.setString(6, enonce.getMot5());
							prepareStat.setString(7, enonce.getMot6());

						}
					}
				}
			}
			log(prepareStat.toString());
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log("new enonce apply successfully");
		} catch (SQLException r) {
			r.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
		}

	}

	public static boolean verifIfEnonceExiste(Enonce enonce) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		boolean res = false;
		log("dans verif if existe");
		try {
			conn = makeJDBCConnection();
			if (enonce.getMot3() == null) {
				String insertQueryStatement = "SELECT COUNT(*) FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 IS NULL && mot4 IS NULL && mot5 IS NULL && mot6 IS NULL ";

				prepareStat = conn.prepareStatement(insertQueryStatement);
				prepareStat.setString(1, enonce.getMot1());
				prepareStat.setString(2, enonce.getMot2());
			}

			else {
				if (enonce.getMot4() == null) {
					String insertQueryStatement = "SELECT COUNT(*)  FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 IS NULL && mot5 IS NULL && mot6 IS NULL ";

					prepareStat = conn.prepareStatement(insertQueryStatement);

					prepareStat.setString(1, enonce.getMot1());
					prepareStat.setString(2, enonce.getMot2());
					prepareStat.setString(3, enonce.getMot3());
				} else {
					if (enonce.getMot5() == null) {
						String insertQueryStatement = "SELECT COUNT(*)  FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 IS NULL && mot6 IS NULL";

						prepareStat = conn.prepareStatement(insertQueryStatement);
						prepareStat.setString(1, enonce.getMot1());
						prepareStat.setString(2, enonce.getMot2());
						prepareStat.setString(3, enonce.getMot3());
						prepareStat.setString(4, enonce.getMot4());
					} else {
						if (enonce.getMot6() == null) {
							String insertQueryStatement = "SELECT COUNT(*)  FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 = ? && mot6 IS NULL";

							prepareStat = conn.prepareStatement(insertQueryStatement);
							prepareStat.setString(1, enonce.getMot1());
							prepareStat.setString(2, enonce.getMot2());
							prepareStat.setString(3, enonce.getMot3());
							prepareStat.setString(4, enonce.getMot4());
							prepareStat.setString(5, enonce.getMot5());
						} else {
							String insertQueryStatement = "SELECT COUNT(*) FROM `enonce` WHERE mot1 = ? && mot2 = ? && mot3 = ? && mot4 = ? && mot5 = ? && mot6 = ?";

							prepareStat = conn.prepareStatement(insertQueryStatement);
							prepareStat.setString(1, enonce.getMot1());
							prepareStat.setString(2, enonce.getMot2());
							prepareStat.setString(3, enonce.getMot3());
							prepareStat.setString(4, enonce.getMot4());
							prepareStat.setString(5, enonce.getMot5());
							prepareStat.setString(6, enonce.getMot6());

						}
					}
				}
			}
			log(prepareStat.toString());
			// execute insert SQL statement
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				if (rs.getInt("COUNT(*)") > 0) {
					log(enonce.getMot1()+"existe dèjà");
					res = true;
				}

			}
		} catch (SQLException r) {
			r.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
		}
		return res;
	}

	public static boolean verifIfMotIsInDB(String mot) {
		Boolean haveParam = false;

		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT COUNT(*) FROM mots WHERE mot = ?";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setString(1, mot);
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				if (rs.getInt("COUNT(*)") > 0) {
					haveParam = true;
				}

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
		return haveParam;
	}
	
	public static void ajouteMotToDB(String mot) {
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT  INTO  mots (mot) VALUES  (?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, mot);

			// execute insert SQL statement
			prepareStat.executeUpdate();
			log(mot + " added successfully");
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
	
	public static void addEnonceToDB(Enonce e) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		if(!verifIfMotIsInDB(e.getMot1())) {
			ajouteMotToDB(e.getMot1());
		}
		
		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `enonce`(`mot1`, `mot2`, `mot3`, `mot4`, `mot5`, `mot6`) VALUES (?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, e.getMot1());
			prepareStat.setString(2, e.getMot2());
			prepareStat.setString(3, e.getMot3());
			prepareStat.setString(4, e.getMot4());
			prepareStat.setString(5, e.getMot5());
			prepareStat.setString(6, e.getMot6());
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log("new enonce added successfully");
		} catch (SQLException r) {
			r.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
		}
	}

	public static void desappliqueEnonceToParamEl1(@NotNull String nomParam) {
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "DELETE FROM `enonceparam` WHERE (idParamEl1=((SELECT idParamEl1 FROM paramel1 WHERE nom = ? )))";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, nomParam);

			log(prepareStat.toString());
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log("enonce disapply successfully");
		} catch (SQLException r) {
			r.printStackTrace();
		} finally {
			if (prepareStat != null) {
				try {
					prepareStat.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException r) {
					// TODO Auto-generated catch block
					r.printStackTrace();
				}
			}
		}

	}

	public static ArrayList<Exo1Math> getHistoriqueEm1EleveFromBD(@NotNull String nomprenom, @NotNull String nom) {
		/*Exo1Math el = null;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM `exo2math` NATURAL JOIN paramem2 NATURAL JOIN eleve NATURAL JOIN classe WHERE idParamEm2=6 && idEleve=58";
			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1, id);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				el = new Exo1Math(rs.getString("nom"), rs.getInt("nbEnonce"), rs.getLong("tempsApparution"),
						rs.getInt("nbApparition"), rs.getBoolean("multipleApparution"),
						rs.getBoolean("enonceDisparait"), rs.getInt("nbAparitionSimultanee"));
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
*/
		return null;

	}

	public static ArrayList<Exo2Math> getHistoriqueEm2EleveFromBD(@NotNull String nomprenom, @NotNull String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Exo1Lecture> getHistoriqueEl1EleveFromBD(@NotNull String nomprenom, @NotNull String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void addEm1ToDB(@NotNull int idParam, @NotNull int idEleve, @NotNull String res, @NotNull Timestamp date, @Valid Calcul[] c) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		
		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `exo1math`(`idParamEm1`, `idEleve`, `score`, `date`) VALUES (?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setInt(1, idParam);
			prepareStat.setInt(2, idEleve);
			prepareStat.setString(3, res);
			prepareStat.setTimestamp(4, date);
			prepareStat.executeUpdate();
			log("Em1 added successfully");
			for (Calcul calcul : c) {
				calcul.setIdExo1Math(getIdEm1FromDB(idEleve,date));
				addCalcul(calcul);
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
		
	}

	private static int getIdEm1FromDB(@NotNull int idEleve, @NotNull Timestamp date) {
		int el=0;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT `idExo1Math` FROM `exo1math` WHERE `idEleve`= ? &&`date`= ? LIMIT 1";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1,idEleve);
			prepareStat.setTimestamp(2, date);
			
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getInt("idExo1Math");
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
	
	private static int getIdEm2FromDB(@NotNull int idEleve, @NotNull Timestamp date) {
		int el=0;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT `idExo2Math` FROM `exo2math` WHERE `idEleve`= ? &&`date`= ? LIMIT 1";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1,idEleve);
			prepareStat.setTimestamp(2, date);
			
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getInt("idExo2Math");
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

	public static void addEm2ToDB(@NotNull int idParam, @NotNull int idEleve, @NotNull String res, @NotNull Timestamp date, @Valid Calcul[] c) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		
		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `exo2math`(`idParamEm2`, `idEleve`, `score`, `date`) VALUES (?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setInt(1, idParam);
			prepareStat.setInt(2, idEleve);
			prepareStat.setString(3, res);
			prepareStat.setTimestamp(4, date);
			prepareStat.executeUpdate();
			log("Em2 added successfully");
			for (Calcul calcul : c) {
				calcul.setIdExo1Math(getIdEm2FromDB(idEleve,date));
				addCalcul(calcul);
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
	}

	public static void addEl1ToDB(@NotNull int idParam, @NotNull int idEleve, @NotNull int idEnonce, @NotNull String res, @NotNull Timestamp date) {
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `exo1lecture`(`idParamEl1`, `idEleve`, `idEnonce`, `resultat`, `date`) VALUES (?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setInt(1, idParam);
			prepareStat.setInt(2, idEleve);
			prepareStat.setInt(3, idEnonce);
			prepareStat.setString(4, res);
			prepareStat.setTimestamp(5, date);
			prepareStat.executeUpdate();
			log("El1 added successfully");
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
	
	public static int getIdBorne(int number) {
		int el=0;
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT `idBorne` FROM `borne` WHERE `nombre`= ? LIMIT 1";

			prepareStat = conn.prepareStatement(getQueryStatement);
			prepareStat.setInt(1,number);			
			log(prepareStat.toString());
			// Execute the Query, and get a java ResultSet
			ResultSet rs = prepareStat.executeQuery();
			while (rs.next()) {
				el = rs.getInt("idExo1Math");
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
	
	public static void addCalcul(Calcul c) {
		Connection conn = null;
		PreparedStatement prepareStat = null;
		
		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `calcul`(`operation`, `reponseEleve`, `reponseJuste`, `idExo1Math`, `idExo2Math`, `idBorne1`, `idBorne2`, `idBorne3`) VALUES (?,?,?,?,?,?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setString(1, c.getOperation());
			prepareStat.setString(2, c.getReponseEleve());
			prepareStat.setString(3, c.getReponseJuste());
			prepareStat.setInt(4, c.getIdExo1Math());
			prepareStat.setInt(5, c.getIdExo2Math());
			prepareStat.setInt(6, getIdBorne(c.getBornes()[1].getNombre()));
			prepareStat.setInt(7, getIdBorne(c.getBornes()[2].getNombre()));
			prepareStat.setInt(8, getIdBorne(c.getBornes()[3].getNombre()));
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log("enonce added successfully");
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
	
	public static void addBorne(int idEx1Math,int nombre,int idCalcul) {
		Connection conn = null;
		PreparedStatement prepareStat = null;

		try {
			conn = makeJDBCConnection();

			String insertQueryStatement = "INSERT INTO `borne`( `nombre`, `idExo1Math`, `idCalcul`) VALUES (?,?,?)";

			prepareStat = conn.prepareStatement(insertQueryStatement);
			prepareStat.setInt(1, nombre);
			prepareStat.setInt(2, idEx1Math);
			prepareStat.setInt(3, idCalcul);
			// execute insert SQL statement
			prepareStat.executeUpdate();
			log("enonce added successfully");
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
	
	// Simple log utility
	private static void log(String string) {
		System.out.println(string);
	}


}
