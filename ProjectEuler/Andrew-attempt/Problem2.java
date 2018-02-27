/*
 * By considering the terms in the Fibonacci sequence whose values 
 * do not exceed four million, find the sum of the even-valued terms
 */
public class Problem2 {

	static int sum = 0;

	public static void main(String[] args) {

		int current = 1;
		int next = 2;

		while( next < 4000000 ) {
			if(next % 2 == 0)
				sum += next;

			int temp = next;

			next += current;
			current = temp;

		}

		System.out.println(sum);
	}
}
