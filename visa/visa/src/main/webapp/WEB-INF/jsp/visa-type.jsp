<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Type de Visa - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Sélectionner le Type de Visa</h1>
            <p class="progress-indicator">Etape 1/3 : Choix du Type de Visa</p>
        </div>

        <div class="cards-grid">
            <c:forEach var="typeVisa" items="${typesVisa}">
                <div class="card selectable">
                    <div class="card-header bg-info">
                        <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                            <path d="M20 13H4c-.55 0-1 .45-1 1v6c0 .55.45 1 1 1h16c.55 0 1-.45 1-1v-6c0-.55-.45-1-1-1zM7 19c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zM20 3H4c-.55 0-1 .45-1 1v6c0 .55.45 1 1 1h16c.55 0 1-.45 1-1V4c0-.55-.45-1-1-1zm-3 6h-2V5h2v4z"/>
                        </svg>
                    </div>
                    <div class="card-body">
                        <h2><c:out value="${typeVisa.label}"/></h2>
                        <p>Champs adaptes automatiquement selon ce type de visa</p>
                    </div>
                    <div class="card-footer">
                        <a href="${pageContext.request.contextPath}/demande-visa/visa-form?typeVisaId=${typeVisa.id}&type_demande_id=${typeDemandeId}" class="btn btn-primary">Continuer</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
