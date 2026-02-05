package fraymus;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Cryptographic Cloaking - RSA-style factorization identity
 * 
 * N = p₁ × p₂
 * 
 * Public N is visible but meaningless without factoring into p and q.
 * Only correct credentials can unlock the entity (proof of identity).
 * Two separate hashes of the seed produce two distinct primes.
 */
public class DNACloaker {
    
    public static class CloakedIdentity {
        public final BigInteger p;
        public final BigInteger q;
        public final BigInteger N;
        
        public CloakedIdentity(BigInteger p, BigInteger q) {
            this.p = p;
            this.q = q;
            this.N = p.multiply(q);
        }
        
        public boolean verify(BigInteger claimedP, BigInteger claimedQ) {
            return (claimedP.equals(p) && claimedQ.equals(q)) ||
                   (claimedP.equals(q) && claimedQ.equals(p));
        }
        
        @Override
        public String toString() {
            return String.format("CloakedIdentity[N=%s..., bits=%d]",
                N.toString(16).substring(0, Math.min(16, N.toString(16).length())),
                N.bitLength());
        }
    }
    
    /**
     * Generate RSA-style cloaked identity: N = p₁ × p₂
     * Two separate SHA-256 hashes produce two distinct primes.
     */
    public static CloakedIdentity generateCloakedIdentity(String seed) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashA = digest.digest((seed + "_A").getBytes("UTF-8"));
            BigInteger bigA = new BigInteger(1, hashA);
            BigInteger p = bigA.nextProbablePrime();
            
            digest.reset();
            byte[] hashB = digest.digest((seed + "_B").getBytes("UTF-8"));
            BigInteger bigB = new BigInteger(1, hashB);
            BigInteger q = bigB.nextProbablePrime();
            
            if (p.equals(q)) {
                q = q.nextProbablePrime();
            }
            
            return new CloakedIdentity(p, q);
        } catch (Exception e) {
            throw new RuntimeException("Crypto Failure", e);
        }
    }
    
    /**
     * Legacy: generate single prime identity (for backward compat)
     */
    public static BigInteger generateIdentity(String seed) {
        return generateCloakedIdentity(seed).N;
    }
    
    /**
     * Entangle two identities: N_entangled = N_a × N_b
     */
    public static BigInteger entangleIdentities(BigInteger sigA, BigInteger sigB) {
        return sigA.multiply(sigB);
    }
    
    /**
     * Verify ownership: can the claimant factor N back into p and q?
     */
    public static boolean verifyOwnership(CloakedIdentity identity, BigInteger claimedP, BigInteger claimedQ) {
        return identity.verify(claimedP, claimedQ);
    }
}
