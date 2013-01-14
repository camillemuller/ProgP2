package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banque.exceptions.CompteInexistantException;
import banque.model.entites.client.Client;
import banque.model.entites.compte.Compte;
import banque.model.entites.compte.Ecriture;

/**
 * 
 *   Permet d'afficher un compte de l'utilisateur et ses ecritures associées.
 * @author camillemuller & Raillet Sebastien 
 * 
 */
@WebServlet("/AfficherCompte")
public class AfficherCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
		
		try {
			String uneGdeT= "";
			Client unCli = (Client)	request.getSession().getAttribute("client");
			Compte unCompte = unCli.getCompte(request.getParameter("numero"));
			
			// Affichage euros 
			request.setAttribute("euros", unCompte.getSolde());

			
			// On parcourt les écritures pour fabriqué le tableau
			for(Ecriture uneEcriture :  unCompte.getEcritures())
			{
				uneGdeT+="<tr>";

				uneGdeT+="<th>"+uneEcriture.getDate()+"</a>"+"</th>";
				
				uneGdeT+="<th>"+uneEcriture.getIntitule()+"</th>";
				
				if(uneEcriture.getMontant()>=0) // Si il est positif on le mets sur la 3eme colonnes.
				{
					uneGdeT+="<th>"+"</th>";
					uneGdeT+="<th>"+uneEcriture.getMontant()+"</th>";
				}
				else // si il est négatif alors on mets le solde sur la 2eme colones.
				{
					uneGdeT+="<th>"+uneEcriture.getMontant()+"</th>";
					uneGdeT+="<th>"+"</th>";
				}

				uneGdeT+="</tr>";
			}

			request.setAttribute("tableau", uneGdeT);
			request.getRequestDispatcher("/WEB-INF/AffichageCompte.jsp").forward(request, response);	

		} catch (CompteInexistantException e) {
			// TODO Auto-generated catch block
			request.getRequestDispatcher("GererComptes").forward(request, response);	
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
