<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Signature Demande - Visa System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <jsp:include page="components/header.jsp" />
    <style>
        .container {
            max-width: 800px;
            margin: 40px auto;
            padding: 20px;
            background-color: var(--card-bg);
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid var(--primary-color);
        }

        .header h1 {
            color: var(--primary-color);
            margin: 0;
            font-size: 24px;
        }

        .demande-info {
            background: var(--bg-color);
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }

        .demande-info p {
            margin: 5px 0;
            color: var(--text-color);
        }

        .signature-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }

        .canvas-wrapper {
            border: 2px solid var(--primary-color);
            border-radius: 8px;
            background-color: white;
            margin-bottom: 15px;
            position: relative;
            width: 100%;
            max-width: 500px;
            margin-left: auto;
            margin-right: auto;
        }

        canvas {
            cursor: crosshair;
            touch-action: none;
            display: block;
            width: 100%;
            height: 250px;
            border-radius: 4px;
        }

        .existing-signature {
            border: 2px solid #28a745;
            border-radius: 8px;
            padding: 10px;
            margin-bottom: 15px;
            background: white;
        }

        .existing-signature img {
            max-width: 100%;
            height: auto;
            display: block;
        }

        .actions {
            display: flex;
            gap: 10px;
            justify-content: center;
            margin-top: 20px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 4px;
            font-weight: 600;
            cursor: pointer;
            border: none;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }

        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .btn:hover {
            opacity: 0.9;
            transform: translateY(-1px);
        }

        .alert {
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            display: none;
        }

        .alert.active {
            display: block;
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .form-section {
            display: none;
        }

        .form-section.active {
            display: block;
            animation: fadeIn 0.3s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Signature Demande N°${demande.id}</h1>
            <a href="${pageContext.request.contextPath}/list" class="btn btn-secondary">Retour à la liste</a>
        </div>

        <div class="demande-info">
            <p><strong>Demandeur :</strong> ${demande.passeport.etatCivil.nom} ${demande.passeport.etatCivil.prenom}</p>
            <p><strong>N° Passeport :</strong> ${demande.passeport.numPasseport}</p>
            <p><strong>Type de Visa :</strong> ${demande.typeVisa.label}</p>
        </div>

        <div id="alertBox" class="alert"></div>

        <c:choose>
            <c:when test="${not empty demande.cheminSignature}">
                <div id="existingSection" class="form-section active">
                    <h3 style="text-align: center; color: #28a745; margin-bottom: 15px;">Signature existante</h3>
                    <div class="signature-container">
                        <div class="existing-signature">
                            <img src="${pageContext.request.contextPath}/${demande.cheminSignature}" alt="Signature actuelle">
                        </div>
                        <div class="actions">
                            <button class="btn btn-primary" onclick="showDrawSection()">Remplacer la signature</button>
                        </div>
                    </div>
                </div>
                <div id="drawSection" class="form-section">
                    <h3 style="text-align: center; margin-bottom: 15px;">Nouvelle Signature</h3>
                    <div class="signature-container">
                        <div class="canvas-wrapper">
                            <canvas id="signaturePad" width="500" height="250"></canvas>
                        </div>
                        <div class="actions">
                            <button class="btn btn-secondary" onclick="hideDrawSection()">Annuler</button>
                            <button class="btn btn-danger" onclick="clearCanvas()">Effacer</button>
                            <button class="btn btn-primary" onclick="saveSignature()">Sauvegarder</button>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div id="drawSection" class="form-section active">
                    <h3 style="text-align: center; margin-bottom: 15px;">Veuillez signer ci-dessous</h3>
                    <div class="signature-container">
                        <div class="canvas-wrapper">
                            <canvas id="signaturePad" width="500" height="250"></canvas>
                        </div>
                        <div class="actions">
                            <button class="btn btn-danger" onclick="clearCanvas()">Effacer</button>
                            <button class="btn btn-primary" onclick="saveSignature()">Sauvegarder</button>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script>
        const canvas = document.getElementById('signaturePad');
        let ctx, isDrawing = false;
        let lastX = 0, lastY = 0;
        let hasSignature = false;

        function initCanvas() {
            if (!canvas) return;
            
            ctx = canvas.getContext('2d');
            ctx.lineWidth = 3;
            ctx.lineCap = 'round';
            ctx.lineJoin = 'round';
            ctx.strokeStyle = '#000000';

            // Clear canvas background to white (transparent by default)
            ctx.fillStyle = '#ffffff';
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            // Mouse events
            canvas.addEventListener('mousedown', startDrawing);
            canvas.addEventListener('mousemove', draw);
            canvas.addEventListener('mouseup', stopDrawing);
            canvas.addEventListener('mouseout', stopDrawing);

            // Touch events
            canvas.addEventListener('touchstart', handleTouchStart, { passive: false });
            canvas.addEventListener('touchmove', handleTouchMove, { passive: false });
            canvas.addEventListener('touchend', stopDrawing);
        }

        function startDrawing(e) {
            isDrawing = true;
            [lastX, lastY] = [e.offsetX, e.offsetY];
            hasSignature = true;
        }

        function draw(e) {
            if (!isDrawing) return;
            ctx.beginPath();
            ctx.moveTo(lastX, lastY);
            ctx.lineTo(e.offsetX, e.offsetY);
            ctx.stroke();
            [lastX, lastY] = [e.offsetX, e.offsetY];
        }

        function stopDrawing() {
            isDrawing = false;
        }

        // Touch handling
        function handleTouchStart(e) {
            e.preventDefault();
            const touch = e.touches[0];
            const rect = canvas.getBoundingClientRect();
            startDrawing({
                offsetX: touch.clientX - rect.left,
                offsetY: touch.clientY - rect.top
            });
        }

        function handleTouchMove(e) {
            e.preventDefault();
            const touch = e.touches[0];
            const rect = canvas.getBoundingClientRect();
            draw({
                offsetX: touch.clientX - rect.left,
                offsetY: touch.clientY - rect.top
            });
        }

        function clearCanvas() {
            if (!ctx) return;
            ctx.fillStyle = '#ffffff';
            ctx.fillRect(0, 0, canvas.width, canvas.height);
            hasSignature = false;
        }

        function showDrawSection() {
            document.getElementById('existingSection').classList.remove('active');
            document.getElementById('drawSection').classList.add('active');
            clearCanvas();
        }

        function hideDrawSection() {
            document.getElementById('drawSection').classList.remove('active');
            document.getElementById('existingSection').classList.add('active');
        }

        function showMessage(msg, isError = false) {
            const alertBox = document.getElementById('alertBox');
            alertBox.textContent = msg;
            alertBox.className = 'alert active ' + (isError ? 'alert-error' : 'alert-success');
            setTimeout(() => {
                alertBox.classList.remove('active');
            }, 3000);
        }

        function saveSignature() {
            if (!hasSignature) {
                showMessage('Veuillez fournir une signature avant de sauvegarder', true);
                return;
            }

            canvas.toBlob(function(blob) {
                const formData = new FormData();
                formData.append('signature', blob, 'signature.png');
                
                // Add empty photo blob to satisfy the endpoint requirement (if the API endpoint requires both photo AND signature as per the instructions though you mentioned focus only on signature)
                // However, since the Sprint 5 spec says "refuser si photo OU signature manquante" for the unique endpoint, we create a dummy photo blob if needed. But for backoffice, maybe there's a dedicated endpoint? I'll use the API if there is one. Let's send to /api/demandes/photo-signature with empty photo for now or a new endpoint if I make one.
                
                // For this, let's create a dedicated controller endpoint to handle just signature upload from backoffice for simplicity and safety, if you haven't yet, let me use the sprint endpoint if it exists or make a new backoffice one. 
                // Wait! Let's submit to a new endpoint `/demande/${demande.id}/upload-signature` which I will create in FrontController.
                
                fetch('${pageContext.request.contextPath}/demande/${demande.id}/upload-signature', {
                    method: 'POST',
                    body: formData
                })
                .then(response => {
                    if (response.ok) {
                        showMessage('Signature sauvegardée avec succès!');
                        setTimeout(() => {
                            window.location.reload();
                        }, 1500);
                    } else {
                        throw new Error('Erreur lors de la sauvegarde');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    showMessage('Une erreur est survenue lors de la sauvegarde', true);
                });
            }, 'image/png');
        }

        // Initialize when DOM is loaded
        document.addEventListener('DOMContentLoaded', initCanvas);
    </script>
</body>
</html>