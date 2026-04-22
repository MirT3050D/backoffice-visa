-- Migration: allow dossier rows for common fields and keep exactly one field reference per row.

ALTER TABLE dossier
    ADD COLUMN IF NOT EXISTS id_champ_fournir_commune INTEGER;

ALTER TABLE dossier
    ALTER COLUMN id_champ_fournir_specifique DROP NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'dossier_id_champ_fournir_commune_fkey'
    ) THEN
        ALTER TABLE dossier
            ADD CONSTRAINT dossier_id_champ_fournir_commune_fkey
            FOREIGN KEY (id_champ_fournir_commune)
            REFERENCES champ_fournir_commune(id);
    END IF;
END $$;

ALTER TABLE dossier
    DROP CONSTRAINT IF EXISTS ck_dossier_one_champ;

ALTER TABLE dossier
    ADD CONSTRAINT ck_dossier_one_champ CHECK (
        (id_champ_fournir_specifique IS NOT NULL AND id_champ_fournir_commune IS NULL)
        OR
        (id_champ_fournir_specifique IS NULL AND id_champ_fournir_commune IS NOT NULL)
    );
