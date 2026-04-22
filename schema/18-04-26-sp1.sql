   CREATE TABLE type_demande_visa(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label)
   );

   CREATE TABLE type_visa(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label)
   );

   CREATE TABLE pays(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label)
   );

   CREATE TABLE ville(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      Id_pays INTEGER NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label),
      FOREIGN KEY(Id_pays) REFERENCES pays(Id)
   );

   CREATE TABLE situation_familiale(
      Id SERIAL,
      situation VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(situation)
   );

   CREATE TABLE nationalite(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label)
   );

   CREATE TABLE type_statut_visa(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      rang DOUBLE PRECISION NOT NULL,
      PRIMARY KEY(Id)
   );

   CREATE TABLE type_statut_demande(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      rang NUMERIC(15,2)   NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(label)
   );

   CREATE TABLE champ_fournir_specifique(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      type_donnee VARCHAR(50)  NOT NULL,
      Id_type_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id)
   );

   CREATE TABLE champ_fournir_commune(
      Id SERIAL,
      label VARCHAR(50)  NOT NULL,
      type_donnee VARCHAR(50)  NOT NULL,
      PRIMARY KEY(Id)
   );

   CREATE TABLE etat_civil(
      Id SERIAL,
      nom VARCHAR(50)  NOT NULL,
      prenoms VARCHAR(50) ,
      nom_jeune_fille VARCHAR(50) ,
      mail VARCHAR(50) ,
      num_tel VARCHAR(50)  NOT NULL,
      date_naissance DATE NOT NULL,
      lieu_naissance VARCHAR(50) ,
      adresse VARCHAR(50)  NOT NULL,
      Id_nationalite INTEGER NOT NULL,
      Id_situation_familiale INTEGER NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(mail),
      UNIQUE(num_tel),
      FOREIGN KEY(Id_nationalite) REFERENCES nationalite(Id),
      FOREIGN KEY(Id_situation_familiale) REFERENCES situation_familiale(Id)
   );

   CREATE TABLE passport(
      Id SERIAL,
      num_passport VARCHAR(50)  NOT NULL,
      date_expiration VARCHAR(50)  NOT NULL,
      date_delivrence DATE NOT NULL,
      Id_etat_civil INTEGER NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(num_passport),
      FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id)
   );

   CREATE TABLE visa_transformable(
      Id SERIAL,
      date_entre DATE NOT NULL,
      date_expiration DATE NOT NULL,
      date_delivrence DATE NOT NULL,
      Id_etat_civil INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id)
   );

   CREATE TABLE demande_visa(
      Id SERIAL,
      date_demande TIMESTAMP NOT NULL,
      Id_passport INTEGER NOT NULL,
      Id_type_visa INTEGER NOT NULL,
      Id_type_demande_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_passport) REFERENCES passport(Id),
      FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id),
      FOREIGN KEY(Id_type_demande_visa) REFERENCES type_demande_visa(Id)
   );

   CREATE TABLE statut_demande(
      Id SERIAL,
      date_statut TIMESTAMP NOT NULL,
      Id_type_statut_demande INTEGER NOT NULL,
      Id_demande_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_type_statut_demande) REFERENCES type_statut_demande(Id),
      FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id)
   );

   CREATE TABLE visa(
      Id SERIAL,
      num_visa VARCHAR(50)  NOT NULL,
      data_entre DATE NOT NULL,
      date_expiration DATE NOT NULL,
      date_delivrence DATE NOT NULL,
      Id_demande_visa INTEGER NOT NULL,
      Id_ville INTEGER NOT NULL,
      Id_etat_civil INTEGER NOT NULL,
      Id_type_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(num_visa),
      FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id),
      FOREIGN KEY(Id_ville) REFERENCES ville(Id),
      FOREIGN KEY(Id_etat_civil) REFERENCES etat_civil(Id),
      FOREIGN KEY(Id_type_visa) REFERENCES type_visa(Id)
   );

   CREATE TABLE statut_visa(
      Id SERIAL,
      date_statut TIMESTAMP NOT NULL,
      Id_type_statut_visa INTEGER NOT NULL,
      Id_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_type_statut_visa) REFERENCES type_statut_visa(Id),
      FOREIGN KEY(Id_visa) REFERENCES visa(Id)
   );

   CREATE TABLE dossier(
      Id SERIAL,
      est_coche BOOLEAN NOT NULL,
      Id_demande_visa INTEGER NOT NULL,
      Id_champ_fournir_specifique INTEGER,
      Id_champ_fournir_commune INTEGER,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_demande_visa) REFERENCES demande_visa(Id),
      FOREIGN KEY(Id_champ_fournir_specifique) REFERENCES champ_fournir_specifique(Id),
      FOREIGN KEY(Id_champ_fournir_commune) REFERENCES champ_fournir_commune(Id),
      CONSTRAINT ck_dossier_one_champ CHECK (
         (Id_champ_fournir_specifique IS NOT NULL AND Id_champ_fournir_commune IS NULL)
         OR
         (Id_champ_fournir_specifique IS NULL AND Id_champ_fournir_commune IS NOT NULL)
      )
   );

   CREATE TABLE carte_resident(
      Id SERIAL,
      num VARCHAR(50)  NOT NULL,
      Id_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      UNIQUE(num),
      FOREIGN KEY(Id_visa) REFERENCES visa(Id)
   );

   CREATE TABLE historique_passeport_visa(
      Id SERIAL,
      date_historique TIMESTAMP NOT NULL,
      Id_passport INTEGER NOT NULL,
      Id_visa INTEGER NOT NULL,
      PRIMARY KEY(Id),
      FOREIGN KEY(Id_passport) REFERENCES passport(Id),
      FOREIGN KEY(Id_visa) REFERENCES visa(Id)
   );
