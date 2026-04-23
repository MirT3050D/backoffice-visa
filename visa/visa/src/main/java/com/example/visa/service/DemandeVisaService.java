package com.example.visa.service;
import com.example.visa.dto.CreerDemandeVisaForm;
import com.example.visa.dto.DemandeVisaEditForm;
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
			ChampFournirSpecifiqueRepository champFournirSpecifiqueRepository) {
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
	public DemandeVisa creerDemandeVisa(CreerDemandeVisaForm form) {
		Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
				.orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));

		SitutationFamiliale situationFamiliale = situtationFamilialeRepository.findById(form.getSituationFamilialeId())
				.orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable"));

		TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(form.getTypeDemandeId())
				.orElseThrow(() -> new IllegalArgumentException("Type de demande introuvable"));

		TypeVisa typeVisa = typeVisaRepository.findById(form.getTypeVisaId())
				.orElseThrow(() -> new IllegalArgumentException("Type visa introuvable"));

		EtatCivil etatCivil = new EtatCivil();
		etatCivil.setNom(form.getNom());
		etatCivil.setPrenom(form.getPrenom());
		etatCivil.setNom_jeune_fille(form.getNomJeuneFille());
		etatCivil.setEmail(form.getEmail());
		etatCivil.setNumero_telephone(form.getNumeroTelephone());
		etatCivil.setDate_naissance(form.getDateNaissance());
		etatCivil.setLieu_naissance(form.getLieuNaissance());
		etatCivil.setAdresse_mada(form.getAdresseMada());
		etatCivil.setNationalite(nationalite);
		etatCivil.setSituation_familiale(situationFamiliale);
		EtatCivil savedEtatCivil = etatCivilRepository.save(etatCivil);

		Passeport passeport = new Passeport();
		passeport.setNumero_passport(form.getNumeroPasseport());
		passeport.setDate_delivrance(form.getDateDelivrancePasseport());
		passeport.setDate_expiration(form.getDateExpirationPasseport());
		passeport.setEtatCivil(savedEtatCivil);
		Passeport savedPasseport = passeportRepository.save(passeport);

		VisaTransformable visaTransformable = new VisaTransformable();
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

		return savedDemandeVisa;
	}

	 @Transactional
	 public DemandeVisa updateDemandeVisa(Long id, DemandeVisaEditForm form) {
		 DemandeVisa demande = demandeVisaRepository.findById(id)
				 .orElseThrow(() -> new IllegalArgumentException("Demande introuvable"));

		 TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(form.getTypeDemandeId())
				 .orElseThrow(() -> new IllegalArgumentException("Type de demande introuvable"));

		 TypeVisa typeVisa = typeVisaRepository.findById(form.getTypeVisaId())
				 .orElseThrow(() -> new IllegalArgumentException("Type visa introuvable"));

		 Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
				 .orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));

		 SitutationFamiliale situationFamiliale = situtationFamilialeRepository.findById(form.getSituationFamilialeId())
				 .orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable"));

		 demande.setDate_demande(form.getDateDemande());
		 demande.setType_demande_visa(typeDemandeVisa);
		 demande.setType_visa(typeVisa);

		 Passeport passeport = demande.getPasseport();
		 EtatCivil etatCivil = passeport.getEtatCivil();

		 etatCivil.setNom(form.getNom());
		 etatCivil.setPrenom(form.getPrenom());
		 etatCivil.setNom_jeune_fille(form.getNomJeuneFille());
		 etatCivil.setEmail(form.getEmail());
		 etatCivil.setNumero_telephone(form.getNumeroTelephone());
		 etatCivil.setDate_naissance(form.getDateNaissance());
		 etatCivil.setLieu_naissance(form.getLieuNaissance());
		 etatCivil.setAdresse_mada(form.getAdresseMada());
		 etatCivil.setNationalite(nationalite);
		 etatCivil.setSituation_familiale(situationFamiliale);

		 passeport.setNumero_passport(form.getNumeroPasseport());
		 passeport.setDate_delivrance(form.getDateDelivrancePasseport());
		 passeport.setDate_expiration(form.getDateExpirationPasseport());

		 VisaTransformable visaTransformable = visaTransformableRepository
				 .findFirstByEtatCivilId(etatCivil.getId())
				 .orElseGet(() -> {
					 VisaTransformable v = new VisaTransformable();
					 v.setEtatCivil(etatCivil);
					 return v;
				 });
		 visaTransformable.setNumero_passport(form.getVisaTranNumPasseport());
		 visaTransformable.setDate_delivrance(form.getVisaTranDateDelivrance());
		 visaTransformable.setDate_expiration(form.getVisaTranDateExpiration());

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
	 public void deleteDemandeVisa(Long id) {
		 if (!demandeVisaRepository.existsById(id)) {
			 throw new IllegalArgumentException("Demande introuvable");
		 }
		 dossierRepository.deleteByDemandeVisaId(id);
		 demandeVisaRepository.deleteById(id);
	 }

}
