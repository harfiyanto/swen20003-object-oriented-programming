package workshop2;

import java.util.Scanner;

public class PredictHeight {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		char gender;
		float fatherH = 0, motherH = 0, result = 0;
		
		boolean cont = true;
		while (cont == true) {
			System.out.println("Please enter the child's gender (m/f):");
			gender = sc.next(".").charAt(0);
			
			if (Character.toLowerCase(gender) == 'm') {
				System.out.println("Male");
				System.out.println("Please the father's height (in inches)");	
				fatherH = sc.nextFloat();
				System.out.println("Please the mother's height (in inches)");	
				motherH = sc.nextFloat();
				result = ((motherH * 13 / 12) + fatherH) / 2;
				System.out.println("Estimated adult height of the child: " + result);
				
			} else if (Character.toLowerCase(gender) == 'f') {
				System.out.println("Female");
				System.out.println("Please the father's height (in inches)");	
				fatherH = sc.nextFloat();
				System.out.println("Please the mother's height (in inches)");	
				motherH = sc.nextFloat();
				result = ((fatherH * 12 / 13) + motherH) / 2;
				System.out.println("Estimated adult height of the child: " + result);
			}
			
			
			System.out.println("Do you still want to continue ? (press y to continue)");
			
			if (sc.next(".").charAt(0) == 'y') {
				cont = true;
			} else {
				System.out.println("Program terminating..");
				cont = false;
			}
		}
	}
}
