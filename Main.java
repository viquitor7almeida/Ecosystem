import rules.board;
import painel.visual;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        // 1. Alocação das matrizes na Heap
        int[][] current = new int[50][50]; 
        int[][] next = new int[50][50];
        
        // 2. Inicializa os dados (usando a classe board)
        board.start(current);

        // 3. Inicializa a interface (passando o endereço de memória)
        visual tela = new visual(current);

        // 4. Configura o Timer para a "Evolução Química" (30 segundos)
        Timer timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Processa a evolução
                board.evolve(current, next);
                
                // Atualiza a interface com os novos dados
                tela.colorAtualizer(next);
                
                // Swap de memória: o que era 'next' vira o 'current' para o próximo ciclo
                for (int l = 0; l < current.length; l++) {
                    System.arraycopy(next[l], 0, current[l], 0, next[l].length);
                }
                
                System.out.println("Ciclo de evolução concluído.");
            }
        });

        timer.start();
    }
}