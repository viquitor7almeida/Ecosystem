import rules.Board;
import rules.EvolutionRules;
import rules.CellsRules;
import painel.Visual;
import javax.swing.Timer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] current = new int[50][50]; 
        int[][] next = new int[50][50];
        
        Board.start(current);
        Board boardController = new Board();

        final Visual[] windowRef = new Visual[1];

        //instancia a interface
        windowRef[0] = new Visual(
            current, 
            //botao executa todo o ciclo
            () -> executeFullCycle(current, next, windowRef[0]), 
            
            //logica de clique no mapa
            (linha, coluna, modoSelecionado) -> {
                if (modoSelecionado == 1) {
                    current[linha][coluna] = (current[linha][coluna] == 1) ? 0 : 1;
                } else if (modoSelecionado == models.Cells.CellType.PREDATOR_CELL.getcellNumber()) {
                    boardController.clickPredator(current, linha, coluna);
                } else if (modoSelecionado == models.Cells.CellType.ANGEL_CEll.getcellNumber()) {
                    boardController.clickAngel(current, linha, coluna);
                } else if (modoSelecionado == models.Cells.CellType.WARRIOR_CELL.getcellNumber()) {
                    boardController.clickWarrior(current, linha, coluna);
                }
                // Repinta imediatamente após o clique para dar feedback visual
                windowRef[0].colorAtualizer(current);
            }
        );

        Timer mainCycleTimer = new Timer(30000, e -> {
            executeFullCycle(current, next, windowRef[0]);
        });

        mainCycleTimer.start();
    }

    private static void executeFullCycle(int[][] current, int[][] next, Visual window) {
        for (int i = 0; i < next.length; i++) {
            Arrays.fill(next[i], 0);
        }

        EvolutionRules.evolve(current, next);
        for (int l = 0; l < current.length; l++) {
            for (int c = 0; c < current[l].length; c++) {
                CellsRules.battles(current, next, l, c);
                CellsRules.mutations(current, next, l, c);
                CellsRules.saves(current, next, l, c);
            }
        }

        for (int l = 0; l < current.length; l++) {
            System.arraycopy(next[l], 0, current[l], 0, next[l].length);
        }

        if (window != null) {
            window.colorAtualizer(current);
        }       
        System.out.println("Ciclo biológico completo processado.");
    }
}