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
<h1>Bienvenue</h1>
<h2>bonjour <%
out.print(request.getAttribute("nom_client")+" "); out.print(request.getAttribute("prenom_client"));                
%>. Que souhaitez-vous faire ?</h2><br/>
<a href="ModificationInfosPerso">Modifier vos informations personnelles</a><br/>
<a href="GererComptes">G�rer vos comptes</a><br/>
<a href="Deconnexion">D�connexion</a>
</div>
</body>	
</html>