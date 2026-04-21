<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Demande de Sejour</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="welcome-section">
            <h1>Gestion des Demandes de Visa de Séjour</h1>
            <p class="subtitle">Système d'Administration - Backoffice</p>
        </div>

        <div class="cards-grid">
            <!-- Card: Nouvelle Demande -->
            <div class="card">
                <div class="card-header bg-primary">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm5 11h-4v4h-2v-4H7v-2h4V7h2v4h4v2z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Nouvelle Demande</h2>
                    <p>Creer une nouvelle demande de visa de sejour</p>
                </div>
                <div class="card-footer">
                    <a href="demande-visa" class="btn btn-primary">Commencer</a>
                </div>
            </div>

            <!-- Card: Demande Visa Court Sejour -->
            <div class="card">
                <div class="card-header bg-secondary">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M9 11H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2zm2-7h-1V2h-2v2H8V2H6v2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V9h14v11z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Visa Court Sejour</h2>
                    <p>Demande pour un sejour de moins de 90 jours</p>
                </div>
                <div class="card-footer">
                    <a href="demande-visa?type=court" class="btn btn-secondary">Remplir</a>
                </div>
            </div>

            <!-- Card: Demande Visa Long Sejour -->
            <div class="card">
                <div class="card-header bg-accent">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V5h14v14z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Visa Long Sejour</h2>
                    <p>Demande pour un sejour superieur a 90 jours</p>
                </div>
                <div class="card-footer">
                    <a href="demande-visa?type=long" class="btn btn-accent">Remplir</a>
                </div>
            </div>

            <!-- Card: Consulter Dossier -->
            <div class="card">
                <div class="card-header bg-info">
                    <svg class="card-icon" viewBox="0 0 24 24" fill="currentColor">
                        <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
                    </svg>
                </div>
                <div class="card-body">
                    <h2>Consulter Dossier</h2>
                    <p>Suivre l'etat de votre demande</p>
                </div>
                <div class="card-footer">
                    <a href="consulter-dossier" class="btn btn-info">Consulter</a>
                </div>
            </div>
        </div>

        <div class="info-section">
            <div class="info-box">
                <h3>Informations Utiles</h3>
                <ul>
                    <li><strong>Delai de traitement:</strong> 15 jours ouvrables</li>
                    <li><strong>Pieces requises:</strong> Passeport valide + justificatifs</li>
                    <li><strong>Frais:</strong> A definir selon le type de visa</li>
                    <li><strong>Support:</strong> contact@visasystem.dz</li>
                </ul>
            </div>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
