<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Comptes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1><i class="fas fa-piggy-bank me-2"></i>Gestion des Comptes</h1>
            <a href="compte?action=new" class="btn btn-primary">
                <i class="fas fa-plus me-1"></i>Nouveau Compte
            </a>
        </div>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <c:choose>
                    <c:when test="${param.success == 'create'}">
                        <i class="fas fa-check-circle me-1"></i>Compte créé avec succès!
                    </c:when>
                </c:choose>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-8 mx-auto">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="card-title mb-0">
                            <i class="fas fa-search me-1"></i>Rechercher un Compte
                        </h5>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-triangle me-1"></i>${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="compte" method="post">
                            <input type="hidden" name="action" value="search">
                            <div class="mb-3">
                                <label for="numeroCompte" class="form-label">Numéro de Compte</label>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="numeroCompte" name="numeroCompte" 
                                           required placeholder="Entrez le numéro de compte">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-search me-1"></i>Rechercher
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="mt-4">
                            <h6>Actions Rapides :</h6>
                            <div class="d-grid gap-2 d-md-flex">
                                <a href="compte?action=new" class="btn btn-outline-primary">
                                    <i class="fas fa-plus me-1"></i>Créer un Compte
                                </a>
                                <a href="particulie?action=list" class="btn btn-outline-secondary">
                                    <i class="fas fa-users me-1"></i>Gérer les Particuliers
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