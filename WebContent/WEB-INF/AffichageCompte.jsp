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
<h1>Compte num�ro <%out.print(request.getParameter("numero")); %></h1>
<p>Le solde de votre compte est de <%out.print(request.getAttribute("euros"));%>euros.</p>
<p>Voici la liste des op�rations effectu�es sur votre compte :</p>
<table border="1">
<thead>
<tr>
<th>Date</th>
<th>Intitul�</th>
<th>D�bit</th>
<th>Cr�dit</th>
</tr>
</thead>
<%
out.print(request.getAttribute("tableau"));              
%>
</table>
<A href="GererComptes">Retour sur la liste de compte</A><br/>
<A href="Accueil">Retour sur la page d'acceuil</A><br/>
</div>
</body>	
</html>