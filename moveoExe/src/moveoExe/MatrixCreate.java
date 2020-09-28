package moveoExe;

public class MatrixCreate {
	int[][] matrix;
	
	public MatrixCreate(int matrixSize) {
		matrix = new int[matrixSize][matrixSize];
		createMatricsWithRandomNumbers(matrixSize);
	}

	public MatrixCreate(){
	}
	public MatrixCreate(int[][] matrix) {
		this.matrix = matrix;
	}

	private int[][] createMatricsWithRandomNumbers(int matrixSize){
		
		
		for (int i = 0 , j = 0; i < matrixSize; i++, j = 0) {
			for (;j < matrixSize; j++) { 
				matrix[i][j]= (int) (Math.random()*10);
			}
		}
		return matrix;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
}
