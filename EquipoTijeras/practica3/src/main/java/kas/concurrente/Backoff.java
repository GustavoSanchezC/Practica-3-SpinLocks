package kas.concurrente;

import java.util.Random;
/**
 * La clase Backoff se utiliza para implementar una estrategia de espera exponencial
 * (backoff) en situaciones donde se necesita una pausa aleatoria entre intentos
 * de reintentar una operación.
 */
public class Backoff {
    final int minDelay, maxDelay;// Retardo mínimo antes de la pausa,Retardo máximo antes de la pausa
    int limit;// Límite actual de retardo
    final Random random;
    /**
     * Crea una nueva instancia de Backoff con los valores mínimos y máximos de retardo dados.
     *
     * @param min El retardo mínimo en milisegundos antes de la pausa.
     * @param max El retardo máximo en milisegundos antes de la pausa.
     */
    public Backoff(int min, int max) {
        minDelay = min;
        maxDelay = max;
        limit = minDelay;
        random = new Random();
    }
    /**
     * Realiza una pausa aleatoria de duración variable antes de continuar.
     *
     * @throws InterruptedException Si se interrumpe la espera durante el backoff.
     */
    public void backoff() throws InterruptedException {
        int delay = random.nextInt(limit);// Calcula un retardo aleatorio
        limit = Math.min(maxDelay, 2 * limit);// Aumenta el límite de retardo (exponencial)
        Thread.sleep(delay);// Realiza la pausa
    }
}
