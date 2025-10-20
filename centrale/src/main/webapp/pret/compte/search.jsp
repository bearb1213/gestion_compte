<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rechercher un Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title mb-0">Rechercher un Compte Prêt</h4>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        <h6>Recherche par ID</h6>
                                    </div>
                                    <div class="card-body">
                                        <form action="comptes" method="post">
                                            <input type="hidden" name="action" value="searchById">
                                            <div class="mb-3">
                                                <label for="id" class="form-label">ID du Compte</label>
                                                <input type="number" class="form-control" id="id" name="id" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary w-100">Rechercher par ID</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        <h6>Recherche par Numéro</h6>
                                    </div>
                                    <div class="card-body">
                                        <form action="comptes" method="post">
                                            <input type="hidden" name="action" value="searchByNumero">
                                            <div class="mb-3">
                                                <label for="numero" class="form-label">Numéro de Compte</label>
                                                <input type="text" class="form-control" id="numero" name="numero" required>
                                            </div>
                                            <button type="submit" class="btn btn-info w-100">Rechercher par Numéro</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3">${error}</div>
                        </c:if>

                        <c:if test="${not empty compte}">
                            <div class="card mt-4">
                                <div class="card-header bg-success text-white">
                                    <h6 class="mb-0">Résultat de la Recherche</h6>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered">
                                            <tr>
                                                <th width="30%">ID</th>
                                                <td>${compte.id}</td>
                                            </tr>
                                            <tr>
                                                <th>Numéro de Compte</th>
                                                <td><strong>${compte.numero}</strong></td>
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
                                    <div class="text-center mt-3">
                                        <a href="dashboardPret?action=applyLoan&numeroCompte=${compte.numero}" 
                                           class="btn btn-success">Demander un Prêt</a>
                                        <a href="comptes" class="btn btn-secondary">Voir Tous les Comptes</a>
                                    </div>
                                </div>
                            </div>
                        </c:if>

                        <div class="text-center mt-3">
                            <a href="comptes" class="btn btn-outline-secondary">Retour à la Liste</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>