<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Particuliers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .table-actions {
            white-space: nowrap;
            width: 120px;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1><i class="fas fa-users me-2"></i>Gestion des Particuliers</h1>
            <a href="compte" class="btn btn-secondary">
                <i class="fas fa-piggy-bank me-1"></i>Gestion des Comptes
            </a>
            <a href="particulie?action=new" class="btn btn-primary">
                <i class="fas fa-plus me-1"></i>Nouveau Particulier
            </a>
        </div>

        <!-- Messages d'alerte -->
        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.success == 'create'}">
                        <i class="fas fa-check-circle me-1"></i>Particulier créé avec succès!
                    </c:when>
                    <c:when test="${param.success == 'update'}">
                        <i class="fas fa-check-circle me-1"></i>Particulier modifié avec succès!
                    </c:when>
                    <c:when test="${param.success == 'delete'}">
                        <i class="fas fa-check-circle me-1"></i>Particulier supprimé avec succès!
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'notfound'}">
                        <i class="fas fa-exclamation-triangle me-1"></i>Particulier non trouvé!
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card">
            <div class="card-header bg-dark text-white">
                <h5 class="card-title mb-0">
                    <i class="fas fa-list me-1"></i>Liste des Particuliers
                    <span class="badge bg-secondary ms-2">${listParticulie.size()}</span>
                </h5>
            </div>
            <div class="card-body">
                <c:if test="${empty listParticulie}">
                    <div class="text-center text-muted py-4">
                        <i class="fas fa-info-circle fa-3x mb-3"></i>
                        <h5>Aucun particulier enregistré</h5>
                        <p>Commencez par ajouter un nouveau particulier.</p>
                        <a href="particulie?action=new" class="btn btn-primary">
                            <i class="fas fa-plus me-1"></i>Ajouter le premier particulier
                        </a>
                    </div>
                </c:if>

                <c:if test="${not empty listParticulie}">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>CIN</th>
                                    <th>Nom</th>
                                    <th>Prénom</th>
                                    <th>Date Naissance</th>
                                    <th>Email</th>
                                    <th>Téléphone</th>
                                    <th class="table-actions">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="particulie" items="${listParticulie}">
                                    <tr>
                                        <td>${particulie.id}</td>
                                        <td>
                                            <span class="badge bg-info text-dark">${particulie.cin}</span>
                                        </td>
                                        <td>${particulie.nom}</td>
                                        <td>${particulie.prenom}</td>
                                        <td>${particulie.dateNaissance}</td>
                                        <td>
                                            <a href="mailto:${particulie.email}" class="text-decoration-none">
                                                ${particulie.email}
                                            </a>
                                        </td>
                                        <td>
                                            <c:if test="${not empty particulie.telephone}">
                                                <a href="tel:${particulie.telephone}" class="text-decoration-none">
                                                    ${particulie.telephone}
                                                </a>
                                            </c:if>
                                        </td>
                                        <td class="table-actions">
                                            <div class="btn-group btn-group-sm">
                                                <a href="particulie?action=view&id=${particulie.id}" 
                                                   class="btn btn-info" title="Voir détails">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                                <a href="particulie?action=edit&id=${particulie.id}" 
                                                   class="btn btn-warning" title="Modifier">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="particulie?action=delete&id=${particulie.id}" 
                                                   class="btn btn-danger" 
                                                   onclick="return confirm('Êtes-vous sûr de vouloir supprimer ${particulie.nom} ${particulie.prenom} ?')"
                                                   title="Supprimer">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>