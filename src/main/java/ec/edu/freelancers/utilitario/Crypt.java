package ec.edu.freelancers.utilitario;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Clase encriptacion desencriptacion, se basa en un metodo combinado creando
 * una llave de encriptacion/desencriptacion, misma llave que sera cambiada
 * automaticamente por el sistema pasado un determinado tiempo, copnfigurado en
 * la DB
 * 
 * @author James
 */
public class Crypt implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Cipher eCipher;
	private static Cipher dCipher;
	private final static String KEY_SPEC = "KeySpec_MRL_SENRES_Actas2010_Powered_By_James";
	private final static String KEY_SALT = "MRLUIOEC";

	public static void main(String[] args) {
		// Random random = new Random();
		// String s = "" + random.nextInt(99999);
		// s = Utilities.complete(s, 5, '0', true);
		// System.out.println("> " + s );
		// System.out.println("> " + Crypt.encrypt(s));
	}

	/**
	 * Genera la calve de encriptacion a partir dela lectura del archivo de
	 * propiedades, eso lo relaiza solo una ves cuando se encuentre en nulos los
	 * valores de encriptacion
	 */
	static {
		if (eCipher == null | dCipher == null) {
			try {
				byte[] salt = KEY_SALT.getBytes();
				int iterationCount = 64;
				String passer = KEY_SPEC;
				KeySpec keySpec = new PBEKeySpec(passer.toCharArray(), salt,
						iterationCount);
				SecretKey key = SecretKeyFactory
						.getInstance("PBEWithMD5AndDES")
						.generateSecret(keySpec);
				eCipher = Cipher.getInstance(key.getAlgorithm());
				dCipher = Cipher.getInstance(key.getAlgorithm());
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
						iterationCount);
				eCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				dCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			} catch (InvalidAlgorithmParameterException e) {
				System.out
						.println("EXCEPTION: InvalidAlgorithmParameterException "
								+ e);
			} catch (InvalidKeySpecException e) {
				System.out.println("EXCEPTION: InvalidKeySpecException " + e);
			} catch (NoSuchPaddingException e) {
				System.out.println("EXCEPTION: NoSuchPaddingException " + e);
			} catch (NoSuchAlgorithmException e) {
				System.out.println("EXCEPTION: NoSuchAlgorithmException  " + e);
			} catch (InvalidKeyException e) {
				System.out.println("EXCEPTION: InvalidKeyException  " + e);
			} catch (Exception e) {
				System.out.println("EXCEPTION: Exception  " + e);
			}
		}
	}

	/**
	 * Permite encriptar una determinada cadena de texto
	 * 
	 * @param value
	 *            Valor a encriptar
	 * @return Cadena de texto encriptada
	 */
	public static String encrypt(String value) {
		try {
			byte[] utf8 = value.getBytes("UTF8");
			byte[] enc = eCipher.doFinal(utf8);
			return Base64.encodeBytes(enc);
		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * Permite desencriptar una determinada cadena de texto
	 * 
	 * @param value
	 *            cadena de encriptada, para desencriptar
	 * @return cedena desencriptada
	 */
	public static String decrypt(String value) {
		try {
			byte[] dec = Base64.decode(value);
			byte[] utf8 = dCipher.doFinal(dec);
			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * El algoritmo MD5 sera el que se utilize para encriptar cadenas de
	 * caracteres corrspondientes principalmenet a claves de acceso para
	 * usuarios, por lo que no se podra recuperar el valor original, para
	 * recuperar valores reales se usara otro tipo de encriptacion
	 * 
	 * @param text
	 *            Cadena a encriptar
	 * @return Cadena resultante de la encriptacion
	 */
	public static String encryptMD5(String text) {
		String result = null;
		try {
			if (text != null) {
				MessageDigest algorithm = MessageDigest.getInstance("MD5");
				algorithm.reset();
				algorithm.update(text.getBytes());
				byte bytes[] = algorithm.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					String hex = Integer.toHexString(0xff & bytes[i]);
					if (hex.length() == 1) {
						sb.append('0');
					}
					sb.append(hex);
				}
				result = sb.toString();
			}
		} catch (NoSuchAlgorithmException e) {
		}
		return result;
	}

	public static String encryptDES(String text) throws Exception {
		String vetorinicializacion = "1s%3piuo";
		String llave = "bpmencri";
		DESKeySpec key = new DESKeySpec(llave.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(key);
		byte iv[] = vetorinicializacion.getBytes();
	    IvParameterSpec dps = new IvParameterSpec(iv);
	    Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
	    c.init(Cipher.ENCRYPT_MODE, secretKey, dps);
	    byte[] unencryptedByteArray = text.getBytes("UTF8");
        // Encrypt
        byte[] encryptedBytes = c.doFinal(unencryptedByteArray);
        // Encode bytes to base64 to get a string
        byte [] encodedBytes = Base64.encodeBytesToBytes(encryptedBytes);	   
	    return new String(encodedBytes, "UTF8");
	}

}
