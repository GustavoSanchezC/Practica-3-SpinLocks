package kas.concurrente;
import java.util.concurrent.atomic.AtomicBoolean;
public class TTASLock implements Lock{
    private AtomicBoolean state = new AtomicBoolean(false);
    @Override
    public void lock() {
        while (true) {
            while (state.get()) {
                // Espera activamente hasta que el bloqueo esté disponible.
            }
            // Si logra cambiar el estado a "true" (es decir, si el bloqueo estaba libre),
            // significa que ha adquirido el bloqueo y puede salir del bucle.
            if (!state.getAndSet(true)) {
                return;

            }
        }
    }



    // Este método libera el bloqueo estableciendo el estado a "false".
    @Override
    public void unlock() {
        state.set(false);

    }
    
}
