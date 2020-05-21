package com.group2.halma;

import com.group2.halma.UI.UI;

import javax.swing.*;

public class Halma {
    public static void main(String[] args){
        JFrame f = UI.mainFramework();
        UI.mainMenu(f);
        f.setVisible(true);
    }
}
