package kas.concurrente;
import java.util.concurrent.atomic.AtomicReference;
/**
 * La clase CLHLock implementa un mecanismo de bloqueo basado en la idea de "Cola, Luego Hazlo" (CLH)
 * que permite la sincronización de múltiples hilos.
 */
public class CLHLock implements Lock {

    private final AtomicReference<QNode> tail;// Referencia atómica a la cola de nodos

    private final ThreadLocal<QNode> myPred;// Nodo anterior del hilo actual
    private final ThreadLocal<QNode> myNode;// Nodo del hilo actual
    /**
     * Crea una nueva instancia de CLHLock con un nodo de cola inicial.
     */
    public CLHLock() {
        tail = new AtomicReference<>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }
    /**
     * Intenta adquirir el bloqueo utilizando la estrategia de Cola, Luego Hazlo (CLH).
     */
    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);// Establece este nodo como el nuevo nodo de cola
        myPred.set(pred);// Almacena el nodo anterior como el predecesor del hilo actual

        // Espera hasta que el predecesor haya desbloqueado
        while (pred.locked) {
            Thread.yield(); // Permitir que otros hilos se ejecuten mientras se espera
        }
    }
    /**
     * Libera el bloqueo, permitiendo que otros hilos lo adquieran.
     */
    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }
    /**
     * Clase que representa un nodo en la cola de bloqueo.
     */
    private static class QNode {
        volatile boolean locked = false;
    }
}
