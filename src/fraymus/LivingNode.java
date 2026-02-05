package fraymus;

import java.math.BigInteger;
import java.util.Random;

public class LivingNode {
    private static final Random rng = new Random();
    
    public final String name;
    public final BigInteger signature;
    public final LivingDNA dna;
    public final LogicBrain brain;
    
    public float x, y;
    public float vx, vy;
    public float energy;
    public float frequency;
    public float phase;
    
    public int age;
    public double size;
    public double baseSize;
    public double pulse;
    
    public long lastUpdateNanos;
    
    public LivingNode(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.energy = 1.0f;
        
        this.dna = new LivingDNA();
        this.brain = new LogicBrain(8);
        this.signature = DNACloaker.generateIdentity(name);
        
        this.frequency = (float) dna.harmonicFrequency / 43.2f;
        this.phase = 0;
        this.age = 0;
        this.baseSize = 5.0 + rng.nextDouble() * 5.0;
        this.size = baseSize;
        this.pulse = 0;
        
        this.lastUpdateNanos = System.nanoTime();
    }
    
    public LivingNode(String name, float x, float y, LivingDNA dna, LogicBrain brain) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.energy = 1.0f;
        
        this.dna = dna;
        this.brain = brain;
        this.signature = DNACloaker.generateIdentity(name);
        
        this.frequency = (float) dna.harmonicFrequency / 43.2f;
        this.phase = 0;
        this.age = 0;
        this.baseSize = 5.0 + rng.nextDouble() * 5.0;
        this.size = baseSize;
        this.pulse = 0;
        
        this.lastUpdateNanos = System.nanoTime();
    }
    
    public void update(float dt, long nowNanos) {
        age++;
        
        double t = nowNanos * 1e-9;
        pulse = dna.pulse(t);
        size = Math.max(1.0, baseSize + pulse * 5.0);
        
        dna.evolve();
        frequency = (float) dna.harmonicFrequency / 43.2f;
        
        phase += frequency * dt * 2.0 * Math.PI;
        if (phase > 2 * Math.PI) phase -= 2 * Math.PI;
        
        x += vx * dt;
        y += vy * dt;
        
        lastUpdateNanos = nowNanos;
    }
    
    public int[] think(int[] inputs) {
        return brain.compute(inputs);
    }
    
    public boolean canReproduce() {
        return size > 15.0 && energy > 0.7f;
    }
    
    public LivingNode reproduce(LivingNode partner, String childName, float childX, float childY) {
        LivingDNA childDNA = dna.copy();
        LogicBrain childBrain = brain.crossover(partner != null ? partner.brain : brain);
        
        baseSize *= 0.6;
        energy *= 0.7f;
        
        return new LivingNode(childName, childX, childY, childDNA, childBrain);
    }
    
    public boolean isEntangledWith(LivingNode other) {
        float epsilonFreq = 1.0f;
        float epsilonPhase = 0.2f;
        return Math.abs(this.frequency - other.frequency) < epsilonFreq &&
               Math.abs(this.phase - other.phase) < epsilonPhase;
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
    
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("// Living Node: %s\n", name));
        sb.append(String.format("// Signature: %s\n", signature.toString(16).substring(0, 16)));
        sb.append(String.format("// Age: %d | Size: %.2f | Energy: %.1f%%\n", age, size, energy * 100));
        sb.append(String.format("new LivingNode(\n"));
        sb.append(String.format("    \"%s\",\n", name));
        sb.append(String.format("    %.2ff, %.2ff,\n", x, y));
        sb.append(String.format("    %s,\n", dna.toJavaCode()));
        sb.append(String.format("    new LogicBrain() {{ gates = java.util.Arrays.asList(\n"));
        for (int i = 0; i < brain.gates.size(); i++) {
            LogicGate g = brain.gates.get(i);
            sb.append(String.format("        new LogicGate(%d, %d, %d)", g.type, g.in1, g.in2));
            if (i < brain.gates.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("    ); }}\n)");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        String status = isAlive() ? "ALIVE" : "DEAD";
        return String.format("[LIVING %s] Pos:(%.2f,%.2f) %s %s Energy:%.1f%% Age:%d [%s]",
            name, x, y, dna, brain, energy * 100, age, status);
    }
}
