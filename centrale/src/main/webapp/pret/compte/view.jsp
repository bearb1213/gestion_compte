<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-info text-white">
                        <h4 class="card-title mb-0">Détails du Compte Prêt</h4>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty compte}">
                            <div class="table-responsive">
                                <table class="table table-bordered">
                                    <tr>
                                        <th width="30%">ID</th>
                                        <td>${compte.id}</td>
                                    </tr>
                                    <tr>
                                        <th>Numéro de Compte</th>
                                        <td><strong class="text-primary">${compte.numero}</strong></td>
                                    </tr>
                                    <tr>
                                        <th>Date d'Ouverture</th>
                                        <td>${compte.dateOuverture}</td>
                                    </tr>
                                    <tr>
                                        <th>ID Particulier</th>
                                        <td><span class="badge bg-primary">${compte.idParticulier}</span></td>
                                    </tr>
                                </table>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                                <a href="dashboardPret?action=applyLoan&numeroCompte=${compte.numero}" 
                                   class="btn btn-success me-md-2">Demander un Prêt</a>
                                <a href="comptes" class="btn btn-secondary">Retour à la Liste</a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>