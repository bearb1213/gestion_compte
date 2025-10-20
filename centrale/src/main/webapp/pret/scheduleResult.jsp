<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Échéancier de Remboursement</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-center mb-4">Échéancier de Remboursement</h2>
                
                <div class="card mb-4">
                    <div class="card-body">
                        <h5>Résumé du Prêt</h5>
                        <p><strong>Montant:</strong> ${montant} €</p>
                        <p><strong>Durée:</strong> ${dureeMois} mois</p>
                        <p><strong>Taux d'intérêt:</strong> ${tauxInteret} %</p>
                    </div>
                </div>
                
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead class="table-dark">
                            <tr>
                                <th>Échéance</th>
                                <th>Date Prévue</th>
                                <th>Capital</th>
                                <th>Intérêts</th>
                                <th>Assurance</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="echeance" items="${schedule}">
                                <tr>
                                    <td>${echeance.numeroEcheance}</td>
                                    <td>${echeance.datePrevue}</td>
                                    <td>${echeance.montantCapital} €</td>
                                    <td>${echeance.montantInteret} €</td>
                                    <td>${echeance.montantAssurance != null ? echeance.montantAssurance : '0.00'} €</td>
                                    <td><strong>${echeance.montantTotal} €</strong></td>
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