package cal335.projet.mes_chums.mapper;

import cal335.projet.mes_chums.dto.AdresseDTO;
import cal335.projet.mes_chums.modele.Adresse;

import java.util.ArrayList;
import java.util.List;

public class AdresseMapper {

    public static Adresse VersModele(AdresseDTO adresseDTO) {
        if (adresseDTO == null) {
            return null;
        }
        return new Adresse(
            adresseDTO.getId_adresse(),
            adresseDTO.getRue(),
            adresseDTO.getVille(),
            adresseDTO.getCodePostal(),
            adresseDTO.getPays(),
            adresseDTO.getCoordonnees()
        );
    }

    public static List<Adresse> versEntites(List<AdresseDTO> adressesDTOs) {
        if (adressesDTOs == null || adressesDTOs.isEmpty()) {
            return new ArrayList<>();
        }

        List<Adresse> adresses = new ArrayList<>();
        for (AdresseDTO adresseDTO : adressesDTOs) {
            adresses.add(VersModele(adresseDTO));
        }
        return adresses;
    }

    public static AdresseDTO versDTO(Adresse adresse) {
        if (adresse == null) {
            return null;
        }

        return new AdresseDTO(
            adresse.getId_adresse(),
            adresse.getRue(),
            adresse.getVille(),
            adresse.getCodePostal(),
            adresse.getPays(),
            adresse.getCoordonnees(),
            adresse.getAdresse_contact_id()
            
        );
    }

    public static List<AdresseDTO> versDTOs(List<Adresse> adresses) {
        if (adresses == null || adresses.isEmpty()) {
            return new ArrayList<>();
        }
        List<AdresseDTO> adressesDTOs = new ArrayList<>();
        for (Adresse adresse : adresses) {
            adressesDTOs.add(versDTO(adresse));
        }
        return adressesDTOs;
    }

}