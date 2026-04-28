package com.example.visa.service;
import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.dto.DemandeVisaEditForm;
import com.example.visa.dto.FinaliserSansDonneesForm;
import com.example.visa.model.*;
import com.example.visa.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class DemandeVisaService {
	private final DemandeVisaRepository demandeVisaRepository;
	private final EtatCivilRepository etatCivilRepository;
	private final PasseportRepository passeportRepository;
	private final NationaliteRepository nationaliteRepository;
	private final SitutationFamilialeRepository situtationFamilialeRepository;
	private final TypeDemandeVisaRepository typeDemandeVisaRepository;
	private final TypeVisaRepository typeVisaRepository;
	private final VisaTransformableRepository visaTransformableRepository;
	private final DossierRepository dossierRepository;
	private final ChampFournirCommuneRepository champFournirCommuneRepository;
	private final ChampFournirSpecifiqueRepository champFournirSpecifiqueRepository;
	private final TypeStatutDemandeRepository typeStatutDemandeRepository;
	private final StatutDemandeRepository statutDemandeRepository;
    private final VisaRepository visaRepository;
    private final StatutVisaRepository statutVisaRepository;
    private final TypeStatutVisaRepository typeStatutVisaRepository;
    private final CarteResidentRepository carteResidentRepository;
    private final HistoriquePasseportVisaRepository historiquePasseportVisaRepository;
    private final VilleRepository villeRepository;

	public DemandeVisaService(
			DemandeVisaRepository demandeVisaRepository,
			EtatCivilRepository etatCivilRepository,
			PasseportRepository passeportRepository,
			NationaliteRepository nationaliteRepository,
			SitutationFamilialeRepository situtationFamilialeRepository,
			TypeDemandeVisaRepository typeDemandeVisaRepository,
			TypeVisaRepository typeVisaRepository,
			VisaTransformableRepository visaTransformableRepository,
			DossierRepository dossierRepository,
			ChampFournirCommuneRepository champFournirCommuneRepository,
			ChampFournirSpecifiqueRepository champFournirSpecifiqueRepository,
			TypeStatutDemandeRepository typeStatutDemandeRepository,
			StatutDemandeRepository statutDemandeRepository,
            VisaRepository visaRepository,
            StatutVisaRepository statutVisaRepository,
            TypeStatutVisaRepository typeStatutVisaRepository,
            CarteResidentRepository carteResidentRepository,
            HistoriquePasseportVisaRepository historiquePasseportVisaRepository,
            VilleRepository villeRepository) {
		this.demandeVisaRepository = demandeVisaRepository;
		this.etatCivilRepository = etatCivilRepository;
		this.passeportRepository = passeportRepository;
		this.nationaliteRepository = nationaliteRepository;
		this.situtationFamilialeRepository = situtationFamilialeRepository;
		this.typeDemandeVisaRepository = typeDemandeVisaRepository;
		this.typeVisaRepository = typeVisaRepository;
		this.visaTransformableRepository = visaTransformableRepository;
		this.dossierRepository = dossierRepository;
		this.champFournirCommuneRepository = champFournirCommuneRepository;
		this.champFournirSpecifiqueRepository = champFournirSpecifiqueRepository;
		this.typeStatutDemandeRepository = typeStatutDemandeRepository;
		this.statutDemandeRepository = statutDemandeRepository;
        this.visaRepository = visaRepository;
        this.statutVisaRepository = statutVisaRepository;
        this.typeStatutVisaRepository = typeStatutVisaRepository;
        this.carteResidentRepository = carteResidentRepository;
        this.historiquePasseportVisaRepository = historiquePasseportVisaRepository;
        this.villeRepository = villeRepository;
	}

	public List<TypeVisa> getAllTypesVisa() {
		return typeVisaRepository.findAll();
	}

	public List<ChampFournirCommune> getChampsCommuns() {
		return champFournirCommuneRepository.findAll();
	}

	public List<ChampFournirSpecifique> getChampsSpecifiques(Long typeVisaId) {
		return champFournirSpecifiqueRepository.findByTypeVisaId(typeVisaId);
	}

	public List<Nationalite> getAllNationalites() {
		return nationaliteRepository.findAll();
	}

	public List<SitutationFamiliale> getAllSituationsFamiliales() {
		return situtationFamilialeRepository.findAll();
	}

	public Map<String, List<Ville>> getVillesParPays() {
		List<Ville> villes = villeRepository.findAllByOrderByPaysLabelAscLabelAsc();
		Map<String, List<Ville>> grouped = new LinkedHashMap<>();
		for (Ville ville : villes) {
			String paysLabel = ville.getPays().getLabel();
			grouped.computeIfAbsent(paysLabel, key -> new java.util.ArrayList<>()).add(ville);
		}
		return grouped;
	}

	 public Optional<DemandeVisa> getDemandeById(Long id) {
		 return demandeVisaRepository.findById(id);
	 }

	public Map<String, String[][]> construireChampsDynamiques(Long typeVisaId) {
		List<ChampFournirCommune> champsCommuns = champFournirCommuneRepository.findAll();
		List<ChampFournirSpecifique> champsSpecifiques = champFournirSpecifiqueRepository.findByTypeVisaId(typeVisaId);

		Map<String, String[][]> map = new LinkedHashMap<>();
		map.put("Champs Communs", convertirCommuns(champsCommuns));
		map.put("Champs Specifiques", convertirSpecifiques(champsSpecifiques));
		map.put("Dossier", convertirDossier(champsSpecifiques));
		return map;
	}

	private String[][] convertirCommuns(List<ChampFournirCommune> champs) {
		String[][] resultat = new String[champs.size()][2];
		for (int i = 0; i < champs.size(); i++) {
			resultat[i][0] = champs.get(i).getLabel();
			resultat[i][1] = champs.get(i).getTypeDonnee();
		}
		return resultat;
	}

	private String[][] convertirSpecifiques(List<ChampFournirSpecifique> champs) {
		String[][] resultat = new String[champs.size()][2];
		for (int i = 0; i < champs.size(); i++) {
			resultat[i][0] = champs.get(i).getLabel();
			resultat[i][1] = champs.get(i).getTypeDonnee();
		}
		return resultat;
	}

	private String[][] convertirDossier(List<ChampFournirSpecifique> champsSpecifiques) {
		String[][] resultat = new String[champsSpecifiques.size()][2];
		for (int i = 0; i < champsSpecifiques.size(); i++) {
			resultat[i][0] = champsSpecifiques.get(i).getLabel();
			resultat[i][1] = "boolean";
		}
		return resultat;
	}

	@Transactional
	public DemandeVisa creerDemandeVisa(CreerDemandeVisaForm form, Long idTypeDemande, int statutInitialRang) {
		Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
				.orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));

		SitutationFamiliale situationFamiliale = situtationFamilialeRepository.findById(form.getSituationFamilialeId())
				.orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable"));

		
		TypeDemandeVisa typeDemandeVisa = new TypeDemandeVisa();
		if (idTypeDemande != null) {
			typeDemandeVisa = typeDemandeVisaRepository.findById(idTypeDemande)
					.orElseThrow(() -> new IllegalArgumentException("Type de demande introuvable"));
		} else {
			typeDemandeVisa = typeDemandeVisaRepository.findById(1L)
					.orElseThrow(() -> new IllegalArgumentException("Type de demande 'Nouveau Titre' (id=1) introuvable"));
		}

		TypeVisa typeVisa = typeVisaRepository.findById(form.getTypeVisaId())
				.orElseThrow(() -> new IllegalArgumentException("Type visa introuvable"));

		EtatCivil etatCivil = new EtatCivil();
		etatCivil.setNom(form.getNom());
		etatCivil.setPrenom(form.getPrenom());
		etatCivil.setNom_jeune_fille(form.getNomJeuneFille());
		etatCivil.setEmail(form.getEmail());
		etatCivil.setNum_tel(form.getNumeroTelephone());
		etatCivil.setDate_naissance(form.getDateNaissance());
		etatCivil.setLieu_naissance(form.getLieuNaissance());
		etatCivil.setAdresse_mada(form.getAdresseMada());
		etatCivil.setNationalite(nationalite);
		etatCivil.setSituation_familiale(situationFamiliale);
		EtatCivil savedEtatCivil = etatCivilRepository.save(etatCivil);

		Passeport passeport = new Passeport();
		passeport.setNum_passeport(form.getNumeroPasseport());
		passeport.setDate_delivrance(form.getDateDelivrancePasseport());
		passeport.setDate_expiration(form.getDateExpirationPasseport());
		passeport.setEtatCivil(savedEtatCivil);
		Passeport savedPasseport = passeportRepository.save(passeport);

		VisaTransformable visaTransformable = new VisaTransformable();
		visaTransformable.setDate_entre(null);
		visaTransformable.setNumero_passport(form.getVisaTranNumPasseport());
		visaTransformable.setDate_delivrance(form.getVisaTranDateDelivrance());
		visaTransformable.setDate_expiration(form.getVisaTranDateExpiration());
		visaTransformable.setEtatCivil(savedEtatCivil);
		visaTransformableRepository.save(visaTransformable);

		DemandeVisa demandeVisa = new DemandeVisa();
		demandeVisa.setDate_demande(form.getDateDemande());
		demandeVisa.setType_demande_visa(typeDemandeVisa);
		demandeVisa.setType_visa(typeVisa);
		demandeVisa.setPasseport(savedPasseport);

		DemandeVisa savedDemandeVisa = demandeVisaRepository.save(demandeVisa);

		Set<Long> champsCommunsCoches = form.getChampsCommunsCoches() == null
				? new HashSet<>()
				: new HashSet<>(form.getChampsCommunsCoches());

		Set<Long> champsSpecifiquesCoches = form.getChampsSpecifiquesCoches() == null
				? new HashSet<>()
				: new HashSet<>(form.getChampsSpecifiquesCoches());

		List<ChampFournirCommune> champsCommuns = champFournirCommuneRepository.findAll();
		for (ChampFournirCommune champCommun : champsCommuns) {
			Dossier dossierCommun = new Dossier();
			dossierCommun.setDemandeVisa(savedDemandeVisa);
			dossierCommun.setChampFournirCommune(champCommun);
			dossierCommun.setChampFournirSpecifique(null);
			dossierCommun.setEstCoche(champsCommunsCoches.contains(champCommun.getId()));
			dossierRepository.save(dossierCommun);
		}

		List<ChampFournirSpecifique> champsSpecifiques = champFournirSpecifiqueRepository.findByTypeVisaId(typeVisa.getId());
		for (ChampFournirSpecifique champSpecifique : champsSpecifiques) {
			Dossier dossierSpecifique = new Dossier();
			dossierSpecifique.setDemandeVisa(savedDemandeVisa);
			dossierSpecifique.setChampFournirCommune(null);
			dossierSpecifique.setChampFournirSpecifique(champSpecifique);
			dossierSpecifique.setEstCoche(champsSpecifiquesCoches.contains(champSpecifique.getId()));
			dossierRepository.save(dossierSpecifique);
		}

		creerStatutInitial(savedDemandeVisa, statutInitialRang);

		return savedDemandeVisa;
	}

	private void creerStatutInitial(DemandeVisa demande, int rang) {
		TypeStatutDemande statut = typeStatutDemandeRepository.findByRang(rang)
			.orElseThrow(() -> new IllegalStateException("Statut rang " + rang + " introuvable"));
		
		StatutDemande statutDemande = new StatutDemande();
		statutDemande.setDemande_visa(demande);
		statutDemande.setType_statut_demande(statut);
		statutDemande.setDate_statut(java.time.LocalDateTime.now().toLocalDate());
		statutDemandeRepository.save(statutDemande);
	}

    @Transactional
    public DemandeVisa creerDemandeDuplicatatSansDonnees(FinaliserSansDonneesForm form) {
        // 1. Créer une demande classique pour la personne fictive: 
        // type 1 (Nouveau Titre), mais on force l'état initial à "Approuvé" (rang 5)
        DemandeVisa demandeNouveauTitre = creerDemandeVisa(form, null, 5);
        
        // 2. Simulation de l'ancien visa à partir de cette demande fictive approuvée
        Visa ancienVisa = new Visa();
        ancienVisa.setNumVisa(form.getAncienNumeroVisa());
        ancienVisa.setDataEntre(java.time.LocalDate.now()); // ou utiliser une date passée
        ancienVisa.setDateDelivrance(form.getAncienDateDelivrance());
        ancienVisa.setDateExpiration(form.getAncienDateExpiration());
        ancienVisa.setDemandeVisa(demandeNouveauTitre);
        ancienVisa.setEtatCivil(demandeNouveauTitre.getPasseport().getEtatCivil());
        ancienVisa.setTypeVisa(demandeNouveauTitre.getType_visa());
        
		// Attribution de la ville depuis le formulaire si fournie, sinon première ville disponible
		Ville ville = null;
		if (form.getAncienVilleId() != null) {
			ville = villeRepository.findById(form.getAncienVilleId())
					.orElseThrow(() -> new IllegalArgumentException("Ville introuvable pour l'ancien visa."));
		} else {
			ville = villeRepository.findAll().stream().findFirst()
					.orElseThrow(() -> new IllegalArgumentException("Aucune ville disponible en base."));
		}
        ancienVisa.setVille(ville);
        
        Visa savedAncienVisa = visaRepository.save(ancienVisa);

        // 3. Statut de ce visa (Approuve = 5)
        TypeStatutVisa typeStatutVisaApprouve = typeStatutVisaRepository.findByRang(5.0)
            .orElseThrow(() -> new IllegalStateException("Type Statut Visa rang 5.0 introuvable"));
        StatutVisa statutVisa = new StatutVisa();
        statutVisa.setVisa(savedAncienVisa);
        statutVisa.setTypeStatutVisa(typeStatutVisaApprouve);
        statutVisa.setDateStatut(java.time.LocalDateTime.now());
        statutVisaRepository.save(statutVisa);

        // 4. Lier le passeport et le visa via l'historique
        HistoriquePasseportVisa historique = new HistoriquePasseportVisa();
        historique.setPasseport(demandeNouveauTitre.getPasseport());
        historique.setVisa(savedAncienVisa);
        historique.setDateHistorique(java.time.LocalDateTime.now());
        historiquePasseportVisaRepository.save(historique);

        // 5. Enregistrer l'éventuelle ancienne Carte Résident liée à ce Visa
        if (form.getAncienNumeroCarteResident() != null && !form.getAncienNumeroCarteResident().trim().isEmpty()) {
            CarteResident ancienneCarte = new CarteResident();
            ancienneCarte.setNum(form.getAncienNumeroCarteResident());
            ancienneCarte.setVisa(savedAncienVisa);
            carteResidentRepository.save(ancienneCarte);
        }

        // 6. Créer la 2ème Demande: TYPE DUPLICATA (id=2L) liée aux MÊMES passeports et type_visa
        TypeDemandeVisa typeDemandeDuplicata = typeDemandeVisaRepository.findById(2L)
                .orElseThrow(() -> new IllegalArgumentException("Type demande Duplicata (id=2) introuvable"));

        DemandeVisa demandeDuplicata = new DemandeVisa();
        demandeDuplicata.setDate_demande(java.time.LocalDate.now());
        demandeDuplicata.setPasseport(demandeNouveauTitre.getPasseport());
        demandeDuplicata.setType_visa(demandeNouveauTitre.getType_visa());
        demandeDuplicata.setType_demande_visa(typeDemandeDuplicata);
        
        DemandeVisa savedDemandeDuplicata = demandeVisaRepository.save(demandeDuplicata);

        // Initialiser la demande de Duplicata (elle commence toujours au rang 1 "Créer")
        creerStatutInitial(savedDemandeDuplicata, 5);

        // 7. Générer une nouvelle Carte Résident fictive issue de cette demande duplicata
        CarteResident nouvelleCarte = new CarteResident();
        nouvelleCarte.setNum("CR-DUP-" + System.currentTimeMillis());
        // Cette nouvelle carte est liée à l'ancien visa 
        // car le duplicata remplace la carte ou le visa
        nouvelleCarte.setVisa(savedAncienVisa);
        carteResidentRepository.save(nouvelleCarte);

        // Retourner la demande finale (Le Duplicata)
        return savedDemandeDuplicata;
    }

	@Transactional
	public DemandeVisa updateDemandeVisa(Long id, DemandeVisaEditForm form) {
		DemandeVisa demande = demandeVisaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

		if (form.getDateDemande() != null) {
			demande.setDate_demande(form.getDateDemande());
		}

		Passeport passeport = demande.getPasseport();
		EtatCivil etatCivil = passeport.getEtatCivil();

		etatCivil.setNom(form.getNom());
		etatCivil.setPrenom(form.getPrenom());
		etatCivil.setEmail(form.getEmail());
		etatCivil.setNum_tel(form.getNumeroTelephone());

		passeport.setNum_passeport(form.getNumeroPasseport());
		if (form.getDateDelivrancePasseport() != null) {
			passeport.setDate_delivrance(form.getDateDelivrancePasseport());
		}
		if (form.getDateExpirationPasseport() != null) {
			passeport.setDate_expiration(form.getDateExpirationPasseport());
		}

		etatCivilRepository.save(etatCivil);
		passeportRepository.save(passeport);
		return demandeVisaRepository.save(demande);
	}

	@Transactional
	public void deleteDemandeVisa(Long id) {
		if (!demandeVisaRepository.existsById(id)) {
			throw new IllegalArgumentException("Demande introuvable");
		}
		dossierRepository.deleteByDemandeVisaId(id);
		demandeVisaRepository.deleteById(id);
	}

	public List<DemandeVisa> getAllDemandes() {
		return demandeVisaRepository.findAll();
	}

}
