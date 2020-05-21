package com.group2.halma.GamePlay;

import java.util.ArrayList;

import static java.lang.Math.abs;

//  |
//  |   This is the unfinished AI part.
//  |

public class AI {
    protected static void AIMove(){
        int[][] board = GamePlay.getBoard();
        ArrayList<AIPiece> PieceList = new ArrayList<>();

        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                if(GamePlay.getPlayer(i, j) == 2){ PieceList.add(new AIPiece(i, j)); }
            }
        }

        for(AIPiece p : PieceList){
            if(!p.movable) { PieceList.remove(p); }
        }
    }

    public static void getHop(int[] p){
        new HopMove(p);
    }
}

class AIPiece {
    int X;
    int Y;
    boolean movable;
    int status;
    ArrayList<int[]> move;

    protected AIPiece(int x, int y){
        this.X = x;
        this.Y = y;
        if(GamePlay.nextStepCheck(x, y, false).size() == 0) { this.movable = false; }
        else { this.movable = true; }
        if(GamePlay.nextStepCheck(x, y, true).size() != 0) { this.status = 10; }
    }

    protected void availableMove(){

    }
}

class HopMove {
    private int[] root;
    private ArrayList<HopMove> moves;

    public HopMove(int[] pos){
        this.root = pos;
        this.moves = new ArrayList<>();
        System.out.println("root="+ root[0] + "," + root[1]);

        for(int[] p : GamePlay.nextStepCheck(pos[0], pos[1], true)){
            if(!p.equals(root) && getMove(p) == -1) { moves.add(new HopMove(p)); }
        }
    }

    protected int getMove(int[] p){
        for(int i = 0; i < moves.size(); i++){
            if(moves.get(i).root.equals(p)) { return i; }
        }
        return -1;
    }
}
