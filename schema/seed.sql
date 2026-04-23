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

-- nationalite
INSERT INTO nationalite (label) VALUES
('Afghan'), ('Sud-africain'), ('Albanais'), ('Algerien'), ('Allemand'), ('Americain'), 
('Andorran'), ('Angolais'), ('Antiguais'), ('Argentin'), ('Armenien'), ('Australien'), 
('Autrichien'), ('Azerbaidjanais'), ('Bahameen'), ('Bahreinien'), ('Bangladeshi'), 
('Barbadien'), ('Belge'), ('Belizeen'), ('Beninois'), ('Bhoutanais'), ('Bielorusse'), 
('Birman'), ('Bolivien'), ('Bosniaque'), ('Botswanais'), ('Bresilien'), ('Britannique'), 
('Bruneien'), ('Bulgare'), ('Burkinabe'), ('Burundais'), ('Cambodgien'), ('Camerounais'), 
('Canadien'), ('Cap-verdien'), ('Centrafricain'), ('Chilien'), ('Chinois'), ('Chypriote'), 
('Colombien'), ('Comorien'), ('Congolais'), ('Costaricain'), ('Croate'), ('Cubain'), 
('Danois'), ('Djiboutien'), ('Dominicain'), ('Egyptien'), ('Emirien'), ('Equatorien'), 
('Erythreen'), ('Espagnol'), ('Estonien'), ('Eswatinien'), ('Fidjien'), ('Finlandais'), 
('Francais'), ('Gabonais'), ('Gambien'), ('Georgien'), ('Ghaneen'), ('Grec'), 
('Grenadien'), ('Guatemalteque'), ('Guineen'), ('Guyanien'), ('Haitien'), ('Hondurien'), 
('Hongrois'), ('Indien'), ('Indonesien'), ('Irakien'), ('Iranien'), ('Irlandais'), 
('Islandais'), ('Israelien'), ('Italien'), ('Ivoirien'), ('Jamaiquain'), ('Japonais'), 
('Jordanien'), ('Kazakhstanais'), ('Kenyan'), ('Kirghize'), ('Kiribatien'), ('Koweitien'), 
('Laotien'), ('Lesothan'), ('Letton'), ('Libanais'), ('Liberien'), ('Libyen'), 
('Liechtensteinois'), ('Lituanien'), ('Luxembourgeois'), ('Macedonien'), ('Malaisien'), 
('Malawien'), ('Maldivien'), ('Malgache'), ('Malien'), ('Maltais'), ('Marocain'), 
('Marshallais'), ('Mauricien'), ('Mauritanien'), ('Mexicain'), ('Micronesien'), 
('Moldave'), ('Monegasque'), ('Mongol'), ('Montenegrin'), ('Mozambicain'), ('Namibien'), 
('Nauruan'), ('Neerlandais'), ('Neo-zelandais'), ('Nepalais'), ('Nicaraguayen'), 
('Nigerian'), ('Nigerien'), ('Nord-coreen'), ('Norvegien'), ('Omanais'), ('Ougandais'), 
('Ouzbek'), ('Pakistanais'), ('Paoan'), ('Panameen'), ('Paraguayen'), ('Peruvien'), 
('Philippin'), ('Polonais'), ('Portoricain'), ('Portugais'), ('Qatarien'), ('Roumain'), 
('Russe'), ('Rwandais'), ('Saint-lucien'), ('Saint-marinais'), ('Vincentais'), 
('Salomonais'), ('Salvadorien'), ('Samoan'), ('Santomeen'), ('Saoudien'), ('Senegalais'), 
('Serbe'), ('Seychellois'), ('Sierra-leonais'), ('Singapourien'), ('Slovaque'), 
('Slovene'), ('Somalien'), ('Soudanais'), ('Sri-lankais'), ('Sud-coreen'), ('Suedois'), 
('Suisse'), ('Surinamien'), ('Syrien'), ('Tadjik'), ('Tanzanien'), ('Tchadien'), 
('Tcheque'), ('Thailandais'), ('Togolais'), ('Tonguien'), ('Trinidadien'), ('Tunisien'), 
('Turkmen'), ('Turc'), ('Tuvaluan'), ('Ukrainien'), ('Uruguayen'), ('Vanuatuan'), 
('Vatican'), ('Venezuelien'), ('Vietnamien'), ('Yemenite'), ('Zambien'), ('Zimbabween');

-- situation_familiale
INSERT INTO situation_familiale (label) VALUES
('Celibataire'),
('Marie(e)'),
('Divorce(e)'),
('Veuf(ve)');
