package servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banque.exceptions.ClientInexistantException;
import banque.model.dao.ClientDAO;
import banque.model.entites.client.Client;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Accueil")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	ClientDAO sonClientDAO;

	public Connexion() {
		super();
		this.sonClientDAO = new ClientDAO();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
		Client unCli = (Client)	request.getSession().getAttribute("client");
		request.setAttribute("nom_client", unCli.getIdentite().getNom());
		request.setAttribute("prenom_client", unCli.getIdentite().getPrenom());
		request.getRequestDispatcher("/WEB-INF/accueil.jsp").include(request, response);	
		}catch(NullPointerException e)
		{
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");

		// Si l'un des champs est vide
		if ("".equals(prenom) || "".equals(nom)) {
			request.setAttribute("erreur", "Vous devez remplir les deux champs.");
			// Redirection vers le formulaire form.jsp
			request.getRequestDispatcher("index.jsp").forward(request, response); 
		}
		// Sinon
		else {
			try {
				Client uncli = 	this.sonClientDAO.recupererClientsParticuliers(nom,prenom);
				request.setAttribute("nom_client", nom);
				request.setAttribute("prenom_client", prenom);
				request.setAttribute("adresse", uncli.getAdresse());

				// Debug
				request.getSession().setAttribute("client", uncli);
				request.getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
			} catch (ClientInexistantException e) {
				// TODO Auto-generated catch block
				request.setAttribute("erreur",e.getMessage());
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}    
		}
	}
}


