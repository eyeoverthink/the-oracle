package fraymus;

import java.math.BigInteger;

public class PhiNode {
    public float x, y, z;
    public float vx, vy, vz;

    public float r, g, b;
    public float energy;

    public float frequency;
    public float phase;
    
    public final BigInteger signature;
    public final String dnaSeed;

    public PhiNode(float x, float y, float freq, String dnaSeed) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.frequency = freq;
        this.phase = 0;
        this.energy = 1.0f;
        this.dnaSeed = dnaSeed;
        
        this.signature = DNACloaker.generateIdentity(dnaSeed);
        
        int hash = this.signature.hashCode();
        this.r = ((hash >> 16) & 0xFF) / 255f;
        this.g = ((hash >> 8) & 0xFF) / 255f;
        this.b = (hash & 0xFF) / 255f;
    }

    public boolean isEntangledWith(PhiNode other) {
        float epsilonFreq = 0.01f;
        float epsilonPhase = 0.05f;
        
        return Math.abs(this.frequency - other.frequency) < epsilonFreq &&
               Math.abs(this.phase - other.phase) < epsilonPhase;
    }
    
    @Override
    public String toString() {
        return String.format("[NODE %s] Pos:(%.2f, %.2f) Freq:%.2f Phase:%.2f Energy:%.2f",
            dnaSeed, x, y, frequency, phase, energy);
    }
}
