import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {

	class QNode {
		boolean locked = false;
		QNode next = null;
	}

	AtomicReference<QNode> queue;
	ThreadLocal<QNode> myNode;

	public MCSLock() {
		queue = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>() {
			protected QNode initialValue() {
				return new QNode();
			}
		};
	}

	@Override
	public Condition newCondition() {
		return null;
	}

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

	@Override
	public boolean tryLock(){
		lock();
		return true;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit){
		lock();
		return true;
	}

	@Override
	public void lockInterruptibly(){
		lock();
	}
}
