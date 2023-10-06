package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;
/**
 * La clase MCSLock implementa un mecanismo de bloqueo basado en la idea de "Queuing Lock" (MCS)
 * que permite la sincronización de múltiples hilos.
 */
public class MCSLock implements Lock {
    AtomicReference<QNode> tail; // Referencia atómica a la cola de nodos
    ThreadLocal<QNode> myNode; // Nodo del hilo actual

    /**
     * Crea una nueva instancia de MCSLock con una cola de nodos inicialmente vacía.
     */
    public MCSLock () {
        tail = new AtomicReference<QNode> (null);
        myNode = new ThreadLocal<QNode> () {
            @Override
            protected QNode initialValue () {
                return new QNode ();
            }
        };
    }

    /**
     * Intenta adquirir el bloqueo utilizando la estrategia de "Queuing Lock" (MCS).
     */
    @Override
    public void lock () {
        QNode qnode = myNode.get ();
        QNode pred = tail.getAndSet (qnode);// Establece este nodo como el nuevo nodo de cola
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            // Espera hasta que el nodo anterior haya desbloqueado
            while (qnode.locked) {
                Thread.yield();// Permitir que otros hilos se ejecuten mientras se espera

            }
        }
    }


    /**
     * Libera el bloqueo, permitiendo que otros hilos lo adquieran.
     */
    @Override
    public void unlock () {
        QNode qnode = myNode.get ();
        if (qnode.next == null) {
            if (tail.compareAndSet (qnode, null)) {
                return;
            }
            while (qnode.next == null){ }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    /**
     * Clase que representa un nodo en la cola de bloqueo.
     */
    class QNode {
        volatile boolean locked = false;// Indica si el bloqueo está adquirido por un hilo
        volatile QNode next = null; // Referencia al siguiente nodo en la cola
    }
}
