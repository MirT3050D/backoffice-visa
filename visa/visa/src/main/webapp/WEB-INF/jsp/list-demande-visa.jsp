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
                    <a href="/demande-visa/visa-type?type_demande_id=1" class="btn btn-secondary">Sans données intérieures</a>
                </div>


                <div class="table-wrapper">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Date Demande</th>
                                <th>Type Demande</th>
                                <th>Type Visa</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="demandesTableBody">
                            <c:choose>
                                <c:when test="${empty demandes}">
                                    <tr>
                                        <td colspan="5" class="table-empty">Aucune demande enregistree pour le moment.</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="demande" items="${demandes}">
                                        <tr class="clickable-row" data-href="${pageContext.request.contextPath}/list/${demande.id}">
                                            <td>${demande.id}</td>
                                            <td>${demande.date_demande}</td>
                                            <td>${demande.type_demande_visa.label}</td>
                                            <td>${demande.type_visa.label}</td>
                                            <td>
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
            var actionElements = document.querySelectorAll('.row-action, .delete-form');
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
