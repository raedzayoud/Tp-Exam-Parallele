package exam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * GestionnaireColis is a class that manages a list of packages (colis) 
 * and provides thread-safe methods for operations on the list.
 */

public class GestionnaireColis {
    private final List<Colis> colisList = new ArrayList<>();
    private final Semaphore semaphore = new Semaphore(1); 
    
    
    public synchronized boolean enregistrerColis(Colis colis) {
        try {
            semaphore.acquire(); // Ensure exclusive access
            for (Colis c : colisList) {
                if (colis.getId() == c.getId()) {
                    return false; // ID already exists
                }
            }
            colisList.add(colis);
            System.out.println("Colis enregistré: " + colis.getId());
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            return false;
        } finally {
            semaphore.release(); 
        }
    }

    public synchronized void livrerColis(int id) {
        try {
            semaphore.acquire(); // Ensure exclusive access
            for (Colis colis : colisList) {
                if (colis.getId() == id && !colis.getStatut().equals("Livré")) {
                    colis.setStatut("Livré");
                    System.out.println("Colis livré: " + id);
                    break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
        } finally {
            semaphore.release(); // Release access
        }
    }

    public synchronized List<Colis> obtenirTousLesColis() {
        try {
        	//Assure que la liste n'est pas modifiée pendant sa lecture .
            semaphore.acquire(); // Ensure exclusive access
            return new ArrayList<>(colisList); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            return new ArrayList<>();
        } finally {
            semaphore.release(); // Release access
        }
    }
}
