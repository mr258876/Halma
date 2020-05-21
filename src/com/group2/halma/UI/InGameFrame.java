package com.group2.halma.UI;

import javax.swing.*;
import java.awt.*;

public class InGameFrame extends JFrame {
    public InGameFrame() {
        new JFrame(UI.getLangLines().get(25));
        this.setSize(250, 380);
        this.setLocation(300, 300);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,200,25));
        this.setIconImage(UI.getIcon().getImage());

        UI.setFrame(this);

        this.setVisible(true);
    }
}
