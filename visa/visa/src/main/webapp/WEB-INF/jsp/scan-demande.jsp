
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Scan Demande</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .scan-list {
            display: grid;
            gap: 1.25rem;
        }

        .scan-card {
            border: 1px solid #e6e6e6;
            border-radius: 12px;
            padding: 1rem 1.25rem;
            background: #fff;
            display: grid;
            gap: 0.75rem;
        }

        .scan-meta {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 1rem;
            flex-wrap: wrap;
        }

        .scan-label {
            font-weight: 600;
            color: #1f2937;
        }

        .scan-status {
            font-size: 0.9rem;
            padding: 0.2rem 0.6rem;
            border-radius: 999px;
            background: #f1f5f9;
            color: #334155;
        }

        .scan-status.ready {
            background: rgba(34, 197, 94, 0.15);
            color: #15803d;
        }

        .scan-status.missing {
            background: rgba(251, 191, 36, 0.15);
            color: #b45309;
        }

        .scan-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 0.75rem;
            align-items: center;
        }

        .scan-actions input[type="file"] {
            max-width: 280px;
        }

        .scan-info {
            padding: 0.75rem 1rem;
            border-radius: 10px;
            background: #f8fafc;
            border: 1px solid #e2e8f0;
        }

        .scan-banner {
            padding: 0.85rem 1rem;
            border-radius: 10px;
            background: rgba(34, 197, 94, 0.15);
            color: #166534;
            border: 1px solid rgba(34, 197, 94, 0.35);
            margin-bottom: 1rem;
        }

        .scan-banner.locked {
            background: rgba(59, 130, 246, 0.15);
            color: #1e40af;
            border-color: rgba(59, 130, 246, 0.35);
        }
    </style>
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
                    <h1>Scan Demande #${demande.id}</h1>
                    <p class="subtitle">Televersement des pieces justificatives</p>
                </div>

                <c:if test="${demande.estVerrouille}">
                    <div class="scan-banner locked">Demande verrouillee. Aucun ajout possible.</div>
                </c:if>

                <c:if test="${not empty successMessage}">
                    <div class="flash-box flash-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="flash-box flash-error">${errorMessage}</div>
                </c:if>

                <div class="scan-info">
                    <strong>Statut dossier:</strong>
                    <c:choose>
                        <c:when test="${isComplet}">Complet</c:when>
                        <c:otherwise>Incomplet</c:otherwise>
                    </c:choose>
                </div>

                <form method="post" action="${pageContext.request.contextPath}/demande/${demande.id}/upload-multi" enctype="multipart/form-data">
                    <section class="scan-list">
                        <c:choose>
                            <c:when test="${empty dossiers}">
                                <div class="scan-card">
                                    <div class="scan-meta">
                                        <span class="scan-label">Aucune piece demandee.</span>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="dossier" items="${dossiers}">
                                    <c:set var="dossierId" value="${dossier.id}" />
                                    <c:choose>
                                        <c:when test="${not empty dossier.champFournirCommune}">
                                            <c:set var="champLabel" value="${dossier.champFournirCommune.label}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="champLabel" value="${dossier.champFournirSpecifique.label}" />
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="scan-card">
                                        <div class="scan-meta">
                                            <span class="scan-label">${champLabel}</span>
                                            <span class="scan-status ${empty dossier.pathFichier ? 'missing' : 'ready'}">
                                                ${empty dossier.pathFichier ? 'Fichier manquant' : 'Fichier present'}
                                            </span>
                                        </div>
                                        <div class="scan-actions">
                                            <c:if test="${not empty dossier.pathFichier}">
                                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/demande/${demande.id}/files/${dossierId}">Telecharger</a>
                                            </c:if>
                                            <input type="file" name="fichier_${dossierId}" ${demande.estVerrouille ? 'disabled' : ''}>
                                            <button class="btn btn-secondary" type="submit" name="singleDossierId" value="${dossierId}" ${demande.estVerrouille ? 'disabled' : ''}>Uploader</button>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </section>

                    <div style="margin-top: 1.25rem; display: flex; gap: 0.75rem; flex-wrap: wrap;">
                        <button class="btn btn-primary" type="submit" ${demande.estVerrouille ? 'disabled' : ''}>Uploader la selection</button>
                    </div>
                </form>

                <form method="post" action="${pageContext.request.contextPath}/demande/${demande.id}/verrouiller" style="margin-top: 1.5rem;">
                    <button class="btn btn-primary" type="submit" onclick="return confirm('Verrouiller la demande ?');" ${demande.estVerrouille ? 'disabled' : ''}>Finaliser le scan</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/list">Retour a la liste</a>
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
