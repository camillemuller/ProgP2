<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Site de ma banque</title>
</head>
<body>
<div class="cadre">
<h1>Bienvenue</h1>
<h2>bonjour <%
out.print(request.getAttribute("nom_client"));                
%>. Que souhaitez-vous faire ?</h2><br/>
<A href="page.htm">Modifier vos informations personnelles</A><br/>
<A href="page.htm">Gérer vos comptes</A><br/>
<A href="page.htm">Se déconnecter</A><br/>
</div>
</body>	
</html>