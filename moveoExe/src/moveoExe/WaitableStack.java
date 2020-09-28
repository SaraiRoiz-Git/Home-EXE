package moveoExe;

import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitableStack<E> {
	private final Stack<E> stack;
	private int numbrOfActionsDoun;
	private int numbrOfobjectEnterd;
	private final Lock lock;


	public WaitableStack() {
		stack = new Stack<E>();
		lock = new ReentrantLock();
	}
	
	private E dequeue() {
		lock.lock();
		E returnData = stack.pop();
		lock.unlock();
		
		return returnData;
	}

	public E[] doubleDequeue() {
		lock.lock();
		E[] returnData = (E[]) new Object[]{stack.pop(),stack.pop()};
		if(returnData[0] == null){
			if(numbrOfActionsDoun < numbrOfobjectEnterd - 1){
				try {
					wait();
					returnData[0] = stack.pop();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("problem with finish calculate");
				}
			}
		}
		if(returnData[0] != null && returnData[1] == null) {
			if (numbrOfActionsDoun < numbrOfobjectEnterd - 1) {
				try {
					wait();
					if (numbrOfActionsDoun < numbrOfobjectEnterd - 1) {
						returnData[1] = stack.pop();
						System.out.println("problem with finish calculate");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("problem with finish calculate");
				}
			}
		}
		lock.unlock();
		return returnData;
	}
	
	public boolean isEmpty() {
		lock.lock();
		boolean is_empty= stack.isEmpty();
		lock.unlock();
		return is_empty;
	}

	public void enqueueAtCreate(E element) {
		lock.lock();
		stack.push(element);
		numbrOfobjectEnterd ++;
		lock.unlock();
	}

	public void enqueueAfterMultiply(E element) {
		lock.lock();
		stack.push(element);
		numbrOfActionsDoun ++;
		lock.unlock();
		notify();
	}


}