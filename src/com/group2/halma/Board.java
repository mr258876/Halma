package com.group2.halma;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    private static Color[] pieceList = new Color[] {Color.red, Color.green, Color.yellow, Color.black, Color.blue};
    private static Color[] boardList = new Color[] {Color.lightGray, Color.black};
    private static int style = 0;
    private Piece[][] matBoard;
    private boolean pieceSelected = false;
    private int selectedX;
    private int selectedY;
    private SidePanel sidePanel;

    public Board(SidePanel sidePanel){
        this.setLayout(new GridLayout(16,16,1,1));
        this.matBoard = new Piece[16][16];
        this.sidePanel = sidePanel;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                getPiece(i, j);
                this.add(matBoard[i][j]);
            }
        }
    }

    protected void paintComponent(Graphics g) {
        this.setBackground(boardList[0]);
        g.setColor(boardList[1]);
        super.paintComponent(g);
        for(int i = 0; i < 16; i++){
            g.drawRect(4 + i * ((getSize().width - 3) / 16), 4, ((getSize().width - 3) / 16), getSize().height - 10);
            g.drawRect(4, 4 + i * ((getSize().height - 3) / 16),
                    getSize().width - 9, (getSize().height - 3) / 16);
//            g.setColor(Color.lightGray);
//            g.fillRect(4 + i * ((getSize().width - 3) / 16) + 1, 4 + i * ((getSize().height - 3) / 16) + 1,
//                    4 + (i+1) * ((getSize().width - 3) / 16) - 1, 4 + (i+1) * ((getSize().height - 3) / 16) - 1);
        }

    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());

        g.drawRect(1, 1, getSize().width - 2, getSize().height - 2);
    }

    protected void getPiece(int i, int j) {
        switch (GamePlay.getPlayer(i, j)) {
            case 1:
                matBoard[i][j] = new Piece(true);
                matBoard[i][j].setBackground(pieceList[0]);
                break;
            case 2:
                matBoard[i][j] = new Piece(true);
                matBoard[i][j].setBackground(pieceList[1]);
                break;
            case 3:
                matBoard[i][j] = new Piece(true);
                matBoard[i][j].setBackground(pieceList[2]);
                break;
            case 4:
                matBoard[i][j] = new Piece(true);
                matBoard[i][j].setBackground(pieceList[3]);
                break;
            default:
                matBoard[i][j] = new Piece(false);
        }
        matBoard[i][j].setVisible(true);

        int finalJ = j;
        int finalI = i;
        matBoard[i][j].addActionListener(e -> {
            clickAction(finalI, finalJ);
        });
    }

    protected void clickAction(int finalI, int finalJ){
        System.out.println("clicked(" + finalI + ", " + finalJ + ")");
        if(pieceSelected){
            if(GamePlay.move(selectedX, selectedY, finalI, finalJ)){
                getPiece(finalI, finalJ);
                getPiece(selectedX, selectedY);
                unSelect();
                sidePanel.refreshSidePanel();

                if(GamePlay.getHopped()){
                    sidePanel.showEndRole();
                    System.out.println("hop!");
                }

                if(GamePlay.getWinner() != 0){
                    UI.win();
                }
            }else {
                unSelect();
            }
        }else {
            if (GamePlay.getPlayer(finalI, finalJ) == GamePlay.getRole()){
                select(finalI, finalJ);
            }
        }
    }

    protected void select(int x, int y){
        selectedX = x;
        selectedY = y;
        pieceSelected = true;
        matBoard[x][y].setBackground(pieceList[4]);
        System.out.println("selected(" + selectedX + ", " + selectedY + ")!");
    }

    protected void unSelect(){
        pieceSelected = false;
        resetBoard();
        System.out.println("Unselected(" + selectedX + ", " + selectedY + ")!");
    }

    protected void resetBoard(){
        for(Piece[] pList : matBoard){
            for(Piece p : pList){
                p.setVisible(false);
            }
        }

        this.removeAll();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                getPiece(i, j);
                this.add(matBoard[i][j]);
            }
        }
    }

    protected static Color[] getPieceList() { return pieceList; }
    protected static Color[] getBoardList() { return boardList; }
}
