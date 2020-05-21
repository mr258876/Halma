package com.group2.halma.UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

    public MainFrame(){
        this.setTitle("Project Halma");
        this.setSize(500, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(UI.getIcon().getImage());
        this.setLayout(new GridBagLayout());

        UI.setFrame(this);
    }

    public void mainReset(){
        this.setLayout(new GridBagLayout());
        UI.setFrame(this);
    }
}
