package cal335.projet.mes_chums.mapper;

import cal335.projet.mes_chums.dto.ContactDTO;

import cal335.projet.mes_chums.modele.Contact;

import java.util.ArrayList;
import java.util.List;


public class ContactMapper {

    public static Contact versModele(ContactDTO contactDTO) {
        if (contactDTO == null) {
            return null;
        }
        return new Contact (contactDTO.getId_contact(), contactDTO.getNom(), contactDTO.getPrenom(), contactDTO.isFavoris(), AdresseMapper.versEntites(contactDTO.getAdressesDTO()));
    }

    public static List<Contact> versEntites(List<ContactDTO> contactsDTOs) {
        if (contactsDTOs == null || contactsDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        List<Contact> contacts = new ArrayList<>();
        for (ContactDTO contactDTO : contactsDTOs) {
            contacts.add(versModele(contactDTO));
        }
        return contacts;
    }

    public static ContactDTO versDTO(Contact contact) {
        if (contact == null) {
            return null;
        }
        return new ContactDTO(contact.getId_contact(),
                contact.getNom(), contact.getPrenom(), contact.isFavoris(), AdresseMapper.versDTOs(contact.getAdresses())
        );
    }

    public static List<ContactDTO> versDTOs(List<Contact> contacts) {
        if (contacts == null || contacts.isEmpty()) {
            return new ArrayList<>();
        }
        List<ContactDTO> contactsDTOs = new ArrayList<>();
        for (Contact contact : contacts) {
            contactsDTOs.add(versDTO(contact));
        }
        return contactsDTOs;
    }

}
