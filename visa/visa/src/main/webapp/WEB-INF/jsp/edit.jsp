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
                            <div class="form-group">
                                <label for="typeDemandeId">Type de demande</label>
                                <select id="typeDemandeId" name="typeDemandeId" required>
                                    <option value="">Selectionner</option>
                                    <c:forEach var="td" items="${typesDemande}">
                                        <option value="${td.id}" ${td.id == form.typeDemandeId ? 'selected' : ''}>${td.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="typeVisaId">Type de visa</label>
                                <select id="typeVisaId" name="typeVisaId" required>
                                    <option value="">Selectionner</option>
                                    <c:forEach var="tv" items="${typesVisa}">
                                        <option value="${tv.id}" ${tv.id == form.typeVisaId ? 'selected' : ''}>${tv.label}</option>
                                    </c:forEach>
                                </select>
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
                                <label for="nomJeuneFille">Nom de jeune fille</label>
                                <input type="text" id="nomJeuneFille" name="nomJeuneFille" value="${form.nomJeuneFille}">
                            </div>
                            <div class="form-group">
                                <label for="dateNaissance">Date de naissance</label>
                                <input type="date" id="dateNaissance" name="dateNaissance" value="${form.dateNaissance}" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="lieuNaissance">Lieu de naissance</label>
                                <input type="text" id="lieuNaissance" name="lieuNaissance" value="${form.lieuNaissance}" required>
                            </div>
                            <div class="form-group">
                                <label for="adresseMada">Adresse a Madagascar</label>
                                <input type="text" id="adresseMada" name="adresseMada" value="${form.adresseMada}" required>
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
                        <div class="form-row">
                            <div class="form-group">
                                <label for="nationaliteId">Nationalite</label>
                                <select id="nationaliteId" name="nationaliteId" required>
                                    <option value="">Selectionner</option>
                                    <c:forEach var="na" items="${nationalites}">
                                        <option value="${na.id}" ${na.id == form.nationaliteId ? 'selected' : ''}>${na.label}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="situationFamilialeId">Situation familiale</label>
                                <select id="situationFamilialeId" name="situationFamilialeId" required>
                                    <option value="">Selectionner</option>
                                    <c:forEach var="sit" items="${situationsFamiliales}">
                                        <option value="${sit.id}" ${sit.id == form.situationFamilialeId ? 'selected' : ''}>${sit.label}</option>
                                    </c:forEach>
                                </select>
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

                    <div class="form-section">
                        <h2 class="section-title">Visa transformable</h2>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="visaTranNumPasseport">Numero passeport (visa)</label>
                                <input type="text" id="visaTranNumPasseport" name="visaTranNumPasseport" value="${form.visaTranNumPasseport}" required>
                            </div>
                            <div class="form-group">
                                <label for="visaTranDateDelivrance">Date delivrance (visa)</label>
                                <input type="date" id="visaTranDateDelivrance" name="visaTranDateDelivrance" value="${form.visaTranDateDelivrance}" required>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="form-group">
                                <label for="visaTranDateExpiration">Date expiration (visa)</label>
                                <input type="date" id="visaTranDateExpiration" name="visaTranDateExpiration" value="${form.visaTranDateExpiration}" required>
                            </div>
                        </div>
                    </div>

                    <div class="form-section">
                        <h2 class="section-title">Champs communs (dossier)</h2>
                        <div class="form-row checkbox-grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px;">
                            <c:forEach var="champ" items="${champsCommuns}">
                                <div class="form-group checkbox-group" style="margin-bottom: 0; display: flex; align-items: center; flex-direction: row; gap: 10px;">
                                    <input type="checkbox" id="commune_${champ.id}" name="champsCommunsCoches" value="${champ.id}" style="width: auto; margin: 0;" ${champsCommunsCoches.contains(champ.id) ? 'checked' : ''}>
                                    <label for="commune_${champ.id}" style="margin: 0; font-weight: normal; cursor: pointer;">
                                        <c:out value="${champ.label}" />
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="form-section">
                        <h2 class="section-title">Champs specifiques (dossier)</h2>
                        <div class="form-row checkbox-grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px;">
                            <c:forEach var="champ" items="${champsSpecifiques}">
                                <div class="form-group checkbox-group" style="margin-bottom: 0; display: flex; align-items: center; flex-direction: row; gap: 10px;">
                                    <input type="checkbox" id="specifique_${champ.id}" name="champsSpecifiquesCoches" value="${champ.id}" style="width: auto; margin: 0;" ${champsSpecifiquesCoches.contains(champ.id) ? 'checked' : ''}>
                                    <label for="specifique_${champ.id}" style="margin: 0; font-weight: normal; cursor: pointer;">
                                        <c:out value="${champ.label}" />
                                    </label>
                                </div>
                            </c:forEach>
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
            var typeVisaSelect = document.getElementById('typeVisaId');

            if (!sidebar || !toggle) {
                return;
            }

            toggle.addEventListener('click', function () {
                var isCollapsed = sidebar.classList.toggle('sidebar-collapsed');
                sidebar.classList.toggle('sidebar-expanded', !isCollapsed);
                toggle.setAttribute('aria-expanded', String(!isCollapsed));
                toggle.setAttribute('aria-label', isCollapsed ? 'Afficher la navigation' : 'Masquer la navigation');
            });

            if (typeVisaSelect) {
                typeVisaSelect.addEventListener('change', function () {
                    if (!typeVisaSelect.value) {
                        return;
                    }
                    var baseUrl = '${pageContext.request.contextPath}/list/${demande.id}/edit';
                    window.location.href = baseUrl + '?typeVisaId=' + encodeURIComponent(typeVisaSelect.value);
                });
            }
        })();
    </script>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
