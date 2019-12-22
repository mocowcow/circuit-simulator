package logicgate;

public class OR extends LogicGate {

    @Override
    public void calculate() {
        for (LogicGate k : inputGateList) {
            if (k.getOutputValue() == 1) {
                outputValue = 1;
                return;
            }
        }
        outputValue = 0;
    }

}
