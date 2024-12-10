package exam;

public class ThreadEnregistrement extends Thread {
    private final GestionnaireColis gestionnaire;
    private final int colisId;
    private final String destination;
    private boolean test = false;

    public ThreadEnregistrement(GestionnaireColis gestionnaire, int colisId, String destination) {
        this.gestionnaire = gestionnaire;
        this.colisId = colisId;
        this.destination = destination;
    }

    @Override
    public void run() {
        Colis colis = new Colis(colisId, destination);
        test = gestionnaire.enregistrerColis(colis);
    }

    public boolean isTest() {
        return test;
    }
}