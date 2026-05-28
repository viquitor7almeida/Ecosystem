package rules;

public class EvolutionRules {

    //metodo de evolucao padrao
    public static void evolve(int[][] current, int[][] next) {
        for (int l = 0; l < current.length; l++) {
            for (int c = 0; c < current[l].length; c++) {
                if (current[l][c] > 1) {
                    next[l][c] = current[l][c];
                    continue;
                }

                int simbiosis = countLocalNeighbors(current, l, c);

                if (current[l][c] == 1) {
                    if (simbiosis < 2 || simbiosis > 3) {
                        next[l][c] = 0; 
                    } else {
                        next[l][c] = 1; 
                    }
                } else {
                    if (simbiosis == 3) {
                        next[l][c] = 1; 
                    } else {
                        next[l][c] = 0;
                    }
                }
            }
        }
    }

    //metodo de contagem de celulas proximas
    private static int countLocalNeighbors(int[][] current, int l, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
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