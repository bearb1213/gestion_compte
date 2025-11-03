<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nouveau Particulier</title>
</head>
<body>
    <h1>Créer un nouveau Particulier</h1>

    <!-- Affichage des erreurs -->
    <c:if test="${not empty errorMessage}">
        <div style="color: red; border: 1px solid red; padding: 10px; margin: 10px 0;">
            <strong>Erreur :</strong> ${errorMessage}
        </div>
    </c:if>

    <form action="<c:url value='/particulier'/>" method="post">
        <input type="hidden" name="action" value="create">
        
        <div>
            <label for="cin">CIN *:</label><br>
            <input type="text" id="cin" name="cin" value="${param.cin}" required size="50">
        </div>
        <br>
        
        <div>
            <label for="nom">Nom *:</label><br>
            <input type="text" id="nom" name="nom" value="${param.nom}" required size="50">
        </div>
        <br>
        
        <div>
            <label for="prenom">Prénom :</label><br>
            <input type="text" id="prenom" name="prenom" value="${param.prenom}" size="50">
        </div>
        <br>
        
        <div>
            <label for="dateNaissance">Date de Naissance *:</label><br>
            <input type="date" id="dateNaissance" name="dateNaissance" value="${param.dateNaissance}" required>
        </div>
        <br>
        
        <div>
            <label for="adresse">Adresse :</label><br>
            <input type="text" id="adresse" name="adresse" value="${param.adresse}" size="50">
        </div>
        <br>
        
        <div>
            <label for="telephone">Téléphone :</label><br>
            <input type="text" id="telephone" name="telephone" value="${param.telephone}" size="50">
        </div>
        <br>
        
        <div>
            <label for="email">Email :</label><br>
            <input type="email" id="email" name="email" value="${param.email}" size="50">
        </div>
        <br>
        
        <div>
            <input type="submit" value="Créer le particulier">
            <input type="reset" value="Réinitialiser">
        </div>
    </form>

    <br>
    <a href="<c:url value='/particulier?action=list'/>">← Retour à la liste</a>
</body>
</html>