package rules;

import java.util.Random;

public class Board {

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
    }