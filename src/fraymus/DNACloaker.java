package fraymus;

import java.math.BigInteger;
import java.security.MessageDigest;

public class DNACloaker {
    
    public static BigInteger generateIdentity(String seed) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(seed.getBytes("UTF-8"));
            
            BigInteger bigInt = new BigInteger(1, hash);
            
            return bigInt.nextProbablePrime();
        } catch (Exception e) {
            throw new RuntimeException("Crypto Failure", e);
        }
    }

    public static BigInteger entangleIdentities(PhiNode a, PhiNode b) {
        return a.signature.multiply(b.signature);
    }
}
