<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Detail Demande</title>
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
                    <h1>Detail de la Demande #${demande.id}</h1>
                    <p class="subtitle">Consultation des informations de la demande</p>
                </div>

                <c:if test="${not empty successMessage}">
                    <div class="flash-box flash-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="flash-box flash-error">${errorMessage}</div>
                </c:if>

                <section class="detail-card">
                    <h2>Informations Demande</h2>
                    <div class="detail-grid">
                        <div class="detail-item"><span>ID</span><strong>${demande.id}</strong></div>
                        <div class="detail-item"><span>Date de demande</span><strong>${demande.date_demande}</strong></div>
                        <div class="detail-item"><span>Type de demande</span><strong>${demande.type_demande_visa.label}</strong></div>
                        <div class="detail-item"><span>Type de visa</span><strong>${demande.type_visa.label}</strong></div>
                    </div>
                </section>

                <section class="detail-card">
                    <h2>Etat Civil</h2>
                    <div class="detail-grid">
                        <div class="detail-item"><span>Nom</span><strong>${demande.passeport.etatCivil.nom}</strong></div>
                        <div class="detail-item"><span>Prenom</span><strong>${demande.passeport.etatCivil.prenom}</strong></div>
                        <div class="detail-item"><span>Email</span><strong>${demande.passeport.etatCivil.email}</strong></div>
                        <div class="detail-item"><span>Telephone</span><strong>${demande.passeport.etatCivil.numero_telephone}</strong></div>
                        <div class="detail-item"><span>Nationalite</span><strong>${demande.passeport.etatCivil.nationalite.label}</strong></div>
                        <div class="detail-item"><span>Situation familiale</span><strong>${demande.passeport.etatCivil.situation_familiale.label}</strong></div>
                    </div>
                </section>

                <section class="detail-card">
                    <h2>Passeport</h2>
                    <div class="detail-grid">
                        <div class="detail-item"><span>Numero passeport</span><strong>${demande.passeport.numero_passport}</strong></div>
                        <div class="detail-item"><span>Date delivrance</span><strong>${demande.passeport.date_delivrance}</strong></div>
                        <div class="detail-item"><span>Date expiration</span><strong>${demande.passeport.date_expiration}</strong></div>
                    </div>
                </section>

                <section class="detail-card">
                    <h2>Champs Communs</h2>
                    <div class="check-list">
                        <c:choose>
                            <c:when test="${empty dossiersCommuns}">
                                <p class="check-empty">Aucun champ commun trouve.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="dossier" items="${dossiersCommuns}">
                                    <div class="check-item">
                                        <span class="check-label">${dossier.champFournirCommune.label}</span>
                                        <span class="check-badge ${dossier.estCoche ? 'checked' : 'unchecked'}">
                                            ${dossier.estCoche ? 'Coche' : 'Non coche'}
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>

                <section class="detail-card">
                    <h2>Champs Specifiques</h2>
                    <div class="check-list">
                        <c:choose>
                            <c:when test="${empty dossiersSpecifiques}">
                                <p class="check-empty">Aucun champ specifique trouve.</p>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="dossier" items="${dossiersSpecifiques}">
                                    <div class="check-item">
                                        <span class="check-label">${dossier.champFournirSpecifique.label}</span>
                                        <span class="check-badge ${dossier.estCoche ? 'checked' : 'unchecked'}">
                                            ${dossier.estCoche ? 'Coche' : 'Non coche'}
                                        </span>
                                    </div>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </section>

                <div class="detail-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/list">Retour a la liste</a>
                    <div class="row-actions">
                        <a class="row-action row-action-edit" href="${pageContext.request.contextPath}/list/${demande.id}/edit" title="Editer">
                            <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                <path d="M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zm2.92 2.33H5v-.92l9.06-9.06.92.92L5.92 19.58zM20.71 7.04a1.003 1.003 0 000-1.42l-2.34-2.34a1.003 1.003 0 00-1.42 0l-1.83 1.83 3.75 3.75 1.84-1.82z"/>
                            </svg>
                        </a>
                        <form method="post" action="${pageContext.request.contextPath}/list/${demande.id}/delete" class="delete-form" onsubmit="return confirm('Supprimer cette demande ?');">
                            <button class="row-action row-action-delete" type="submit" title="Supprimer">
                                <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                    <path d="M6 7h12v2H6V7zm2 3h8l-1 10H9L8 10zm3-6h2l1 1h4v2H6V5h4l1-1z"/>
                                </svg>
                            </button>
                        </form>
                    </div>
                </div>
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
