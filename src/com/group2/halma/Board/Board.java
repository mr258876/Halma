package com.group2.halma.Board;

import com.group2.halma.GamePlay.GamePlay;
import com.group2.halma.UI.UI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private static final Color[] pieceList = new Color[] {Color.red, Color.green, Color.yellow, Color.orange,
            Color.blue, Color.cyan};
    private final Piece[][] matBoard;
    private boolean pieceSelected = false;
    private int selectedX;
    private int selectedY;
    private final SidePanel sidePanel;
    private static boolean showTips = true;

    public Board(SidePanel sidePanel){
        this.setLayout(new GridLayout(16,16,1,1));
        this.matBoard = new Piece[16][16];
        this.sidePanel = sidePanel;
        this.setOpaque(false);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                getPiece(i, j);
                this.add(matBoard[i][j]);
            }
        }
    }

    public void getPiece(int i, int j) {
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

        matBoard[i][j].addActionListener(e -> {
            clickAction(i, j);
        });
    }

    public void recommendPiece(int x, int y) {
        ArrayList<int[]> available = GamePlay.nextStepCheck(x, y, GamePlay.getHopped());
        for(int[] position : available){
            int x1 = position[0];
            int y1 = position[1];
            matBoard[x1][y1].setVisible(false);
            this.remove(x1 * 16 + y1);
            matBoard[x1][y1] = new Piece(true);
            matBoard[x1][y1].setBackground(pieceList[5]);
            matBoard[x1][y1].addActionListener(e -> {
                clickAction(x1, y1);
            });
            this.add(matBoard[x1][y1], x1*16 + y1);
        }
    }

    public void clickAction(int finalI, int finalJ){
//        System.out.println("clicked(" + finalI + ", " + finalJ + ")");
        if(pieceSelected){
            if(GamePlay.move(selectedX, selectedY, finalI, finalJ)){
                getPiece(finalI, finalJ);
                getPiece(selectedX, selectedY);
                unSelect();
                sidePanel.refreshSidePanel();

                if(GamePlay.getHopped()){
                    sidePanel.showEndRole();
//                    System.out.println("hop!");
                } else {
                    sidePanel.hideEndRole();
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

    public void select(int x, int y){
        selectedX = x;
        selectedY = y;
        pieceSelected = true;
        matBoard[x][y].setBackground(pieceList[4]);
//        AI.getHop(new int[] {x,y});
        if(showTips) { recommendPiece(x, y); }
//        System.out.println("selected(" + selectedX + ", " + selectedY + ")!");
    }

    public void unSelect(){
        pieceSelected = false;
        resetBoard();
//        System.out.println("Unselected(" + selectedX + ", " + selectedY + ")!");
    }

    public void resetBoard(){
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

    public static Color[] getPieceList() { return pieceList; }
    public static boolean isShowTips() { return showTips; }

    public static void setShowTips(boolean showTips1) { showTips = showTips1; }
}
