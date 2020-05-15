package com.group2.halma;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    private static Color[] pieceList = new Color[] {Color.red, Color.green, Color.yellow, Color.black,
            Color.blue, Color.cyan};
    private static int style = 0;
    private Piece[][] matBoard;
    private boolean pieceSelected = false;
    private int selectedX;
    private int selectedY;
    private SidePanel sidePanel;
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

    protected void recommendPiece(int x, int y) {
        ArrayList<String> available = GamePlay.nextStepCheck(x, y, GamePlay.getHopped());
        for(String position : available){
            int x1 = Integer.parseInt(position.substring(0, position.indexOf(" ")));
            int y1 = Integer.parseInt(position.substring(position.indexOf(" ") + 1));
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
        recommendPiece(x, y);
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
    public static boolean isShowTips() { return showTips; }

    public static void setShowTips(boolean showTips1) { showTips = showTips1; }
}
