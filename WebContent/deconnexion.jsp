<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="5 ; url=index.jsp">
<title>Site de ma banque</title>
</head>
<body>
<div class="cadre">
<h1>D�connexion r�ussie</h1>
<h2>Au revoir <%
out.print(request.getAttribute("nom_client")+" "); out.print(request.getAttribute("prenom_client"));                
%> .</h2><br/>
</div>
</body>	
</html>