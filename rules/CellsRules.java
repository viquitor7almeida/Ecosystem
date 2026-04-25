package rules;

import java.util.Random;
import models.Cells;
import models.Cells.CellType;

public class CellsRules {

    private static final Random rand = new Random();

    public static void battles(int[][] current, int[][] next, int l, int c) {
        int id_predator = Cells.CellType.PREDATOR_CELL.getcellNumber();
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_dead = Cells.CellType.DEAD_CELL.getcellNumber();
        int id_warrior = Cells.CellType.WARRIOR_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        if (current[l][c] == id_predator) {
            if (c < current[l].length - 1) {
                int right = current[l][c + 1];

                if (right == id_alive || right == id_angel) {
                    next[l][c] = id_dead;
                    next[l][c + 1] = id_predator;
                } else if (right == id_dead && c < current[l].length - 2) {
                    if (current[l][c + 2] == id_dead) {
                        next[l][c] = id_dead;
                    } else {
                        next[l][c] = id_dead;
                        next[l][c + 1] = id_predator;
                    }
                } else if (right == id_warrior) {
                    if (rand.nextDouble() < 0.9) {
                        next[l][c] = id_dead;
                    }
                } else if (right == id_predator) {
                    if (rand.nextDouble() > 0.5) {
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

    public static void saves(int[][] current, int[][] next, int l, int c) {
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_dead = Cells.CellType.DEAD_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        if (current[l][c] == id_angel) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int nl = l + i;
                    int nc = c + j;
                    if (nl >= 0 && nl < current.length && nc >= 0 && nc < current[0].length) {
                        if (current[nl][nc] == id_dead && rand.nextDouble() < 0.2) {
                            next[nl][nc] = id_alive;
                        }
                    }
                }
            }
        }
    }

    public static void mutations(int[][] current, int[][] next, int l, int c) {
        int id_predator = Cells.CellType.PREDATOR_CELL.getcellNumber();
        int id_alive = Cells.CellType.ALIVE_CELL.getcellNumber();
        int id_warrior = Cells.CellType.WARRIOR_CELL.getcellNumber();
        int id_angel = Cells.CellType.ANGEL_CEll.getcellNumber();

        int neighbors = countAliveNeighbors(current, l, c);

        if (neighbors == 0 && rand.nextDouble() < 0.005) {
            next[l][c] = id_angel;
        }

        if (neighbors > 8 && rand.nextDouble() < 0.02) {
            next[l][c] = id_predator;
        }

        if (current[l][c] == id_angel) {
            if (c < current[l].length - 3) {
                boolean deathWay = false;
                for (int i = 0; i < 4; i++) {
                    if (current[l][c + i] == 0) {
                        deathWay = true;
                        break;
                    }
                }
                if (!deathWay && current[l][c + 3] == id_alive && rand.nextDouble() < 0.15) {
                    next[l][c + 3] = id_warrior;
                }
            }
        }
    }

    private static int countAliveNeighbors(int[][] current, int l, int c) {
        int count = 0;
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i == 0 && j == 0) continue;
                int nl = l + i;
                int nc = c + j;
                if (nl >= 0 && nl < current.length && nc >= 0 && nc < current[0].length) {
                    if (current[nl][nc] > 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}