package painel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import models.Cells;

public class Visual extends JFrame {

    public interface CellClickHandler {
        void handle(int linha, int coluna, int tipoSelecionado);
    }

    private final PainelBioquimico painel;
    private float pulsoAnimacao = 0f;
    private long lastTick = System.currentTimeMillis();
    private float progressoTimer = 0f;
    private int modoDesenho = 1;

    public Visual(int[][] matriz, Runnable onEvolve, CellClickHandler onCellClick) {
        setTitle("BIO-SYNTH | Control Terminal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 800));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(5, 7, 10));

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(250, 0));
        sidePanel.setBackground(new Color(12, 15, 20));
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(0, 255, 150, 80)));

        sidePanel.add(Box.createVerticalStrut(20));

        JButton btnEvoluir = new JButton("FORÇAR EVOLUÇÃO");
        btnEvoluir.setMaximumSize(new Dimension(200, 45));
        btnEvoluir.setBackground(new Color(5, 7, 10));
        btnEvoluir.setForeground(new Color(0, 255, 150));
        btnEvoluir.setFont(new Font("Monospaced", Font.BOLD, 14));
        btnEvoluir.setFocusPainted(false);
        btnEvoluir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEvoluir.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 150)));
        btnEvoluir.addActionListener(e -> onEvolve.run());
        sidePanel.add(btnEvoluir);

        sidePanel.add(Box.createVerticalStrut(40));

        JLabel lblModo = new JLabel("MODO DE INSERÇÃO");
        lblModo.setForeground(new Color(0, 255, 150));
        lblModo.setFont(new Font("Monospaced", Font.BOLD, 12));
        lblModo.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(lblModo);

        sidePanel.add(Box.createVerticalStrut(15));

        ButtonGroup group = new ButtonGroup();
        adicionarBotaoModo("VIVO (Padrão)", 1, group, sidePanel, true);
        adicionarBotaoModo("PREDADOR", Cells.CellType.PREDATOR_CELL.getcellNumber(), group, sidePanel, false);
        adicionarBotaoModo("GUERREIRO", Cells.CellType.WARRIOR_CELL.getcellNumber(), group, sidePanel, false);
        adicionarBotaoModo("ANJO", Cells.CellType.ANGEL_CEll.getcellNumber(), group, sidePanel, false);

        add(sidePanel, BorderLayout.EAST);

        this.painel = new PainelBioquimico(matriz);
        this.painel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int colunas = matriz[0].length;
                int linhas = matriz.length;
                float celulaW = (float) painel.getWidth() / colunas;
                float celulaH = (float) (painel.getHeight() - 40) / linhas;
                int j = (int) (e.getX() / celulaW);
                int i = (int) ((e.getY() - 40) / celulaH);

                if (i >= 0 && i < linhas && j >= 0 && j < colunas) {
                    onCellClick.handle(i, j, modoDesenho);
                    painel.repaint();
                }
            }
        });
        add(this.painel, BorderLayout.CENTER);

        Timer animador = new Timer(16, e -> {
            pulsoAnimacao += 0.08f;
            long agora = System.currentTimeMillis();
            progressoTimer = ((agora - lastTick) % 30000) / 30000f; 
            painel.repaint();
        });
        animador.start();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarBotaoModo(String texto, int tipo, ButtonGroup group, JPanel panel, boolean selecionado) {
        JRadioButton btn = new JRadioButton(texto);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(12, 15, 20));
        btn.setFont(new Font("Monospaced", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setSelected(selecionado);
        btn.addActionListener(e -> modoDesenho = tipo);
        group.add(btn);
        panel.add(btn);
        panel.add(Box.createVerticalStrut(10));
    }

    public void colorAtualizer(int[][] matriz) {
        painel.setMatriz(matriz);
        lastTick = System.currentTimeMillis(); 
    }

    private class PainelBioquimico extends JPanel {
        private int[][] matriz;
        private final Color COLOR_BG = new Color(3, 5, 8);
        private final Color COLOR_ALIVE = new Color(0, 255, 150);
        private final Color COLOR_PREDATOR = new Color(255, 40, 60);
        private final Color COLOR_WARRIOR = new Color(170, 80, 255);
        private final Color COLOR_ANGEL = new Color(255, 255, 180);

        public PainelBioquimico(int[][] matriz) {
            this.matriz = matriz;
            setBackground(COLOR_BG);
        }

        public void setMatriz(int[][] novaMatriz) {
            this.matriz = novaMatriz;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            drawHeader(g2d);

            int colunas = matriz[0].length;
            int linhas = matriz.length;
            float celulaW = (float) getWidth() / colunas;
            float celulaH = (float) (getHeight() - 40) / linhas; 
            float tamBase = Math.min(celulaW, celulaH) * 0.82f;
            float pulso = (float) (Math.sin(pulsoAnimacao) * 1.5f);

            int PREDATOR_ID = Cells.CellType.PREDATOR_CELL.getcellNumber();
            int WARRIOR_ID = Cells.CellType.WARRIOR_CELL.getcellNumber();
            int ANGEL_ID = Cells.CellType.ANGEL_CEll.getcellNumber();

            for (int i = 0; i < linhas; i++) {
                for (int j = 0; j < colunas; j++) {
                    float x = j * celulaW + (celulaW - tamBase) / 2;
                    float y = 40 + i * celulaH + (celulaH - tamBase) / 2;
                    int type = matriz[i][j];

                    if (type != 0) {
                        if (type == PREDATOR_ID) {
                            renderEntity(g2d, x, y, tamBase, pulso, COLOR_PREDATOR, createPoly(x+tamBase/2, y+tamBase/2, tamBase+pulso, 3, pulsoAnimacao));
                        } else if (type == WARRIOR_ID) {
                            renderEntity(g2d, x, y, tamBase, pulso, COLOR_WARRIOR, createPoly(x+tamBase/2, y+tamBase/2, tamBase+pulso, 6, 0));
                        } else if (type == ANGEL_ID) {
                            renderEntity(g2d, x, y, tamBase, pulso, COLOR_ANGEL, createCross(x+tamBase/2, y+tamBase/2, tamBase+pulso));
                        } else {
                            renderEntity(g2d, x, y, tamBase, pulso, COLOR_ALIVE, new Ellipse2D.Float(x - pulso/2, y - pulso/2, tamBase+pulso, tamBase+pulso));
                        }
                    } else {
                        renderGrid(g2d, x, y, tamBase);
                    }
                }
            }
        }

        private void renderEntity(Graphics2D g2d, float x, float y, float tam, float pulso, Color c, Shape s) {
            float cx = x + tam/2;
            float cy = y + tam/2;
            float glowSize = tam * 1.3f;
            
            RadialGradientPaint rgp = new RadialGradientPaint(
                new Point2D.Float(cx, cy), glowSize,
                new float[]{0f, 1f},
                new Color[]{new Color(c.getRed(), c.getGreen(), c.getBlue(), 50), new Color(0,0,0,0)}
            );
            g2d.setPaint(rgp);
            g2d.fill(new Ellipse2D.Float(cx - glowSize, cy - glowSize, glowSize*2, glowSize*2));

            g2d.setPaint(new GradientPaint(x, y, c, x + tam, y + tam, c.darker().darker()));
            g2d.fill(s);

            g2d.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 180));
            g2d.setStroke(new BasicStroke(1.2f));
            g2d.draw(s);

            g2d.setColor(Color.WHITE);
            float core = tam * 0.15f;
            g2d.fill(new Ellipse2D.Float(cx - core/2, cy - core/2, core, core));
        }

        private void renderGrid(Graphics2D g2d, float x, float y, float tam) {
            g2d.setColor(new Color(255, 255, 255, 10));
            float offset = tam * 0.45f;
            g2d.draw(new Line2D.Float(x+offset, y+tam/2, x+tam-offset, y+tam/2));
            g2d.draw(new Line2D.Float(x+tam/2, y+offset, x+tam/2, y+tam-offset));
        }

        private void drawHeader(Graphics2D g2d) {
            g2d.setColor(new Color(10, 12, 18));
            g2d.fillRect(0, 0, getWidth(), 40);
            
            g2d.setColor(new Color(0, 255, 150, 40));
            g2d.fillRect(0, 37, (int)(getWidth() * progressoTimer), 3);

            g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g2d.setColor(new Color(0, 255, 150));
            String txt = "SYNTHETIC_CYCLE_MONITOR: " + (int)(30 - (progressoTimer * 30)) + "s TO NEXT EVOLUTION";
            g2d.drawString(txt, 15, 25);
        }

        private Shape createPoly(float x, float y, float t, int l, float rot) {
            Path2D p = new Path2D.Float();
            float r = t / 2;
            for (int i = 0; i < l; i++) {
                double a = rot + i * 2 * Math.PI / l;
                float px = (float)(x + Math.cos(a) * r);
                float py = (float)(y + Math.sin(a) * r);
                if (i == 0) p.moveTo(px, py); else p.lineTo(px, py);
            }
            p.closePath();
            return p;
        }

        private Shape createCross(float x, float y, float t) {
            Path2D p = new Path2D.Float();
            float r = t/2;
            float w = t/4;
            p.moveTo(x-w/2, y-r); p.lineTo(x+w/2, y-r); p.lineTo(x+w/2, y-w/2);
            p.lineTo(x+r, y-w/2); p.lineTo(x+r, y+w/2); p.lineTo(x+w/2, y+w/2);
            p.lineTo(x+w/2, y+r); p.lineTo(x-w/2, y+r); p.lineTo(x-w/2, y+w/2);
            p.lineTo(x-r, y+w/2); p.lineTo(x-r, y-w/2); p.lineTo(x-w/2, y-w/2);
            p.closePath();
            return p;
        }
    }
}