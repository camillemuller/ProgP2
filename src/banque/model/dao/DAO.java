package banque.model.dao;
import java.sql.*;
import javax.naming.*;
import javax.sql.DataSource;


//import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * G�re la configuration de la BDD
 * @author Muller Camille,Raillet Sebastien
 *
 */
public class DAO {


	DataSource ds;

	public DAO() {

		// Initialisation de la pool
		try {
			Class.forName("com.mysql.jdbc.Driver");
			initProperties();	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					


	}
	/**
	 * Cr�er une connection la BDD,
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {

		return ds.getConnection();

	}

	
	
	
	
	
	
	
	
	/**
	 *  configuration de la pool
	 */
	private void initProperties() {



		try {		
			Context initCtx;
			initCtx = new InitialContext();
			// On va cherch� dans le fichier de config la configuration de la pool
			ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/TestDB");		
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}