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
        <div class="dashboard-layout">
            <aside id="navSidebar" class="sidebar sidebar-expanded">
                <button id="sidebarToggle" class="sidebar-toggle" type="button" aria-controls="navSidebar" aria-expanded="true" aria-label="Masquer la navigation">
                    <span class="sidebar-toggle-icon" aria-hidden="true">
                        <svg viewBox="0 0 24 24" focusable="false">
                            <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"/>
                        </svg>
                    </span>
                </button>
                <h2 class="sidebar-title">Navigation</h2>
                <nav class="sidebar-nav">
                    <a class="sidebar-link active" href="${pageContext.request.contextPath}/creation">Creation</a>
                    <a class="sidebar-link" href="${pageContext.request.contextPath}/list">List</a>
                </nav>
            </aside>

            <main class="dashboard-content">
                <div class="welcome-section">
                    <h1>Gestion des Demandes de Visa de Sejour</h1>
                    <p class="subtitle">Systeme d'Administration - Backoffice</p>
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
                                <c:choose>
                                    <c:when test="${typeDemande.id == 2}">
                                        <a href="${pageContext.request.contextPath}/demande-visa/list" class="btn btn-primary">Commencer</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/demande-visa/visa-type?type_demande_id=${typeDemande.id}" class="btn btn-primary">Commencer</a>
                                    </c:otherwise>
                                </c:choose>
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
            </main>
        </div>
    </div>

    <c:if test="${not empty successMessage}">
        <div id="successToast" class="toast-success" role="status" aria-live="polite">
            <span class="toast-icon" aria-hidden="true">&#10003;</span>
            <span>${successMessage}</span>
        </div>
        <script>
            setTimeout(function () {
                var toast = document.getElementById('successToast');
                if (toast) {
                    toast.classList.add('hide');
                }
            }, 2800);
        </script>
    </c:if>

    <script>
        (function () {
            var sidebar = document.getElementById('navSidebar');
            var toggle = document.getElementById('sidebarToggle');

            if (!sidebar || !toggle) {
                return;
            }

            toggle.addEventListener('click', function () {
                var isCollapsed = sidebar.classList.toggle('sidebar-collapsed');
                sidebar.classList.toggle('sidebar-expanded', !isCollapsed);
                toggle.setAttribute('aria-expanded', String(!isCollapsed));
                toggle.setAttribute('aria-label', isCollapsed ? 'Afficher la navigation' : 'Masquer la navigation');
            });
        })();
    </script>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
