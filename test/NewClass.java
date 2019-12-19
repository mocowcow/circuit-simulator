
import mysim.CircuitSimulator;

public class NewClass {

    public static void main(String[] args) {
        CircuitSimulator simulator = new CircuitSimulator();
        simulator.addInputGate("g1");
        simulator.addInputGate("g2");
        //
        simulator.addOutputGate("g5");
        //
        simulator.addConfigString(new String[]{"g3", "and", "g1", "g2"});
        simulator.addConfigString(new String[]{"g4", "buf", "g3"});
        simulator.addConfigString(new String[]{"g5", "buf", "g4"});
        //
        simulator.buildCircuitByConfig();
        //
        simulator.fillInput("00");
        System.out.println(simulator.getOuputs());
    }
}
