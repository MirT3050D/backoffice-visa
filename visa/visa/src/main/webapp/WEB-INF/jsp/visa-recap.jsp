<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Récapitulatif - Demande de Visa Travailleur</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Récapitulatif de Votre Demande</h1>
            <p class="progress-indicator">Etape 3/3 : Confirmation et Soumission</p>
        </div>

        <div class="info-section" style="margin-bottom: 2rem;">
            <div style="display: flex; align-items: center; gap: 1rem;">
                <svg style="width: 48px; height: 48px; color: var(--success);" viewBox="0 0 24 24" fill="currentColor">
                    <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                </svg>
                <div>
                    <h3 style="color: var(--success); margin: 0;">Vérifiez vos informations</h3>
                    <p style="color: #666; margin: 0.25rem 0 0 0;">Merci de relire tous les détails avant de confirmer votre demande</p>
                </div>
            </div>
        </div>

        <!-- SECTION: INFORMATIONS PERSONNELLES -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">👤</span>
                Informations Personnelles
            </h3>
            
            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Nom Complet</strong>
                    <p style="margin: 0; color: #555;">Jean DUPONT</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Date de Naissance</strong>
                    <p style="margin: 0; color: #555;">15/03/1990</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Lieu de Naissance</strong>
                    <p style="margin: 0; color: #555;">Paris, France</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Nationalité</strong>
                    <p style="margin: 0; color: #555;">Français</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Email</strong>
                    <p style="margin: 0; color: #555;">jean.dupont@email.com</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Téléphone</strong>
                    <p style="margin: 0; color: #555;">+33 6 12 34 56 78</p>
                </div>
                <div style="grid-column: 1 / -1;">
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Adresse</strong>
                    <p style="margin: 0; color: #555;">123 Rue de la Paix, 75000 Paris, France</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Situation Familiale</strong>
                    <p style="margin: 0; color: #555;">Marié(e)</p>
                </div>
            </div>
        </div>

        <!-- SECTION: INFORMATIONS PASSEPORT -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">📄</span>
                Informations Passeport
            </h3>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Numéro de Passeport</strong>
                    <p style="margin: 0; color: #555;">AB123456</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Date de Délivrance</strong>
                    <p style="margin: 0; color: #555;">10/01/2018</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Date d'Expiration</strong>
                    <p style="margin: 0; color: #555;">10/01/2028</p>
                </div>
            </div>
        </div>

        <!-- SECTION: INFORMATIONS PROFESSIONNELLES -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">💼</span>
                Informations Professionnelles
            </h3>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Poste Proposé</strong>
                    <p style="margin: 0; color: #555;">Ingénieur Informatique Senior</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Secteur d'Activité</strong>
                    <p style="margin: 0; color: #555;">Informatique</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Entreprise</strong>
                    <p style="margin: 0; color: #555;">Société InfoTech SARL</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Ville de Travail</strong>
                    <p style="margin: 0; color: #555;">Paris</p>
                </div>
                <div style="grid-column: 1 / -1;">
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Adresse de l'Entreprise</strong>
                    <p style="margin: 0; color: #555;">456 Avenue de la République, 75011 Paris</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Date de Début</strong>
                    <p style="margin: 0; color: #555;">01/06/2026</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Salaire Proposé</strong>
                    <p style="margin: 0; color: #555;">45 000 EUR/an</p>
                </div>
            </div>
        </div>

        <!-- SECTION: EDUCATION ET QUALIFICATIONS -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">🎓</span>
                Éducation et Qualifications
            </h3>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Niveau d'Étude</strong>
                    <p style="margin: 0; color: #555;">Master (Bac+5)</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Diplôme Principal</strong>
                    <p style="margin: 0; color: #555;">Master en Informatique</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Expérience</strong>
                    <p style="margin: 0; color: #555;">8 ans</p>
                </div>
            </div>

            <div style="margin-top: 1.5rem;">
                <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Compétences Particulières</strong>
                <p style="margin: 0; color: #555; line-height: 1.6;">
                    Maîtrise de Java, Python, C++, bases de données relationnelles et NoSQL. 
                    Expérience en architecture d'applications et gestion de projets agiles. 
                    Certification AWS Solutions Architect.
                </p>
            </div>
        </div>

        <!-- SECTION: SANTE -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">⚕️</span>
                Informations de Santé
            </h3>

            <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 1.5rem;">
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Visite Médicale</strong>
                    <p style="margin: 0; color: #555;">Oui</p>
                </div>
                <div>
                    <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Date de Visite</strong>
                    <p style="margin: 0; color: #555;">15/04/2026</p>
                </div>
            </div>

            <div style="margin-top: 1.5rem;">
                <strong style="color: var(--primary); display: block; margin-bottom: 0.25rem;">Problèmes de Santé</strong>
                <p style="margin: 0; color: #555;">Aucun problème particulier déclaré</p>
            </div>
        </div>

        <!-- SECTION: DOCUMENTS JOINTS -->
        <div class="info-section">
            <h3 style="display: flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem;">
                <span style="font-size: 1.5rem;">📎</span>
                Documents Joints
            </h3>

            <ul style="list-style: none; padding: 0;">
                <li style="padding: 0.75rem 0; border-bottom: 1px solid #eee; display: flex; align-items: center; gap: 0.75rem;">
                    <svg style="width: 20px; height: 20px; color: var(--success);" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                    </svg>
                    <span style="color: #555;">Contrat de Travail - contrat_travail_2026.pdf</span>
                </li>
                <li style="padding: 0.75rem 0; border-bottom: 1px solid #eee; display: flex; align-items: center; gap: 0.75rem;">
                    <svg style="width: 20px; height: 20px; color: var(--success);" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                    </svg>
                    <span style="color: #555;">Certificats de Diplômes - diplomes.pdf</span>
                </li>
                <li style="padding: 0.75rem 0; border-bottom: 1px solid #eee; display: flex; align-items: center; gap: 0.75rem;">
                    <svg style="width: 20px; height: 20px; color: var(--success);" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41L9 16.17z"/>
                    </svg>
                    <span style="color: #555;">Rapport Médical - rapport_medical_2026.pdf</span>
                </li>
            </ul>
        </div>

        <!-- ALERTES ET AVERTISSEMENTS -->
        <div class="alert-info" style="margin: 2rem 0;">
            <strong>⚠️ Important :</strong> Veuillez vérifier toutes les informations ci-dessus. 
            Toute information inexacte pourrait entraîner le rejet de votre demande.
        </div>

        <!-- BOUTONS DE NAVIGATION -->
        <div class="form-navigation" style="margin-top: 2rem;">
            <a href="visa-travailleur" class="btn btn-secondary">Modifier</a>
            <form method="POST" action="soumettre-demande-travailleur" style="display: flex; gap: 1rem; flex: 1;">
                <button type="submit" class="btn btn-primary" style="flex: 1; max-width: 220px;">
                    Confirmer et Soumettre
                </button>
            </form>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
