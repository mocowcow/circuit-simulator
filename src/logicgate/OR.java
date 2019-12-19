/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
