package com.group2.halma.UI;

import com.group2.halma.GamePlay.GamePlay;

import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad {
    protected static void save(File file){
        ArrayList<String> list = new ArrayList<>();
        list.add(String.valueOf(GamePlay.getPlayers()));
        list.add(String.valueOf(GamePlay.getRole()));
        list.add(String.valueOf(GamePlay.getRound()));
        list.add(String.valueOf(GamePlay.isMultiHopping()));
        list.add(String.valueOf(GamePlay.isHopped()));
        list.add(String.valueOf(GamePlay.getHopX()));
        list.add(String.valueOf(GamePlay.getHopY()));
        list.add(String.valueOf(GamePlay.isAllowGoBack()));
        list.add(String.valueOf(GamePlay.getLastX()));
        list.add(String.valueOf(GamePlay.getLastY()));
        list.add(String.valueOf(GamePlay.isTeamPlay()));
        for(int i = 0; i < 16; i++){
            list.add(GamePlay.getRow(i));
        }

        FileOutputStream fileOutputStream = null;
        try {
            if(file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            for(String i : list){
                fileOutputStream.write((i + "\n").getBytes());
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
//            e.printStackTrace();
            UI.saveError();
        }
    }

    protected static boolean load(File file){
        ArrayList<String> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String text = null;
            while((text = bufferedReader.readLine()) != null){
                list.add(text);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            UI.loadError();
            return false;
        }

        if(!fileCheck(list)) { return false; }

        try {
            GamePlay.reset();
            GamePlay.setPlayers(Integer.parseInt(list.get(0)));
            GamePlay.setRole(Integer.parseInt(list.get(1)));
            GamePlay.setRound(Integer.parseInt(list.get(2)));
            if (list.get(3).equals("false")) { GamePlay.setMultiHopping(false); }
            else { GamePlay.setMultiHopping(true); }
            if (list.get(4).equals("false")) { GamePlay.setHopped(false); }
            else { GamePlay.setHopped(true); }
            GamePlay.setHopX(Integer.parseInt(list.get(5)));
            GamePlay.setHopY(Integer.parseInt(list.get(6)));
            if (list.get(7).equals("false")) { GamePlay.setAllowGoBack(false); }
            else { GamePlay.setAllowGoBack(true); }
            GamePlay.setLastX(Integer.parseInt(list.get(8)));
            GamePlay.setLastY(Integer.parseInt(list.get(9)));
            if (list.get(10).equals("false")) { GamePlay.setTeamPlay(false); }
            else { GamePlay.setTeamPlay(true); }
            for (int i = 0; i < 16; i++) {
                GamePlay.rowSetBoard(list.get(11 + i), i);
            }
        } catch (Exception e) {
            UI.loadError();
            return false;
        }

        if(GamePlay.winnerCheck()){ UI.loadError(3); return false; }

        return true;
    }

    protected static boolean fileCheck(ArrayList<String> list){
        int players;
        int role;
        try {
            players = Integer.parseInt(list.get(0));
            role = Integer.parseInt(list.get(1));
        } catch (Exception e) {
                return false;
        }

        if(players != 2 && players != 4) { UI.loadError(4); return false; }
        if(0 >= role || role > players) { UI.loadError(4); return false; }

        int p1c = 0;
        int p2c = 0;
        int p3c = 0;
        int p4c = 0;
        try{
            for (int i = 0; i < 16; i++) {
                String s = list.get(11 + i);
                if(s.length() != 16) { UI.loadError(2); return false; }
                for (int j = 0; j < 16; j++) {
                    if(s.substring(j,j+1).equals("1")) p1c++;
                    else if(s.substring(j,j+1).equals("2")) p2c++;
                    else if(s.substring(j,j+1).equals("3")) p3c++;
                    else if(s.substring(j,j+1).equals("4")) p4c++;
                    else if(s.substring(j,j+1).equals("0")) ;
                    else return false;
                }
            }
        } catch (Exception e) {
            UI.loadError(2);
            return false;
        }


        if(players == 2) {
            if (p1c != 19 || p2c != 19 || p3c!= 0 || p4c != 0) { UI.loadError(1); return false; }
        } else {
            if (p1c != 13 || p2c != 13 || p3c!= 13 || p4c != 13) { UI.loadError(1); return false; }
        }

        return true;
    }
}
