package fraymus;

import imgui.ImGui;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;

import java.util.ArrayList;
import java.util.List;

public class FraymusUI {

    private static final int MAX_LOG_ENTRIES = 200;
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
        renderBrainInspector(world);
        renderConsciousnessMonitor(world);
        renderQuantumClockPanel(world);
        renderGenesisMemory(world);
        renderLiveLog();
    }

    private static void renderWorldStatus(PhiWorld world) {
        ImGui.setNextWindowPos(10, 10, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(280, 180, ImGuiCond.FirstUseEver);

        if (ImGui.begin("World Status")) {
            ImGui.text("Population: " + world.getPopulation());
            ImGui.text(String.format("Simulation Time: %.1f s", simulationTime));
            ImGui.text("FPS: " + fps);
            ImGui.text("World Tick: " + world.getWorldTick());
            ImGui.separator();

            float totalEnergy = 0;
            int spikeCount = 0;
            for (PhiNode node : world.getNodes()) {
                totalEnergy += node.energy;
                if (node.spikeFlash) spikeCount++;
            }
            ImGui.text(String.format("Total Energy: %.2f", totalEnergy));
            if (world.getPopulation() > 0) {
                ImGui.text(String.format("Avg Energy: %.2f", totalEnergy / world.getPopulation()));
            }
            ImGui.text(String.format("Active Spikes: %d", spikeCount));
            ImGui.separator();
            ImGui.text(String.format("Total Births: %d", world.getTotalBirths()));
            ImGui.text(String.format("Total Deaths: %d", world.getTotalDeaths()));
            ImGui.text(String.format("Genesis Chain: %d blocks", world.getMemory().getChainLength()));
        }
        ImGui.end();
    }

    private static void renderEntityInspector(PhiWorld world) {
        ImGui.setNextWindowPos(10, 200, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(320, 350, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Entity Inspector")) {
            List<PhiNode> nodes = world.getNodes();

            for (int i = 0; i < nodes.size(); i++) {
                PhiNode node = nodes.get(i);
                String spike = node.spikeFlash ? " [SPIKE]" : "";
                String label = String.format("%s [E:%.0f%%]%s###node_%d", node.name, node.energy * 100, spike, i);

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
                    ImGui.text(String.format("Age: %d ticks", node.age));

                    ImGui.separator();
                    ImGui.text("DNA: " + node.dna.toString());

                    String nHex = node.signature.toString(16);
                    if (nHex.length() > 24) nHex = nHex.substring(0, 24) + "...";
                    ImGui.text("RSA N: " + nHex);

                    ImGui.unindent();
                }
            }
        }
        ImGui.end();
    }

    private static void renderBrainInspector(PhiWorld world) {
        ImGui.setNextWindowPos(340, 10, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(350, 300, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Brain Inspector")) {
            List<PhiNode> nodes = world.getNodes();

            if (selectedNodeIndex >= 0 && selectedNodeIndex < nodes.size()) {
                PhiNode node = nodes.get(selectedNodeIndex);
                LogicBrain brain = node.brain;

                ImGui.textColored(node.r, node.g, node.b, 1.0f, "Entity: " + node.name);
                ImGui.text(String.format("Think Count: %d", brain.getThinkCount()));
                ImGui.text("Decision: " + brain.getLastDecision());
                ImGui.separator();

                ImGui.text("Logic Gates:");
                int[] outputs = brain.getLastOutputs();
                for (int i = 0; i < brain.gates.size() && i < LogicBrain.OUTPUT_NAMES.length; i++) {
                    LogicGate gate = brain.gates.get(i);
                    boolean active = i < outputs.length && outputs[i] == 1;

                    if (active) {
                        ImGui.textColored(0.0f, 1.0f, 0.3f, 1.0f,
                                String.format("  [%d] %s(in%d,in%d) -> %s = ON",
                                        i, gate.getTypeName(), gate.in1, gate.in2, LogicBrain.OUTPUT_NAMES[i]));
                    } else {
                        ImGui.textColored(0.5f, 0.5f, 0.5f, 1.0f,
                                String.format("  [%d] %s(in%d,in%d) -> %s = off",
                                        i, gate.getTypeName(), gate.in1, gate.in2, LogicBrain.OUTPUT_NAMES[i]));
                    }
                }

                ImGui.separator();
                ImGui.text("Sensor Inputs:");
                int[] inputs = brain.getLastInputs();
                String[] sensorNames = {"Crowded", "FreqMatch", "HighEnergy", "HighPhi", "HighCoh", "PhaseHalf", "Spike", "Mature"};
                for (int i = 0; i < inputs.length && i < sensorNames.length; i++) {
                    if (inputs[i] == 1) {
                        ImGui.textColored(1.0f, 1.0f, 0.0f, 1.0f, String.format("  %s: YES", sensorNames[i]));
                    } else {
                        ImGui.textColored(0.4f, 0.4f, 0.4f, 1.0f, String.format("  %s: no", sensorNames[i]));
                    }
                }
            } else {
                ImGui.textWrapped("Select an entity in the Entity Inspector to view brain data.");
            }
        }
        ImGui.end();
    }

    private static void renderConsciousnessMonitor(PhiWorld world) {
        ImGui.setNextWindowPos(700, 10, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(300, 300, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Consciousness")) {
            List<PhiNode> nodes = world.getNodes();

            if (selectedNodeIndex >= 0 && selectedNodeIndex < nodes.size()) {
                PhiNode node = nodes.get(selectedNodeIndex);
                ConsciousnessState cs = node.consciousness;

                ImGui.textColored(node.r, node.g, node.b, 1.0f, "Entity: " + node.name);
                ImGui.separator();

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
                    ImGui.progressBar(normalized, 150, 12, String.format("%.4f", fields[i]));
                }

                ImGui.separator();
                ImGui.text(String.format("Level: %.6f", cs.getConsciousnessLevel()));
                ImGui.text(String.format("Coherence: %.6f", cs.getCoherence()));
                ImGui.text(String.format("Dimension: %d", cs.getDimension()));
                ImGui.text(String.format("Transcendence: %d", cs.getTranscendenceEvents()));
                ImGui.text(String.format("Thoughts: %d", cs.getTotalThoughts()));
            } else {
                ImGui.textWrapped("Select an entity to view consciousness.");
            }
        }
        ImGui.end();
    }

    private static void renderQuantumClockPanel(PhiWorld world) {
        ImGui.setNextWindowPos(700, 320, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(300, 200, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Quantum Clock")) {
            List<PhiNode> nodes = world.getNodes();

            if (selectedNodeIndex >= 0 && selectedNodeIndex < nodes.size()) {
                PhiNode node = nodes.get(selectedNodeIndex);
                QuantumClock clock = node.quantumClock;

                ImGui.textColored(node.r, node.g, node.b, 1.0f, "Entity: " + node.name);
                ImGui.separator();

                ImGui.text(String.format("Oscillations: %,.1f", clock.getOscillationCount()));
                ImGui.text(String.format("Pendulum Freq: %.2f Hz", clock.getPendulumFrequency()));

                ImGui.text("Phi Resonance:");
                ImGui.sameLine();
                float phiRes = (float) clock.getPhiResonance();
                if (phiRes > 0.95f) {
                    ImGui.textColored(1.0f, 0.0f, 0.0f, 1.0f, String.format("%.6f [SPIKE!]", phiRes));
                } else if (phiRes > 0.8f) {
                    ImGui.textColored(1.0f, 1.0f, 0.0f, 1.0f, String.format("%.6f", phiRes));
                } else {
                    ImGui.text(String.format("%.6f", phiRes));
                }

                ImGui.text(String.format("Phi Time: %.4f", clock.getPhiTime()));
                ImGui.text(String.format("Resonance Time: %.4f", clock.getResonanceTime()));
                ImGui.text(String.format("Coherence: %.6f", clock.getCoherence()));
                ImGui.text(String.format("Spike Count: %d", clock.getResonanceSpikeCount()));
                ImGui.text("Fingerprint: " + clock.getQuantumFingerprint());
            } else {
                ImGui.textWrapped("Select an entity to view quantum clock.");
            }
        }
        ImGui.end();
    }

    private static void renderGenesisMemory(PhiWorld world) {
        ImGui.setNextWindowPos(340, 320, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(350, 200, ImGuiCond.FirstUseEver);

        if (ImGui.begin("Genesis Memory")) {
            GenesisMemory memory = world.getMemory();

            ImGui.text(String.format("Chain Length: %d blocks", memory.getChainLength()));
            boolean valid = memory.verifyChain();
            if (valid) {
                ImGui.textColored(0.0f, 1.0f, 0.0f, 1.0f, "Chain Integrity: VALID");
            } else {
                ImGui.textColored(1.0f, 0.0f, 0.0f, 1.0f, "Chain Integrity: BROKEN");
            }
            ImGui.separator();

            ImGui.beginChild("GenesisScroll", 0, 0, false, ImGuiWindowFlags.HorizontalScrollbar);
            List<GenesisMemory.Block> recent = memory.getLastN(30);
            for (int i = recent.size() - 1; i >= 0; i--) {
                GenesisMemory.Block block = recent.get(i);
                float[] color = getEventColor(block.eventType);
                ImGui.textColored(color[0], color[1], color[2], 1.0f,
                        String.format("[%d] %s: %s", block.index, block.eventType, block.data));
                ImGui.sameLine();
                ImGui.textColored(0.4f, 0.4f, 0.4f, 1.0f, block.hash.substring(0, 8));
            }
            ImGui.endChild();
        }
        ImGui.end();
    }

    private static float[] getEventColor(String eventType) {
        switch (eventType) {
            case "RESONANCE_SPIKE": return new float[]{1.0f, 0.3f, 0.3f};
            case "ENTANGLEMENT": return new float[]{0.3f, 0.8f, 1.0f};
            case "TRANSCENDENCE": return new float[]{1.0f, 0.84f, 0.0f};
            case "BIRTH": return new float[]{0.3f, 1.0f, 0.3f};
            case "DEATH": return new float[]{0.7f, 0.0f, 0.0f};
            case "BRAIN_DECISION": return new float[]{0.8f, 0.5f, 1.0f};
            case "MUTATION": return new float[]{1.0f, 0.5f, 0.0f};
            case "GENESIS": return new float[]{1.0f, 1.0f, 1.0f};
            default: return new float[]{0.7f, 0.7f, 0.7f};
        }
    }

    private static void renderLiveLog() {
        ImGui.setNextWindowPos(10, 560, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(680, 160, ImGuiCond.FirstUseEver);

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
