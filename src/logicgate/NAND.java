package logicgate;

public class NAND extends AND {

    @Override
    public void calculate() {
        super.calculate();
        outputValue = (outputValue + 1) % 2;

    }

}
