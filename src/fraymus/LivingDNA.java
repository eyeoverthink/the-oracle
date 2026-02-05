package fraymus;

import java.util.Random;

public class LivingDNA {
    private static final Random rng = new Random();
    
    public double harmonicFrequency;
    public double resonance;
    public double evolutionRate;
    
    public LivingDNA() {
        this.harmonicFrequency = 432.0 + rng.nextDouble() * 20.0;
        this.resonance = 0.5 + rng.nextDouble();
        this.evolutionRate = 0.05;
    }
    
    public LivingDNA(double freq, double res, double evo) {
        this.harmonicFrequency = freq;
        this.resonance = res;
        this.evolutionRate = evo;
    }
    
    public void evolve() {
        harmonicFrequency += evolutionRate;
        if (harmonicFrequency > 528.0) {
            harmonicFrequency = 432.0;
        }
    }
    
    public double pulse(double time) {
        return Math.sin(harmonicFrequency * time * 0.0001) * resonance;
    }
    
    public LivingDNA copy() {
        return new LivingDNA(harmonicFrequency, resonance, evolutionRate);
    }
    
    public String toJavaCode() {
        return String.format("new LivingDNA(%.3f, %.3f, %.3f)", 
            harmonicFrequency, resonance, evolutionRate);
    }
    
    @Override
    public String toString() {
        return String.format("DNA[freq=%.2fHz, res=%.2f, evo=%.3f]", 
            harmonicFrequency, resonance, evolutionRate);
    }
}
