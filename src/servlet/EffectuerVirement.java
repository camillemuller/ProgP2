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
    private Client unCli;
	private String unDeb;
	private String unCred;
	private String unMontant;
	private String unLib;

       
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
		this.unCli = (Client)	request.getSession().getAttribute("client");
		this.rechargementPage(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.unDeb = request.getParameter("Debiter");
		this.unCred = request.getParameter("Crediter");
		this.unMontant = request.getParameter("montant");
		this.unLib = request.getParameter("libelle");




		if(this.unDeb==null || this.unCred==null || this.unMontant==null || this.unLib==null
			||!this.unMontant.matches("^[0-9]*([.]{1}[0-9]{0,2}){0,1}$")) 
			// Nous sommes dans le cas ou il manque au moins une information.
			// Ou le montant ne soit pas bien ecris.
		{

			if(this.unDeb==null || this.unCred==null)	
				// Il n'y aucun choix de virement
				request.setAttribute("erreurChoix","Veuillez choisir un compte débiteur et un compte créditeur");
			else if(this.unDeb.equals(this.unCred))
			// Erreur de choix
				request.setAttribute("erreurChoix","Veuillez choisir un compte débiteur et un compte créditeur différents");
			
			if(!this.unMontant.matches("^[0-9]*([.]{1}[0-9]{0,2}){0,1}$") || this.unMontant.isEmpty())
				// Montant est soit null, soit ne correspond pas a un décimal ou est négatif
				request.setAttribute("erreurMontant","Veuillez saisir un nombre décimal(utiliser le point pour séparer les euros des cents)");
			if(this.unLib.isEmpty())
				// libellé null
				request.setAttribute("erreurLib","Veuillez saisir un libellé");
			
			this.rechargementPage(request, response);
		}
		else
			// Tout est bon
		{
			try{
				this.unCli.effectuerVirement(this.unCli.getCompte(this.unDeb),this.unCli.getCompte(this.unCred),new Float(this.unMontant),this.unLib,new Date());
				this.sonEcriDAO.sauvegarder(new Ecriture(new Date(), this.unLib,-(new Float(unMontant))), this.unCli.getCompte(this.unDeb));
				this.sonEcriDAO.sauvegarder(new Ecriture(new Date(), this.unLib,(new Float(this.unMontant))), this.unCli.getCompte(this.unCred));

				// affichage des resultats 
				
				request.setAttribute("Montant", this.unMontant);
				request.setAttribute("Debiteur", this.unDeb);
				request.setAttribute("Crediteur", this.unCred);
				
				request.getRequestDispatcher("/WEB-INF/VirementReussi.jsp").forward(request, response);
			}catch(EcritureRefuseeException e)
			{
				request.setAttribute("erreurSolde","Le virement que vous avez demandé n'a pas pu être réalisé le découvert autorisé serait dépassé");
				rechargementPage(request, response);
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
		

		for(Compte unCompte :  this.unCli.getComptes())
		{
			uneGdeT+="<tr>";
			uneGdeT+="<th>"+unCompte.getNumeroCompte()+"</th>";
			
			uneGdeT+="<th>"+unCompte.getSolde()+"</th>";
			
			uneGdeT+="<th> <input type=\"radio\" name=\"Debiter\" value=\""+unCompte.getNumeroCompte()+"\"";
					
			// Pour la recreation du tableau...
			if(this.unDeb!=null)
			{
			if(this.unDeb.equals(unCompte.getNumeroCompte()))
			uneGdeT+=" checked";
			
			}
			uneGdeT+="></th>";
			
			
			uneGdeT+="<th> <input type=\"radio\" name=\"Crediter\" value=\""+unCompte.getNumeroCompte()+"\"";

			// Pour la recreation du tableau...
			if(this.unCred!=null)
			if(this.unCred.equals(unCompte.getNumeroCompte()))
			uneGdeT+=" checked";
			
			uneGdeT+="></th>";

			
			uneGdeT+="</tr>";
		}
		request.setAttribute("tableau", uneGdeT);
	}
	
	/**
	 * Permet de revenir a la page a son état précédant le POST.
	 * @param request
	 */
	public void rechargementPage(HttpServletRequest request,HttpServletResponse response)
	{
		// On recreer le tableau(la fonction s'occupe de replacer les cases cochés) 
		// et on remets les valeurs saisies... 
		this.creatableau(request);
		request.setAttribute("montant", this.unMontant);
		request.setAttribute("libelle",this.unLib);
		try {
			request.getRequestDispatcher("/WEB-INF/Virement.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
