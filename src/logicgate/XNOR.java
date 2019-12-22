package logicgate;

public class XNOR extends NOR {

    @Override
    public void calculate() {
        super.calculate();
        outputValue = (outputValue + 1) % 2;
    }

}
