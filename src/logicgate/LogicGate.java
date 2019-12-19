package logicgate;

import event.*;
import java.util.LinkedList;

public abstract class LogicGate extends InputEventEmitter implements InputListener {

    protected int outputValue;
    protected LinkedList<LogicGate> inputGateList = new LinkedList<>();
    protected boolean isCalculated;

    public LogicGate() {
    }

    abstract void calculate();

    final public int getOutputValue() {
       // System.out.println("called " + this.getClass() + " getOutput");
        if (!isCalculated) {
            calculate();
            isCalculated = true;
        }
        return outputValue;
    }

    final public void addInputGate(LogicGate gate) {
        inputGateList.add(gate);
        gate.addInputListener(this);
    }

    @Override
    final public void onInputChanged() {
        if (!isCalculated) {
            return ;
        }
        isCalculated = false;
        listenerList.forEach(x -> {
            x.onInputChanged();
        });
    }

}
