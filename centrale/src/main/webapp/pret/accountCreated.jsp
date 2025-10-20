<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Compte Créé</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h4 class="text-center">Compte Créé avec Succès</h4>
                    </div>
                    <div class="card-body">
                        <div class="alert alert-success">
                            <strong>Succès!</strong> ${success}
                        </div>
                        
                        <div class="card mb-3">
                            <div class="card-body">
                                <h5>Détails du Compte</h5>
                                <p><strong>Numéro de Compte:</strong> ${compte.numero}</p>
                                <p><strong>Date d'Ouverture:</strong> ${compte.dateOuverture}</p>
                                <p><strong>ID Particulier:</strong> ${compte.idParticulier}</p>
                            </div>
                        </div>
                        
                        <c:if test="${not empty particulier}">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <h5>Informations du Particulier</h5>
                                    <p><strong>Nom:</strong> ${particulier.nom} ${particulier.prenom}</p>
                                    <p><strong>Email:</strong> ${particulier.email}</p>
                                </div>
                            </div>
                        </c:if>
                        
                        <div class="d-grid gap-2">
                            <a href="dashboardPret" class="btn btn-primary">Retour au Tableau de Bord</a>
                            <a href="dashboardPret?action=applyLoan" class="btn btn-success">Demander un Prêt</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>