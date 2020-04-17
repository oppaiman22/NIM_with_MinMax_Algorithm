/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamenim.model;

import javax.swing.JButton;

/**
 *
 * @author iqbal
 */
public class CustomJButton extends JButton{
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CustomJButton(int index) {
        this.index = index;
    }
    
}
