/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicgate;

public class NOR extends OR {

    @Override
    public void calculate() {
        super.calculate();
        outputValue = (outputValue + 1) % 2;
    }

}
