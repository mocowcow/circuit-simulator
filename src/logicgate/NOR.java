package logicgate;

public class NOR extends OR {

    @Override
    public void calculate() {
        super.calculate();
        outputValue = (outputValue + 1) % 2;
    }

}
