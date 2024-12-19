package cal335.projet.mes_chums.dao;

import cal335.projet.mes_chums.modele.Adresse;
import cal335.projet.mes_chums.modele.Coordonnees;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresseDAO implements DAOGenerique<Adresse> {

    @Override
    public void ajouter(Adresse adresse) {
        String requetesql = "INSERT INTO Adresse(rue, ville, codePostal, pays, latitude, longitude /*, contact_id*/ ) VALUES (?,?,?,?,?,?,?)";
        try (Connection c = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = c.prepareStatement(requetesql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, adresse.getRue());
            statement.setString(2, adresse.getVille());
            statement.setString(3, adresse.getCodePostal());
            statement.setString(4, adresse.getPays());
            statement.setDouble(5, adresse.getCoordonnees().getLatitude());
            statement.setDouble(6, adresse.getCoordonnees().getLongitude());
            statement.setInt(7, adresse.getAdresse_contact_id());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    adresse.setId_adresse(resultSet.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur ajout adresse: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Integer id) {
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement("DELETE FROM Adresse WHERE id_adresse = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur suppression adresse: " + e.getMessage());
        }
    }

    @Override
    public void mettreAJour(Adresse adresse) {
        String sql = "UPDATE Adresse SET rue=?, ville=?, codePostal=?, pays=?, latitude=?, longitude=? WHERE id_adresse=?";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, adresse.getRue());
            statement.setString(2, adresse.getVille());
            statement.setString(3, adresse.getCodePostal());
            statement.setString(4, adresse.getPays());
            statement.setDouble(5, adresse.getCoordonnees().getLatitude());
            statement.setDouble(6, adresse.getCoordonnees().getLongitude());
            statement.setInt(7, adresse.getId_adresse());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur mise à jour adresse: " + e.getMessage());
        }
    }

    @Override
    public Adresse trouverParId(Integer id) {
        String sql = "SELECT * FROM Adresse WHERE id_adresse=?";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Adresse adresse = new Adresse();
                    adresse.setId_adresse(resultSet.getInt("id_adresse"));
                    adresse.setRue(resultSet.getString("rue"));
                    adresse.setVille(resultSet.getString("ville"));
                    adresse.setCodePostal(resultSet.getString("codePostal"));
                    adresse.setPays(resultSet.getString("pays"));
                    adresse.setCoordonnees(new Coordonnees(resultSet.getDouble("latitude"), resultSet.getDouble("longitude")));
                    adresse.setAdresse_contact_id(resultSet.getInt("contact_id"));
                    return adresse;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche adresse: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adresse> trouverTous() {
        List<Adresse> adresses = new ArrayList<>();
        String sql = "SELECT * FROM Adresse";
        try (Connection connexion = ConnexionBaseDeDonnees.obtenirConnexion();
             PreparedStatement statement = connexion.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
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
        } catch (SQLException e) {
            System.err.println("Erreur liste adresses: " + e.getMessage());
        }
        return adresses;
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
