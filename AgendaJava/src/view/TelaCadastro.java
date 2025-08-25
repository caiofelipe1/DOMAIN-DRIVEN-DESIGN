package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastro extends JFrame {
    public TelaCadastro() {
        setTitle("Cadastro");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fonte personalizada
        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);

        JLabel lblTitulo = new JLabel("Cadastro de Usuário");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField(20);
        JButton btnSalvar = new JButton("Salvar");

        // Ícone no botão
        btnSalvar.setIcon(new ImageIcon("icons/save.png"));
        btnSalvar.setBackground(new Color(60, 179, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);

        // Aplicar fonte
        lblNome.setFont(fonteCampos);
        lblEmail.setFont(fonteCampos);
        lblSenha.setFont(fonteCampos);
        txtNome.setFont(fonteCampos);
        txtEmail.setFont(fonteCampos);
        txtSenha.setFont(fonteCampos);
        btnSalvar.setFont(fonteCampos);

        // Painel principal com borda
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblSenha);
        panel.add(txtSenha);
        panel.add(new JLabel());
        panel.add(btnSalvar);

        // Layout geral
        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> {
            if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || new String(txtSenha.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                new TelaLogin().setVisible(true);
                dispose();
            }
        });
    }
}
