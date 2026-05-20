<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Backoffice Visa - Detail Demande</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <style>
        .photo-detail-card {
            padding: 1.2rem;
        }

        .photo-header {
            margin-bottom: 1rem;
        }

        .photo-header h2 {
            margin: 0;
            font-size: 1.2rem;
            color: #1f2937;
        }

        .photo-subtitle {
            margin-top: 0.25rem;
            font-size: 0.9rem;
            color: #6b7280;
        }

        .photo-layout {
            display: flex;
            gap: 1rem;
            align-items: center;
            justify-content: flex-start;
            flex-wrap: wrap;
        }

        /* PHOTO */
        .photo-box {
            width: 180px;
            height: 180px;
            border-radius: 14px;
            overflow: hidden;
            background: #f8fafc;
            border: 2px solid #dbeafe;
            box-shadow: 0 4px 14px rgba(0,0,0,0.06);
            flex-shrink: 0;
        }

        .demande-photo {
            width: 100%;
            height: 100%;
            object-fit: cover;
            display: block;
        }

        /* SIGNATURE */
        .signature-box {
            width: 260px;
            height: 120px;
            border-radius: 14px;
            border: 2px dashed #cbd5e1;
            background: #f8fafc;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
        }

        .signature-placeholder {
            color: #94a3b8;
            font-size: 0.95rem;
            font-weight: 600;
            letter-spacing: 0.5px;
        }

        .pdf-modal {
            position: fixed;
            inset: 0;
            background: rgba(15, 23, 42, 0.65);
            display: none;
            align-items: center;
            justify-content: center;
            z-index: 2000;
            padding: 1.5rem;
        }

        .pdf-modal.is-open {
            display: flex;
        }

        .pdf-modal-content {
            background: #fff;
            border-radius: 16px;
            width: min(1100px, 100%);
            height: min(90vh, 760px);
            box-shadow: 0 24px 60px rgba(15, 23, 42, 0.3);
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .pdf-modal-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 0.85rem 1.25rem;
            border-bottom: 1px solid #e2e8f0;
            background: #f8fafc;
        }

        .pdf-modal-title {
            font-size: 1rem;
            font-weight: 600;
            color: #0f172a;
        }

        .pdf-modal-close {
            border: none;
            background: transparent;
            font-size: 1.4rem;
            line-height: 1;
            cursor: pointer;
            color: #64748b;
        }

        .pdf-modal-body {
            position: relative;
            flex: 1;
            background: #0f172a;
        }

        .pdf-frame {
            width: 100%;
            height: 100%;
            border: 0;
            display: block;
        }

        .pdf-overlay {
            position: absolute;
            inset: 0;
            background: transparent;
            z-index: 2;
            pointer-events: none;
        }

        /* RESPONSIVE */
        @media (max-width: 768px) {
            .photo-layout {
                flex-direction: column;
                align-items: stretch;
            }

            .photo-box,
            .signature-box {
                width: 100%;
            }

            .photo-box {
                height: 260px;
            }

            .pdf-modal-content {
                height: 90vh;
            }
        }
    </style>
</head>

<body>

<jsp:include page="components/header.jsp" />

<div class="container">

    <div class="dashboard-layout">

        <aside id="navSidebar" class="sidebar sidebar-expanded">

            <button id="sidebarToggle"
                    class="sidebar-toggle"
                    type="button"
                    aria-controls="navSidebar"
                    aria-expanded="true"
                    aria-label="Masquer la navigation">

                <span class="sidebar-toggle-icon" aria-hidden="true">
                    <svg viewBox="0 0 24 24" focusable="false">
                        <path d="M15.41 7.41L14 6l-6 6 6 6 1.41-1.41L10.83 12z"/>
                    </svg>
                </span>

            </button>

            <h2 class="sidebar-title">Navigation</h2>

            <nav class="sidebar-nav">
                <a class="sidebar-link"
                   href="${pageContext.request.contextPath}/creation">
                    Creation
                </a>

                <a class="sidebar-link active"
                   href="${pageContext.request.contextPath}/list">
                    List
                </a>
            </nav>

        </aside>

        <main class="dashboard-content">

            <div class="welcome-section">
                <h1>Detail de la Demande #${demande.id}</h1>
                <p class="subtitle">
                    Consultation des informations de la demande
                </p>
            </div>

            <!-- PHOTO EN PREMIER -->
            <section class="detail-card photo-detail-card">
                <div class="photo-header">
                    <div>
                        <h2>Photo du demandeur</h2>
                        <p class="photo-subtitle">
                            Photo capturee lors du scan de la demande
                        </p>
                    </div>
                </div>

                <div class="photo-layout">
                    <!-- PHOTO -->
                    <div class="photo-box">
                        <img
                            src="${pageContext.request.contextPath}/demande/${demande.id}/photo/view"
                            alt="Photo demandeur"
                            class="demande-photo"
                            onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/assets/images/no-photo.png';"
                        />
                    </div>

                    <!-- SIGNATURE -->
                    <div class="signature-box" id="signature-container-${demande.id}">
                        <c:if test="${not empty demande.cheminSignature}">
                            <img
                                src="${pageContext.request.contextPath}/demande/${demande.id}/signature/view"
                                alt="Signature demandeur"
                                class="demande-photo"
                                style="object-fit: contain;"
                                onerror="document.getElementById('signature-container-${demande.id}').innerHTML='<div class=&quot;signature-placeholder&quot;>Signature non disponible</div>';"
                            />
                        </c:if>
                        <c:if test="${empty demande.cheminSignature}">
                            <div class="signature-placeholder">
                                Signature
                            </div>
                        </c:if>
                    </div>
                </div>
            </section>

            <c:if test="${demande.estVerrouille}">
                <div class="flash-box flash-success">
                    Demande verrouillee (scan termine).
                </div>
            </c:if>

            <c:if test="${not empty successMessage}">
                <div class="flash-box flash-success">
                    ${successMessage}
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="flash-box flash-error">
                    ${errorMessage}
                </div>
            </c:if>

            <section class="detail-card">

                <h2>Informations Demande</h2>

                <div class="detail-grid">

                    <div class="detail-item">
                        <span>ID</span>
                        <strong>${demande.id}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Date de demande</span>
                        <strong>${demande.dateDemande}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Type de demande</span>
                        <strong>${demande.typeDemandeVisa.label}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Type de visa</span>
                        <strong>${demande.typeVisa.label}</strong>
                    </div>

                </div>

            </section>

            <section class="detail-card">

                <h2>Etat Civil</h2>

                <div class="detail-grid">

                    <div class="detail-item">
                        <span>Nom</span>
                        <strong>${demande.passeport.etatCivil.nom}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Prenom</span>
                        <strong>${demande.passeport.etatCivil.prenom}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Date de naissance</span>
                        <strong>${demande.passeport.etatCivil.dateNaissance}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Email</span>
                        <strong>${demande.passeport.etatCivil.email}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Telephone</span>
                        <strong>${demande.passeport.etatCivil.numTel}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Nationalite</span>
                        <strong>${demande.passeport.etatCivil.nationalite.label}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Situation familiale</span>
                        <strong>${demande.passeport.etatCivil.situationFamiliale.label}</strong>
                    </div>

                </div>

            </section>

            <section class="detail-card">

                <h2>Passeport</h2>

                <div class="detail-grid">

                    <div class="detail-item">
                        <span>Numero passeport</span>
                        <strong>${demande.passeport.numPasseport}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Date delivrance</span>
                        <strong>${demande.passeport.dateDelivrance}</strong>
                    </div>

                    <div class="detail-item">
                        <span>Date expiration</span>
                        <strong>${demande.passeport.dateExpiration}</strong>
                    </div>

                </div>

            </section>

            <div class="detail-actions">

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/list">

                    Retour a la liste
                </a>

                <button class="btn btn-primary"
                        type="button"
                        data-action="open-pdf">

                    Apercu des pieces justificatives
                </button>

                <div class="row-actions">

                    <a class="row-action row-action-edit"
                       href="${pageContext.request.contextPath}/demande/${demande.id}/scan"
                       title="Scanner">

                        <svg viewBox="0 0 24 24"
                             aria-hidden="true"
                             focusable="false">

                            <path d="M4 4h16v4H4V4zm2 6h12v8H6v-8zm2 2v4h8v-4H8zm9-6h3v2h-3V6z"/>
                        </svg>

                    </a>

                    <a class="row-action row-action-edit"
                       href="${pageContext.request.contextPath}/demande/${demande.id}/photo"
                       title="Prendre une photo">

                        <svg viewBox="0 0 24 24"
                             aria-hidden="true"
                             focusable="false">

                            <path d="M9 2L7.17 4H4a2 2 0 00-2 2v14a2
                                     2 0 002 2h16a2 2 0 002-2V6a2
                                     2 0 00-2-2h-3.17L15 2H9zm3
                                     16a5 5 0 110-10 5 5 0 010 10z"/>
                        </svg>

                    </a>

                </div>

            </div>

        </main>

    </div>

</div>

<div class="pdf-modal" id="pdfModal" aria-hidden="true">
    <div class="pdf-modal-content" role="dialog" aria-modal="true" aria-labelledby="pdfModalTitle">
        <div class="pdf-modal-header">
            <span class="pdf-modal-title" id="pdfModalTitle">Apercu des pieces justificatives</span>
            <button class="pdf-modal-close" type="button" data-action="close-pdf" aria-label="Fermer">&times;</button>
        </div>
        <div class="pdf-modal-body" id="pdfModalBody">
            <iframe
                class="pdf-frame"
                id="pdfFrame"
                src=""
                title="Apercu des pieces justificatives">
            </iframe>
            <div class="pdf-overlay" aria-hidden="true"></div>
        </div>
    </div>
</div>

<script>

    (function () {

        // Sidebar
        var sidebar = document.getElementById('navSidebar');
        var toggle = document.getElementById('sidebarToggle');

        if (sidebar && toggle) {

            toggle.addEventListener('click', function () {

                var isCollapsed =
                    sidebar.classList.toggle('sidebar-collapsed');

                sidebar.classList.toggle(
                    'sidebar-expanded',
                    !isCollapsed
                );

                toggle.setAttribute(
                    'aria-expanded',
                    String(!isCollapsed)
                );

                toggle.setAttribute(
                    'aria-label',
                    isCollapsed
                        ? 'Afficher la navigation'
                        : 'Masquer la navigation'
                );

            });

        }

        // Gestion photo
        var photo = document.getElementById('demandePhoto');
        var photoNotFound = document.getElementById('photoNotFound');

        if (photo) {

            photo.onload = function () {

                photo.style.display = 'block';

                if (photoNotFound) {
                    photoNotFound.style.display = 'none';
                }

            };

            photo.onerror = function () {

                photo.style.display = 'none';

                if (photoNotFound) {
                    photoNotFound.style.display = 'flex';
                }

            };

        }

        var pdfModal = document.getElementById('pdfModal');
        var pdfFrame = document.getElementById('pdfFrame');
        var openPdfButton = document.querySelector('[data-action="open-pdf"]');
        var closePdfButton = document.querySelector('[data-action="close-pdf"]');
        var pdfModalBody = document.getElementById('pdfModalBody');
        var pdfUrl = '${pageContext.request.contextPath}/api/demandes/${demande.id}/pieces-jointes/fusion#toolbar=0&navpanes=0';

        function openPdfModal() {
            if (!pdfModal || !pdfFrame) {
                return;
            }
            pdfFrame.src = pdfUrl;
            pdfModal.classList.add('is-open');
            pdfModal.setAttribute('aria-hidden', 'false');
            document.body.style.overflow = 'hidden';
        }

        function closePdfModal() {
            if (!pdfModal || !pdfFrame) {
                return;
            }
            pdfModal.classList.remove('is-open');
            pdfModal.setAttribute('aria-hidden', 'true');
            pdfFrame.src = '';
            document.body.style.overflow = '';
        }

        if (openPdfButton) {
            openPdfButton.addEventListener('click', openPdfModal);
        }

        if (closePdfButton) {
            closePdfButton.addEventListener('click', closePdfModal);
        }

        if (pdfModal) {
            pdfModal.addEventListener('click', function (event) {
                if (event.target === pdfModal) {
                    closePdfModal();
                }
            });
        }

        if (pdfModalBody) {
            pdfModalBody.addEventListener('contextmenu', function (event) {
                event.preventDefault();
            });
        }

    })();

</script>

<jsp:include page="components/footer.jsp" />

</body>
</html>