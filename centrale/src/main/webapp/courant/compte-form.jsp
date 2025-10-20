<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nouveau Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-plus-circle me-2"></i>Création d'un Nouveau Compte
                        </h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-triangle me-1"></i>${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="compte" method="post">
                            <input type="hidden" name="action" value="create">

                            <div class="mb-3">
                                <label for="particulieId" class="form-label">Particulier *</label>
                                <select class="form-select" id="particulieId" name="particulieId" required>
                                    <option value="">Sélectionnez un particulier</option>
                                    <c:forEach var="particulie" items="${particuliers}">
                                        <option value="${particulie.id}">
                                            ${particulie.nom} ${particulie.prenom} - CIN: ${particulie.cin}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="row">
                                <div class="col-md-4 mb-3">
                                    <label for="montantDecouvert" class="form-label">Montant Découvert Autorisé *</label>
                                    <input type="number" class="form-control" id="montantDecouvert" name="montantDecouvert" 
                                           step="0.01" min="0" value="0" required>
                                    <div class="form-text">Montant maximum de découvert autorisé</div>
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label for="tenuCompte" class="form-label">Frais de Tenu de Compte *</label>
                                    <input type="number" class="form-control" id="tenuCompte" name="tenuCompte" 
                                           step="0.01" min="0" value="0" required>
                                    <div class="form-text">Frais mensuels de tenu de compte</div>
                                </div>

                                <div class="col-md-4 mb-3">
                                    <label for="fraisDecouvert" class="form-label">Frais de Découvert *</label>
                                    <input type="number" class="form-control" id="fraisDecouvert" name="fraisDecouvert" 
                                           step="0.01" min="0" value="0" required>
                                    <div class="form-text">Frais appliqués en cas de découvert</div>
                                </div>
                            </div>

                            <div class="alert alert-info">
                                <h6><i class="fas fa-info-circle me-1"></i>Informations :</h6>
                                <ul class="mb-0">
                                    <li>Le numéro de compte sera généré automatiquement</li>
                                    <li>Le statut initial du compte sera "Actif"</li>
                                    <li>Les frais seront appliqués mensuellement</li>
                                </ul>
                            </div>

                            <div class="d-flex justify-content-between">
                                <a href="compte?action=list" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>Retour
                                </a>
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-save me-1"></i>Créer le Compte
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>