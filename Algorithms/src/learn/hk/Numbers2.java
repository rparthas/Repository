package learn.hk;

import java.math.BigInteger;

public class Numbers2 {

	public static void main(String[] args) {
		long a = 9;
		long b = 24;
		long n = 5;

		long gcd = gcd(a, b);
		a = a / gcd;
		b = b / gcd;

		long multiple = a * b * (n / (a + b - 1));
		n = n - (n / (a + b - 1)) * (a + b - 1);
		long m = Math.max(((n * a) / (a + b - 1)) * b, ((n * b) / (a + b - 1)) * a);

		while ((m / a) + (m / b) < n) {
			m = Math.min(((m + a) / a) * a, ((m + b) / b) * b);
		}
		multiple = multiple + m;
		multiple = multiple * gcd;
		System.out.println(multiple);

	}

	public static long gcd(long a, long b) {
		BigInteger b1 = BigInteger.valueOf(a);
		BigInteger b2 = BigInteger.valueOf(b);
		BigInteger gcd = b1.gcd(b2);
		return gcd.intValue();
	}

}
