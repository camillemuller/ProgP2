package banque.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banque.exceptions.ClientInexistantException;
import banque.model.entites.client.Client;
import banque.model.entites.client.Particulier;

/**
 * Permet de gérer les clients avec la base SQL
 * @author camillemuller,Raillet sebastien
 *
 */
public class ClientDAO extends DAO {

	public ClientDAO() {
		super();
	}

	/**
	 * Cette fonction retourne un client particulier a partir de la base de données
	 * @param unNom 
	 * @param unPrenom
	 * @return Particulier retourne un client particulier.
	 * @throws ClientInexistantException si il n'y a pas de client, ou si le client c'est trompé
	 */
	public Particulier recupererClientsParticuliers(String unNom,String unPrenom) throws ClientInexistantException // -> On redirige la levée
	{

		Connection con;
		try {
			con = this.getConnection();

			PreparedStatement st = con.prepareStatement("SELECT ID_PARTICULIER, NOM, PRENOM, ADRESSE FROM PARTICULIER WHERE NOM=? AND PRENOM=?");
			st.setString(1, unNom);
			st.setString(2, unPrenom);

			ResultSet rs = st.executeQuery();
						
			if(!rs.first()) // Si la Requete renvoie un resultat vide,alors on leve une Exception
			{
				st.close(); // On referme la connection
				rs.close();
				con.close();
				throw new ClientInexistantException("Mauvais MDP,ou votre compte n'existe pas.");
			}
			else
			{
				
				Particulier leClient = new Particulier(rs.getString(2),rs.getString(3),rs.getString(4));
				st.close();
				rs.close();
				con.close();
				return leClient;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;



	}
	
	/**
	 * Permer de mettre a jour un clinet
	 * @param unClient
	 */
	public void sauvegarder(Client unClient)
			{
			Connection con;
			try {
				con = this.getConnection();
				
				
				PreparedStatement st = con.prepareStatement("update PARTICULIER set adresse = ?  where nom = ? and prenom = ? ");
				
				// On mets en forme la requete
				st.setString(1, unClient.getAdresse());
				st.setString(2, unClient.getIdentite().getNom());
				st.setString(3, unClient.getIdentite().getPrenom());
				// exec de la requete
				st.executeUpdate();
				
				st.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			}
}
