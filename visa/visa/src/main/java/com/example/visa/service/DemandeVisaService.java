package com.example.visa.service;
import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.dto.DemandeVisaEditForm;
import com.example.visa.dto.FinaliserSansDonneesForm;
import com.example.visa.dto.FinaliserTransfertSansDonneesForm;
import com.example.visa.dto.TransfertResult;
import com.example.visa.model.*;
import com.example.visa.repository.*;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.google.zxing.WriterException;

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
    private final PaysRepository paysRepository;
	private final QrCodeService qrCodeService;

	@Value("${app.uploads.dir:uploads}")
	private String uploadBaseDir;

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
            VilleRepository villeRepository,
            PaysRepository paysRepository,
			QrCodeService qrCodeService) {
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
        this.paysRepository = paysRepository;
		this.qrCodeService = qrCodeService;
	}

	public List<Pays> getAllPays() {
		return paysRepository.findAll();
	}

	public List<TypeVisa> getAllTypesVisa() {
		return typeVisaRepository.findAll();
	}

	public List<TypeDemandeVisa> getAllTypesDemandeVisa() {
		return typeDemandeVisaRepository.findAll();
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

	public Optional<Visa> rechercherVisaPourDuplicata(String rechercheType, String rechercheValeur) {
		if (rechercheValeur == null || rechercheValeur.trim().isEmpty()) {
			return Optional.empty();
		}

		if ("passeport".equalsIgnoreCase(rechercheType)) {
			List<HistoriquePasseportVisa> historiques = historiquePasseportVisaRepository
					.findLatestByPasseportNumero(rechercheValeur.trim(), PageRequest.of(0, 1));
			return historiques.isEmpty() ? Optional.empty() : Optional.of(historiques.get(0).getVisa());
		}

		try {
			Long demandeId = Long.parseLong(rechercheValeur.trim());
			return visaRepository.findFirstByDemandeVisaId(demandeId);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	 public Optional<DemandeVisa> getDemandeById(Long id) {
		 return demandeVisaRepository.findById(id);
	 }

	 public Optional<VisaTransformable> getVisaTransformableByEtatCivilId(Long etatCivilId) {
		 return visaTransformableRepository.findFirstByEtatCivilId(etatCivilId);
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
		etatCivil.setNomJeuneFille(form.getNomJeuneFille());
		etatCivil.setEmail(form.getEmail());
		etatCivil.setNumTel(form.getNumeroTelephone());
		etatCivil.setDateNaissance(form.getDateNaissance());
		etatCivil.setLieuNaissance(form.getLieuNaissance());
		etatCivil.setAdresseMada(form.getAdresseMada());
		etatCivil.setNationalite(nationalite);
		etatCivil.setSituationFamiliale(situationFamiliale);
		EtatCivil savedEtatCivil = etatCivilRepository.save(etatCivil);

		Passeport passeport = new Passeport();
		passeport.setNumPasseport(form.getNumeroPasseport());
		passeport.setDateDelivrance(form.getDateDelivrancePasseport());
		passeport.setDateExpiration(form.getDateExpirationPasseport());
		passeport.setEtatCivil(savedEtatCivil);
		if (form.getPaysId() != null) {
			passeport.setPays(paysRepository.findById(form.getPaysId()).orElse(null));
		}
		Passeport savedPasseport = passeportRepository.save(passeport);

		VisaTransformable visaTransformable = new VisaTransformable();
		visaTransformable.setDateEntre(null);
		visaTransformable.setNumeroPassport(form.getVisaTranNumPasseport());
		visaTransformable.setDateDelivrance(form.getVisaTranDateDelivrance());
		visaTransformable.setDateExpiration(form.getVisaTranDateExpiration());
		visaTransformable.setEtatCivil(savedEtatCivil);
		if (form.getVisaTranPaysId() != null) {
			visaTransformable.setPays(paysRepository.findById(form.getVisaTranPaysId()).orElse(null));
		}
		visaTransformableRepository.save(visaTransformable);

		DemandeVisa demandeVisa = new DemandeVisa();
		demandeVisa.setDateDemande(form.getDateDemande());
		demandeVisa.setTypeDemandeVisa(typeDemandeVisa);
		demandeVisa.setTypeVisa(typeVisa);
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
			dossierCommun.setEstCoche(true);
			dossierRepository.save(dossierCommun);
		}

		List<ChampFournirSpecifique> champsSpecifiques = champFournirSpecifiqueRepository.findByTypeVisaId(typeVisa.getId());
		for (ChampFournirSpecifique champSpecifique : champsSpecifiques) {
			Dossier dossierSpecifique = new Dossier();
			dossierSpecifique.setDemandeVisa(savedDemandeVisa);
			dossierSpecifique.setChampFournirCommune(null);
			dossierSpecifique.setChampFournirSpecifique(champSpecifique);
			dossierSpecifique.setEstCoche(true);
			dossierRepository.save(dossierSpecifique);
		}

		creerStatutInitial(savedDemandeVisa, statutInitialRang);

		return savedDemandeVisa;
	}

	private void creerStatutInitial(DemandeVisa demande, int rang) {
		TypeStatutDemande statut = typeStatutDemandeRepository.findByRang(rang)
			.orElseThrow(() -> new IllegalStateException("Statut rang " + rang + " introuvable"));
		
		StatutDemande statutDemande = new StatutDemande();
		statutDemande.setDemandeVisa(demande);
		statutDemande.setTypeStatutDemande(statut);
		statutDemande.setDateStatut(java.time.LocalDateTime.now().toLocalDate());
		statutDemandeRepository.save(statutDemande);
	}

	@Transactional
	public void changerStatutDemande(Long demandeId, int rang) {
		DemandeVisa demande = demandeVisaRepository.findById(demandeId)
			.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));
		creerStatutInitial(demande, rang);
	}

	@Transactional
	public CarteResident creerDemandeDuplicatatSansDonnees(FinaliserSansDonneesForm form) {
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
		ancienVisa.setTypeVisa(demandeNouveauTitre.getTypeVisa());
        
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
		demandeDuplicata.setDateDemande(java.time.LocalDate.now());
        demandeDuplicata.setPasseport(demandeNouveauTitre.getPasseport());
		demandeDuplicata.setTypeVisa(demandeNouveauTitre.getTypeVisa());
		demandeDuplicata.setTypeDemandeVisa(typeDemandeDuplicata);
        
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

        // Retourner la carte dupliquee
        return nouvelleCarte;
    }

	@Transactional
	public CarteResident creerDuplicataAvecDonnees(Long visaId) {
		Visa visa = visaRepository.findById(visaId)
				.orElseThrow(() -> new IllegalArgumentException("Visa introuvable"));
		CarteResident nouvelleCarte = new CarteResident();
		nouvelleCarte.setNum("CR-DUP-" + System.currentTimeMillis());
		nouvelleCarte.setVisa(visa);

		TypeDemandeVisa typeDemandeDuplicata = typeDemandeVisaRepository.findById(2L)
				.orElseThrow(() -> new IllegalArgumentException("Type demande Duplicata (id=2) introuvable"));

		DemandeVisa demandeDuplicata = new DemandeVisa();
		demandeDuplicata.setDateDemande(java.time.LocalDate.now());
		demandeDuplicata.setPasseport(visa.getDemandeVisa().getPasseport());
		demandeDuplicata.setTypeVisa(visa.getTypeVisa());
		demandeDuplicata.setTypeDemandeVisa(typeDemandeDuplicata);

		DemandeVisa savedDemandeDuplicata = demandeVisaRepository.save(demandeDuplicata);
		creerStatutInitial(savedDemandeDuplicata, 5);

		return carteResidentRepository.save(nouvelleCarte);
	}

	public Optional<CarteResident> getCarteResidentById(Long carteId) {
		return carteResidentRepository.findById(carteId);
	}

	public Optional<Visa> getVisaById(Long visaId) {
		return visaRepository.findById(visaId);
	}

	public Optional<Passeport> getPasseportById(Long passeportId) {
		return passeportRepository.findById(passeportId);
	}

	@Transactional
	public Passeport creerTransfertAvecDonnees(Long visaId, String numeroPasseport,
			java.time.LocalDate dateDelivrance, java.time.LocalDate dateExpiration, Long paysId) {
		Visa visa = visaRepository.findById(visaId)
				.orElseThrow(() -> new IllegalArgumentException("Visa introuvable"));
		Passeport nouveauPasseport = new Passeport();
		nouveauPasseport.setNumPasseport(numeroPasseport);
		nouveauPasseport.setDateDelivrance(dateDelivrance);
		nouveauPasseport.setDateExpiration(dateExpiration);
		nouveauPasseport.setEtatCivil(visa.getEtatCivil());
		if (paysId != null) {
			nouveauPasseport.setPays(paysRepository.findById(paysId).orElse(null));
		}
		Passeport savedPasseport = passeportRepository.save(nouveauPasseport);

		HistoriquePasseportVisa historique = new HistoriquePasseportVisa();
		historique.setPasseport(savedPasseport);
		historique.setVisa(visa);
		historique.setDateHistorique(java.time.LocalDateTime.now());
		historiquePasseportVisaRepository.save(historique);

		TypeDemandeVisa typeDemandeTransfert = typeDemandeVisaRepository.findById(3L)
				.orElseThrow(() -> new IllegalArgumentException("Type demande Transfert (id=3) introuvable"));
		DemandeVisa demandeTransfert = new DemandeVisa();
		demandeTransfert.setDateDemande(java.time.LocalDate.now());
		demandeTransfert.setPasseport(savedPasseport);
		demandeTransfert.setTypeVisa(visa.getTypeVisa());
		demandeTransfert.setTypeDemandeVisa(typeDemandeTransfert);
		DemandeVisa savedDemandeTransfert = demandeVisaRepository.save(demandeTransfert);
		creerStatutInitial(savedDemandeTransfert, 5);

		return savedPasseport;
	}

	@Transactional
	public TransfertResult creerDemandeTransfertSansDonnees(FinaliserTransfertSansDonneesForm form) {
		// 1. Créer une demande fictive approuvée (Nouveau Titre) pour générer l'ancien visa
		DemandeVisa demandeNouveauTitre = creerDemandeVisa(form, null, 5);

		// 2. Simulation de l'ancien visa à partir de cette demande fictive approuvée
		Visa ancienVisa = new Visa();
		ancienVisa.setNumVisa(form.getAncienNumeroVisa());
		ancienVisa.setDataEntre(java.time.LocalDate.now());
		ancienVisa.setDateDelivrance(form.getAncienDateDelivrance());
		ancienVisa.setDateExpiration(form.getAncienDateExpiration());
		ancienVisa.setDemandeVisa(demandeNouveauTitre);
		ancienVisa.setEtatCivil(demandeNouveauTitre.getPasseport().getEtatCivil());
		ancienVisa.setTypeVisa(demandeNouveauTitre.getTypeVisa());

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

		// 4. Lier l'ancien passeport et le visa via l'historique
		HistoriquePasseportVisa historiqueAncien = new HistoriquePasseportVisa();
		historiqueAncien.setPasseport(demandeNouveauTitre.getPasseport());
		historiqueAncien.setVisa(savedAncienVisa);
		historiqueAncien.setDateHistorique(java.time.LocalDateTime.now());
		historiquePasseportVisaRepository.save(historiqueAncien);

		// 5. Enregistrer l'ancienne Carte Resident (optionnel)
		if (form.getAncienNumeroCarteResident() != null && !form.getAncienNumeroCarteResident().trim().isEmpty()) {
			CarteResident ancienneCarte = new CarteResident();
			ancienneCarte.setNum(form.getAncienNumeroCarteResident());
			ancienneCarte.setVisa(savedAncienVisa);
			carteResidentRepository.save(ancienneCarte);
		}

		// 6. Inserer le nouveau passeport et le lier au meme etat civil
		Passeport nouveauPasseport = new Passeport();
		nouveauPasseport.setNumPasseport(form.getNouveauNumeroPasseport());
		nouveauPasseport.setDateDelivrance(form.getNouveauDateDelivrance());
		nouveauPasseport.setDateExpiration(form.getNouveauDateExpiration());
		nouveauPasseport.setEtatCivil(demandeNouveauTitre.getPasseport().getEtatCivil());
		if (form.getNouveauPaysId() != null) {
			nouveauPasseport.setPays(paysRepository.findById(form.getNouveauPaysId()).orElse(null));
		}
		Passeport savedNouveauPasseport = passeportRepository.save(nouveauPasseport);

		// 7. Lier le nouveau passeport au visa cree via l'historique
		HistoriquePasseportVisa historiqueNouveau = new HistoriquePasseportVisa();
		historiqueNouveau.setPasseport(savedNouveauPasseport);
		historiqueNouveau.setVisa(savedAncienVisa);
		historiqueNouveau.setDateHistorique(java.time.LocalDateTime.now());
		historiquePasseportVisaRepository.save(historiqueNouveau);

		// 8. Créer la 2eme Demande: TYPE TRANSFERT (id=3L) liee au nouveau passeport
		TypeDemandeVisa typeDemandeTransfert = typeDemandeVisaRepository.findById(3L)
				.orElseThrow(() -> new IllegalArgumentException("Type demande Transfert (id=3) introuvable"));

		DemandeVisa demandeTransfert = new DemandeVisa();
		demandeTransfert.setDateDemande(java.time.LocalDate.now());
		demandeTransfert.setPasseport(savedNouveauPasseport);
		demandeTransfert.setTypeVisa(demandeNouveauTitre.getTypeVisa());
		demandeTransfert.setTypeDemandeVisa(typeDemandeTransfert);

		DemandeVisa savedDemandeTransfert = demandeVisaRepository.save(demandeTransfert);

		creerStatutInitial(savedDemandeTransfert, 5);

		return new TransfertResult(savedAncienVisa.getId(), savedNouveauPasseport.getId());
	}

	@Transactional
	public DemandeVisa updateDemandeVisa(Long id, DemandeVisaEditForm form) {
		DemandeVisa demande = demandeVisaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));
		if (demande.isEstVerrouille()) {
			throw new IllegalStateException("Demande verrouillee");
		}

		Passeport passeport = demande.getPasseport();
		EtatCivil etatCivil = passeport.getEtatCivil();
		etatCivil.setNom(form.getNom());
		etatCivil.setPrenom(form.getPrenom());
		etatCivil.setNomJeuneFille(form.getNomJeuneFille());
		etatCivil.setEmail(form.getEmail());
		etatCivil.setNumTel(form.getNumeroTelephone());
		etatCivil.setDateNaissance(form.getDateNaissance());
		etatCivil.setLieuNaissance(form.getLieuNaissance());
		etatCivil.setAdresseMada(form.getAdresseMada());
		if (form.getNationaliteId() != null) {
			Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
					.orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));
			etatCivil.setNationalite(nationalite);
		}
		if (form.getSituationFamilialeId() != null) {
			SitutationFamiliale situationFamiliale = situtationFamilialeRepository
					.findById(form.getSituationFamilialeId())
					.orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable"));
			etatCivil.setSituationFamiliale(situationFamiliale);
		}

		passeport.setNumPasseport(form.getNumeroPasseport());
		passeport.setDateDelivrance(form.getDateDelivrancePasseport());
		passeport.setDateExpiration(form.getDateExpirationPasseport());
		if (form.getPaysId() != null) {
			passeport.setPays(paysRepository.findById(form.getPaysId()).orElse(null));
		}

		if (form.getTypeVisaId() != null) {
			TypeVisa typeVisa = typeVisaRepository.findById(form.getTypeVisaId())
					.orElseThrow(() -> new IllegalArgumentException("Type de visa introuvable"));
			demande.setTypeVisa(typeVisa);
		}

		if (form.getTypeDemandeId() != null) {
			TypeDemandeVisa typeDemande = typeDemandeVisaRepository.findById(form.getTypeDemandeId())
					.orElseThrow(() -> new IllegalArgumentException("Type de demande introuvable"));
			demande.setTypeDemandeVisa(typeDemande);
		}

		VisaTransformable visaTransformable = visaTransformableRepository
				.findFirstByEtatCivilId(etatCivil.getId())
				.orElseGet(() -> {
					VisaTransformable v = new VisaTransformable();
					v.setEtatCivil(etatCivil);
					return v;
				});
		visaTransformable.setNumeroPassport(form.getVisaTranNumPasseport());
		visaTransformable.setDateDelivrance(form.getVisaTranDateDelivrance());
		visaTransformable.setDateExpiration(form.getVisaTranDateExpiration());
		if (form.getVisaTranPaysId() != null) {
			visaTransformable.setPays(paysRepository.findById(form.getVisaTranPaysId()).orElse(null));
		}

		etatCivilRepository.save(etatCivil);
		passeportRepository.save(passeport);
		visaTransformableRepository.save(visaTransformable);

		DemandeVisa savedDemande = demandeVisaRepository.save(demande);

		Set<Long> champsCommunsCoches = form.getChampsCommunsCoches() == null
				? new HashSet<>()
				: new HashSet<>(form.getChampsCommunsCoches());

		Set<Long> champsSpecifiquesCoches = form.getChampsSpecifiquesCoches() == null
				? new HashSet<>()
				: new HashSet<>(form.getChampsSpecifiquesCoches());

		dossierRepository.deleteByDemandeVisaId(savedDemande.getId());

		List<ChampFournirCommune> champsCommuns = champFournirCommuneRepository.findAll();
		for (ChampFournirCommune champCommun : champsCommuns) {
			Dossier dossierCommun = new Dossier();
			dossierCommun.setDemandeVisa(savedDemande);
			dossierCommun.setChampFournirCommune(champCommun);
			dossierCommun.setChampFournirSpecifique(null);
			dossierCommun.setEstCoche(champsCommunsCoches.contains(champCommun.getId()));
			dossierRepository.save(dossierCommun);
		}

		TypeVisa typeVisa = demande.getTypeVisa();
		List<ChampFournirSpecifique> champsSpecifiques = champFournirSpecifiqueRepository.findByTypeVisaId(typeVisa.getId());
		for (ChampFournirSpecifique champSpecifique : champsSpecifiques) {
			Dossier dossierSpecifique = new Dossier();
			dossierSpecifique.setDemandeVisa(savedDemande);
			dossierSpecifique.setChampFournirCommune(null);
			dossierSpecifique.setChampFournirSpecifique(champSpecifique);
			dossierSpecifique.setEstCoche(champsSpecifiquesCoches.contains(champSpecifique.getId()));
			dossierRepository.save(dossierSpecifique);
		}

		return savedDemande;
	}

	@Transactional
	public Dossier uploadPiece(Long idDemande, Long dossierId, Part fichier) {
		DemandeVisa demande = demandeVisaRepository.findById(idDemande)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));
		if (demande.isEstVerrouille()) {
			throw new IllegalStateException("Demande verrouillee");
		}
		if (fichier == null || fichier.getSize() == 0) {
			throw new IllegalArgumentException("Fichier vide");
		}

		Dossier dossier = dossierRepository.findByIdAndDemandeVisaId(dossierId, idDemande)
				.orElseThrow(() -> new IllegalArgumentException("Dossier introuvable"));
		if (!dossier.isEstCoche()) {
			throw new IllegalStateException("Piece non requise");
		}

		Path demandeDir = getDemandeUploadDir(idDemande);
		try {
			Files.createDirectories(demandeDir);
			String original = safeFileName(fichier.getSubmittedFileName());
			String fileName = idDemande + "-" + dossierId + "-" + System.currentTimeMillis() + "-" + original;
			Path target = demandeDir.resolve(fileName).normalize();
			if (!target.startsWith(demandeDir)) {
				throw new IllegalArgumentException("Nom de fichier invalide");
			}

			deleteExistingFile(dossier.getPathFichier());
			try (InputStream input = fichier.getInputStream()) {
				Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
			}
			dossier.setPathFichier(target.toString());
			return dossierRepository.save(dossier);
		} catch (IOException ex) {
			throw new IllegalStateException("Erreur lors de l'upload", ex);
		}
	}

	public byte[] fusionnerPiecesJointes(Long idDemande) {

		List<Dossier> dossiers =
			dossierRepository.findByDemandeVisaIdOrderByIdAsc(idDemande);

		List<Path> fichiers = new ArrayList<>();

		for (Dossier dossier : dossiers) {

			if (!dossier.isEstCoche()) {
				continue;
			}

			String path = dossier.getPathFichier();

			if (path == null || path.isBlank()) {
				continue;
			}

			Path fichier = Paths.get(path);

			if (
				Files.exists(fichier)
				&& Files.isRegularFile(fichier)
			) {
				fichiers.add(fichier);
			}
		}

		if (fichiers.isEmpty()) {
			throw new IllegalStateException(
				"Aucune piece jointe disponible"
			);
		}

		try (
			PDDocument document = new PDDocument();
			ByteArrayOutputStream output =
				new ByteArrayOutputStream()
		) {

			for (Path fichier : fichiers) {

				String nom =
					fichier.getFileName()
						.toString()
						.toLowerCase();

				// =========================
				// PDF
				// =========================
				if (nom.endsWith(".pdf")) {

					try (
						PDDocument pdf =
							PDDocument.load(
								fichier.toFile()
							);
					) {

						for (PDPage page : pdf.getPages()) {
							document.importPage(page);
						}
					}
				}

				// =========================
				// Images
				// =========================
				else if (
					nom.endsWith(".png")
					|| nom.endsWith(".jpg")
					|| nom.endsWith(".jpeg")
				) {

					PDPage page =
						new PDPage(PDRectangle.A4);

					document.addPage(page);

					PDImageXObject image =
						PDImageXObject.createFromFile(
							fichier.toString(),
							document
						);

					float pageWidth =
						page.getMediaBox().getWidth();

					float pageHeight =
						page.getMediaBox().getHeight();

					float imageWidth =
						image.getWidth();

					float imageHeight =
						image.getHeight();

					float scale =
						Math.min(
							pageWidth / imageWidth,
							pageHeight / imageHeight
						);

					float drawWidth =
						imageWidth * scale;

					float drawHeight =
						imageHeight * scale;

					float x =
						(pageWidth - drawWidth) / 2;

					float y =
						(pageHeight - drawHeight) / 2;

					try (
						PDPageContentStream content =
							new PDPageContentStream(
								document,
								page
							)
					) {

						content.drawImage(
							image,
							x,
							y,
							drawWidth,
							drawHeight
						);
					}
				}
			}

			document.save(output);

			return output.toByteArray();

		} catch (IOException ex) {

			throw new IllegalStateException(
				"Erreur lors de la fusion des fichiers",
				ex
			);
		}
	}

	@Transactional
	public DemandeVisa verrouillerDemande(Long idDemande) {
		DemandeVisa demande = demandeVisaRepository.findById(idDemande)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));
		if (demande.isEstVerrouille()) {
			return demande;
		}

		List<Dossier> dossiers = dossierRepository.findByDemandeVisaIdOrderByIdAsc(idDemande);
		boolean dossierComplet = dossiers.stream()
				.filter(Dossier::isEstCoche)
				.allMatch(dossier -> dossier.getPathFichier() != null && !dossier.getPathFichier().isBlank());
		// if (!dossierComplet) {
		// 	throw new IllegalStateException("Dossier incomplet");
		// }

		// scan autorisé même si incompletp

		Path photoPath = Paths.get(
        "uploads",
        "demande-" + idDemande,
        "photo-" + idDemande + ".png"
		);

		if (Files.exists(photoPath)) {
			demande.setCheminPhoto(photoPath.toString());
		}
		demande.setEstVerrouille(true);
		DemandeVisa savedDemande = demandeVisaRepository.save(demande);
		TypeStatutDemande statut = typeStatutDemandeRepository.findByRang(5)
				.orElseThrow(() -> new IllegalStateException("Statut rang 5 (scanne) introuvable"));
		StatutDemande statutDemande = new StatutDemande();
		statutDemande.setDemandeVisa(savedDemande);
		statutDemande.setTypeStatutDemande(statut);
		statutDemande.setDateStatut(java.time.LocalDate.now());
		statutDemandeRepository.save(statutDemande);
		return savedDemande;
	}

	private Path getDemandeUploadDir(Long idDemande) {
		return Paths.get(uploadBaseDir, "demande-" + idDemande);
	}

	private String safeFileName(String fileName) {
		if (fileName == null || fileName.isBlank()) {
			return "fichier";
		}
		return Paths.get(fileName).getFileName().toString();
	}

	private void deleteExistingFile(String path) throws IOException {
		if (path == null || path.isBlank()) {
			return;
		}
		Path existing = Paths.get(path);
		if (Files.exists(existing)) {
			Files.delete(existing);
		}
	}

	@Transactional
	public void deleteDemandeVisa(Long id) {
		if (!demandeVisaRepository.existsById(id)) {
			throw new IllegalArgumentException("Demande introuvable");
		}
		statutDemandeRepository.deleteByDemandeVisaId(id);
		dossierRepository.deleteByDemandeVisaId(id);
		demandeVisaRepository.deleteById(id);
	}

	public List<DemandeVisa> getAllDemandes() {
		return demandeVisaRepository.findAll();
	}

	@Transactional
	public void enregistrerPhotoDemande(Long idDemande, String base64Image) {

		DemandeVisa demande = demandeVisaRepository.findById(idDemande)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

		if (base64Image == null || base64Image.isBlank()) {
			throw new IllegalArgumentException("Image vide");
		}

		try {

			// enlever data:image/png;base64,
			String imageData = base64Image.split(",")[1];

			byte[] decodedBytes = java.util.Base64
					.getDecoder()
					.decode(imageData);

			Path demandeDir = getDemandeUploadDir(idDemande);

			Files.createDirectories(demandeDir);

			String fileName = "photo-" + idDemande + ".png";

			Path target = demandeDir.resolve(fileName);

			// écrase automatiquement l'ancienne photo
			Files.write(target, decodedBytes);

			/*
			* Vérifier le dernier statut
			*/
			List<StatutDemande> statuts = statutDemandeRepository
					.findByDemandeVisaIdOrderByDateStatutDesc(idDemande);

			boolean dejaPhotoTerminee = false;

			if (!statuts.isEmpty()) {

				StatutDemande dernierStatut = statuts.get(0);

				if (dernierStatut.getTypeStatutDemande() != null
						&& dernierStatut.getTypeStatutDemande().getRang() == 2) {

					dejaPhotoTerminee = true;
				}
			}

			/*
			* Ajouter le statut seulement si absent
			*/
			if (!dejaPhotoTerminee) {

				TypeStatutDemande typeStatut = typeStatutDemandeRepository
						.findByRang(2)
						.orElseThrow(() ->
								new IllegalStateException("Statut rang 2 introuvable"));

				StatutDemande statutDemande = new StatutDemande();

				statutDemande.setDemandeVisa(demande);
				statutDemande.setTypeStatutDemande(typeStatut);
				statutDemande.setDateStatut(java.time.LocalDate.now());

				statutDemandeRepository.save(statutDemande);
			}

		} catch (IOException e) {
			throw new IllegalStateException("Erreur sauvegarde photo", e);
		}
	}

	@Transactional
	public void mettreAJourStatutMedia(Long idDemande) {

		DemandeVisa demande = demandeVisaRepository.findById(idDemande)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

		boolean hasPhoto = Files.exists(Paths.get("uploads/demande-" + idDemande + "/photo-" + idDemande + ".png"));
		boolean hasSignature = demande.getCheminSignature() != null && !demande.getCheminSignature().isBlank();

		int rangStatut;

		if (hasPhoto && hasSignature) {
			rangStatut = 4; // Signature et photo terminee
		} else if (hasSignature) {
			rangStatut = 3; // Signature terminee
		} else if (hasPhoto) {
			rangStatut = 2; // Photo terminee
		} else {
			return;
		}

		TypeStatutDemande typeStatut = typeStatutDemandeRepository.findByRang(rangStatut)
				.orElseThrow(() -> new IllegalStateException("Statut introuvable"));

		StatutDemande statut = new StatutDemande();
		statut.setDemandeVisa(demande);
		statut.setTypeStatutDemande(typeStatut);
		statut.setDateStatut(java.time.LocalDate.now());

		statutDemandeRepository.save(statut);
	}

	public void verifierAutorisationScan(Long idDemande) {

        DemandeVisa demande = demandeVisaRepository.findById(idDemande)
                .orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

        StatutDemande dernierStatut = statutDemandeRepository
                .findTopByDemandeVisaIdOrderByIdDesc(idDemande)
                .orElseThrow(() -> new IllegalStateException("Aucun statut trouvé"));

        boolean autoriseScan = dernierStatut.getTypeStatutDemande().getRang() >= 4;

        if (!autoriseScan) {
            throw new IllegalStateException("Veuillez d'abord finaliser l'étape de prise de photo et de signature avant de procéder au scan des documents.");
        }
    }

	@Transactional
	public byte[] generateLettreReceptionPdf(Long id, String qrUrlBase) throws IOException, WriterException {
		DemandeVisa demande = demandeVisaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

		// Validation : lever une exception si le statut de la demande n'est pas au moins SCANNE (rang >= 5)
		StatutDemande dernierStatut = statutDemandeRepository
				.findTopByDemandeVisaIdOrderByIdDesc(id)
				.orElseThrow(() -> new IllegalStateException("Aucun statut trouvé"));

		if (dernierStatut.getTypeStatutDemande().getRang() < 5) {
			throw new IllegalStateException("Le statut de la demande n'est pas au moins SCANNE (Scan terminé).");
		}

		PDDocument document = new PDDocument();
		try {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			// Setup colors and typography
			float pageWidth = PDRectangle.A4.getWidth();
			float pageHeight = PDRectangle.A4.getHeight();

			// Header/Letterhead
			drawCenteredText(contentStream, "REPOBLIKAN'I MADAGASCAR", PDType1Font.HELVETICA_BOLD, 12, pageWidth, 800);
			drawCenteredText(contentStream, "Fitiavana - Tanindrazana - Fandrosoana", PDType1Font.HELVETICA_OBLIQUE, 9, pageWidth, 785);
			contentStream.setLineWidth(0.5f);
			contentStream.setStrokingColor(150, 150, 150);
			contentStream.moveTo(200, 775);
			contentStream.lineTo(395, 775);
			contentStream.stroke();

			drawCenteredText(contentStream, "MINISTERE DE L'INTERIEUR", PDType1Font.HELVETICA_BOLD, 10, pageWidth, 755);
			drawCenteredText(contentStream, "Direction de l'Immigration et de l'Emigration", PDType1Font.HELVETICA, 10, pageWidth, 740);

			// Date of generation
			java.time.LocalDate localDate = java.time.LocalDate.now();
			String formattedDate = localDate.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			drawText(contentStream, "Date: " + formattedDate, PDType1Font.HELVETICA, 10, 50, 710);

			// Title Banner (Slate/Navy Blue)
			contentStream.setNonStrokingColor(44, 62, 80);
			contentStream.addRect(50, 665, 495, 30);
			contentStream.fill();

			// White text on banner
			contentStream.setNonStrokingColor(255, 255, 255);
			drawCenteredText(contentStream, "ACCUSE DE RECEPTION - DEMANDE DE VISA", PDType1Font.HELVETICA_BOLD, 12, pageWidth, 675);

			// Reset non-stroking color to dark gray/black for main text
			contentStream.setNonStrokingColor(30, 30, 30);

			// Get references
			Passeport passeport = demande.getPasseport();
			EtatCivil etatCivil = passeport.getEtatCivil();

			// Left details block
			float startX = 60;
			float currentY = 620;
			float lineSpacing = 18;

			// Section 1: Informations de la Demande
			drawText(contentStream, "INFORMATIONS DE LA DEMANDE", PDType1Font.HELVETICA_BOLD, 11, startX, currentY);
			contentStream.setStrokingColor(44, 62, 80);
			contentStream.setLineWidth(1f);
			contentStream.moveTo(startX, currentY - 3);
			contentStream.lineTo(startX + 180, currentY - 3);
			contentStream.stroke();

			currentY -= 20;
			drawText(contentStream, "Reference Demande : " + demande.getId(), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			String dateDemandeStr = demande.getDateDemande() != null ? demande.getDateDemande().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
			drawText(contentStream, "Date de Demande : " + dateDemandeStr, PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Type de Demande : " + (demande.getTypeDemandeVisa() != null ? demande.getTypeDemandeVisa().getLabel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Type de Visa sollicite : " + (demande.getTypeVisa() != null ? demande.getTypeVisa().getLabel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);

			// Section 2: Etat Civil
			currentY -= 35;
			drawText(contentStream, "INFORMATIONS D'ETAT CIVIL", PDType1Font.HELVETICA_BOLD, 11, startX, currentY);
			contentStream.moveTo(startX, currentY - 3);
			contentStream.lineTo(startX + 180, currentY - 3);
			contentStream.stroke();

			currentY -= 20;
			drawText(contentStream, "Nom : " + (etatCivil.getNom() != null ? etatCivil.getNom() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Prenom(s) : " + (etatCivil.getPrenom() != null ? etatCivil.getPrenom() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			if (etatCivil.getNomJeuneFille() != null && !etatCivil.getNomJeuneFille().isBlank()) {
				currentY -= lineSpacing;
				drawText(contentStream, "Nom de jeune fille : " + etatCivil.getNomJeuneFille(), PDType1Font.HELVETICA, 10, startX, currentY);
			}
			currentY -= lineSpacing;
			String dateNaisStr = etatCivil.getDateNaissance() != null ? etatCivil.getDateNaissance().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
			drawText(contentStream, "Date de Naissance : " + dateNaisStr, PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Lieu de Naissance : " + (etatCivil.getLieuNaissance() != null ? etatCivil.getLieuNaissance() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Nationalite : " + (etatCivil.getNationalite() != null ? etatCivil.getNationalite().getLabel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Situation Familiale : " + (etatCivil.getSituationFamiliale() != null ? etatCivil.getSituationFamiliale().getLabel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Adresse : " + (etatCivil.getAdresseMada() != null ? etatCivil.getAdresseMada() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Telephone : " + (etatCivil.getNumTel() != null ? etatCivil.getNumTel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Email : " + (etatCivil.getEmail() != null ? etatCivil.getEmail() : ""), PDType1Font.HELVETICA, 10, startX, currentY);

			// Section 3: Passeport
			currentY -= 35;
			drawText(contentStream, "INFORMATIONS DU PASSEPORT", PDType1Font.HELVETICA_BOLD, 11, startX, currentY);
			contentStream.moveTo(startX, currentY - 3);
			contentStream.lineTo(startX + 180, currentY - 3);
			contentStream.stroke();

			currentY -= 20;
			drawText(contentStream, "Numero de Passeport : " + (passeport.getNumPasseport() != null ? passeport.getNumPasseport() : ""), PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			String dateDelivStr = passeport.getDateDelivrance() != null ? passeport.getDateDelivrance().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
			drawText(contentStream, "Date de Delivrance : " + dateDelivStr, PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			String dateExpStr = passeport.getDateExpiration() != null ? passeport.getDateExpiration().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
			drawText(contentStream, "Date d'Expiration : " + dateExpStr, PDType1Font.HELVETICA, 10, startX, currentY);
			currentY -= lineSpacing;
			drawText(contentStream, "Pays de Delivrance : " + (passeport.getPays() != null ? passeport.getPays().getLabel() : ""), PDType1Font.HELVETICA, 10, startX, currentY);

			// Right photo box
			float photoX = 430;
			float photoY = 500;
			float photoW = 100;
			float photoH = 133;

			contentStream.setLineWidth(1f);
			contentStream.setStrokingColor(180, 180, 180);
			contentStream.addRect(photoX, photoY, photoW, photoH);
			contentStream.stroke();

			String photoPathStr = demande.getCheminPhoto();
			boolean photoDrawn = false;
			if (photoPathStr != null && !photoPathStr.isBlank()) {
				Path photoPath = Paths.get(photoPathStr);
				if (Files.exists(photoPath)) {
					try {
						PDImageXObject photoImage = PDImageXObject.createFromFile(photoPath.toString(), document);
						contentStream.drawImage(photoImage, photoX + 1, photoY + 1, photoW - 2, photoH - 2);
						photoDrawn = true;
					} catch (Exception e) {
						// Ignored, fallback to text
					}
				}
			}

			if (!photoDrawn) {
				drawText(contentStream, "PHOTO", PDType1Font.HELVETICA_BOLD, 10, photoX + 30, photoY + 75);
				drawText(contentStream, "IDENTITE", PDType1Font.HELVETICA_BOLD, 10, photoX + 25, photoY + 60);
			}

			// QR Code box (Bottom right)
			float qrX = 420;
			float qrY = 100;
			float qrSize = 110;

			byte[] qrBytes = null;
			try {
				qrBytes = qrCodeService.generateQrCode(qrUrlBase + id, 150, 150);
			} catch (Exception e) {
				// Ignored
			}

			if (qrBytes != null) {
				try {
					PDImageXObject qrImage = PDImageXObject.createFromByteArray(document, qrBytes, "qrcode");
					contentStream.drawImage(qrImage, qrX, qrY, qrSize, qrSize);
				} catch (Exception e) {
					// Ignored
				}
			} else {
				contentStream.setLineWidth(1f);
				contentStream.setStrokingColor(180, 180, 180);
				contentStream.addRect(qrX, qrY, qrSize, qrSize);
				contentStream.stroke();
				drawText(contentStream, "QR CODE", PDType1Font.HELVETICA_BOLD, 10, qrX + 30, qrY + 55);
			}

			// Notice text next to QR Code (Bottom left)
			float noticeX = 60;
			float noticeY = 180;
			drawText(contentStream, "Veuillez conserver precieusement cet accuse de reception.", PDType1Font.HELVETICA_BOLD, 9, noticeX, noticeY);
			drawText(contentStream, "Vous pouvez scanner le QR Code ci-contre pour suivre en temps reel", PDType1Font.HELVETICA, 9, noticeX, noticeY - 14);
			drawText(contentStream, "l'avancement du traitement de votre demande de visa.", PDType1Font.HELVETICA, 9, noticeX, noticeY - 28);

			// Signature block (Bottom left-middle)
			float sigX = 60;
			float sigY = 110;
			drawText(contentStream, "Signature du demandeur", PDType1Font.HELVETICA_OBLIQUE, 9, sigX, sigY);
			contentStream.setLineWidth(0.5f);
			contentStream.setStrokingColor(180, 180, 180);
			contentStream.addRect(sigX, sigY - 70, 180, 60);
			contentStream.stroke();

			String sigPathStr = demande.getCheminSignature();
			if (sigPathStr != null && !sigPathStr.isBlank()) {
				Path sigPath = Paths.get(sigPathStr);
				if (Files.exists(sigPath)) {
					try {
						PDImageXObject sigImage = PDImageXObject.createFromFile(sigPath.toString(), document);
						contentStream.drawImage(sigImage, sigX + 2, sigY - 68, 176, 56);
					} catch (Exception e) {
						// Ignored, fallback to empty box
					}
				}
			}

			contentStream.close();

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			document.save(output);
			return output.toByteArray();
		} finally {
			document.close();
		}
	}

	private String stripAccents(String text) {
		if (text == null) return "";
		return java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "")
				.replace("æ", "ae")
				.replace("Œ", "OE")
				.replace("œ", "oe");
	}

	private void drawCenteredText(PDPageContentStream contentStream, String text, PDType1Font font, float fontSize, float pageWidth, float y) throws IOException {
		String cleanText = stripAccents(text);
		float titleWidth = font.getStringWidth(cleanText) / 1000 * fontSize;
		float x = (pageWidth - titleWidth) / 2;
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(cleanText);
		contentStream.endText();
	}

	private void drawText(PDPageContentStream contentStream, String text, PDType1Font font, float fontSize, float x, float y) throws IOException {
		String cleanText = stripAccents(text);
		contentStream.beginText();
		contentStream.setFont(font, fontSize);
		contentStream.newLineAtOffset(x, y);
		contentStream.showText(cleanText);
		contentStream.endText();
	}
}
