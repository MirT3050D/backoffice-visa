<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transfert - Resultat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .result-card {
            background: #fff;
            border: 1px solid #e5e5e5;
            border-radius: 0.75rem;
            padding: 1.5rem;
            margin-top: 1.5rem;
        }

        .result-title {
            font-size: 1.1rem;
            font-weight: 600;
            margin-bottom: 1rem;
            color: #333;
        }

        .result-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 1rem;
        }

        .result-item span {
            display: block;
            font-size: 0.85rem;
            color: #666;
            margin-bottom: 0.35rem;
        }

        .result-item strong {
            font-size: 1rem;
            color: #222;
        }

        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
            justify-content: flex-start;
        }

        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Transfert termine</h1>
            <p class="progress-indicator">Visa et nouveau passeport lies</p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="flash-box flash-error">${errorMessage}</div>
        </c:if>

        <c:if test="${not empty visaResult && not empty nouveauPasseport}">
            <div class="result-card">
                <div class="result-title">Nouveau passeport</div>
                <div class="result-grid">
                    <div class="result-item">
                        <span>Numero passeport</span>
                        <strong>${nouveauPasseport.numPasseport}</strong>
                    </div>
                    <div class="result-item">
                        <span>Date de delivrance</span>
                        <strong>${nouveauPasseport.dateDelivrance}</strong>
                    </div>
                    <div class="result-item">
                        <span>Date d'expiration</span>
                        <strong>${nouveauPasseport.dateExpiration}</strong>
                    </div>
                </div>
            </div>

            <div class="result-card">
                <div class="result-title">Visa associe</div>
                <div class="result-grid">
                    <div class="result-item">
                        <span>Numero de visa</span>
                        <strong>${visaResult.numVisa}</strong>
                    </div>
                    <div class="result-item">
                        <span>Type de visa</span>
                        <strong>${visaResult.typeVisa.label}</strong>
                    </div>
                    <div class="result-item">
                        <span>Ville de delivrance</span>
                        <strong>${visaResult.ville.label}</strong>
                    </div>
                    <div class="result-item">
                        <span>Date d'expiration</span>
                        <strong>${visaResult.dateExpiration}</strong>
                    </div>
                </div>
            </div>
        </c:if>

        <div class="btn-group">
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/demande-visa/list?type_demande_id=3">Retour</a>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
