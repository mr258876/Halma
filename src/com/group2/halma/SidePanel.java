package com.group2.halma;

import javax.swing.*;
import java.awt.*;

import static com.group2.halma.UI.*;

public class SidePanel extends JPanel {
    private JFrame f;
    private JLabel lPlayer;
    private JLabel lRole;
    private JButton endRole;
    private JButton save;
    private JButton setting;
    private JButton exit;

    public SidePanel(JFrame f){
        this.f = f;
        this.setLayout(new GridLayout(6,1,20,15));

        this.lPlayer = new JLabel();
        if(GamePlay.getPlayers() == 2) {lPlayer.setText(langLines.get(22));} else {lPlayer.setText(langLines.get(23));}
        lPlayer.setFont(new Font("微软雅黑", 1, 15));
        this.add(lPlayer);

        this.lRole = new JLabel(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
        lRole.setFont(new Font("微软雅黑", 1, 15));
        this.add(lRole);

        this.endRole = new JButton(langLines.get(53));
        endRole.setPreferredSize(new Dimension(80,32));
        endRole.addActionListener(e -> {
            GamePlay.nextRole();
            lRole.setText(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
            endRole.setVisible(false);
        });
        this.endRole.setVisible(false);
        this.add(endRole);

        this.save = new JButton(langLines.get(53));
        save = new JButton(langLines.get(43));
        save.setPreferredSize(new Dimension(80,32));
        save.addActionListener(e -> {
            UI.save(f);
        });
        this.add(save);

        this.setting = new JButton(langLines.get(53));
        setting = new JButton(langLines.get(44));
        setting.setPreferredSize(new Dimension(75,32));
        setting.addActionListener(e -> {
            JFrame fi = UI.inGameFramework();
            UI.settings(fi, true);
        });
        this.add(setting);

        this.exit = new JButton(langLines.get(53));
        exit = new JButton(langLines.get(32));
        exit.setPreferredSize(new Dimension(75,32));
        exit.addActionListener(e -> {
            f.setContentPane(new Container());
            UI.mainReset();
            UI.mainMenu(f);
        });
        this.add(exit);
    }

    protected void showEndRole() {
        this.endRole.setVisible(true);
    }

    protected void refreshSidePanel(){
        lRole.setText(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
    }
}
