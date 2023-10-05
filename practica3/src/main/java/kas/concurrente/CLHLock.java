package kas.concurrente;
import java.util.concurrent.atomic.AtomicReference;
public class CLHLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<QNode>(null);
        myNode = ThreadLocal.withInitial(() -> new QNode());
        myPred = ThreadLocal.withInitial(() -> null);
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
            // Espera activamente hasta que el nodo predecesor haya liberado el bloqueo.
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }
    private static class QNode {
        volatile boolean locked = false;
    }
}
