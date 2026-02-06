package fraymus;

import java.math.BigInteger;
import java.util.List;

public class ExperimentManager {

    private final PhiWorld world;
    private float gravityForce = 0.0f;
    private float speedMultiplier = 1.0f;
    private boolean boundaryEnabled = true;

    public ExperimentManager(PhiWorld world) {
        this.world = world;
    }

    public void runPrimeTest(String args) {
        if (args.isEmpty()) {
            CommandTerminal.printError("Usage: prime <number>");
            return;
        }
        try {
            long n = Long.parseLong(args.trim());
            boolean result = QuantumTunneler.isPrime(n);
            if (result) {
                CommandTerminal.printSuccess(String.format("%d is PRIME", n));
            } else {
                CommandTerminal.printColored(String.format("%d is NOT prime", n), 1.0f, 0.5f, 0.0f);
            }
        } catch (NumberFormatException e) {
            try {
                BigInteger big = new BigInteger(args.trim());
                boolean result = QuantumTunneler.isPrimeBig(big);
                if (result) {
                    CommandTerminal.printSuccess(big + " is PROBABLY PRIME (Miller-Rabin)");
                } else {
                    CommandTerminal.printColored(big + " is NOT prime", 1.0f, 0.5f, 0.0f);
                }
            } catch (NumberFormatException e2) {
                CommandTerminal.printError("Invalid number: " + args);
            }
        }
    }

    public void runFactor(String args) {
        if (args.isEmpty()) {
            CommandTerminal.printError("Usage: factor <number>");
            return;
        }
        try {
            long n = Long.parseLong(args.trim());
            if (n < 2) {
                CommandTerminal.printError("Number must be >= 2");
                return;
            }

            List<PhiNode> nodes = world.getNodes();
            PhiNode circuit = QuantumTunneler.selectBestCircuit(nodes);

            CommandTerminal.printHighlight(String.format("Quantum Tunneling %d using circuit %s...",
                    n, circuit != null ? circuit.name : "none"));

            QuantumTunneler.TunnelResult result = QuantumTunneler.quantumTunnel(n, circuit);

            if (result.success) {
                CommandTerminal.printSuccess(String.format("TUNNELED in %d iterations (%.3f ms)",
                        result.iterations, result.elapsedNanos / 1_000_000.0));
                CommandTerminal.printSuccess(String.format("  %d = %s x %s",
                        n, result.factorP, result.factorQ));
                CommandTerminal.print(String.format("  Verification: %s x %s = %s",
                        result.factorP, result.factorQ, result.factorP.multiply(result.factorQ)));
                CommandTerminal.print(String.format("  Circuit: %s", result.circuitName));

                if (world.getMemory() != null) {
                    world.getMemory().record("QUANTUM_TUNNEL",
                            String.format("factor|%d|%s*%s|iters=%d|circuit=%s",
                                    n, result.factorP, result.factorQ, result.iterations, result.circuitName));
                }
                FraymusUI.addLog(String.format("[TUNNEL] %d = %s x %s (%d iters)",
                        n, result.factorP, result.factorQ, result.iterations));
            } else {
                CommandTerminal.printError(String.format("Tunneling failed after %d iterations", result.iterations));
                if (QuantumTunneler.isPrime(n)) {
                    CommandTerminal.printInfo("  (Number is prime - cannot be factored)");
                }
            }
        } catch (NumberFormatException e) {
            try {
                BigInteger big = new BigInteger(args.trim());
                List<PhiNode> nodes = world.getNodes();
                PhiNode circuit = QuantumTunneler.selectBestCircuit(nodes);

                CommandTerminal.printHighlight(String.format("Quantum Tunneling %s... using circuit %s",
                        big.toString().substring(0, Math.min(20, big.toString().length())),
                        circuit != null ? circuit.name : "none"));

                QuantumTunneler.TunnelResult result = QuantumTunneler.quantumTunnelBig(big, circuit);

                if (result.success) {
                    CommandTerminal.printSuccess(String.format("TUNNELED in %d iterations (%.3f ms)",
                            result.iterations, result.elapsedNanos / 1_000_000.0));
                    CommandTerminal.printSuccess(String.format("  = %s x %s", result.factorP, result.factorQ));
                } else {
                    CommandTerminal.printError(String.format("Tunneling failed after %d iterations", result.iterations));
                }
            } catch (NumberFormatException e2) {
                CommandTerminal.printError("Invalid number: " + args);
            }
        }
    }

    public void runQuantumTunnel(String args) {
        int bits = 32;
        if (!args.isEmpty()) {
            try {
                bits = Integer.parseInt(args.trim());
                if (bits < 8) bits = 8;
                if (bits > 128) {
                    CommandTerminal.printInfo("Capping at 128 bits for performance");
                    bits = 128;
                }
            } catch (NumberFormatException e) {
                CommandTerminal.printError("Usage: tunnel <bits>");
                return;
            }
        }

        BigInteger n = QuantumTunneler.generateSemiprime(bits);
        CommandTerminal.printHighlight(String.format("Generated %d-bit semiprime:", bits));
        String nStr = n.toString();
        if (nStr.length() > 40) nStr = nStr.substring(0, 40) + "...";
        CommandTerminal.print("  N = " + nStr);

        List<PhiNode> nodes = world.getNodes();
        CommandTerminal.printInfo(String.format("Deploying %d living circuits for quantum tunneling...", nodes.size()));

        PhiNode bestCircuit = QuantumTunneler.selectBestCircuit(nodes);
        if (bestCircuit != null) {
            CommandTerminal.print(String.format("  Lead circuit: %s [%s] freq=%.1fHz energy=%.0f%%",
                    bestCircuit.name, bestCircuit.getRole().displayName,
                    bestCircuit.frequency, bestCircuit.energy * 100));
        }

        QuantumTunneler.TunnelResult result;
        if (bits <= 64) {
            result = QuantumTunneler.quantumTunnel(n.longValue(), bestCircuit);
        } else {
            result = QuantumTunneler.quantumTunnelBig(n, bestCircuit);
        }

        if (result.success) {
            CommandTerminal.printSuccess(String.format("QUANTUM TUNNEL SUCCESS in %d iterations (%.3f ms)",
                    result.iterations, result.elapsedNanos / 1_000_000.0));
            CommandTerminal.printSuccess(String.format("  p = %s", result.factorP));
            CommandTerminal.printSuccess(String.format("  q = %s", result.factorQ));
            CommandTerminal.print(String.format("  Verification: p * q = %s  Match: %s",
                    result.factorP.multiply(result.factorQ),
                    result.factorP.multiply(result.factorQ).equals(n) ? "YES" : "NO"));

            if (bestCircuit != null) {
                bestCircuit.boostEnergy(0.1f);
                CommandTerminal.printInfo(String.format("  Circuit %s rewarded (+10%% energy)", bestCircuit.name));
            }

            if (world.getMemory() != null) {
                world.getMemory().record("QUANTUM_TUNNEL",
                        String.format("semiprime|%dbits|iters=%d|circuit=%s",
                                bits, result.iterations, result.circuitName));
            }
            FraymusUI.addLog(String.format("[TUNNEL] %d-bit semiprime cracked in %d iters by %s",
                    bits, result.iterations, result.circuitName));
        } else {
            CommandTerminal.printError(String.format("Tunneling failed after %d iterations (%.3f ms)",
                    result.iterations, result.elapsedNanos / 1_000_000.0));
            CommandTerminal.printInfo("  Try smaller bit size or wait for circuits to evolve");
        }
    }

    public void runHash(String args) {
        if (args.isEmpty()) {
            CommandTerminal.printError("Usage: hash <text>");
            return;
        }
        HashReverser.HashResult result = HashReverser.phiHash(args);
        CommandTerminal.printHighlight("Phi-Harmonic Hash:");
        CommandTerminal.print("  Input: " + result.input);
        CommandTerminal.printColored("  SHA-256: " + result.hash, 0.4f, 1.0f, 0.8f);
        CommandTerminal.print(String.format("  Phi Resonance: %.10f", result.phiResonance));
        CommandTerminal.print(String.format("  Harmonic Freq: %.2f Hz", result.harmonicFrequency));
    }

    public void runCrackHash(String args) {
        if (args.isEmpty()) {
            CommandTerminal.printError("Usage: crack <hash_prefix>");
            return;
        }
        String target = args.trim();
        CommandTerminal.printHighlight(String.format("Attempting to reverse hash: %s...", target));
        CommandTerminal.printInfo("Using living circuits as computational guides...");

        List<PhiNode> nodes = world.getNodes();
        int maxIter = 100_000;

        HashReverser.CrackResult result = HashReverser.crackHash(target, nodes, maxIter);

        if (result.fullMatch) {
            CommandTerminal.printSuccess(String.format("HASH CRACKED in %d iterations (%.3f ms)!",
                    result.iterations, result.elapsedNanos / 1_000_000.0));
            CommandTerminal.printSuccess("  Input: " + result.foundInput);
            CommandTerminal.printSuccess("  Hash:  " + HashReverser.sha256(result.foundInput));
        } else {
            CommandTerminal.printColored(String.format("Best partial match: %d/%d prefix bits after %d iterations",
                    result.bestPartialBits, target.length() * 4, result.iterations),
                    1.0f, 0.5f, 0.0f);
            if (result.bestPartial != null) {
                String partialHash = HashReverser.sha256(result.bestPartial);
                CommandTerminal.print("  Best input:  " + result.bestPartial);
                CommandTerminal.print("  Best hash:   " + partialHash.substring(0, Math.min(target.length() + 4, partialHash.length())));
                CommandTerminal.print("  Target:      " + target);
            }
            CommandTerminal.printInfo("  Full SHA-256 reversal is cryptographically infeasible");
            CommandTerminal.printInfo("  But living circuits found " + result.bestPartialBits + " matching prefix bits");
        }

        if (world.getMemory() != null) {
            world.getMemory().record("HASH_CRACK",
                    String.format("target=%s|bits=%d|iters=%d",
                            target.substring(0, Math.min(16, target.length())),
                            result.bestPartialBits, result.iterations));
        }
    }

    public void runRsaChallenge(String args) {
        int bits = 16;
        if (!args.isEmpty()) {
            try {
                bits = Integer.parseInt(args.trim());
                if (bits < 8) bits = 8;
                if (bits > 64) {
                    CommandTerminal.printInfo("Capping at 64 bits for terminal demo");
                    bits = 64;
                }
            } catch (NumberFormatException e) {
                CommandTerminal.printError("Usage: rsa <bits>");
                return;
            }
        }

        CommandTerminal.printHighlight(String.format("=== RSA %d-BIT CHALLENGE ===", bits));

        CommandTerminal.printInfo("[BLUE TEAM] Generating keypair...");
        RSASandbox.BlueTeam blue = new RSASandbox.BlueTeam(bits);
        String nHex = blue.N.toString(16);
        CommandTerminal.print(String.format("  Public N: %s (%d bits)",
                nHex.substring(0, Math.min(32, nHex.length())), blue.N.bitLength()));

        String secret = bits <= 16 ? "PHI" : "ALIVE";
        CommandTerminal.printInfo("[BLUE TEAM] Encrypting: \"" + secret + "\"");
        BigInteger cipher = blue.encrypt(secret);

        CommandTerminal.printColored("[RED TEAM] Initiating Fermat factorization...", 1.0f, 0.3f, 0.3f);

        RSASandbox.RedTeam red = new RSASandbox.RedTeam();
        long start = System.nanoTime();
        BigInteger[] factors = red.crack(blue.N);
        long elapsed = System.nanoTime() - start;

        if (factors != null) {
            CommandTerminal.printSuccess(String.format("[RED TEAM] FACTORS FOUND in %d steps (%.3f ms)",
                    red.getSteps(), elapsed / 1_000_000.0));
            CommandTerminal.printSuccess(String.format("  p = %s", factors[0]));
            CommandTerminal.printSuccess(String.format("  q = %s", factors[1]));
            CommandTerminal.print(String.format("  p * q == N: %s",
                    factors[0].multiply(factors[1]).equals(blue.N) ? "YES" : "NO"));

            BigInteger stolenD = red.derivePrivateKey(factors[0], factors[1], blue.e);
            String decrypted = red.decryptMessage(cipher, stolenD, blue.N);
            CommandTerminal.printHighlight("[RED TEAM] DECRYPTED: \"" + decrypted + "\"");

            if (decrypted.equals(secret)) {
                CommandTerminal.printSuccess("RSA DEFEATED - Identity proven and broken");
            }

            if (world.getMemory() != null) {
                world.getMemory().record("RSA_CHALLENGE",
                        String.format("bits=%d|cracked|steps=%d", bits, red.getSteps()));
            }
            FraymusUI.addLog(String.format("[RSA] %d-bit key cracked in %d steps", bits, red.getSteps()));
        } else {
            CommandTerminal.printError("[RED TEAM] Factorization exceeded limit");
        }
    }

    public void runIdentityChallenge(String args) {
        if (args.isEmpty()) {
            CommandTerminal.printError("Usage: identity <entity_name>");
            return;
        }
        String name = args.trim();
        PhiNode target = null;
        for (PhiNode node : world.getNodes()) {
            if (node.name.equalsIgnoreCase(name)) {
                target = node;
                break;
            }
        }
        if (target == null) {
            CommandTerminal.printError("Entity not found: " + name);
            return;
        }

        CommandTerminal.printHighlight(String.format("=== IDENTITY CHALLENGE: %s ===", target.name));
        CommandTerminal.print(String.format("  Cloaked N: %s... (%d bits)",
                target.signature.toString(16).substring(0, Math.min(24, target.signature.toString(16).length())),
                target.signature.bitLength()));

        RSASandbox.RedTeam red = new RSASandbox.RedTeam();
        long start = System.nanoTime();
        BigInteger[] factors = red.crack(target.signature);
        long elapsed = System.nanoTime() - start;

        if (factors != null) {
            boolean verified = target.cloakedIdentity.verify(factors[0], factors[1]);
            CommandTerminal.printSuccess(String.format("  Cracked in %d steps (%.3f ms)",
                    red.getSteps(), elapsed / 1_000_000.0));
            CommandTerminal.printSuccess(String.format("  Ownership verified: %s", verified ? "YES" : "NO"));

            if (world.getMemory() != null) {
                world.getMemory().record("IDENTITY_CHALLENGE",
                        String.format("entity=%s|cracked|steps=%d|verified=%s",
                                target.name, red.getSteps(), verified));
            }
        } else {
            CommandTerminal.printColored(String.format("  Identity SECURE - could not factor in %d steps",
                    red.getSteps()), 0.3f, 1.0f, 1.0f);
            CommandTerminal.printInfo("  256-bit primes require quantum-scale computation");
        }
    }

    public void runPhysicsCommand(String args) {
        if (args.isEmpty()) {
            showPhysicsStatus();
            return;
        }
        String[] parts = args.split("\\s+", 2);
        String sub = parts[0].toLowerCase();
        String val = parts.length > 1 ? parts[1] : "";

        switch (sub) {
            case "gravity":
                try {
                    gravityForce = Float.parseFloat(val);
                    CommandTerminal.printSuccess(String.format("Gravity set to %.2f", gravityForce));
                } catch (NumberFormatException e) {
                    CommandTerminal.printError("Usage: physics gravity <float>");
                }
                break;
            case "speed":
                try {
                    speedMultiplier = Float.parseFloat(val);
                    speedMultiplier = Math.max(0.1f, Math.min(10.0f, speedMultiplier));
                    CommandTerminal.printSuccess(String.format("Speed multiplier set to %.1fx", speedMultiplier));
                } catch (NumberFormatException e) {
                    CommandTerminal.printError("Usage: physics speed <float>");
                }
                break;
            case "boundary":
                boundaryEnabled = !boundaryEnabled;
                CommandTerminal.printSuccess("Boundaries " + (boundaryEnabled ? "ENABLED" : "DISABLED"));
                break;
            case "chaos":
                for (PhiNode node : world.getNodes()) {
                    node.vx = (float)(Math.random() * 20 - 10);
                    node.vy = (float)(Math.random() * 20 - 10);
                }
                CommandTerminal.printSuccess("Chaos mode: all velocities randomized!");
                FraymusUI.addLog("[PHYSICS] Chaos mode activated");
                break;
            case "freeze":
                for (PhiNode node : world.getNodes()) {
                    node.vx = 0;
                    node.vy = 0;
                }
                CommandTerminal.printSuccess("All entities frozen");
                break;
            case "energy":
                try {
                    float e = Float.parseFloat(val);
                    e = Math.max(0, Math.min(1.0f, e));
                    for (PhiNode node : world.getNodes()) {
                        node.energy = e;
                    }
                    CommandTerminal.printSuccess(String.format("All entities set to %.0f%% energy", e * 100));
                } catch (NumberFormatException e) {
                    CommandTerminal.printError("Usage: physics energy <0.0-1.0>");
                }
                break;
            case "explode":
                for (PhiNode node : world.getNodes()) {
                    float angle = (float)(Math.random() * Math.PI * 2);
                    float force = 10.0f + (float)(Math.random() * 10.0);
                    node.vx = (float) Math.cos(angle) * force;
                    node.vy = (float) Math.sin(angle) * force;
                }
                CommandTerminal.printSuccess("EXPLOSION! All entities scattered");
                FraymusUI.addLog("[PHYSICS] Explosion event");
                break;
            case "collapse":
                for (PhiNode node : world.getNodes()) {
                    float dx = -node.x;
                    float dy = -node.y;
                    float dist = (float) Math.sqrt(dx * dx + dy * dy);
                    if (dist > 1) {
                        node.vx = (dx / dist) * 8.0f;
                        node.vy = (dy / dist) * 8.0f;
                    }
                }
                CommandTerminal.printSuccess("Gravitational collapse: all entities pulled to center");
                FraymusUI.addLog("[PHYSICS] Gravitational collapse");
                break;
            default:
                CommandTerminal.printError("Unknown physics command: " + sub);
                showPhysicsHelp();
                break;
        }
    }

    private void showPhysicsStatus() {
        CommandTerminal.printHighlight("=== PHYSICS STATUS ===");
        CommandTerminal.print(String.format("  Gravity: %.2f", gravityForce));
        CommandTerminal.print(String.format("  Speed: %.1fx", speedMultiplier));
        CommandTerminal.print(String.format("  Boundaries: %s", boundaryEnabled ? "ON" : "OFF"));
        CommandTerminal.print(String.format("  Entities: %d", world.getPopulation()));

        float avgVel = 0;
        for (PhiNode node : world.getNodes()) {
            avgVel += Math.sqrt(node.vx * node.vx + node.vy * node.vy);
        }
        if (world.getPopulation() > 0) avgVel /= world.getPopulation();
        CommandTerminal.print(String.format("  Avg Velocity: %.2f", avgVel));
        showPhysicsHelp();
    }

    private void showPhysicsHelp() {
        CommandTerminal.print("");
        CommandTerminal.printInfo("Physics commands:");
        CommandTerminal.print("  physics gravity <f>  Set gravity");
        CommandTerminal.print("  physics speed <f>    Set speed (0.1-10.0x)");
        CommandTerminal.print("  physics boundary     Toggle boundaries");
        CommandTerminal.print("  physics chaos        Randomize velocities");
        CommandTerminal.print("  physics freeze       Stop all motion");
        CommandTerminal.print("  physics energy <f>   Set all energy (0-1)");
        CommandTerminal.print("  physics explode      Scatter all entities");
        CommandTerminal.print("  physics collapse     Pull to center");
    }

    public float getGravityForce() { return gravityForce; }
    public float getSpeedMultiplier() { return speedMultiplier; }
    public boolean isBoundaryEnabled() { return boundaryEnabled; }
}
