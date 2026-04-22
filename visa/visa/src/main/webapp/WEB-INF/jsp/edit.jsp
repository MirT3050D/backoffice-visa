<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Editer Demande</title>
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
                    <a class="sidebar-link" href="${pageContext.request.contextPath}/creation">Creation</a>
                    <a class="sidebar-link active" href="${pageContext.request.contextPath}/list">List</a>
                </nav>
            </aside>

            <main class="dashboard-content">
                <div class="welcome-section">
                    <h1>Editer la Demande #${demande.id}</h1>
                    <p class="subtitle">Mise a jour des informations principales</p>
                </div>

                <form class="visa-form" method="post" action="${pageContext.request.contextPath}/list/${demande.id}/edit">
                    <div class="form-section">
                        <h2 class="section-title">Demande</h2>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="dateDemande">Date de demande</label>
                                <input type="date" id="dateDemande" name="dateDemande" value="${form.dateDemande}" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <h2 class="section-title">Etat civil</h2>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nom">Nom</label>
                                <input type="text" id="nom" name="nom" value="${form.nom}" required>
                            </div>
                            <div class="form-group">
                                <label for="prenom">Prenom</label>
                                <input type="text" id="prenom" name="prenom" value="${form.prenom}" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="email" value="${form.email}">
                            </div>
                            <div class="form-group">
                                <label for="numeroTelephone">Telephone</label>
                                <input type="text" id="numeroTelephone" name="numeroTelephone" value="${form.numeroTelephone}" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <h2 class="section-title">Passeport</h2>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="numeroPasseport">Numero passeport</label>
                                <input type="text" id="numeroPasseport" name="numeroPasseport" value="${form.numeroPasseport}" required>
                            </div>
                            <div class="form-group">
                                <label for="dateDelivrancePasseport">Date delivrance</label>
                                <input type="date" id="dateDelivrancePasseport" name="dateDelivrancePasseport" value="${form.dateDelivrancePasseport}" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="dateExpirationPasseport">Date expiration</label>
                                <input type="date" id="dateExpirationPasseport" name="dateExpirationPasseport" value="${form.dateExpirationPasseport}" required>
                            </div>
                        </div>
                    </div>

                    <div class="detail-actions">
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/list/${demande.id}">Annuler</a>
                        <button class="btn btn-primary" type="submit">Enregistrer</button>
                    </div>
                </form>
            </main>
        </div>
    </div>

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
