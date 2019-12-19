/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicgate;

public class XOR extends LogicGate {

    @Override
    public void calculate() { 
        if (inputGateList.getFirst().getOutputValue() == inputGateList.getLast().getOutputValue()) {
            outputValue = 0;
        } else {
            outputValue = 1;
        }
    }

}
