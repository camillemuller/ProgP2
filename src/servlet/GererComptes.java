package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banque.model.dao.CompteDAO;
import banque.model.dao.EcritureDAO;
import banque.model.entites.client.Client;
import banque.model.entites.compte.Compte;

/**
 * Permet d'afficher les comptes et leur solde associées.
 * @author camillemuller & Raillet Sebastien.
 *
 */
@WebServlet("/GererComptes")
public class GererComptes extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CompteDAO sonCompteDAO;
    private EcritureDAO sonEcriDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GererComptes() {
        super();
        this.sonEcriDAO = new EcritureDAO();
        this.sonCompteDAO= new CompteDAO();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uneGdeT= "";
		Client unCli = (Client)	request.getSession().getAttribute("client");
		sonCompteDAO.recupererCompte(unCli);

		for(Compte unCompte :  unCli.getComptes())
		{
			// Premiere execution du dao
			if(unCompte.getEcritures().size()==0)
			sonEcriDAO.chargerEcriture(unCompte);

			
			uneGdeT+="<tr>";
			uneGdeT+="<th>"+"<a href=\"AfficherCompte?numero="+unCompte.getNumeroCompte()+"\">"+unCompte.getNumeroCompte()+"</a>"+"</th>";
			
			if(unCompte.getSolde()>=0) // Si il est positif on le mets sur la 3eme colonnes.
			{
				uneGdeT+="<th>"+"</th>";
				uneGdeT+="<th>"+unCompte.getSolde()+"</th>";
			}
			else // si il est négatif alors on mets le solde sur la 2eme colones.
			{
				uneGdeT+="<th>"+unCompte.getSolde()+"</th>";
				uneGdeT+="<th>"+"</th>";
			}
			
			uneGdeT+="</tr>";
		}
		
		request.setAttribute("tableau", uneGdeT);
		request.getRequestDispatcher("/WEB-INF/AccueilComptes.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
