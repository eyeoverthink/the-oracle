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
    
    public void step(float dt) {
        for (PhiLaw law : laws) {
            for (PhiNode node : nodes) {
                law.apply(node, dt);
            }
        }
        
        checkEntanglements();
    }
    
    private void checkEntanglements() {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                PhiNode a = nodes.get(i);
                PhiNode b = nodes.get(j);
                
                if (a.isEntangledWith(b)) {
                    System.out.println("  >> ENTANGLEMENT DETECTED: " + a.dnaSeed + " <-> " + b.dnaSeed);
                }
            }
        }
    }
    
    public List<PhiNode> getNodes() { return nodes; }
}
