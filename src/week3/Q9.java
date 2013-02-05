package week3;

import java.math.BigInteger;

public class Q9 {
	public static void main(String[] args) {
		BigInteger y3 = new BigInteger("00000000000000000000000000000002", 16);
		BigInteger x3 = new BigInteger("00000000000000000000000000000002", 16);
		BigInteger f2 = new BigInteger("6aba8d054eea3b883da1428189be19b7", 16)
				.xor(y3);
		BigInteger x4 = new BigInteger("00000000000000000000000000000003", 16);
		BigInteger e4 = new BigInteger("afd9d94764c222dccbf6cc2524d0dde1", 16);
		BigInteger y4 = f2.xor(e4);

		System.out.println("y3:\t\t" + y3.toString(16));
		System.out.println("x3:\t\t" + x3.toString(16));
		System.out.println("f2:\t\t" + f2.toString(16));
		System.out.println("x4:\t\t" + x4);
		System.out.println("y4:\t\tf2(x3,y3) ^ Encrypt(x4)");
		System.out.println("y4:\t\t" + y4.toString(16));

	}
}
