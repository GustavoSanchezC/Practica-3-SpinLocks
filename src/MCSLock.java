import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Clase MCS Lock que mantiene una LinkedList para hilos esperando a entrar a la sección crítica.
 */
public class MCSLock implements Lock {

	/**
	 * Clase para representar a un nodo de la lista
	 */
	class QNode {
		boolean locked = false;
		QNode next = null;
	}

	/* Apuntador a la cola */
	AtomicReference<QNode> queue;
	/* Nodo único por cada hilo */
	ThreadLocal<QNode> myNode;

	/**
	 * Constructor de la clase, inicializa el candado.
	 */
	public MCSLock() {
		queue = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>() {
			protected QNode initialValue() {
				return new QNode();
			}
		};
	}

	/**
	 * Regresa una nueva condición acotada a la instancia del candado.
	 * @return una nueva condicióon para esta instancia del candado.
	 */
	@Override
	public Condition newCondition() {
		return null;
	}

	/**
	 * Consigue el candado.
	 */
	@Override
	public void lock() {
		QNode qnode = myNode.get();
		QNode pred = queue.getAndSet(qnode);
		if (pred != null) {
			qnode.locked = true;
			pred.next = qnode;

			while(qnode.locked) { 
				// Wait until predecessor gives up the lock 
			}
		}
	}

	/**
	 * Libera el candado.
	 */
	@Override
	public void unlock() {
		QNode qnode = myNode.get();
		if (qnode.next == null) {
			if (queue.compareAndSet(qnode, null))
				return;
			while(qnode.next == null){
				// wait until predecessor fills in its next field
			}
		}
		qnode.next.locked = false;
		qnode.next = null;
	}

	/**
	 * Adquiere el candado.
	 * @return true
	 */
	@Override
	public boolean tryLock(){
		lock();
		return true;
	}

	/**
	 * Adquiere el candado.
	 * @return true
	 */
	@Override
	public boolean tryLock(long time, TimeUnit unit){
		lock();
		return true;
	}

	/**
	 * Adquiere el candado.
	 */
	@Override
	public void lockInterruptibly(){
		lock();
	}
}
