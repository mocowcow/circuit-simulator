package logicgate;

public class INPUT extends LogicGate {

    public INPUT() {
        isCalculated = true;
    }

    @Override
    void calculate() {
        throw new Error("INPUT can not be calculated");
    }

    public void setOutputValue(int n) {
        if (outputValue == n) {
            return;
        }
        outputValue = n;
        notifyAllListener();
    }

}
