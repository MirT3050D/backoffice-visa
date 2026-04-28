<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sélectionner le Type de Visa - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .visa-cards-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin: 30px 0;
        }

        .visa-card {
            background: white;
            border: 2px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .visa-card:hover {
            border-color: #0066cc;
            box-shadow: 0 4px 12px rgba(0, 102, 204, 0.2);
        }

        .visa-card.selected {
            border-color: #0066cc;
            background-color: #f0f6ff;
            box-shadow: 0 4px 12px rgba(0, 102, 204, 0.3);
        }

        .visa-card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .visa-card h3 {
            margin: 0;
            color: #0066cc;
            font-size: 18px;
        }

        .visa-checkbox {
            width: 24px;
            height: 24px;
            cursor: pointer;
        }

        .visa-type-badge {
            display: inline-block;
            background-color: #e8f4f8;
            color: #0066cc;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .visa-details {
            margin-bottom: 10px;
            font-size: 14px;
            color: #666;
        }

        .visa-detail-row {
            display: flex;
            justify-content: space-between;
            padding: 5px 0;
            border-bottom: 1px solid #eee;
        }

        .visa-detail-row:last-child {
            border-bottom: none;
        }

        .visa-detail-label {
            font-weight: 600;
            color: #333;
        }

        .visa-detail-value {
            color: #666;
        }

        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            margin-top: 20px;
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

        .btn-primary:disabled {
            background-color: #ccc;
            cursor: not-allowed;
        }

        .btn-secondary {
            background-color: #666;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #555;
        }

        .error {
            color: #cc0000;
            font-size: 14px;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffeeee;
            border-radius: 4px;
        }

        .success-message {
            background-color: #f0fff0;
            border: 1px solid #90ee90;
            color: #008000;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        input[type="hidden"] {
            display: none;
        }
    </style>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Sélectionner le Type de Visa à Attacher</h1>
            <p class="progress-indicator">Etape 2/3 : Sélection du Visa</p>
        </div>

        <c:if test="${not empty passeportData}">
            <div class="success-message">
                ✓ Passeport créé avec succès pour <strong>${passeportData.prenom} ${passeportData.nom}</strong>
            </div>
        </c:if>

        <div class="form-container">
            <form method="POST" action="/demande-visa/attach-visa" id="visaSelectionForm">
                <p style="color: #666; margin-bottom: 20px;">
                    Sélectionnez un type de visa à attacher à ce passeport. Les données ci-dessous sont des exemples statiques.
                </p>

                <div class="visa-cards-grid" id="visaCardsContainer">
                    <!-- Visa 1: Investisseur -->
                    <div class="visa-card" onclick="selectCard(this, 1)">
                        <div class="visa-card-header">
                            <h3>Investisseur</h3>
                            <input type="checkbox" name="selectedVisa" value="1" class="visa-checkbox" />
                        </div>
                        <span class="visa-type-badge">Type: Travailleur/Commerce</span>
                        <div class="visa-details">
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Numéro Visa:</span>
                                <span class="visa-detail-value">VIS-INV-2024-001</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'entrée:</span>
                                <span class="visa-detail-value">01/06/2024</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'expiration:</span>
                                <span class="visa-detail-value">01/06/2027</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Pays de destination:</span>
                                <span class="visa-detail-value">Madagascar</span>
                            </div>
                        </div>
                    </div>

                    <!-- Visa 2: Travailleur -->
                    <div class="visa-card" onclick="selectCard(this, 2)">
                        <div class="visa-card-header">
                            <h3>Travailleur</h3>
                            <input type="checkbox" name="selectedVisa" value="2" class="visa-checkbox" />
                        </div>
                        <span class="visa-type-badge">Type: Emploi/Salariat</span>
                        <div class="visa-details">
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Numéro Visa:</span>
                                <span class="visa-detail-value">VIS-WORK-2024-002</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'entrée:</span>
                                <span class="visa-detail-value">15/05/2024</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'expiration:</span>
                                <span class="visa-detail-value">15/05/2025</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Employeur:</span>
                                <span class="visa-detail-value">Tech Solutions Ltd</span>
                            </div>
                        </div>
                    </div>

                    <!-- Visa 3: Regroupement Familial -->
                    <div class="visa-card" onclick="selectCard(this, 3)">
                        <div class="visa-card-header">
                            <h3>Regroupement Familial</h3>
                            <input type="checkbox" name="selectedVisa" value="3" class="visa-checkbox" />
                        </div>
                        <span class="visa-type-badge">Type: Famille/Reunion</span>
                        <div class="visa-details">
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Numéro Visa:</span>
                                <span class="visa-detail-value">VIS-FAM-2024-003</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'entrée:</span>
                                <span class="visa-detail-value">20/04/2024</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'expiration:</span>
                                <span class="visa-detail-value">20/04/2026</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Lien familial:</span>
                                <span class="visa-detail-value">Conjoint</span>
                            </div>
                        </div>
                    </div>

                    <!-- Visa 4: Nouveau titre -->
                    <div class="visa-card" onclick="selectCard(this, 4)">
                        <div class="visa-card-header">
                            <h3>Nouveau Titre</h3>
                            <input type="checkbox" name="selectedVisa" value="4" class="visa-checkbox" />
                        </div>
                        <span class="visa-type-badge">Type: Renouvellement</span>
                        <div class="visa-details">
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Numéro Visa:</span>
                                <span class="visa-detail-value">VIS-NEW-2024-004</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'entrée:</span>
                                <span class="visa-detail-value">10/03/2024</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Date d'expiration:</span>
                                <span class="visa-detail-value">10/03/2028</span>
                            </div>
                            <div class="visa-detail-row">
                                <span class="visa-detail-label">Statut:</span>
                                <span class="visa-detail-value">En cours de traitement</span>
                            </div>
                        </div>
                    </div>
                </div>

                <input type="hidden" name="typeDemandeId" value="${typeDemandeId}" />
                <input type="hidden" name="passeportNom" value="${passeportData.nom}" />
                <input type="hidden" name="passeportPrenom" value="${passeportData.prenom}" />

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary" id="submitBtn" disabled>Attacher le visa au passeport</button>
                    <a href="/" class="btn btn-secondary" style="text-decoration: none; display: inline-block;">Annuler</a>
                </div>
            </form>
        </div>
    </div>

    <script>
        function selectCard(cardElement, visaId) {
            const allCards = document.querySelectorAll('.visa-card');
            const currentCheckbox = cardElement.querySelector('.visa-checkbox');
            
            // Si cette carte est déjà sélectionnée, la désélectionner
            if (cardElement.classList.contains('selected')) {
                cardElement.classList.remove('selected');
                currentCheckbox.checked = false;
            } else {
                // Désélectionner toutes les autres cartes
                allCards.forEach(card => {
                    card.classList.remove('selected');
                    card.querySelector('.visa-checkbox').checked = false;
                });
                
                // Sélectionner la carte actuelle
                cardElement.classList.add('selected');
                currentCheckbox.checked = true;
            }
            
            // Mettre à jour l'état du bouton submit
            updateSubmitButton();
        }

        function updateSubmitButton() {
            const submitBtn = document.getElementById('submitBtn');
            const anyChecked = document.querySelector('input[name="selectedVisa"]:checked');
            submitBtn.disabled = !anyChecked;
        }

        // Initialiser les écouteurs d'événements
        document.addEventListener('DOMContentLoaded', function() {
            const checkboxes = document.querySelectorAll('.visa-checkbox');
            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', updateSubmitButton);
            });
        });
    </script>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
