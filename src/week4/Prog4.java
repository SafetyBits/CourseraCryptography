package week4;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import week2.Utils;

/**
 * In this project you will experiment with a padding oracle attack against a
 * toy web site hosted at crypto-class.appspot.com. Padding oracle
 * vulnerabilities affect a wide variety of products, including secure tokens.
 * This project will show how they can be exploited. We discussed CBC padding
 * oracle attacks in Lecture 7.6, but if you want to read more about them,
 * please see Vaudenay's paper.
 * <p>
 * Now to business. Suppose an attacker wishes to steal secret information from
 * our target web site <a
 * href="crypto-class.appspot.com">crypto-class.appspot.com</a>. The attacker
 * suspects that the web site embeds encrypted customer data in URL parameters
 * such as this: <nobr>
 * <code>http://crypto-class.appspot.com/po?er=
 * f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4
 * </code></nobr> That is, when customer Alice interacts with the site, the site
 * embeds a URL like this in web pages it sends to Alice. The attacker
 * intercepts the URL listed above and guesses that the ciphertext following the
 * "po?er=" is a hex encoded AES CBC encryption with a random IV of some secret
 * data about Alice's session.
 * <p>
 * After some experimentation the attacker discovers that the web site is
 * vulnerable to a CBC padding oracle attack. In particular, when a decrypted
 * CBC ciphertext ends in an invalid pad the web server returns a 403 error code
 * (forbidden request). When the CBC padding is valid, but the message is
 * malformed, the web server returns a 404 error code (URL not found).
 * <p>
 * Armed with this information your goal is to decrypt the ciphertext listed
 * above. To do so you can send arbitrary HTTP requests to the web site of the
 * form
 * <code>http://crypto-class.appspot.com/po?er="your ciphertext here"</code> and
 * observe the resulting error code. The padding oracle will let you decrypt the
 * given ciphertext one byte at a time. To decrypt a single byte you will need
 * to send up to 256 HTTP requests to the site. Keep in mind that the first
 * ciphertext block is the random IV. The decrypted message is ASCII encoded.
 * <p>
 * To get you started here is a short Python script that sends a ciphertext
 * supplied on the command line to the site and prints the resulting error code.
 * You can extend this script (or write one from scratch) to implement the
 * padding oracle attack. Once you decrypt the given ciphertext, please enter
 * the decrypted message in the box below.
 * <p>
 * This project shows that when using encryption you must prevent padding oracle
 * attacks by either using encrypt-then-MAC as in EAX or GCM, or if you must use
 * MAC-then-encrypt then ensure that the site treats padding errors the same way
 * it treats MAC errors.
 * 
 * <p>
 * <b>Solution:</b> <br>
 * 
 * <b>The Magic Words are Squeamish Ossifrage<b>
 * 
 * @author rustam
 * 
 */
public class Prog4 {
	private static final int BLOCK_SIZE = 16;
	private static final String urlS = "http://crypto-class.appspot.com/po?er=";
	private static final String qs = "f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4";
	private static final byte[] ct = Utils.hexToBytes(qs);

	private static final byte[][] ctBlocks = new byte[ct.length / 16][];

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < ctBlocks.length; i++) {
			ctBlocks[i] = Arrays.copyOfRange(ct, i * BLOCK_SIZE, (i + 1)
					* BLOCK_SIZE);
		}

		byte[] pt = new byte[ct.length - BLOCK_SIZE];
		// cycle for all 16 byte blocks except IV
		for (int i = ctBlocks.length - 1; i > 0; i--) {
			System.out.println("block number: " + i);
			byte[] prevBlock = ctBlocks[i - 1];
			byte[] currCT = Arrays.copyOf(ct, (i + 1) * BLOCK_SIZE);
			// cycle for all paddings
			for (int pad = 1; pad <= BLOCK_SIZE; pad++) {
				System.out.println("pad: " + pad);
				// apply previously founded LSB
				for (int k = 1; k < pad; k++) {
					int idx = currCT.length - BLOCK_SIZE - k;
					currCT[idx] = (byte) (prevBlock[BLOCK_SIZE - k] ^ pt[idx] ^ pad);
				}
				// cycle for all guesses
				for (int g = 0; g < 256; g++) {
					// skipping iteration when guessed byte and pad are the same
					// and cancels each other
					if (g == 1 && pad == 1 && i == ctBlocks.length - 1)
						continue;
					// XORing previous cipher text block with guess byte and pad
					currCT[currCT.length - BLOCK_SIZE - pad] = (byte) (prevBlock[BLOCK_SIZE
							- pad]
							^ g ^ pad);
					String quess = Utils.toHex(currCT);
					// send guess to server
					int stat = sendGuessRequest(quess);
					if (stat == HttpURLConnection.HTTP_NOT_FOUND
							|| stat == HttpURLConnection.HTTP_OK) {
						pt[i * BLOCK_SIZE - pad] = (byte) g;
						System.out.println(g);
						break;
					}
				}
			}
		}
		System.out.println(new String(pt));
		FileOutputStream fos = new FileOutputStream("padding oracle result");
		fos.write(pt);
		fos.flush();
		fos.close();
	}

	/**
	 * Function makes HTTP request to http://crypto-class.appspot.com with a
	 * given query string parameter
	 * 
	 * @param qs
	 * @return
	 * @throws IOException
	 */
	private static int sendGuessRequest(String qs) throws IOException {
		URL url = new URL(urlS + qs);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(Integer.MAX_VALUE);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setDefaultUseCaches(false);
		int serverResponseCode = conn.getResponseCode();
		conn.disconnect();
		return serverResponseCode;
	}
}
