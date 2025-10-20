<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Créer un Compte Prêt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="text-center">Créer un Compte Prêt</h4>
                    </div>
                    <div class="card-body">
                        <form action="pret/createAccount" method="post">
                            <div class="mb-3">
                                <label for="idParticulier" class="form-label">Sélectionner un Particulier</label>
                                <select class="form-select" id="idParticulier" name="idParticulier" required>
                                    <option value="">Choisir un particulier...</option>
                                    <c:forEach var="particulier" items="${particuliers}">
                                        <option value="${particulier.id}">
                                            ${particulier.nom} ${particulier.prenom} (ID: ${particulier.id})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Créer le Compte</button>
                                <a href="dashboardPret" class="btn btn-secondary">Retour</a>
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