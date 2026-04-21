CREATE TABLE type_demande_visa(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label)
);

CREATE TABLE type_visa(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label)
);

CREATE TABLE pays(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label)
);

CREATE TABLE ville(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   Id_pays INT NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label),
   FOREIGN KEY(Id_pays) REFERENCES pays(Id)
);

CREATE TABLE situation_familiale(
   Id COUNTER,
   situation VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(situation)
);

CREATE TABLE nationalite(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label)
);

CREATE TABLE type_statut_visa(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   rang DOUBLE NOT NULL,
   PRIMARY KEY(Id)
);

CREATE TABLE type_statut_demande(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   rang DECIMAL(15,2) NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(label)
);

CREATE TABLE champ_fournir(
   Id COUNTER,
   label VARCHAR(50) NOT NULL,
   type_donnee VARCHAR(50) NOT NULL,
   Id_type_visa INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id)
);

CREATE TABLE etat_civil(
   Id COUNTER,
   nom VARCHAR(50) NOT NULL,
   prenoms VARCHAR(50) NOT NULL,
   nom_jeune_fille VARCHAR(50),
   mail VARCHAR(50),
   num_tel VARCHAR(50),
   date_naissance DATE NOT NULL,
   lieu_naissance VARCHAR(50) NOT NULL,
   adresse VARCHAR(50) NOT NULL,
   Id_nationalite INT NOT NULL,
   Id_situation_familiale INT NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(mail),
   UNIQUE(num_tel),
   FOREIGN KEY(Id_nationalite) REFERENCES nationalite(Id),
   FOREIGN KEY(Id_situation_familiale) REFERENCES situation_familiale(Id)
);

CREATE TABLE passport(
   Id COUNTER,
   num_passport VARCHAR(50) NOT NULL,
   date_expiration VARCHAR(50) NOT NULL,
   date_delivrence DATE NOT NULL,
   Id_etat_civil INT NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(num_passport),
   FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id)
);

CREATE TABLE visa_transformable(
   Id COUNTER,
   date_entre DATE NOT NULL,
   date_expiration DATE NOT NULL,
   date_delivrence DATE NOT NULL,
   Id_etat_civil INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id)
);

CREATE TABLE demande_visa(
   Id COUNTER,
   date_demande DATETIME NOT NULL,
   Id_passport INT NOT NULL,
   Id_type_visa INT NOT NULL,
   Id_type_demande_visa INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_passport) REFERENCES passport(Id),
   FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id),
   FOREIGN KEY(Id_type_demande_visa) REFERENCES type_demande_visa(Id)
);

CREATE TABLE statut_demande(
   Id COUNTER,
   date_statut DATETIME NOT NULL,
   Id_type_statut_demande INT NOT NULL,
   Id_demande_visa INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_type_statut_demande) REFERENCES type_statut_demande(Id),
   FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id)
);

CREATE TABLE visa(
   Id COUNTER,
   num_visa VARCHAR(50) NOT NULL,
   data_entre DATE NOT NULL,
   date_expiration DATE NOT NULL,
   date_delivrence DATE NOT NULL,
   Id_demande_visa INT NOT NULL,
   Id_ville INT NOT NULL,
   Id_etat_civil INT NOT NULL,
   Id_type_visa INT NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(num_visa),
   FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id),
   FOREIGN KEY(Id_ville) REFERENCES ville(Id),
   FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id),
   FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id)
);

CREATE TABLE statut_visa(
   Id COUNTER,
   date_statut DATETIME NOT NULL,
   Id_type_statut_visa INT NOT NULL,
   Id_visa INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_type_statut_visa) REFERENCES type_statut_visa(Id),
   FOREIGN KEY(Id_visa) REFERENCES visa(Id)
);

CREATE TABLE dossier(
   Id COUNTER,
   est_coche LOGICAL NOT NULL,
   Id_demande_visa INT NOT NULL,
   Id_champ_fournir INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id),
   FOREIGN KEY(Id_champ_fournir) REFERENCES champ_fournir(Id)
);

CREATE TABLE carte_resident(
   Id COUNTER,
   num VARCHAR(50) NOT NULL,
   Id_visa INT NOT NULL,
   PRIMARY KEY(Id),
   UNIQUE(num),
   FOREIGN KEY(Id_visa) REFERENCES visa(Id)
);

CREATE TABLE historique_passeport_visa(
   Id COUNTER,
   date_historique DATETIME NOT NULL,
   Id_passport INT NOT NULL,
   Id_visa INT NOT NULL,
   PRIMARY KEY(Id),
   FOREIGN KEY(Id_passport) REFERENCES passport(Id),
   FOREIGN KEY(Id_visa) REFERENCES visa(Id)
);
