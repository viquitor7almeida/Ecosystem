package rules;

import java.util.Random;

import models.Cells;
import models.Cells.CellType;

public class CellsRules {

    //regras da batalhas
    public void battles(int[][] current, int[][] next, int l, int c){
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
                    Random damage_trade = new Random();
                    double damage_receive = damage_trade.nextDouble();
                    if(damage_receive<0.9){
                        next[l][c] = id_dead;
                    }
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

    //regras de salvamentos entre celulas
    public void saves (CellType type, int[][] current, int[][] next, int l, int c){
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_dead = Cells.CellType.DEAD_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        if (current[l][c] == id_angel){

            if(l < current[l - 2].length){
                int right_neighbor_cell = current[l][c + 1];
                
                if(right_neighbor_cell == id_dead){
                    next[l][c] = id_alive;
                }
        }
    }
}

    //regras pra nascimento de celulas especiais
    public void borns(int[][] current, int[][] next, int l, int c){
        int left = (c > 0 ? current[l][c - 1] : 0) + (c > 1 ? current[l][c - 2] : 0);
        int rigth = (c < current[l].length - 1 ? current[l][c + 1] : 0) + (c < current[l].length - 2 ? current[l][c + 2] : 0);
        int top = (l > 0 ? current[l - 1][c] : 0) + (l > 1 ? current[l - 2][c] : 0);
        int down = (l < current.length - 1 ? current[l + 1][c] : 0) + (l < current.length - 2 ? current[l + 2][c] : 0);

        int id_predator = Cells.CellType.PREDATOR_CELL.getcellNumber();
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_dead = Cells.CellType.DEAD_CELL.getcellNumber();
        int id_warrior = Cells.CellType.WARRIOR_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        //nascimento anjo
        if(left+rigth+top+down == 0){
            next[l][c]=id_angel;
            }

        //nascimento predador
        if(left+rigth+top+down > 8){
            next[l][c]=id_predator;
            }

        //nascimento guerreiro
        if(current[l][c] == id_angel){
            if (c < current[l].length - 3) {
                boolean deathWay = false;

                for(int i=0; i < 4; i++){
                    if(current[l][c+i] == id_dead){
                        deathWay=true;
                        break;
                    }
        }
                if(!deathWay && current[l][c+3] == id_alive){
                current[l][c+3] = id_warrior;
                }
        }
    }
}
}