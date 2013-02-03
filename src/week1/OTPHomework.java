package week1;

public class OTPHomework {
	public static void main(String[] args) {
		byte[] pt1 = getEnc("attack at dawn");
		byte[] pt2 = getEnc("attack at dusk");

		// { 0x61, 0x74, 0x74, 0x61, 0x63, 0x6b, 0x20, 0x61, 0x74,
		// 0x20, 0x64, 0x61, 0x77, 0x6e };
		// 09 e1 c5 f7 0a 65 ac 51 94 58 e7 f1 3b 33
		// 09 e1 c5 f7 0a 65 ac 51 94 58 e7 e5 3f 36
		int[] ct1 = { 0x09, 0xe1, 0xc5, 0xf7, 0x0a, 0x65, 0xac, 0x51, 0x94,
				0x58, 0xe7, 0xe5, 0x3f, 0x36 };
		int[] ct2 = new int[pt1.length];
		String ct2Hex = "";
		int[] key = new int[pt1.length];
		String ptEnc = "";
		// String keyStr = "";
		for (int i = 0; i < pt1.length; i++) {
			ptEnc += String.valueOf((char) pt1[i]);
			key[i] = pt1[i] ^ ct1[i];
		}
		System.out.println(ptEnc);
		ptEnc = "";
		for (int i = 0; i < pt2.length; i++) {
			// ptEnc += String.valueOf((char) pt2[i]);
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

	private static byte[] getEnc(String pt) {
		byte[] res = new byte[pt.length()];
		for (int i = 0; i < pt.length(); i++) {
			res[i] = (byte) pt.charAt(i);
		}
		return res;
	}
}
