package kas.concurrente;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MAX_DELAY = 10000; // Valor máximo de retraso (ajústalo según tus necesidades)
    private static final int MIN_DELAY = 10;    // Valor mínimo de retraso (ajústalo según tus necesidades)
    @Override
    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);

        while (true) {
            while (state.get()) {
                // Espera activamente hasta que el bloqueo esté disponible.
            }
            if (!state.getAndSet(true)) {
                return;
                // Si logra cambiar el estado a "true" (es decir, si el bloqueo estaba libre),
                // significa que ha adquirido el bloqueo y puede salir del bucle.
            } else {
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restablece la interrupción
                }
                // Si no pudo adquirir el bloqueo, realiza un retroceso exponencial antes de volver a intentar.
            }
        }
    }


    @Override
    public void unlock() {
        state.set(false);
        // Este método libera el bloqueo estableciendo el estado a "false".
    }
    
}
