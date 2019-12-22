package logicgate;

public class BUF extends LogicGate {

    @Override
    void calculate() {
        outputValue = inputGateList.getFirst().getOutputValue();
    }

}
