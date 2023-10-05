package kas.concurrente;
import java.util.concurrent.atomic.AtomicBoolean;
public class TASLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    @Override
    public void lock() {
        while (state.getAndSet(true)) {
            // Este método se ejecuta en un bucle hasta que obtenga el bloqueo.
            // 1. state.getAndSet(true): Intenta cambiar el estado a "true" y obtiene el valor anterior.
            //    Si el valor anterior era "false", significa que se adquirió el bloqueo.
            //    Si el valor anterior era "true", significa que el bloqueo está siendo utilizado por otro hilo,
            //    por lo que continuamos en el bucle hasta que esté disponible.
        }
    }

    @Override
    public void unlock() {
        state.set(false);
        // Este método libera el bloqueo estableciendo el estado a "false".
    }
    
}
