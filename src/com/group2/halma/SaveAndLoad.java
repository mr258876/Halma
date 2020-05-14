package com.group2.halma;

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
            e.printStackTrace();
        }
    }

    protected static void load(File file){
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
            e.printStackTrace();
        }

        GamePlay.setPlayers(Integer.parseInt(list.get(0)));
        GamePlay.setRole(Integer.parseInt(list.get(1)));
        GamePlay.setRound(Integer.parseInt(list.get(2)));
        if(list.get(3).equals("false")){ GamePlay.setMultiHopping(false); }
        else { GamePlay.setMultiHopping(true); }
        if(list.get(4).equals("false")){ GamePlay.setHopped(false); }
        else { GamePlay.setHopped(true); }
        GamePlay.setHopX(Integer.parseInt(list.get(5)));
        GamePlay.setHopY(Integer.parseInt(list.get(6)));
        if(list.get(7).equals("false")){ GamePlay.setAllowGoBack(false); }
        else { GamePlay.setAllowGoBack(true); }
        GamePlay.setLastX(Integer.parseInt(list.get(8)));
        GamePlay.setLastY(Integer.parseInt(list.get(9)));
        for(int i = 0; i < 16; i++){
            GamePlay.rowSetBoard(list.get(10+i), i);
        }

    }
}
