package com.group2.halma.UI;

import com.group2.halma.Board.BackgroundBoard;
import com.group2.halma.Board.Board;
import com.group2.halma.Board.SidePanel;
import com.group2.halma.GamePlay.GamePlay;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;


public class UI {

    public static ArrayList<String> langLines = null;
    private static String language = "zh-CN";
    private static MainFrame f;
    private static ImageIcon icon;

    public static ArrayList<String> getLangLines() { return langLines; }
    public static ImageIcon getIcon() { return icon; }

    public static JFrame mainFramework(){
        icon = new ImageIcon(Objects.requireNonNull(UI.class.getClassLoader().getResource("icon.png")));

        lang(language);

        f = new MainFrame();

        return f;
    }

    protected static void setFrame(JFrame f){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JLabel title = new JLabel();
        title.setFont(new java.awt.Font("微软雅黑", Font.BOLD, 30));
        title.setForeground(Color.white);
        title.setOpaque(true);
        title.setBackground(Color.blue);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(title, gbc);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel menu = new JPanel();
        menuPanel.add(menu);

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.2;
        f.add(titlePanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.6;
        f.add(menuPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 0.2;
        f.add(backPanel, gbc);
    }

    public static void mainReset(){ f.mainReset(); }

    public static void lang(String lang){
        langLines = new ArrayList<>();
        try {
            InputStream in = UI.class.getClassLoader().getResourceAsStream("lang/" + lang + ".txt");
            assert in != null;
            BufferedReader bf = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String s;
            while ((s=bf.readLine()) != null) {
                langLines.add(s);
            }
        } catch (IOException e){
//                e.printStackTrace();
        }
    }

    private static JPanel getTitlePanel(JFrame f){ return (JPanel) f.getRootPane().getContentPane().getComponent(0); }

    private static JLabel getTitle(JFrame f){ return (JLabel) getTitlePanel(f).getComponent(0); }

    private static JPanel getMenuPanel(JFrame f){ return (JPanel) f.getRootPane().getContentPane().getComponent(1); }

    private static JPanel getMenu(JFrame f) { return (JPanel) getMenuPanel(f).getComponent(0); }

    private static void clearMenu(JFrame f){
        for(Component i : getMenu(f).getComponents()){
            i.setVisible(false);
            getMenu(f).remove(i);
        }
    }

    private static void addBack(JFrame f){
        JButton back = new JButton(langLines.get(32));
        back.setPreferredSize(new Dimension(75,35));
        getBackPanel(f).add(back);
    }

    private static JPanel getBackPanel(JFrame f){ return (JPanel) f.getRootPane().getContentPane().getComponent(2); }

    private static JButton getBack(JFrame f) { return (JButton) getBackPanel(f).getComponent(0); }

    private static void removeBack(JFrame f){
        getBackPanel(f).getComponent(0).setVisible(false);
        getBackPanel(f).remove(0);
    }

    private static void addFinish(JFrame f){
        JButton back = new JButton(langLines.get(69));
        back.setPreferredSize(new Dimension(75,35));
        getBackPanel(f).add(back);
    }

    public static void mainMenu(JFrame f){
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


//        System.out.println("main menu done!");
    }

    private static void newGame(JFrame f){
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
        single.setVisible(false);
        getMenu(f).add(single);

        JButton multi = new JButton(langLines.get(12));
        multi.addActionListener(e -> {
            removeBack(f);
            multiPlay(f);
        });
        getMenu(f).add(multi);

//        System.out.println("newGame menu done!");
    }

    private static void loadGame(JFrame f){
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

//        System.out.println("loadGame menu done!");
    }

    public static void settings(JFrame f, boolean inGame){
        if(inGame) f.setTitle(langLines.get(5));
        clearMenu(f);

        getTitle(f).setText(langLines.get(25));


        if(inGame) {
            getMenu(f).setLayout(new GridLayout(3,1,10,10));
            getMenu(f).setPreferredSize(new Dimension(150, 125));
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
        JButton ShowTips = new JButton();
        if(Board.isShowTips()) { ShowTips.setText(langLines.get(85) + langLines.get(57)); }
        else { ShowTips.setText(langLines.get(85) + langLines.get(58)); }

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
        ShowTips.addActionListener(e -> {
            Board.setShowTips(!Board.isShowTips());
            if(Board.isShowTips()) { ShowTips.setText(langLines.get(85) + langLines.get(57)); }
            else { ShowTips.setText(langLines.get(85) + langLines.get(58)); }
        });

        getMenu(f).add(lang);
        getMenu(f).add(theme);
        if(!inGame) { getMenu(f).add(mHopping); }
        getMenu(f).add(ShowTips);

//        System.out.println("setting menu done!");
    }

    private static void setLang(JFrame f, boolean inGame){
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
//            System.out.println("set language as zh-CN");
            if(inGame) { playReset(); settings(f, inGame); }
            else { mainMenu(f); }
        });
        getMenu(f).add(chs);

        JButton eng = new JButton(langLines.get(37));
        eng.addActionListener(e -> {
            removeBack(f);
            language = "en-US";
            lang("en-US");
//            System.out.println("set language as en-US");
            if(inGame) { playReset();  settings(f, inGame); }
            else { mainMenu(f); }
        });
        getMenu(f).add(eng);

//        System.out.println("setLang menu done!");
    }

    private static void about(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(30));

        getMenu(f).setLayout(new GridLayout(2,1,0,0));
        getMenu(f).setPreferredSize(new Dimension(150, 200));
        JLabel aboutIcon = new JLabel(icon);
        JLabel aboutText = new JLabel("by Mr.258876. May 2020");
        getMenu(f).add(aboutIcon);
        getMenu(f).add(aboutText);

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            mainMenu(f);
        });

//        System.out.println("about done!");
    }

    private static void singlePlay(JFrame f){
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
            new GamePlay(1);
//            AI.easy();
            play(f);
        });
        getMenu(f).add(easy);

        JButton medium = new JButton(langLines.get(17));
        medium.addActionListener(e -> {
            removeBack(f);
            new GamePlay(1);
//            AI.medium();
            play(f);
        });
        getMenu(f).add(medium);

        JButton hard = new JButton(langLines.get(18));
        hard.addActionListener(e -> {
            removeBack(f);
            new GamePlay(1);
//            AI.hard();
            play(f);
        });
        getMenu(f).add(hard);

//        System.out.println("singlePlay menu done!");
    }

    private static void multiPlay(JFrame f){
        clearMenu(f);

        getTitle(f).setText(langLines.get(20));

        getMenu(f).setLayout(new GridLayout(3,1,10,10));
        getMenu(f).setPreferredSize(new Dimension(150, 125));

        addBack(f);
        getBack(f).addActionListener(e -> {
            removeBack(f);
            newGame(f);
        });

        JButton dual = new JButton(langLines.get(22));
        dual.addActionListener(e -> {
            removeBack(f);
            new GamePlay(2);
            play(f);
        });
        getMenu(f).add(dual);

        JButton quad = new JButton(langLines.get(23));
        quad.addActionListener(e -> {
            removeBack(f);
            new GamePlay(4);
            play(f);
        });
        getMenu(f).add(quad);

        JButton quadT = new JButton(langLines.get(108));
        quadT.addActionListener(e -> {
            removeBack(f);
            new GamePlay(4);
            GamePlay.setTeamPlay(true);
            play(f);
        });
        getMenu(f).add(quadT);

//        System.out.println("multiPlay menu done!");
    }

    private static void play(JFrame f){
        SidePanel pL = new SidePanel(f);

        BackgroundBoard Bg = new BackgroundBoard();
        Board Br = new Board(pL);
        pL.setBoard(Br);
        JPanel pR = new JPanel();
        pR.setLayout(new OverlayLayout(pR));
        pR.add(Br);pR.add(Bg);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pL, pR);
        sp.setDividerLocation(100);
        f.setContentPane(sp);

//        System.out.println("play done!");
    }

    public static void playReset(){
//        Use this only when is in playing
        f.setContentPane(new Container());
        mainReset();
        mainMenu(f);
        play(f);
    }

    public static void win(){
        if(!GamePlay.isTeamPlay()) {
            JOptionPane.showMessageDialog(f, langLines.get(50) + GamePlay.getWinner() + langLines.get(51));
        }else {
            JOptionPane.showMessageDialog(f, langLines.get(109) + GamePlay.getWinner() + langLines.get(51));
        }
        f.setContentPane(new Container());
        mainReset();
        mainMenu(f);
    }

    private static void theme(JFrame f, boolean inGame){
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

//        System.out.println("theme menu done!");
    }

    public static void save(JFrame f){
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
//        fc.changeToParentDirectory();

        int returnVal =  fc.showSaveDialog(f);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(f, langLines.get(48) + file.getAbsolutePath());
            SaveAndLoad.save(file);
        }
    }

    public static void saveError(){ JOptionPane.showMessageDialog(f, langLines.get(99)); }

    public static boolean load(JFrame f){
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
//        fc.setCurrentDirectory(new File(String.valueOf(UI.class.getResource("/"))));

        int returnVal = fc.showOpenDialog(f);
        File file = fc.getSelectedFile();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(f, langLines.get(39) + file.getAbsolutePath());
            return SaveAndLoad.load(file);
        }else {
            return false;
        }
    }

    public static void loadError(){ JOptionPane.showMessageDialog(f, langLines.get(97)); }

    public static void loadError(int e){
        switch(e){
            case 0:
                JOptionPane.showMessageDialog(f, langLines.get(97));
                break;
            case 1:
                JOptionPane.showMessageDialog(f, langLines.get(103));
                break;
            case 2:
                JOptionPane.showMessageDialog(f, langLines.get(104));
                break;
            case 3:
                JOptionPane.showMessageDialog(f, langLines.get(105));
                break;
            case 4:
                JOptionPane.showMessageDialog(f, langLines.get(106));
                break;
        }
    }

    private static void pieceColor(JFrame f, boolean inGame){
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.red, Color.green, Color.yellow, Color.black,
                Color.blue, Color.pink, Color.white, Color.cyan, Color.orange, Color.darkGray, Color.gray,
                Color.lightGray, Color.magenta));

        String[] strColors = new String[] {langLines.get(71), langLines.get(72), langLines.get(73), langLines.get(74),
                langLines.get(75), langLines.get(76), langLines.get(77), langLines.get(78), langLines.get(79),
                langLines.get(80), langLines.get(81), langLines.get(82), langLines.get(83)};

        clearMenu(f);

        getTitle(f).setText(langLines.get(60));

        getMenu(f).setLayout(new GridLayout(6,2,3,10));
        getMenu(f).setPreferredSize(new Dimension(150, 160));

        addFinish(f);

        JLabel p0 = new JLabel(langLines.get(63));
        JLabel p1 = new JLabel(langLines.get(64));
        JLabel p2 = new JLabel(langLines.get(65));
        JLabel p3 = new JLabel(langLines.get(66));
        JLabel p4 = new JLabel(langLines.get(67));
        JLabel p5 = new JLabel(langLines.get(95));

        JComboBox<String> cp0 = new JComboBox<>(strColors);
        cp0.setSelectedIndex(colors.indexOf(Board.getPieceList()[0]));
        JComboBox<String> cp1 = new JComboBox<>(strColors);
        cp1.setSelectedIndex(colors.indexOf(Board.getPieceList()[1]));
        JComboBox<String> cp2 = new JComboBox<>(strColors);
        cp2.setSelectedIndex(colors.indexOf(Board.getPieceList()[2]));
        JComboBox<String> cp3 = new JComboBox<>(strColors);
        cp3.setSelectedIndex(colors.indexOf(Board.getPieceList()[3]));
        JComboBox<String> cp4 = new JComboBox<>(strColors);
        cp4.setSelectedIndex(colors.indexOf(Board.getPieceList()[4]));
        JComboBox<String> cp5 = new JComboBox<>(strColors);
        cp5.setSelectedIndex(colors.indexOf(Board.getPieceList()[5]));

        getBack(f).addActionListener(e -> {
            Board.getPieceList()[0] = colors.get(cp0.getSelectedIndex());
            Board.getPieceList()[1] = colors.get(cp1.getSelectedIndex());
            Board.getPieceList()[2] = colors.get(cp2.getSelectedIndex());
            Board.getPieceList()[3] = colors.get(cp3.getSelectedIndex());
            Board.getPieceList()[4] = colors.get(cp4.getSelectedIndex());
            Board.getPieceList()[5] = colors.get(cp5.getSelectedIndex());
            if(inGame) { playReset(); }
            removeBack(f);
            settings(f, inGame);
        });

        getMenu(f).add(p0);
        getMenu(f).add(cp0);
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

    private static void boardColor(JFrame f, boolean inGame){
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(new Color(255, 255, 204),
                new Color(170, 170, 170), new Color(241, 241, 241),
                new Color(152, 152, 152)));

        String[] strColors = new String[] {langLines.get(90), langLines.get(91), langLines.get(92), langLines.get(93)};

        clearMenu(f);

        getTitle(f).setText(langLines.get(61));

        getMenu(f).setLayout(new GridLayout(2,2,3,10));
        getMenu(f).setPreferredSize(new Dimension(150, 60));

        addFinish(f);

        JLabel p1 = new JLabel(langLines.get(87));
        JLabel p2 = new JLabel(langLines.get(88));

        JComboBox<String> cp1 = new JComboBox<>(strColors);
        cp1.setSelectedIndex(colors.indexOf(BackgroundBoard.getBoardColor()[0]));
        JComboBox<String> cp2 = new JComboBox<>(strColors);
        cp2.setSelectedIndex(colors.indexOf(BackgroundBoard.getBoardColor()[1]));

        getBack(f).addActionListener(e -> {
            BackgroundBoard.getBoardColor()[0] = colors.get(cp1.getSelectedIndex());
            BackgroundBoard.getBoardColor()[1] = colors.get(cp2.getSelectedIndex());
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