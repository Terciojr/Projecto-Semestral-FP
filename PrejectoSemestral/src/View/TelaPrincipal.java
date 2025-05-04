package View;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Gerenciamento");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel esquerdo com gradiente
        JPanel painelEsquerdo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 180, 100), width, height, new Color(0, 100, 50));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        painelEsquerdo.setPreferredSize(new Dimension(getWidth() / 2, getHeight()));
        add(painelEsquerdo, BorderLayout.WEST);

        // Painel direito com botões
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(Color.WHITE);
        add(painelDireito, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        adicionarBotao(painelDireito, gbc, "Registar Departamento", "Registar novo departamento", CadastroDepartamento.class);
        adicionarBotao(painelDireito, gbc, "Registar Funcionário", "Registar novo funcionário", TelaCadastro.class);
        adicionarBotao(painelDireito, gbc, "Lista de Departamentos", "Listar departamentos", ListaDepartamentos.class);
        adicionarBotao(painelDireito, gbc, "Lista de Funcionários", "Listar funcionários", ListaFuncionarios.class);
        adicionarBotao(painelDireito, gbc, "Registar Presenças", "Registar presenças", RegistroPresencas.class);
        adicionarBotao(painelDireito, gbc, "Relatório de Faltas", "Gerar relatório de faltas", RelatorioFaltas.class);
        adicionarBotao(painelDireito, gbc, "Relatório de Funcionários por Departamento", "Relatório por departamento", RelatorioFuncionariosDepartamento.class);
        adicionarBotao(painelDireito, gbc, "Relatório de Presença", "Gerar relatório de presença", RelatorioPresenca.class);
        adicionarBotao(painelDireito, gbc, "Solicitação de Férias", "Solicitar férias", SolicitacaoFerias.class);

        setVisible(true);
    }

    private void adicionarBotao(JPanel painel, GridBagConstraints gbc, String texto, String tooltip, Class<?> classeDaTela) {
        JButton botao = criarBotaoMenu(texto, tooltip);
        botao.addActionListener(e -> abrirTela(classeDaTela));
        painel.add(botao, gbc);
        gbc.gridy++;
    }

    private void abrirTela(Class<?> classeDaTela) {
        try {
            JFrame tela = (JFrame) classeDaTela.getDeclaredConstructor().newInstance();
            tela.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir a tela: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JButton criarBotaoMenu(String texto, String tooltip) {
        JButton botao = new JButton(texto);
        botao.setForeground(Color.BLACK);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setToolTipText(tooltip);
        botao.setBackground(new Color(0, 130, 90));
        botao.setBorder(new RoundedBorder(10));
        botao.setPreferredSize(new Dimension(300, 40));
        return botao;
    }

    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 130, 90));
            g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

}
