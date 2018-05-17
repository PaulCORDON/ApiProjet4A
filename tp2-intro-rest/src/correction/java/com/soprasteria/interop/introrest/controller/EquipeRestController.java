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

import com.soprasteria.interop.introrest.model.Equipe;

@RestController
public class EquipeRestController {
	// Simulation d'une persistence de notre ressource en attribut d'instance
	// pour simplifier le TP. Bien évidemment, on ne fait pas ça en tant normal
	// ... car c'est stateful !
	private AtomicInteger fakeSeq = new AtomicInteger(1);
	private Map<Integer, Equipe> fakeDb = new ConcurrentHashMap<Integer, Equipe>();

	@PostMapping("/equipes")
	public ResponseEntity<Equipe> postEquipe(@RequestBody @Valid Equipe equipe) {
		// affectation d'un id et persistance
		equipe.setId(fakeSeq.incrementAndGet());
		fakeDb.put(equipe.getId(), equipe);

		// URI de localisation de la ressource
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(equipe.getId())
				.toUri();

		// réponse 202 avec la localisation et la ressource créée
		return ResponseEntity.created(location).body(equipe);
	}

	@GetMapping("/equipes/{id}")
	public ResponseEntity<Equipe> getEquipe(@PathVariable @NotNull Integer id) {
		if (fakeDb.containsKey(id)) {
			return ResponseEntity.ok(fakeDb.get(id));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/equipes/{id}")
	public ResponseEntity<Equipe> deleteEquipe(@PathVariable @NotNull Integer id) {
		if (fakeDb.containsKey(id)) {
			fakeDb.remove(id);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/equipes/{id}")
	public ResponseEntity<Void> deleteEquipe(@PathVariable @NotNull Integer id, @RequestBody @Valid Equipe equipe) {
		if (fakeDb.containsKey(id)) {
			// replace
			equipe.setId(id);
			fakeDb.put(id, equipe);

			return ResponseEntity.ok().build();
		} else {
			// affectation d'un id et persistance
			equipe.setId(fakeSeq.incrementAndGet());
			fakeDb.put(equipe.getId(), equipe);

			// URI de localisation de la ressource
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().build(equipe.getId());

			// réponse 202 avec la localisation et la ressource créée
			return ResponseEntity.created(location).build();
		}
	}
}
