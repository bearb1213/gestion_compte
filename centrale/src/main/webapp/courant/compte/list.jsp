<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Comptes</title>
</head>
<body>
    <h1>Liste des Comptes Bancaires</h1>

    <a href="<c:url value='/compte?action=create'/>">+ Créer un nouveau compte</a>
    <br><br>

    <c:if test="${empty comptes}">
        <p>Aucun compte bancaire trouvé.</p>
    </c:if>

    <c:if test="${not empty comptes}">
        <table border="1" width="100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Numéro Compte</th>
                    <th>Date Ouverture</th>
                    <th>Découvert Autorisé</th>
                    <th>Plafond</th>
                    <th>Client</th>
                    <th>Contact</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="compte" items="${comptes}">
                    <tr>
                        <td align="center">${compte.id}</td>
                        <td><strong>${compte.numeroCompte}</strong></td>
                        <td>${compte.dateOuverture}</td>
                        <td align="right">${compte.montantDecouvert} Ar</td>
                        <td align="right">${compte.plafond} Ar</td>
                        <td>
                            <strong>${compte.particulier.nom} ${compte.particulier.prenom}</strong>
                            <br><em>CIN: ${compte.particulier.cin}</em>
                        </td>
                        <td>
                            📧 ${compte.particulier.email}
                            <br>📞 ${compte.particulier.telephone}
                        </td>
                        <td align="center">
                            <a href="<c:url value='/compte?action=view&id=${compte.id}'/>">Voir</a>
                            <br>
                            <!-- <a href="<c:url value='/compte?action=edit&id=${compte.id}'/>">Modifier</a> -->
                            <br>
                            <a href="<c:url value='/compte?action=delete&id=${compte.id}'/>" 
                               onclick="return confirm('Supprimer le compte ${compte.numeroCompte} ?')">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <br>
        <p><strong>Total : ${comptes.size()} compte(s) bancaire(s)</strong></p>
    </c:if>

    <br>
    <a href="javascript:history.back()">← Retour</a>
</body>
</html>