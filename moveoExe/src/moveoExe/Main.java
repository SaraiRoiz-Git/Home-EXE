package moveoExe;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Scanner scan = new Scanner(System.in);
		MatricesMultiplicationManeger matricesMultiplication =new MatricesMultiplicationManeger(scan);
		matricesMultiplication.multiplythematricesandprinttheoutput();
		scan.close();
	}

}
