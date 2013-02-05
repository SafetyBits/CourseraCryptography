package week2;

/**
 * Recall that the Luby-Rackoff theorem discussed in Lecture 3.2 states that
 * applying a three round Feistel network to a secure PRF gives a secure block
 * cipher. Let's see what goes wrong if we only use a two round Feistel. Let
 * <code>F:K×{0,1}<sup>32</sup>→{0,1}<sup>32</sup></code> be a secure PRF.
 * Recall that a 2-round Feistel defines the following PRP
 * <code>F2:K2×{0,1}<sup>64</sup>→{0,1}<sup>64</sup></code>:
 * 
 * <img src="docs/images_Feistel.jpg" />
 * <p>
 * Here R0 is the right 32 bits of the 64-bit input and L0 is the left 32 bits.
 * <p>
 * One of the following lines is the output of this PRP F2 using a random key,
 * while the other three are the output of a truly random permutation
 * <code>f:{0,1}<sup>64</sup>→{0,1}<sup>64</sup></code>. All 64-bit outputs are
 * encoded as 16 hex characters. Can you say which is the output of the PRP?
 * Note that since you are able to distinguish the output of F2 from random, F2
 * is not a secure block cipher, which is what we wanted to show.
 * <p>
 * Hint: First argue that there is a detectable pattern in the xor of
 * <code>F2(⋅,0<sup>64</sup>)</code> and
 * <code>F2(⋅,1<sup>32</sup>0<sup>32</sup>)</code>. Then try to detect this
 * pattern in the given outputs.
 * <p>
 * <b>Solution:</b>
 * <p>
 * Feistel network result for 0<sup>64</sup>
 * <p>
 * <code>
 * R1 = F(k1,0<sup>32</sup>) ⊕ 0<sup>32</sup>
 * <p>
 * L1 = 0<sup>32</sup>
 * <p>
 * R2 = F(k2,R1) ⊕ 0<sup>32</sup>
 * <p>
 * L2 = R1
 * </code>
 * <p>
 * Feistel network result for 1<sup>32</sup>0<sup>32</sup>
 * <p>
 * <code>
 * R`1 = F(k1,0<sup>32</sup>) ⊕ 1<sup>32</sup>
 * <p>
 * L`1 = 1<sup>32</sup>
 * <p>
 * R`2 = F(k2, R`1) ⊕ 1<sup>32</sup>
 * <p>
 * L`2 = R`1
 * </code>
 * <p>
 * Xoring the results gives:
 * <p>
 * <code>
 * F2(⋅,0<sup>64</sup>) ⊕ F2(⋅,1<sup>32</sup>0<sup>32</sup>) =
 * <p> 
 * = (F(k1,0<sup>32</sup>) ⊕ 0<sup>32</sup> || F(k2,R1) ⊕ 0<sup>32</sup>) ⊕ (F(k1,0<sup>32</sup>) ⊕ 1<sup>32</sup> || F(k2, R`1) ⊕ 1<sup>32</sup>) = 
 * <p>
 * = 1<sup>32</sup> || F(k2,R1) ⊕ 0<sup>32</sup> ⊕ F(k2, R`1) ⊕ 1<sup>32</sup>
 * <p>
 * </code>
 * <p>
 * so, result of xor should contains 1 in first 32 bits.
 * 
 * @author rustam
 * 
 */
public class Q4 {
	public static void main(String[] args) {
		long c0 = 0x2d1cfa42c0b1d266L;
		long c10 = 0xeea6e3ddb2146dd0L;
		System.out.println(Long.toHexString(c0));
		System.out.println(Long.toHexString(c10));
		System.out.println(Long.toBinaryString(c0 ^ c10));
		System.out.println();

		c0 = 0x4af532671351e2e1L;
		c10 = 0x87a40cfa8dd39154L;
		System.out.println(Long.toHexString(c0));
		System.out.println(Long.toHexString(c10));
		System.out.println(Long.toBinaryString(c0 ^ c10));
		System.out.println();

		c0 = 0x5f67abaf5210722bL;
		c10 = 0xbbe033c00bc9330eL;
		System.out.println(Long.toHexString(c0));
		System.out.println(Long.toHexString(c10));
		System.out.println(Long.toBinaryString(c0 ^ c10));
		System.out.println();

		c0 = 0x290b6e3a39155d6fL;
		c10 = 0xd6f491c5b645c008L;
		System.out.println(Long.toHexString(c0));
		System.out.println(Long.toHexString(c10));
		System.out.println(Long.toBinaryString(c0 ^ c10));
		System.out.println();
	}
}
