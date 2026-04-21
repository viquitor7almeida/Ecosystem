import rules.Board;
import rules.EvolutionRules;
import painel.Visual;
import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {
        int[][] current = new int[50][50]; 
        int[][] next = new int[50][50];
        
        Board.start(current);

        // Instancia a Visual passando os comportamentos (callbacks)
        Visual tela = new Visual(
            current, 
            () -> executarEvolucao(current, next, null), // Ação do Botão
            (linha, coluna) -> { // Ação do Clique na Célula
                current[linha][coluna] = (current[linha][coluna] == 1) ? 0 : 1;
            }
        );

        // Timer de 30 segundos
        Timer timer = new Timer(30000, e -> executarEvolucao(current, next, tela));
        timer.start();
    }

    // Método centralizado para evitar repetição de código (DRY)
    private static void executarEvolucao(int[][] current, int[][] next, Visual tela) {
        EvolutionRules.evolve(current, next);
        
        // Swap de memória
        for (int l = 0; l < current.length; l++) {
            System.arraycopy(next[l], 0, current[l], 0, next[l].length);
        }

        // Atualiza a tela se ela for passada como argumento
        if (tela != null) {
            tela.colorAtualizer(current);
        }
        
        System.out.println("Ciclo de evolução concluído.");
    }
}