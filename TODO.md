# Todo - Sprint 1 : Creation d'une Nouvelle Demande de Visa

## Frontend (Amboara)
- [ ] Creation de la page de creation d'une demande (nouveau titre)
- [ ] Creer le composant/section "Informations d'Etats Civils" :
  - [ ] Formulaire pour les champs de etat_civil.
  - [ ] Validation des champs obligatoires: nom, date_naissance, adresse, nationalite, num_tel.
  - [ ] Rendre optionnels les autres champs (prenoms, mail, nom_jeune_fille, etc.).
  - [ ] Formulaire pour les champs de passport.
  - [ ] Formulaire pour les champs de visa_transformable.
- [ ] Creer le composant/section "Informations de Dossiers" (dynamique selon le type de visa) :
  - [ ] Formulaire dynamique pour afficher les champ_fournir_commune et champ_fournir_specifique selon le type de visa choisi.
  - [ ] Ajouter les checkBox est_coche pour ces dossiers.
- [ ] Creation de la page liste de demande.
- [ ] Apres succes de l'insertion, redirection vers la liste de demande.

## Backend (Spring Boot + PostgreSQL) (Mira)

### Classes a creer (obligatoires)

- [ ] Entity (pour modeliser les tables PostgreSQL et appliquer les regles metier de base au niveau objet)
  - [ ] EtatCivil
  - [ ] Passport
  - [ ] VisaTransformable
  - [ ] ChampFournirCommune
  - [ ] ChampFournirSpecifique
  - [ ] DemandeVisa
  - [ ] TypeStatutDemande
  - [ ] StatutDemande
  - [ ] Dossier

- [ ] Repository (pour lire/ecrire les donnees en base via Spring Data JPA)
  - [ ] EtatCivilRepository
  - [ ] PassportRepository
  - [ ] VisaTransformableRepository
  - [ ] ChampFournirCommuneRepository
  - [ ] ChampFournirSpecifiqueRepository
  - [ ] DemandeVisaRepository
  - [ ] TypeStatutDemandeRepository
  - [ ] StatutDemandeRepository
  - [ ] DossierRepository

- [ ] DTO (pour transporter les donnees entre front, controller et service sans exposer directement les entities)
  - [ ] NouvelleDemandeDTO
    - Contenu attendu:
      - etatCivil: EtatCivilDTO
      - passport: PassportDTO (ou champs passport inline)
      - visaTransformable: VisaTransformableDTO (ou champs inline)
      - idTypeVisa: Long
      - idTypeDemandeVisa: Long
      - dossiers: List<DossierSaisieDTO>
    - Role: payload principal envoye par le front au POST /api/demandes.
  - [ ] EtatCivilDTO
    - Contenu attendu:
      - nom: String (obligatoire)
      - dateNaissance: LocalDate (obligatoire)
      - adresse: String (obligatoire)
      - idNationalite: Long (obligatoire)
      - numTel: String (obligatoire)
      - prenoms: String (facultatif)
      - mail: String (facultatif)
      - nomJeuneFille: String (facultatif)
      - lieuNaissance: String (selon schema)
      - idSituationFamiliale: Long (selon schema)
    - Role: transporter les donnees etat civil sans exposer Entity EtatCivil.
  - [ ] ChampDefinition
    - Contenu attendu:
      - fieldName: String
      - label: String
      - type: String (text, date, number, checkbox, select)
      - required: boolean
      - order: Integer
      - options: List<OptionDTO> (si select)
    - Role: decrire un champ de formulaire pour generation dynamique front.
  - [ ] ChampFournirCommuneDTO
    - Contenu attendu:
      - idChampFournirCommune: Long
      - label: String
      - typeDonnee: String
      - obligatoire: boolean (si la regle existe)
      - idTypeVisa: Long
    - Role: representer les pieces/champs communs a fournir selon le type de visa.
  - [ ] ChampFournirSpecifiqueDTO
    - Contenu attendu:
      - idChampFournirSpecifique: Long
      - label: String
      - typeDonnee: String
      - obligatoire: boolean (si la regle existe)
      - idTypeVisa: Long
    - Role: representer les pieces/champs specifiques a fournir selon le type de visa.
  - [ ] DossierSaisieDTO
    - Contenu attendu:
      - idChampFournirCommune: Long (nullable)
      - idChampFournirSpecifique: Long (nullable)
      - estCoche: boolean
      - valeur: String (optionnel, si un champ demande une valeur)
    - Role: capturer ce que l'utilisateur a coche/saisi pour chaque dossier (commun ou specifique).
  - [ ] DemandeVisaListDTO
    - Contenu attendu:
      - idDemande: Long
      - dateDemande: LocalDateTime
      - nomComplet: String
      - typeVisaLabel: String
      - statutCourant: String
      - numPassport: String
    - Role: DTO de sortie pour la page "liste des demandes".

- [ ] Service (pour centraliser la logique metier: generation de formulaire, creation de demande, statut initial, dossiers)
  - [ ] FormulaireService
  - [ ] DemandeVisaService

- [ ] Controller (pour exposer les endpoints REST au frontend)
  - [ ] FormulaireController
  - [ ] DemandeVisaController

### Pseudo-code a mettre dans les fichiers backend

- [ ] FormulaireService.java
```java
// Retourne les champs techniques d'une entite pour generation du formulaire front
public List<ChampDefinition> extraireChamps(Class<?> entiteClass);

// Retourne tous les blocs de formulaire de base
// etat_civil + passport + visa_transformable
public Map<String, List<ChampDefinition>> getFormStructureBase();

// Retourne les champs dossiers communs selon le type de visa
public List<ChampFournirCommuneDTO> getChampsFournirCommunsParTypeVisa(Long idTypeVisa);

// Retourne les champs dossiers specifiques selon le type de visa
public List<ChampFournirSpecifiqueDTO> getChampsFournirSpecifiquesParTypeVisa(Long idTypeVisa);
```

- [ ] EtatCivil.java (meme logique pour DTO EtatCivilDTO)
```java
public EtatCivil(String nom, LocalDate dateNaissance, String adresse, Long idNationalite, String numTel,
                 String prenoms, String mail, String nomJeuneFille) {
    // Controle obligatoire via setters appeles dans le constructeur
    setNom(nom);
    setDateNaissance(dateNaissance);
    setAdresse(adresse);
    setIdNationalite(idNationalite);
    setNumTel(numTel);

    // Champs facultatifs acceptent null
    setPrenoms(prenoms);
    setMail(mail);
    setNomJeuneFille(nomJeuneFille);
}

public void setNom(String nom) {
    if (nom == null || nom.isBlank()) throw new IllegalArgumentException("nom obligatoire");
    this.nom = nom;
}
// setDateNaissance, setAdresse, setIdNationalite, setNumTel => meme principe obligatoire
```

- [ ] DemandeVisaService.java
```java
@Transactional
public DemandeVisa creerDemande(NouvelleDemandeDTO dto) {
    // 1) Construire EtatCivil via constructeur/setters (valide obligatoire)
    // 2) Si exception de validation => rollback, demande non creee
    // 3) Enregistrer Passport et VisaTransformable
    // 4) Creer DemandeVisa
    // 5) Creer StatutDemande initial avec TypeStatutDemande = "DEMANDE_CREEE"
    // 6) Enregistrer tous les Dossier saisis (communs + specifiques, est_coche true/false)
    // 7) Retourner la demande creee
}

public List<DemandeVisaListDTO> listerToutesLesDemandes();
```

- [ ] DemandeVisaController.java
```java
@PostMapping("/api/demandes")
public ResponseEntity<DemandeVisaListDTO> creer(@RequestBody NouvelleDemandeDTO dto);

@GetMapping("/api/demandes")
public ResponseEntity<List<DemandeVisaListDTO>> lister();
```

- [ ] FormulaireController.java
```java
@GetMapping("/api/formulaires/structure")
public ResponseEntity<Map<String, List<ChampDefinition>>> structureBase();

@GetMapping("/api/formulaires/champs-fournir/communs")
public ResponseEntity<List<ChampFournirCommuneDTO>> champsFournirCommuns(@RequestParam Long idTypeVisa);

@GetMapping("/api/formulaires/champs-fournir/specifiques")
public ResponseEntity<List<ChampFournirSpecifiqueDTO>> champsFournirSpecifiques(@RequestParam Long idTypeVisa);
```
## Integration (Steeve)


Todo Sprint2 27/04/2026
Affichage : Amboara
  Ajouter une page pour la demande de duplicata (apres le choix du type de demande)
    Reprendre les memes champs que la demande de visa
    Ajouter un bouton "sans donnees interieures"
    Afficher la liste des visas disponibles (issus des demandes de visa)
  Ajouter une page pour la demande de transfert de visa (apres le choix du type de demande)
    Ajouter un bouton "sans donnees interieures"
    Reprendre les memes champs que la demande de visa
    Afficher la liste des visas disponibles (issus des demandes de visa)
  Ajouter la validation front des champs obligatoires pour les parcours duplicata et transfert
  Ajouter la redirection vers la liste des demandes apres une creation reussie
    
Métier : Steeve
    Tache: Gestion des demandes de visa
      Fonction: CreateDemandeVisaValide((attribut DemandeVisa))
        Description: Cree une demande de visa valide, enregistre les informations principales et initialise le statut de la demande.
        Classe: DemandeVisaService, DemandeVisaRepository, DemandeVisaController
        Base: demande_visa, statut_demande, dossier, passport, etat_civil, visa_transformable

    Tache: Gestion des duplicata
      Fonction: CreateDuplicata((attribut Duplicata))
        Description: Cree une demande de duplicata a partir d'un visa existant et trace son cycle de traitement.
        Classe: DuplicataService, DuplicataRepository, DuplicataController
        Base: duplicata, statut_demande, demande_visa, visa

    Tache: Gestion des transferts de visa
      Fonction: CreateTransfertVisa((attribut TransfertVisa))
        Description: Cree une demande de transfert de visa en liant les donnees du visa source et le nouveau contexte de transfert.
        Classe: TransfertVisaService, TransfertVisaRepository, TransfertVisaController
        Base: transfert_visa, statut_demande, demande_visa, visa

    Tache: Creation des classes et modeles
      Fonction: Creer les classes metier, DTO et modeles necessaires
        Description: Met en place les classes techniques et objets de donnees pour supporter les parcours demande, duplicata et transfert.
        Classe: DemandeVisa, Duplicata, TransfertVisa, DemandeVisaCreateDTO, DemandeVisaListDTO, DuplicataCreateDTO, DuplicataListDTO, TransfertVisaCreateDTO, TransfertVisaListDTO
        Base: demande_visa, duplicata, transfert_visa, dossier, statut_demande

    Tache: Validation, statuts et tests
      Fonction: Implementer les regles metier, les statuts initiaux et les tests
        Description: Applique les controles metier, initialise les statuts de suivi et garantit la qualite via des tests automatises.
        Classe: DemandeVisaService, DuplicataService, TransfertVisaService
        Base: type_statut_demande, statut_demande, demande_visa, duplicata, transfert_visa

Base: Liste des tables a utiliser pour le metier
  type_demande_visa
  type_visa
  type_statut_demande
  etat_civil
  passport
  visa_transformable
  demande_visa
  statut_demande
  visa
  dossier
  duplicata
  transfert_visa