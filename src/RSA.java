import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class RSA {
    private Cipher cipher;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSA(int keySize) throws Exception {
        this.cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        KeyPair keyPair = keyGen.generateKeyPair();
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public String encrypt(String plaintext) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public static void main(String[] args) throws Exception {

        RSA rsa = new RSA(2048);

        String plaintext = "Viettel Cyber Security";
        String encryptedText = rsa.encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = rsa.decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
