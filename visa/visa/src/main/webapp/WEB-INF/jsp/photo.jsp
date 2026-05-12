<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prendre une photo</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <style>
        .camera-page {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 2rem;
        }

        .camera-card {
            width: 100%;
            max-width: 900px;
            background: white;
            border-radius: 1rem;
            padding: 2rem;
            box-shadow: 0 10px 30px rgba(15, 23, 42, 0.08);
            border: 1px solid #e5e7eb;
        }

        .camera-header {
            margin-bottom: 2rem;
        }

        .camera-header h1 {
            font-size: 2rem;
            color: #111827;
            margin-bottom: 0.5rem;
        }

        .camera-header p {
            color: #6b7280;
            font-size: 1rem;
        }

        .camera-layout {
            display: grid;
            grid-template-columns: 1fr 320px;
            gap: 2rem;
            align-items: start;
        }

        .camera-video-wrapper {
            position: relative;
            border-radius: 1rem;
            overflow: hidden;
            border: 2px solid #d1d5db;
            background: #000;
        }

        #video {
            width: 100%;
            display: block;
            min-height: 450px;
            object-fit: cover;
        }

        .camera-badge {
            position: absolute;
            top: 1rem;
            left: 1rem;
            background: rgba(0,0,0,0.7);
            color: white;
            padding: 0.5rem 0.9rem;
            border-radius: 999px;
            font-size: 0.85rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }

        .camera-dot {
            width: 10px;
            height: 10px;
            border-radius: 50%;
            background: #ef4444;
            animation: pulse 1.5s infinite;
        }

        @keyframes pulse {
            0% {
                opacity: 1;
                transform: scale(1);
            }
            50% {
                opacity: 0.5;
                transform: scale(1.2);
            }
            100% {
                opacity: 1;
                transform: scale(1);
            }
        }

        .camera-side {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .preview-card {
            border: 1px solid #e5e7eb;
            border-radius: 1rem;
            padding: 1rem;
            background: #fafafa;
        }

        .preview-card h3 {
            margin-bottom: 1rem;
            color: #111827;
        }

        #preview {
            width: 100%;
            border-radius: 0.75rem;
            border: 2px dashed #cbd5e1;
            min-height: 220px;
            object-fit: cover;
            background: white;
        }

        .camera-actions {
            display: flex;
            flex-direction: column;
            gap: 1rem;
        }

        .camera-btn {
            border: none;
            border-radius: 0.75rem;
            padding: 1rem 1.2rem;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.25s ease;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 0.7rem;
        }

        .camera-btn svg {
            width: 20px;
            height: 20px;
            fill: currentColor;
        }

        .camera-btn-primary {
            background: #4CAF50;
            color: white;
        }

        .camera-btn-primary:hover {
            background: #3f9142;
            transform: translateY(-2px);
        }

        .camera-btn-secondary {
            background: #2563eb;
            color: white;
        }

        .camera-btn-secondary:hover {
            background: #1d4ed8;
            transform: translateY(-2px);
        }

        .camera-info {
            background: #f9fafb;
            border: 1px solid #e5e7eb;
            border-radius: 1rem;
            padding: 1rem;
        }

        .camera-info h4 {
            margin-bottom: 0.75rem;
            color: #111827;
        }

        .camera-info ul {
            padding-left: 1.2rem;
            color: #4b5563;
        }

        .camera-info li {
            margin-bottom: 0.5rem;
        }

        #canvas {
            display: none;
        }

        @media (max-width: 900px) {
            .camera-layout {
                grid-template-columns: 1fr;
            }

            #video {
                min-height: 300px;
            }
        }
    </style>
</head>
<body>

<jsp:include page="components/header.jsp" />

<div class="container">
    <div class="dashboard-layout">

        <aside id="navSidebar" class="sidebar sidebar-expanded">
            <button id="sidebarToggle" class="sidebar-toggle" type="button">
                <span class="sidebar-toggle-icon">
                    <svg viewBox="0 0 24 24">
                        <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"/>
                    </svg>
                </span>
            </button>

            <h2 class="sidebar-title">Navigation</h2>

            <nav class="sidebar-nav">
                <a class="sidebar-link" href="${pageContext.request.contextPath}/creation">Creation</a>
                <a class="sidebar-link active" href="${pageContext.request.contextPath}/list">List</a>
            </nav>
        </aside>

        <main class="dashboard-content">

            <div class="camera-page">

                <div class="camera-card">

                    <div class="camera-header">
                        <h1>Prendre une photo</h1>
                        <p>Capture webcam pour la demande VISA #${id}</p>
                    </div>

                    <div class="camera-layout">

                        <!-- VIDEO -->
                        <div class="camera-video-wrapper">

                            <div class="camera-badge">
                                <span class="camera-dot"></span>
                                Camera Active
                            </div>

                            <video id="video" autoplay playsinline></video>
                        </div>

                        <!-- SIDE -->
                        <div class="camera-side">

                            <div class="preview-card">
                                <h3>Prévisualisation</h3>

                                <img id="preview" />
                            </div>

                            <div class="camera-actions">

                                <button class="camera-btn camera-btn-primary"
                                        onclick="takePhoto()">

                                    <svg viewBox="0 0 24 24">
                                        <path d="M9 2L7.17 4H4a2 2 0 00-2 2v14a2 2 0 002 2h16a2 2 0 002-2V6a2 2 0 00-2-2h-3.17L15 2H9zm3 16a5 5 0 110-10 5 5 0 010 10z"/>
                                    </svg>

                                    Capturer
                                </button>

                                <button class="camera-btn camera-btn-secondary"
                                        onclick="sendPhoto()">

                                    <svg viewBox="0 0 24 24">
                                        <path d="M5 20h14v-2H5v2zm7-18L5.33 9h3.84v4h5.66V9h3.84L12 2z"/>
                                    </svg>

                                    Envoyer
                                </button>

                            </div>

                            <div class="camera-info">
                                <h4>Conseils</h4>

                                <ul>
                                    <li>Bien centrer le visage</li>
                                    <li>Utiliser un bon éclairage</li>
                                    <li>Eviter les mouvements</li>
                                    <li>Verifier l'aperçu avant envoi</li>
                                </ul>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </main>

    </div>
</div>

<canvas id="canvas"></canvas>

<script>
    let video = document.getElementById('video');
    let canvas = document.getElementById('canvas');
    let preview = document.getElementById('preview');

    navigator.mediaDevices.getUserMedia({
        video: true
    })
    .then(function(stream) {
        video.srcObject = stream;
    })
    .catch(function(err) {
        alert("Erreur caméra : " + err);
    });

    function takePhoto() {

        let context = canvas.getContext('2d');

        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;

        context.drawImage(video, 0, 0);

        let dataUrl = canvas.toDataURL('image/png');

        preview.src = dataUrl;
        preview.dataset.image = dataUrl;
    }

    function sendPhoto() {

        let image = preview.dataset.image;

        if (!image) {
            alert("Veuillez capturer une photo");
            return;
        }

        fetch('${pageContext.request.contextPath}/demande/${id}/photo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                image: image
            })
        })
        .then(res => res.text())
        .then(msg => {
            alert("Photo envoyée avec succès");
        })
        .catch(err => {
            alert("Erreur upload");
        });
    }

    (function () {
        var sidebar = document.getElementById('navSidebar');
        var toggle = document.getElementById('sidebarToggle');

        if (!sidebar || !toggle) {
            return;
        }

        toggle.addEventListener('click', function () {
            var isCollapsed = sidebar.classList.toggle('sidebar-collapsed');
            sidebar.classList.toggle('sidebar-expanded', !isCollapsed);
            toggle.setAttribute('aria-expanded', String(!isCollapsed));
        });
    })();
</script>

<jsp:include page="components/footer.jsp" />

</body>
</html>