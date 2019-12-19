/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
