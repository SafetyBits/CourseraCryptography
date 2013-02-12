package week4;

import week2.Utils;

/**
 * An attacker intercepts the following ciphertext (hex encoded):
 * <p>
 * <code>20814804c1767293b99f1d9cab3bc3e7ac1e37bfb15599e5f40eef805488281d</code>
 * <p>
 * He knows that the plaintext is the ASCII encoding of the message
 * "Pay Bob 100$" (excluding the quotes). He also knows that the cipher used is
 * CBC encryption with a random IV using AES as the underlying block cipher.
 * Show that the attacker can change the ciphertext so that it will decrypt to
 * "Pay Bob 500$". What is the resulting ciphertext (hex encoded)? This shows
 * that CBC provides no integrity.
 * <p>
 * <b>Solution</b>
 * <p>
 * Simply XOR ciphertext with <code>1 ⊕ 5</code> at the position where 1 occurs
 * in plaintext
 * <p>
 * <b>20814804c1767293bd9f1d9cab3bc3e7ac1e37bfb15599e5f40eef805488281d</b>
 * 
 * @author rustam
 * 
 */
public class Q1 {
	private static final String pt = "Pay Bob 100$";
	private static final String ctHex = "20814804c1767293b99f1d9cab3bc3e7ac1e37bfb15599e5f40eef805488281d";
	private static final byte[] ct = Utils.hexToBytes(ctHex);

	public static void main(String[] args) {
		System.out.println(ct.length);
		System.out.println(pt.length());
		System.out.println(pt.indexOf('1'));
		ct[8] ^= 1 ^ 5;
		System.out.println(Utils.toHex(ct));
	}
}
