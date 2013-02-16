package week3;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

import week2.Utils;

public class GCMTest {
	public static void main(String[] args) {
		try {
			gcm1();
			gcm2();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void gcm1() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, IllegalStateException {
		System.out.println("GCM1: ");
		SecureRandom random = new SecureRandom();
		IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
		Key key = Utils.createKeyForAES(256, random);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		String input = "Transfer 0000100 to AC 1234-5678";

		System.out.println("input : " + input);

		// encryption step

		cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

		byte[] cipherText = new byte[cipher.getOutputSize(input.length())];

		int ctLength = cipher.update(Utils.toByteArray(input), 0,
				input.length(), cipherText, 0);
		ctLength += cipher.doFinal(cipherText, ctLength);
		// tampering step

		// cipherText[9] ^= '0' ^ '9';

		// decryption step

		cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

		byte[] plainText = cipher.doFinal(cipherText, 0, ctLength);
		System.out.println("output: " + new String(plainText));
	}

	private static void gcm2() throws IllegalStateException,
			InvalidCipherTextException, NoSuchAlgorithmException,
			NoSuchProviderException {
		System.out.println("GCM2: ");
		SecureRandom random = new SecureRandom();
		IvParameterSpec ivSpec = Utils.createCtrIvForAES(1, random);
		byte iv[] = ivSpec.getIV();
		byte inMsg[] = "Transfer 0000100 to AC 1234-5678".getBytes();
		byte header[] = "header".getBytes();
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(128);
		byte key[] = generator.generateKey().getEncoded();

		// encrypt
		AEADParameters parameters = new AEADParameters(new KeyParameter(key),
				128, iv, header);
		GCMBlockCipher gcmEngine = new GCMBlockCipher(new AESFastEngine());
		gcmEngine.init(true, parameters);

		byte[] encMsg = new byte[gcmEngine.getOutputSize(inMsg.length)];
		int encLen = gcmEngine.processBytes(inMsg, 0, inMsg.length, encMsg, 0);
		encLen += gcmEngine.doFinal(encMsg, encLen);

		// decrypt
		gcmEngine.init(false, parameters);

		byte[] decMsg = new byte[gcmEngine.getOutputSize(encMsg.length)];
		int decLen = gcmEngine
				.processBytes(encMsg, 0, encMsg.length, decMsg, 0);
		decLen += gcmEngine.doFinal(decMsg, decLen);

		System.out.println("output: " + new String(decMsg));
	}
}
