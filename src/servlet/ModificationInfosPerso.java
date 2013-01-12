package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banque.model.dao.ClientDAO;
import banque.model.entites.client.Client;

/**
 * Permet a l'utilisateur de changer son adresse.
 * @author camillemuller & Raillet sebastien.
 *
 */
@WebServlet("/ModificationInfosPerso")
public class ModificationInfosPerso extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	private ClientDAO sonClientDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationInfosPerso() {
        super();
        this.sonClientDAO = new ClientDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Client unCli = (Client)	request.getSession().getAttribute("client");
		request.setAttribute("nom_client", unCli.getIdentite().getNom());
		request.setAttribute("prenom_client", unCli.getIdentite().getPrenom());
		request.setAttribute("adresse", unCli.getAdresse());
		request.getRequestDispatcher("/WEB-INF/ModificationInfosPerso.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		Client unCli = (Client)	request.getSession().getAttribute("client");

		//Récupération de la valeur adresse
		String uneAdr = (String) request.getParameter("adresse");

		//On verfie que l'adresse n'est plus la meme.
		if(!unCli.getAdresse().equals(uneAdr) )
				{
			System.out.print(uneAdr);
				unCli.setAdresse(uneAdr);
				this.sonClientDAO.sauvegarder(unCli);
		//MAJ
		request.setAttribute("nom_client", unCli.getIdentite().getNom());
		request.setAttribute("prenom_client", unCli.getIdentite().getPrenom());
		request.setAttribute("adresse", unCli.getAdresse());
		request.getRequestDispatcher("/WEB-INF/ModificationAccepte.jsp").forward(request, response);

				}else
				{  // retour sur la page si rien n'a changer.
					this.doGet(request, response);
				}

	}
	

}
