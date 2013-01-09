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



<form action="Accueil" class="cadre" method="POST">
<div class="form">
<h1 style="text-align:center;" >Ma banque</h1><p>Veuillez vous identifier:</p> 

Entrez votre prenom : <input type="text" name="prenom"/><br>
 
Entrez votre nom :<input type="text" name="nom"/><br>

<button name="btnOK" value="OK" type="submit">Se connecter</button>
      <%
      // Récupération du message d'erreur 
      String erreur = (String) request.getAttribute("erreur");
      // Affichage du message s'il existe
      if (erreur != null) { %>
            <p class="erreur">Erreur : 
      <% out.print(erreur);
      } %> </p>

</div>
</form>
</body>
</html>