package week2;

/**
 * Recall that encryption systems do not fully hide the length of transmitted
 * messages. Leaking the length of web requests has been used to eavesdrop on
 * encrypted HTTPS traffic to a number of web sites, such as tax preparation
 * sites, Google searches, and healthcare sites. Suppose an attacker intercepts
 * a packet where he knows that the packet payload is encrypted using AES in CBC
 * mode with a random IV. The encrypted packet payload is 128 bytes. Which of
 * the following messages is plausibly the decryption of the payload:
 * 
 * @author rustam
 * 
 */
public class Q8 {
	private static final String[] msg = {
			"The most direct computation would be for the enemy to try all 2^r possible keys, one by one.",
			"To consider the resistance of an enciphering process to being broken we should assume that at same times the enemy knows everything but the key being used and to break it needs only discover the key from this information.",
			"The significance of this general conjecture, assuming its truth, is easy to see. It means that it may be feasible to design ciphers that are effectively unbreakable.",
			"In this letter I make some remarks on a general principle relevant to enciphering in general and my machine." };

	public static void main(String[] args) {
		for (int i = 0; i < msg.length; i++) {
			int msgLen = msg[i].toCharArray().length;
			if (msgLen <= 112 && msgLen > 96) {
				System.out.println("The message is: " + msg[i]);
				break;
			}
		}

	}
}
