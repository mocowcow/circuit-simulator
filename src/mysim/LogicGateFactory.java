package mysim;

import logicgate.*;

public class LogicGateFactory {

    public static LogicGate getLogicGate(String type) {
        switch (type.hashCode()) {
            case 96727:
                return new AND();
            case 3555:
                return new OR();
            case 3373737:
                return new NAND();
            case 109265:
                return new NOR();
            case 118875:
                return new XOR();
            case 3684185:
                return new XNOR();
            case 97907:
                return new BUF();
            case 109267:
                return new NOT();
            default:
                throw new AssertionError();
        }
    }
}
