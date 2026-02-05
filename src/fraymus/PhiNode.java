package fraymus;

import java.math.BigInteger;

public class PhiNode {
    public float x, y, z;
    public float vx, vy, vz;

    public float r, g, b;
    public float energy;

    public float frequency;
    public float phase;
    public float phiResonance;
    
    public final BigInteger signature;
    public final String dnaSeed;
    public long lastUpdateNanos;

    public PhiNode(float x, float y, float freq, String dnaSeed) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.frequency = freq;
        this.phase = 0;
        this.energy = 1.0f;
        this.dnaSeed = dnaSeed;
        
        this.signature = DNACloaker.generateIdentity(dnaSeed);
        
        int hash = this.signature.hashCode();
        this.r = ((hash >> 16) & 0xFF) / 255f;
        this.g = ((hash >> 8) & 0xFF) / 255f;
        this.b = (hash & 0xFF) / 255f;
        
        this.lastUpdateNanos = System.nanoTime();
    }

    public void updateInternalState(float dt, long nowNanos) {
        this.energy = Math.max(0f, this.energy - 0.01f * dt);
        
        double t = nowNanos * 1e-9;
        this.phiResonance = (float)(Math.sin(t * frequency * 1.618) * 0.5 + 1.0);
        
        this.lastUpdateNanos = nowNanos;
    }

    public boolean isEntangledWith(PhiNode other) {
        float epsilonFreq = 0.5f;
        float epsilonPhase = 0.1f;
        
        return Math.abs(this.frequency - other.frequency) < epsilonFreq &&
               Math.abs(this.phase - other.phase) < epsilonPhase;
    }
    
    public boolean isAlive() {
        return energy > 0.001f;
    }
    
    @Override
    public String toString() {
        String status = isAlive() ? "ALIVE" : "DEAD";
        return String.format("[NODE %s] Pos:(%.2f, %.2f) Freq:%.2f Phase:%.2f Energy:%.1f%% Phi:%.3f [%s]",
            dnaSeed, x, y, frequency, phase, energy * 100, phiResonance, status);
    }
}
