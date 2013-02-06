package week3;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/**
 * In this question and the next, you are asked to find collisions on two
 * compression functions:
 * <ol>
 * <li>
 * <code>f<sub>1</sub>(x,y)=AES(y,x) ⊕ y,</code> and</li>
 * <li>
 * <code>f<sub>2</sub>(x,y)=AES(x,x) ⊕ y,</li></code>
 * </ol>
 * where AES(x,y) is the AES-128 encryption of y under key x.
 * <p>
 * We provide an <code>AES</code> function for you to play with. The function
 * takes as input a key k and an x value and outputs <code>AES(k,x)</code> once
 * you press the "encrypt" button. It takes as input a key k and a y value and
 * outputs <code>AES<sup>−1</sup>(k,y)</code> once you press the "decrypt"
 * button. All three values <code>k,x,y</code> are assumed to be hex values
 * (i.e. using only characters 0-9 and a-f) and the function zero-pads them as
 * needed.
 * 
 * Your goal is to find four distinct pairs
 * <code>(x<sub>1</sub>,y<sub>1</sub>), (x<sub>2</sub>,y<sub>2</sub>), (x<sub>3</sub>,y<sub>3</sub>), (x<sub>4</sub>,y<sub>4</sub>)</code>
 * such that
 * <code>f<sub>1</sub>(x<sub>1</sub>,y<sub>1</sub>)=f<sub>1</sub>(x<sub>2</sub>,y<sub>2</sub>)</code>
 * and
 * <code>f<sub>2</sub>(x<sub>3</sub>,y<sub>3</sub>)=f<sub>2</sub>(x<sub>4</sub>,y<sub>4</sub>)</code>
 * . In other words, the first two pairs are a collision for f<sub>1</sub> and
 * the last two pairs are a collision for f<sub>2</sub>. Once you find all four
 * pairs, please enter them below and check your answer using the "check"
 * button.
 * <p>
 * <b>Collision for first function is:</b>
 * <p>
 * <code>
 * x<sub>2</sub> := AES<sup>-1</sup>(y<sub>2</sub>,f<sub>1</sub>(x<sub>1</sub>,y<sub>1</sub>) ⊕ y<sub>2</sub>)
 * </code> for any <code>x<sub>1</sub>, y<sub>1</sub>, y<sub>2</sub></code>
 * 
 * @author rustam
 * 
 */
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
