/*
 * What is the largest prime factor of the number 600851475143 ?
 */
 public class Problem3 {

 	public static int gcd(int x, int y) {
 		if( y == 0 )
 			return x;

 		return gcd(y, x % y);
 	}
 	public static void main(String[] args) {

 		// Lol of course, num > Integer.MAX_VALUE
 		int res = gcd(600851475143);
 		System.out.println(res);
 	}
 }