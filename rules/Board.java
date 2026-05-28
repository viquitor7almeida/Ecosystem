package rules;

import java.util.Random;

import models.Cells;

public class Board {

    //metodo de populacao do ecossistema
    public static void start(int[][] current) {
        Random generator = new Random();
        for(int l=0; l<current.length; l++) {
            for(int c=0; c<current[l].length; c++) {
                double vitality = generator.nextDouble();
                if (vitality > 0.8) {
                    current[l][c]=1;
                }
                else {
                    current[l][c]=0;
                } 
            }
        System.out.println();
        System.out.println();
            }
        }

        //click para criar predador
        public void clickPredator(int[][] current, int l, int c){
        int id_predator = Cells.CellType.PREDATOR_CELL.getcellNumber();
        current[l][c] = id_predator;
        }

        //click para criar anjo
        public void clickAngel(int[][] current, int l, int c){
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();
        current[l][c] = id_angel;
        }

        //click para criar guerreiro
        public void clickWarrior(int[][] current, int l, int c){
        int id_warrior = Cells.CellType.WARRIOR_CELL.getcellNumber();
        current[l][c] = id_warrior;
        }
    }