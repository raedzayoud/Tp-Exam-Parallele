package exam;

/**
 * This class represents a package (colis) to be managed in the delivery system.
 * Each package has a unique ID, a destination, and a status.
 */
public class Colis {
    private int id;
    private String statut; // "En attente", "En transit", "Livré"
    private String destination; // Destination of the colis

    public Colis(int id, String destination) {
        this.id = id;
        this.statut = "En attente";
        this.destination = destination;
    }
    //A chaque instant, un seul thread peut exécuter et modifier des variables dans sa zone critique les autres threads attendront leur tour.
    public synchronized void setStatut(String statut) {
        this.statut = statut;
    }

    public synchronized String getStatut() {
        return statut;
    }

    public int getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }
}