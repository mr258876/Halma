package com.group2.halma;

import javax.swing.*;
import java.awt.*;

public class BackgroundBoard extends JPanel {
    private static Color BOARD_COLOR_1 = new Color(255, 255, 204);
    private static Color BOARD_COLOR_2 = new Color(170, 170, 170);
    private FilledSquare[][] matSquare;

    public BackgroundBoard(){
        this.setLayout(new GridLayout(16,16,0,0));
        this.matSquare = new FilledSquare[16][16];
        paintBoard();
    }

    private void paintBoard(){
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                matSquare[row][col] = new FilledSquare(
                        (row + col) % 2 == 0 ? BOARD_COLOR_1 : BOARD_COLOR_2);
                this.add(matSquare[row][col]);
            }
        }
    }
}

class FilledSquare extends JLabel {
    FilledSquare(Color color){
        this.setOpaque(true);
        this.setBackground(color);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}
