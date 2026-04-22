-- type_visa
INSERT INTO type_visa (label) VALUES
('Investisseur'),
('Travailleur'),
('Regroupement familial');

-- type_demande_visa
INSERT INTO type_demande_visa (label) VALUES
('Nouveau titre'),
('Duplicata'),
('Transfert de visa');

-- type_statut_demande (id, label, ordre_statut)
INSERT INTO type_statut_demande (label, ordre_statut) VALUES
('Creer', 1),
('Scanne', 2);

-- champ_fournir_commune
-- Tous ce qui est genre de papier physique, vont avoir un type boolean
INSERT INTO champ_fournir_commune (label, type_donnee) VALUES
('02 photos d''identite', 'boolean'),
('Notice de renseignement', 'boolean'),
('Demande adressee a Mr le Ministere', 'boolean'),
('Photocopie certifiee du visa en cours', 'boolean'),
('Photocopie certifiee de la 1ere page du passeport', 'boolean'),
('Photocopie certifiee de la carte resident', 'boolean'),
('Certificat de residence a Madagascar', 'boolean'),
('Extrait de casier judiciaire moins de 3 mois', 'boolean');

-- champ_fournir_specifique
-- Investisseur (ID = 1)
INSERT INTO champ_fournir_specifique (label, type_donnee, id_type_visa) VALUES
('Statut de la Societe', 'boolean', 1),
('Extrait d''inscription au registre commerce', 'boolean', 1),
('Carte fiscale', 'boolean', 1);

-- Travailleur (ID = 2)
INSERT INTO champ_fournir_specifique (label, type_donnee, id_type_visa) VALUES
('Autorisation emploi (Ministere fonction publique)', 'boolean', 2),
('Attestation d''emploi de l''employeur', 'boolean', 2);

-- Regroupement familial (ID = 3)
INSERT INTO champ_fournir_specifique (label, type_donnee, id_type_visa) VALUES
('Acte de naissance (< 6 mois) ou acte de mariage', 'boolean', 3),
('Justificatif de ressources', 'boolean', 3),
('Autorisation emploi pour le regroupement familial', 'boolean', 3);
