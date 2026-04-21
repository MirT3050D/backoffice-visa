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