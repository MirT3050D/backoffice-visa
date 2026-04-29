<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nouveau Passeport - Transfert de Visa</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            margin-top: 20px;
        }

        .form-section {
            margin-bottom: 25px;
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

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #333;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
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
    </style>
</head>
<body>
    <jsp:include page="components/header.jsp" />

    <div class="container">
        <div class="form-header">
            <h1>Nouveau Passeport (Transfert)</h1>
            <p class="progress-indicator">Etape 4/4 : Nouveau passeport</p>
        </div>

        <div class="form-container">
            <c:choose>
                <c:when test="${not empty visaId}">
                    <form action="/demande-visa/transfert-avec-donnees" method="POST">
                        <input type="hidden" name="visa_id" value="${visaId}" />
                </c:when>
                <c:otherwise>
                    <form action="/demande-visa/finaliser-transfert" method="POST">
                </c:otherwise>
            </c:choose>
                <input type="hidden" name="typeDemandeId" value="${typeDemandeId}" />
                <input type="hidden" name="typeVisaId" value="${typeVisaId}" />

                <div class="form-section">
                    <h3>Informations du Nouveau Passeport</h3>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="nouveauNumeroPasseport">Numero de passeport</label>
                            <input type="text" id="nouveauNumeroPasseport" name="nouveauNumeroPasseport" placeholder="Ex: AB123456" required>
                        </div>
                        <div class="form-group">
                            <label for="nouveauDateDelivrance">Date de delivrance</label>
                            <input type="date" id="nouveauDateDelivrance" name="nouveauDateDelivrance" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="nouveauDateExpiration">Date d'expiration</label>
                        <input type="date" id="nouveauDateExpiration" name="nouveauDateExpiration" required>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn btn-primary">Valider le nouveau passeport</button>
                    <a href="/demande-visa/select-visa?type_demande_id=${typeDemandeId}" class="btn btn-secondary" style="text-decoration: none;">Retour</a>
                </div>
            </form>
        </div>
    </div>

    <jsp:include page="components/footer.jsp" />
</body>
</html>
