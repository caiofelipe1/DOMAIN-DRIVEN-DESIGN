package view;

import controller.UsuarioController;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaLogin extends JFrame {

    private final UsuarioController controller = new UsuarioController();

    public TelaLogin() {
        setTitle("Login");
        setSize(420, 260);
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

        Color verde = new Color(60, 179, 113);

        JButton btnEntrar    = IconLoader.button("Entrar",    "/icons/login.png", 20, verde, Color.WHITE);
        JButton btnCadastrar = IconLoader.button("Cadastrar", "/icons/add.png",   20, verde, Color.WHITE);

        lblEmail.setFont(fonteCampos);
        lblSenha.setFont(fonteCampos);
        txtEmail.setFont(fonteCampos);
        txtSenha.setFont(fonteCampos);

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

        // Abrir cadastro
        btnCadastrar.addActionListener(e -> {
            new TelaCadastro().setVisible(true);
            dispose();
        });

        // Entrar (autenticação pelo controller)
        btnEntrar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha email e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            var usuario = controller.autenticar(email, senha);
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + usuario.getNome() + "!");
                new TelaConsultaDocente().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas.");
            }
        });
    }
}
