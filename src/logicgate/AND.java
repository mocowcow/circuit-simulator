/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
