package cal335.projet.mes_chums.geocodage;

import cal335.projet.mes_chums.modele.Coordonnees;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApiGeocodage {
    private static final String BASE_URL = "https://nominatim.openstreetmap.org/search";

    public Coordonnees obtenirCoordonneesPourAdresse(String rue, String ville, String codePostal, String pays) {
        OkHttpClient client = new OkHttpClient();

        String adresse = String.format("%s, %s, %s, %s", rue, ville, codePostal, pays).replace(" ", "+");
        String url = String.format("%s?q=%s&format=json", BASE_URL, adresse);

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "mes_chums_app")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("Erreur API Nominatim : " + response.code());
                return null;
            }

            String jsonData = response.body().string();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonData);

            if (!root.isArray() || root.size() == 0) {
                System.err.println("Aucun résultat trouvé pour l'adresse.");
                return null;
            }

            JsonNode location = root.get(0);
            double latitude = location.get("lat").asDouble();
            double longitude = location.get("lon").asDouble();

            return new Coordonnees(latitude, longitude);
         catch (IOException e) {
            System.err.println("Erreur lors de l'appel API : " + e.getMessage());
            return null;
        }
    }
}
