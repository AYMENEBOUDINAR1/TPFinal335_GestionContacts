package cal335.projet.mes_chums;

import cal335.projet.mes_chums.controleur.ContactControleur;
import cal335.projet.mes_chums.service.ContactService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;


public class ApplicationMesChums {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer serveur = HttpServer.create(new InetSocketAddress(8080), 0);

        ContactControleur controleur = new ContactControleur(new ContactService());
        serveur.createContext("/contacts", controleur);

        serveur.setExecutor(null);
        serveur.start();

        System.out.println("Serveur mes_chums démarré sur le port 8080");


    }
}

