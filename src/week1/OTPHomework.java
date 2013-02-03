package week1;

public class OTPHomework {
	public static void main(String[] args) {
		byte[] pt1 = "attack at dawn".getBytes();
		byte[] pt2 = "attack at dusk".getBytes();
		int[] ct1 = { 0x09, 0xe1, 0xc5, 0xf7, 0x0a, 0x65, 0xac, 0x51, 0x94,
				0x58, 0xe7, 0xe5, 0x3f, 0x36 };
		int[] ct2 = new int[pt1.length];
		String ct2Hex = "";
		int[] key = new int[pt1.length];
		String ptEnc = "";
		for (int i = 0; i < pt1.length; i++) {
			ptEnc += String.valueOf((char) pt1[i]);
			key[i] = pt1[i] ^ ct1[i];
		}
		System.out.println(ptEnc);
		ptEnc = "";
		for (int i = 0; i < pt2.length; i++) {
			ct2[i] = pt2[i] ^ key[i];
			ptEnc += String.valueOf((char) (ct2[i] ^ key[i]));
			if (ct2[i] < 16)
				ct2Hex += "0" + Integer.toHexString(ct2[i]);
			else
				ct2Hex += Integer.toHexString(ct2[i]);

		}
		System.out.println(ptEnc);
		System.out.println(ct2Hex);
	}
}
