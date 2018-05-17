package client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import fr.ensim.projet4a.model.Eleve;

public class ClientRestEleve {
	public static void main(String[] args) {
		// client REST
		UriBuilderFactory uriTemplate = new DefaultUriBuilderFactory("http://localhost:9090");
		RestTemplate restTemplate = new RestTemplateBuilder().uriTemplateHandler(uriTemplate).build();

		// préparation de la représentation de la ressource
		Eleve equipeToto = new Eleve();
		equipeToto.setNomPrenom("ValentinLeMechec");
		equipeToto.setID(0);

		// création via POST
		ResponseEntity<Eleve> postResponse = restTemplate.postForEntity("/eleve", equipeToto, Eleve.class);
		System.out.println("POST => " + postResponse.getBody());

		// modif role
		equipeToto = postResponse.getBody();
		equipeToto.setNomPrenom("CordonPaul");

		// envoi de la modif via PUT
		// (l'uri de localisation est récupérée directement de la réponse POST)
		restTemplate.put(postResponse.getHeaders().getLocation(), equipeToto);
		System.out.println("PUT");

		// récupération de l'objet via GET
		// (l'uri est ici construite pour montrer une autre façon de faire par
		// rapport au PUT)
		equipeToto = restTemplate.getForObject("/equipes/{id}", Eleve.class, equipeToto.getID());
		System.out.println("GET => " + equipeToto);

		// suppression de la ressource via DELETE
		restTemplate.delete("/eleve/{id}", equipeToto.getID());
		System.out.println("DELETE");

	}


}
