package workshop2;
import java.util.Scanner;

public class ComputeSquareRoot {
	public static void main (String []args) {
		int n;
		float guess, prevguess, r;
		System.out.println("Please enter an integer: ");
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		guess = ((float) n ) / 2;
		
		prevguess = guess;
		r = n / guess;
		guess = (guess + r) / 2;
		
		while(guess != prevguess) {
			r = n / guess;
			prevguess = guess;
			guess = (guess + r) / 2;
		}
		System.out.printf("The inverse square root is %f", guess);
	}
}
