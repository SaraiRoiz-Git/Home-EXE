package moveoExe;

public class MathActionsOnMatrixs {
 	public int[][] multipleTwoMtrices(int[][] matrix1, int[][] matrix2){
 		int matrixSize = matrix1.length;
 		int[][] newMatrix = new int[matrixSize][matrixSize];
 		for(int i=0;i<matrixSize;i++){    
 			for(int j=0;j<matrixSize;j++){    
 				newMatrix[i][j]=0;      
	 			for(int k=0;k<matrixSize;k++){      
 				newMatrix[i][j]+=matrix1[i][k]*matrix2[k][j];      
	 			}
 			}
 		}   
 		return newMatrix;
 	} 
}
