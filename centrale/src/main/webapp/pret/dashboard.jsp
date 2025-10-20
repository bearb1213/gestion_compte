<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord Prêts</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h1 class="text-center mb-4">Gestion des Prêts</h1>
                
                <div class="row">
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Créer un Compte</h5>
                                <p class="card-text">Créer un nouveau compte de prêt</p>
                                <a href="dashboardPret?action=createAccount" class="btn btn-primary">Créer Compte</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Voir Comptes</h5>
                                <p class="card-text">Liste de tous les comptes</p>
                                <a href="dashboardPret?action=viewAccounts" class="btn btn-info">Voir Comptes</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Demander un Prêt</h5>
                                <p class="card-text">Soumettre une demande de prêt</p>
                                <a href="dashboardPret?action=applyLoan" class="btn btn-success">Demander Prêt</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-md-3 mb-3">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Simuler Prêt</h5>
                                <p class="card-text">Visualiser l'échéancier</p>
                                <a href="dashboardPret?action=viewSchedule" class="btn btn-warning">Simuler Prêt</a>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row mt-4">
                    <div class="col-md-6 mb-3">
                        <div class="card">
                            <div class="card-body text-center">
                                <h5 class="card-title">Liste des Particuliers</h5>
                                <p class="card-text">Voir tous les particuliers</p>
                                <a href="/bank/particulie" class="btn btn-secondary">Voir Particuliers</a>
                            </div>
                        </div>
                    </div>
                </div>
                
                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
                
                <c:if test="${not empty success}">
                    <div class="alert alert-success mt-3">${success}</div>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>