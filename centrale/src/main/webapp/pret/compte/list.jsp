<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Comptes Prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>Gestion des Comptes Prêt</h2>
                    <div>
                        <a href="comptes?action=create" class="btn btn-primary me-2">Créer un Compte</a>
                        <a href="comptes?action=search" class="btn btn-info me-2">Rechercher</a>
                        <a href="dashboardPret" class="btn btn-secondary">Retour</a>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>

                <div class="card">
                    <div class="card-header">
                        <h5 class="card-title mb-0">Liste des Comptes (${comptes.size()})</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty comptes}">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover">
                                        <thead class="table-dark">
                                            <tr>
                                                <th>ID</th>
                                                <th>Numéro de Compte</th>
                                                <th>Date d'Ouverture</th>
                                                <th>ID Particulier</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="compte" items="${comptes}">
                                                <tr>
                                                    <td>${compte.id}</td>
                                                    <td>
                                                        <strong>${compte.numero}</strong>
                                                    </td>
                                                    <td>${compte.dateOuverture}</td>
                                                    <td>
                                                        <span class="badge bg-primary">${compte.idParticulier}</span>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group btn-group-sm">
                                                            <a href="comptes?action=view&id=${compte.id}" 
                                                               class="btn btn-outline-primary">Voir</a>
                                                            <a href="dashboardPret?action=applyLoan&numeroCompte=${compte.numero}" 
                                                               class="btn btn-outline-success">Demander Prêt</a>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info text-center">
                                    <p>Aucun compte de prêt trouvé.</p>
                                    <a href="comptes?action=create" class="btn btn-primary">Créer le premier compte</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html> 