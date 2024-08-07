import java.security.*;

public class DigitalSignature {
    public static void main(String[] args) throws Exception {
        String message = "Viettel Cyber Secutity";

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair key = keyGen.generateKeyPair();

        PublicKey publicKey =  key.getPublic();
        PrivateKey privateKey = key.getPrivate();


        byte[] messageBytes = message.getBytes();

        //sign
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initSign(privateKey);
        s.update(messageBytes);
        byte[] signature = s.sign();

        //verify
        s.initVerify(publicKey);
        s.update(message.getBytes());
        boolean valid = s.verify(signature);

        System.out.println(valid);

    }
}
