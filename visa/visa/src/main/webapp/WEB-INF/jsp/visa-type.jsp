<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Type de Visa - Demande de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Sélectionner le Type de Visa</h1>
            <p class="progress-indicator">Etape 1/3 : Type de Visa</p>
        </div>

        <div class="cards-grid">
            <!-- Card: Visa Travailleur -->
            <div class="card selectable">
                <div class="card-header bg-info">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M20 13H4c-.55 0-1 .45-1 1v6c0 .55.45 1 1 1h16c.55 0 1-.45 1-1v-6c0-.55-.45-1-1-1zM7 19c-1.1 0-2-.9-2-2s.9-2 2-2 2 .9 2 2-.9 2-2 2zM20 3H4c-.55 0-1 .45-1 1v6c0 .55.45 1 1 1h16c.55 0 1-.45 1-1V4c0-.55-.45-1-1-1zm-3 6h-2V5h2v4z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Visa Travailleur</h2>
                    <p>Pour les ressortissants étrangers souhaitant travailler</p>
                    <ul class="visa-features">
                        <li>✓ Contrat de travail</li>
                        <li>✓ Diplômes et qualifications</li>
                        <li>✓ Visite médicale</li>
                    </ul>
                </div>
                <div class="card-footer">
                    <a href="visa-form" class="btn btn-primary">Continuer</a>
                </div>
            </div>

            <!-- Card: Visa Investisseur -->
            <div class="card selectable">
                <div class="card-header bg-success">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Visa Investisseur</h2>
                    <p>Pour les investisseurs et créateurs d'entreprise</p>
                    <ul class="visa-features">
                        <li>✓ Plan d'affaires</li>
                        <li>✓ Preuves financières</li>
                        <li>✓ Statut d'entreprise</li>
                    </ul>
                </div>
                <div class="card-footer">
                    <a href="#" class="btn btn-secondary">Bientôt disponible</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
