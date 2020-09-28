//created by: sarai roiz
package moveoExe;

import java.util.HashMap;
import java.util.Scanner;

public class MatricesMultiplicationManeger implements Runnable{
	private int numbersOfThreads;
	private int quantityOfsquareMatrices;
	private int dimensionOftheSquareMatrices;
	private final Scanner scan;

	WaitableStack<MatrixCreate> waitablestack;
	private HashMap threadMap;
	
	public MatricesMultiplicationManeger(Scanner scan){
		this.scan = scan;
	}

	public void multiplythematricesandprinttheoutput(){
		resetAllInputs();
		getInputFromUser(scan);
		createQueueOfMatrices();
		run();
//		threadsCreate();
//		waitForThreadsToFinish();
	}

	private void createQueueOfMatrices(){
		waitablestack = new WaitableStack<MatrixCreate>();
 		for(int i = 0 ; i < quantityOfsquareMatrices ; i++) {
 			waitablestack.enqueueAtCreate(new MatrixCreate(dimensionOftheSquareMatrices));
		}
	}

	private void resetAllInputs() {
		numbersOfThreads = 0;
		quantityOfsquareMatrices = 0;
		dimensionOftheSquareMatrices = 0;
	}

	private void getInputFromUser(Scanner scan) {
		while (numbersOfThreads > 20 || numbersOfThreads < 2 ) {
		numbersOfThreads = InputFromUser.getIntFromUser("How many threads do you wants to create between 2to20 ?", scan);
		}
		
		while(quantityOfsquareMatrices < 2) {
		quantityOfsquareMatrices = InputFromUser.getIntFromUser("please enter a number quantity of square matrices(at least 2) ?", scan);
		}
		
		while(dimensionOftheSquareMatrices < 1) {
		dimensionOftheSquareMatrices = InputFromUser.getIntFromUser("please enter a number of dimension of the square matrices ?", scan);
		}
	}

	private void  printFinalMatrix(int[][] matrix){
			System.out.println("final matrix:");
			System.out.println("{");
			for (int i = 0; i<quantityOfsquareMatrices; i++ ){
				System.out.print("{");
				for (int j = 0; j < quantityOfsquareMatrices; j++ ){
					System.out.print(matrix[i][j]);
					if(j+1 < quantityOfsquareMatrices){
						System.out.print(", ");
					}
				}
				System.out.println("}");
			}
			
			System.out.println("}");
		}

	@Override
	public void run() {
		boolean isRuning = true;

		while(isRuning){
			MatrixCreate[] matrices = waitablestack.doubleDequeue();
			if(matrices[1] == null){
				if(matrices[0] != null){
					printFinalMatrix(matrices[0].getMatrix());
				}
				isRuning = false;
				threadMap.remove(Thread.currentThread().getId());
			}
			else{
				int[][]  newMatrix = new MathActionsOnMatrixs().multipleTwoMtrices(matrices[0].getMatrix(),matrices[1].getMatrix());
				MatrixCreate newMatrixClass = new MatrixCreate(newMatrix);
				waitablestack.enqueueAfterMultiply(newMatrixClass);
			}
		}


	}

	private void threadsCreate() {
		threadMap  =  new HashMap<Long,Thread>();
		for (int i = 0; i < numbersOfThreads; i++){
			Thread thread = new Thread();
			threadMap.put(thread.getId(), thread);
			thread.start();
		}
	}

	private void waitForThreadsToFinish(){
		while (!threadMap.isEmpty()){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
