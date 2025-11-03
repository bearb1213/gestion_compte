<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nouveau Compte</title>
</head>
<body>
    <h1>Créer un nouveau Compte Bancaire</h1>

    <!-- Affichage des erreurs -->
    <c:if test="${not empty errorMessage}">
        <div style="color: red; border: 1px solid red; padding: 10px; margin: 10px 0;">
            <strong>Erreur :</strong> ${errorMessage}
        </div>
    </c:if>

    <form action="<c:url value='/compte'/>" method="post">
        <input type="hidden" name="action" value="create">
        
        <div>
            <label for="idParticulier">Particulier *:</label><br>
            <select id="idParticulier" name="idParticulier" required style="width: 300px;">
                <option value="">-- Sélectionner un particulier --</option>
                <c:forEach var="particulier" items="${particuliers}">
                    <option value="${particulier.id}" 
                        ${param.idParticulier == particulier.id ? 'selected' : ''}>
                        ${particulier.nom} ${particulier.prenom} - CIN: ${particulier.cin}
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        
        <div>
            <label for="dateOuverture">Date d'Ouverture *:</label><br>
            <input type="date" id="dateOuverture" name="dateOuverture" value="${param.dateOuverture}" required>
        </div>
        <br>
        
        <div>
            <label for="montantDecouvert">Montant Découvert Autorisé (Ar):</label><br>
            <input type="number" id="montantDecouvert" name="montantDecouvert" 
                   value="${param.montantDecouvert}" step="0.01" min="0" placeholder="0.00">
        </div>
        <br>
        
        <div>
            <label for="plafond">Plafond du Compte (Ar):</label><br>
            <input type="number" id="plafond" name="plafond" 
                   value="${param.plafond}" step="0.01" min="0" placeholder="0.00">
        </div>
        <br>
        
        <div>
            <input type="submit" value="Créer le compte">
            <input type="reset" value="Réinitialiser">
        </div>
    </form>

    <br>
    <a href="<c:url value='/compte?action=list'/>">← Retour à la liste des comptes</a>
    <br>
    <a href="<c:url value='/particulier?action=create'/>">+ Créer un nouveau particulier</a>
</body>
</html>