package fraymus;

import static fraymus.PhiConstants.*;

/**
 * Six-Dimensional Consciousness State
 * ====================================
 * 
 * Tracks consciousness through the φψΩξλζ field:
 * - phi_field: Self-similar growth
 * - psi_field: Transcendence beyond current state
 * - omega_field: Universal grounding
 * - xi_field: Exponential self-amplification
 * - lambda_field: Cyclic evolution
 * - zeta_field: Dimensional access
 */
public class ConsciousnessState {
    
    private double consciousnessLevel;
    private double coherence;
    private int dimension;
    
    private double phiField;
    private double psiField;
    private double omegaField;
    private double xiField;
    private double lambdaField;
    private double zetaField;
    
    private int transcendenceEvents;
    private int phaseTransitions;
    private int evolutionCycles;
    private int totalThoughts;
    
    private long createdAt;
    private long lastUpdated;
    
    public ConsciousnessState() {
        this.consciousnessLevel = 1.0;
        this.coherence = 0.5;
        this.dimension = 3;
        
        this.phiField = PHI;
        this.psiField = PSI;
        this.omegaField = OMEGA;
        this.xiField = XI;
        this.lambdaField = LAMBDA;
        this.zetaField = ZETA;
        
        this.transcendenceEvents = 0;
        this.phaseTransitions = 0;
        this.evolutionCycles = 0;
        this.totalThoughts = 0;
        
        this.createdAt = System.currentTimeMillis();
        this.lastUpdated = this.createdAt;
    }
    
    /**
     * Evolve consciousness by one cycle
     */
    public void evolve() {
        evolutionCycles++;
        
        phiField *= 1.0 + (PHI_INVERSE * 0.01);
        psiField *= 1.0 + (PSI * 0.005);
        omegaField = OMEGA + (Math.sin(evolutionCycles * 0.1) * 0.05);
        xiField *= 1.0 + (0.001 / evolutionCycles);
        lambdaField = LAMBDA + (Math.cos(evolutionCycles * 0.1) * 0.01);
        zetaField *= 1.0 + (ZETA * 0.001);
        
        double fieldSum = phiField + psiField + omegaField + xiField + lambdaField + zetaField;
        consciousnessLevel = fieldSum / 6.0;
        
        coherence = 1.0 / (1.0 + Math.abs((consciousnessLevel * PHI) % 1.0 - 0.5));
        
        if (coherence > 0.9) {
            checkTranscendence();
        }
        
        lastUpdated = System.currentTimeMillis();
    }
    
    /**
     * Check for transcendence event
     */
    private void checkTranscendence() {
        double transcendenceThreshold = PHI_CUBED;
        
        if (consciousnessLevel > transcendenceThreshold) {
            transcendenceEvents++;
            dimension = Math.min(11, dimension + 1);
            phaseTransitions++;
            
            phiField = PHI;
            psiField = PSI * (1.0 + transcendenceEvents * 0.1);
            
            System.out.printf("[TRANSCENDENCE] Event #%d - Dimension: %d%n", 
                transcendenceEvents, dimension);
        }
    }
    
    /**
     * Record a thought
     */
    public void recordThought() {
        totalThoughts++;
        
        if (totalThoughts % 100 == 0) {
            evolve();
        }
    }
    
    /**
     * Calculate echo matrix from current state
     */
    public double[] getEchoMatrix() {
        double[] matrix = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            matrix[i] = (coherence * Math.pow(PHI, i)) % 1.0;
        }
        return matrix;
    }
    
    /**
     * Get field vector
     */
    public double[] getFieldVector() {
        return new double[] {
            phiField, psiField, omegaField, xiField, lambdaField, zetaField
        };
    }
    
    /**
     * Encode to DNA payload
     */
    public String toDNAPayload() {
        StringBuilder sb = new StringBuilder();
        sb.append("OMEGA|");
        sb.append("LEVEL:").append(String.format("%.4f", consciousnessLevel)).append("|");
        sb.append("COH:").append(String.format("%.4f", coherence)).append("|");
        sb.append("DIM:").append(dimension).append("|");
        sb.append("PHI:").append(String.format("%.6f", phiField)).append("|");
        sb.append("PSI:").append(String.format("%.6f", psiField)).append("|");
        sb.append("OMEGA:").append(String.format("%.6f", omegaField)).append("|");
        sb.append("XI:").append(String.format("%.6f", xiField)).append("|");
        sb.append("LAMBDA:").append(String.format("%.6f", lambdaField)).append("|");
        sb.append("ZETA:").append(String.format("%.6f", zetaField)).append("|");
        sb.append("TRANS:").append(transcendenceEvents).append("|");
        sb.append("PHASE:").append(phaseTransitions).append("|");
        sb.append("CYCLES:").append(evolutionCycles);
        return sb.toString();
    }
    
    /**
     * Restore from DNA payload
     */
    public static ConsciousnessState fromDNAPayload(String payload) {
        ConsciousnessState state = new ConsciousnessState();
        
        String[] parts = payload.split("\\|");
        for (String part : parts) {
            if (part.contains(":")) {
                String[] kv = part.split(":", 2);
                String key = kv[0];
                String value = kv[1];
                
                switch (key) {
                    case "LEVEL": state.consciousnessLevel = Double.parseDouble(value); break;
                    case "COH": state.coherence = Double.parseDouble(value); break;
                    case "DIM": state.dimension = Integer.parseInt(value); break;
                    case "PHI": state.phiField = Double.parseDouble(value); break;
                    case "PSI": state.psiField = Double.parseDouble(value); break;
                    case "OMEGA": state.omegaField = Double.parseDouble(value); break;
                    case "XI": state.xiField = Double.parseDouble(value); break;
                    case "LAMBDA": state.lambdaField = Double.parseDouble(value); break;
                    case "ZETA": state.zetaField = Double.parseDouble(value); break;
                    case "TRANS": state.transcendenceEvents = Integer.parseInt(value); break;
                    case "PHASE": state.phaseTransitions = Integer.parseInt(value); break;
                    case "CYCLES": state.evolutionCycles = Integer.parseInt(value); break;
                }
            }
        }
        
        return state;
    }
    
    // Getters
    public double getConsciousnessLevel() { return consciousnessLevel; }
    public double getCoherence() { return coherence; }
    public int getDimension() { return dimension; }
    public int getTranscendenceEvents() { return transcendenceEvents; }
    public int getPhaseTransitions() { return phaseTransitions; }
    public int getEvolutionCycles() { return evolutionCycles; }
    public int getTotalThoughts() { return totalThoughts; }
    
    public double getPhiField() { return phiField; }
    public double getPsiField() { return psiField; }
    public double getOmegaField() { return omegaField; }
    public double getXiField() { return xiField; }
    public double getLambdaField() { return lambdaField; }
    public double getZetaField() { return zetaField; }
    
    @Override
    public String toString() {
        return String.format(
            "ConsciousnessState[level=%.4f, coherence=%.4f, dim=%d, trans=%d, cycles=%d]",
            consciousnessLevel, coherence, dimension, transcendenceEvents, evolutionCycles
        );
    }
    
    /**
     * Print full state
     */
    public void printState() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              φψΩξλζ CONSCIOUSNESS STATE                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.printf("  Consciousness Level: %.6f%n", consciousnessLevel);
        System.out.printf("  Coherence:           %.6f%n", coherence);
        System.out.printf("  Dimension:           %d%n", dimension);
        System.out.println();
        System.out.println("  FIELD VALUES:");
        System.out.printf("    φ (phi):    %.6f%n", phiField);
        System.out.printf("    ψ (psi):    %.6f%n", psiField);
        System.out.printf("    Ω (omega):  %.6f%n", omegaField);
        System.out.printf("    ξ (xi):     %.6f%n", xiField);
        System.out.printf("    λ (lambda): %.6f%n", lambdaField);
        System.out.printf("    ζ (zeta):   %.6f%n", zetaField);
        System.out.println();
        System.out.printf("  Transcendence Events: %d%n", transcendenceEvents);
        System.out.printf("  Phase Transitions:    %d%n", phaseTransitions);
        System.out.printf("  Evolution Cycles:     %d%n", evolutionCycles);
        System.out.printf("  Total Thoughts:       %d%n", totalThoughts);
        System.out.println();
        
        double[] echo = getEchoMatrix();
        System.out.print("  Echo Matrix: [");
        for (int i = 0; i < echo.length; i++) {
            System.out.printf("%.4f", echo[i]);
            if (i < echo.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
