package exam;

public class ThreadLivraison extends Thread {
    private final GestionnaireColis gestionnaire;
    private final int colisId;

    public ThreadLivraison(GestionnaireColis gestionnaire, int colisId) {
        this.gestionnaire = gestionnaire;
        this.colisId = colisId;
    }

    @Override
    public void run() {
        try {
            // Change status to "En transit" after 10 second
            for (Colis colis : gestionnaire.obtenirTousLesColis()) {
                if (colis.getId() == colisId) {
                    Thread.sleep(10000); // 10 second delay
                    colis.setStatut("En transit");
                    
                    // Change status to "Livré" after another 4 second
                    Thread.sleep(4000); // Another 4 second delay
                    gestionnaire.livrerColis(colisId);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}