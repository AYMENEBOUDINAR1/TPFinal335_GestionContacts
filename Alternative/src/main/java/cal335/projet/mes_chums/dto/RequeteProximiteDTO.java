package cal335.projet.mes_chums.dto;

public class RequeteProximiteDTO {
    private String adresseDeReference;
    private double rayon;

    public RequeteProximiteDTO(String adresseDeReference, double rayon) {
        this.adresseDeReference = adresseDeReference;
        this.rayon = rayon;
    }

    public String getAdresseDeReference() {
        return adresseDeReference;
    }

    public void setAdresseDeReference(String adresseDeReference) {
        this.adresseDeReference = adresseDeReference;
    }

    public double getRayon() {
        return rayon;
    }

    public void setRayon(double rayon) {
        this.rayon = rayon;
    }
}
