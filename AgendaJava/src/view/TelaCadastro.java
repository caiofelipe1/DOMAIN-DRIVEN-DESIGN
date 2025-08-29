package view;

import controller.UsuarioController;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastro extends JFrame {

    private final UsuarioController controller = new UsuarioController();

    public TelaCadastro() {
        setTitle("Cadastro");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        JButton btnSalvar = IconLoader.button("Salvar", "/icons/save.png", 20,
                new Color(60,179,113), Color.WHITE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(lblNome); panel.add(txtNome);
        panel.add(lblEmail); panel.add(txtEmail);
        panel.add(lblSenha); panel.add(txtSenha);
        panel.add(new JLabel()); panel.add(btnSalvar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> {
            try {
                controller.cadastrar(
                        txtNome.getText().trim(),
                        txtEmail.getText().trim(),
                        new String(txtSenha.getPassword()).trim()
                );
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                new TelaLogin().setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
