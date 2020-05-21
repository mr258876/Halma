package com.group2.halma.GamePlay;

import com.group2.halma.UI.UI;

import java.util.ArrayList;
import java.util.Random;

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
    private static boolean teamPlay = false;

//   | NOTICE!!!!!                                                   |
//   |                                                               |
//   | In the following codes, X means column and Y means row        |
//   |                                                               |
//   | I was not able to fix it when I realized this serious problem |
//   |                                                               |
//   | So, good luck!                                                |
//   |                                         From author @mr258876 |

    public GamePlay(int numPlayers){
        reset();
        players = numPlayers;
        teamPlay = false;
        for(int i = 0; i < 16; i++){
            for(int j = 0; j < 16; j++){
                board[i][j] = 0;
            }
        }

        Random random = new Random();

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
            role = random.nextInt(2) + 1;
            round = 1;
//            System.out.println("2 players board initialize done!");

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
            role = random.nextInt(4) + 1;
            round = 1;
//            System.out.println("4 players board initialize done!");
        }
    }

    public static boolean move(int x0, int y0, int x1, int y1){
        if(getPlayer(x0, y0) != getRole()){
//            System.out.println("player error!");
            return false;
        }
        if(board[x1][y1] != 0){
//            System.out.println("move error!");
            return false;
        }

        if(campCheck(x0, y0) && !campCheck(x1, y1)) { return false; }

        int opX = x1 - x0;
        int opY = y1 - y0;
        if(-2 < opX && opX < 2 && -2 < opY && opY < 2 && !hopped){
            board[x0][y0] = 0;
            board[x1][y1] = role;
//            System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
            nextRole();
            return true;
        }
        if(-2 > opX || opX > 2 || -2 > opY || opY > 2){
//            System.out.println("move error!");
            return false;
        }

        int drX, drY;
        if(opX == 0) {drX = 0;} else {drX = opX / abs(opX);}
        if(opY == 0) {drY = 0;} else {drY = opY / abs(opY);}
        if(board[x0 + drX][y0 + drY] != 0 && (opX * opY != 2 && opX * opY != -2)){
            if( !hopped ){
                board[x0][y0] = 0;
                board[x1][y1] = role;
//                System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
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
//                System.out.println("player" + role + "moves from(" + x0 + "," + y0 + ") to(" + x1 + "," + y1 + ")");
                hopped = true;
                hopX = x1;
                hopY = y1;
                lastX = x0;
                lastY = y0;
                if(nextStepCheck(x1, y1, true).size() == 0 && !allowGoBack) { nextRole(); }
                return true;
            }else {
//                System.out.println("move error!");
                return false;
            }

        }else {
//            System.out.println("move error!");
            return false;
        }
    }

    public static ArrayList<int[]> nextStepCheck(int x, int y, boolean hopping){
        ArrayList<int[]> list = new ArrayList<>();
//        System.out.println(lastX);
//        System.out.println(lastY);
        if(!hopping) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(0 < x+i-1 && x+i-1 < 16 && 0 < y+j-1 && y+j-1 < 16) {
                        if(moveCheck(x, y, x + i - 1, y + j - 1) && (x + i - 1 != lastX || y + j - 1 != lastY)){
                            list.add(new int[] {(x+i-1), (y+j-1)});
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 5; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                if(0 <= x+i-2 && x+i-2 < 16 && 0 <= y+j-2 && y+j-2 < 16) {
                    if(moveCheck(x, y, x + i - 2, y + j - 2) && (x + i - 2 != lastX || y + j - 2 != lastY)){
                        list.add(new int[] {(x+i-2), (y+j-2)});
                    }
                }
            }
        }
        return list;
    }

    public static boolean moveCheck(int x0, int y0, int x1, int y1){
        if(board[x1][y1] != 0){
            return false;
        }

        if(campCheck(x0, y0) && !campCheck(x1, y1)) { return false; }

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
                return true;
            }else return hopX == x0 && hopY == y0;
        }else {
            return false;
        }
    }

    public static boolean winnerCheck(){
        if(players == 2 || players == 1){
            for(int i = 0, j = 4; i < 3 && j >= 0; j--){
                if(board[i][j] != 2) { break; }
                if(board[j][i] != 2) { break; }
                if(j == i) { i++; j = 6 - i; }
                if (i == 3) {
                    winner = 2;
                    break;
                }
            }
            for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                if(board[i][j] != 1) { break; }
                if(board[j][i] != 1) { break; }
                if(j == i) { i--; j = 24 - i; }
                if (i == 12) {
                    winner = 1;
                    break;
                }
            }
            if(winner == 1) {/*System.out.println("player1 win!");*/ return true;}
            if(winner == 2) {/*System.out.println("player2 win!");*/ return true;}
            return false;
        }else if(players == 4 && !teamPlay){
            for(int i = 0, j = 3; i < 3 && j >= 0; j--){
                if(board[i][j] != 3) { break; }
                if(board[j][i] != 3) { break; }
                if(j == i) {i++; j = 5 - i;}
                if (i == 3) {
                    winner = 3;
                    break;
                }
            }
            for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                if(board[i][j] != 4) { break; }
                if(j == 15) { i++; j = 10 + i; }
                if (i == 4) {
                    winner = 4;
                    break;
                }
            }
            for(int i = 15, j = 12; i > 12 && j <= 15; j++){
                if(board[i][j] != 1) { break; }
                if(board[j][i] != 1) { break; }
                if(j == i) {i--; j = 25 - i;}
                if (i == 12) {
                    winner = 1;
                    break;
                }
            }
            for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                if(board[i][j] != 2) { break; }
                if(i == 15) { j++; i = 10 + j; }
                if (j == 4) {
                    winner = 2;
                    break;
                }
            }
            if(winner == 1) {/*System.out.println("player1 win!");*/ return true;}
            if(winner == 2) {/*System.out.println("player2 win!");*/ return true;}
            if(winner == 3) {/*System.out.println("player3 win!");*/ return true;}
            if(winner == 4) {/*System.out.println("player4 win!");*/ return true;}
            return false;
        }else if(players == 4 && teamPlay){
//            System.out.println("team check!");
            boolean C1 = false;
            boolean C2 = false;
            boolean C3 = false;
            boolean C4 = false;

            for(int i = 0, j = 3; i < 3 && j >= 0; j--){
                if(board[i][j] != 2 && board[i][j] != 4) {System.out.println(i + " " + j); break; }
                if(board[j][i] != 2 && board[j][i] != 4) {System.out.println(j + " " + i); break; }
                if(j == i) {i++; j = 5 - i;}
                if (i == 3) {
//                    System.out.println("C1!");
                    C1 = true;
                    break;
                }
            }
            for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                if(board[i][j] != 1 && board[i][j] != 3) { break; }
                if(j == 15) { i++; j = 10 + i; }
                if (i == 4) {
//                    System.out.println("C2!");
                    C2 = true;
                    break;
                }
            }
            for(int i = 15, j = 12; i > 12 && j <= 15; j++){
                if(board[i][j] != 2 && board[i][j] != 4) { break; }
                if(board[j][i] != 2 && board[j][i] != 4) { break; }
                if(j == i) {i--; j = 25 - i;}
                if (i == 12) {
//                    System.out.println("C4!");
                    C4 = true;
                    break;
                }
            }
            for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                if(board[i][j] != 1 && board[i][j] != 3) { break; }
                if(i == 15) { j++; i = 10 + j; }
                if (j == 4) {
//                    System.out.println("C3!");
                    C3 = true;
                    break;
                }
            }
            if(C1 && C3) {/*System.out.println("team1 win!");*/ winner = 1; return true;}
            if(C2 && C4) {/*System.out.println("team2 win!");*/ winner = 2; return true;}
            return false;
        }else {
            return false;
        }
    }

    public static boolean campCheck(int x, int y){
        if(players == 2 || players == 1){
            if(getPlayer(x, y) == 2 || getPlayer(x, y) == 0) {
                for (int i = 0, j = 4; i < 3 && j >= 0; j--) {
                    if (x == i && y == j) { return true; }
                    if (y == i && x == j) { return true; }
                    if (j == i) { i++; j = 6 - i; }
                }
            }
            if(getPlayer(x, y) == 1 || getPlayer(x, y) == 0) {
                for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                    if(x == i && y == j) { return true; }
                    if(y == i && x == j) { return true; }
                    if(j == i) { i--; j = 24 - i; }
                }
            }
            return false;
        }else if(players == 4 && !teamPlay){
            if(getPlayer(x, y) == 3 || getPlayer(x, y) == 0) {
                for (int i = 0, j = 4; i < 3 && j >= 0; j--) {
                    if (x == i && y == j) { return true; }
                    if (y == i && x == j) { return true; }
                    if (j == i) { i++; j = 6 - i; }
                }
            }
            if(getPlayer(x, y) == 4 || getPlayer(x, y) == 0) {
                for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                    if(x == i && y == j) { return true; }
                    if(j == 15) { i++; j = 10 + i; }
                }
            }
            if(getPlayer(x, y) == 1 || getPlayer(x, y) == 0) {
                for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                    if(x == i && y == j) { return true; }
                    if(y == i && x == j) { return true; }
                    if(j == i) { i--; j = 24 - i; }
                }
            }
            if(getPlayer(x, y) == 2 || getPlayer(x, y) == 0) {
                for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                    if(x == i && y == j) { return true; }
                    if(i == 15) { j++; i = 10 + j; }
                }
            }
            return false;
        }else {
            if(getPlayer(x, y) == 2 || getPlayer(x, y) == 4 || getPlayer(x, y) == 0) {
                for (int i = 0, j = 4; i < 3 && j >= 0; j--) {
                    if (x == i && y == j) { return true; }
                    if (y == i && x == j) { return true; }
                    if (j == i) { i++; j = 6 - i; }
                }
            }
            if(getPlayer(x, y) == 1 || getPlayer(x, y) == 3 || getPlayer(x, y) == 0) {
                for(int i = 0, j = 12; i < 4 && j <= 15; j++){
                    if(x == i && y == j) { return true; }
                    if(j == 15) { i++; j = 10 + i; }
                }
            }
            if(getPlayer(x, y) == 2 || getPlayer(x, y) == 3 || getPlayer(x, y) == 0) {
                for(int i = 15, j = 11; i > 12 && j <= 15; j++){
                    if(x == i && y == j) { return true; }
                    if(y == i && x == j) { return true; }
                    if(j == i) { i--; j = 24 - i; }
                }
            }
            if(getPlayer(x, y) == 1 || getPlayer(x, y) == 3 || getPlayer(x, y) == 0) {
                for(int i = 12, j = 0; j < 4 && i <= 15; i++){
                    if(x == i && y == j) { return true; }
                    if(i == 15) { j++; i = 10 + j; }
                }
            }
            return false;
        }
    }

    public static void nextRole(){
        if(players == 2 && role == 2) { role = 0; round++; /*System.out.println("round" + round + "start!");*/ }
        else if(players == 4 && role == 4) { role = 0; round++; /*System.out.println("round" + round + "start!");*/ }
        role++;
        hopped = false;
//        System.out.println("next role!");
        winnerCheck();
    }

    public static String getRow(int numRow){
        StringBuilder row = new StringBuilder();
        for(int i = 0; i < 16; i++){
            row.append(board[numRow][i]);
        }
        return row.toString();
    }

    public static void reset(){
        board = new int[16][16];
        players = 0;
        role = 0;
        round = 0;
        winner = 0;
        multiHopping = true;
        hopped = false;
        hopX = 17;
        hopY = 17;
        allowGoBack = false;
        lastX = 17;
        lastY = 17;
    }

    public static int getPlayer(int x, int y){
        return board[x][y];
    }

    public static int getPlayers() { return players; }
    public static int getRole() { return role; }
    public static int getRound() { return round; }
    public static int[][] getBoard() { return board; }
    public static boolean isMultiHopping() { return multiHopping; }
    public static boolean getHopped() { return hopped; }
    public static int getWinner() { return winner; }
    public static boolean isHopped() { return hopped; }
    public static int getHopX() { return hopX; }
    public static int getHopY() { return hopY; }
    public static boolean isAllowGoBack() { return allowGoBack; }
    public static int getLastX() { return lastX; }
    public static int getLastY() { return lastY; }
    public static boolean isTeamPlay() { return teamPlay; }

    public static void setPlayers(int players) { GamePlay.players = players; }
    public static void setRole(int role) { GamePlay.role = role; }
    public static void setRound(int round) { GamePlay.round = round; }
    public static void rowSetBoard(String rowIn, int row) {
        for (int i = 0; i < 16; i++) {
            board[row][i] = Integer.parseInt(rowIn.substring(i, i + 1));
        }
    }
    public static void setMultiHopping(boolean enableHopping) { GamePlay.multiHopping = enableHopping; }
    public static void setHopped(boolean hopped) { GamePlay.hopped = hopped; }
    public static void setHopX(int hopX) { GamePlay.hopX = hopX; }
    public static void setHopY(int hopY) { GamePlay.hopY = hopY; }
    public static void setAllowGoBack(boolean allowGoBack) { GamePlay.allowGoBack = allowGoBack; }
    public static void setLastX(int lastX) { GamePlay.lastX = lastX; }
    public static void setLastY(int lastY) { GamePlay.lastY = lastY; }
    public static void setTeamPlay(boolean teamPlay) { GamePlay.teamPlay = teamPlay; }
}
