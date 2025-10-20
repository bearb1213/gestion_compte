<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consultation du Solde</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-secondary text-white">
                        <h3 class="card-title mb-0">
                            <i class="fas fa-balance-scale me-2"></i>Consultation du Solde
                        </h3>
                    </div>
                    <div class="card-body">
                        <c:if test="${not empty compte}">
                            <div class="text-center mb-4">
                                <div class="bg-light rounded-circle d-inline-flex align-items-center justify-content-center" 
                                     style="width: 100px; height: 100px;">
                                    <i class="fas fa-piggy-bank fa-3x text-primary"></i>
                                </div>
                                <h2 class="mt-3 ${solde >= 0 ? 'text-success' : 'text-danger'}">${solde} DH</h2>
                                <p class="text-muted">Solde <c:if test="${not empty dateRecherche}">au ${dateRecherche}</c:if></p>
                            </div>

                            <div class="alert alert-info">
                                <h6><i class="fas fa-info-circle me-1"></i>Informations du Compte :</h6>
                                <p class="mb-1"><strong>Numéro :</strong> ${compte.numeroCompte}</p>
                                <p class="mb-1"><strong>Client :</strong> ${compte.particulier.nom} ${compte.particulier.prenom}</p>
                                <p class="mb-1"><strong>Découvert Autorisé :</strong> ${compte.montantDecouvert} DH</p>
                                <c:if test="${not empty dateRecherche}">
                                    <p class="mb-0"><strong>Date de Consultation :</strong> ${dateRecherche}</p>
                                </c:if>
                            </div>

                            <!-- Formulaire pour consulter le solde à une date spécifique -->
                            <form action="compte" method="get" class="mt-4">
                                <input type="hidden" name="action" value="balance">
                                <input type="hidden" name="numero" value="${compte.numeroCompte}">
                                
                                <div class="mb-3">
                                    <label for="date" class="form-label">Consulter le solde à une date spécifique</label>
                                    <input type="date" class="form-control" id="date" name="date" 
                                           max="<%= java.time.LocalDate.now() %>">
                                </div>
                                <button type="submit" class="btn btn-outline-primary w-100">
                                    <i class="fas fa-search me-1"></i>Consulter
                                </button>
                            </form>

                            <div class="d-grid gap-2 mt-3">
                                <a href="compte?action=view&numero=${compte.numeroCompte}" class="btn btn-primary">
                                    <i class="fas fa-arrow-left me-1"></i>Retour au Compte
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>