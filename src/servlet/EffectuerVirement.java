package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import banque.exceptions.CompteInexistantException;
import banque.exceptions.EcritureRefuseeException;
import banque.model.dao.EcritureDAO;
import banque.model.entites.client.Client;
import banque.model.entites.compte.Compte;
import banque.model.entites.compte.Ecriture;

/**
 * Permet d'effectuer un virement entre deux comptes.
 * @author camillemuller & Raillet Sebastien
 *
 */
@WebServlet("/EffectuerVirement")
public class EffectuerVirement extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private EcritureDAO sonEcriDAO;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EffectuerVirement() {
        super();
        this.sonEcriDAO = new EcritureDAO();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		creatableau(request);
		request.getRequestDispatcher("/WEB-INF/Virement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Client unCli = (Client)	request.getSession().getAttribute("client");
		String unDeb = request.getParameter("Debiter");
		String unCred = request.getParameter("Crediter");
		String unMontant = request.getParameter("montant");
		String unLib = request.getParameter("libelle");




		if(unDeb==null || unCred==null || unMontant==null || unLib==null) 
			// Nous sommes dans le cas ou il manque au moins une information.
		{

			if(unDeb==null || unCred==null)	
				// Il n'y aucun choix de virement
				request.setAttribute("erreurChoix","Veuillez choisir un compte débiteur et un compte créditeur");
			else if(unDeb.equals(unCred))
			// Erreur de choix
				request.setAttribute("erreurChoix","Veuillez choisir un compte débiteur et un compte créditeur différents");
			
			if(!unMontant.matches("^[0-9]*([.]{1}[0-9]{0,2}){0,1}$") || unMontant.isEmpty())
				// Montant est soit null, soit ne correspond pas a un décimal ou est négatif
				request.setAttribute("erreurMontant","Veuillez saisir un nombre décimal(utiliser le point pour séparer les euros des cents)");
			if(unLib.isEmpty())
				// libellé null
				request.setAttribute("erreurLib","Veuillez saisir un libellé");
			
			// On recreer le tableau(la fonction s'occupe de replacer les cases cochés 
			// et on remets les valeurs saisies... 
			this.creatableau(request);
			request.setAttribute("montant", unMontant);
			request.setAttribute("libelle",unLib);
			request.getRequestDispatcher("/WEB-INF/Virement.jsp").forward(request, response);
		}
		else
			// Tout est bon
		{
			try{
				unCli.effectuerVirement(unCli.getCompte(unDeb),unCli.getCompte(unCred),new Float(unMontant),unLib,new Date());
				this.sonEcriDAO.sauvegarder(new Ecriture(new Date(), unLib,-(new Float(unMontant))), unCli.getCompte(unDeb));
				this.sonEcriDAO.sauvegarder(new Ecriture(new Date(), unLib,(new Float(unMontant))), unCli.getCompte(unCred));

				// affichage des resultats 
				
				request.setAttribute("Montant", unMontant);
				request.setAttribute("Debiteur", unDeb);
				request.setAttribute("Crediteur", unCred);
				
				request.getRequestDispatcher("/WEB-INF/VirementReussi.jsp").forward(request, response);
			}catch(EcritureRefuseeException e)
			{
				request.setAttribute("erreurSolde","Le virement que vous avez demandé n'a pas pu être réalisé le découvert autorisé serait dépassé");
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CompteInexistantException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	/**
	 * Création du tableau pour la JSP VIREMENT
	 * @param request
	 */
	public void creatableau(HttpServletRequest request)
	{
	String uneGdeT= "";
		
		Client unCli = (Client)	request.getSession().getAttribute("client");

		for(Compte unCompte :  unCli.getComptes())
		{
			
			String unDeb = (String) request.getParameter("Debiter");
			String unCred = (String) request.getParameter("Crediter");

			
			uneGdeT+="<tr>";
			uneGdeT+="<th>"+unCompte.getNumeroCompte()+"</th>";
			
			uneGdeT+="<th>"+unCompte.getSolde()+"</th>";
			
			uneGdeT+="<th> <input type=\"radio\" name=\"Debiter\" value=\""+unCompte.getNumeroCompte()+"\"";
					
			// Pour la recreation du tableau...
			if(unDeb!=null)
			{
			if(unDeb.equals(unCompte.getNumeroCompte()))
			uneGdeT+=" checked";
			
			}
			uneGdeT+="></th>";
			
			
			uneGdeT+="<th> <input type=\"radio\" name=\"Crediter\" value=\""+unCompte.getNumeroCompte()+"\"";

			// Pour la recreation du tableau...
			if(unCred!=null)
			if(unCred.equals(unCompte.getNumeroCompte()))
			uneGdeT+=" checked";
			
			uneGdeT+="></th>";

			
			uneGdeT+="</tr>";
		}
		request.setAttribute("tableau", uneGdeT);
	}
	
	

}
