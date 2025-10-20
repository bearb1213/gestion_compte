<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détails du Particulier</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-info text-white">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-user-circle me-2"></i>Détails du Particulier
                        </h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty particulie}">
                            <div class="row mb-4">
                                <div class="col-md-4 text-center">
                                    <div class="bg-light rounded-circle d-inline-flex align-items-center justify-content-center" 
                                         style="width: 100px; height: 100px;">
                                        <i class="fas fa-user fa-3x text-secondary"></i>
                                    </div>
                                </div>
                                <div class="col-md-8">
                                    <h4>${particulie.nom} ${particulie.prenom}</h4>
                                    <p class="text-muted">ID: ${particulie.id}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <h5 class="border-bottom pb-2">Informations Personnelles</h5>
                                    <dl class="row">
                                        <dt class="col-sm-4">CIN:</dt>
                                        <dd class="col-sm-8">
                                            <span class="badge bg-primary">${particulie.cin}</span>
                                        </dd>

                                        <dt class="col-sm-4">Nom:</dt>
                                        <dd class="col-sm-8">${particulie.nom}</dd>

                                        <dt class="col-sm-4">Prénom:</dt>
                                        <dd class="col-sm-8">${particulie.prenom}</dd>

                                        <dt class="col-sm-4">Date Naissance:</dt>
                                        <dd class="col-sm-8">${particulie.dateNaissance}</dd>
                                    </dl>
                                </div>

                                <div class="col-md-6">
                                    <h5 class="border-bottom pb-2">Coordonnées</h5>
                                    <dl class="row">
                                        <dt class="col-sm-4">Email:</dt>
                                        <dd class="col-sm-8">
                                            <a href="mailto:${particulie.email}" class="text-decoration-none">
                                                ${particulie.email}
                                            </a>
                                        </dd>

                                        <dt class="col-sm-4">Téléphone:</dt>
                                        <dd class="col-sm-8">
                                            <c:choose>
                                                <c:when test="${not empty particulie.telephone}">
                                                    <a href="tel:${particulie.telephone}" class="text-decoration-none">
                                                        ${particulie.telephone}
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Non renseigné</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </dd>

                                        <dt class="col-sm-4">Adresse:</dt>
                                        <dd class="col-sm-8">
                                            <c:choose>
                                                <c:when test="${not empty particulie.adresse}">
                                                    ${particulie.adresse}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Non renseignée</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${empty particulie}">
                            <div class="alert alert-warning text-center">
                                <i class="fas fa-exclamation-triangle me-2"></i>
                                Particulier non trouvé
                            </div>
                        </c:if>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="particulie?action=list" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-1"></i>Retour à la liste
                            </a>
                            <div>
                                <a href="compte?"></a>
                                <a href="particulie?action=edit&id=${particulie.id}" class="btn btn-warning">
                                    <i class="fas fa-edit me-1"></i>Modifier
                                </a>
                                <a href="particulie?action=list" class="btn btn-primary ms-2">
                                    <i class="fas fa-list me-1"></i>Liste complète
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>