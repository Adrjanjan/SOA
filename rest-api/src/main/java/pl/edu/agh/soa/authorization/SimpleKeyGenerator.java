package pl.edu.agh.soa.authorization;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class SimpleKeyGenerator implements KeyGenerator {

    @Override
    public Key generateKey() {
        var secret = "ujsnyadgfuyUYSFN216UynfkN$VUnhjgBUIONNYJ5ytd5ufb67rb7867YTFKYUnystfuTjgnqyufauyYUBFNJ";
        return new SecretKeySpec(secret.getBytes(), 0, secret.length(), "DES");
    }
}
