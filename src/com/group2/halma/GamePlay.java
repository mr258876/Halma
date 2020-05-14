package com.group2.halma;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class GamePlay {
    private static int[][] board = new int[16][16];
    private static int players;
    private static int role;
    private static int round;
    private static int winner = 0;
    private static boolean multiHopping = true;
    private static boolean hopped = false;
    private static int hopX;
    private static int hopY;
    private static boolean allowGoBack = false;
    private static int lastX;
    private static int lastY;


    protected GamePlay(int numPlayers){
        players = numPlayers;
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                board[i][j] = 0;
            }
        }

        if(numPlayers == 2 || numPlayers == 1){
            for(int i = 0, j = 4; i < 3 && j >= 0; j--){
                board[i][j] = 1;
                board[j][i] = 1;
                if(j == i) {i++; j = 6 - i;}
            }
            for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                board[i][j] = 2;
                board[j][i] = 2;
                if(j == i) {i--; j = 24 - i;}
            }
            role = 1;
            round = 1;
            System.out.println("2 players board initialize done!");

        }else {
            for(int i = 0, j = 3; i < 3 && j >= 0; j--){
                board[i][j] = 1;
                board[j][i] = 1;
                if(j == i) {i++; j = 5 - i;}
            }
            for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                board[i][j] = 2;
                if(j == 15) {i++; j = 10 + i;}
            }
            for(int i = 15, j = 12; i > 12 && j <= 15; j++){
                board[i][j] = 3;
                board[j][i] = 3;
                if(j == i) {i--; j = 25 - i;}
            }
            for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                board[i][j] = 4;
                if(i == 15) {j++; i = 10 + j;}
            }
            role = 1;
            round = 1;
            System.out.println("4 players board initialize done!");
        }
    }

    protected static boolean move(int x0, int y0, int x1, int y1){
        if(getPlayer(x0, y0) != getRole()){
            System.out.println("player error!");
            return false;
        }
        if(board[x1][y1] != 0){
            System.out.println("move error!");
            return false;
        }

        int opX = x1 - x0;
        int opY = y1 - y0;
        if(-2 < opX && opX < 2 && -2 < opY && opY < 2 && !hopped){
            board[x0][y0] = 0;
            board[x1][y1] = role;
            System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
            nextRole();
            return true;
        }
        if(-2 > opX || opX > 2 || -2 > opY || opY > 2){
            System.out.println("move error!");
            return false;
        }

        int drX, drY;
        if(opX == 0) {drX = 0;} else {drX = opX / abs(opX);}
        if(opY == 0) {drY = 0;} else {drY = opY / abs(opY);}
        if(board[x0 + drX][y0 + drY] != 0 && (opX * opY != 2 || opX * opY != -2)){
            if( !hopped ){
                board[x0][y0] = 0;
                board[x1][y1] = role;
                System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
                hopped = true;
                hopX = x1;
                hopY = y1;
                lastX = x0;
                lastY = y0;
                if(multiHopping) {
                    if(nextStepCheck(x1, y1, true).size() == 0 && !allowGoBack) { nextRole(); }
                    return true;
                } else { nextRole(); return true; }
            }else if(hopX == x0 && hopY == y0){
                board[x0][y0] = 0;
                board[x1][y1] = role;
                System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
                hopped = true;
                hopX = x1;
                hopY = y1;
                lastX = x0;
                lastY = y0;
                return true;
            }else {
                System.out.println("move error!");
                return false;
            }

        }else {
            System.out.println("move error!");
            return false;
        }
    }

    protected static ArrayList<String> nextStepCheck(int x, int y, boolean hopping){
        ArrayList<String> list = new ArrayList<>();
        if(!hopping) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (moveCheck(x, y, x + i - 1, y + j - 1) && (x + i - 1 != lastX || y + j - 1 != lastY)) {
                        list.add((x + i - 1) + " " + (y + i - 1));
                    }
                }
            }
        }
        for(int i = 0; i < 5; i += 4) {
            for (int j = 0; j < 5; j += 4) {
                if(moveCheck(x, y, x+i-2, y+j-2) && (x+i-2 != lastX || y+j-2 != lastY)){
                    list.add((x+i-2) + " " + (y+i-2));
                }
            }
        }
        return list;
    }

    protected static boolean moveCheck(int x0, int y0, int x1, int y1){
        if(board[x1][y1] != 0){
            return false;
        }

        int opX = x1 - x0;
        int opY = y1 - y0;
        if(-2 < opX && opX < 2 && -2 < opY && opY < 2 && !hopped){
            return true;
        }
        if(-2 > opX || opX > 2 || -2 > opY || opY > 2){
            return false;
        }

        int drX, drY;
        if(opX == 0) {drX = 0;} else {drX = opX / abs(opX);}
        if(opY == 0) {drY = 0;} else {drY = opY / abs(opY);}
        if(board[x0 + drX][y0 + drY] != 0 && (opX * opY != 2 || opX * opY != -2)){
            if( !hopped ){
                if(multiHopping){ return true; }
                else { nextRole(); return true; }
            }else if(hopX == x0 && hopY == y0){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    protected static boolean winnerCheck(){
        if(players == 2 || players == 1){
            for(int i = 0, j = 4; i < 3 && j >= 0; j--){
                if(board[i][j] != 2) { break; }
                if(board[j][i] != 2) { break; }
                if(j == i) { i++; j = 6 - i; }
                if(i == 3) { winner = 2; }
            }
            for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                if(board[i][j] != 1) { break; }
                if(board[j][i] != 1) { break; }
                if(j == i) { i--; j = 24 - i; }
                if(i == 12) { winner = 1; }
            }
            if(winner == 1) {System.out.println("player1 win!"); return true;}
            if(winner == 2) {System.out.println("player2 win!"); return true;}
            return false;
        }else if(players == 4){
            for(int i = 0, j = 4; i < 3 && j >= 0; j--){
                if(board[i][j] != 3) { break; }
                if(board[j][i] != 3) { break; }
                if(j == i) { i++; j = 6 - i; }
                if(i == 3) { winner = 3; }
            }
            for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                if(board[i][j] != 4) { break; }
                if(j == 15) { i++; j = 10 + i; }
                if(i == 4) { winner = 4; }
            }
            for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                if(board[i][j] != 1) { break; }
                if(board[j][i] != 1) { break; }
                if(j == i) { i--; j = 24 - i; }
                if(i == 12) { winner = 1; }
            }
            for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                if(board[i][j] != 2) { break; }
                if(i == 15) { j++; i = 10 + j; }
                if(j == 4) { winner = 2;}
            }
            if(winner == 1) {System.out.println("player1 win!"); return true;}
            if(winner == 2) {System.out.println("player2 win!"); return true;}
            if(winner == 3) {System.out.println("player3 win!"); return true;}
            if(winner == 4) {System.out.println("player4 win!"); return true;}
            return false;
        }else {
            return false;
        }
    }

    protected static void nextRole(){
        if(players == 2 && role == 2) { role = 0; round++; System.out.println("round" + round + "start!"); }
        else if(players == 4 && role == 4) { role = 0; round++; System.out.println("round" + round + "start!"); }
        role++;
        hopped = false;
        System.out.println("next role!");
        winnerCheck();
    }

    protected static String getRow(int numRow){
        String row = "";
        for(int i = 0; i < 16; i++){
            row = row + board[numRow][i];
        }
        return row;
    }

    protected static int getPlayer(int x, int y){
        return board[x][y];
    }

    protected static int getPlayers() { return players; }
    protected static int getRole() { return role; }
    protected static int getRound() { return round; }
    protected static int[][] getBoard() { return board; }
    protected static boolean isMultiHopping() { return multiHopping; }
    protected static boolean getHopped() { return hopped; }
    protected static int getWinner() { return winner; }
    protected static boolean isHopped() { return hopped; }
    protected static int getHopX() { return hopX; }
    protected static int getHopY() { return hopY; }
    protected static boolean isAllowGoBack() { return allowGoBack; }
    protected static int getLastX() { return lastX; }
    protected static int getLastY() { return lastY; }

    protected static void setPlayers(int players) { GamePlay.players = players; }
    protected static void setRole(int role) { GamePlay.role = role; }
    protected static void setRound(int round) { GamePlay.round = round; }
    protected static void rowSetBoard(String rowIn, int row) {
        for(int i = 0; i < 16 ; i++){
            board[row][i] = Integer.parseInt(rowIn.substring(i,i+1));
        }
    }
    protected static void setMultiHopping(boolean enableHopping) { GamePlay.multiHopping = enableHopping; }
    protected static void setHopped(boolean hopped) { GamePlay.hopped = hopped; }
    protected static void setHopX(int hopX) { GamePlay.hopX = hopX; }
    protected static void setHopY(int hopY) { GamePlay.hopY = hopY; }
    protected static void setAllowGoBack(boolean allowGoBack) { GamePlay.allowGoBack = allowGoBack; }
    protected static void setLastX(int lastX) { GamePlay.lastX = lastX; }
    protected static void setLastY(int lastY) { GamePlay.lastY = lastY; }
}
