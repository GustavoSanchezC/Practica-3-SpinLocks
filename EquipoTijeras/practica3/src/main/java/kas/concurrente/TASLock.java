package kas.concurrente;
import java.util.concurrent.atomic.AtomicBoolean;
public class TASLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);


    /**
     * Intenta adquirir el bloqueo. Si el bloqueo está ocupado, el hilo entra en un bucle de espera
     * activa hasta que pueda adquirir el bloqueo.
     */
    @Override
    public void lock() {
        // Espera activamente hasta que se adquiera el bloqueo.
        while (state.getAndSet(true)) {

        }
    }
    /**
     * Libera el bloqueo, permitiendo que otros hilos lo adquieran.
     */
    @Override
    public void unlock() {
        state.set(false);

    }
    
}
