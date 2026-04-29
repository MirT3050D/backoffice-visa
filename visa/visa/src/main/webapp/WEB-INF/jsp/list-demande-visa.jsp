<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Liste des Demandes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .search-section {
            margin-bottom: 2rem;
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        .search-panel {
            display: grid;
            grid-template-columns: 1fr auto;
            gap: 1rem;
            align-items: end;
            margin-bottom: 1.5rem;
            padding: 1.25rem;
            border: 1px solid #e5e5e5;
            border-radius: 0.75rem;
            background-color: #fafafa;
        }

        .search-fields {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }

        .search-field label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #333;
        }

        .search-field select,
        .search-field input {
            width: 100%;
            padding: 0.75rem 1rem;
            border: 1px solid #ddd;
            border-radius: 0.5rem;
            font-size: 1rem;
            background-color: #fff;
        }

        .search-hint {
            grid-column: 1 / -1;
            font-size: 0.9rem;
            color: #666;
        }

        .search-wrapper {
            position: relative;
            display: flex;
            align-items: center;
            flex: 1;
        }

        .search-icon {
            position: absolute;
            left: 1.2rem;
            width: 1.25rem;
            height: 1.25rem;
            fill: #666;
            pointer-events: none;
            z-index: 1;
        }

        .search-input {
            width: 100%;
            padding: 0.875rem 1rem 0.875rem 2.8rem;
            border: 1px solid #ddd;
            border-radius: 0.5rem;
            font-size: 1rem;
            transition: all 0.3s ease;
            background-color: #fff;
            font-family: inherit;
        }

        .search-input:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
            background-color: #fafafa;
        }

        .search-input::placeholder {
            color: #999;
        }

        .btn {
            padding: 0.875rem 1.5rem;
            border: none;
            border-radius: 0.5rem;
            font-size: 1rem;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s ease;
            white-space: nowrap;
        }

        .btn-secondary {
            background-color: #f5f5f5;
            color: #333;
            border: 1px solid #ddd;
        }

        .btn-secondary:hover {
            background-color: #e8e8e8;
            border-color: #bbb;
        }

        .row-arrow {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            padding: 0.5rem;
            cursor: pointer;
            transition: transform 0.2s ease;
            color: #4CAF50;
        }

        .clickable-row:hover .row-arrow {
            transform: translateX(4px);
        }

        .row-arrow svg {
            width: 1.25rem;
            height: 1.25rem;
            fill: currentColor;
        }

        .result-card {
            background: #fff;
            border: 1px solid #e5e5e5;
            border-radius: 0.75rem;
            padding: 1.5rem;
            margin-top: 1.5rem;
        }

        .result-title {
            font-size: 1.1rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: #333;
        }

        .result-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 1rem;
        }

        .result-item span {
            display: block;
            font-size: 0.85rem;
            color: #666;
            margin-bottom: 0.35rem;
        }

        .result-item strong {
            font-size: 1rem;
            color: #222;
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
                    <h1>Recherche de Visa pour Duplicata</h1>
                    <c:choose>
                        <c:when test="${typeDemandeId == 2}">
                            <p class="subtitle">Duplicata avec donnees anterieures</p>
                        </c:when>
                        <c:when test="${typeDemandeId == 3}">
                            <p class="subtitle">Transfert avec donnees anterieures</p>
                        </c:when>
                        <c:otherwise>
                            <p class="subtitle">Rechercher un visa par reference</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${not empty successMessage}">
                    <div class="flash-box flash-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="flash-box flash-error">${errorMessage}</div>
                </c:if>

                <form class="search-panel" action="/demande-visa/recherche-duplicata" method="GET">
                    <div class="search-fields">
                        <div class="search-field">
                            <label for="rechercheType">Type de recherche</label>
                            <select id="rechercheType" name="rechercheType">
                                <option value="demande" ${searchType == 'demande' ? 'selected' : ''}>Reference demande</option>
                                <option value="passeport" ${searchType == 'passeport' ? 'selected' : ''}>Reference passeport</option>
                            </select>
                        </div>
                        <div class="search-field">
                            <label for="rechercheValeur">Reference</label>
                            <input type="text" id="rechercheValeur" name="rechercheValeur" placeholder="Ex: 15 ou P123456" value="${searchValue}" required>
                        </div>
                        <p class="search-hint">Pour la recherche par passeport, la reference est verifiee sur le passeport le plus recent lie au visa (historique).</p>
                    </div>
                    <div class="btn-group" style="margin-top: 0; justify-content: flex-start;">
                        <button type="submit" class="btn btn-secondary">Rechercher</button>
                        <c:choose>
                            <c:when test="${typeDemandeId == 3}">
                                <a href="/demande-visa/visa-type?type_demande_id=3" class="btn btn-secondary">Sans donnees anterieures</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/demande-visa/visa-type?type_demande_id=2" class="btn btn-secondary">Sans donnees anterieures</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <input type="hidden" name="type_demande_id" value="${typeDemandeId}" />
                </form>

                <c:choose>
                    <c:when test="${not empty visaResult}">
                        <div class="result-card">
                            <div class="result-title">Visa trouve</div>
                            <div class="result-grid">
                                <div class="result-item">
                                    <span>Numero de visa</span>
                                    <strong>${visaResult.numVisa}</strong>
                                </div>
                                <div class="result-item">
                                    <span>Type de visa</span>
                                    <strong>${visaResult.typeVisa.label}</strong>
                                </div>
                                <div class="result-item">
                                    <span>Date de delivrance</span>
                                    <strong>${visaResult.dateDelivrance}</strong>
                                </div>
                                <div class="result-item">
                                    <span>Date d'expiration</span>
                                    <strong>${visaResult.dateExpiration}</strong>
                                </div>
                                <div class="result-item">
                                    <span>Ville de delivrance</span>
                                    <strong>${visaResult.ville.label}</strong>
                                </div>
                                <div class="result-item">
                                    <span>Demande associee</span>
                                    <strong>${visaResult.demandeVisa.id}</strong>
                                </div>
                            </div>
                            <c:if test="${typeDemandeId != 3}">
                                <div class="btn-group" style="justify-content: flex-start; margin-top: 1.5rem;">
                                    <form action="/demande-visa/dupliquer-visa" method="POST">
                                        <input type="hidden" name="visa_id" value="${visaResult.id}" />
                                        <button type="submit" class="btn btn-secondary">Creer la carte resident dupliquee</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:when>
                    <c:when test="${not empty searchValue}">
                        <div class="result-card">
                            <div class="result-title">Resultat de recherche</div>
                            <p class="subtitle" style="margin: 0;">Aucun visa trouve. Verifiez la reference et reessayez.</p>
                        </div>
                    </c:when>
                </c:choose>
            </main>
        </div>
    </div>

    <script>
        (function () {
            // Sidebar toggle
            var sidebar = document.getElementById('navSidebar');
            var toggle = document.getElementById('sidebarToggle');

            if (sidebar && toggle) {
                toggle.addEventListener('click', function () {
                    var isCollapsed = sidebar.classList.toggle('sidebar-collapsed');
                    sidebar.classList.toggle('sidebar-expanded', !isCollapsed);
                    toggle.setAttribute('aria-expanded', String(!isCollapsed));
                    toggle.setAttribute('aria-label', isCollapsed ? 'Afficher la navigation' : 'Masquer la navigation');
                });
            }

        })();
    </script>




    <jsp:include page="components/footer.jsp" />
</body>
</html>
