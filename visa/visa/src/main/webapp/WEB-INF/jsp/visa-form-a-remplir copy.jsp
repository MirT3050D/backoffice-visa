<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visa - Formulaire de Demande</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Demande de Visa</h1>
            <p class="progress-indicator">Etape 2/3 : Formulaire de Demande</p>
        </div>

        <form id="visaForm" class="visa-form" method="POST" action="${pageContext.request.contextPath}/demande-visa/creer">
            <input type="hidden" name="typeVisaId" value="${typeVisaId}" />
            <input type="hidden" name="typeDemandeId" value="${typeDemandeId}" />
            
            <!-- SECTION 1: ETAT CIVIL -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[IDENTITE]</span>
                    Etat Civil
                </h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom *</label>
                        <input type="text" id="nom" name="nom" required>
                    </div>
                    <div class="form-group">
                        <label for="prenoms">Prénoms *</label>
                        <input type="text" id="prenoms" name="prenom" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nomJeuneFille">Nom de Jeune Fille</label>
                        <input type="text" id="nomJeuneFille" name="nomJeuneFille">
                    </div>
                    <div class="form-group">
                        <label for="dateNaissance">Date de Naissance *</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="lieuNaissance">Lieu de Naissance *</label>
                        <input type="text" id="lieuNaissance" name="lieuNaissance" required>
                    </div>
                    <div class="form-group">
                        <label for="nationaliteId">Nationalité *</label>
                        <select id="nationaliteId" name="nationaliteId" required>
                            <option value="">Sélectionner</option>
                            <c:forEach var="na" items="${nationalites}">
                                <option value="${na.id}">${na.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Adresse Email *</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="telephone">Numéro de Téléphone *</label>
                        <input type="tel" id="telephone" name="numeroTelephone" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="adresseMada">Adresse à Madagascar *</label>
                        <input type="text" id="adresseMada" name="adresseMada" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="situationFamilialeId">Situation Familiale *</label>
                        <select id="situationFamilialeId" name="situationFamilialeId" required>
                            <option value="">Sélectionner</option>
                            <c:forEach var="sit" items="${situationsFamiliales}">
                                <option value="${sit.id}">${sit.label}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <!-- SECTION 2: PASSEPORT -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DOCUMENT]</span>
                    Passeport
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="numPasseport">Numéro de Passeport *</label>
                        <input type="text" id="numPasseport" name="numeroPasseport" required>
                    </div>
                    <div class="form-group">
                        <label for="dateDelivrance">Date de Délivrance *</label>
                        <input type="date" id="dateDelivrance" name="dateDelivrancePasseport" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateExpiration">Date d'Expiration *</label>
                        <input type="date" id="dateExpiration" name="dateExpirationPasseport" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 3: VISA TRANSFORMABLE -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[VISA]</span>
                    Visa Transformable
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="visaTranNumPasseport">Numéro de Passeport (Visa) *</label>
                        <input type="text" id="visaTranNumPasseport" name="visaTranNumPasseport" required>
                    </div>
                    <div class="form-group">
                        <label for="visaTranDateDelivrance">Date de Délivrance *</label>
                        <input type="date" id="visaTranDateDelivrance" name="visaTranDateDelivrance" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="visaTranDateExpiration">Date d'Expiration *</label>
                        <input type="date" id="visaTranDateExpiration" name="visaTranDateExpiration" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 4: CHAMPS COMMUNS (DOSSIER) -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DOSSIER]</span>
                    Champs Communs
                </h2>

                <div class="form-row checkbox-grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px;">
                    <c:forEach var="champ" items="${champsCommuns}">
                        <div class="form-group checkbox-group" style="margin-bottom: 0; display: flex; align-items: center; flex-direction: row; gap: 10px;">
                            <input type="checkbox" id="commune_${champ.id}" name="champsCommunsCoches" value="${champ.id}" style="width: auto; margin: 0;">
                            <label for="commune_${champ.id}" style="margin: 0; font-weight: normal; cursor: pointer;">
                                <c:out value="${champ.label}" />
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- SECTION 5: CHAMPS SPECIFIQUES (DOSSIER) -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DOSSIER SPECIFIQUE]</span>
                    Champs Spécifiques
                </h2>

                <div class="form-row checkbox-grid" style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-bottom: 20px;">
                    <c:forEach var="champ" items="${champsSpecifiques}">
                        <div class="form-group checkbox-group" style="margin-bottom: 0; display: flex; align-items: center; flex-direction: row; gap: 10px;">
                            <input type="checkbox" id="specifique_${champ.id}" name="champsSpecifiquesCoches" value="${champ.id}" style="width: auto; margin: 0;">
                            <label for="specifique_${champ.id}" style="margin: 0; font-weight: normal; cursor: pointer;">
                                <c:out value="${champ.label}" />
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- SECTION 6: ACCORD ET SOUMISSION -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[CONFIRMATION]</span>
                    Acceptation et Soumission
                </h2>

                <div class="form-row">
                    <div class="form-group checkbox-group full-width">
                        <input type="checkbox" id="veracite" name="veracite" required>
                        <label for="veracite">
                            Je certifie l'exactitude et la complétude de toutes les informations fournies ci-dessus *
                        </label>
                    </div>
                </div>
            </div>

            <!-- Boutons de Navigation -->
            <div class="form-navigation">
                <a href="${pageContext.request.contextPath}/demande-visa/visa-type" class="btn btn-secondary">Retour</a>
                <button type="submit" class="btn btn-primary">Soumettre la Demande</button>
            </div>
        </form>
    </div>

    <jsp:include page="components/footer.jsp" />

    <script>
        document.getElementById('visaForm').addEventListener('submit', function(e) {
            console.log('Formulaire soumis');
        });
    </script>
</body>
</html>
