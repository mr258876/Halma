package com.group2.halma;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;


public class UI {

    protected static ArrayList<String> langLines = null;
    private static String language = "zh-CN";
    private static JFrame f;

    public static JFrame mainFramework(){
        f = new JFrame("Project Halma");
        f.setSize(500, 400);
        f.setLocation(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lang(language);
        f.setLayout(new FlowLayout(FlowLayout.CENTER,200,25));

        JLabel title = new JLabel();
        title.setFont(new java.awt.Font("微软雅黑", 1, 30));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(Color.blue);
        JPanel menu = new JPanel();

        f.add(title);
        f.add(menu);

        return f;
    }

    protected static void mainReset(){
        f.setLayout(new FlowLayout(FlowLayout.CENTER,200,25));

        JLabel title = new JLabel();
        title.setFont(new java.awt.Font("微软雅黑", 1, 30));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(Color.blue);
        JPanel menu = new JPanel();

        f.add(title);
        f.add(menu);
    }

    protected static void lang(String lang){
        langLines = new ArrayList<>();
        try {
            InputStream in = UI.class.getClassLoader().getResourceAsStream("lang/" + lang + ".txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            String s;
            while ((s=bf.readLine()) != null) {
                langLines.add(s);
            }
        } catch (IOException e){
                e.printStackTrace();
        }
    }

    protected static JLabel getTitle(JFrame f){
        return (JLabel) f.getRootPane().getContentPane().getComponent(0);
    }

    protected static JPanel getMenu(JFrame f){
        return (JPanel) f.getRootPane().getContentPane().getComponent(1);
    }

    protected static void clearMenu(JFrame f){
        for(Component i : getMenu(f).getComponents()){
            i.setVisible(false);
            getMenu(f).remove(i);
        }
    }

    protected static void addBack(JFrame f){
        JButton back = new JButton(langLines.get(32));
        back.setPreferredSize(new Dimension(75,35));
        f.getRootPane().getContentPane().add(back);
    }

    protected static JButton getBack(JFrame f){
        return (JButton) f.getRootPane().getContentPane().getComponent(2);
    }

    protected static void removeBack(JFrame f){
        f.getRootPane().getContentPane().getComponent(2).setVisible(false);
        f.getRootPane().getContentPane().remove(2);
    }

    protected static void addFinish(JFrame f){
        JButton back = new JButton(langLines.get(69));
        back.setPreferredSize(new Dimension(75,35));
        f.getRootPane().getContentPane().add(back);
    }

    protected static void mainMenu(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(1));

        getMenu(f).setLayout(new GridLayout(5,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 200));

        JButton newGame = new JButton(langLines.get(3));
        newGame.addActionListener(e -> newGame(f));
        getMenu(f).add(newGame);

        JButton loadGame = new JButton(langLines.get(4));
        loadGame.addActionListener(e -> loadGame(f));
        getMenu(f).add(loadGame);

        JButton settings = new JButton(langLines.get(5));
        settings.addActionListener(e -> settings(f, false));
        getMenu(f).add(settings);

        JButton about = new JButton(langLines.get(6));
        about.addActionListener(e -> about(f));
        getMenu(f).add(about);

        JButton exit = new JButton(langLines.get(7));
        exit.addActionListener(e -> System.exit(0));
        getMenu(f).add(exit);


        System.out.println("main menu done!");
    }

    protected static void newGame(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(9));

        getMenu(f).setLayout(new GridLayout(2,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 80));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            mainMenu(f);
        });

        JButton single = new JButton(langLines.get(11));
        single.addActionListener(e -> {
            removeBack(f);
            singlePlay(f);
        });
        getMenu(f).add(single);

        JButton multi = new JButton(langLines.get(12));
        multi.addActionListener(e -> {
            removeBack(f);
            multiPlay(f);
        });
        getMenu(f).add(multi);

        System.out.println("newGame menu done!");
    }

    protected static void loadGame(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(39));

        getMenu(f).setLayout(new GridLayout(1,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150,35));

        JButton open = new JButton(langLines.get(41));
        open.addActionListener(e -> {
            if(load(f)) {
                removeBack(f);
                play(f);
            }
        });
        getMenu(f).add(open);

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            mainMenu(f);
        });

        System.out.println("loadGame menu done!");
    }

    protected static void settings(JFrame f, boolean inGame){
        f.setTitle(langLines.get(5));
        clearMenu(f);

        getTitle(f).setText(langLines.get(25));


        if(inGame) {
            getMenu(f).setLayout(new GridLayout(2,1,10,10));
            getMenu(f).setPreferredSize(new Dimension(150, 80));
        } else {
            getMenu(f).setLayout(new GridLayout(4,1,10,10));
            getMenu(f).setPreferredSize(new Dimension(150, 170));
        }

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            if(inGame) { f.dispose();}
            else { mainMenu(f); }
        });

        JButton lang = new JButton(langLines.get(27));
        JButton theme = new JButton(langLines.get(28));
        JButton mHopping = new JButton();
        if(GamePlay.isMultiHopping()) { mHopping.setText(langLines.get(55) + langLines.get(57)); }
        else { mHopping.setText(langLines.get(55) + langLines.get(58)); }
        JButton GoBack = new JButton();
        if(GamePlay.isAllowGoBack()) { GoBack.setText(langLines.get(85) + langLines.get(57)); }
        else { GoBack.setText(langLines.get(85) + langLines.get(58)); }

        lang.addActionListener(e -> {
            removeBack(f);
            setLang(f, inGame);
        });
        theme.addActionListener(e -> {
            removeBack(f);
            theme(f, inGame);
        });
        mHopping.addActionListener(e -> {
            GamePlay.setMultiHopping(!GamePlay.isMultiHopping());
            if(GamePlay.isMultiHopping()) { mHopping.setText(langLines.get(55) + langLines.get(57)); }
            else { mHopping.setText(langLines.get(55) + langLines.get(58)); }
        });
        GoBack.addActionListener(e -> {
            GamePlay.setAllowGoBack(!GamePlay.isAllowGoBack());
            if(GamePlay.isAllowGoBack()) { GoBack.setText(langLines.get(55) + langLines.get(57)); }
            else { GoBack.setText(langLines.get(55) + langLines.get(58)); }
        });

        getMenu(f).add(lang);
        getMenu(f).add(theme);
        if(!inGame) { getMenu(f).add(mHopping); }
//        if(!inGame) { getMenu(f).add(GoBack); }

        System.out.println("setting menu done!");
    }

    protected static void setLang(JFrame f, boolean inGame){
        clearMenu(f);

        getTitle(f).setText(langLines.get(34));

        getMenu(f).setLayout(new GridLayout(2,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 80));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            settings(f, inGame);
        });

//        To add a new language:
//        put language file (*.txt) in /lang
//        and then copy the JButton chs below
//        change "zh-CN" and the JButton name into your language
//        And dont forget to change the panel size above!

        JButton chs = new JButton(langLines.get(36));
        chs.addActionListener(e -> {
            removeBack(f);
            language = "zh-CN";
            lang("zh-CN");
            System.out.println("set language as zh-CN");
            if(inGame) { playReset(); settings(f, inGame); }
            else { mainMenu(f); }
        });
        getMenu(f).add(chs);

        JButton eng = new JButton(langLines.get(37));
        eng.addActionListener(e -> {
            removeBack(f);
            language = "en-US";
            lang("en-US");
            System.out.println("set language as en-US");
            if(inGame) { playReset();  settings(f, inGame); }
            else { mainMenu(f); }
        });
        getMenu(f).add(eng);

        System.out.println("setLang menu done!");
    }

    protected static void about(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(30));

        getMenu(f).setLayout(new GridLayout(1,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 80));
        JLabel aboutText = new JLabel("by Mr.258876");
        getMenu(f).add(aboutText);

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            mainMenu(f);
        });

        System.out.println("about done!");
    }

    protected static void singlePlay(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(14));

        getMenu(f).setLayout(new GridLayout(3,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 125));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            newGame(f);
        });

        JButton easy = new JButton(langLines.get(16));
        easy.addActionListener(e -> {
            removeBack(f);
            GamePlay gp = new GamePlay(1);
//            AI.easy();
            play(f);
        });
        getMenu(f).add(easy);

        JButton medium = new JButton(langLines.get(17));
        medium.addActionListener(e -> {
            removeBack(f);
            GamePlay gp = new GamePlay(1);
//            AI.medium();
            play(f);
        });
        getMenu(f).add(medium);

        JButton hard = new JButton(langLines.get(18));
        hard.addActionListener(e -> {
            removeBack(f);
            GamePlay gp = new GamePlay(1);
//            AI.hard();
            play(f);
        });
        getMenu(f).add(hard);

        System.out.println("singlePlay menu done!");
    }

    protected static void multiPlay(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(20));

        getMenu(f).setLayout(new GridLayout(2,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 80));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            newGame(f);
        });

        JButton dual = new JButton(langLines.get(22));
        dual.addActionListener(e -> {
            removeBack(f);
            GamePlay gp = new GamePlay(2);
            play(f);
        });
        getMenu(f).add(dual);

        JButton quad = new JButton(langLines.get(23));
        quad.addActionListener(e -> {
            removeBack(f);
            GamePlay gp = new GamePlay(4);
            play(f);
        });
        getMenu(f).add(quad);

        System.out.println("multiPlay menu done!");
    }

    protected static void play(JFrame f){
        SidePanel pL = new SidePanel(f);

        BackgroundBoard Bg = new BackgroundBoard();
        Board Br = new Board(pL);
        JPanel pR = new JPanel();
        pR.setLayout(new OverlayLayout(pR));
        pR.add(Br);pR.add(Bg);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pL, pR);
        sp.setDividerLocation(100);
        f.setContentPane(sp);
    }

    protected static void playReset(){
//        Use this only when is in playing
        f.setContentPane(new Container());
        mainReset();
        mainMenu(f);
        play(f);
    }

    protected static void win(){
        JOptionPane.showMessageDialog(f, langLines.get(50) + GamePlay.getWinner() + langLines.get(51));
        f.setContentPane(new Container());
        mainReset();
        mainMenu(f);
    }

    protected static JFrame inGameFramework(){
        JFrame fi = new JFrame(langLines.get(25));
        fi.setSize(250, 350);
        fi.setLocation(300, 300);
        lang(language);
        fi.setLayout(new FlowLayout(FlowLayout.CENTER,200,25));

        JLabel title = new JLabel();
        title.setFont(new java.awt.Font("微软雅黑", 1, 20));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(Color.blue);

        JPanel menu = new JPanel();
        menu.setLayout(new GridLayout(2,1,10,10));
        menu.setPreferredSize(new Dimension(150, 80));

        fi.add(title);
        fi.add(menu);

        fi.setVisible(true);
        System.out.println("inGame framework done!");

        return fi;
    }

    protected static void theme(JFrame f, boolean inGame){
        clearMenu(f);

        getTitle(f).setText(langLines.get(28));

        getMenu(f).setLayout(new GridLayout(3,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 125));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            settings(f, inGame);
        });

        JButton pieceColor = new JButton(langLines.get(60));
        JButton boardColor = new JButton(langLines.get(61));

        pieceColor.addActionListener(e -> {
            removeBack(f);
            pieceColor(f ,inGame);
        });
        boardColor.addActionListener(e -> {
            removeBack(f);
            boardColor(f, inGame);
        });

        getMenu(f).add(pieceColor);
        getMenu(f).add(boardColor);

        System.out.println("theme menu done!");
    }

    protected static void save(JFrame f){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                // TODO Auto-generated method stub
                return ".txt";
            }

            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".txt");
            }
        });
        fc.changeToParentDirectory();

        int returnVal =  fc.showSaveDialog(f);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(f, langLines.get(48) + file.getAbsolutePath());
            SaveAndLoad.save(file);
        }
    }

    protected static boolean load(JFrame f){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {
            @Override
            public String getDescription() {
                // TODO Auto-generated method stub
                return ".txt";
            }

            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith(".txt");
            }
        });
        fc.changeToParentDirectory();

        int returnVal = fc.showOpenDialog(f);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(f, langLines.get(39) + file.getAbsolutePath());
            SaveAndLoad.load(file);
            return true;
        }else {
            return false;
        }
    }

    protected static void pieceColor(JFrame f, boolean inGame){
        ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.red, Color.green, Color.yellow, Color.black, Color.blue,
                Color.pink, Color.white, Color.cyan, Color.orange, Color.darkGray, Color.gray, Color.lightGray,
                Color.magenta));

        String[] strColors = new String[] {langLines.get(71), langLines.get(72), langLines.get(73), langLines.get(74),
                langLines.get(75), langLines.get(76), langLines.get(77), langLines.get(78), langLines.get(79),
                langLines.get(80), langLines.get(81), langLines.get(82), langLines.get(83)};

        clearMenu(f);

        getTitle(f).setText(langLines.get(60));

        getMenu(f).setLayout(new GridLayout(5,2,3,10));
        getMenu(f).setPreferredSize(new Dimension(150, 125));

        addFinish(f);

        JLabel p1 = new JLabel(langLines.get(63));
        JLabel p2 = new JLabel(langLines.get(64));
        JLabel p3 = new JLabel(langLines.get(65));
        JLabel p4 = new JLabel(langLines.get(66));
        JLabel p5 = new JLabel(langLines.get(67));

        JComboBox<String> cp1 = new JComboBox<>(strColors);
        cp1.setSelectedIndex(colors.indexOf(Board.getPieceList()[0]));
        JComboBox<String> cp2 = new JComboBox<>(strColors);
        cp2.setSelectedIndex(colors.indexOf(Board.getPieceList()[1]));
        JComboBox<String> cp3 = new JComboBox<>(strColors);
        cp3.setSelectedIndex(colors.indexOf(Board.getPieceList()[2]));
        JComboBox<String> cp4 = new JComboBox<>(strColors);
        cp4.setSelectedIndex(colors.indexOf(Board.getPieceList()[3]));
        JComboBox<String> cp5 = new JComboBox<>(strColors);
        cp5.setSelectedIndex(colors.indexOf(Board.getPieceList()[4]));

        getBack(f).addActionListener(e -> {
            Board.getPieceList()[0] = colors.get(cp1.getSelectedIndex());
            Board.getPieceList()[1] = colors.get(cp2.getSelectedIndex());
            Board.getPieceList()[2] = colors.get(cp3.getSelectedIndex());
            Board.getPieceList()[3] = colors.get(cp4.getSelectedIndex());
            Board.getPieceList()[4] = colors.get(cp5.getSelectedIndex());
            if(inGame) { playReset(); }
            removeBack(f);
            settings(f, inGame);
        });

        getMenu(f).add(p1);
        getMenu(f).add(cp1);
        getMenu(f).add(p2);
        getMenu(f).add(cp2);
        getMenu(f).add(p3);
        getMenu(f).add(cp3);
        getMenu(f).add(p4);
        getMenu(f).add(cp4);
        getMenu(f).add(p5);
        getMenu(f).add(cp5);

    }

    protected static void boardColor(JFrame f, boolean inGame){
        ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.red, Color.green, Color.yellow, Color.black, Color.blue,
                Color.pink, Color.white, Color.cyan, Color.orange, Color.darkGray, Color.gray, Color.lightGray,
                Color.magenta));

        String[] strColors = new String[] {langLines.get(71), langLines.get(72), langLines.get(73), langLines.get(74),
                langLines.get(75), langLines.get(76), langLines.get(77), langLines.get(78), langLines.get(79),
                langLines.get(80), langLines.get(81), langLines.get(82), langLines.get(83)};

        clearMenu(f);

        getTitle(f).setText(langLines.get(61));

        getMenu(f).setLayout(new GridLayout(2,2,3,10));
        getMenu(f).setPreferredSize(new Dimension(150, 60));

        addFinish(f);

        JLabel p1 = new JLabel(langLines.get(87));
        JLabel p2 = new JLabel(langLines.get(88));

        JComboBox<String> cp1 = new JComboBox<>(strColors);
        cp1.setSelectedIndex(colors.indexOf(Board.getBoardList()[0]));
        JComboBox<String> cp2 = new JComboBox<>(strColors);
        cp2.setSelectedIndex(colors.indexOf(Board.getBoardList()[1]));

        getBack(f).addActionListener(e -> {
            Board.getBoardList()[0] = colors.get(cp1.getSelectedIndex());
            Board.getBoardList()[1] = colors.get(cp2.getSelectedIndex());
            if(inGame) { playReset(); }
            removeBack(f);
            settings(f, inGame);
        });

        getMenu(f).add(p1);
        getMenu(f).add(cp1);
        getMenu(f).add(p2);
        getMenu(f).add(cp2);
    }
}