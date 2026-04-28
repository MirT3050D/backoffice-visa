# Résumé de la réalisation du Sprint 1 - Backoffice Visa

## 1. Objectifs du Sprint 1
L'objectif principal de ce premier sprint était de mettre en place le socle de l'application : **le processus classique de création et de gestion d'une demande de visa**.
Il s'agissait d'implémenter les opérations CRUD de base tout en assurant une structure de données solide et conforme aux scripts de base de données fournis.

## 2. Développements réalisés

### A. Modélisation Fondamentale (JPA/Hibernate)
- Création de l'entité centrale `DemandeVisa` pour stocker les informations principales de la demande.
- Implémentation du système de suivi d'état avec `StatutDemande` et `TypeStatutDemande`.
- Gestion des documents et champs requis via l'entité `Dossier`, faisant le lien avec `ChampFournirCommune` et `ChampFournirSpecifique` en fonction du type de visa demandé.
- **Conformité stricte :** Alignement minutieux des modèles Java avec le fichier SQL `18-04-26-sp1.sql` (ex: utilisation de `ordre_statut`, des types `TIMESTAMP`, etc.).

### B. Contrôleurs et Interfaces (JSP)
- Mise en place des formulaires d'initialisation de demande.
- Gestion de la session utilisateur pour conserver les données de formulaire au fil des étapes (wizards).

### C. Logique Métier (`DemandeVisaService.java`)
- Création de la méthode clé `creerDemandeVisa(...)` qui orchestre l'enregistrement de la demande de base.
- Traitement automatique de la liste des champs à fournir (`Dossier`) selon le type de visa choisi.
- Initialisation automatique du statut de la demande dès sa création via `creerStatutInitial(savedDemandeVisa)`, qui attribue le tout premier état (ordre de statut = 1), marquant ainsi le point de départ du cycle de vie du visa.

## 3. Points d'attention et Stabilisation
Ce sprint constitue la "base saine" du projet. Il a été formellement validé que :
- Le cycle de validation des champs requis par type de visa fonctionne.
- L'historique d'état commence correctement dès l'insertion en base de données.
- **Principe d'immuabilité vis-à-vis des autres sprints :** Les évolutions futures (telles que le Sprint 2 pour les duplicatas) ont été conçues de manière à s'appuyer sur cette base sans en altérer le mécanisme (préservation de l'appel à la fonction d'état initial lors des créations standard).

## 4. Bilan
Le système prend en charge un cycle de vie complet de soumission de demande de visa classique, avec un suivi rigoureux des pièces justificatives et du statut de traitement de chaque demande.