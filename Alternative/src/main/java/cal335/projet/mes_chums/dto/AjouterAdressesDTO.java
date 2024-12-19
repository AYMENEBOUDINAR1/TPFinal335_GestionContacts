package cal335.projet.mes_chums.dto;

import java.util.List;

public class AjouterAdressesDTO {
    private Integer contactId;
    private List<AdresseDTO> adresses;

    // Getters et Setters
    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public List<AdresseDTO> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<AdresseDTO> adresses) {
        this.adresses = adresses;
    }
}
