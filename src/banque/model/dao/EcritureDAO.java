package banque.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import banque.exceptions.EcritureRefuseeException;
import banque.model.entites.compte.Compte;

import banque.model.entites.compte.Ecriture;

/**
 * Permet de gérer les opérations des clients
 * @author camillemuller
 *
 */
public class EcritureDAO extends DAO {
	
	public EcritureDAO()
	{
		
	}

	/**
	 * Charges les écritures d'un clients a partir de la BDD
	 * @param unCompte
	 */
	public void chargerEcriture(Compte unCompte)
	{
		
	
	try
	{	
		
		Connection con = this.getConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT ID_ECRITURE,DATE_ECRITURE,LIBELLE,MONTANT,ID_COMPTE from ECRITURE where ID_COMPTE="+unCompte.getId());			
		
		
		while (rs.next()) {
			
			//public Ecriture(int id, java.util.Date date, String intitule, float montant)
			unCompte.addEcriture(new Ecriture(rs.getInt("ID_ECRITURE"),rs.getDate("DATE_ECRITURE"),rs.getString("LIBELLE"),rs.getFloat("MONTANT")));
		}
		rs.close();
		st.close();
		con.close();
		
	}catch(SQLException e)
	{
		System.out.println("Erreur SQL : " + e.getMessage());
		e.printStackTrace();
		System.exit(1);
	} catch (EcritureRefuseeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
		
	/**
	 * Enregistre une ecriture d'un client sur la BDD
	 * @param uneEcriture
	 * @param unCompte
	 */
	public void sauvegarder(Ecriture uneEcriture,Compte unCompte)
	{
		
		try
		{	
		
			
			Connection con = this.getConnection();
			
				Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT ID_ECRITURE,DATE_ECRITURE,LIBELLE,MONTANT,ID_COMPTE from ECRITURE");			
			rs.last();
			
			PreparedStatement stprmd = 
			con.prepareStatement("insert into ecriture(ID_ECRITURE,DATE_LIBELLE,LIBELLE,MONTANT,ID_COMPTE)" +
								 "values(?,?,?,?,?)");
			
			stprmd.setInt(1, rs.getRow()+1 );
			stprmd.setDate(2, new java.sql.Date(uneEcriture.getDate().getTime()) );
			stprmd.setString(3, uneEcriture.getIntitule());
			stprmd.setFloat(4, uneEcriture.getMontant());
			stprmd.setInt(5, unCompte.getId());
			
			stprmd.executeUpdate();
			
			stprmd.close();
			con.close();
			
		}catch(SQLException e)
		{
		
		}

	}
}
