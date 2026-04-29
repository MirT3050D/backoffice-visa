<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Saisie Informations du Visa et Dossiers - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            margin-top: 20px;
        }

        .section-title {
            color: #0066cc;
            border-bottom: 2px solid #e8f4f8;
            padding-bottom: 10px;
            margin-bottom: 20px;
            font-size: 1.2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .form-row {
            display: flex;
            gap: 20px;
            margin-bottom: 15px;
        }

        .form-group {
            flex: 1;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #333;
        }

        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .documents-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 15px;
            margin-bottom: 30px;
        }

        .document-item {
            display: flex;
            align-items: flex-start;
            padding: 12px;
            background: #f8f9fa;
            border: 1px solid #e9ecef;
            border-radius: 6px;
            transition: all 0.2s ease;
        }

        .document-item:hover {
            background: #f0f6ff;
            border-color: #cce5ff;
        }

        .document-item input[type="checkbox"] {
            margin-top: 3px;
            margin-right: 12px;
            width: 18px;
            height: 18px;
            cursor: pointer;
        }

        .document-item label {
            margin: 0;
            cursor: pointer;
            font-size: 14px;
            color: #495057;
            flex: 1;
            line-height: 1.4;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
            justify-content: center;
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
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }

        .btn-action {
            background-color: #28a745;
            color: white;
            padding: 6px 12px;
            font-size: 0.9rem;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }

        .btn-action:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Saisie du Visa & Documents Fournis</h1>
            <c:choose>
                <c:when test="${typeDemandeId == 3}">
                    <p class="progress-indicator">Etape 3/4 : Dossiers et ancien visa</p>
                </c:when>
                <c:otherwise>
                    <p class="progress-indicator">Etape 3/3 : Finalisation</p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-container">
            <form action="/demande-visa/finaliser-duplicata" method="POST">
                <input type="hidden" name="typeVisaId" value="${typeVisaId}" />
                <input type="hidden" name="typeDemandeId" value="${typeDemandeId}" />

                <h2 class="section-title">Informations du Visa (Ancien Titre)</h2>
                <div class="form-row">
                    <div class="form-group">
                        <label for="numeroVisa">Numéro de Visa</label>
                        <input type="text" id="numeroVisa" name="ancienNumeroVisa" required placeholder="Ex: VIS-2023-XXXX">
                    </div>
                    <div class="form-group">
                        <label for="dateDelivrance">Date de DÃ©livrance</label>
                        <input type="date" id="dateDelivrance" name="ancienDateDelivrance" required>
                    </div>
                    <div class="form-group">
                        <label for="dateExpiration">Date d'Expiration</label>
                        <input type="date" id="dateExpiration" name="ancienDateExpiration" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="ancienVilleId">Ville de DÃ©livrance</label>
                        <select id="ancienVilleId" name="ancienVilleId" required>
                            <option value="">-- SÃ©lectionnez --</option>
                            <c:forEach items="${villesParPays}" var="entry">
                                <optgroup label="${entry.key}">
                                    <c:forEach items="${entry.value}" var="ville">
                                        <option value="${ville.id}">${ville.label}</option>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                     <div class="form-group">
                        <label for="numeroCarteResident">NumÃ©ro de Carte RÃ©sident (si existante)</label>
                        <input type="text" id="numeroCarteResident" name="ancienNumeroCarteResident" placeholder="Ex: CR-2023-XXXX">
                    </div>
                </div>

                <h2 class="section-title">
                    Pièces à fournir (Dossiers)
                    <button type="button" class="btn-action" onclick="toutCocher()">Tout Sélectionner</button>
                </h2>
                
                <h3 style="font-size: 1rem; color: #555; margin-bottom: 15px;">Dossiers Communs</h3>
                <div class="documents-grid">
                    <c:forEach items="${champsCommuns}" var="champ">
                        <div class="document-item">
                            <input type="checkbox" id="commun_${champ.id}" name="champsCommunsCoches" value="${champ.id}" class="dossier-checkbox">
                            <label for="commun_${champ.id}">${champ.label}</label>
                        </div>
                    </c:forEach>
                </div>

                <c:if test="${not empty champsSpecifiques}">
                    <h3 style="font-size: 1rem; color: #555; margin-bottom: 15px;">Dossiers Spécifiques (Visa sélectionné)</h3>
                    <div class="documents-grid">
                        <c:forEach items="${champsSpecifiques}" var="champ">
                            <div class="document-item">
                                <input type="checkbox" id="specifique_${champ.id}" name="champsSpecifiquesCoches" value="${champ.id}" class="dossier-checkbox">
                                <label for="specifique_${champ.id}">${champ.label}</label>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <div class="btn-group">
                    <c:choose>
                        <c:when test="${typeDemandeId == 3}">
                            <a href="/demande-visa/nouveau-passeport?type_demande_id=${typeDemandeId}&type_visa_id=${typeVisaId}" class="btn btn-primary" style="text-decoration: none;">Continuer vers le nouveau passeport</a>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary">Valider et Créer le Duplicata</button>
                        </c:otherwise>
                    </c:choose>
                    <a href="/demande-visa/select-visa?type_demande_id=${typeDemandeId}" class="btn btn-secondary" style="text-decoration: none;">Retour</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        function toutCocher() {
            const checkboxes = document.querySelectorAll('.dossier-checkbox');
            // Vérifie si la majorité est cochée pour décider si on coche tout ou décoche tout
            let toutEstCoche = true;
            checkboxes.forEach(cb => {
                if (!cb.checked) toutEstCoche = false;
            });

            checkboxes.forEach(cb => {
                cb.checked = !toutEstCoche; // Toggle : Coche tout -> Décoche tout -> Coche tout
            });
            
            // Mise à jour visuelle optionnelle (flash color)
            const btn = document.querySelector('.btn-action');
            if (toutEstCoche) {
                btn.textContent = "Tout Sélectionner";
                btn.style.backgroundColor = "#28a745";
            } else {
                btn.textContent = "Tout Désélectionner";
                btn.style.backgroundColor = "#dc3545";
            }
        }
    </script>

    <jsp:include page="components/footer.jsp" />
</body>
</html>