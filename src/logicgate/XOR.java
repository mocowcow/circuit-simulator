package logicgate;

public class XOR extends LogicGate {

    @Override
    public void calculate() {
        if (inputGateList.getFirst().getOutputValue() == inputGateList.getLast().getOutputValue()) {
            outputValue = 0;
        } else {
            outputValue = 1;
        }
    }

}
