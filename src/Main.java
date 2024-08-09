import java.security.MessageDigest;
import java.util.Base64;
public class Main {

    public static void main(String[] args) throws Exception {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        String text = "Viettel CYber Security";
        byte[] byteResult = messageDigest.digest(text.getBytes());
        System.out.println("Message Digest: " + Base64.getEncoder().encodeToString(byteResult));

    }
}
