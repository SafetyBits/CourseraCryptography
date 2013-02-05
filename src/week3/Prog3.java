package week3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import week2.Utils;

public class Prog3 {
	private static final String filePath = "C:\\Users\\rustam\\Videos\\Coursera\\Cryptography I\\week3\\6 - 1 - Introduction (11 min).mp4";
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
