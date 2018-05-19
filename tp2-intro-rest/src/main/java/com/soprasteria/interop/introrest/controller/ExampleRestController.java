package com.soprasteria.interop.introrest.controller;

import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import database.DBService;
import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;

@CrossOrigin(origins = "*", maxAge = 0)
@RestController
public class ExampleRestController {		
		/*
		 * Toutes les actions disponibles pour gérer les élèves;
		 */	
		
		/**
		 * Création d'un élève 
		 * @param eleve
		 * @return l'élève créé
		 */
		@PostMapping("/eleve")
		public ResponseEntity<Eleve> postEleve(@RequestBody @Valid Eleve eleve) {
			// URI de localisation de la ressource
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{nomprenom,dateDeNaissance,classeName}").buildAndExpand(eleve).toUri();
			DBService.addEleveToDB(eleve.getNomPrenom(), eleve.getDateDeNaissance(),eleve.getClasseName());
			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(eleve);
		}	
		
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
		 * Récupère la classe grace à son ID
		 * @param nomClasse
		 * @return la classe
		 */
		@GetMapping("/classe/{nom}")
		public Classe getClasse(@PathVariable @NotNull String nom) {
			Classe cl=DBService.getClasseFromDB(nom);
			return cl;
		}		
		
//		/**
//		 * Récupère l'éleve grace à son nomprenom
//		 * @param nomprenom
//		 * @return l'élève
//		 */
//		@GetMapping("/eleve/{nomprenom}")
//		public ResponseEntity<ArrayList<Eleve>> getEquipe(@PathVariable @NotNull String nomprenom) {
//			return ResponseEntity.ok( DBService.getEleveFromDB(nomprenom));
//		}	
		
//		/**
//		 * Supprime l'éleve grace à son ID
//		 * @param id
//		 * @return
//		 */
//		@DeleteMapping("/eleve/{id}")
//		public ResponseEntity<Eleve> deleteEquipe(@PathVariable @NotNull Integer id) {
//			if (fakeDb.containsKey(id)) {
//				fakeDb.remove(id);
//				return ResponseEntity.noContent().build();
//			}
//
//			return ResponseEntity.notFound().build();
//		}
//		
		/**
		 * Modidie l'éleve grace à son ID
		 * @param id
		 * @param eleve
		 * @return
		 */
//		@PutMapping("/eleve/{id}")
//		public ResponseEntity<Void> deleteEquipe(@PathVariable @NotNull Integer id, @RequestBody @Valid Eleve eleve) {
//			if (fakeDb.containsKey(id)) {
//				// replace
//				eleve.setID(id);
//				fakeDb.put(id, eleve);
//
//				return ResponseEntity.ok().build();
//			} else {
//				// affectation d'un id et persistance
//				eleve.setID(fakeSeq.incrementAndGet());
//				fakeDb.put(eleve.getID(), eleve);
//
//				// URI de localisation de la ressource
//				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(eleve.getID());
//
//				// réponse 202 avec la localisation et la ressource créée
//				return ResponseEntity.created(location).build();
//			}
//		}	
//		
//		/*
//		 * Toutes les actions disponible pour gérer les classes;
//		 */	
//		
//		/**
//		 * Création d'une classe
//		 * @param  Classe
//		 * @return la classe créée
//		 */
//		@PostMapping("/classe")
//		public ResponseEntity< Classe> postEquipe(@RequestBody @Valid Classe  classe) {
//			// affectation d'un id et persistance
//			 classe.setNom(nom);(fakeSeq.incrementAndGet());
//			fakeDb.put( classe.getID(),  classe);
//
//			// URI de localisation de la ressource
//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand( classe.getID())
//					.toUri();
//
//			// réponse 202 avec la localisation et la ressource créée
//			return ResponseEntity.created(location).body(eleve);
//		}	
//		
//		/**
//		 * Récupère l'éleve grace à son ID
//		 * @param id
//		 * @return l'élève
//		 */
//		@GetMapping("/eleve/{id}")
//		public ResponseEntity<Eleve> getEquipe(@PathVariable @NotNull Integer id) {
//			if (fakeDb.containsKey(id)) {
//				return ResponseEntity.ok(fakeDb.get(id));
//			}
//
//			return ResponseEntity.notFound().build();
//		}		
//		
//		/**
//		 * Supprime l'éleve grace à son ID
//		 * @param id
//		 * @return
//		 */
//		@DeleteMapping("/eleve/{id}")
//		public ResponseEntity<Eleve> deleteEquipe(@PathVariable @NotNull Integer id) {
//			if (fakeDb.containsKey(id)) {
//				fakeDb.remove(id);
//				return ResponseEntity.noContent().build();
//			}
//
//			return ResponseEntity.notFound().build();
//		}
//		
//		/**
//		 * Modidie l'éleve grace à son ID
//		 * @param id
//		 * @param eleve
//		 * @return
//		 */
//		@PutMapping("/eleve/{id}")
//		public ResponseEntity<Void> deleteEquipe(@PathVariable @NotNull Integer id, @RequestBody @Valid Eleve eleve) {
//			if (fakeDb.containsKey(id)) {
//				// replace
//				eleve.setID(id);
//				fakeDb.put(id, eleve);
//
//				return ResponseEntity.ok().build();
//			} else {
//				// affectation d'un id et persistance
//				eleve.setID(fakeSeq.incrementAndGet());
//				fakeDb.put(eleve.getID(), eleve);
//
//				// URI de localisation de la ressource
//				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(eleve.getID());
//
//				// réponse 202 avec la localisation et la ressource créée
//				return ResponseEntity.created(location).build();
//			}
//		}	
//		
//		

}