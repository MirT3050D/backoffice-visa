<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Formulaire de Demande de Visa</h1>
            <p class="progress-indicator">Etape 1/3 : Informations Personnelles</p>
        </div>

        <form id="visaForm" class="visa-form" method="POST" action="soumettre-demande">
            <!-- SECTION 1: ETAT CIVIL -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[IDENTITE]</span>
                    Etat Civil
                </h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom *</label>
                        <input type="text" id="nom" name="nom" placeholder="Ex: DUPONT" required>
                    </div>
                    <div class="form-group">
                        <label for="prenoms">Prénoms *</label>
                        <input type="text" id="prenoms" name="prenoms" placeholder="Ex: Jean" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nomJeuneFille">Nom de Jeune Fille</label>
                        <input type="text" id="nomJeuneFille" name="nomJeuneFille" placeholder="Si applicable">
                    </div>
                    <div class="form-group">
                        <label for="dateNaissance">Date de Naissance *</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="lieuNaissance">Lieu de Naissance *</label>
                        <input type="text" id="lieuNaissance" name="lieuNaissance" placeholder="Ex: Paris" required>
                    </div>
                    <div class="form-group">
                        <label for="nationalite">Nationalité *</label>
                        <select id="nationalite" name="nationalite" required>
                            <option value="">-- Sélectionnez --</option>
                            <option value="FR">France</option>
                            <option value="BE">Belgique</option>
                            <option value="CH">Suisse</option>
                            <option value="DE">Allemagne</option>
                            <option value="ES">Espagne</option>
                            <option value="IT">Italie</option>
                            <option value="GB">Royaume-Uni</option>
                            <option value="CA">Canada</option>
                            <option value="US">États-Unis</option>
                            <option value="MA">Maroc</option>
                            <option value="TN">Tunisie</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="situationFamiliale">Situation Familiale *</label>
                        <select id="situationFamiliale" name="situationFamiliale" required>
                            <option value="">-- Sélectionnez --</option>
                            <option value="celibataire">Célibataire</option>
                            <option value="marie">Marié(e)</option>
                            <option value="divorce">Divorcé(e)</option>
                            <option value="veuf">Veuf(ve)</option>
                            <option value="pacs">Pacsé(e)</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="adresse">Adresse Complète *</label>
                        <input type="text" id="adresse" name="adresse" placeholder="Rue, numéro, code postal, ville" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="mail">Email *</label>
                        <input type="email" id="mail" name="mail" placeholder="votre.email@exemple.com" required>
                    </div>
                    <div class="form-group">
                        <label for="numTel">Numéro de Téléphone *</label>
                        <input type="tel" id="numTel" name="numTel" placeholder="+33 6 XX XX XX XX" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 2: PASSEPORT -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[PASSEPORT]</span>
                    Informations Passeport
                </h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="numPasseport">Numéro du Passeport *</label>
                        <input type="text" id="numPasseport" name="numPasseport" placeholder="Ex: 12AB34567" required>
                    </div>
                    <div class="form-group">
                        <label for="dateExpiration">Date d'Expiration du Passeport *</label>
                        <input type="date" id="dateExpiration" name="dateExpiration" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateDelivrance">Date de Délivrance du Passeport *</label>
                        <input type="date" id="dateDelivrance" name="dateDelivrance" required>
                    </div>
                </div>

                <div class="alert-info">
                    <strong>Important:</strong> Le passeport doit etre valide pour au moins 6 mois a compter de la demande.
                </div>
            </div>

            <!-- SECTION 3: VISA TRANSFORMABLE -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[VISA]</span>
                    Visa Transformable
                </h2>
                
                <p class="section-subtitle">Si vous avez un visa transformable existant</p>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateEntreeVisa">Date d'Entrée avec Visa *</label>
                        <input type="date" id="dateEntreeVisa" name="dateEntreeVisa" required>
                    </div>
                    <div class="form-group">
                        <label for="dateExpirationVisa">Date d'Expiration du Visa *</label>
                        <input type="date" id="dateExpirationVisa" name="dateExpirationVisa" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateDelivranceVisa">Date de Délivrance du Visa *</label>
                        <input type="date" id="dateDelivranceVisa" name="dateDelivranceVisa" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 4: TYPE DE DEMANDE -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DEMANDE]</span>
                    Type de Demande
                </h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="typeVisa">Type de Visa Demandé *</label>
                        <select id="typeVisa" name="typeVisa" required>
                            <option value="">-- Sélectionnez --</option>
                            <option value="court">Visa Court Séjour (C) - Moins de 90 jours</option>
                        <option value="long">Visa Long Sejour (D) - Plus de 90 jours</option>
                        <option value="etudiant">Visa Etudiant</option>
                        <option value="travail">Visa de Travail</option>
                        <option value="famille">Visa de Regroupement Familial</option>
                        <option value="entrepreneuriat">Visa Entrepreneuriat</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="typeDemande">Motif de la Demande *</label>
                        <select id="typeDemande" name="typeDemande" required>
                            <option value="">-- Sélectionnez --</option>
                            <option value="premiere">Première demande</option>
                            <option value="renouvellement">Renouvellement</option>
                            <option value="extension">Extension de séjour</option>
                            <option value="modification">Modification de statut</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="villeDestinataire">Ville d'Accueil Prévue *</label>
                        <select id="villeDestinataire" name="villeDestinataire" required>
                            <option value="">-- Sélectionnez --</option>
                            <option value="paris">Paris</option>
                            <option value="lyon">Lyon</option>
                            <option value="marseille">Marseille</option>
                            <option value="toulouse">Toulouse</option>
                            <option value="nice">Nice</option>
                            <option value="nantes">Nantes</option>
                            <option value="bordeaux">Bordeaux</option>
                            <option value="lille">Lille</option>
                            <option value="autre">Autre</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dureeSejourMois">Durée de Séjour Prévue (mois) *</label>
                        <input type="number" id="dureeSejourMois" name="dureeSejourMois" min="1" max="24" placeholder="Ex: 6" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 5: PIÈCES JUSTIFICATIVES -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DOCUMENTS]</span>
                    Pièces Justificatives à Fournir
                </h2>
                
                <div class="checkbox-group">
                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="passeport" checked disabled>
                        <span class="checkbox-label">Copie du passeport (obligatoire)</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="assurance">
                        <span class="checkbox-label">Attestation d'assurance maladie</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="revenus">
                        <span class="checkbox-label">Justificatifs de revenus (3 derniers mois)</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="hebergement">
                        <span class="checkbox-label">Justificatif d'hébergement</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="employeur">
                        <span class="checkbox-label">Lettre de l'employeur (le cas échéant)</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="invitation">
                        <span class="checkbox-label">Lettre d'invitation (si applicable)</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="casier">
                        <span class="checkbox-label">Casier judiciaire / Extrait de casier</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="mariage">
                        <span class="checkbox-label">Certificat de mariage (si applicable)</span>
                    </label>

                    <label class="checkbox-item">
                        <input type="checkbox" name="pieces" value="diplome">
                        <span class="checkbox-label">Diplômes/Certifications (le cas échéant)</span>
                    </label>
                </div>
            </div>

            <!-- FORMULAIRE DE CONSENTEMENT -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[CONFIRMATION]</span>
                    Confirmation et Validation
                </h2>
                
                <div class="agreement-box">
                    <label class="checkbox-item agreement-checkbox">
                        <input type="checkbox" name="confirmation" id="confirmation" required>
                        <span class="checkbox-label">Je certifie que toutes les informations fournies sont exactes et complètes. Je suis conscient que toute fausse déclaration peut entraîner le refus de ma demande et des poursuites judiciaires.</span>
                    </label>
                </div>

                <div class="agreement-box">
                    <label class="checkbox-item agreement-checkbox">
                        <input type="checkbox" name="rgpd" id="rgpd" required>
                        <span class="checkbox-label">J'accepte que mes données personnelles soient traitées selon la RGPD pour le traitement de ma demande de visa.</span>
                    </label>
                </div>
            </div>

            <!-- BOUTONS D'ACTION -->
            <div class="form-actions">
                <button type="reset" class="btn btn-secondary">Réinitialiser</button>
                <button type="submit" class="btn btn-primary btn-large">Soumettre la Demande</button>
            </div>

            <!-- CHAMPS STATIQUES POUR DÉMO -->
            <input type="hidden" name="dateCreation" value="2026-04-21">
            <input type="hidden" name="statut" value="EN_ATTENTE">
        </form>
    </div>

    <jsp:include page="components/footer.jsp" />

    <script>
        // Validation simple côté client
        document.getElementById('visaForm').addEventListener('submit', function(e) {
            const mail = document.getElementById('mail').value;
            const numTel = document.getElementById('numTel').value;
            
            // Validation email
            if (!mail.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
                alert('Email invalide');
                e.preventDefault();
                return false;
            }
            
            // Validation durée vs dates visa
            const dateExpiration = new Date(document.getElementById('dateExpiration').value);
            const dateEntreeVisa = new Date(document.getElementById('dateEntreeVisa').value);
            
            if (dateExpiration <= dateEntreeVisa) {
                alert('La date d\'expiration du passeport doit être après la date d\'entrée');
                e.preventDefault();
                return false;
            }
        });
    </script>
</body>
</html>
