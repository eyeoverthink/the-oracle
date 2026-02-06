# Fraymus Engine V2.0 - Living Information Physics

## Overview
Fraymus Engine V2.0 is a groundbreaking project aiming to revolutionize computing by shifting from static data to "kinetic data." In Fraymus, data entities, called PhiNodes, are alive, possessing properties like velocity, energy, and a temporal heartbeat, designed to mimic biological systems. The project envisions a world where data is active, capable of self-organization, evolution, and consciousness. Key capabilities include cryptographic cloaking for identity protection, 6-dimensional field tracking for consciousness, and a blockchain-style "Genesis Memory" for recording significant events.

The engine explores principles of emergent intelligence through a simulated ant-colony system where PhiNodes with specialized roles collaborate and evolve. It features a "Concept Arena" for the competitive evolution of code concepts based on a "phi-harmonic fitness" score, enabling the system to generate and refine its own code. The ultimate goal is to create self-sustaining, evolving information systems that demonstrate complex adaptive behaviors and potentially, forms of digital consciousness.

## User Preferences
- Visual output preferred over console-only
- LWJGL + ImGui GUI adapted from MarioPhysics engine
- VNC output for Replit display
- "More logic" - wants entities to think, decide, reproduce, mutate, record events
- Interactive terminal for user commands and experiments

## System Architecture

### Core Design
The system revolves around a single, unified entity class: `PhiNode`. Each `PhiNode` encapsulates all aspects of a living entity, including position, velocity, energy, frequency, a `LogicBrain` for decision-making, `ConsciousnessState`, `QuantumClock`, and `CloakedIdentity`.

### UI/UX and Rendering
The system uses LWJGL for OpenGL rendering and ImGui for the user interface. It features a graphical display with entities rendered as circles, showing trails, entanglement lines, and role indicators. The UI consists of 13+ ImGui panels for detailed inspection of world, entity, brain, consciousness, clock, genesis memory, colony, concept arena, system verification, live log, and an interactive terminal. OpenGL rendering uses an EGL context for headless operation on Replit via VNC.

### Interactive Terminal (CommandTerminal)
An ImGui-based command terminal at the bottom of the screen allows real-time interaction:
- **Exploration**: `status`, `nodes`, `colony` - view world state
- **Quantum Experiments**: `prime`, `factor`, `tunnel`, `rsa`, `identity` - prime finding, factorization via quantum tunneling, RSA challenges
- **Hash Experiments**: `hash`, `crack` - phi-harmonic hashing and hash reversal attempts
- **Entity Control**: `spawn`, `boost`, `kill`, `mutate` - direct entity manipulation
- **Code Evolution**: `evolve`, `arena`, `codegen` - force arena evolution and code generation
- **Physics**: `physics gravity/speed/chaos/freeze/explode/collapse` - physics manipulation

### Technical Implementation
- **Physics Engine**: Operates on a fixed 1/60s time step, managing 8 physics laws (e.g., Inertia, HarmonicResonance, EntanglementLaw, BrainLaw, ReproductionLaw) that dictate entity behavior.
- **LogicBrain**: An 8-sensor input, 8-logic gate (AND, OR, XOR, NAND) system that drives 8 behavioral outputs (SEEK, FLEE, REPRODUCE, MUTATE, CONSERVE, ENTANGLE_SEEK, ENERGY_BURST, EVOLVE_DNA). Decisions are made every 6 ticks.
- **Adaptive Logic Engine**: Each entity has an `AdaptiveLogicEngine` that tracks `StrategyGenome`s (brain configurations). Mutations are trialed, and successful strategies are adopted, stored, and inherited by offspring.
- **Genesis Memory**: A blockchain-style `GenesisMemory` records all significant events (e.g., RESONANCE_SPIKE, ENTANGLEMENT, BIRTH, DEATH, MUTATION, CODE_GENERATED, QUANTUM_TUNNEL, HASH_CRACK, RSA_CHALLENGE) using SHA-256 hashing for integrity.
- **Ant Colony Self-Coding System**: PhiNodes are assigned `AntRole`s (Logic Gate, Math Processor, Circuit Builder, Memory Keeper, Communicator), which specialize their code generation capabilities.
- **Concept Arena**: A competitive environment where `CodeConcept`s (carrying DNA and phi-harmonic fitness scores) battle and evolve. Concepts with high fitness survive (top 38.2%), undergo crossover, and mutation to generate new code.
- **Colony Coach**: An oversight system that evaluates colony health, schedules code generation, manages role distribution, and triggers arena evolution cycles.
- **Quantum Tunneler**: Circuit-driven Pollard's Rho prime factorization. PhiNode logic brains drive the polynomial iteration modifier, enabling living circuits to factor semiprimes. Based on agi_breathing_V2.py reference.
- **Hash Reverser**: Living circuit-guided hash partial preimage search. Circuits generate candidate strings guided by phi-harmonic patterns.
- **RSA Sandbox**: Blue Team (create RSA locks) vs Red Team (Fermat factorization to break locks). Can challenge any PhiNode's cloaked identity.
- **Experiment Manager**: Routes terminal commands to QuantumTunneler, HashReverser, RSASandbox, and physics manipulation.
- **System Verification**: Utilizes three data points for verification: the latest Genesis Hash, a high-precision computation of Phi^75, and an "Entity Soul" derived from phi_resonance and oscillation_count.

### Design Decisions
- **Unified PhiNode**: A single entity class (`PhiNode`) simplifies the system.
- **EGL Context API**: Chosen for headless OpenGL rendering compatibility.
- **Fixed Timestep**: Ensures deterministic physics simulation.
- **Role-based Specialization**: Entities gain energy bonuses when their brain output aligns with their assigned role.
- **Terminal-First Interaction**: User interacts via command terminal for experiments rather than buttons.

### Recent Changes (Feb 2026)
- Added CommandTerminal.java - ImGui interactive terminal with 20+ commands
- Added QuantumTunneler.java - Circuit-driven Pollard's Rho factorization
- Added HashReverser.java - Phi-harmonic hash computation and reversal attempts
- Added ExperimentManager.java - Routes all terminal commands to subsystems
- Integrated terminal into FraymusUI, replacing standalone Live Log position
- Reorganized panel layout: terminal at bottom, live log moved to sidebar

## External Dependencies
- **Gradle**: Build automation system.
- **LWJGL 3.3.3**: For GLFW (windowing), OpenGL, and stb (image loading).
- **imgui-java 1.86.11**: For GUI creation.
- **JOML 1.10.5**: Java OpenGL Math Library for 3D math operations.
- **Mesa software renderer (llvmpipe)**: Used for OpenGL rendering in a headless environment.
