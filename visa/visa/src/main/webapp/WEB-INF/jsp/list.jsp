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

        .status-cell {
            display: flex;
            align-items: flex-start;
            gap: 0.75rem;
            flex-wrap: wrap;
        }

        .status-current {
            font-weight: 600;
            color: #1f2937;
        }

        .status-history {
            border: 1px solid #e2e8f0;
            border-radius: 0.5rem;
            padding: 0.35rem 0.75rem;
            background: #fff;
            font-size: 0.85rem;
            box-shadow: 0 2px 10px rgba(15, 23, 42, 0.06);
        }

        .status-history summary {
            cursor: pointer;
            list-style: none;
            display: inline-flex;
            align-items: center;
            gap: 0.35rem;
            color: #0f172a;
            font-weight: 600;
        }

        .status-history summary::-webkit-details-marker {
            display: none;
        }

        .status-history summary::after {
            content: "▼";
            font-size: 0.6rem;
            color: #64748b;
            transform: translateY(-1px);
        }

        .status-history[open] summary::after {
            content: "▲";
        }

        .status-history-list {
            margin-top: 0.5rem;
            display: grid;
            gap: 0.35rem;
            min-width: 200px;
            padding-top: 0.5rem;
            border-top: 1px solid #e2e8f0;
        }

        .status-history-item {
            display: flex;
            justify-content: space-between;
            gap: 0.75rem;
            font-size: 0.85rem;
        }

        .status-history-date {
            color: #64748b;
            white-space: nowrap;
        }

        .qr-code-cell {
            cursor: pointer;
            text-align: center;
        }

        .qr-code-cell img {
            transition: transform 0.2s ease;
        }

        .qr-code-cell img:hover {
            transform: scale(1.15);
        }

        .qr-modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.85);
            align-items: center;
            justify-content: center;
        }

        .qr-modal.active {
            display: flex;
        }

        .qr-modal-content {
            background-color: white;
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.4);
            text-align: center;
            position: relative;
        }

        .qr-modal-content img {
            max-width: 500px;
            max-height: 500px;
            border: 3px solid #4CAF50;
            padding: 1rem;
        }

        .qr-modal-close {
            position: absolute;
            top: 1rem;
            right: 1.5rem;
            font-size: 2.5rem;
            font-weight: bold;
            color: white;
            cursor: pointer;
            background: none;
            border: none;
            padding: 0;
            line-height: 1;
            transition: color 0.2s ease;
        }

        .qr-modal-close:hover {
            color: #ddd;
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
                    <h1>Liste des Demandes de Visa</h1>
                    <p class="subtitle">Toutes les demandes enregistrees</p>
                </div>

                <c:if test="${not empty successMessage}">
                    <div class="flash-box flash-success">${successMessage}</div>
                </c:if>
                <c:if test="${not empty errorMessage}">
                    <div class="flash-box flash-error">${errorMessage}</div>
                </c:if>

                <div class="search-section">
                    <div class="search-wrapper">
                        <svg class="search-icon" viewBox="0 0 24 24" aria-hidden="true">
                            <path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0016 9.5 6.5 6.5 0 109.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
                        </svg>
                        <input type="text" id="searchInput" class="search-input" placeholder="Rechercher par ID, date, type...">
                    </div>
                </div>

                <div class="table-wrapper">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Date Demande</th>
                                <th>Type Demande</th>
                                <th>Type Visa</th>
                                <th>Statut</th>
                                <th>QR Code</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="demandesTableBody">
                            <c:choose>
                                <c:when test="${empty demandes}">
                                    <tr>
                                        <td colspan="7" class="table-empty">Aucune demande enregistree pour le moment.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="demande" items="${demandes}">
                                        <tr class="clickable-row" data-href="${pageContext.request.contextPath}/list/${demande.id}">
                                            <td>${demande.id}</td>
                                            <td>${demande.dateDemande}</td>
                                            <td>${demande.typeDemandeVisa.label}</td>
                                            <td>${demande.typeVisa.label}</td>
                                            <td>
                                                <div class="status-cell">
                                                    <span class="status-current">${statutLabels[demande.id]}</span>
                                                    <details class="status-history">
                                                        <summary>Historique</summary>
                                                        <div class="status-history-list">
                                                            <c:choose>
                                                                <c:when test="${empty statutHistory[demande.id]}">
                                                                    <div class="status-history-item">
                                                                        <span>Aucun historique</span>
                                                                    </div>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach var="statut" items="${statutHistory[demande.id]}">
                                                                        <div class="status-history-item">
                                                                            <span>${statut.label}</span>
                                                                            <span class="status-history-date">${statut.date}</span>
                                                                        </div>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </details>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="qr-code-cell" onclick="openQrModal(${demande.id}, event)">
                                                    <img src="${pageContext.request.contextPath}/qr/${demande.id}" alt="QR Code" width="50" height="50" style="border: 1px solid #ddd; padding: 2px;">
                                                </div>
                                            </td>
                                            <td>
                                                <div class="row-actions">
                                                    <a class="row-action row-action-edit" href="${pageContext.request.contextPath}/demande/${demande.id}/scan" title="Scanner">
                                                        <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                                            <path d="M4 4h16v4H4V4zm2 6h12v8H6v-8zm2 2v4h8v-4H8zm9-6h3v2h-3V6z"/>
                                                        </svg>
                                                    </a>
                                                    <a class="row-action row-action-edit" href="${pageContext.request.contextPath}/demande/${demande.id}/signature" title="Signature">
                                                        <svg viewBox="0 0 24 24" aria-hidden="true" focusable="false">
                                                            <path d="M12.44 3.73l-8.2 8.2v2.83h2.83l8.2-8.2-2.83-2.83zm2.12-2.12a.996.996 0 0 1 1.41 0l1.41 1.41c.39.39.39 1.02 0 1.41l-1.42 1.42-2.83-2.83 1.43-1.41zm-11.56 18.39h16v2h-16v-2z"/>
                                                        </svg>
                                                    </a>
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
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <!-- QR Code Modal -->
    <div id="qrModal" class="qr-modal">
        <button class="qr-modal-close" onclick="closeQrModal()">&times;</button>
        <div class="qr-modal-content">
            <img id="qrModalImage" src="" alt="QR Code">
        </div>
    </div>

    <script>
        // QR Code Modal Functions
        function openQrModal(demandeId, event) {
            event.stopPropagation();
            var modal = document.getElementById('qrModal');
            var modalImage = document.getElementById('qrModalImage');
            var contextPath = '${pageContext.request.contextPath}';
            modalImage.src = contextPath + '/qr/' + demandeId;
            modal.classList.add('active');
        }

        function closeQrModal() {
            var modal = document.getElementById('qrModal');
            modal.classList.remove('active');
        }

        // Close modal when clicking outside the content
        document.addEventListener('DOMContentLoaded', function() {
            var modal = document.getElementById('qrModal');
            modal.addEventListener('click', function(event) {
                if (event.target === this) {
                    closeQrModal();
                }
            });
        });

        // Close modal on ESC key
        document.addEventListener('keydown', function(event) {
            if (event.key === 'Escape') {
                closeQrModal();
            }
        });

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

            // Search functionality
            var searchInput = document.getElementById('searchInput');
            var tableBody = document.getElementById('demandesTableBody');
            var rows = tableBody ? tableBody.getElementsByTagName('tr') : [];

            if (searchInput && tableBody) {
                searchInput.addEventListener('keyup', function() {
                    var filter = searchInput.value.toLowerCase().trim();
                    
                    for (var i = 0; i < rows.length; i++) {
                        var cells = rows[i].getElementsByTagName('td');
                        var match = false;
                        
                        // Skip last column (actions)
                        for (var j = 0; j < cells.length - 1; j++) {
                            if (cells[j].innerText.toLowerCase().indexOf(filter) > -1) {
                                match = true;
                                break;
                            }
                        }
                        
                        rows[i].style.display = match ? "" : "none";
                    }
                });
            }

            // Clickable rows
            var clickableRows = document.querySelectorAll('.clickable-row');
            clickableRows.forEach(function (row) {
                row.addEventListener('click', function () {
                    var href = row.getAttribute('data-href');
                    if (href) {
                        window.location.href = href;
                    }
                });
            });

            // Prevent action clicks from triggering row click
            var actionElements = document.querySelectorAll('.row-action, .delete-form, .status-history, .status-history summary');
            actionElements.forEach(function (element) {
                element.addEventListener('click', function (event) {
                    event.stopPropagation();
                });
                element.addEventListener('submit', function (event) {
                    event.stopPropagation();
                });
            });
        })();
    </script>




    <jsp:include page="components/footer.jsp" />
</body>
</html>
