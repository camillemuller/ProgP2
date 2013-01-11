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
<h1>Liste de vos comptes</h1>
<table border="1">
<tr>
<th>Numéro</th>
<th>Débit</th>
<th>Crédit</th>
</tr>
<%
out.print(request.getAttribute("tableau"));         
%>
</table>
<A href="Accueil">Retour sur la page d'acceuil</A><br/>
</div>
</body>	
</html>