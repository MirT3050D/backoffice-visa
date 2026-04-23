# Guide pratique Spring Boot: Modeles, Controllers et Routes (copier-coller)

Ce document donne des modeles prets a l'emploi pour:
- Creer des modeles JPA (`@Entity`)
- Gerer les relations (`OneToOne`, `OneToMany`, `ManyToOne`, `ManyToMany`)
- Construire des controllers REST
- Definir les routes les plus utiles

> Package de base du projet: `com.example.visa`

---

## 1) Structure recommandee

```text
src/main/java/com/example/visa/
  model/
  repository/
  controller/
  dto/ (optionnel)
```

---

## 2) Dependances utiles (pom.xml)

Assure-toi d'avoir au minimum:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Ex: PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

---

## 3) Modele simple (`@Entity`) - pret a copier

### Fichier: `model/Applicant.java`

```java
package com.example.visa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "applicant")
public class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(unique = true, nullable = false, length = 150)
    private String email;

    public Applicant() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

---

## 4) Relations JPA utiles (avec exemples)

## 4.1 One-To-One

Cas typique: un `Applicant` a un seul `Passport`.

### `model/Passport.java`

```java
package com.example.visa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String number;

    @OneToOne
    @JoinColumn(name = "applicant_id", nullable = false, unique = true)
    private Applicant applicant;

    public Passport() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
```

### Variante bidirectionnelle (optionnelle)

Dans `Applicant`, ajouter:

```java
@OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
private Passport passport;
```

---

## 4.2 One-To-Many / Many-To-One

Cas typique: un `Applicant` peut avoir plusieurs `VisaRequest`.

### `model/VisaRequest.java` (cote MANY)

```java
package com.example.visa.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa_request")
public class VisaRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String destinationCountry;

    private LocalDate travelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private Applicant applicant;

    public VisaRequest() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public LocalDate getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
```

### `model/Applicant.java` (cote ONE)

Ajouter ce champ dans `Applicant`:

```java
import java.util.ArrayList;
import java.util.List;

@OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
private List<VisaRequest> visaRequests = new ArrayList<>();
```

---

## 4.3 Many-To-Many

Cas typique: une `VisaRequest` peut avoir plusieurs `Document`, et un `Document` peut etre lie a plusieurs demandes.

### `model/Document.java`

```java
package com.example.visa.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @ManyToMany(mappedBy = "documents")
    private Set<VisaRequest> visaRequests = new HashSet<>();

    public Document() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<VisaRequest> getVisaRequests() {
        return visaRequests;
    }

    public void setVisaRequests(Set<VisaRequest> visaRequests) {
        this.visaRequests = visaRequests;
    }
}
```

### Ajouter dans `model/VisaRequest.java`

```java
import java.util.HashSet;
import java.util.Set;

@ManyToMany
@JoinTable(
    name = "visa_request_document",
    joinColumns = @JoinColumn(name = "visa_request_id"),
    inverseJoinColumns = @JoinColumn(name = "document_id")
)
private Set<Document> documents = new HashSet<>();
```

---

## 4.4 Comprendre vite: JPA <-> SQL (scripts concrets)

Si tu veux retenir une seule idee:
- `@JoinColumn` = une cle etrangere dans une table
- `One-To-One` = cle etrangere + contrainte `UNIQUE`
- `One-To-Many` = cle etrangere du cote MANY
- `Many-To-Many` = table intermediaire (table de jointure)

### A) One-To-One (`Applicant` <-> `Passport`)

En JPA:

```java
@OneToOne
@JoinColumn(name = "applicant_id", nullable = false, unique = true)
private Applicant applicant;
```

Equivalent SQL (PostgreSQL):

```sql
-- Table principale
CREATE TABLE applicant (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

-- Table liee en one-to-one
CREATE TABLE passport (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(50) NOT NULL UNIQUE,
    applicant_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_passport_applicant
        FOREIGN KEY (applicant_id)
        REFERENCES applicant(id)
        ON DELETE CASCADE
);
```

Donnees de test:

```sql
INSERT INTO applicant (first_name, last_name, email)
VALUES ('Aina', 'Rakoto', 'aina.rakoto@mail.com');

INSERT INTO passport (number, applicant_id)
VALUES ('P1234567', 1);

-- Ceci va echouer (normal) car applicant_id doit etre unique
-- INSERT INTO passport (number, applicant_id) VALUES ('P9999999', 1);
```

Verification:

```sql
SELECT a.id, a.first_name, p.number
FROM applicant a
JOIN passport p ON p.applicant_id = a.id;
```

### B) One-To-Many / Many-To-One (`Applicant` -> `VisaRequest`)

En JPA (cote MANY):

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "applicant_id", nullable = false)
private Applicant applicant;
```

Equivalent SQL:

```sql
CREATE TABLE visa_request (
    id BIGSERIAL PRIMARY KEY,
    destination_country VARCHAR(100) NOT NULL,
    travel_date DATE,
    applicant_id BIGINT NOT NULL,
    CONSTRAINT fk_visa_request_applicant
        FOREIGN KEY (applicant_id)
        REFERENCES applicant(id)
        ON DELETE CASCADE
);
```

Donnees de test:

```sql
INSERT INTO visa_request (destination_country, travel_date, applicant_id)
VALUES
('France', '2026-07-15', 1),
('Canada', '2026-08-20', 1);
```

Verification:

```sql
-- Un applicant avec plusieurs demandes
SELECT a.id, a.first_name, vr.id AS visa_request_id, vr.destination_country
FROM applicant a
JOIN visa_request vr ON vr.applicant_id = a.id
WHERE a.id = 1;
```

### C) Many-To-Many (`VisaRequest` <-> `Document`)

En JPA:

```java
@ManyToMany
@JoinTable(
    name = "visa_request_document",
    joinColumns = @JoinColumn(name = "visa_request_id"),
    inverseJoinColumns = @JoinColumn(name = "document_id")
)
private Set<Document> documents = new HashSet<>();
```

Equivalent SQL:

```sql
CREATE TABLE document (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL
);

-- Table de jointure many-to-many
CREATE TABLE visa_request_document (
    visa_request_id BIGINT NOT NULL,
    document_id BIGINT NOT NULL,
    PRIMARY KEY (visa_request_id, document_id),
    CONSTRAINT fk_vrd_visa_request
        FOREIGN KEY (visa_request_id)
        REFERENCES visa_request(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_vrd_document
        FOREIGN KEY (document_id)
        REFERENCES document(id)
        ON DELETE CASCADE
);
```

Donnees de test:

```sql
INSERT INTO document (type)
VALUES ('Passeport scanne'), ('Photo identite'), ('Reservation billet');

-- La demande 1 a 2 documents
INSERT INTO visa_request_document (visa_request_id, document_id)
VALUES (1, 1), (1, 2);

-- La demande 2 reutilise un document + un autre
INSERT INTO visa_request_document (visa_request_id, document_id)
VALUES (2, 2), (2, 3);
```

Verification:

```sql
SELECT vr.id AS visa_request_id, d.id AS document_id, d.type
FROM visa_request vr
JOIN visa_request_document vrd ON vrd.visa_request_id = vr.id
JOIN document d ON d.id = vrd.document_id
ORDER BY vr.id, d.id;
```

### D) Version complete (copier-coller) dans l'ordre

```sql
DROP TABLE IF EXISTS visa_request_document;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS visa_request;
DROP TABLE IF EXISTS passport;
DROP TABLE IF EXISTS applicant;

CREATE TABLE applicant (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE passport (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(50) NOT NULL UNIQUE,
    applicant_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_passport_applicant
        FOREIGN KEY (applicant_id)
        REFERENCES applicant(id)
        ON DELETE CASCADE
);

CREATE TABLE visa_request (
    id BIGSERIAL PRIMARY KEY,
    destination_country VARCHAR(100) NOT NULL,
    travel_date DATE,
    applicant_id BIGINT NOT NULL,
    CONSTRAINT fk_visa_request_applicant
        FOREIGN KEY (applicant_id)
        REFERENCES applicant(id)
        ON DELETE CASCADE
);

CREATE TABLE document (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL
);

CREATE TABLE visa_request_document (
    visa_request_id BIGINT NOT NULL,
    document_id BIGINT NOT NULL,
    PRIMARY KEY (visa_request_id, document_id),
    CONSTRAINT fk_vrd_visa_request
        FOREIGN KEY (visa_request_id)
        REFERENCES visa_request(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_vrd_document
        FOREIGN KEY (document_id)
        REFERENCES document(id)
        ON DELETE CASCADE
);
```

---

## 5) Repository - pret a copier

### `repository/ApplicantRepository.java`

```java
package com.example.visa.repository;

import com.example.visa.model.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    Optional<Applicant> findByEmail(String email);
}
```

### `repository/VisaRequestRepository.java`

```java
package com.example.visa.repository;

import com.example.visa.model.VisaRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VisaRequestRepository extends JpaRepository<VisaRequest, Long> {
    List<VisaRequest> findByApplicantId(Long applicantId);
}
```

---

## 6) Controller REST - routes essentielles

### `controller/ApplicantController.java`

```java
package com.example.visa.controller;

import com.example.visa.model.Applicant;
import com.example.visa.repository.ApplicantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    // GET /api/applicants
    @GetMapping
    public List<Applicant> getAll() {
        return applicantRepository.findAll();
    }

    // GET /api/applicants/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Applicant> getById(@PathVariable Long id) {
        return applicantRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/applicants
    @PostMapping
    public ResponseEntity<Applicant> create(@RequestBody Applicant applicant) {
        Applicant saved = applicantRepository.save(applicant);
        return ResponseEntity
                .created(URI.create("/api/applicants/" + saved.getId()))
                .body(saved);
    }

    // PUT /api/applicants/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Applicant> update(@PathVariable Long id, @RequestBody Applicant payload) {
        return applicantRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(payload.getFirstName());
                    existing.setLastName(payload.getLastName());
                    existing.setEmail(payload.getEmail());
                    return ResponseEntity.ok(applicantRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/applicants/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!applicantRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        applicantRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 7) Controller pour une relation (One-To-Many)

### `controller/VisaRequestController.java`

```java
package com.example.visa.controller;

import com.example.visa.model.Applicant;
import com.example.visa.model.VisaRequest;
import com.example.visa.repository.ApplicantRepository;
import com.example.visa.repository.VisaRequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/visa-requests")
public class VisaRequestController {

    private final VisaRequestRepository visaRequestRepository;
    private final ApplicantRepository applicantRepository;

    public VisaRequestController(VisaRequestRepository visaRequestRepository,
                                 ApplicantRepository applicantRepository) {
        this.visaRequestRepository = visaRequestRepository;
        this.applicantRepository = applicantRepository;
    }

    // GET /api/visa-requests?applicantId=1
    @GetMapping
    public List<VisaRequest> getAll(@RequestParam(required = false) Long applicantId) {
        if (applicantId != null) {
            return visaRequestRepository.findByApplicantId(applicantId);
        }
        return visaRequestRepository.findAll();
    }

    // POST /api/visa-requests/applicant/{applicantId}
    @PostMapping("/applicant/{applicantId}")
    public ResponseEntity<VisaRequest> createForApplicant(@PathVariable Long applicantId,
                                                          @RequestBody VisaRequest visaRequest) {
        Applicant applicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant introuvable"));

        visaRequest.setApplicant(applicant);
        VisaRequest saved = visaRequestRepository.save(visaRequest);

        return ResponseEntity
                .created(URI.create("/api/visa-requests/" + saved.getId()))
                .body(saved);
    }
}
```

---

## 8) Rappel rapide des annotations de routes

- `@RestController`: controller REST (retour JSON)
- `@RequestMapping("/api/x")`: prefixe commun de routes
- `@GetMapping`: lecture
- `@PostMapping`: creation
- `@PutMapping`: remplacement/mise a jour complete
- `@PatchMapping`: mise a jour partielle
- `@DeleteMapping`: suppression
- `@PathVariable`: valeur dans l'URL (`/api/x/{id}`)
- `@RequestParam`: parametre query (`/api/x?page=1`)
- `@RequestBody`: corps JSON entrant

Exemple ultra-court:

```java
@GetMapping("/{id}")
public ResponseEntity<MyEntity> getOne(@PathVariable Long id) { ... }

@PostMapping
public ResponseEntity<MyEntity> create(@RequestBody MyEntity body) { ... }

@GetMapping
public List<MyEntity> search(@RequestParam(required = false) String keyword) { ... }
```

---

## 9) Exemples JSON de test

### POST ` /api/applicants`

```json
{
  "firstName": "Aina",
  "lastName": "Rakoto",
  "email": "aina.rakoto@mail.com"
}
```

### POST ` /api/visa-requests/applicant/1`

```json
{
  "destinationCountry": "France",
  "travelDate": "2026-07-15"
}
```

---

## 10) Bonnes pratiques importantes

- Evite de retourner directement des entites complexes avec relations bidirectionnelles sans DTO (risque de boucle JSON).
- Utilise `FetchType.LAZY` sur les relations volumineuses.
- Controle les suppressions avec `cascade` et `orphanRemoval`.
- Ajoute la validation (`jakarta.validation`) pour les champs obligatoires.

Exemple rapide de validation:

```java
@Column(nullable = false)
@NotBlank
private String firstName;
```

Et dans le controller:

```java
public ResponseEntity<Applicant> create(@Valid @RequestBody Applicant applicant) { ... }
```

---

Ce guide est concu pour etre copie/colle puis adapte a ton domaine metier (passeport, demande de visa, pieces justificatives, etc.).
