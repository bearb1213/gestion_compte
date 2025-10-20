<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Comptes Prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-center mb-4">Liste des Comptes Prêt</h2>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="alert alert-success">${success}</div>
                </c:if>
                
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Numéro</th>
                                <th>Date Ouverture</th>
                                <th>ID Particulier</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="compte" items="${comptes}">
                                <tr>
                                    <td>${compte.id}</td>
                                    <td>${compte.numero}</td>
                                    <td>${compte.dateOuverture}</td>
                                    <td>${compte.idParticulier}</td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="dashboardPret?action=blockAccount&numeroCompte=${compte.numero}" 
                                               class="btn btn-warning btn-sm" 
                                               onclick="return confirm('Bloquer ce compte?')">Bloquer</a>
                                            <a href="dashboardPret?action=unblockAccount&numeroCompte=${compte.numero}" 
                                               class="btn btn-success btn-sm"
                                               onclick="return confirm('Débloquer ce compte?')">Débloquer</a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="text-center mt-4">
                    <a href="dashboardPret" class="btn btn-primary">Retour au Tableau de Bord</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>