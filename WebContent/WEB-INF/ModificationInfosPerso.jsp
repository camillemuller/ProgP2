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
<form action="ModificationInfosPerso" class="cadre" method="POST">
<h1>Bienvenue</h1>
<h2>Nom : <%out.print(request.getAttribute("nom_client"));%></h2>
<br/>
<h2>Prenom : <% out.print(request.getAttribute("prenom_client"));%></h2>
<br/>
Entrez votre nom :<input type="text" name="adresse" value="<%out.print(request.getAttribute("adresse"));%>" /> <br>
<button name="btnOK" value="OK" type="submit">Modification</button>
<A href="Accueil">Retour sur la page d'acceuil</A><br/>
</form>

</body>
</html>