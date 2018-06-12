package com.soprasteria.interop.introrest.controller;

import java.net.URI;
import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import database.DBService;
import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;
import fr.ensim.projet4a.model.Enonce;
import fr.ensim.projet4a.model.Exo1Math;
import fr.ensim.projet4a.model.ParamEl1;
import fr.ensim.projet4a.model.ParamEm1;
import fr.ensim.projet4a.model.ParamEm2;
import fr.ensim.projet4a.model.SousCompetence;

@CrossOrigin(origins = "*", maxAge = 0)
@RestController
public class ExampleRestController {		
	
	
	
		/*
		 * Toutes les actions disponibles pour gérer les élèves;
		 */	
		/**
		 * Récupère l'éleve grace à son nomprenom
		 * @param nomprenom
		 * @return l'élève
		 */
		@GetMapping("/classe/{nom}/eleve/{nomprenom}")
		public ArrayList<Eleve> getEleve(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
			return DBService.getEleveFromDB(nomprenom,nom);
		}
		/**
		 * Récupère tous les eleves qui ont le paramètre param d'apliquer dans l'exercice 1 de calcul
		 */
		@GetMapping("/parameleve/paramEm1/{nomparam}")
		public ArrayList<Eleve> getEleveAvecParamEm1(@PathVariable @NotNull String nomparam) {
			
			return DBService.getElevesAvecParamEm1FromDB(nomparam);
		}
		/**
		 * Récupère tous les eleves qui ont le paramètre param d'apliquer dans l'exercice 2 de calcul
		 */
		@GetMapping("/parameleve/paramEm2/{nomparam}")
		public ArrayList<Eleve> getEleveAvecParamEm2(@PathVariable @NotNull String nomparam) {
			return DBService.getElevesAvecParamEm2FromDB(nomparam);
		}
		/**
		 * Récupère tous les eleves qui ont le paramètre param d'apliquer dans l'exercice 1 de lecture
		 */
		@GetMapping("/parameleve/paramEl1/{nomparam}")
		public ArrayList<Eleve> getEleveAvecParamEl1(@PathVariable @NotNull String nomparam) {
			return DBService.getElevesAvecParamEl1FromDB(nomparam);
		}		
		/**
		 * Création d'un élève 
		 * @param eleve
		 * @return l'élève créé
		 */
		@PostMapping("/eleve")
		public ResponseEntity<Eleve> postEleve(@RequestBody @Valid Eleve eleve) {
			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nomprenom,dateDeNaissance,classeName}").buildAndExpand(eleve).toUri();
			DBService.addEleveToDB(eleve.getNomPrenom(), eleve.getDateDeNaissance(),eleve.getClasseName());
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(eleve);
		}	
				
		/**
		 * Supprime l'éleve grace à son ID
		 * @param id
		 * @return
		 */
		@DeleteMapping("/classe/{nom}/eleve/{nomPrenom}")
		public void deleteEleve(@PathVariable @NotNull String nomPrenom,@PathVariable @NotNull String nom) {
			DBService.deleteEleveFromDB(nomPrenom,nom);		
		}
				
		/**
		 * Modidie l'éleve grace à son nomPrenom et le nom de sa classe
		 * @param nomPrenom de l'élève 
		 * @param nom de sa classe
		 * @return l'élève modifié
		 */
		@PutMapping("/classe/{nom}/eleve/{nomPrenom}")
		public void updateEleve(@PathVariable @NotNull String nomPrenom, @PathVariable @NotNull String nom,@RequestBody @Valid Eleve eleve) {
			ServletUriComponentsBuilder.fromCurrentRequest().path("/{nomprenom,dateDeNaissance,classeName}").buildAndExpand(eleve).toUri();
			System.out.println(""+eleve.getNomPrenom()+eleve.getClasseName()+eleve.getDateDeNaissance().toString());
			DBService.updateEleveFromDB(nomPrenom,nom,eleve);			
		}	
		
		
		/*
		 * Toutes les actions disponible pour gérer les classes;
		 */	
		/**
		 * Création d'une classe
		 * @param classe
		 * @return la classe créé
		 */
		@PostMapping("/classe")
		public ResponseEntity<Classe> postClasse(@RequestBody @Valid Classe classe) {
			
			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nom}").buildAndExpand(classe).toUri();
			
			DBService.addClasseToDB(classe.getNom());
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(classe);
		}	
		
		/**
		 * Récupère la classe grace à son nom
		 * @param nomClasse
		 * @return la classe
		 */
		@GetMapping("/classe/{nom}")
		public Classe getClasse(@PathVariable @NotNull String nom) {
			return DBService.getClasseFromDB(nom);
		}	
		
		/**
		 * Récupère toutes les classes 
		 * @return toutes les classes
		 */
		@GetMapping("/classe")
		public ArrayList<Classe> getAllClasse() {
			return DBService.getAllClasseFromDB();
		}	

		/**
		 * Supprime une classe grace à son nom
		 * @param id
		 * @return
		 */
		@DeleteMapping("/classe/{nom}")
		public void deleteClasse(@PathVariable @NotNull String nom) {
			DBService.deleteClasseFromDB(nom);		
		}
		
		/*
		 * Toutes les actions disponible pour gérer les Parametres;
		 */	
		/**
		 * Desapplique de parameleve un paramètre de l'exercice 1 de calcul
		 */
		@DeleteMapping("/parameleve/paramEm1/{nom}")
		public void desappliqueParamEm1(@PathVariable @NotNull String nom) {
			DBService.desappliqueParamEm1FromDB(nom);		
		}
		/**
		 * Desapplique de parameleve un paramètre de l'exercice 1 de calcul
		 */
		@DeleteMapping("/parameleve/paramEm2/{nom}")
		public void desappliqueParamEm2(@PathVariable @NotNull String nom) {
			DBService.desappliqueParamEm2FromDB(nom);		
		}
		/**
		 * Desapplique de parameleve un paramètre de l'exercice 1 de calcul
		 */
		@DeleteMapping("/parameleve/paramEl1/{nom}")
		public void desappliqueParamEl1(@PathVariable @NotNull String nom) {
			DBService.desappliqueParamEl1FromDB(nom);		
		}		
		/**
		 * Supprime un paramètre de l'exercice 1 de lecture
		 */
		@DeleteMapping("/paramEl1/{nom}")
		public void supprParamEl1(@PathVariable @NotNull String nom) {
			DBService.supprParamEl1fromBD(nom);		
		}
		/**
		 * Supprime un paramètre de l'exercice 1 de calcul
		 */
		@DeleteMapping("/paramEm1/{nom}")
		public void supprParamEm1(@PathVariable @NotNull String nom) {
			DBService.supprParamEm1fromBD(nom);		
		}
		/**
		 * Supprime un paramètre de l'exercice 2 de calcul
		 */
		@DeleteMapping("/paramEm2/{nom}")
		public void supprParamEm2(@PathVariable @NotNull String nom) {
			DBService.supprParamEm2fromBD(nom);		
		}
		
		/**
		 * Récupère tous les Parametre de l'exercice 1 de calcul
		 * @return tous les Parametre de l'exercice 1 de calcul
		 */
		@GetMapping("/paramEm1")
		public ArrayList<ParamEm1> getAllParamEm1(){	
			
			return DBService.getAllParamEm1FromDB();
		}
		/**
		 * Récupère tous les Parametre de l'exercice 1 de calcul
		 * @return tous les Parametre de l'exercice 1 de calcul
		 */
		@GetMapping("/paramEm2")
		public ArrayList<ParamEm2> getAllParamEm2(){	
			
			return DBService.getAllParamEm2FromDB();
		}
		/**
		 * Récupère tous les Parametre de l'exercice 1 de lecture
		 * @return tous les Parametre de l'exercice 1 de lecture
		 */
		@GetMapping("/paramEl1")
		public ArrayList<ParamEl1> getAllParamEl1(){	
			
			return DBService.getAllParamEl1FromDB();
		}
		
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param nomprenom
		 * @param nom
		 * @return paramem1
		 */
		@GetMapping("/classe/{nom}/eleve/{nomprenom}/paramEm1")
		public ArrayList<ParamEm1> getParamEm1(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
			return DBService.getEleveParamEm1FromBD(nomprenom, nom);
		}
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param nomprenom
		 * @param nom
		 * @return paramem2
		 */
		@GetMapping("/classe/{nom}/eleve/{nomprenom}/paramEm2")
		public ArrayList<ParamEm2> getParamEm2(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
			return DBService.getEleveParamEm2FromBD(nomprenom, nom);
		}
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param nomprenom
		 * @param nom
		 * @return paramel1
		 */
		@GetMapping("/classe/{nom}/eleve/{nomprenom}/paramEl1")
		public ArrayList<ParamEl1> getParamEl1(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
			return DBService.getEleveParamEl1FromBD(nomprenom, nom);
		}
		/** 
		 * Ajoute les parametres saisis par le prof
		 */
		@PostMapping("/paramEl1")
		public ResponseEntity<ParamEl1> postParamEl1(@RequestBody @Valid ParamEl1 param) {
			
			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nom,enonceDisparait,multipleApparution,nbApparition,tempsApparution,nbEnonce,nbAparitionSimultanee}").buildAndExpand(param).toUri();
			
			DBService.addParamEl1ToDB(param);
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(param);
		}
		/** 
		 * Ajoute les parametres saisis par le prof
		 */
		@PostMapping("/paramEm1")
		public ResponseEntity<ParamEm1> postParamEm1(@RequestBody @Valid ParamEm1 param) {
			
			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nom,nbBornes, nbQuestions,disparition,tempsRestantApparant, ordreApparition, borneSelectionnable, borneEqualsOp, valMax, frise, tempsRep, pairOnly, operateur1, operateur2, operateur3, operateur4}").buildAndExpand(param).toUri();
			
			DBService.addParamEm1ToDB(param);
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(param);
		}
		/** 
		 * Ajoute les parametres saisis par le prof
		 */
		@PostMapping("/paramEm2")
		public ResponseEntity<ParamEm2> postParamEm2(@RequestBody @Valid ParamEm2 param) {
			
			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nom, typeRep, nbCalcul, valMaxOperande,nombrePair, nombreImpair, typeNombre, repDeuxBornes, repQuatreBornes, repPaveNum, tempsRep, pairOnly, operateur1, operateur2, operateur3, operateur4, calcChaine}").buildAndExpand(param).toUri();
			
			DBService.addParamEm2ToDB(param);
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(param);
		}
		/**
		 * méthode qui applique un paramètre de l'exercice 2 de calcul à un eleve		 * 
		 */
		@PostMapping("/paramEm2/{nomParam}/classe/{nom}/eleve/{nomPrenom}")
		public void appliqueParamEm2(@PathVariable @NotNull String nomPrenom, @PathVariable @NotNull String nom,@PathVariable @NotNull String nomParam) {
			DBService.appliqueParamEm2ToEleve(nomParam,nomPrenom,nom);			
		}
		/**
		 * méthode qui applique un paramètre de l'exercice 1 de calcul à un eleve		 * 
		 */
		@PostMapping("/paramEm1/{nomParam}/classe/{nom}/eleve/{nomPrenom}")
		public void appliqueParamEm1(@PathVariable @NotNull String nomPrenom, @PathVariable @NotNull String nom,@PathVariable @NotNull String nomParam) {
			DBService.appliqueParamEm1ToEleve(nomParam,nomPrenom,nom);
		}
		/**
		 * méthode qui applique un paramètre de l'exercice 1 de lecture à un eleve
		 */
		@PostMapping("/paramEl1/{nomParam}/classe/{nom}/eleve/{nomPrenom}")
		public void appliqueParamEl1(@PathVariable @NotNull String nomPrenom, @PathVariable @NotNull String nom,@PathVariable @NotNull String nomParam) {
			DBService.appliqueParamEl1ToEleve(nomParam,nomPrenom,nom);
		}
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param id
		 * @return paramem2
		 */
		@GetMapping("/eleve/{id}/paramEm1")
		public ParamEm1 getParamEm1(@PathVariable @NotNull int id) {
			return DBService.getEleveParamEm1FromBD(id);
		}
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param id
		 * @return paramem2
		 */
		@GetMapping("/eleve/{id}/paramEm2")
		public ParamEm2 getParamEm2(@PathVariable @NotNull int id) {
			return DBService.getEleveParamEm2FromBD(id);
		}
		/** Récupère les parametres mis sur la tablette d'un eleve d'une classe
		 * @param id
		 * @return paramem2
		 */
		@GetMapping("/eleve/{id}/paramEl1")
		public ParamEl1 getParamEl1(@PathVariable @NotNull int id) {
			return DBService.getEleveParamEl1FromBD(id);
		}
		/*
		 * Toutes les actions disponibles pour gérer les SousCompetences;
		 */	
		@GetMapping("/classe/{nom}/eleve/{nomprenom}/SousCompetence")
		public ArrayList<SousCompetence> getSousCompEleve(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
			return DBService.getEleveSousCompetencesFromBD(nomprenom, nom);
		}
		
		/*
		 * Toutes les actions disponibles pour gérer les mots;
		 */	
		@GetMapping("/mots/{mot}")
		public ArrayList<String> getSousCompEleve(@PathVariable @NotNull String mot) {
			return DBService.getEnonceFromBD(mot);
		}
		/**
		 * Applique un enoncé à un paramètrage d'exercice 1 de lecture en désapliquant les anciennes énoncé du paramétrage
		 */
		@PostMapping("/paramEl1/{nomParam}/enonce")
		public void appliqueEnonceToParamEl1SupprHistorique(@PathVariable @NotNull String nomParam,@RequestBody @Valid Enonce enonce) {
			DBService.appliqueEnonceToParamEl1(nomParam,enonce);
		}
		
		/**
		 * Désapplique un enoncé à un paramètrage d'exercice 1 de lecture
		 */
		@PostMapping("/paramEl1/{nomParam}/supprenonce")
		public void desappliqueEnonceToParamEl1(@PathVariable @NotNull String nomParam,@RequestBody @Valid Enonce enonce) {
			DBService.desappliqueEnonceToParamEl1(nomParam);	
		}	
		
		
		/*
		 * Toutes les actions disponibles pour gérer les exercices;
		 */	
		
//		@GetMapping("/classe/{nom}/eleve/{nomprenom}/historiqueEm1")
//		public ArrayList<Exo1Math> getHistoriqueExercices1MEleve(@PathVariable @NotNull String nomprenom, @PathVariable @NotNull String nom) {
//			return DBService.getHistoriqueEm1EleveFromBD(nomprenom,nom);
//		}
		
		
}