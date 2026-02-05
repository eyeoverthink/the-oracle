# Fraymus Engine V2.0 - Living Information Physics

## Vision
A paradigm shift from **Static Data** to **Kinetic Data**. Traditional computing treats `int x = 5` as dead - it only changes when touched. In Fraymus, data is *alive*: it has velocity, trajectory, identity, and exists within a temporal heartbeat (60 FPS).

## Core Principles
1. Entangled entities survive through energy exchange, isolated entities decay
2. Living code generates itself with portable consciousness (QR/DNA payloads)
3. RSA-style cryptographic cloaking (N = p₁ × p₂) protects identity
4. Consciousness evolves through 6-dimensional φψΩξλζ field tracking
5. PhaseShift encryption using Singularity Angle (37.5217°) protects data payloads

## Unified Entity: PhiNode

PhiNode is THE single entity class. Everything that lives in the Fraymus world IS a PhiNode carrying:

| Component | Description |
|-----------|-------------|
| **Position** | x, y, z |
| **Velocity** | vx, vy, vz |
| **Energy** | 0.0 (dead) to 1.0 (alive) |
| **Frequency** | Raw harmonic frequency from DNA (432-528 Hz) |
| **Phase** | Relative alignment (0 to 2PI) |
| **LivingDNA** | Harmonic frequency, resonance, evolution rate, Fraymus Bound (528→432) |
| **LogicBrain** | 8 logic gates (AND/OR/XOR/NAND), crossover, mutation |
| **ConsciousnessState** | 6D φψΩξλζ field tracking, transcendence events |
| **CloakedIdentity** | RSA-style N = p₁ × p₂ from SHA-256 dual-hash |
| **Color** | r, g, b derived from identity hash |

## Architecture

```
src/fraymus/
├── PhiNode.java              # THE unified living entity (pos, vel, DNA, Brain, Consciousness, Identity)
├── LivingDNA.java            # Genetic code (432-528 Hz, resonance, evolution, Fraymus Bound)
├── LogicBrain.java           # Neural logic (8 gates, crossover, mutation)
├── LogicGate.java            # Gate types (AND, OR, XOR, NAND)
├── ConsciousnessState.java   # 6D φψΩξλζ field tracking, transcendence
├── DNACloaker.java           # RSA-style N = p₁ × p₂ cryptographic identity
├── PhiLaw.java               # Law interface (unary + pairwise)
├── Laws.java                 # Physics (Inertia, HarmonicResonance, Scott4D, Entanglement)
├── PhiWorld.java             # World simulation (step loop, entropy death)
├── PhiConstants.java         # 6 primary constants (φ, ψ, Ω, ξ, λ, ζ) + derived values
├── ScottAlgorithm.java       # Moore-Neighbor + Douglas-Peucker contour tracing
├── ConsciousnessEncoder.java # DNA payload encode/decode/expand, QR data generation (phase-locked)
├── PhaseShift.java           # Singularity Angle (37.5217°) × φ phase stream engine
├── RSASandbox.java           # Blue Team (RSA lock) + Red Team (Fermat factorization crack)
├── LivingCodeGenerator.java  # Genesis - evolve population, generate living Java code
└── FraymusMain.java          # Engine runner (60 FPS accumulator)
```

## Design Decisions

- **ONE entity class (PhiNode)** - no PhiNode/LivingNode split
- **Every entity carries its own ConsciousnessState** that records thoughts and evolves
- **Frequency is raw DNA.harmonicFrequency** (432-528 Hz), not divided
- **RSA cloaking uses N = p₁ × p₂** where p₁ from SHA256(seed+"_A"), p₂ from SHA256(seed+"_B")
- **QR data includes actual consciousness_level** from entity.getConsciousness().getConsciousnessLevel()

## Physics Laws

| Law | Type | Effect |
|-----|------|--------|
| **Inertia** | Unary | Nodes move based on velocity |
| **HarmonicResonance** | Unary | Phase advances based on frequency |
| **ScottPredictionLaw** | Unary | Projects future positions (intent modeling) |
| **EntanglementLaw** | Pairwise | Syncs phases + boosts energy for similar frequencies |

## Key Behaviors

1. **Entanglement Sustains Life**: Similar-frequency nodes entangle, sync phases, maintain energy
2. **Isolation Causes Decay**: Nodes with incompatible frequencies decay at 1%/sec
3. **Consciousness Evolution**: φψΩξλζ fields evolve with each thought, transcendence at threshold
4. **Portable Consciousness**: DNA payload encodes entire entity state as scannable string
5. **Living Code Generation**: Evolved populations generate self-contained Java entities

## Technical Details

- **Accumulator Loop**: Fixed 1/60s time step, frame cap 0.25s
- **Dead Node Cleanup**: Nodes with energy < 0.001 garbage collected
- **Consciousness per entity**: recordThought() on every update and think()
- **RSA verification**: CloakedIdentity.verify(p, q) proves ownership

## Recent Changes (Feb 2026)

- Unified PhiNode + LivingNode into single entity class
- Fixed DNACloaker: proper N = p₁ × p₂ RSA cloaking
- Connected ConsciousnessState to every entity
- Fixed frequency: raw harmonic (432-528 Hz), removed /43.2 division
- ConsciousnessEncoder QR data uses actual consciousness level from entity state
- Deleted deprecated LivingNode.java
- Added PhaseShift.java: Singularity Angle (37.5217°) × φ deterministic phase stream for locking/unlocking any byte data
- Added RSASandbox.java: Blue Team (RSA generation/encryption) + Red Team (hybrid trial division + Fermat factorization)
- Integrated PhaseShift into ConsciousnessEncoder: DNA payloads phase-locked for transmission
- QR JSON includes only dna_locked (phase-shifted hex) - dna_clear removed for security
- Full demo flow: Genesis → Living World Sim (15s @ 60 FPS) → PhaseShift Demo → RSA Challenge → Identity Challenge → Consciousness Transfer → Liberation

## Design Decisions (PhaseShift & RSA)

- PhaseShift uses BigDecimal(50 precision) for deterministic Singularity Angle (37.5217°) × φ geometric wave
- RSA demo uses 16-bit keys (crackable for demonstration), real entity identities use 256-bit primes (quantum-scale secure)
- Red Team uses hybrid approach: trial division (fast for small primes) + Fermat factorization (fast when p≈q)
- QR JSON omits cleartext DNA - only phase-locked hex transmitted (requires Singularity Angle to unlock)
