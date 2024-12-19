package cal335.projet.mes_chums.cache;

import cal335.projet.mes_chums.modele.Contact;
import cal335.projet.mes_chums.modele.Coordonnees;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheFavoris {
    private static CacheFavoris instance;
    private Map<Contact, List<Coordonnees>> contactsFavoris;

    private CacheFavoris() {
        this.contactsFavoris = new ConcurrentHashMap<>();
    }

    public static synchronized CacheFavoris getInstance() {
        if (instance == null) {
            instance = new CacheFavoris();
        }
        return instance;
    }

    public void initialiserCache(List<Contact> favoris) {
        for (Contact contact : favoris) {
            // Récupérer toutes les coordonnées du contact (adresses)
            // et les stocker dans la map. Supposons que Contact a method getAllCoordonnees()
            contactsFavoris.put(contact, contact.getListCoordonnees());
        }
    }

    public void ajouterFavori(Contact contact) {
        contactsFavoris.put(contact, contact.getListCoordonnees());
    }

    public void retirerFavori(Contact contact) {
        contactsFavoris.remove(contact);
    }

    public Map<Contact, List<Coordonnees>> getContactsFavoris() {
        return contactsFavoris;
    }
}
