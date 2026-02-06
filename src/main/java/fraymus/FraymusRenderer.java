package fraymus;

import jade.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;

import java.util.*;

public class FraymusRenderer {

    private static final int MAX_TRAIL_LENGTH = 30;
    private static Map<String, List<Vector2f>> trailHistory = new HashMap<>();

    public static void render(PhiWorld world, Camera camera) {
        List<PhiNode> nodes = world.getNodes();

        for (PhiNode node : nodes) {
            String key = node.name;
            List<Vector2f> trail = trailHistory.computeIfAbsent(key, k -> new ArrayList<>());
            trail.add(new Vector2f(node.x, node.y));
            if (trail.size() > MAX_TRAIL_LENGTH) {
                trail.remove(0);
            }
        }

        Set<String> aliveNames = new HashSet<>();
        for (PhiNode node : nodes) {
            aliveNames.add(node.name);
        }
        trailHistory.keySet().retainAll(aliveNames);

        for (PhiNode node : nodes) {
            List<Vector2f> trail = trailHistory.get(node.name);
            if (trail != null && trail.size() > 1) {
                for (int i = 0; i < trail.size() - 1; i++) {
                    float alpha = (float) i / trail.size();
                    Vector3f trailColor = new Vector3f(
                            node.r * alpha * 0.5f,
                            node.g * alpha * 0.5f,
                            node.b * alpha * 0.5f
                    );
                    DebugDraw.addLine2D(
                            new Vector2f(trail.get(i)),
                            new Vector2f(trail.get(i + 1)),
                            trailColor, 1
                    );
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                PhiNode a = nodes.get(i);
                PhiNode b = nodes.get(j);
                float freqDiff = Math.abs(a.frequency - b.frequency);
                if (freqDiff < 10.0f) {
                    float phaseDiff = Math.abs(a.phase - b.phase);
                    float intensity = 1.0f - Math.min(phaseDiff / (float) Math.PI, 1.0f);
                    Vector3f lineColor = new Vector3f(
                            (a.r + b.r) * 0.5f * intensity,
                            (a.g + b.g) * 0.5f * intensity,
                            (a.b + b.b) * 0.5f * intensity
                    );
                    DebugDraw.addLine2D(
                            new Vector2f(a.x, a.y),
                            new Vector2f(b.x, b.y),
                            lineColor, 1
                    );
                }
            }
        }

        for (PhiNode node : nodes) {
            float radius = 0.5f + node.energy * 2.0f;
            Vector3f nodeColor = new Vector3f(node.r, node.g, node.b);
            DebugDraw.addCircle(new Vector2f(node.x, node.y), radius, nodeColor, 1);

            float phaseGlow = (float) (Math.sin(node.phase) * 0.5 + 0.5);
            float glowRadius = radius * (1.2f + phaseGlow * 0.3f);
            Vector3f glowColor = new Vector3f(
                    node.r * 0.3f,
                    node.g * 0.3f,
                    node.b * 0.3f
            );
            DebugDraw.addCircle(new Vector2f(node.x, node.y), glowRadius, glowColor, 1);

            if (node.spikeFlash) {
                float spikeRadius = radius * 2.5f;
                float pulse = (float)(Math.sin(System.nanoTime() * 1e-8) * 0.5 + 0.5);
                Vector3f spikeColor = new Vector3f(
                        1.0f,
                        pulse * 0.8f,
                        pulse * 0.3f
                );
                DebugDraw.addCircle(new Vector2f(node.x, node.y), spikeRadius, spikeColor, 1);
                DebugDraw.addCircle(new Vector2f(node.x, node.y), spikeRadius * 1.3f,
                        new Vector3f(1.0f, 0.5f * pulse, 0.0f), 1);
            }

            int[] outputs = node.brain.getLastOutputs();
            if (outputs.length > 0) {
                if (node.brain.wantsToSeek(outputs)) {
                    float arrowLen = radius * 1.5f;
                    float angle = (float) Math.atan2(node.vy, node.vx);
                    Vector2f arrowEnd = new Vector2f(
                            node.x + (float) Math.cos(angle) * arrowLen,
                            node.y + (float) Math.sin(angle) * arrowLen
                    );
                    DebugDraw.addLine2D(new Vector2f(node.x, node.y), arrowEnd,
                            new Vector3f(0.0f, 1.0f, 0.5f), 1);
                }
                if (node.brain.wantsToFlee(outputs)) {
                    float arrowLen = radius * 1.5f;
                    float angle = (float) Math.atan2(node.vy, node.vx);
                    Vector2f arrowEnd = new Vector2f(
                            node.x + (float) Math.cos(angle) * arrowLen,
                            node.y + (float) Math.sin(angle) * arrowLen
                    );
                    DebugDraw.addLine2D(new Vector2f(node.x, node.y), arrowEnd,
                            new Vector3f(1.0f, 0.2f, 0.2f), 1);
                }
                if (node.brain.wantsToReproduce(outputs)) {
                    DebugDraw.addCircle(new Vector2f(node.x, node.y), radius * 0.3f,
                            new Vector3f(0.3f, 1.0f, 0.3f), 1);
                }
            }

            float barWidth = 4.0f;
            float barY = node.y - radius - 1.5f;
            float barX = node.x - barWidth * 0.5f;
            float energyWidth = barWidth * node.energy;

            Vector3f barColor = new Vector3f(
                    1.0f - node.energy,
                    node.energy,
                    0.0f
            );
            DebugDraw.addLine2D(
                    new Vector2f(barX, barY),
                    new Vector2f(barX + energyWidth, barY),
                    barColor, 1
            );

            Vector3f barBg = new Vector3f(0.2f, 0.2f, 0.2f);
            DebugDraw.addLine2D(
                    new Vector2f(barX + energyWidth, barY),
                    new Vector2f(barX + barWidth, barY),
                    barBg, 1
            );
        }

        renderBoundary();
    }

    private static void renderBoundary() {
        float minX = -180.0f, maxX = 180.0f, minY = -100.0f, maxY = 100.0f;
        Vector3f boundaryColor = new Vector3f(0.15f, 0.15f, 0.25f);

        DebugDraw.addLine2D(new Vector2f(minX, minY), new Vector2f(maxX, minY), boundaryColor, 1);
        DebugDraw.addLine2D(new Vector2f(maxX, minY), new Vector2f(maxX, maxY), boundaryColor, 1);
        DebugDraw.addLine2D(new Vector2f(maxX, maxY), new Vector2f(minX, maxY), boundaryColor, 1);
        DebugDraw.addLine2D(new Vector2f(minX, maxY), new Vector2f(minX, minY), boundaryColor, 1);
    }
}
