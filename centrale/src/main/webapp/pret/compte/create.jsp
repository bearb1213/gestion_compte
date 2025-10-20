<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Créer un Compte Prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4 class="card-title mb-0">Créer un Nouveau Compte Prêt</h4>
                    </div>
                    <div class="card-body">
                        <form action="comptes" method="post">
                            <input type="hidden" name="action" value="create">
                            
                            <div class="mb-3">
                                <label for="idParticulier" class="form-label">Sélectionner un Particulier</label>
                                <select class="form-select" id="idParticulier" name="idParticulier" required>
                                    <option value="">Choisir un particulier...</option>
                                    <c:forEach var="particulier" items="${particuliers}">
                                        <option value="${particulier.id}">
                                            ${particulier.nom} ${particulier.prenom} 
                                            (CIN: ${particulier.cin}, ID: ${particulier.id})
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="form-text">
                                    Sélectionnez le particulier pour lequel créer le compte de prêt.
                                </div>
                            </div>
                            
                            <div class="alert alert-info">
                                <h6>Information :</h6>
                                <ul class="mb-0">
                                    <li>Un numéro de compte unique sera généré automatiquement</li>
                                    <li>Le statut initial du compte sera "Actif"</li>
                                    <li>La date d'ouverture sera la date actuelle</li>
                                </ul>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="comptes" class="btn btn-secondary me-md-2">Annuler</a>
                                <button type="submit" class="btn btn-primary">Créer le Compte</button>
                            </div>
                        </form>
                        
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mt-3">${error}</div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>