Back
Creation classe:
- Passport
- Visatransformable
- EtatCivil
Mba avadio nullable ze champ tsy obligatoire de non nullable ilay champ obligatoire (alefako eo ambany) ao amin'ny conception
- Demande 
- typeVisa
- ChampFournir
   - getChampBy(typeVisa)
- typedemande 

Création fonction:
Avant creation objet, contrôle des données

CreerDemande()
     SavePasseport()
     SaveVisaTransformable()
     SaveEtatCivil()
     SaveDemande()
     Transactionnel daholo

Front
- Choix demande nouveau titre
- Formulaire
EtatCivil: ses attributs 
Passport : ses champs
Visa transformables: ses champs 
Dossier importants: via fonctions getChampBy(typeVisa) à coché daholo