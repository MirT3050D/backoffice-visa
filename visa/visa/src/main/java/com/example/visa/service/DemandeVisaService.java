package com.example.visa.service;
import com.example.visa.model.*;
import com.example.visa.repository.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DemandeVisaService {
	private final DemandeVisaRepository demandeVisaRepository;
	private final EtatCivilRepository etatCivilRepository;
	private final PasseportRepository passeportRepository;
	private final NationaliteRepository nationaliteRepository;
	private final SitutationFamilialeRepository situtationFamilialeRepository;
	private final TypeDemandeVisaRepository typeDemandeVisaRepository;
	private final TypeVisaRepository typeVisaRepository;

	public DemandeVisaService(
			DemandeVisaRepository demandeVisaRepository,
			EtatCivilRepository etatCivilRepository,
			PasseportRepository passeportRepository,
			NationaliteRepository nationaliteRepository,
			SitutationFamilialeRepository situtationFamilialeRepository,
			TypeDemandeVisaRepository typeDemandeVisaRepository,
			TypeVisaRepository typeVisaRepository) {
		this.demandeVisaRepository = demandeVisaRepository;
		this.etatCivilRepository = etatCivilRepository;
		this.passeportRepository = passeportRepository;
		this.nationaliteRepository = nationaliteRepository;
		this.situtationFamilialeRepository = situtationFamilialeRepository;
		this.typeDemandeVisaRepository = typeDemandeVisaRepository;
		this.typeVisaRepository = typeVisaRepository;
	}

	@Transactional
	public DemandeVisa creerDemandeVisa(CreerDemandeVisaForm form) {
		Nationalite nationalite = nationaliteRepository.findById(form.getNationaliteId())
				.orElseThrow(() -> new IllegalArgumentException("Nationalite introuvable"));

		SitutationFamiliale situationFamiliale = situtationFamilialeRepository.findById(form.getSituationFamilialeId())
				.orElseThrow(() -> new IllegalArgumentException("Situation familiale introuvable"));

		TypeDemandeVisa typeDemandeVisa = typeDemandeVisaRepository.findById(form.getTypeDemandeVisaId())
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

		DemandeVisa demandeVisa = new DemandeVisa();
		demandeVisa.setDate_demande(form.getDateDemande());
		demandeVisa.setType_demande_visa(typeDemandeVisa);
		demandeVisa.setType_visa(typeVisa);
		demandeVisa.setPasseport(savedPasseport);

		return demandeVisaRepository.save(demandeVisa);
	}

	public static class CreerDemandeVisaForm {
		@NotNull
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		private LocalDate dateDemande;

		@NotBlank
		private String nom;

		@NotBlank
		private String prenom;

		private String nomJeuneFille;

		private String email;

		@NotBlank
		private String numeroTelephone;

		@NotNull
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		private LocalDate dateNaissance;

		@NotBlank
		private String lieuNaissance;

		@NotBlank
		private String adresseMada;

		@NotNull
		private Long nationaliteId;

		@NotNull
		private Long situationFamilialeId;

		@NotBlank
		private String numeroPasseport;

		@NotNull
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		private LocalDate dateDelivrancePasseport;

		@NotNull
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
		private LocalDate dateExpirationPasseport;

		@NotNull
		private Long typeDemandeVisaId;

		@NotNull
		private Long typeVisaId;

		public LocalDate getDateDemande() {
			return dateDemande;
		}

		public void setDateDemande(LocalDate dateDemande) {
			this.dateDemande = dateDemande;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public String getNomJeuneFille() {
			return nomJeuneFille;
		}

		public void setNomJeuneFille(String nomJeuneFille) {
			this.nomJeuneFille = nomJeuneFille;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNumeroTelephone() {
			return numeroTelephone;
		}

		public void setNumeroTelephone(String numeroTelephone) {
			this.numeroTelephone = numeroTelephone;
		}

		public LocalDate getDateNaissance() {
			return dateNaissance;
		}

		public void setDateNaissance(LocalDate dateNaissance) {
			this.dateNaissance = dateNaissance;
		}

		public String getLieuNaissance() {
			return lieuNaissance;
		}

		public void setLieuNaissance(String lieuNaissance) {
			this.lieuNaissance = lieuNaissance;
		}

		public String getAdresseMada() {
			return adresseMada;
		}

		public void setAdresseMada(String adresseMada) {
			this.adresseMada = adresseMada;
		}

		public Long getNationaliteId() {
			return nationaliteId;
		}

		public void setNationaliteId(Long nationaliteId) {
			this.nationaliteId = nationaliteId;
		}

		public Long getSituationFamilialeId() {
			return situationFamilialeId;
		}

		public void setSituationFamilialeId(Long situationFamilialeId) {
			this.situationFamilialeId = situationFamilialeId;
		}

		public String getNumeroPasseport() {
			return numeroPasseport;
		}

		public void setNumeroPasseport(String numeroPasseport) {
			this.numeroPasseport = numeroPasseport;
		}

		public LocalDate getDateDelivrancePasseport() {
			return dateDelivrancePasseport;
		}

		public void setDateDelivrancePasseport(LocalDate dateDelivrancePasseport) {
			this.dateDelivrancePasseport = dateDelivrancePasseport;
		}

		public LocalDate getDateExpirationPasseport() {
			return dateExpirationPasseport;
		}

		public void setDateExpirationPasseport(LocalDate dateExpirationPasseport) {
			this.dateExpirationPasseport = dateExpirationPasseport;
		}

		public Long getTypeDemandeVisaId() {
			return typeDemandeVisaId;
		}

		public void setTypeDemandeVisaId(Long typeDemandeVisaId) {
			this.typeDemandeVisaId = typeDemandeVisaId;
		}

		public Long getTypeVisaId() {
			return typeVisaId;
		}

		public void setTypeVisaId(Long typeVisaId) {
			this.typeVisaId = typeVisaId;
		}
	}

}
