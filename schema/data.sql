-- Assurez-vous que les types de base sont déjà insérés (via seed.sql)

-- 1. Insérer un état civil
INSERT INTO etat_civil (nom, prenoms, mail, num_tel, date_naissance, lieu_naissance, adresse, Id_nationalite, Id_situation_familiale) VALUES
('Dupont', 'Jean', 'jean.dupont@email.com', '0123456789', '1990-05-15', 'Paris', '123 Rue de la Paix, Paris', 
 (SELECT Id FROM nationalite WHERE label = 'Francais'), 
 (SELECT Id FROM situation_familiale WHERE label = 'Celibataire'))
RETURNING Id;

-- Notez l'ID de l'état civil retourné (par exemple, 1) pour l'utiliser ci-dessous.

-- 2. Insérer un passeport lié à l'état civil
-- Remplacer 'ID_ETAT_CIVIL' par l'ID obtenu à l'étape précédente
INSERT INTO passport (num_passport, date_expiration, date_delivrence, Id_etat_civil) VALUES
('AB123456', '2030-01-01', '2020-01-01', 1);

-- 3. Insérer une demande de visa liée au passeport
INSERT INTO demande_visa (date_demande, Id_passport, Id_type_visa, Id_type_demande_visa) VALUES
(NOW(), 
 (SELECT Id FROM passport WHERE num_passport = 'AB123456'),
 (SELECT Id FROM type_visa WHERE label = 'Travailleur'),
 (SELECT Id FROM type_demande_visa WHERE label = 'Nouveau titre'));

-- 4. Insérer un statut initial pour la demande
INSERT INTO statut_demande (date_statut, Id_type_statut_demande, Id_demande_visa) VALUES
(NOW(),
 (SELECT Id FROM type_statut_demande WHERE label = 'Creer'),
 (SELECT Id FROM demande_visa WHERE Id_passport = (SELECT Id FROM passport WHERE num_passport = 'AB123456')));
