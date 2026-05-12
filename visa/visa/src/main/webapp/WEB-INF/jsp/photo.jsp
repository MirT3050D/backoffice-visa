    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Prendre une photo</title>
        <style>
            video, canvas {
                width: 400px;
                border: 2px solid #4CAF50;
                border-radius: 10px;
            }

            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
                gap: 15px;
                margin-top: 30px;
            }

            button {
                padding: 10px 20px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>

    <h2>Prendre une photo pour la demande ${id}</h2>

    <div class="container">
        <video id="video" autoplay></video>
        <canvas id="canvas" style="display:none;"></canvas>

        <button onclick="takePhoto()">📸 Capturer</button>
        <button onclick="sendPhoto()">💾 Envoyer</button>

        <img id="preview" style="margin-top:10px; border:1px solid #ccc;" />
    </div>

    <script>
    let video = document.getElementById('video');
    let canvas = document.getElementById('canvas');
    let preview = document.getElementById('preview');
    let stream;

    // 1. Ouvrir la caméra
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(function(s) {
            stream = s;
            video.srcObject = stream;
        })
        .catch(function(err) {
            alert("Erreur caméra: " + err);
        });

    // 2. Capturer image
    function takePhoto() {
        let context = canvas.getContext('2d');

        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;

        context.drawImage(video, 0, 0);

        let dataUrl = canvas.toDataURL('image/png');

        preview.src = dataUrl;
        preview.dataset.image = dataUrl;
    }

    // 3. Envoyer vers Spring Boot
    function sendPhoto() {
        let image = preview.dataset.image;

        fetch('${pageContext.request.contextPath}/demande/${id}/photo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ image: image })
        })
        .then(res => res.text())
        .then(msg => alert("Upload OK"))
        .catch(err => alert("Erreur upload"));
    }
    </script>

    </body>
    </html>