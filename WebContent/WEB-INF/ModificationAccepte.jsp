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
<div class="cadre">
<h1>Modification effectuée</h1>
<h2>Vos informations ont bien été prises en compte :</h2><br/>
<h2>Nom :<%out.print(request.getAttribute("nom_client"));%></h2><br/>
<h2>Prénom :<%out.print(request.getAttribute("prenom_client"));%></h2>
<h2>Adresse :<%out.print(request.getAttribute("adresse"));%></h2><br/>
<A href="Accueil">Retour sur la page d'acceuil</A><br/>
</div>
</body>	
</html>