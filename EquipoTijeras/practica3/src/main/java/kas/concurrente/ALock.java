package kas.concurrente;

import java.util.concurrent.atomic.AtomicInteger;
/**
 * La clase ALock implementa un mecanismo de bloqueo basado en números de ranura (slot numbers).
 * Este mecanismo permite la sincronización de hilos utilizando un conjunto de ranuras (slots)
 * y un índice de ranura para cada hilo.
 */
public class ALock implements Lock {
    ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };
    AtomicInteger tail;
    volatile boolean[] flag;
    int size;

    /**
     * Constructor de la clase ALock.
     *
     * @param capacity Capacidad de la estructura de bloqueo (número de ranuras).
     */
    public ALock(int capacity) {
        size = capacity;
        tail = new AtomicInteger(0);
        flag = new boolean[capacity];
        flag[0] = true;
    }
    /**
     * Intenta adquirir el bloqueo utilizando el número de ranura correspondiente al hilo actual.
     */
    @Override
    public void lock() {
        int slotIndex = tail.getAndIncrement() % size;
        mySlotIndex.set(slotIndex);
        while (!flag[slotIndex]) {}
    }
    /**
     * Libera el bloqueo y permite que otros hilos adquieran el siguiente número de ranura.
     */
    @Override
    public void unlock() {
        int slotIndex = mySlotIndex.get();
        flag[slotIndex] = false;
        flag[(slotIndex + 1) % size] = true;
    }
}
