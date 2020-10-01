package moveoExe;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

class WaitableStack<MatrixCreate> {
	private final Stack<MatrixCreate> stack;
	private int numberOfActionsDone;
	private int numberObjectEntered;

	public WaitableStack() {
		stack = new Stack<>();
	}

	public synchronized List<MatrixCreate> doubleDequeue() {
		while (stack.size() < 2) {
			if (numberOfActionsDone == (numberObjectEntered - 1)) {
					return Arrays.asList();
			}
			else {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return Arrays.asList(stack.pop(), stack.pop());
	}

	public synchronized  MatrixCreate dequeue(){
		if(!stack.empty()){
			return  stack.pop();
		}
		 return null;
	}


	public synchronized void enqueueAtCreate(MatrixCreate element) {
			stack.push(element);
			numberObjectEntered++;
	}

	public synchronized void enqueueAfterMultiply(MatrixCreate element) {
			stack.push(element);
			numberOfActionsDone++;
			notifyAll();

	}
}