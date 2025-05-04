package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

public class TelaLogin extends JFrame {

    private JTextField campoNome;
    private JPasswordField campoSenha;

    public TelaLogin() {
        setTitle("Login");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel esquerdo (gradiente verde)
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
        add(painelEsquerdo, BorderLayout.WEST);
        painelEsquerdo.setPreferredSize(new Dimension(getWidth() / 2, getHeight()));

        // Painel direito (branco)
        JPanel painelDireito = new JPanel();
        painelDireito.setBackground(Color.WHITE);
        add(painelDireito, BorderLayout.CENTER);
        painelDireito.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelNome = new JLabel("Nome:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 16));
        painelDireito.add(labelNome, gbc);

        campoNome = new JTextField(20);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 16));
        campoNome.setBorder(new RoundedBorder(10));
        campoNome.setToolTipText("Digite seu nome de usuário");
        gbc.gridy++;
        painelDireito.add(campoNome, gbc);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy++;
        painelDireito.add(labelSenha, gbc);

        campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        campoSenha.setBorder(new RoundedBorder(10));
        campoSenha.setToolTipText("Digite sua senha");
        gbc.gridy++;
        painelDireito.add(campoSenha, gbc);

        JButton botaoLogin = new JButton("Login");
        botaoLogin.setFont(new Font("Arial", Font.BOLD, 18));
        botaoLogin.setForeground(Color.WHITE);
        botaoLogin.setBackground(new Color(0, 180, 200));
        botaoLogin.setBorder(new RoundedBorder(10));
        botaoLogin.setFocusPainted(false);
        botaoLogin.setPreferredSize(new Dimension(120, 40));
        botaoLogin.setToolTipText("Clique para fazer login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String senha = new String(campoSenha.getPassword());

                if (nome.equals("DMI") && senha.equals("123")) {
                    new TelaPrincipal();
                    dispose();
                } else {
                    if (!nome.equals("DMI")) {
                        campoNome.setBorder(new RoundedBorder(10, Color.RED, 2));
                        tremerCampo(campoNome);
                    }
                    if (!senha.equals("123")) {
                        campoSenha.setBorder(new RoundedBorder(10, Color.RED, 2));
                        tremerCampo(campoSenha);
                    }
                    JOptionPane.showMessageDialog(TelaLogin.this, "Nome ou senha incorretos.");
                }
            }
        });

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        painelDireito.add(botaoLogin, gbc);

        setVisible(true);
    }

    private void tremerCampo(JComponent campo) {
        Point posicaoOriginal = campo.getLocation();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                campo.setLocation(posicaoOriginal.x - 5, posicaoOriginal.y);
                try { Thread.sleep(50); } catch (InterruptedException ex) {}
                campo.setLocation(posicaoOriginal.x + 5, posicaoOriginal.y);
                try { Thread.sleep(50); } catch (InterruptedException ex) {}
            }
            campo.setLocation(posicaoOriginal);
            campo.setBorder(new RoundedBorder(10));
        });
        thread.start();
    }

    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        private int thickness;

        RoundedBorder(int radius) {
            this.radius = radius;
            this.color = Color.GRAY;
            this.thickness = 1;
        }

        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(thickness));
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