package week3;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public class Q8 {
	public static void main(String[] args) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, ShortBufferException {
		BigInteger y1 = new BigInteger("00000000000000000000000000000000", 16);
		BigInteger x1 = new BigInteger("00000000000000000000000000000000", 16);
		BigInteger f1 = new BigInteger("66e94bd4ef8a2c3b884cfa59ca342b2e", 16)
				.xor(y1);
		BigInteger y2 = new BigInteger("00000000000000000000000000000001", 16);
		BigInteger f11 = f1.xor(y2);

		System.out.println("y1:\t\t" + y1.toString(16));
		System.out.println("x1:\t\t" + x1.toString(16));
		System.out.println("f1(x1,y1):\t" + f1.toString(16));

		System.out.println("y2:\t\t" + y2.toString(16));
		System.out.println("f1(x1,y1) ^ y2:\t" + f11.toString(16));

		System.out.println("x2:\t\tDecrypt(y2, f1(x1,y1) ^ y2)");
		System.out.println("x2:\t\tba9535be65af00ecd90f8d6ddd49a807");

	}
}
