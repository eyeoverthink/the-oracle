package fraymus;

import java.util.ArrayList;
import java.util.List;

public class PhiWorld {
    private List<PhiNode> nodes = new ArrayList<>();
    private List<PhiLaw> laws = new ArrayList<>();
    
    public void addNode(PhiNode node) {
        nodes.add(node);
    }
    
    public void addLaw(PhiLaw law) {
        laws.add(law);
    }
    
    public void step(float dt, long nowNanos) {
        for (PhiLaw law : laws) {
            if (law.isPairwise()) {
                for (int i = 0; i < nodes.size(); i++) {
                    for (int j = i + 1; j < nodes.size(); j++) {
                        law.applyPair(nodes.get(i), nodes.get(j), dt);
                    }
                }
            } else {
                for (PhiNode node : nodes) {
                    law.apply(node, dt);
                }
            }
        }
        
        for (PhiNode node : nodes) {
            node.updateInternalState(dt, nowNanos);
        }
        
        int deadCount = 0;
        for (PhiNode n : nodes) {
            if (!n.isAlive()) deadCount++;
        }
        if (deadCount > 0) {
            System.out.printf("  [ENTROPY] %d node(s) expired%n", deadCount);
        }
        nodes.removeIf(n -> !n.isAlive());
    }
    
    public List<PhiNode> getNodes() { return nodes; }
    
    public int getPopulation() { return nodes.size(); }
}
