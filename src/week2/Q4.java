package week2;

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
