import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class AES {
    private Cipher cipher;
    private SecretKey key;
    private IvParameterSpec ivParams;

    private String mode;
    private String paddingType;

    public AES(String mode, String paddingType, int keyLength) throws Exception {
        this.mode = mode;
        this.paddingType = paddingType;
        this.cipher = Cipher.getInstance("AES/" + mode + "/" + paddingType);

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keyLength);
        this.key = keyGen.generateKey();

        if (mode.equals("CBC")) {
            byte[] iv = new byte[this.cipher.getBlockSize()];
            new java.security.SecureRandom().nextBytes(iv);
            this.ivParams = new IvParameterSpec(iv);
        }
    }

    public String encrypt(String plaintext) throws Exception {
        if (mode.equals("CBC")) {
            cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ivParams);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
        }

        byte[] encryptedByte = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedByte);

        return encryptedText;
    }

    public String decrypt(String encryptedText) throws Exception {
        if (mode.equals("CBC")) {
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedByte);

        return decryptedText;
    }
}