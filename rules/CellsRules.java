package rules;

import java.util.Random;

import models.Cells;
import models.Cells.CellType;

public class CellsRules {

    //Regras da celula predadora
    public void predator(CellType type, int[][] current, int[][] next, int l, int c){
        int id_predator = Cells.CellType.PREDATOR_CELL.getcellNumber();
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_dead = Cells.CellType.DEAD_CELL.getcellNumber();
        int id_warrior = Cells.CellType.WARRIOR_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        if (current[l][c] == id_predator) {

            //verifica a borda em uma casa de distancia
            if (c < current[l].length - 1) {
                int right_neighbor_cell = current[l][c + 1];

                //interacao celula predadora com celula viva
                if (right_neighbor_cell == id_alive) {
                    next[l][c] = id_dead;
                    next[l][c + 1] = id_predator;
                } 
                //interacao celula predadora com celula morta
                else if (c < current[l].length - 2 && right_neighbor_cell == id_dead) {
                    if (current[l][c + 2] == id_dead) {
                        next[l][c] = id_dead;
                        next[l][c + 1] = id_dead;
                    } else {
                        next[l][c] = id_dead;
                        next[l][c + 1] = id_predator;
                    }
                }
                //interacao celula predadora com celula guerreira
                else if (right_neighbor_cell == id_warrior) {
                    next[l][c] = id_dead;
                }
                //interacao celula predadora com celula anjo
                else if (right_neighbor_cell == id_angel) {
                    next[l][c] = id_dead;
                    next[l][c + 1] = id_predator;
                }
                //interacao entre predadores
                else if (right_neighbor_cell == id_predator) {
                    Random battle = new Random();
                    double attack = battle.nextDouble();
                    double defence = battle.nextDouble();
                    if (attack > defence) {
                        next[l][c] = id_dead;
                        next[l][c + 1] = id_predator;
                    } else {
                        next[l][c] = id_predator;
                        next[l][c + 1] = id_dead;
                    }
                }
            }
        }
    }
}