package cal335.projet.mes_chums.dao;

import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Contact;
import cal335.projet.mes_chums.modele.Coordonnees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO implements DAOGenerique<Contact> {

    @Override
    public void ajouter(Contact contact) {
        String requetesql = "INSERT INTO Contact(nom, prenom, isFavoris) VALUES (?, ?, ?)";

        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requetesql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setBoolean(3, contact.isFavoris());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    contact.setId_contact(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur ajout contact: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Integer id) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement("DELETE FROM Contact WHERE id_contact = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur suppression contact: " + e.getMessage());
        }
    }

    @Override
    public void mettreAJour(Contact contact) {
        String requetesql = "UPDATE Contact SET nom=?, prenom=?, isFavoris=? WHERE id_contact=?";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requetesql)) {
            statement.setString(1, contact.getNom());
            statement.setString(2, contact.getPrenom());
            statement.setBoolean(3, contact.isFavoris());
            statement.setInt(4, contact.getId_contact());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour contact: " + e.getMessage());
        }
    }

    @Override
    public Contact trouverParId(Integer id) {
        String requete = "SELECT id_contact, nom, prenom, isFavoris FROM Contact WHERE id_contact = ?";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(requete)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Contact contact = new Contact();
                    contact.setId_contact(resultSet.getInt("id_contact"));
                    contact.setNom(resultSet.getString("nom"));
                    contact.setPrenom(resultSet.getString("prenom"));
                    contact.setFavoris(resultSet.getBoolean("isFavoris"));
                    return contact;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche contact: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Contact> trouverTous() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT id_contact, nom, prenom, isFavoris FROM Contact";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId_contact(resultSet.getInt("id_contact"));
                contact.setNom(resultSet.getString("nom"));
                contact.setPrenom(resultSet.getString("prenom"));
                contact.setFavoris(resultSet.getBoolean("isFavoris"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            System.err.println("Erreur liste contacts: " + e.getMessage());
        }
        return contacts;
    }

    public void ajouterAdressesAuContact(int contactId, List<Adresse> adresses) {
        String sql = "INSERT INTO Adresse (rue, ville, codePostal, pays, contact_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {

            for (Adresse adresse : adresses) {
                statement.setString(1, adresse.getRue());
                statement.setString(2, adresse.getVille());
                statement.setString(3, adresse.getCodePostal());
                statement.setString(4, adresse.getPays());
                statement.setInt(5, contactId);
                statement.addBatch(); 
            }

            statement.executeBatch();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'adresses : " + e.getMessage());
        }
    }

    public List<Adresse> trouverParContactId(int contactId) {
    List<Adresse> adresses = new ArrayList<>();
    String requetesql = "SELECT * FROM Adresse WHERE contact_id = ?";
    try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
         PreparedStatement statement = connexion.prepareStatement(requetesql)) {
        statement.setInt(1, contactId);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Adresse adresse = new Adresse();
                adresse.setId_adresse(resultSet.getInt("id_adresse"));
                adresse.setRue(resultSet.getString("rue"));
                adresse.setVille(resultSet.getString("ville"));
                adresse.setCodePostal(resultSet.getString("codePostal"));
                adresse.setPays(resultSet.getString("pays"));
                adresse.setCoordonnees(new Coordonnees(resultSet.getDouble("latitude"), resultSet.getDouble("longitude")));
                adresse.setAdresse_contact_id(resultSet.getInt("contact_id"));
                adresses.add(adresse);
            }
        }
    } catch (SQLException e) {
        System.err.println("Erreur lors de la recherche des adresses par contact_id: " + e.getMessage());
    }
    return adresses;
}

}
