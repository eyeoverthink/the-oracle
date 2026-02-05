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
        private int tickCounter = 0;

        public ScottPredictionLaw(float lookAhead) {
            this.lookAheadTime = lookAhead;
        }

        @Override
        public void apply(PhiNode n, float dt) {
            float futureX = n.x + (n.vx * lookAheadTime);
            float futureY = n.y + (n.vy * lookAheadTime);

            tickCounter++;
            if ((Math.abs(n.vx) > 0.5 || Math.abs(n.vy) > 0.5) && tickCounter % 120 == 0) {
                FraymusUI.addLog(String.format("[SCOTT-4D] %s -> Future(%.1f, %.1f)", n.name, futureX, futureY));
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

        private int pairTick = 0;

        @Override
        public void applyPair(PhiNode a, PhiNode b, float dt) {
            if (Math.abs(a.frequency - b.frequency) > epsFreq) return;

            float d = wrapPhase(a.phase - b.phase);

            float correction = -kPhase * d;

            a.phase += correction * dt;
            b.phase -= correction * dt;

            a.energy = Math.min(1.0f, a.energy + 0.05f * dt);
            b.energy = Math.min(1.0f, b.energy + 0.05f * dt);

            pairTick++;
            if (pairTick % 180 == 0) {
                FraymusUI.addLog(String.format("[ENTANGLE] %s <-> %s (sync: %.3f)", a.name, b.name, Math.abs(d)));
            }
        }

        private float wrapPhase(float p) {
            while (p > Math.PI) p -= (float)(2 * Math.PI);
            while (p < -Math.PI) p += (float)(2 * Math.PI);
            return p;
        }
    }
}
