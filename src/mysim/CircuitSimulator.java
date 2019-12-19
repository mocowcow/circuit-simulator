package mysim;

import java.util.HashMap;
import java.util.LinkedList;
import logicgate.*;

public class CircuitSimulator {

    private HashMap<String, LogicGate> gateList = new HashMap<>();
    private LinkedList<String[]> configString = new LinkedList<>();
    private LinkedList<INPUT> inputGateList = new LinkedList<>();
    private LinkedList<String> outputList = new LinkedList<>();
    private LinkedList<LogicGate> outputGateList;

    public void fillInput(String ipvs) {
        if (ipvs.length() != inputGateList.size()) {
            throw new java.lang.RuntimeException("Input Size mismatch:" + ipvs.length() + "," + inputGateList.size());
        }
        int index = 0;
        for (INPUT k : inputGateList) {
            if (ipvs.charAt(index) == '0') {
                k.setOutputValue(0);
            } else {
                k.setOutputValue(1);
            }
            index++;
        }
    }

    public String getOuputs() {
        String s = "";
        for (LogicGate k : outputGateList) {
            s += k.getOutputValue();
        }
        return s;
    }

    public void addConfigString(String[] s) {
        configString.add(s);
    }

    public void buildCircuitByConfig() {
        configString.forEach(x -> {
            LogicGate gate = LogicGateFactory.getLogicGate(x[1]);
            for (int i = 2; i < x.length; i++) {
                gate.addInputGate(gateList.get(x[i]));
            }
            gateList.put(x[0], gate);
        });
        buildOutputGateList();
    }

    private void buildOutputGateList() {
        outputGateList = new LinkedList<>();
        outputList.forEach(x -> {
            outputGateList.add(gateList.get(x));
        });
    }

    public void addInputGate(String s) {
        INPUT input = new INPUT();
        inputGateList.add(input);
        gateList.put(s, input);
    }

    public void addOutputGate(String s) {
        outputList.add(s);
    }

}
