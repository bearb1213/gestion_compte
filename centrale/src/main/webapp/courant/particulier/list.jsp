<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Particuliers</title>
</head>
<body>
    <h1>Liste des Particuliers</h1>

    <a href="<c:url value='/particulier?action=create'/>">Ajouter un particulier</a>
    <br><br>

    <c:if test="${empty particuliers}">
        <p>Aucun particulier trouvé.</p>
    </c:if>

    <c:if test="${not empty particuliers}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>CIN</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Date Naissance</th>
                <th>Adresse</th>
                <th>Téléphone</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="particulier" items="${particuliers}">
                <tr>
                    <td>${particulier.id}</td>
                    <td>${particulier.cin}</td>
                    <td>${particulier.nom}</td>
                    <td>${particulier.prenom}</td>
                    <td>${particulier.dateNaissance}</td>
                    <td>${particulier.adresse}</td>
                    <td>${particulier.telephone}</td>
                    <td>${particulier.email}</td>
                    <td>
                        <a href="<c:url value='/particulier?action=edit&id=${particulier.id}'/>">Modifier</a>
                        <a href="<c:url value='/particulier?action=delete&id=${particulier.id}'/>" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce particulier ?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p>Total: ${particuliers.size()} particulier(s)</p>
    </c:if>

    <br>
    <a href="javascript:history.back()">Retour</a>
</body>
</html>