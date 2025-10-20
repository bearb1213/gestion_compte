<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Compte Créé</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h4 class="card-title mb-0">Compte Créé avec Succès</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-success">
                            <i class="bi bi-check-circle-fill"></i>
                            <strong>Succès!</strong> ${success}
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card mb-3">
                                    <div class="card-header">
                                        <h6>Détails du Compte</h6>
                                    </div>
                                    <div class="card-body">
                                        <p><strong>Numéro de Compte:</strong><br>
                                           <span class="fs-5 text-primary">${compte.numero}</span>
                                        </p>
                                        <p><strong>Date d'Ouverture:</strong><br>
                                           ${compte.dateOuverture}
                                        </p>
                                        <p><strong>ID Particulier:</strong><br>
                                           <span class="badge bg-primary">${compte.idParticulier}</span>
                                        </p>
                                    </div>
                                </div>
                            </div>
                            
                            <c:if test="${not empty particulier}">
                                <div class="col-md-6">
                                    <div class="card">
                                        <div class="card-header">
                                            <h6>Informations du Particulier</h6>
                                        </div>
                                        <div class="card-body">
                                            <p><strong>Nom Complet:</strong><br>
                                               ${particulier.nom} ${particulier.prenom}
                                            </p>
                                            <p><strong>CIN:</strong><br>
                                               ${particulier.cin}
                                            </p>
                                            <p><strong>Email:</strong><br>
                                               ${particulier.email}
                                            </p>
                                            <p><strong>Téléphone:</strong><br>
                                               ${particulier.telephone}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                            <a href="comptes?action=create" class="btn btn-primary me-md-2">Créer un Autre Compte</a>
                            <a href="dashboardPret?action=applyLoan&numeroCompte=${compte.numero}" class="btn btn-success me-md-2">Demander un Prêt</a>
                            <a href="comptes" class="btn btn-secondary">Voir Tous les Comptes</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>