package com.automoto.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for password encryption and decryption using AES-GCM algorithm.
 * This class provides methods for secure password handling using AES encryption
 * with Galois/Counter Mode (GCM). It supports both random key generation and
 * password-based key derivation (PBKDF2).
 * The implementation uses:
 * - AES-256 encryption with GCM mode
 * - 128-bit authentication tag
 * - 12-byte initialization vector (IV)
 * - 16-byte salt for PBKDF2
 * - 65,536 iterations for key derivation
 */
public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generates a cryptographically secure random nonce of specified length.
     *
     * @param numBytes the length of the nonce in bytes
     * @return a byte array containing the random nonce
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a new AES secret key of the specified size.
     *
     * @param keysize the key size in bits (typically 128, 192, or 256)
     * @return the generated SecretKey
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES secret key from a password using PBKDF2 with HMAC-SHA256.
     * Uses a fixed iteration count of 65536 and key length of 256 bits.
     *
     * @param password the password to derive the key from
     * @param salt the salt to use for key derivation
     * @return the derived SecretKey, or null if derivation fails
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // iterationCount = 65536
            // keyLength = 256
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Encrypts a password using AES-GCM with a key derived from the employee ID.
     * The encrypted result includes the IV, salt, and ciphertext concatenated together
     * and encoded as a Base64 string.
     *
     * @param employee_id the employee ID used as the password derivation source
     * @param password the plaintext password to encrypt
     * @return Base64-encoded encrypted string (IV+salt+ciphertext), or null if encryption fails
     */
    public static String encrypt(String employee_id, String password) {
        try {
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
            SecretKey aesKeyFromPassword = getAESKeyFromPassword(employee_id.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] cipherText = cipher.doFinal(password.getBytes());
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Decrypts a password encrypted by the encrypt method.
     * The input string should be a Base64-encoded combination of IV, salt, and ciphertext.
     *
     * @param encryptedPassword the Base64-encoded encrypted password
     * @param username the username used for key derivation (must match the one used for encryption)
     * @return the decrypted plaintext password, or null if decryption fails
     */
    public static String decrypt(String encryptedPassword, String username) {
        try {
            byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));
            ByteBuffer bb = ByteBuffer.wrap(decode);
            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);
            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);
            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);
            SecretKey aesKeyFromPassword = PasswordUtil.getAESKeyFromPassword(username.toCharArray(), salt);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] plainText = cipher.doFinal(cipherText);
            return new String(plainText, UTF_8);
        } catch(Exception ex) {
            return null;
        }
    }
}