package cal335.projet.mes_chums;

import cal335.projet.mes_chums.modele.Coordonnees;

public class CalculateurDistance {
    public static double calculerDistance(Coordonnees c1, Coordonnees c2) {
        final int rayonTerre = 6371;
        double latDistance = Math.toRadians(c2.getLatitude() - c1.getLatitude());
        double lonDistance = Math.toRadians(c2.getLongitude() - c1.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(c1.getLatitude())) * Math.cos(Math.toRadians(c2.getLatitude())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return rayonTerre * c;
    }
}
