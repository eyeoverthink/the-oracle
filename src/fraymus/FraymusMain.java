package fraymus;

public class FraymusMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║     FRAYMUS PHYSICS ENGINE - Kinetic Data Simulation         ║");
        System.out.println("║          Deterministic Physics for Information               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        PhiWorld world = new PhiWorld();
        
        world.addLaw(new Laws.Inertia());
        world.addLaw(new Laws.HarmonicResonance());
        world.addLaw(new Laws.ScottPredictionLaw(1.0f));
        
        PhiNode alpha = new PhiNode(0, 0, 10.0f, "ALPHA_PRIME");
        alpha.vx = 1.5f;
        System.out.println(">> Created ALPHA_PRIME - Signature: " + alpha.signature.toString().substring(0, 20) + "...");
        
        PhiNode beta = new PhiNode(10, 0, 10.005f, "BETA_RESONANT");
        beta.vx = -1.5f;
        System.out.println(">> Created BETA_RESONANT - Signature: " + beta.signature.toString().substring(0, 20) + "...");
        
        PhiNode gamma = new PhiNode(100, 100, 50.0f, "GAMMA_NOISE");
        System.out.println(">> Created GAMMA_NOISE - Signature: " + gamma.signature.toString().substring(0, 20) + "...");
        
        world.addNode(alpha);
        world.addNode(beta);
        world.addNode(gamma);
        
        System.out.println();
        System.out.println(">> WORLD LOADED. NODES: " + world.getNodes().size());
        System.out.println(">> LAWS: Inertia, HarmonicResonance, Scott4D Prediction (1.0s lookahead)");
        System.out.println(">> Starting 60 FPS simulation for 10 seconds...");
        System.out.println();
        
        long lastTime = System.nanoTime();
        float frameTime = 1.0f / 60.0f;
        int tick = 0;
        
        for (int i = 0; i < 600; i++) {
            long now = System.nanoTime();
            float dt = (now - lastTime) / 1_000_000_000f;
            
            if (dt >= frameTime) {
                world.step(dt);
                
                if (tick % 60 == 0) {
                    int seconds = tick / 60;
                    System.out.println("═══════════════════════════════════════════════════════");
                    System.out.printf("  TICK %d (t = %d seconds)%n", tick, seconds);
                    System.out.println("═══════════════════════════════════════════════════════");
                    for (PhiNode n : world.getNodes()) {
                        System.out.println("  " + n.toString());
                    }
                    System.out.println();
                }
                
                lastTime = now;
                tick++;
            }
            Thread.sleep(1);
        }
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              SIMULATION COMPLETE - STATE PRESERVED           ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
}
