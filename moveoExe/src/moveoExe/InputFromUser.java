package moveoExe;

import java.util.Scanner;

public class InputFromUser {
	
	public static int getIntFromUser(String KindofinpurRequset, Scanner scan) {
		System.out.println(KindofinpurRequset);
		return scan.nextInt();	
	}

}
