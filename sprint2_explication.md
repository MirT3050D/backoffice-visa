# Documentation Sprint 2 - Gestion des Duplicatas (Sans Données Antérieures)

Ce document explique le fonctionnement technique et le flux métier mis en place lors du **Sprint 2** pour gérer spécifiquement la création de **demandes de duplicata (ou transfert) de visa sans données** préalablement enregistrées dans le système.

## 1. Contexte et Enjeu Métier
Normalement, un duplicata s'applique à un visa existant dans le système (qui doit être généré depuis une demande "Nouveau Titre" ayant atteint le statut "Approuvé"). 
Cependant, pour un **Duplicata sans données intérieures**, le demandeur possède un visa valide physiquement, mais le système n'en a aucune trace. 
**Solution adoptée** : Créer de manière transparente et automatique une "fausse" demande initiale de type "Nouveau Titre", l'approuver directement, générer le visa virtuel correspondant, puis attacher la demande de duplicata à ce visa simulé.

## 2. Architecture et Flux de Données

Le développement implique une connexion fluide entre plusieurs formulaires Frontend (JSP), un agrégateur de données (Controller) et la logique métier transactionnelle (Service).

### A. Frontend (Vues JSP)
1. **`passport-form.jsp`** : Le demandeur saisit ses informations personnelles et celles de son passeport.
2. **`select-visa.jsp`** : Le demandeur choisit le type de visa pour le duplicata.
3. **`saisie-visa-dossiers.jsp`** : Dernière étape où l'utilisateur fournit les pièces jointes (dossiers) mais **surtout**, saisit les informations de l'ancien visa physique (numéro, date de délivrance, date d'expiration). Les champs (ex: `ancienNumeroVisa`, `ancienDateDelivrance`) correspondent exactement au DTO attendu par le serveur.

### B. Le Contrôleur (`DemandeVisaController.java`)
Le contrôleur orchestre le transfert des données entre les vues grâce aux sessions (`@SessionAttributes("passeportData")`).

Lors du POST final vers `/finaliser-duplicata` :
* Le contrôleur récupère l'objet `passeportData` stocké en session (Infos du demandeur, passeport).
* Il récupère le `FinaliserSansDonneesForm` provenant du formulaire de dossiers (Infos de l'ancien visa).
* Il fusionne ces informations dans l'objet final `form` avant de l'envoyer au service métier.

```java
@PostMapping("/finaliser-duplicata")
public String finaliserDuplicata(
        @ModelAttribute("form") FinaliserSansDonneesForm form,
        @ModelAttribute("passeportData") PasseportForm passeportForm, ...) {
    // 1. Fusion des données issues des différentes étapes
    // 2. Appel au service : demandeVisaService.creerDemandeDuplicatatSansDonnees(form);
}
```

### C. Les DTOs (Data Transfer Objects)
* **`PasseportForm`** : Structure qui porte l'État civil et le Passeport.
* **`FinaliserSansDonneesForm`** : Hérite de `CreerDemandeVisaForm` et y ajoute les attributs de l'ancien visa (`ancienNumeroVisa`, `ancienDateDelivrance`, `ancienDateExpiration`, etc.).

### D. Le Service (`DemandeVisaService.java`)
C'est le cœur du système. Sa méthode `creerDemandeDuplicatatSansDonnees` effectue les opérations en base dans une transaction (`@Transactional`) :

1. **Création d'une Demande Fictive** :
   Fait appel à la fonction standard `creerDemandeVisa(...)` mais avec des arguments spécifiques : 
   `demandeVisaService.creerDemandeVisa(form, 1L, 5);`
   - `1L` correspond au type de demande "Nouveau Titre".
   - `5` correspond au rang initial "Approuvé" (contrairement à "Créé" : rang 1 d'une demande classique).
   
2. **Simulation du "Passé"** :
   La création de cette demande "Approuvée" génère les informations nécessaires (État civil, Passeport). Le service simule ensuite l'existence de l'ancien visa (génération de l'entité `Visa`) avec les données transmises (`ancienNumeroVisa`, etc.).
   
3. **Création de la Vraie Demande** :
   La demande réelle de duplicata est alors enregistrée et reliée au visa généré à l'étape 2.

## 3. Résumé des Nouveautés Techniques
* L'ajout d'un argument `statutInitialRang` dans la signature logicielle de création de visa, permettant la flexibilité entre une demande "standard" (rang 1) et une demande "simulée" (rang 5).
* L'utilisation systématique de l'héritage et la session (`@SessionAttributes`) pour gérer le parcours multi-étapes sans persister partiellement les données en base.
* Le mapping précis des attributs entre les champs HTML de `saisie-visa-dossiers.jsp` et les propriétés des DTOs Java.

*Dernière mise à jour : 28 Avril 2026*