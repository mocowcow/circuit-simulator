package logicgate;

public class AND extends LogicGate {

    @Override
    public void calculate() {
        for (LogicGate k : inputGateList) {
            if (k.getOutputValue() == 0) {
                outputValue = 0;
                return;
            }
        }
        outputValue = 1;
    }

}
