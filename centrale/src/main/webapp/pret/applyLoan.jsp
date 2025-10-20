<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Demander un Prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">Demander un Prêt</h4>
                    </div>
                    <div class="card-body">
                        <form action="pret/applyLoan" method="post">
                            <div class="mb-3">
                                <label for="numeroCompte" class="form-label">Sélectionner un Compte</label>
                                <select class="form-select" id="numeroCompte" name="numeroCompte" required>
                                    <option value="">Choisir un compte...</option>
                                    <c:forEach var="compte" items="${comptes}">
                                        <option value="${compte.numero}">
                                            ${compte.numero} (Particulier ID: ${compte.idParticulier})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="montant" class="form-label">Montant du Prêt (€)</label>
                                <input type="number" step="0.01" class="form-control" id="montant" name="montant" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="dureeMois" class="form-label">Durée (mois)</label>
                                <input type="number" class="form-control" id="dureeMois" name="dureeMois" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="tauxInteret" class="form-label">Taux d'Intérêt (%)</label>
                                <input type="number" step="0.01" class="form-control" id="tauxInteret" name="tauxInteret" required>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success">Soumettre la Demande</button>
                                <a href="dashboardPret" class="btn btn-secondary">Retour</a>
                            </div>
                        </form>
                        
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3">${error}</div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>