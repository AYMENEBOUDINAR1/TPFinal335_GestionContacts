package cal335.projet.mes_chums.controleur;

import cal335.projet.mes_chums.dto.AdresseDTO;
import cal335.projet.mes_chums.dto.AjouterAdressesDTO;
import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.dto.RequeteProximiteDTO;
import cal335.projet.mes_chums.mapper.AdresseMapper;
import cal335.projet.mes_chums.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ContactControleur implements HttpHandler {
    private final ContactService contactService;
    private final ObjectMapper objectMapper;

    public ContactControleur(ContactService contactService) {
        this.contactService = contactService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange echange) throws IOException {
        String methode = echange.getRequestMethod();
        String path = echange.getRequestURI().getPath();


        if ("POST".equals(methode) && echange.getRequestURI().getPath().equals("/contacts")) {
            ajouterContact(echange);
        }
        else if ("POST".equalsIgnoreCase(methode) && "/contacts/adresses".equals(path)) {
            ajouterAdressesAuContact(echange);
        }  else if ("POST".equalsIgnoreCase(methode) && path.equals("/contacts/rechercher")) {
                rechercherContactParId(echange);

        } else if ("POST".equals(methode) && echange.getRequestURI().getPath().equals("/contacts/favori")) {
            marquerFavori(echange);
        } else if ("DELETE".equals(methode) && echange.getRequestURI().getPath().equals("/contacts/favori")) {
            enleverFavori(echange);
        } else if ("GET".equals(methode) && echange.getRequestURI().getPath().equals("/contacts/proximite")) {
            rechercherProximite(echange);
        } else {
            echange.sendResponseHeaders(404, -1);
        }
    }

    private void ajouterContact(HttpExchange echange) throws IOException {
        ContactDTO contactDTO = objectMapper.readValue(echange.getRequestBody(), ContactDTO.class);
        contactService.ajouterContact(contactDTO);
        
        echange.sendResponseHeaders(201, -1);
    }


    private void rechercherContactParId(HttpExchange echange) throws IOException {
        Map<String, Integer> reponse = objectMapper.readValue(echange.getRequestBody(), Map.class);

        Integer id = reponse.get("id");
        ContactDTO contact = contactService.rechercherContactParId(id);

        echange.sendResponseHeaders(204, -1);
        
    }

    private void marquerFavori(HttpExchange echange) throws IOException {
        Integer id = objectMapper.readValue(echange.getRequestBody(), Integer.class);
        contactService.marquerFavori(id);
        echange.sendResponseHeaders(204, -1);
    }

    private void enleverFavori(HttpExchange echange) throws IOException {
        Integer id = objectMapper.readValue(echange.getRequestBody(), Integer.class);
        contactService.enleverFavori(id);
        echange.sendResponseHeaders(204, -1);
    }

    private void ajouterAdressesAuContact(HttpExchange echange) throws IOException {

    AjouterAdressesDTO reponse = objectMapper.readValue(echange.getRequestBody(), AjouterAdressesDTO.class);

    Integer contactId = reponse.getContactId();
    List<AdresseDTO> adressesDTO = reponse.getAdresses();
    contactService.ajouterAdressesAuContact(contactId, AdresseMapper.versEntites(adressesDTO));
    echange.sendResponseHeaders(204, -1); 
    }


  
    private void rechercherProximite(HttpExchange echange) throws IOException {
        Map<String, Object> requete = objectMapper.readValue(echange.getRequestBody(), Map.class);

        Double latitude = (Double) requete.get("latitude");
        Double longitude = (Double) requete.get("longitude");
        Double rayon = (Double) requete.get("rayon");
        List<ContactDTO> proches = contactService.rechercherContactsProches(latitude, longitude, rayon);
        echange.sendResponseHeaders(204, -1);
       
    }


    
    
}
