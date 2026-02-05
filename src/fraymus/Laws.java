package fraymus;

public class Laws {

    public static class Inertia implements PhiLaw {
        @Override
        public void apply(PhiNode n, float dt) {
            n.x += n.vx * dt;
            n.y += n.vy * dt;
            n.z += n.vz * dt;
        }
    }

    public static class HarmonicResonance implements PhiLaw {
        @Override
        public void apply(PhiNode n, float dt) {
            n.phase += n.frequency * dt;
            if (n.phase > Math.PI * 2) n.phase -= (float)(Math.PI * 2);
        }
    }

    public static class ScottPredictionLaw implements PhiLaw {
        private float lookAheadTime;

        public ScottPredictionLaw(float lookAhead) {
            this.lookAheadTime = lookAhead;
        }

        @Override
        public void apply(PhiNode n, float dt) {
            float futureX = n.x + (n.vx * lookAheadTime);
            float futureY = n.y + (n.vy * lookAheadTime);
            
            if (Math.abs(n.vx) > 0.5 || Math.abs(n.vy) > 0.5) {
                System.out.printf("  [SCOTT-4D] %s -> Future(%.2f, %.2f) in %.1fs%n",
                   n.dnaSeed, futureX, futureY, lookAheadTime);
            }
        }
    }

    public static class EntanglementLaw implements PhiLaw {
        private final float epsFreq = 0.5f;
        private final float kPhase = 2.0f;

        @Override
        public boolean isPairwise() { return true; }

        @Override
        public void apply(PhiNode n, float dt) {}

        @Override
        public void applyPair(PhiNode a, PhiNode b, float dt) {
            if (Math.abs(a.frequency - b.frequency) > epsFreq) return;

            float d = wrapPhase(a.phase - b.phase);
            
            float correction = -kPhase * d;
            
            a.phase += correction * dt;
            b.phase -= correction * dt;
            
            a.energy = Math.min(1.0f, a.energy + 0.05f * dt);
            b.energy = Math.min(1.0f, b.energy + 0.05f * dt);
            
            System.out.printf("  [ENTANGLE] %s <-> %s (phase sync: %.3f)%n", 
                a.dnaSeed, b.dnaSeed, Math.abs(d));
        }

        private float wrapPhase(float p) {
            while (p > Math.PI) p -= (float)(2 * Math.PI);
            while (p < -Math.PI) p += (float)(2 * Math.PI);
            return p;
        }
    }
}
