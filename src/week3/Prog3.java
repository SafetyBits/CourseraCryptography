package week3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import week2.Utils;

/**
 * Suppose a web site hosts large video file F that anyone can download.
 * Browsers who download the file need to make sure the file is authentic before
 * displaying the content to the user. One approach is to have the web site hash
 * the contents of F using a collision resistant hash and then distribute the
 * resulting short hash value <code>h=H(F)</code> to users via some
 * authenticated channel (later on we will use digital signatures for this).
 * Browsers would download the entire file F, check that <code>H(F)</code> is
 * equal to the authentic hash value <code>h</code> and if so, display the video
 * to the user.
 * <p>
 * Unfortunately, this means that the video will only begin playing after the
 * *entire* file F has been downloaded. Our goal in this project is to build a
 * file authentication system that lets browsers authenticate and play video
 * chunks as they are downloaded without having to wait for the entire file.
 * <p>
 * Instead of computing a hash of the entire file, the web site breaks the file
 * into 1KB blocks (1024 bytes). It computes the hash of the last block and
 * appends the value to the second to last block. It then computes the hash of
 * this augmented second to last block and appends the resulting hash to the
 * third block from the end. This process continues from the last block to the
 * first as in the following diagram:
 * <p>
 * <img src="docs/images_pp3-fig.jpg">
 * <p>
 * The final hash value <code>h0</code> – a hash of the first block with its
 * appended hash – is distributed to users via the authenticated channel as
 * above.
 * <p>
 * Now, a browser downloads the file F one block at a time, where each block
 * includes the appended hash value from the diagram above. When the first block
 * <code>(B0 || h1)</code> is received the browser checks that
 * <code>H(B0 || h1)</code> is equal to <code>h0</code> and if so it begins
 * playing the first video block. When the second block <code>(B1 || h2)</code>
 * is received the browser checks that <code>H(B1 || h2)</code> is equal to h1
 * and if so it plays this second block. This process continues until the very
 * last block. This way each block is authenticated and played as it is received
 * and there is no need to wait until the entire file is downloaded.
 * <p>
 * It is not difficult to argue that if the hash function <code>H</code> is
 * collision resistant then an attacker cannot modify any of the video blocks
 * without being detected by the browser. Indeed, since
 * <code>h0=H(B0 || h1)</code> an attacker cannot find a pair
 * <code>(B′0,h′1)≠(B0,h1)</code> such that <code>h0=H(B0 || h1)</code> since
 * this would break collision resistance of <code>H</code>. Therefore after the
 * first hash check the browser is convinced that both B0 and h1 are authentic.
 * Exactly the same argument proves that after the second hash check the browser
 * is convinced that both B1 and h2 are authentic, and so on for the remaining
 * blocks.
 * <p>
 * In this project we will be using <b>SHA256</b> as the hash function. For an
 * implementation of <b>SHA256</b> use an existing crypto library such as
 * PyCrypto (Python), Crypto++ (C++), or any other. When appending the hash
 * value to each block, please append it as binary data, that is, as 32
 * unencoded bytes (which is 256 bits). If the file size is not a multiple of
 * 1KB then the very last block will be shorter than 1KB, but all other blocks
 * will be exactly 1KB.
 * <p>
 * Your task is to write code to compute the hash h0 of a given file F and to
 * verify blocks of F as they are received by the client. In the box below
 * please enter the (hex encoded) hash h0 for <a href=
 * "https://class.coursera.org/crypto-005/lecture/download.mp4?lecture_id=27"
 * >this video file</a>.
 * <p>
 * You can check your code by using it to hash a different file. In particular,
 * the hex encoded h0 for <a href=
 * "https://class.coursera.org/crypto-005/lecture/download.mp4?lecture_id=28"
 * >this video file</a> is:
 * <code>03c08f4ee0b576fe319338139c045c89c3e8e9409633bea29442e21425006ea8</code>
 * <p>
 * <b>Solution</b>
 * <p>
 * Just use standard JCA/JCE API.
 * 
 * @author rustam
 * 
 */
public class Prog3 {
	private static final String filePath = "C:\\Users\\rustam\\Videos\\Coursera\\Cryptography I\\Week3.2 Collision Resistance\\6 - 1 - Introduction (11 min).mp4";
	// 6 - 2 - Generic birthday attack (16 min).mp4
	private static MessageDigest digest = null;

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException {
		File f = new File(filePath);
		RandomAccessFile rf = new RandomAccessFile(f, "r");
		digest = MessageDigest.getInstance("SHA-256");
		byte[] chunk = new byte[1024 + digest.getDigestLength()];
		byte[] prevChunkHash = new byte[digest.getDigestLength()];

		long index = f.length() - f.length() % 1024;
		for (; index >= 0; index -= 1024) {
			rf.seek(index);
			int r = rf.read(chunk, 0, 1024);
			if (r < 1024) {
				digest.update(chunk, 0, r);
			} else {
				System.arraycopy(prevChunkHash, 0, chunk, 1024,
						prevChunkHash.length);
				digest.update(chunk, 0, chunk.length);
			}
			prevChunkHash = digest.digest();
		}
		System.out.println(Utils.toHex(prevChunkHash));
	}
}
