package cal335.projet.mes_chums.dto;

import java.util.List;

import cal335.projet.mes_chums.modele.Adresse;

public class ContactDTO {
    private Integer id_contact;
    private String nom;
    private String prenom;
    private boolean isFavoris;
    private List<AdresseDTO> adressesDTO;

    public ContactDTO() {}

    public ContactDTO(Integer id_contact, String nom, String prenom, boolean isFavoris, List<AdresseDTO> adresses) {
        this.id_contact = id_contact;
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
        this.adressesDTO = adressesDTO;
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

    public List<AdresseDTO> getAdressesDTO() {
        return adressesDTO;
    }

    public void setAdresses(List<AdresseDTO> adresses) {
        this.adressesDTO = adresses;
    }

}
