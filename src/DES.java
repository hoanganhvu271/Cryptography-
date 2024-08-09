import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.util.Base64;

public class DES {
    private Cipher cipher;
    private SecretKey key;
    private IvParameterSpec ivParams;
    private String mode;
    private String paddingType;

    public DES(String mode, String paddingType) throws Exception {
        this.mode = mode;
        this.paddingType = paddingType;
        this.cipher = Cipher.getInstance("DES/" + mode + "/" + paddingType);

        generateKey();

        if (mode.equals("CBC")) {
            byte[] iv = new byte[this.cipher.getBlockSize()];
            new java.security.SecureRandom().nextBytes(iv);
            this.ivParams = new IvParameterSpec(iv);
        }
    }

    private void generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        this.key = keyGen.generateKey();
    }
    public String encrypt(String plaintext) throws Exception {
        if (this.mode.equals("CBC")) {
            cipher.init(Cipher.ENCRYPT_MODE, this.key, this.ivParams);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, this.key);
        }

        byte[] encryptedByte = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedByte);

        return encryptedText;
    }

    public String decrypt(String encryptedText) throws Exception {
        if (this.mode.equals("CBC")) {
            cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedByte);

        return decryptedText;
    }
}