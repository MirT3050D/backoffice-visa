<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visa Travailleur - Formulaire de Demande</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Demande de Visa Travailleur</h1>
            <p class="progress-indicator">Etape 2/3 : Formulaire de Demande</p>
        </div>

        <form id="visaTravailleurForm" class="visa-form" method="POST" action="visa-recap">
            
            <!-- SECTION 1: INFORMATIONS PERSONNELLES -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[IDENTITE]</span>
                    Informations Personnelles
                </h2>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="nom">Nom *</label>
                        <input type="text" id="nom" name="nom" value="DUPONT" placeholder="Ex: DUPONT" required>
                    </div>
                    <div class="form-group">
                        <label for="prenoms">Prénoms *</label>
                        <input type="text" id="prenoms" name="prenoms" value="Jean" placeholder="Ex: Jean" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="nomJeuneFille">Nom de Jeune Fille</label>
                        <input type="text" id="nomJeuneFille" name="nomJeuneFille" placeholder="Si applicable">
                    </div>
                    <div class="form-group">
                        <label for="dateNaissance">Date de Naissance *</label>
                        <input type="date" id="dateNaissance" name="dateNaissance" value="1990-03-15" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="lieuNaissance">Lieu de Naissance *</label>
                        <input type="text" id="lieuNaissance" name="lieuNaissance" value="Paris" placeholder="Ex: Paris" required>
                    </div>
                    <div class="form-group">
                        <label for="nationalite">Nationalité *</label>
                        <select id="nationalite" name="nationalite" required>
                            <option value="">--Sélectionner--</option>
                            <option value="francais" selected>Français</option>
                            <option value="belge">Belge</option>
                            <option value="suisse">Suisse</option>
                            <option value="canadien">Canadien</option>
                            <option value="autre">Autre</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="email">Adresse Email *</label>
                        <input type="email" id="email" name="email" value="jean.dupont@email.com" placeholder="exemple@email.com" required>
                    </div>
                    <div class="form-group">
                        <label for="telephone">Numéro de Téléphone *</label>
                        <input type="tel" id="telephone" name="telephone" value="+33 6 12 34 56 78" placeholder="+33 6 XX XX XX XX" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="adresse">Adresse de Résidence *</label>
                        <input type="text" id="adresse" name="adresse" value="123 Rue de la Paix, 75000 Paris" placeholder="Ex: 123 Rue de la Paix, 75000 Paris" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="situationFamiliale">Situation Familiale *</label>
                        <select id="situationFamiliale" name="situationFamiliale" required>
                            <option value="">--Sélectionner--</option>
                            <option value="celibataire">Célibataire</option>
                            <option value="marie" selected>Marié(e)</option>
                            <option value="divorce">Divorcé(e)</option>
                            <option value="veuf">Veuf/Veuve</option>
                            <option value="pacs">Pacsé(e)</option>
                        </select>
                    </div>
                </div>
            </div>

            <!-- SECTION 2: INFORMATIONS PASSEPORT -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[DOCUMENT]</span>
                    Passeport
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="numPasseport">Numéro de Passeport *</label>
                        <input type="text" id="numPasseport" name="numPasseport" value="AB123456" placeholder="Ex: AB123456" required>
                    </div>
                    <div class="form-group">
                        <label for="dateDelivrance">Date de Délivrance *</label>
                        <input type="date" id="dateDelivrance" name="dateDelivrance" value="2018-01-10" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateExpiration">Date d'Expiration *</label>
                        <input type="date" id="dateExpiration" name="dateExpiration" value="2028-01-10" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 3: INFORMATIONS PROFESSIONNELLES SPECIFIQUES AU VISA TRAVAILLEUR -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[TRAVAIL]</span>
                    Informations Professionnelles
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="poste">Poste Proposé *</label>
                        <input type="text" id="poste" name="poste" value="Ingénieur Informatique Senior" placeholder="Ex: Ingénieur Informatique" required>
                    </div>
                    <div class="form-group">
                        <label for="secteurActivite">Secteur d'Activité *</label>
                        <select id="secteurActivite" name="secteurActivite" required>
                            <option value="">--Sélectionner--</option>
                            <option value="informatique" selected>Informatique</option>
                            <option value="sante">Santé</option>
                            <option value="construction">Construction</option>
                            <option value="education">Éducation</option>
                            <option value="commerce">Commerce</option>
                            <option value="autre">Autre</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="entreprise">Nom de l'Entreprise *</label>
                        <input type="text" id="entreprise" name="entreprise" value="Société InfoTech SARL" placeholder="Ex: Société XYZ SARL" required>
                    </div>
                    <div class="form-group">
                        <label for="ville">Ville de Travail *</label>
                        <select id="ville" name="ville" required>
                            <option value="">--Sélectionner--</option>
                            <option value="paris" selected>Paris</option>
                            <option value="lyon">Lyon</option>
                            <option value="marseille">Marseille</option>
                            <option value="toulouse">Toulouse</option>
                            <option value="nice">Nice</option>
                            <option value="autre">Autre</option>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="adresseEntreprise">Adresse de l'Entreprise *</label>
                        <input type="text" id="adresseEntreprise" name="adresseEntreprise" value="456 Avenue de la République, 75011 Paris" placeholder="Ex: 456 Avenue de la République, 75011 Paris" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="dateDebut">Date de Début d'Emploi *</label>
                        <input type="date" id="dateDebut" name="dateDebut" value="2026-06-01" required>
                    </div>
                    <div class="form-group">
                        <label for="salaire">Salaire Proposé (EUR) *</label>
                        <input type="number" id="salaire" name="salaire" value="45000" placeholder="Ex: 35000" min="0" step="0.01" required>
                    </div>
                </div>
            </div>

            <!-- SECTION 4: DIPLOMES ET QUALIFICATIONS -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[EDUCATION]</span>
                    Diplômes et Qualifications
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="niveauEtude">Niveau d'Étude Maximal *</label>
                        <select id="niveauEtude" name="niveauEtude" required>
                            <option value="">--Sélectionner--</option>
                            <option value="bac">Baccalauréat</option>
                            <option value="licence">Licence (Bac+3)</option>
                            <option value="master" selected>Master (Bac+5)</option>
                            <option value="doctorat">Doctorat (Bac+8)</option>
                            <option value="autre">Autre</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="diplome">Diplôme Principal *</label>
                        <input type="text" id="diplome" name="diplome" value="Master en Informatique" placeholder="Ex: Master Informatique" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="experience">Expérience Professionnelle (en années) *</label>
                        <input type="number" id="experience" name="experience" value="8" placeholder="Ex: 5" min="0" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="competences">Compétences Particulières</label>
                        <textarea id="competences" name="competences" placeholder="Décrivez vos compétences pertinentes pour le poste..." rows="4">Maîtrise de Java, Python, C++, bases de données relationnelles et NoSQL. Expérience en architecture d'applications et gestion de projets agiles. Certification AWS Solutions Architect.</textarea>
                    </div>
                </div>
            </div>

            <!-- SECTION 5: VISITE MEDICALE -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[SANTE]</span>
                    Visite Médicale
                </h2>

                <div class="form-row">
                    <div class="form-group">
                        <label for="visite">Visite Médicale Effectuée *</label>
                        <select id="visite" name="visite" required>
                            <option value="">--Sélectionner--</option>
                            <option value="oui" selected>Oui</option>
                            <option value="non">Non</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="dateVisite">Date de Visite (si applicable)</label>
                        <input type="date" id="dateVisite" name="dateVisite" value="2026-04-15">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="problemesSante">Problèmes de Santé Particuliers</label>
                        <textarea id="problemesSante" name="problemesSante" placeholder="Décrivez tout problème pertinent..." rows="3"></textarea>
                    </div>
                </div>
            </div>

            <!-- SECTION 6: DOCUMENTS JOINTS -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[FICHIERS]</span>
                    Documents Joints
                </h2>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="contratTravail">Contrat de Travail</label>
                        <input type="file" id="contratTravail" name="contratTravail" accept=".pdf,.doc,.docx">
                        <small>Format: PDF, DOC, DOCX (Max 5 MB)</small>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="certificatDiplome">Certificat(s) de Diplôme</label>
                        <input type="file" id="certificatDiplome" name="certificatDiplome" accept=".pdf,.jpg,.png">
                        <small>Format: PDF, JPG, PNG (Max 5 MB)</small>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="rapportMedical">Rapport Médical</label>
                        <input type="file" id="rapportMedical" name="rapportMedical" accept=".pdf,.jpg,.png">
                        <small>Format: PDF, JPG, PNG (Max 5 MB)</small>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group full-width">
                        <label for="autresDocuments">Autres Documents Justificatifs</label>
                        <input type="file" id="autresDocuments" name="autresDocuments" accept=".pdf,.jpg,.png,.doc,.docx" multiple>
                        <small>Format: PDF, JPG, PNG, DOC, DOCX (Max 5 MB par fichier)</small>
                    </div>
                </div>
            </div>

            <!-- SECTION 7: ACCORD ET SOUMISSION -->
            <div class="form-section">
                <h2 class="section-title">
                    <span class="section-icon">[CONFIRMATION]</span>
                    Acceptation et Soumission
                </h2>

                <div class="form-row">
                    <div class="form-group checkbox-group full-width">
                        <input type="checkbox" id="veracite" name="veracite" checked>
                        <label for="veracite">
                            Je certifie l'exactitude et la complétude de toutes les informations fournies ci-dessus *
                        </label>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group checkbox-group full-width">
                        <input type="checkbox" id="autorite" name="autorite" checked>
                        <label for="autorite">
                            Je reconnais avoir volontairement fourni ces informations aux autorités compétentes*
                        </label>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group checkbox-group full-width">
                        <input type="checkbox" id="conditions" name="conditions" checked>
                        <label for="conditions">
                            J'accepte les conditions d'utilisation et la politique de confidentialité *
                        </label>
                    </div>
                </div>
            </div>

            <!-- Boutons de Navigation -->
            <div class="form-navigation">
                <a href="visa-type" class="btn btn-secondary">Retour</a>
                <%-- ajout lien pour teste --%>
                <button type="submit" class="btn btn-primary"><a href="visa-recap" style="color:white">Soumettre la Demande</a></button>
            </div>
        </form>
    </div>

    <jsp:include page="components/footer.jsp" />

    <script>
        document.getElementById('visaTravailleurForm').addEventListener('submit', function(e) {
            // Validation supplémentaire si nécessaire
            console.log('Formulaire Visa Travailleur soumis');
        });
    </script>
</body>
</html>
