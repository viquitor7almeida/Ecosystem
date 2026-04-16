import rules.Board;
import painel.Visual;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        //Alocação das matrizes na Heap
        int[][] current = new int[50][50]; 
        int[][] next = new int[50][50];
        
        //Inicializa os dados (usando a classe board)
        Board.start(current);

        //Inicializa a interface (passando o endereço de memória)
        Visual tela = new Visual(current);

        //Configura o Timer para a evolucao quimica (30 segundos)
        Timer timer = new Timer(30000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Processa a evolução
                Board.evolve(current, next);
                
                //Atualiza a interface com os novos dados
                tela.colorAtualizer(next);
                
                //Swap de memoria onde  o que era 'next' vira o 'current' para o proximo ciclo
                for (int l = 0; l < current.length; l++) {
                    System.arraycopy(next[l], 0, current[l], 0, next[l].length);
                }
                
                System.out.println("Ciclo de evolução concluído.");
            }
        });

        timer.start();
    }
}