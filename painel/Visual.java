package painel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.util.function.BiConsumer;

public class Visual extends JFrame {

    private final PainelBioquimico painel;
    private float pulsoAnimacao = 0f;
    private long lastTick = System.currentTimeMillis();
    private float progressoTimer = 0f;

    public Visual(int[][] matriz, Runnable onEvolve, BiConsumer<Integer, Integer> onCellClick) {
        setTitle("BIO-SYNTH | Advanced Chemical Evolution");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 750));
        setLayout(new BorderLayout());

        JButton btnEvoluir = new JButton("FORÇAR EVOLUÇÃO");
        btnEvoluir.setBackground(new Color(20, 30, 40));
        btnEvoluir.setForeground(new Color(0, 255, 150));
        btnEvoluir.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnEvoluir.setFocusPainted(false);
        btnEvoluir.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 150), 1));
        btnEvoluir.addActionListener(e -> onEvolve.run());
        add(btnEvoluir, BorderLayout.NORTH);

        this.painel = new PainelBioquimico(matriz);
        this.painel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int colunas = matriz[0].length;
                int linhas = matriz.length;
                
                int j = (int) (e.getX() / ((float) painel.getWidth() / colunas));
                int i = (int) ((e.getY() - 40) / ((float) (painel.getHeight() - 40) / linhas));

                if (i >= 0 && i < linhas && j >= 0 && j < colunas) {
                    onCellClick.accept(i, j);
                }
            }
        });
        add(this.painel, BorderLayout.CENTER);

        Timer animador = new Timer(16, e -> {
            pulsoAnimacao += 0.05f;
            long agora = System.currentTimeMillis();
            progressoTimer = ((agora - lastTick) % 30000) / 30000f; 
            painel.repaint();
        });
        animador.start();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void colorAtualizer(int[][] matriz) {
        painel.setMatriz(matriz);
        lastTick = System.currentTimeMillis(); 
    }

    private class PainelBioquimico extends JPanel {
        private int[][] matriz;

        public PainelBioquimico(int[][] matriz) {
            this.matriz = matriz;
            setBackground(new Color(5, 7, 10));
            setPreferredSize(new Dimension(800, 800));
        }

        public void setMatriz(int[][] novaMatriz) {
            this.matriz = novaMatriz;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawTimer(g2d);

            int colunas = matriz[0].length;
            int linhas = matriz.length;

            float celulaW = (float) getWidth() / colunas;
            float celulaH = (float) (getHeight() - 40) / linhas; 
            float tamanhoBase = Math.min(celulaW, celulaH) * 0.8f;
            float fatorPulso = (float) (Math.sin(pulsoAnimacao) * 2.0f);

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    float x = j * celulaW + (celulaW - tamanhoBase) / 2;
                    float y = 40 + i * celulaH + (celulaH - tamanhoBase) / 2;

                    if (matriz[i][j] == 1) {
                        drawCell(g2d, x, y, tamanhoBase, fatorPulso);
                    } else {
                        drawEmpty(g2d, x, y, tamanhoBase);
                    }
                }
            }
        }

        private void drawTimer(Graphics2D g2d) {
            int w = getWidth();
            g2d.setColor(new Color(20, 25, 30));
            g2d.fillRect(0, 0, w, 40);

            g2d.setColor(new Color(0, 255, 150, 40));
            g2d.fillRect(0, 35, (int)(w * progressoTimer), 5);

            g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
            g2d.setColor(new Color(0, 255, 150));
            String status = "PRÓXIMA EVOLUÇÃO EM: " + (int)(30 - (progressoTimer * 30)) + "s";
            g2d.drawString(status, 20, 25);
        }

        private void drawCell(Graphics2D g2d, float x, float y, float tam, float pulso) {
            float tamPulsado = tam + pulso;
            RadialGradientPaint glow = new RadialGradientPaint(
                new Point2D.Float(x + tam/2, y + tam/2),
                tamPulsado * 0.8f,
                new float[]{0f, 1f},
                new Color[]{new Color(0, 255, 120, 50), new Color(0, 255, 120, 0)}
            );
            g2d.setPaint(glow);
            g2d.fill(new Ellipse2D.Float(x - pulso, y - pulso, tamPulsado, tamPulsado));

            g2d.setPaint(new GradientPaint(x, y, new Color(0, 180, 100), x + tam, y + tam, new Color(0, 80, 50)));
            g2d.fill(new Ellipse2D.Float(x, y, tam, tam));

            g2d.setColor(new Color(200, 255, 230, 200));
            g2d.fill(new Ellipse2D.Float(x + tam*0.3f, y + tam*0.25f, tam*0.35f, tam*0.35f));
            
            g2d.setColor(Color.WHITE);
            g2d.fill(new Ellipse2D.Float(x + tam*0.35f, y + tam*0.3f, tam*0.1f, tam*0.1f));
        }

        private void drawEmpty(Graphics2D g2d, float x, float y, float tam) {
            g2d.setColor(new Color(20, 25, 35));
            g2d.setStroke(new BasicStroke(1.0f));
            g2d.draw(new Ellipse2D.Float(x + tam*0.2f, y + tam*0.2f, tam*0.6f, tam*0.6f));
        }
    }
}