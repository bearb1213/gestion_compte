<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Retrait</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-warning text-dark">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-hand-holding-usd me-2"></i>Effectuer un Retrait
                        </h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-triangle me-1"></i>${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <c:if test="${not empty compte}">
                            <div class="alert alert-info">
                                <h6><i class="fas fa-info-circle me-1"></i>Informations du Compte :</h6>
                                <p class="mb-1"><strong>Numéro :</strong> ${compte.numeroCompte}</p>
                                <p class="mb-1"><strong>Client :</strong> ${compte.particulier.nom} ${compte.particulier.prenom}</p>
                                <p class="mb-1"><strong>Découvert Autorisé :</strong> ${compte.montantDecouvert} DH</p>
                            </div>

                            <form action="operation" method="post">
                                <input type="hidden" name="action" value="retrait">
                                <input type="hidden" name="numeroCompte" value="${compte.numeroCompte}">

                                <div class="mb-3">
                                    <label for="montant" class="form-label">Montant du Retrait (DH) *</label>
                                    <input type="number" class="form-control" id="montant" name="montant" 
                                           step="0.01" min="0.01" required placeholder="Entrez le montant">
                                    <div class="form-text">Le retrait peut utiliser le découvert autorisé</div>
                                </div>

                                <div class="d-flex justify-content-between">
                                    <a href="compte?action=view&numero=${compte.numeroCompte}" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left me-1"></i>Annuler
                                    </a>
                                    <button type="submit" class="btn btn-warning">
                                        <i class="fas fa-check me-1"></i>Confirmer le Retrait
                                    </button>
                                </div>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>