package cal335.projet.mes_chums.service;

import cal335.projet.mes_chums.cache.CacheFavoris;
import cal335.projet.mes_chums.dao.AdresseDAO;
import cal335.projet.mes_chums.dao.ContactDAO;
import cal335.projet.mes_chums.dto.ContactDTO;
import cal335.projet.mes_chums.dto.RequeteProximiteDTO;
import cal335.projet.mes_chums.geocodage.ApiGeocodage;
import cal335.projet.mes_chums.mapper.ContactMapper;
import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Contact;
import cal335.projet.mes_chums.modele.Coordonnees;
import cal335.projet.mes_chums.CalculateurDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactService {

    private final ContactDAO contactDAO;
    private final AdresseDAO adresseDAO;
    private final ApiGeocodage apiGeocodage;
    private final CacheFavoris cacheFavoris;

    public ContactService() {
        this.contactDAO = new ContactDAO();
        this.adresseDAO = new AdresseDAO();
        this.apiGeocodage = new ApiGeocodage();
        this.cacheFavoris = CacheFavoris.getInstance();

        List<Contact> tous = contactDAO.trouverTous();
        List<Contact> favoris = new ArrayList<>();
        for (Contact contact : tous) {
            if (contact.isFavoris()) {
                favoris.add(contact);
            }
        }
        cacheFavoris.initialiserCache(favoris);
    }

    public ContactDTO ajouterContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.versModele(contactDTO);

        for (Adresse adresse : contact.getAdresses()) {
            Coordonnees coords = apiGeocodage.obtenirCoordonneesPourAdresse(adresse.getRue(), adresse.getVille(), adresse.getCodePostal(), adresse.getPays());
            adresse.setCoordonnees(new Coordonnees( coords.getLatitude(), coords.getLongitude()));
        }
        contactDAO.ajouter(contact);

        for (Adresse adresse : contact.getAdresses()) {
            adresse.setAdresse_contact_id(contact.getId_contact());
            adresseDAO.ajouter(adresse);
        }

        if (contact.isFavoris()) {
            cacheFavoris.ajouterFavori(contact);
        }

        return ContactMapper.versDTO(contact);
    }

    public ContactDTO rechercherContactParId(int id) {
        Contact contact = contactDAO.trouverParId(id);
        if (contact == null) {
            return null;
        }
    
        List<Adresse> adresses = adresseDAO.trouverParContactId(id);
        contact.setAdresses(adresses);
    
        return ContactMapper.versDTO(contact);
    }
    

    public void marquerFavori(int id) {
        Contact contact = contactDAO.trouverParId(id);
        if (contact != null) {
            contact.setFavoris(true);
            contactDAO.mettreAJour(contact);
            cacheFavoris.ajouterFavori(contact);
        }
    }

    public void enleverFavori(int id) {
        Contact contact = contactDAO.trouverParId(id);
        if (contact != null) {
            contact.setFavoris(false);
            contactDAO.mettreAJour(contact);
            cacheFavoris.retirerFavori(contact);
        }
    }

    public void ajouterAdressesAuContact(int contactId, List<Adresse> adresses) {
        for (Adresse adresse : adresses) {
            var coords = apiGeocodage.obtenirCoordonneesPourAdresse(
                adresse.getRue(),
                adresse.getVille(),
                adresse.getCodePostal(),
                adresse.getPays()
            );

            adresse.setCoordonnees(new Coordonnees(coords.getLatitude(), coords.getLongitude()));
        }

        contactDAO.ajouterAdressesAuContact(contactId, adresses);
    }


public List<ContactDTO> rechercherContactsProches(double latitude, double longitude, double rayon) {
    Coordonnees mesCoordonnees = new Coordonnees(latitude, longitude);

    List<Contact> tousLesContacts = contactDAO.trouverTous();
    List<Contact> contactsProches = new ArrayList<>();

    for (Contact contact : tousLesContacts) {
        for (Adresse adresse : contact.getAdresses()) {
            double distance = CalculateurDistance.calculerDistance(mesCoordonnees, adresse.getCoordonnees());
            if (distance <= rayon) {
                contactsProches.add(contact);
                break; 
            }
        }
    }

    return ContactMapper.versDTOs(contactsProches);
}

}
