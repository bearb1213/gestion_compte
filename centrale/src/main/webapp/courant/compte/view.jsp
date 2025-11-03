<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du Compte</title>
</head>
<body>
    <h1>Détails du Compte</h1>

    <c:if test="${empty compteComplet}">
        <p style="color: red;">Compte non trouvé.</p>
        <a href="<c:url value='/compte?action=list'/>">← Retour à la liste</a>
    </c:if>

    <c:if test="${not empty compteComplet}">
        <!-- Informations du compte -->
        <h2>Informations du Compte</h2>
        <table border="1">
            <tr>
                <th>Numéro de Compte</th>
                <td><strong>${compteComplet.compte.numeroCompte}</strong></td>
            </tr>
            <tr>
                <th>Date d'Ouverture</th>
                <td>${compteComplet.compte.dateOuverture}</td>
            </tr>
            <tr>
                <th>Montant Découvert Autorisé</th>
                <td>${compteComplet.compte.montantDecouvert} ar</td>
            </tr>
            <tr>
                <th>Plafond</th>
                <td>${compteComplet.compte.plafond} ar</td>
            </tr>
            <tr>
                <th>Statut Actuel</th>
                <td>
                    <strong>${compteComplet.statusActuelCompte.libelle}</strong>
                    <c:if test="${compteComplet.statusActuelCompte.id == 1}">
                        <span style="color: green;">●</span>
                    </c:if>
                    <c:if test="${compteComplet.statusActuelCompte.id == 2}">
                        <span style="color: red;">●</span>
                    </c:if>
                    <c:if test="${compteComplet.statusActuelCompte.id == 3}">
                        <span style="color: orange;">●</span>
                    </c:if>
                </td>
            </tr>
        </table>

        <!-- Informations du client -->
        <h2>Informations du Client</h2>
        <table border="1">
            <tr>
                <th>Nom</th>
                <td>${compteComplet.compte.particulier.nom}</td>
            </tr>
            <tr>
                <th>Prénom</th>
                <td>${compteComplet.compte.particulier.prenom}</td>
            </tr>
            <tr>
                <th>CIN</th>
                <td>${compteComplet.compte.particulier.cin}</td>
            </tr>
            <tr>
                <th>Date de Naissance</th>
                <td>${compteComplet.compte.particulier.dateNaissance}</td>
            </tr>
            <tr>
                <th>Adresse</th>
                <td>${compteComplet.compte.particulier.adresse}</td>
            </tr>
            <tr>
                <th>Téléphone</th>
                <td>${compteComplet.compte.particulier.telephone}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${compteComplet.compte.particulier.email}</td>
            </tr>
        </table>

        <!-- Historique des statuts -->
        <h2>Historique des Statuts</h2>
        <c:if test="${empty compteComplet.histoStatuses}">
            <p>Aucun historique de statut disponible.</p>
        </c:if>
        <c:if test="${not empty compteComplet.histoStatuses}">
            <table border="1" width="100%">
                <tr>
                    <th>Date de Changement</th>
                    <th>Statut</th>
                </tr>
                <c:forEach var="histo" items="${compteComplet.histoStatuses}">
                    <tr>
                        <td>${histo.dateChangement}</td>
                        <td>
                            <strong>${histo.status.libelle}</strong>
                            <c:if test="${histo.status.id == 1}">
                                <span style="color: green;">(Actif)</span>
                            </c:if>
                            <c:if test="${histo.status.id == 2}">
                                <span style="color: red;">(Bloqué)</span>
                            </c:if>
                            <c:if test="${histo.status.id == 3}">
                                <span style="color: orange;">(Suspendu)</span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <!-- Actions -->
        <br>
        <div>
            <a href="<c:url value='/compte?action=edit&id=${compteComplet.compte.id}'/>">✏️ Modifier le compte</a> &nbsp;|&nbsp;
            <a href="<c:url value='/compte?action=changer-status&id=${compteComplet.compte.id}'/>">🔄 Changer le statut</a> &nbsp;|&nbsp;
            <a href="<c:url value='/compte?action=list'/>">📋 Retour à la liste</a>
        </div>
    </c:if>
</body>
</html>