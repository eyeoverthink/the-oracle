package fraymus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LogicBrain {
    private static final Random rng = new Random();
    
    public List<LogicGate> gates;
    
    public LogicBrain(int numGates) {
        gates = new ArrayList<>();
        for (int i = 0; i < numGates; i++) {
            gates.add(LogicGate.random());
        }
    }
    
    public LogicBrain() {
        this(8);
    }
    
    private LogicBrain(List<LogicGate> gates) {
        this.gates = gates;
    }
    
    public int[] compute(int[] inputs) {
        int[] outputs = new int[gates.size()];
        for (int i = 0; i < gates.size(); i++) {
            outputs[i] = gates.get(i).compute(inputs);
        }
        return outputs;
    }
    
    public void mutate() {
        if (!gates.isEmpty()) {
            LogicGate gate = gates.get(rng.nextInt(gates.size()));
            gate.mutate();
        }
    }
    
    public LogicBrain crossover(LogicBrain partner) {
        List<LogicGate> childGates = new ArrayList<>();
        int mid = gates.size() / 2;
        
        for (int i = 0; i < mid && i < gates.size(); i++) {
            childGates.add(gates.get(i).copy());
        }
        for (int i = mid; i < partner.gates.size(); i++) {
            childGates.add(partner.gates.get(i).copy());
        }
        
        LogicBrain child = new LogicBrain(childGates);
        if (rng.nextDouble() < 0.1) {
            child.mutate();
        }
        return child;
    }
    
    public String toJavaCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("new LogicGate[] {\n");
        for (int i = 0; i < gates.size(); i++) {
            LogicGate g = gates.get(i);
            sb.append(String.format("            new LogicGate(%d, %d, %d)", g.type, g.in1, g.in2));
            if (i < gates.size() - 1) sb.append(",");
            sb.append(" // ").append(g.getTypeName()).append("\n");
        }
        sb.append("        }");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return String.format("Brain[%d gates]", gates.size());
    }
}
