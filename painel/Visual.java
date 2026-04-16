package painel;

import javax.swing.*;
import java.awt.*;

public class Visual{
    private JFrame window;
    private JPanel[][] cells;
    private int size;

    public Visual(int[][] matriz) {
        this.size = matriz.length;
        this.cells = new JPanel[size] [size];

        window = new JFrame("Quimic Evolution");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(size , size));

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                cells[i][j] = new JPanel();
                cells[i][j].setBorder(BorderFactory.createLineBorder((Color.DARK_GRAY)));
                window.add(cells[i][j]);
            }
        }
        window.setSize(800, 800);
        window.setVisible(true);

        colorAtualizer(matriz);
    }

        public void colorAtualizer(int[][] matriz){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; i++){
                if(matriz[i][j]==1){
                    cells[i][j].setBackground(Color.GREEN);
                } else{
                    cells[i][j].setBackground(Color.BLACK);
                }
        }
    }
}
}