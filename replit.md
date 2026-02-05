# Fraymus Engine V2.0 - Living Information Physics

## Vision
A paradigm shift from **Static Data** to **Kinetic Data**. Traditional computing treats `int x = 5` as dead - it only changes when touched. In Fraymus, data is *alive*: it has velocity, trajectory, identity, and exists within a temporal heartbeat (60 FPS).

## Core Philosophy: The Four Axes of Existence

### 1. Spatial Axis (WHERE)
- Position: `x, y, z`
- Momentum: `vx, vy, vz`
- Data has geometry and intent

### 2. Spectral Axis (WHAT)
- Color: `r, g, b` (derived from identity hash)
- Energy: `0.0` (dead) to `1.0` (fully alive)
- Information encoded as frequency

### 3. Temporal Axis (WHEN)
- Frequency: The node's internal clock
- Phase: Relative alignment (0 to 2PI)
- Phi Resonance: 1.618-based "breathing"

### 4. Genetic Axis (WHO)
- DNA Seed: Human-readable name
- Signature: Prime number identity (SHA-256 -> Prime)
- Unique, cryptographically verifiable soul

## Architecture

```
src/fraymus/
├── DNACloaker.java     # Identity Engine (SHA-256 -> Prime Signatures)
├── PhiNode.java        # State-Vector Cell (10D Data Organism)
├── PhiLaw.java         # Law Interface (Unary + Pairwise operations)
├── Laws.java           # Physics Rules (Inertia, Resonance, Scott4D, Entanglement)
├── PhiWorld.java       # World Container (Simulation Loop + Garbage Collection)
└── FraymusMain.java    # Engine Runner (60 FPS Accumulator Pattern)
```

## Physics Laws

| Law | Type | Effect |
|-----|------|--------|
| **Inertia** | Unary | Nodes move based on velocity (kinetic proof) |
| **HarmonicResonance** | Unary | Phase advances based on frequency |
| **ScottPredictionLaw** | Unary | Projects future positions (intent modeling) |
| **EntanglementLaw** | Pairwise | Syncs phases + boosts energy for similar frequencies |

## Key Behaviors Demonstrated

1. **Entanglement Sustains Life**: ALPHA_PRIME and BETA_RESONANT share similar frequencies (10.0 vs 10.1), so they entangle, sync phases, and maintain 100% energy indefinitely.

2. **Isolation Causes Decay**: GAMMA_NOISE has frequency 50.0 (too different), so it cannot entangle. It decays at 1% per second, demonstrating that disconnected information dies.

3. **Phase Synchronization**: Entangled nodes pull toward phase alignment via spring force (`kPhase = 2.0`), simulating quantum coherence.

4. **Predictive Intent (Scott 4D)**: The engine calculates where nodes WILL be, enabling anticipatory logic.

## Technical Details

- **Accumulator Loop**: Prevents "spiral of death" with frame time cap (0.25s max)
- **Fixed Time Step**: 1/60 second for deterministic physics
- **Dead Node Cleanup**: Nodes with energy < 0.001 are garbage collected
- **O(N) Unary Laws + O(N²) Pairwise Laws**: Proper separation of concerns

## Future Vision

- **Spatial Hash**: Optimize O(N²) entanglement checks to O(k) neighbor lookup
- **Shadow World**: Branch Scott 4D predictions into parallel timelines
- **Energy Transfer**: Allow nodes to transmit energy on entanglement
- **Prime Factorization Lock**: Use `entangleIdentities()` for cryptographic binding
