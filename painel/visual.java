package painel;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import rules.board;

public class visual {
    public static void createWindow(String[] args) {
        board game = new board();


            int[][] init = new int[50][50];
            int[][] turn = new int[50][50];

            Timer timer = new Timer (30000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event){
                    game.evolve(init, turn);
                }
        });

        timer.start();

            game.start(init);
            game.evolve(init, turn);
    }
}
