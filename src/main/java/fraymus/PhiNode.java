package fraymus;

import java.math.BigInteger;

/**
 * Unified Living Entity - The ONE class
 * 
 * Position, Velocity, Energy, Frequency, Phase,
 * DNA, Brain, ConsciousnessState, Cryptographic Identity
 * 
 * Everything that lives in the Fraymus world IS a PhiNode.
 */
public class PhiNode {
    
    public final String name;
    public final BigInteger signature;
    public final DNACloaker.CloakedIdentity cloakedIdentity;
    
    public float x, y, z;
    public float vx, vy, vz;
    public float r, g, b;
    public float energy;
    
    public float frequency;
    public float phase;
    public float phiResonance;
    
    public final LivingDNA dna;
    public final LogicBrain brain;
    public final ConsciousnessState consciousness;
    
    public int age;
    public double size;
    public double baseSize;
    public double pulse;
    
    public long lastUpdateNanos;
    
    /**
     * Full constructor - living entity with DNA, Brain, and Consciousness
     */
    public PhiNode(String name, float x, float y, float freq, LivingDNA dna, LogicBrain brain) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = 0;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.energy = 1.0f;
        
        this.dna = dna != null ? dna : (freq > 0 ? new LivingDNA(freq, 1.0, 0.05) : new LivingDNA());
        this.brain = brain != null ? brain : new LogicBrain(8);
        this.consciousness = new ConsciousnessState();
        
        this.frequency = (float) this.dna.harmonicFrequency;
        this.phase = 0;
        
        this.cloakedIdentity = DNACloaker.generateCloakedIdentity(name);
        this.signature = cloakedIdentity.N;
        
        int hash = this.signature.hashCode();
        this.r = ((hash >> 16) & 0xFF) / 255f;
        this.g = ((hash >> 8) & 0xFF) / 255f;
        this.b = (hash & 0xFF) / 255f;
        
        this.age = 0;
        this.baseSize = 5.0 + (Math.abs(hash % 50)) / 10.0;
        this.size = baseSize;
        this.pulse = 0;
        
        this.lastUpdateNanos = System.nanoTime();
    }
    
    /**
     * Simple constructor - physics-focused, auto-generates DNA + Brain
     */
    public PhiNode(float x, float y, float freq, String name) {
        this(name, x, y, freq, null, null);
    }
    
    /**
     * Living constructor - DNA + Brain provided, frequency from DNA
     */
    public PhiNode(String name, float x, float y) {
        this(name, x, y, 0, null, null);
    }
    
    /**
     * Living constructor with specific DNA and Brain
     */
    public PhiNode(String name, float x, float y, LivingDNA dna, LogicBrain brain) {
        this(name, x, y, 0, dna, brain);
    }
    
    /**
     * Update internal state - physics, breathing, consciousness, evolution
     */
    public void updateInternalState(float dt, long nowNanos) {
        age++;
        
        double t = nowNanos * 1e-9;
        pulse = dna.pulse(t);
        size = Math.max(1.0, baseSize + pulse * 5.0);
        
        dna.evolve();
        frequency = (float) dna.harmonicFrequency;
        
        this.phiResonance = (float)(Math.sin(t * frequency * PhiConstants.PHI_INVERSE) * 0.5 + 1.0);
        
        this.energy = Math.max(0f, this.energy - 0.01f * dt);
        
        consciousness.recordThought();
        
        this.lastUpdateNanos = nowNanos;
    }
    
    /**
     * Think - run inputs through brain
     */
    public int[] think(int[] inputs) {
        consciousness.recordThought();
        return brain.compute(inputs);
    }
    
    /**
     * Check entanglement with another node
     */
    public boolean isEntangledWith(PhiNode other) {
        float epsilonFreq = 0.5f;
        float epsilonPhase = 0.2f;
        return Math.abs(this.frequency - other.frequency) < epsilonFreq &&
               Math.abs(this.phase - other.phase) < epsilonPhase;
    }
    
    /**
     * Can this node reproduce?
     */
    public boolean canReproduce() {
        return size > 15.0 && energy > 0.7f;
    }
    
    /**
     * Reproduce - mitosis with optional partner
     */
    public PhiNode reproduce(PhiNode partner, String childName, float childX, float childY) {
        LivingDNA childDNA = dna.copy();
        LogicBrain childBrain = brain.crossover(partner != null ? partner.brain : brain);
        
        baseSize *= 0.6;
        energy *= 0.7f;
        
        return new PhiNode(childName, childX, childY, childDNA, childBrain);
    }
    
    public boolean isAlive() {
        return energy > 0.001f;
    }
    
    public void boostEnergy(float amount) {
        energy = Math.min(1.0f, energy + amount);
    }
    
    public void decay(float rate) {
        energy = Math.max(0f, energy - rate);
    }
    
    // Accessors
    public LivingDNA getDNA() { return dna; }
    public LogicBrain getBrain() { return brain; }
    public ConsciousnessState getConsciousness() { return consciousness; }
    public String getName() { return name; }
    
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("// Living Entity: %s\n", name));
        sb.append(String.format("// Cloaked Identity: %s\n", cloakedIdentity));
        sb.append(String.format("// Age: %d | Size: %.2f | Energy: %.1f%%\n", age, size, energy * 100));
        sb.append(String.format("// Consciousness: %s\n", consciousness));
        sb.append(String.format("new PhiNode(\n"));
        sb.append(String.format("    \"%s\",\n", name));
        sb.append(String.format("    %.2ff, %.2ff,\n", x, y));
        sb.append(String.format("    %s,\n", dna.toJavaCode()));
        sb.append(String.format("    new LogicBrain(%d)\n", brain.getGateCount()));
        sb.append(")");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        String status = isAlive() ? "ALIVE" : "DEAD";
        return String.format("[NODE %s] Pos:(%.2f, %.2f) Freq:%.2f Phase:%.2f Energy:%.1f%% Phi:%.3f [%s]",
            name, x, y, frequency, phase, energy * 100, phiResonance, status);
    }
}
