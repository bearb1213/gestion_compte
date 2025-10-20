<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du Compte</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <!-- Messages de succès -->
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.success == 'create'}">
                        <i class="fas fa-check-circle me-1"></i>Compte créé avec succès!
                    </c:when>
                    <c:when test="${param.success == 'block'}">
                        <i class="fas fa-check-circle me-1"></i>Compte bloqué avec succès!
                    </c:when>
                    <c:when test="${param.success == 'unblock'}">
                        <i class="fas fa-check-circle me-1"></i>Compte débloqué avec succès!
                    </c:when>
                    <c:when test="${param.success == 'depot'}">
                        <i class="fas fa-check-circle me-1"></i>Dépôt de ${param.montant} effectué avec succès!
                    </c:when>
                    <c:when test="${param.success == 'retrait'}">
                        <i class="fas fa-check-circle me-1"></i>Retrait de ${param.montant} effectué avec succès!
                    </c:when>
                    <c:when test="${param.success == 'transfert'}">
                        <i class="fas fa-check-circle me-1"></i>Transfert de ${param.montant} vers ${param.destinataire} effectué avec succès!
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty compte}">
            <div class="row">
                <div class="col-md-8">
                    <div class="card">
                        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                            <h3 class="card-title mb-0">
                                <i class="fas fa-piggy-bank me-2"></i>Compte ${compte.numeroCompte}
                            </h3>
                            <span class="badge bg-${status.libelle == 'Actif' ? 'success' : 'danger'} fs-6">
                                ${status.libelle}
                            </span>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <h5>Informations du Compte</h5>
                                    <dl class="row">
                                        <dt class="col-sm-5">Numéro:</dt>
                                        <dd class="col-sm-7"><strong>${compte.numeroCompte}</strong></dd>

                                        <dt class="col-sm-5">Date Ouverture:</dt>
                                        <dd class="col-sm-7">${compte.dateOuverture}</dd>

                                        <dt class="col-sm-5">Découvert Autorisé:</dt>
                                        <dd class="col-sm-7">${compte.montantDecouvert} DH</dd>

                                        <dt class="col-sm-5">Solde Actuel:</dt>
                                        <dd class="col-sm-7">
                                            <span class="badge bg-${solde >= 0 ? 'success' : 'danger'} fs-6">
                                                ${solde} DH
                                            </span>
                                        </dd>
                                    </dl>
                                </div>
                                <div class="col-md-6">
                                    <h5>Informations du Client</h5>
                                    <dl class="row">
                                        <dt class="col-sm-4">Client:</dt>
                                        <dd class="col-sm-8">${compte.particulier.nom} ${compte.particulier.prenom}</dd>

                                        <dt class="col-sm-4">CIN:</dt>
                                        <dd class="col-sm-8">${compte.particulier.cin}</dd>

                                        <dt class="col-sm-4">Email:</dt>
                                        <dd class="col-sm-8">${compte.particulier.email}</dd>

                                        <dt class="col-sm-4">Téléphone:</dt>
                                        <dd class="col-sm-8">${compte.particulier.telephone}</dd>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Actions -->
                    <div class="card mt-4">
                        <div class="card-header">
                            <h5 class="card-title mb-0"><i class="fas fa-cogs me-2"></i>Actions</h5>
                        </div>
                        <div class="card-body">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="d-grid gap-2">
                                        <a href="operation?action=depot&numero=${compte.numeroCompte}" 
                                           class="btn btn-success btn-lg">
                                            <i class="fas fa-money-bill-wave me-2"></i>Dépôt
                                        </a>
                                        <a href="operation?action=retrait&numero=${compte.numeroCompte}" 
                                           class="btn btn-warning btn-lg">
                                            <i class="fas fa-hand-holding-usd me-2"></i>Retrait
                                        </a>
                                        <a href="operation?action=transfert&numero=${compte.numeroCompte}" 
                                           class="btn btn-info btn-lg">
                                            <i class="fas fa-exchange-alt me-2"></i>Transfert
                                        </a>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="d-grid gap-2">
                                        <a href="compte?action=balance&numero=${compte.numeroCompte}" 
                                           class="btn btn-secondary">
                                            <i class="fas fa-balance-scale me-2"></i>Consulter Solde
                                        </a>
                                        <c:choose>
                                            <c:when test="${status.libelle == 'Actif'}">
                                                <a href="compte?action=block&numero=${compte.numeroCompte}" 
                                                   class="btn btn-danger"
                                                   onclick="return confirm('Êtes-vous sûr de vouloir bloquer ce compte ?')">
                                                    <i class="fas fa-lock me-2"></i>Bloquer le Compte
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="compte?action=unblock&numero=${compte.numeroCompte}" 
                                                   class="btn btn-success"
                                                   onclick="return confirm('Êtes-vous sûr de vouloir débloquer ce compte ?')">
                                                    <i class="fas fa-unlock me-2"></i>Débloquer le Compte
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <!-- Informations rapides -->
                    <div class="card">
                        <div class="card-header bg-info text-white">
                            <h5 class="card-title mb-0"><i class="fas fa-info-circle me-2"></i>Résumé</h5>
                        </div>
                        <div class="card-body">
                            <div class="text-center mb-4">
                                <div class="bg-light rounded-circle d-inline-flex align-items-center justify-content-center" 
                                     style="width: 80px; height: 80px;">
                                    <i class="fas fa-piggy-bank fa-2x text-primary"></i>
                                </div>
                                <h4 class="mt-2">${solde} DH</h4>
                                <p class="text-muted">Solde Actuel</p>
                            </div>

                            <div class="list-group">
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Découvert Autorisé
                                    <span class="badge bg-primary rounded-pill">${compte.montantDecouvert} DH</span>
                                </div>
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Statut
                                    <span class="badge bg-${status.libelle == 'Actif' ? 'success' : 'danger'}">
                                        ${status.libelle}
                                    </span>
                                </div>
                                <div class="list-group-item d-flex justify-content-between align-items-center">
                                    Date Ouverture
                                    <span>${compte.dateOuverture}</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Navigation -->
                    <div class="card mt-3">
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <a href="compte?action=list" class="btn btn-outline-primary">
                                    <i class="fas fa-arrow-left me-1"></i>Retour à la Liste
                                </a>
                                <a href="compte?action=new" class="btn btn-outline-success">
                                    <i class="fas fa-plus me-1"></i>Nouveau Compte
                                </a>
                                <a href="particulie?action=list" class="btn btn-outline-secondary">
                                    <i class="fas fa-users me-1"></i>Gérer les Clients
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>