<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Site de ma banque</title>
</head>
<body>
<% String unMontant = request.getParameter("montant"); %>
<% String unLib = request.getParameter("libelle"); %>
<div class="cadre">
<form action="EffectuerVirement" method="POST">
      <%
      // Récupération du message d'erreur 
      String erreurSolde = (String) request.getAttribute("erreurSolde");
      // Affichage du message s'il existe
      if (erreurSolde != null) { %>
            <p class="erreur">Erreur : 
      <% out.print(erreurSolde);
      } %> </p>

<table border="1">
<thead>
<tr>
<th>Numéro de compte</th>
<th>Solde</th>
<th>Débiter</th>
<th>Créditer</th>
</tr>
</thead>
<%
out.print(request.getAttribute("tableau"));              
%>
</table>
      <%
      // Récupération du message d'erreur 
      String erreurChoix = (String) request.getAttribute("erreurChoix");
      // Affichage du message s'il existe
      if (erreurChoix != null) { %>
            <p class="erreur">Erreur : 
      <% out.print(erreurChoix);
      } %> </p>
Montant :  <input type="text" name="montant"  value="<%if(unMontant!=null)out.print(unMontant);%>" />
      <%
      // Récupération du message d'erreur 
      String erreurMontant = (String) request.getAttribute("erreurMontant");
      // Affichage du message s'il existe
      if (erreurMontant != null) { %>
            <p class="erreur">Erreur : 
      <% out.print(erreurMontant);
      } %> </p>


<br/>
Libellé :  <input type="text" name="libelle" value="<%if(unLib!=null)out.print(unLib);%>"  />
      <%
      // Récupération du message d'erreur 
      String erreurLib = (String) request.getAttribute("erreurLib");
      // Affichage du message s'il existe
      if (erreurLib != null) { %>
            <p class="erreur">Erreur : 
      <% out.print(erreurLib);
      } %> </p>


<br/>
(Le libellé sera automatiquement précédé par "VIR" pour le compte débité)<br/>
<button name="btnOK" value="OK" type="submit">Se connecter</button>
</form>
<A href="GererComptes">Retour sur la liste de compte</A><br/>
<A href="Accueil">Retour sur la page d'acceuil</A><br/>
</div>
</body>	
</html>