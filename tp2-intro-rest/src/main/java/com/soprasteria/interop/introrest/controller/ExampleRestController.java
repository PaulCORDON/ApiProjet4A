package com.soprasteria.interop.introrest.controller;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.ensim.projet4a.model.Classe;
import fr.ensim.projet4a.model.Eleve;

@RestController
public class ExampleRestController {

	// Simulation d'une persistence de notre ressource en attribut d'instance
		// pour simplifier le TP. Bien évidemment, on ne fait pas ça en tant normal
		// ... car c'est stateful !
		private AtomicInteger fakeSeq = new AtomicInteger(1);
		private Map<Integer, Eleve> fakeDb = new ConcurrentHashMap<Integer, Eleve>();
		private AtomicInteger fakeSeqc = new AtomicInteger(1);
		private Map<Integer, Classe> fakeDbc = new ConcurrentHashMap<Integer, Classe>();
		
		
		/*
		 * Toutes les actions disponible pour gérer les élèves;
		 */	
		
		/**
		 * Création d'un élève 
		 * @param eleve
		 * @return l'élève créé
		 */
		@PostMapping("/eleve")
		public ResponseEntity<Eleve> postEquipe(@RequestBody @Valid Eleve eleve) {
			// affectation d'un id et persistance
			eleve.setID(fakeSeq.incrementAndGet());
			fakeDb.put(eleve.getID(), eleve);

			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(eleve.getID())
					.toUri();

			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).body(eleve);
		}	
		
		/**
		 * Récupère l'éleve grace à son ID
		 * @param id
		 * @return l'élève
		 */
		@GetMapping("/eleve/{id}")
		public ResponseEntity<Eleve> getEquipe(@PathVariable @NotNull Integer id) {
			if (fakeDb.containsKey(id)) {
				return ResponseEntity.ok(fakeDb.get(id));
			}

			return ResponseEntity.notFound().build();
		}		
		
		/**
		 * Supprime l'éleve grace à son ID
		 * @param id
		 * @return
		 */
		@DeleteMapping("/eleve/{id}")
		public ResponseEntity<Eleve> deleteEquipe(@PathVariable @NotNull Integer id) {
			if (fakeDb.containsKey(id)) {
				fakeDb.remove(id);
				return ResponseEntity.noContent().build();
			}

			return ResponseEntity.notFound().build();
		}
		
		/**
		 * Modidie l'éleve grace à son ID
		 * @param id
		 * @param eleve
		 * @return
		 */
		@PutMapping("/eleve/{id}")
		public ResponseEntity<Void> deleteEquipe(@PathVariable @NotNull Integer id, @RequestBody @Valid Eleve eleve) {
			if (fakeDb.containsKey(id)) {
				// replace
				eleve.setID(id);
				fakeDb.put(id, eleve);

				return ResponseEntity.ok().build();
			} else {
				// affectation d'un id et persistance
				eleve.setID(fakeSeq.incrementAndGet());
				fakeDb.put(eleve.getID(), eleve);

				// URI de localisation de la ressource
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(eleve.getID());

				// réponse 202 avec la localisation et la ressource créée
				return ResponseEntity.created(location).build();
			}
		}	
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