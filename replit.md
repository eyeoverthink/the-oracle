# Fraymus Physics Engine

## Overview
A deterministic physics engine for "kinetic data" - where data entities are treated as living organisms with position, velocity, energy, frequency, and cryptographic identity (Prime Number signatures).

## Core Concepts
- **Kinetic Data**: Data that is "alive" with velocity, trajectory, and temporal existence (60 FPS heartbeat)
- **PhiNode**: State-vector cells with spatial, spectral, temporal, and genetic dimensions
- **PhiLaw**: Interface for physics rules that govern the simulation
- **Scott 4D**: Predictive engine that projects future states

## Project Structure
```
src/fraymus/
├── DNACloaker.java    # Cryptographic identity engine (SHA-256 → Prime)
├── PhiNode.java       # State-vector cell with 10D properties
├── PhiLaw.java        # Law interface
├── Laws.java          # Inertia, HarmonicResonance, ScottPrediction
├── PhiWorld.java      # World container and simulation loop
└── FraymusMain.java   # Main entry point
```

## Running
The simulation runs at 60 FPS for 10 seconds, outputting state every second. It demonstrates:
- Nodes moving with inertia
- Harmonic oscillation (Phi-based breathing)
- Entanglement detection between nodes with similar frequencies
- Scott 4D prediction of future positions

## Architecture
- **Laws are decoupled from data** - PhiLaw interface allows new physics rules
- **Identity via primes** - DNACloaker generates deterministic prime signatures from seeds
- **Entanglement** - Nodes with matching frequency/phase are considered entangled
