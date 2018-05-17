package com.soprasteria.interop.introrest.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import com.soprasteria.interop.introrest.model.Equipe;

public class ClientRestEquipe {
	public static void main(String[] args) {
		// client REST
		UriBuilderFactory uriTemplate = new DefaultUriBuilderFactory("http://localhost:9090");
		RestTemplate restTemplate = new RestTemplateBuilder().uriTemplateHandler(uriTemplate).build();

		// préparation de la représentation de la ressource
		Equipe equipeToto = new Equipe();
		equipeToto.setName("TOTO");

		// création via POST
		ResponseEntity<Equipe> postResponse = restTemplate.postForEntity("/equipes", equipeToto, Equipe.class);
		System.out.println("POST => " + postResponse.getBody());

		// modif role
		equipeToto = postResponse.getBody();
		equipeToto.setRole("TEST");

		// envoi de la modif via PUT
		// (l'uri de localisation est récupérée directement de la réponse POST)
		restTemplate.put(postResponse.getHeaders().getLocation(), equipeToto);
		System.out.println("PUT");

		// récupération de l'objet via GET
		// (l'uri est ici construite pour montrer une autre façon de faire par
		// rapport au PUT)
		equipeToto = restTemplate.getForObject("/equipes/{id}", Equipe.class, equipeToto.getId());
		System.out.println("GET => " + equipeToto);

		// suppression de la ressource via DELETE
		restTemplate.delete("/equipes/{id}", equipeToto.getId());
		System.out.println("DELETE");

	}
}
