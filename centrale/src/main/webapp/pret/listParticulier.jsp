<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Particuliers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-md-12">
                <h2 class="text-center mb-4">Liste des Particuliers</h2>
                
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Prénom</th>
                                <th>Email</th>
                                <th>Téléphone</th>
                                <th>Date de Naissance</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="particulier" items="${particuliers}">
                                <tr>
                                    <td>${particulier.id}</td>
                                    <td>${particulier.nom}</td>
                                    <td>${particulier.prenom}</td>
                                    <td>${particulier.email}</td>
                                    <td>${particulier.telephone}</td>
                                    <td>${particulier.dateNaissance}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="text-center mt-4">
                    <a href="dashboardPret" class="btn btn-primary">Retour au Tableau de Bord</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>