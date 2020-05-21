package com.group2.halma.Board;

import com.group2.halma.GamePlay.GamePlay;
import com.group2.halma.UI.InGameFrame;
import com.group2.halma.UI.UI;

import javax.swing.*;
import java.awt.*;

import static com.group2.halma.UI.UI.*;

public class SidePanel extends JPanel {
    private JFrame f;
    private JLabel lPlayer;
    private JLabel lRole;
    private JButton endRole;
    private JButton load;
    private JButton save;
    private JButton setting;
    private JButton exit;
    private Board Br;

    public SidePanel(JFrame f){
        this.f = f;
        this.setLayout(new GridLayout(7,1,20,10));

        this.lPlayer = new JLabel();
        if(GamePlay.getPlayers() == 2) { lPlayer.setText(langLines.get(22)); }
        else if(GamePlay.getPlayers() == 1) { lPlayer.setText(langLines.get(11)); }
        else {
            if(!GamePlay.isTeamPlay()) { lPlayer.setText(langLines.get(23)); }
            else { lPlayer.setText(langLines.get(108)); }
        }
        lPlayer.setFont(new Font("微软雅黑", 1, 15));
        this.add(lPlayer);

        this.lRole = new JLabel(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
        lRole.setFont(new Font("微软雅黑", 1, 15));
        this.add(lRole);

        endRole = new JButton(langLines.get(53));
        endRole.setPreferredSize(new Dimension(80,32));
        endRole.addActionListener(e -> {
            GamePlay.nextRole();
            lRole.setText(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
            Br.unSelect();
            endRole.setVisible(false);
        });
        this.endRole.setVisible(false);
        this.add(endRole);

        load = new JButton(langLines.get(101));
        load.setPreferredSize(new Dimension(80,32));
        load.addActionListener(e -> {
            if(UI.load(f)) UI.playReset();
        });
        this.add(load);

        save = new JButton(langLines.get(43));
        save.setPreferredSize(new Dimension(80,32));
        save.addActionListener(e -> {
            UI.save(f);
        });
        this.add(save);

        setting = new JButton(langLines.get(44));
        setting.setPreferredSize(new Dimension(75,32));
        setting.addActionListener(e -> {
            InGameFrame fi = new InGameFrame();
            UI.settings(fi, true);
        });
        this.add(setting);

        exit = new JButton(langLines.get(32));
        exit.setPreferredSize(new Dimension(75,32));
        exit.addActionListener(e -> {
            f.setContentPane(new Container());
            UI.mainReset();
            UI.mainMenu(f);
        });
        this.add(exit);
    }

    public void showEndRole() { this.endRole.setVisible(true); }

    public void hideEndRole() { this.endRole.setVisible(false); }

    public void refreshSidePanel(){
        lRole.setText(langLines.get(45) + GamePlay.getRole() + langLines.get(46));
    }

    public void setBoard(Board br) {
        this.Br = br;
    }
}
