-- Migration Sprint 3: scan des pieces
-- Ajout des colonnes pour le stockage des fichiers et le verrouillage

ALTER TABLE demande_visa
    ADD COLUMN IF NOT EXISTS est_verrouille BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE dossier
    ADD COLUMN IF NOT EXISTS path_fichier VARCHAR(255);
