package com.group2.halma.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Piece extends JButton {
    private boolean exist;

    public Piece(boolean exist) {
        this.exist = exist;
        this.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                ((JButton) e.getSource()).setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        });
        Dimension size = getPreferredSize();

        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        if(exist) {
            g.setColor(getBackground());
            g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
            super.paintComponents(g);
        }
    }

    protected void paintBorder(Graphics g) {
        if(exist) {
            g.setColor(Color.black);
            g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
        }
    }

}