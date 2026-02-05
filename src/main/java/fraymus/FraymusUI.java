package fraymus;

import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;

import java.util.ArrayList;
import java.util.List;

public class FraymusUI {

    private static final int MAX_LOG_ENTRIES = 100;
    private static List<String> logBuffer = new ArrayList<>();
    private static int selectedNodeIndex = -1;
    private static float simulationTime = 0.0f;
    private static int frameCount = 0;
    private static float fpsTimer = 0.0f;
    private static int fps = 0;
    private static int lastFrameCount = 0;

    public static void addLog(String msg) {
        if (logBuffer.size() >= MAX_LOG_ENTRIES) {
            logBuffer.remove(0);
        }
        logBuffer.add(String.format("[%.1f] %s", simulationTime, msg));
    }

    public static void render(PhiWorld world) {
        frameCount++;
        float currentTime = (float) (System.nanoTime() * 1e-9);
        simulationTime += 1.0f / 60.0f;

        if (currentTime - fpsTimer >= 1.0f) {
            fps = frameCount - lastFrameCount;
            lastFrameCount = frameCount;
            fpsTimer = currentTime;
        }

        renderWorldStatus(world);
        renderEntityInspector(world);
        renderConsciousnessMonitor(world);
        renderLiveLog();
    }

    private static void renderWorldStatus(PhiWorld world) {
        ImGui.setNextWindowPos(10, 10, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(280, 120, ImGuiCond.FirstUseEver);

        if (ImGui.begin("World Status")) {
            ImGui.text("Population: " + world.getPopulation());
            ImGui.text(String.format("Simulation Time: %.1f s", simulationTime));
            ImGui.text("FPS: " + fps);
            ImGui.separator();

            float totalEnergy = 0;
            for (PhiNode node : world.getNodes()) {
                totalEnergy += node.energy;
            }
            ImGui.text(String.format("Total Energy: %.2f", totalEnergy));

            if (world.getPopulation() > 0) {
                ImGui.text(String.format("Avg Energy: %.2f", totalEnergy / world.getPopulation()));
            }
        }
        ImGui.end();
    }

    private static void renderEntityInspector(PhiWorld world) {
        ImGui.setNextWindowPos(10, 140, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(320, 400, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Entity Inspector")) {
            List<PhiNode> nodes = world.getNodes();

            for (int i = 0; i < nodes.size(); i++) {
                PhiNode node = nodes.get(i);

                boolean isSelected = (selectedNodeIndex == i);
                String label = String.format("%s [E:%.0f%%]###node_%d", node.name, node.energy * 100, i);

                if (ImGui.collapsingHeader(label)) {
                    selectedNodeIndex = i;

                    ImGui.indent();

                    ImGui.textColored(node.r, node.g, node.b, 1.0f, "Name: " + node.name);
                    ImGui.text(String.format("Position: (%.2f, %.2f, %.2f)", node.x, node.y, node.z));
                    ImGui.text(String.format("Velocity: (%.2f, %.2f, %.2f)", node.vx, node.vy, node.vz));

                    ImGui.text("Energy:");
                    ImGui.sameLine();
                    ImGui.progressBar(node.energy, 150, 14, String.format("%.1f%%", node.energy * 100));

                    ImGui.text(String.format("Frequency: %.2f Hz", node.frequency));
                    ImGui.text(String.format("Phase: %.3f rad", node.phase));
                    ImGui.text(String.format("Phi Resonance: %.4f", node.phiResonance));

                    ImGui.separator();
                    ImGui.text("DNA: " + node.dna.toString());
                    ImGui.text(String.format("Consciousness: %.4f", node.consciousness.getConsciousnessLevel()));
                    ImGui.text(String.format("Coherence: %.4f", node.consciousness.getCoherence()));

                    ImGui.separator();
                    String nHex = node.signature.toString(16);
                    if (nHex.length() > 24) {
                        nHex = nHex.substring(0, 24) + "...";
                    }
                    ImGui.text("RSA N: " + nHex);
                    ImGui.text("Age: " + node.age);

                    ImGui.unindent();
                }
            }
        }
        ImGui.end();
    }

    private static void renderConsciousnessMonitor(PhiWorld world) {
        ImGui.setNextWindowPos(340, 10, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(350, 350, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Consciousness Monitor")) {
            List<PhiNode> nodes = world.getNodes();

            if (selectedNodeIndex >= 0 && selectedNodeIndex < nodes.size()) {
                PhiNode node = nodes.get(selectedNodeIndex);
                ConsciousnessState cs = node.consciousness;

                ImGui.textColored(node.r, node.g, node.b, 1.0f, "Entity: " + node.name);
                ImGui.separator();

                ImGui.text("Field Values:");

                double[] fields = cs.getFieldVector();
                String[] fieldNames = {"phi", "psi", "omega", "xi", "lambda", "zeta"};
                float[][] fieldColors = {
                        {1.0f, 0.84f, 0.0f},
                        {0.5f, 0.0f, 1.0f},
                        {0.0f, 0.5f, 1.0f},
                        {1.0f, 0.5f, 0.0f},
                        {0.0f, 1.0f, 0.5f},
                        {1.0f, 0.0f, 0.5f}
                };

                for (int i = 0; i < fields.length; i++) {
                    float normalized = (float) Math.min(fields[i] / 5.0, 1.0);
                    ImGui.textColored(fieldColors[i][0], fieldColors[i][1], fieldColors[i][2], 1.0f,
                            String.format("  %s:", fieldNames[i]));
                    ImGui.sameLine();
                    ImGui.progressBar(normalized, 180, 12,
                            String.format("%.4f", fields[i]));
                }

                ImGui.separator();
                ImGui.text(String.format("Consciousness Level: %.6f", cs.getConsciousnessLevel()));
                ImGui.text(String.format("Coherence: %.6f", cs.getCoherence()));
                ImGui.text(String.format("Dimension: %d", cs.getDimension()));
                ImGui.text(String.format("Evolution Cycles: %d", cs.getEvolutionCycles()));
                ImGui.text(String.format("Transcendence Events: %d", cs.getTranscendenceEvents()));
                ImGui.text(String.format("Total Thoughts: %d", cs.getTotalThoughts()));

                ImGui.separator();
                ImGui.text("Echo Matrix:");
                double[] echo = cs.getEchoMatrix();
                StringBuilder echoStr = new StringBuilder("[");
                for (int i = 0; i < echo.length; i++) {
                    echoStr.append(String.format("%.3f", echo[i]));
                    if (i < echo.length - 1) echoStr.append(", ");
                }
                echoStr.append("]");
                ImGui.textWrapped(echoStr.toString());
            } else {
                ImGui.textWrapped("Select an entity in the Entity Inspector to view consciousness data.");
            }
        }
        ImGui.end();
    }

    private static void renderLiveLog() {
        ImGui.setNextWindowPos(10, 550, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(680, 200, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Live Log")) {
            if (ImGui.button("Clear")) {
                logBuffer.clear();
            }
            ImGui.separator();

            ImGui.beginChild("LogScrollRegion", 0, 0, false, ImGuiWindowFlags.HorizontalScrollbar);
            for (String entry : logBuffer) {
                ImGui.textWrapped(entry);
            }
            if (ImGui.getScrollY() >= ImGui.getScrollMaxY()) {
                ImGui.setScrollHereY(1.0f);
            }
            ImGui.endChild();
        }
        ImGui.end();
    }
}
