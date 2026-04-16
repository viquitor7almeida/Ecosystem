package rules;

import java.util.Random;

public class Board {

    public static void start(int[][] current) {
        Random generator = new Random();
        for(int l=0; l<current.length; l++) {
            for(int c=0; c<current[l].length; c++) {
                double vitality = generator.nextDouble();
                if (vitality > 0.6) {
                    current[l][c]=1;
                }
                else {
                    current[l][c]=0;
                } 
            System.out.print(current[l][c]==1 ? " 1:🦠 " : " 0:☠️  ");
            }
        System.out.println();
        System.out.println();
        }
    }

    public static void evolve(int[][] current, int[][] next) {
        for(int l=0; l<current.length; l++) {
            for(int c=0; c<current[l].length; c++) {
                
                int cell = current[l][c];

                int left = (c > 0 ? current[l][c - 1] : 0) + (c > 1 ? current[l][c - 2] : 0);
                int rigth = (c < current[l].length - 1 ? current[l][c + 1] : 0) + (c < current[l].length - 2 ? current[l][c + 2] : 0);
                int top = (l > 0 ? current[l - 1][c] : 0) + (l > 1 ? current[l - 2][c] : 0);
                int down = (l < current.length - 1 ? current[l + 1][c] : 0) + (l < current.length - 2 ? current[l + 2][c] : 0);

                int simbiosis = (left+rigth+top+down);

                if(simbiosis>=3){
                    next[l][c]=1;
                }
                    else if(simbiosis<2){
                        next[l][c]=0;
                    }
                    else if(simbiosis>5){
                        next[l][c]=0;
                    }
                    else if(current[l][c]==0 && simbiosis==3){
                        next[l][c]=1;
                        }
                    System.out.print(next[l][c]==1 ? " 1e:🦠 " : " 0e:☠️  ");    
                    }
                System.out.println();
                System.out.println();
            }
        }
    }