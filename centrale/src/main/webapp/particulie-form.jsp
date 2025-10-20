<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Formulaire Particulier</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .required-label::after {
            content: " *";
            color: red;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-user me-2"></i>
                            <c:choose>
                                <c:when test="${empty particulie}">Nouveau Particulier</c:when>
                                <c:otherwise>Modifier le Particulier</c:otherwise>
                            </c:choose>
                        </h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <i class="fas fa-exclamation-triangle me-1"></i>${error}
                                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                        </c:if>

                        <form action="particulie" method="post" id="particulieForm">
                            <c:if test="${not empty particulie}">
                                <input type="hidden" name="id" value="${particulie.id}">
                                <input type="hidden" name="action" value="update">
                            </c:if>
                            <c:if test="${empty particulie}">
                                <input type="hidden" name="action" value="insert">
                            </c:if>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="cin" class="form-label required-label">CIN</label>
                                    <input type="text" class="form-control" id="cin" name="cin" 
                                           value="${particulie.cin}" required maxlength="50"
                                           placeholder="Numéro de CIN">
                                    <div class="form-text">Le CIN doit être unique</div>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="nom" class="form-label required-label">Nom</label>
                                    <input type="text" class="form-control" id="nom" name="nom" 
                                           value="${particulie.nom}" required maxlength="50"
                                           placeholder="Nom de famille">
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="prenom" class="form-label">Prénom</label>
                                    <input type="text" class="form-control" id="prenom" name="prenom" 
                                           value="${particulie.prenom}" maxlength="50"
                                           placeholder="Prénom">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="dateNaissance" class="form-label required-label">Date de Naissance</label>
                                    <input type="date" class="form-control" id="dateNaissance" name="dateNaissance" 
                                           value="${particulie.dateNaissance}" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="adresse" class="form-label">Adresse</label>
                                <textarea class="form-control" id="adresse" name="adresse" 
                                          rows="3" maxlength="50" placeholder="Adresse complète">${particulie.adresse}</textarea>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="telephone" class="form-label">Téléphone</label>
                                    <input type="tel" class="form-control" id="telephone" name="telephone" 
                                           value="${particulie.telephone}" maxlength="50"
                                           placeholder="Numéro de téléphone">
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="email" class="form-label required-label">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" 
                                           value="${particulie.email}" required maxlength="50"
                                           placeholder="adresse@email.com">
                                </div>
                            </div>

                            <div class="d-flex justify-content-between mt-4">
                                <a href="particulie?action=list" class="btn btn-secondary">
                                    <i class="fas fa-arrow-left me-1"></i>Retour à la liste
                                </a>
                                <button type="submit" class="btn btn-success">
                                    <i class="fas fa-save me-1"></i>
                                    <c:choose>
                                        <c:when test="${empty particulie}">Créer</c:when>
                                        <c:otherwise>Modifier</c:otherwise>
                                    </c:choose>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validation côté client
        document.getElementById('particulieForm').addEventListener('submit', function(e) {
            const cin = document.getElementById('cin').value.trim();
            const nom = document.getElementById('nom').value.trim();
            const email = document.getElementById('email').value.trim();
            const dateNaissance = document.getElementById('dateNaissance').value;
            
            if (!cin || !nom || !email || !dateNaissance) {
                e.preventDefault();
                alert('Veuillez remplir tous les champs obligatoires (*)');
                return false;
            }
            
            // Validation basique de l'email
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                e.preventDefault();
                alert('Veuillez entrer une adresse email valide');
                return false;
            }
        });
    </script>
</body>
</html>