package fraymus;

public class FraymusMain {
    
    private static final int TARGET_FPS = 60;
    private static final float TIME_STEP = 1.0f / TARGET_FPS;
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║        FRAYMUS ENGINE V2.0 - Living Information Physics      ║");
        System.out.println("║              Deterministic | Kinetic | Entangled             ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        PhiWorld world = new PhiWorld();
        
        world.addLaw(new Laws.Inertia());
        world.addLaw(new Laws.HarmonicResonance());
        world.addLaw(new Laws.ScottPredictionLaw(1.0f));
        world.addLaw(new Laws.EntanglementLaw());
        
        PhiNode alpha = new PhiNode(0, 0, 10.0f, "ALPHA_PRIME");
        alpha.vx = 2.0f;
        System.out.println(">> Genesis: " + alpha.dnaSeed + " [Freq: " + alpha.frequency + "]");
        System.out.println("   Signature: " + alpha.signature.toString().substring(0, 24) + "...");
        
        PhiNode beta = new PhiNode(10, 0, 10.1f, "BETA_RESONANT");
        beta.vx = -1.0f;
        System.out.println(">> Genesis: " + beta.dnaSeed + " [Freq: " + beta.frequency + "]");
        System.out.println("   Signature: " + beta.signature.toString().substring(0, 24) + "...");
        
        PhiNode gamma = new PhiNode(100, 100, 50.0f, "GAMMA_NOISE");
        gamma.vx = 0.5f;
        System.out.println(">> Genesis: " + gamma.dnaSeed + " [Freq: " + gamma.frequency + "]");
        System.out.println("   Signature: " + gamma.signature.toString().substring(0, 24) + "...");
        
        world.addNode(alpha);
        world.addNode(beta);
        world.addNode(gamma);
        
        System.out.println();
        System.out.println(">> LAWS: Inertia | HarmonicResonance | Scott4D | Entanglement");
        System.out.println(">> MODE: Accumulator Loop @ " + TARGET_FPS + " FPS");
        System.out.println(">> PREDICTION: Alpha & Beta will entangle (freq diff < 0.5)");
        System.out.println(">> PREDICTION: Gamma will decay alone (freq diff > 0.5)");
        System.out.println();
        
        long prevTime = System.nanoTime();
        double accumulator = 0.0;
        long frameCount = 0;
        
        while (!world.getNodes().isEmpty()) {
            long now = System.nanoTime();
            double frameTime = (now - prevTime) / 1_000_000_000.0;
            prevTime = now;
            
            if (frameTime > 0.25) frameTime = 0.25;
            
            accumulator += frameTime;
            
            while (accumulator >= TIME_STEP) {
                world.step(TIME_STEP, now);
                accumulator -= TIME_STEP;
                frameCount++;
                
                if (frameCount % TARGET_FPS == 0) {
                    printDashboard(world, frameCount / TARGET_FPS);
                }
            }
            
            try { Thread.sleep(1); } catch (InterruptedException e) {}
            
            if (frameCount > TARGET_FPS * 15) break;
        }
        
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    SIMULATION COMPLETE                       ║");
        System.out.println("║   Result: Entangled nodes survive, isolated nodes decay      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
    
    private static void printDashboard(PhiWorld world, long seconds) {
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.printf("  TIME: %ds | POPULATION: %d%n", seconds, world.getPopulation());
        System.out.println("═══════════════════════════════════════════════════════════════");
        for (PhiNode n : world.getNodes()) {
            System.out.println("  " + n.toString());
        }
    }
}
