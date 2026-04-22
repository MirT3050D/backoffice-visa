# 📋 Structure des Pages - Demande de Visa

## Vue d'ensemble du flux utilisateur

```
index.jsp (Types de demandes)
    ↓
type-visa.jsp (Sélection du type: Travailleur/Investisseur)
    ↓
visa-travailleur.jsp (Formulaire de demande détaillé)
    ↓
confirmation-visa.jsp (Récapitulatif avant soumission)
    ↓
soumettre-demande-travailleur (Traitement - Backend)
```

---

## 📄 Description des Pages

### 1. **index.jsp** - Page d'Accueil
**Chemin:** `/src/main/webapp/WEB-INF/jsp/index.jsp`

**Rôle:** Page d'accueil du système de gestion des visas

**Contenu:**
- Titre principal et description du backoffice
- Grille de cartes affichant les types de demandes disponibles
- Actuellement: "Demande de Visa" (avec liens vers type-visa.jsp)

**Navigation:**
- Bouton "Commencer" → redirige vers `type-visa.jsp`

---

### 2. **type-visa.jsp** - Sélection du Type de Visa
**Chemin:** `/src/main/webapp/WEB-INF/jsp/type-visa.jsp`

**Rôle:** Permet à l'utilisateur de choisir entre les différents types de visa

**Contenu (Etape 1/3):**
- **Visa Travailleur:** 
  - Description: "Pour les ressortissants étrangers souhaitant travailler"
  - Fonctionnalités: Contrat de travail, Diplômes et qualifications, Visite médicale
  - Bouton: "Continuer" → `visa-travailleur.jsp`

- **Visa Investisseur:** 
  - Description: "Pour les investisseurs et créateurs d'entreprise"
  - Fonctionnalités: Plan d'affaires, Preuves financières, Statut d'entreprise
  - Bouton: "Bientôt disponible" (placeholder)

**Caractéristiques:**
- Cartes sélectionnables avec effet hover amélioré
- Icônes SVG représentant chaque type
- Listes de fonctionnalités spécifiques

---

### 3. **visa-travailleur.jsp** - Formulaire de Demande
**Chemin:** `/src/main/webapp/WEB-INF/jsp/visa-travailleur.jsp`

**Rôle:** Collecte les informations complètes pour une demande de visa travailleur

**Contenu (Etape 2/3):**

#### Section 1: Informations Personnelles
- Nom, Prénoms, Nom de Jeune Fille
- Date et lieu de naissance
- Nationalité (select dropdown)
- Email et numéro de téléphone
- Adresse de résidence
- Situation familiale (select dropdown)

#### Section 2: Informations Passeport
- Numéro de passeport
- Date de délivrance
- Date d'expiration

#### Section 3: Informations Professionnelles (Spécifique au Visa Travailleur)
- Poste proposé
- Secteur d'activité (select dropdown)
- Nom de l'entreprise
- Ville de travail (select dropdown)
- Adresse de l'entreprise
- Date de début d'emploi
- Salaire proposé (en EUR)

#### Section 4: Diplômes et Qualifications
- Niveau d'étude maximal (select dropdown)
- Diplôme principal
- Expérience professionnelle (en années)
- Compétences particulières (textarea)

#### Section 5: Visite Médicale
- Visite médicale effectuée (Oui/Non)
- Date de visite
- Problèmes de santé particuliers (textarea)

#### Section 6: Documents Joints
- Contrat de travail (PDF, DOC, DOCX) - Obligatoire
- Certificats de diplôme (PDF, JPG, PNG) - Obligatoire
- Rapport médical (PDF, JPG, PNG) - Optionnel
- Autres documents justificatifs (PDF, JPG, PNG, DOC, DOCX) - Optionnel

#### Section 7: Acceptance et Soumission
- 3 cases à cocher pour les confirmations légales
  - Exactitude des informations
  - Autorisation aux autorités
  - Acceptation des conditions

**Validation:**
- Tous les champs marqués avec "*" sont obligatoires
- Les fichiers sont limités à 5 MB
- Formats acceptés sont spécifiés

---

### 4. **confirmation-visa.jsp** - Récapitulatif
**Chemin:** `/src/main/webapp/WEB-INF/jsp/confirmation-visa.jsp`

**Rôle:** Affiche un résumé complet de la demande pour vérification avant soumission

**Contenu (Etape 3/3):**

Affiche en lecture seule:
- ✅ Informations Personnelles
- ✅ Informations Passeport
- ✅ Informations Professionnelles
- ✅ Éducation et Qualifications
- ✅ Informations de Santé
- ✅ Documents Joints (liste des fichiers uploadés)

**Navigation:**
- Bouton "Modifier" → retour à `visa-travailleur.jsp`
- Bouton "Confirmer et Soumettre" → POST vers `soumettre-demande-travailleur`

**Alerte Important:**
- Rappel à l'utilisateur de vérifier toutes les informations

---

## 🎨 Composants Réutilisables

### Header (components/header.jsp)
```jsp
<jsp:include page="components/header.jsp" />
```
- Navigation sticky au-dessus
- Logo et titre "VISA SYSTEM"
- Bouton "Retour" automatique (sauf sur index.jsp)

### Footer (components/footer.jsp)
```jsp
<jsp:include page="components/footer.jsp" />
```
- Copyright et informations légales

---

## 🎯 Flux des Données

### Checkpoints de Validation:

1. **type-visa.jsp** → Sélection du type
2. **visa-travailleur.jsp** → Saisie des données (champs obligatoires)
3. **confirmation-visa.jsp** → Révision avant soumission
4. **Backend** → Traitement et enregistrement en base de données

L'URL d'action du formulaire: `soumettre-demande-travailleur`

---

## 📱 Design Responsive

- **Desktop (> 768px):** Grille 2-3 colonnes
- **Tablet (768px):** Grille 1-2 colonnes  
- **Mobile (< 480px):** Grille 1 colonne, boutons en largeur complète

---

## 🔄 Extensibilité Future

Pour ajouter un nouveau type de visa (ex: Visa Investisseur):

1. Créer une nouvelle page `visa-investisseur.jsp`
2. Ajouter une nouvelle card dans `type-visa.jsp` 
3. Ajouter les champs spécifiques à l'investisseur
4. Créer `confirmation-investisseur.jsp` si nécessaire
5. Implémenter l'action backend `soumettre-demande-investisseur`

> ⚠️ **Note:** Les pages actuelles sont statiques (HTML/CSS/JS côté client). Pour le fonctionnement complet, des contrôleurs Spring Boot seront nécessaires pour traiter les soumissions.

---

## 📊 Classes CSS Principales

### Cartes
- `.card` - Conteneur de carte
- `.card.selectable` - Cartes cliquables
- `.card-header`, `.card-body`, `.card-footer` - Sections de la carte

### Formulaires
- `.visa-form` - Conteneur du formulaire
- `.form-section` - Section du formulaire
- `.form-row` - Rangée de champs
- `.form-group` - Groupe de champs individuels
- `.form-navigation` - Navigation du formulaire

### Utilitaires
- `.full-width` - Champ pleine largeur
- `.visa-features` - Listes de fonctionnalités

---

## 🔗 Routes Actuellement Définies

```
GET  /                          → index.jsp
GET  /type-visa                 → type-visa.jsp
GET  /visa-travailleur          → visa-travailleur.jsp
GET  /confirmation-visa         → confirmation-visa.jsp
POST /soumettre-demande-travailleur → [À implémenter - Backend]
```

> ⚠️ Les routes sont actuellement statiques. Le contrôleur Spring Boot doit les exposer.
