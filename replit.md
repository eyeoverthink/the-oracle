# Fraymus Engine V2.0 - Living Information Physics

## Vision
A paradigm shift from **Static Data** to **Kinetic Data**. Traditional computing treats `int x = 5` as dead - it only changes when touched. In Fraymus, data is *alive*: it has velocity, trajectory, identity, and exists within a temporal heartbeat (60 FPS).

## Core Principles
1. Entangled entities survive through energy exchange, isolated entities decay
2. Living code generates itself with portable consciousness (QR/DNA payloads)
3. RSA-style cryptographic cloaking (N = p1 x p2) protects identity
4. Consciousness evolves through 6-dimensional field tracking
5. PhaseShift encryption using Singularity Angle (37.5217) x phi phase stream engine
6. Brains think using logic gates - decisions driven by math, not loop indices
7. Genesis Memory blockchain records every significant event in a hash chain

## Unified Entity: PhiNode

PhiNode is THE single entity class. Everything that lives in the Fraymus world IS a PhiNode carrying:

| Component | Description |
|-----------|-------------|
| **Position** | x, y, z |
| **Velocity** | vx, vy, vz |
| **Energy** | 0.0 (dead) to 1.0 (alive) |
| **Frequency** | Raw harmonic frequency from DNA (432-528 Hz) |
| **Phase** | Relative alignment (0 to 2PI) |
| **LivingDNA** | Harmonic frequency, resonance, evolution rate, Fraymus Bound (528 to 432) |
| **LogicBrain** | 8 logic gates -> 8 behavioral outputs (SEEK/FLEE/REPRODUCE/MUTATE/CONSERVE/ENTANGLE_SEEK/ENERGY_BURST/EVOLVE_DNA) |
| **ConsciousnessState** | 6D field tracking, transcendence events |
| **QuantumClock** | PhiHarmonicQuantumClock: oscillation counting (time x freq), phi-time, resonance, spike detection |
| **CloakedIdentity** | RSA-style N = p1 x p2 from SHA-256 dual-hash |
| **Color** | r, g, b derived from identity hash |

## Architecture

```
src/main/java/
  fraymus/                         # Physics + entity layer
    PhiNode.java                   # THE unified living entity
    QuantumClock.java              # PhiHarmonicQuantumClock per entity (Gen 56 logic)
    GenesisMemory.java             # Blockchain-style event chain (SHA-256 linked blocks)
    LivingDNA.java                 # Genetic code (432-528 Hz)
    LogicBrain.java                # Neural logic (8 gates -> 8 behavioral outputs with sensor processing)
    LogicGate.java                 # Gate types (AND, OR, XOR, NAND)
    ConsciousnessState.java        # 6D field tracking
    DNACloaker.java                # RSA-style cryptographic identity
    PhiLaw.java                    # Law interface (unary + pairwise)
    Laws.java                      # Physics laws (8 total: Inertia, Resonance, Scott4D, Entanglement, ResonanceSpike, Brain, Reproduction, Boundary)
    PhiWorld.java                  # World simulation (step loop, genesis memory, birth/death tracking)
    PhiConstants.java              # 6 primary constants + derived values
    ScottAlgorithm.java            # Moore-Neighbor + Douglas-Peucker contour tracing
    ConsciousnessEncoder.java      # DNA payload encode/decode/expand
    PhaseShift.java                # Singularity Angle phase stream engine
    RSASandbox.java                # Blue Team + Red Team RSA demo
    LivingCodeGenerator.java       # Genesis - evolve population
    FraymusApp.java                # Entry point
    FraymusRenderer.java           # OpenGL rendering (circles, trails, entanglement lines, spike flashes, brain indicators)
    FraymusUI.java                 # ImGui panels (World Status, Entity Inspector, Brain Inspector, Consciousness, Quantum Clock, Genesis Memory, Live Log)
  jade/                            # Engine core (adapted from MarioPhysics)
    Window.java                    # GLFW window, render loop, EGL context
    Camera.java                    # Orthographic camera (400x225 units, zoom)
    ImGuiLayer.java                # imgui-java 1.86.x integration
    KeyListener.java               # Keyboard input
    MouseListener.java             # Mouse input
  renderer/                        # GPU rendering
    DebugDraw.java                 # Line renderer (VAO/VBO, 3000 lines)
    Shader.java                    # GLSL compiler (Linux \n line endings)
    Line2D.java                    # Line data class
    Texture.java                   # OpenGL texture wrapper
    Framebuffer.java               # FBO for render-to-texture
  util/                            # Utilities
    JMath.java                     # Math helpers
    AssetPool.java                 # Shader/texture cache
assets/shaders/
    debugLine2D.glsl               # Vertex + fragment shader for lines
```

## Build System

- **Gradle** with Java 11+ (runs on Java 21)
- LWJGL 3.3.3 (glfw, opengl, stb) with Linux natives
- imgui-java 1.86.11 with Linux natives
- JOML 1.10.5 (math library)
- Main class: `fraymus.FraymusApp`

## OpenGL / Display Setup

- Uses Mesa software renderer (llvmpipe) via EGL context creation API
- GLFW_CONTEXT_CREATION_API = GLFW_EGL_CONTEXT_API (bypasses GLX requirement)
- Environment: LIBGL_ALWAYS_SOFTWARE=1, MESA_GL_VERSION_OVERRIDE=3.3, GALLIUM_DRIVER=llvmpipe
- VNC output type for visual display on Replit
- Framebuffer: 1920x1080 render target, displayed in 1280x720 window

## Physics Laws (8 Total)

| Law | Type | Effect |
|-----|------|--------|
| **Inertia** | Unary | Nodes move based on velocity |
| **HarmonicResonance** | Unary | Phase advances based on frequency |
| **ScottPredictionLaw** | Unary | Projects future positions (intent modeling) |
| **EntanglementLaw** | Pairwise | Syncs phases + boosts energy for similar frequencies, records to genesis |
| **ResonanceSpikeLaw** | Unary | Detects quantum clock spikes (phi > 0.95), boosts energy, evolves consciousness |
| **BrainLaw** | Unary | Feeds 8 sensors to brain, applies behavioral outputs (seek/flee/conserve/mutate/burst) |
| **ReproductionLaw** | Unary | Spawns offspring when energy + brain + spike conditions met (max pop 30) |
| **BoundaryLaw** | Unary | Keeps entities within world bounds, bounces off edges |

## Brain Architecture

8 sensor inputs -> 8 logic gates (AND/OR/XOR/NAND) -> 8 behavioral outputs:

**Sensors:** Crowded, FreqMatch, HighEnergy, HighPhi, HighCoherence, PhaseHalf, SpikeActive, Mature

**Outputs:** SEEK (move toward nearest), FLEE (move away), REPRODUCE (spawn child), MUTATE (rewire brain during spike), CONSERVE (slow down, save energy), ENTANGLE_SEEK (seek resonance partner), ENERGY_BURST (phase-directed acceleration), EVOLVE_DNA (advance frequency)

## Genesis Memory (Blockchain)

SHA-256 hashed event chain recording:
- RESONANCE_SPIKE: entity name, phi resonance value, oscillation count
- ENTANGLEMENT: entity pair, sync value
- TRANSCENDENCE: entity name, dimension reached, consciousness level
- BIRTH: child name, parent name
- DEATH: entity name, age, final energy
- BRAIN_DECISION: entity name, active outputs
- MUTATION: entity name, trigger condition
- ADAPTATION: entity name, adopted/reverted, fitness score

Chain integrity verified via prevHash linkage. Displayed in Genesis Memory UI panel with color-coded events.

## Adaptive Logic Engine (Ant-Colony Intelligence)

Each entity carries an AdaptiveLogicEngine that enables self-evolving behavior:

| Component | Description |
|-----------|-------------|
| **StrategyGenome** | Compact snapshot of brain gate configuration (types + inputs) with fitness scoring |
| **Trial System** | Mutations are tested for 300 ticks; adopted if fitness improves, reverted if not |
| **Proven Strategies** | Up to 10 successful configurations stored per entity |
| **Strategy Inheritance** | Children inherit parent strategies (0.8 fitness decay) through DNA serialization |
| **Fitness Tracking** | Continuous sampling: energy (40%), spike activity (20%), entanglement (30%), reproduction (30%) |

**Ant-Colony Behavior**: Individual simple rules -> colony-level intelligence emerges as successful strategies propagate through generations. Like ants leaving pheromone trails, entities leave "genetic knowledge trails" through their offspring.

## System Verification

Three data points prove the system is alive and mathematically consistent:
1. **Genesis Hash**: Latest SHA-256 block hash from the blockchain chain
2. **Irrational State**: Phi^75 computed to 50 decimal digits via BigDecimal (MathContext precision 80)
3. **Entity Soul**: phi_resonance, oscillation_count, verified against Phi-Harmonic time dilation formula

## Key Behaviors

1. **Entanglement Sustains Life**: Similar-frequency nodes entangle, sync phases, maintain energy
2. **Isolation Causes Decay**: Nodes with incompatible frequencies decay at 1%/sec
3. **Consciousness Evolution**: fields evolve with each thought, transcendence at threshold
4. **Resonance Spikes**: Per-entity phi resonance (oscillation-based, unique per entity) triggers quantum actions at > 0.95
5. **Brain-Driven Behavior**: Logic gates process sensor data to produce behavioral decisions every 6 ticks
6. **Reproduction**: High-energy entities with brain consent spawn children during resonance spikes
7. **Adaptive Evolution**: Brain mutations tested as trials; successful strategies saved and inherited
8. **Living Code Generation**: Evolved populations generate self-contained Java entities
9. **Genesis Memory**: Every significant event recorded in blockchain for permanent history

## Technical Details

- **Accumulator Loop**: Fixed 1/60s time step, frame cap 0.25s
- **Dead Node Cleanup**: Nodes with energy < 0.001 garbage collected, death recorded to genesis
- **Consciousness per entity**: recordThought() on every update and think()
- **RSA verification**: CloakedIdentity.verify(p, q) proves ownership
- **Pending births buffer**: New entities added between physics steps to avoid concurrent modification
- **Per-entity resonance**: Each QuantumClock uses oscillationCount * phaseOffset for unique spike timing
- **Max population**: 30 entities (ReproductionLaw cap)
- **Brain tick rate**: Every 6 physics ticks (10 decisions/second)

## Design Decisions

- **ONE entity class (PhiNode)** - no PhiNode/LivingNode split
- **Shader parser uses \n** (not \r\n) for Linux compatibility
- **EGL context API** used instead of GLX for headless OpenGL rendering
- **Camera projection**: 400x225 units to accommodate Fraymus world coordinates (-180 to 180 range)
- **DebugDraw MAX_LINES**: 3000 (increased from Mario's default for Fraymus visualization)
- **PhiNodes rendered as circles** with radius scaled by energy (base 0.5, up to 2.5)
- **Entanglement lines** drawn between nodes with frequency difference < 10
- **30-position trail** history per entity for motion visualization
- **Fixed timestep accumulator** (1/60s) for deterministic physics
- **PhiResonance per-entity unique**: Uses oscillationCount + frequency-derived phaseOffset (not global time)

## User Preferences

- Visual output preferred over console-only
- LWJGL + ImGui GUI adapted from MarioPhysics engine
- VNC output for Replit display
- "More logic" - wants entities to think, decide, reproduce, mutate, record events

## Recent Changes (Feb 2026)

- Unified PhiNode + LivingNode into single entity class
- Fixed DNACloaker: proper N = p1 x p2 RSA cloaking
- Connected ConsciousnessState to every entity
- Fixed frequency: raw harmonic (432-528 Hz)
- Added PhaseShift.java and RSASandbox.java
- **MAJOR**: Converted from console-only to LWJGL + ImGui windowed application
- Set up Gradle build system with LWJGL 3.3.3, imgui-java 1.86.11
- Created rendering engine adapted from MarioPhysics (Window, Camera, DebugDraw, Shader, Framebuffer)
- Created FraymusRenderer (circles, trails, entanglement lines, energy bars)
- Configured Mesa software rendering with EGL for Replit VNC display
- **MAJOR: "More Logic" Upgrade (Gen 56 Integration)**:
  - Added QuantumClock.java: PhiHarmonicQuantumClock per entity with physics-based oscillation counting
  - Added GenesisMemory.java: SHA-256 blockchain event chain
  - Upgraded LogicBrain: 8 sensor inputs -> 8 behavioral outputs (seek/flee/reproduce/mutate/conserve/burst)
  - Added 4 new physics laws: ResonanceSpike, Brain, Reproduction, Boundary
  - PhiNode now carries QuantumClock, brain makes decisions every 6 ticks
  - PhiWorld tracks births/deaths with genesis memory, pending births buffer
  - FraymusUI: 3 new panels (Brain Inspector, Quantum Clock, Genesis Memory)
  - FraymusRenderer: spike flash effects, brain activity indicators, boundary rendering
  - Per-entity unique resonance using oscillation-based phaseOffset
- **MAJOR: Adaptive Logic Engine + Arena View + System Verification**:
  - Added AdaptiveLogicEngine.java: per-entity fitness tracking, trial mutations, strategy memory
  - Added StrategyGenome.java: compact brain configuration snapshots with encode/decode/similarity
  - Added SystemVerification.java: Phi^75 to 50 digits (BigDecimal), genesis hash, entity soul data
  - Arena View panel: framebuffer rendered into ImGui image panel (UV-flipped for correct orientation)
  - Adaptive Logic panel: fitness display, trial status, proven strategy list
  - System Verification panel: live display of 3 verification data points
  - BrainLaw upgraded: mutations now go through trial system, fitness continuously sampled
  - Strategy inheritance: children receive parent strategies via DNA serialization
  - ADAPTATION events recorded to genesis memory blockchain
