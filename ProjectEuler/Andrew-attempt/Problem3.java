/*
 * What is the largest prime factor of the number 600851475143 ?
 */
 public class Problem3 {

 	public static void main(String[] args) {
 		Long num = 600851475143L;

 		int largest = 0;

 		for(largest = 2; largest <= num; largest++) {
 			if(num % largest == 0) 
 				num /= largest--;
 		}

 		System.out.println(largest);
 	}
 }