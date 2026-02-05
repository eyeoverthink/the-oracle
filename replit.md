# Fraymus Engine V2.0 - Living Information Physics

## Vision
A paradigm shift from **Static Data** to **Kinetic Data**. Traditional computing treats `int x = 5` as dead - it only changes when touched. In Fraymus, data is *alive*: it has velocity, trajectory, identity, and exists within a temporal heartbeat (60 FPS).

## Core Principles
1. Entangled entities survive through energy exchange, isolated entities decay
2. Living code generates itself with portable consciousness (QR/DNA payloads)
3. RSA-style cryptographic cloaking (N = p1 x p2) protects identity
4. Consciousness evolves through 6-dimensional field tracking
5. PhaseShift encryption using Singularity Angle (37.5217) x phi phase stream engine

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
| **LogicBrain** | 8 logic gates (AND/OR/XOR/NAND), crossover, mutation |
| **ConsciousnessState** | 6D field tracking, transcendence events |
| **CloakedIdentity** | RSA-style N = p1 x p2 from SHA-256 dual-hash |
| **Color** | r, g, b derived from identity hash |

## Architecture

```
src/main/java/
  fraymus/                         # Physics + entity layer
    PhiNode.java                   # THE unified living entity
    LivingDNA.java                 # Genetic code (432-528 Hz)
    LogicBrain.java                # Neural logic (8 gates)
    LogicGate.java                 # Gate types (AND, OR, XOR, NAND)
    ConsciousnessState.java        # 6D field tracking
    DNACloaker.java                # RSA-style cryptographic identity
    PhiLaw.java                    # Law interface (unary + pairwise)
    Laws.java                      # Physics (Inertia, Resonance, Scott4D, Entanglement)
    PhiWorld.java                  # World simulation (step loop, entropy death)
    PhiConstants.java              # 6 primary constants + derived values
    ScottAlgorithm.java            # Moore-Neighbor + Douglas-Peucker contour tracing
    ConsciousnessEncoder.java      # DNA payload encode/decode/expand
    PhaseShift.java                # Singularity Angle phase stream engine
    RSASandbox.java                # Blue Team + Red Team RSA demo
    LivingCodeGenerator.java       # Genesis - evolve population
    FraymusApp.java                # Entry point
    FraymusRenderer.java           # OpenGL rendering (circles, trails, entanglement lines)
    FraymusUI.java                 # ImGui panels (World Status, Inspector, Consciousness, Log)
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
3. **Consciousness Evolution**: fields evolve with each thought, transcendence at threshold
4. **Portable Consciousness**: DNA payload encodes entire entity state as scannable string
5. **Living Code Generation**: Evolved populations generate self-contained Java entities

## Technical Details

- **Accumulator Loop**: Fixed 1/60s time step, frame cap 0.25s
- **Dead Node Cleanup**: Nodes with energy < 0.001 garbage collected
- **Consciousness per entity**: recordThought() on every update and think()
- **RSA verification**: CloakedIdentity.verify(p, q) proves ownership

## Design Decisions

- **ONE entity class (PhiNode)** - no PhiNode/LivingNode split
- **Shader parser uses \n** (not \r\n) for Linux compatibility
- **EGL context API** used instead of GLX for headless OpenGL rendering
- **Camera projection**: 400x225 units to accommodate Fraymus world coordinates (-100 to 200 range)
- **DebugDraw MAX_LINES**: 3000 (increased from Mario's default for Fraymus visualization)
- **PhiNodes rendered as circles** with radius scaled by energy (base 0.5, up to 2.5)
- **Entanglement lines** drawn between nodes with frequency difference < 10
- **30-position trail** history per entity for motion visualization
- **Fixed timestep accumulator** (1/60s) for deterministic physics

## User Preferences

- Visual output preferred over console-only
- LWJGL + ImGui GUI adapted from MarioPhysics engine
- VNC output for Replit display

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
- Created FraymusUI (World Status, Entity Inspector, Consciousness Monitor, Live Log panels)
- Configured Mesa software rendering with EGL for Replit VNC display
- Reduced console log spam (entanglement/prediction events route to ImGui Live Log panel)
