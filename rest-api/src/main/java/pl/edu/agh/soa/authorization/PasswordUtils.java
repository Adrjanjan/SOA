package pl.edu.agh.soa.authorization;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

@Slf4j
public class PasswordUtils {
    private PasswordUtils() {
    }

    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] hashedPassword = md.digest();
            return Base64.encodeBase64String(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Cant encode password: " + e.getMessage());
        }
    }

}
