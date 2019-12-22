package logicgate;

public class NOT extends LogicGate {

    @Override
    void calculate() {
        if (inputGateList.getFirst().getOutputValue() == 1) {
            outputValue = 0;
        } else {
            outputValue = 1;
        }
    }

}
