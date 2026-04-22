# Explication des Méthodes (Contrôleur et Service)

Ce document explique en détail les méthodes utilisées dans le contrôleur `DemandeVisaController` et le service `DemandeVisaService`, en mettant l'accent sur les annotations et syntaxes particulières propres à Spring Boot.

## 1. Le Contrôleur (`DemandeVisaController.java`)

Le contrôleur est responsable de la gestion des requêtes HTTP (les routes) et de la liaison entre la vue et la logique métier.

### Annotations de classe :
- `@Controller` : Indique à Spring que cette classe est un contrôleur Web (générant des pages HTML, contrairement à `@RestController` qui génère du JSON).
- `@RequestMapping("/demande-visa")` : Permet de préfixer toutes les routes de ce contrôleur par `/demande-visa`.

### A. La méthode `creerDemandeVisa(Model model)`

```java
@GetMapping("/creer")
public String creerDemandeVisa(Model model) {
    model.addAttribute("form", new CreerDemandeVisaForm());
    return "creer_demande_visa";
}
```

**Explications et syntaxes particulières :**
- `@GetMapping("/creer")` : Intercepte les requêtes HTTP de type `GET` vers l'URL `/demande-visa/creer`.
- `Model model` : L'objet `Model` sert à passer des données (des variables) du contrôleur vers la vue (le fichier JSP).
- `model.addAttribute("form", new CreerDemandeVisaForm());` : On instancie un objet vide de notre DTO (`CreerDemandeVisaForm`) et on l'injecte dans le modèle sous le nom `"form"`. C'est cet objet que le formulaire HTML utilisera pour sier (binder) ses champs.
- `return "creer_demande_visa";` : Retourne le nom de la vue à afficher (le fichier `creer_demande_visa.jsp` situé dans vos dossiers de templates ou vues).

### B. La méthode `enregistrerDemandeVisa(...)`

```java
@PostMapping("/creer")
public String enregistrerDemandeVisa(
        @Valid @ModelAttribute("form") CreerDemandeVisaForm form,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes) {
    
    // ...
```

**Explications et syntaxes particulières :**
- `@PostMapping("/creer")` : Intercepte la soumission du formulaire (requête `POST` vers `/demande-visa/creer`).
- `@Valid` : Demande à Spring de valider l'objet avant d'entrer dans la méthode (selon les annotations `@NotNull`, `@NotBlank` définies dans le DTO).
- `@ModelAttribute("form")` : Indique que l'objet `form` provient des données envoyées par le formulaire et liées au modèle `"form"`.
- `BindingResult bindingResult` : Contient le résultat de la validation de l'objet (via `@Valid`). S'il y a des erreurs, `bindingResult.hasErrors()` est `true`, et on renvoie le formulaire avec les messages d'erreur. *(Attention: cet argument doit obligatoirement suivre l'objet à valider).*
- `RedirectAttributes redirectAttributes` : Permet de faire passer des données à l'étape suivante lors d'une redirection (ex: un flash message de succès avec `addFlashAttribute`).
- `return "redirect:/demande-visa/creer";` : Redirige l'utilisateur vers une autre URL (au lieu de juste renvoyer une vue) afin d'éviter la soumission en double (Pattern Post-Redirect-Get).


---

## 2. Le Service (`DemandeVisaService.java`)

Le service contient toute la logique métier. Ici, la classe extrait les informations du DTO pour créer et sauvegarder les différentes entités en base de données.

### Annotations de classe :
- `@Service` : Indique à Spring que cette classe est un composant de type Service, et Spring l'instanciera automatiquement (Injection de dépendance).

### A. La méthode `creerDemandeVisa(CreerDemandeVisaForm form)`

```java
@Transactional
public DemandeVisa creerDemandeVisa(CreerDemandeVisaForm form) {
    // ... 
    Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
            .orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));
    // ...
}
```

**Explications et syntaxes particulières :**
- `@Transactional` : Cruciale lorsqu'on insère des données dans plusieurs tables liées. Si l'une des requêtes (ex: sauvegarde du Passeport) échoue, toutes les autres opérations précédentes (ex: sauvegarde de l'EtatCivil) sont annulées (Rollback). Cela garantit l'intégrité de la base de données.
- `.orElseThrow(() -> new IllegalArgumentException(...))` : Les méthodes de type `findById` dans Spring Data JPA retournent un objet `Optional<T>`. Cette syntaxe avec une fonction lambda (flèche `->`) dit : "soit tu me retournes l'objet s'il existe, soit tu lèves une exception".

**Workflow au sein de la méthode :**
1. **Validation et récupération des dépendances :** On va chercher les entités liées (`Nationalite`, `SituationFamiliale`, `TypeDemandeVisa`, etc.) à partir des IDs envoyés dans le DTO `form`.
2. **Assemblage :** On peuple manuellement des nouvelles instances (`EtatCivil`, `Passeport`, et `DemandeVisa`) avec les valeurs issues du DTO et les dépendances récupérées.
3. **Sauvegardes cascadées / séquentielles :** 
    - L'état civil est sauvegardé pour obtenir un ID.
    - Le passeport est sauvegardé en lui associant l'état civil précédemment inséré.
    - La demande de visa est sauvegardée en associant tout le reste.
    - On retourne enfin l'objet final persisté en base de données de manière transactionnelle.
