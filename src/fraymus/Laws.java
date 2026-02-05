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
            if (n.phase > Math.PI * 2) n.phase -= Math.PI * 2;
            
            float phi = 1.618f;
            n.energy = 0.9f + 0.1f * (float)Math.sin(n.phase * phi);
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
            
            if (Math.abs(n.vx) > 0.1 || Math.abs(n.vy) > 0.1) {
                System.out.printf("  >> PREDICTION [%s]: Will be at (%.2f, %.2f) in %.2fs%n",
                   n.dnaSeed, futureX, futureY, lookAheadTime);
            }
        }
    }
}
