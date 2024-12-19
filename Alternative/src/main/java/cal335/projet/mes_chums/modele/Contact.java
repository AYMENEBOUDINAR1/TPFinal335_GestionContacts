package cal335.projet.mes_chums.modele;

import java.util.ArrayList;
import java.util.List;


public class Contact {
    private Integer id_contact;
    private String nom;
    private String prenom;
    private boolean isFavoris;
    private List<Adresse> adresses;

    public Contact() {}

    public Contact(Integer id_contact, String nom, String prenom, boolean isFavoris, List<Adresse> adresses) {
        this.id_contact = id_contact;
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
        this.adresses = adresses;
    }

    public Integer getId_contact() {
        return id_contact;
    }

    public void setId_contact(Integer id_contact) {
        this.id_contact = id_contact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isFavoris() {
        return isFavoris;
    }

    public void setFavoris(boolean favoris) {
        isFavoris = favoris;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public List<Coordonnees> getListCoordonnees() {
        List<Coordonnees> coordonnees = new ArrayList<>();
        for (Adresse adresse : adresses) {
            coordonnees.add(adresse.getCoordonnees());
        }
        return coordonnees;
    }


}
