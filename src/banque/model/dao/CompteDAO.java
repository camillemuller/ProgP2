package banque.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import banque.exceptions.CompteDejaExistantException;
import banque.exceptions.CompteInexistantException;
import banque.model.entites.client.Client;
import banque.model.entites.compte.CompteCheque;
import banque.model.entites.compte.CompteEpargne;

/**
 * Permet de gŽrer les comptes avec la BDD
 * @author camillemuller & Raillet sebastien
 *
 */
public class CompteDAO extends DAO {
	
	
	public CompteDAO()
	{
	super();	
	}
	/**
	 * RŽcupere les comptes du client sur la BDD
	 * @param unClient
	 */
	public void recupererCompte(Client unClient)
	{

		try{
			Connection con = this.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT id_compte,numero,solde,type,decouvert from COMPTE where id_particulier="+unClient.getId());			

			while (rs.next()) {
				if(rs.getString("type")=="CHEQUE")
				{
					unClient.ajouterCompte(new CompteEpargne(rs.getInt("id_compte"),rs.getString("numero"),rs.getFloat("solde") ) );
				}
				else
				{
					unClient.ajouterCompte(new CompteCheque(rs.getInt("id_compte"),rs.getString("numero"),rs.getFloat("solde"),rs.getFloat("decouvert")));
				}
			
			}
			rs.close();
			st.close();
			con.close();
			
		}catch(SQLException e)
		{
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}catch(CompteDejaExistantException e)
		{
			//Debug
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Enregistre un compte epargne d'un clients sur la BDD
	 * @param unNumero
	 * @param unClient
	 * @throws CompteDejaExistantException
	 */
	public void sauvegarderCompteEpargne(String unNumero,Client unClient) throws CompteDejaExistantException
	{
		try {
			CompteEpargne leCompte =  (CompteEpargne) unClient.getCompte(unNumero);
			Connection con = this.getConnection();
			

			PreparedStatement st1 = con.prepareStatement("select numero from COMPTE where numero=?");
			st1.setString(1, unNumero);
			ResultSet leRst;
			
			leRst = st1.executeQuery();
			
			
			
			if(leRst.getRow()>0)
			{
				
				throw new CompteDejaExistantException();
			}
			else
			{
			
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT id_compte,numero,solde,type,decouvert from COMPTE");
				rs.last();
				
			PreparedStatement stprmd = 
			con.prepareStatement("insert into COMPTE(id_compte, numero, solde, type,id_particulier)" +
					             "values(?,?,?,?,?)");
			
			
			
			stprmd.setInt(1, rs.getRow()+1);
			stprmd.setString(2, leCompte.getNumeroCompte());
			stprmd.setFloat(3, leCompte.getSolde());
			stprmd.setString(4, "EPARGNE");
			stprmd.setInt(5, unClient.getId());
			
			stprmd.executeUpdate();
			
			
			stprmd.close();
			con.close();
			}
			
		} catch (CompteInexistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException sqle)
		{
			System.out.println("Erreur SQL : " + sqle.getMessage());
			sqle.printStackTrace();
			System.exit(1);
		}
		
	}

	/**
	 * Enregistre un compte cheque sur la BDD d'un client
	 * @param unNumero
	 * @param unClient
	 * @throws CompteDejaExistantException
	 */
	public void sauvegarderCompteCheque(String unNumero,Client unClient) throws CompteDejaExistantException
	{
		try {
			CompteCheque leCompte =  (CompteCheque) unClient.getCompte(unNumero);
			Connection con = this.getConnection();
			
			PreparedStatement st1 = con.prepareStatement("select numero from COMPTE where numero=?");
			st1.setString(1, unNumero);
			ResultSet leRst;
			
			leRst = st1.executeQuery();
			
			
			
			if(leRst.getRow()>0)
			{
				throw new CompteDejaExistantException();
			}
			else
			{
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT id_compte,numero,solde,type,decouvert from COMPTE");
				rs.last();
				
				PreparedStatement stprmd = 
			con.prepareStatement("insert into COMPTE(id_compte, numero, solde, decouvert, type,id_particulier)" +
					             "values(?,?,?,?,?,?)");
			
			
			
			stprmd.setInt(1, rs.getRow()+1);
			stprmd.setString(2, leCompte.getNumeroCompte());
			stprmd.setFloat(3, leCompte.getSolde());
			stprmd.setFloat(4, leCompte.getAutorisationDecouvert());
			stprmd.setString(5, "EPARGNE");
			stprmd.setInt(6, unClient.getId());
			stprmd.executeUpdate();
			
			
			stprmd.close();
			con.close();
			}
			
			
			
			
			
		} catch (CompteInexistantException e) {
			e.printStackTrace();
		}catch(SQLException sqle)
		{
			System.out.println("Erreur SQL : " + sqle.getMessage());
			sqle.printStackTrace();
			System.exit(1);
			
		}
		
	}
	
	}
