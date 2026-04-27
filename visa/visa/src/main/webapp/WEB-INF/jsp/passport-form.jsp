<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer Passeport - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .form-section {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            border-left: 4px solid #0066cc;
        }

        .form-section h3 {
            color: #0066cc;
            margin-top: 0;
            margin-bottom: 15px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #333;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #0066cc;
            box-shadow: 0 0 5px rgba(0, 102, 204, 0.3);
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .error {
            color: #cc0000;
            font-size: 14px;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffeeee;
            border-radius: 4px;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: #0066cc;
            color: white;
        }

        .btn-primary:hover {
            background-color: #0052a3;
        }

        .btn-secondary {
            background-color: #666;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Créer un Passeport</h1>
            <p class="progress-indicator">Etape 1/3 : Informations du Passeport</p>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form:form method="POST" action="/demande-visa/visa-type" modelAttribute="passeportForm" class="form-container">
            
            <!-- Section Identité (Modèle: EtatCivil) -->
            <div class="form-section">
                <h3>Informations Personnelles</h3>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom<span style="color: red;">*</span></label>
                        <form:input path="nom" id="nom" placeholder="Entrez votre nom" required="true" />
                    </div>
                    <div class="form-group">
                        <label for="prenom">Prénom<span style="color: red;">*</span></label>
                        <form:input path="prenom" id="prenom" placeholder="Entrez votre prénom" required="true" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nom_jeune_fille">Nom de Jeune Fille</label>
                        <form:input path="nom_jeune_fille" id="nom_jeune_fille" placeholder="(optionnel)" />
                    </div>
                    <div class="form-group">
                        <label for="date_naissance">Date de Naissance<span style="color: red;">*</span></label>
                        <form:input path="date_naissance" id="date_naissance" type="date" required="true" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="lieu_naissance">Lieu de Naissance<span style="color: red;">*</span></label>
                    <form:input path="lieu_naissance" id="lieu_naissance" placeholder="Ville/Pays de naissance" required="true" />
                </div>

                <div class="form-group">
                    <label for="adresse_mada">Adresse à Madagascar<span style="color: red;">*</span></label>
                    <form:input path="adresse_mada" id="adresse_mada" placeholder="Adresse complète" required="true" />
                </div>
            </div>

            <!-- Section Contact et Nationalité (Modèle: EtatCivil) -->
            <div class="form-section">
                <h3>Contact et Situation</h3>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Email<span style="color: red;">*</span></label>
                        <form:input path="email" id="email" type="email" placeholder="email@exemple.com" required="true" />
                    </div>
                    <div class="form-group">
                        <label for="numero_telephone">Numéro de Téléphone<span style="color: red;">*</span></label>
                        <form:input path="numero_telephone" id="numero_telephone" placeholder="Ex: +261 XX XXX XXX" required="true" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nationaliteId">Nationalité<span style="color: red;">*</span></label>
                        <form:select path="nationaliteId" id="nationaliteId" required="true">
                            <option value="">-- Sélectionner une nationalité --</option>
                            <c:forEach var="nat" items="${nationalites}">
                                <option value="${nat.id}">${nat.label}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                    <div class="form-group">
                        <label for="situationFamiliale">Situation Familiale<span style="color: red;">*</span></label>
                        <form:select path="situationFamiliale" id="situationFamiliale" required="true">
                            <option value="">-- Sélectionner une situation --</option>
                            <c:forEach var="sit" items="${situationsFamiliales}">
                                <option value="${sit.id}">${sit.label}</option>
                            </c:forEach>
                        </form:select>
                    </div>
                </div>
            </div>

            <!-- Section Passeport (Modèle: Passeport) -->
            <div class="form-section">
                <h3>Informations du Passeport</h3>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="numero_passport">Numéro de Passeport<span style="color: red;">*</span></label>
                        <form:input path="numero_passport" id="numero_passport" placeholder="Ex: AB123456" required="true" />
                    </div>
                    <div class="form-group">
                        <label for="date_delivrance">Date de Délivrance<span style="color: red;">*</span></label>
                        <form:input path="date_delivrance" id="date_delivrance" type="date" required="true" />
                    </div>
                </div>

                <div class="form-group">
                    <label for="date_expiration">Date d'Expiration<span style="color: red;">*</span></label>
                    <form:input path="date_expiration" id="date_expiration" type="date" required="true" />
                </div>
            </div>

            <form:hidden path="typeDemandeId" />

            <div class="btn-group">
                <button type="submit" class="btn btn-primary">Continuer vers la sélection du visa</button>
                <a href="/" class="btn btn-secondary" style="text-decoration: none; display: inline-block;">Annuler</a>
            </div>
        </form:form>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
