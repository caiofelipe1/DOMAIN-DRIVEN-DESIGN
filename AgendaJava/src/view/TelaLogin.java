package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLogin extends JFrame {
    public TelaLogin() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);

        JLabel lblTitulo = new JLabel("Login do Usuário");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCadastrar = new JButton("Cadastrar");

        // Ícones e estilo dos botões
        btnEntrar.setIcon(new ImageIcon("icons/login.png"));
        btnCadastrar.setIcon(new ImageIcon("icons/add.png"));

        Color verdePrincipal = new Color(60, 179, 113); // mesmo verde da tela de cadastro

        btnEntrar.setBackground(verdePrincipal);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);

        btnCadastrar.setBackground(verdePrincipal);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);

        lblEmail.setFont(fonteCampos);
        lblSenha.setFont(fonteCampos);
        txtEmail.setFont(fonteCampos);
        txtSenha.setFont(fonteCampos);
        btnEntrar.setFont(fonteCampos);
        btnCadastrar.setFont(fonteCampos);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblSenha);
        panel.add(txtSenha);
        panel.add(btnEntrar);
        panel.add(btnCadastrar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        btnEntrar.addActionListener(e -> {
            // Aqui você pode validar o login
            JOptionPane.showMessageDialog(this, "Login realizado!");
            new TelaConsultaDocente().setVisible(true);
            dispose(); // Fecha a tela de login
        });
    }
}
