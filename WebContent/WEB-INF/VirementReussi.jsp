<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="refresh" content="5 ; url=EffectuerVirement">
<title>Site de ma banque</title>
</head>
<body>
<div class="cadre">
<h1>Virement effectué avec succès</h1><br/>
<p>
Votre virement de <%out.print(request.getAttribute("Montant"));%>
a bien été effectué depuis votre compte
<%out.print(request.getAttribute("Crediteur"));%>
vers votre compte 
<%out.print(request.getAttribute("Debiteur"));%>
.
</p>
</div>
</body>	
</html>