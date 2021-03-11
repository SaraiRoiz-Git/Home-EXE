
package moveoExe;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MatricesMultiplicationManager implements Runnable{
	private int numbersOfThreads;
	private int quantityOfSquareMatrices;
	private int dimensionOfSquareMatrices;
	private final Scanner scan;
	boolean continueRunning;

	WaitableStack<MatrixCreate> waitablestack;
	private HashMap threadMap ;

	
	public MatricesMultiplicationManager(){
		scan =  new Scanner(System.in);
		continueRunning = true;
	}

	public void multiplyMatricesAndPrintTheOutput(){
		while (continueRunning){
			resetAllInputs();
			getInputFromUser(scan);
			if (continueRunning){
				createQueueOfMatrices();
				createThreadAndRun();
				waitForThreadsToFinish();
				printFinalMatrix(waitablestack.dequeue().getMatrix());

				
			}
		}
		scan.close();
	}

	private void createQueueOfMatrices(){
		waitablestack = new WaitableStack<>();
 		for(int i = 0; i < quantityOfSquareMatrices; i++) {
 			waitablestack.enqueueAtCreate(new MatrixCreate(dimensionOfSquareMatrices));
		}
	}

	private void resetAllInputs() {
		numbersOfThreads = 0;
		quantityOfSquareMatrices = 0;
		dimensionOfSquareMatrices = 0;
	}

	private void getInputFromUser(Scanner scan) {
		while (numbersOfThreads > 20 || numbersOfThreads < 2 ) {
			if (numbersOfThreads == 1){
				continueRunning = false;
				return;
			}
		numbersOfThreads = InputFromUser.getIntFromUser("How many threads do you wants to create between 2 to 20 ? \n Press 1 for exit", scan);
		}
		
		while(quantityOfSquareMatrices < 2) {
		quantityOfSquareMatrices = InputFromUser.getIntFromUser("please enter a number quantity of square matrices(at least 2) ?", scan);
		}
		
		while(dimensionOfSquareMatrices < 1) {
		dimensionOfSquareMatrices = InputFromUser.getIntFromUser("please enter a number of dimension of the square matrices ?", scan);
		}
	}

	private void  printFinalMatrix(int[][] matrix){
			System.out.println("final matrix:");
			System.out.println("{");
			for (int i = 0; i< dimensionOfSquareMatrices; i++ ){
				System.out.print("{");
				for (int j = 0; j < dimensionOfSquareMatrices; j++ ){
					System.out.print(matrix[i][j]);
					if(j+1 < dimensionOfSquareMatrices){
						System.out.print(", ");
					}
				}
				System.out.println("}");
			}
			System.out.println("}");
		}

	@Override
	public void run() {


		boolean isRunning = true;
		while(isRunning) {
			List<MatrixCreate> matrices =  waitablestack.doubleDequeue();
			if (matrices.size()<2) {
				isRunning = false;
				threadMap.remove(Thread.currentThread().getId());
			} else {
				int[][] newMatrix = new MathActionsOnMatrixs().multipleTwoMtrices(matrices.get(0).getMatrix(), matrices.get(1).getMatrix());
				MatrixCreate newMatrixClass = new MatrixCreate(newMatrix);
				waitablestack.enqueueAfterMultiply(newMatrixClass);
			}
		}
	}

	private void createThreadAndRun() {
		threadMap  =  new HashMap<Long,Thread>();
		for (int i = 1; i < (numbersOfThreads); i++){
			Thread thread = new Thread(this);
			threadMap.put(thread.getId(), thread);
			thread.start();
		}
	}

	private void waitForThreadsToFinish(){
		while (!threadMap.isEmpty()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
