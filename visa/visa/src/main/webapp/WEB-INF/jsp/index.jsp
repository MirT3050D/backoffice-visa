<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Demande de Sejour</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="welcome-section">
            <h1>Gestion des Demandes de Visa de Séjour</h1>
            <p class="subtitle">Système d'Administration - Backoffice</p>
        </div>

        <div class="cards-grid">
            <c:forEach var="typeDemande" items="${typeDemandes}">
                <div class="card">
                    <div class="card-header bg-primary">
                        <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"/>
                        </svg>
                    </div>
                    <div class="card-body">
                        <h2>${typeDemande.label}</h2>
                        <p>Faire une demande de type ${typeDemande.label}</p>
                    </div>
                    <div class="card-footer">
                        <a href="${pageContext.request.contextPath}/demande-visa/visa-type?type_demande_id=${typeDemande.id}" class="btn btn-primary">Commencer</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="info-section">
            <div class="info-box">
                <h3>Informations Utiles</h3>
                <ul>
                    <li><strong>Delai de traitement:</strong> 15 jours ouvrables</li>
                    <li><strong>Pieces requises:</strong> Passeport valide + justificatifs</li>
                    <li><strong>Frais:</strong> A definir selon le type de visa</li>
                    <li><strong>Support:</strong> contact@visasystem.dz</li>
                </ul>
            </div>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
