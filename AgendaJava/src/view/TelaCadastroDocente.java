package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class TelaCadastroDocente extends JFrame {
    private JLabel lblFotoPreview;
    private File fotoSelecionada;

    public TelaCadastroDocente() {
        setTitle("Cadastro de Docente");
        setSize(450, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);
        Color verdePrincipal = new Color(60, 179, 113);

        JLabel lblTitulo = new JLabel("Cadastro de Docente");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblCargo = new JLabel("Cargo:");
        JTextField txtCargo = new JTextField(20);

        JButton btnSelecionarFoto = new JButton("Selecionar Foto");
        btnSelecionarFoto.setIcon(new ImageIcon("icons/photo.png"));
        btnSelecionarFoto.setBackground(verdePrincipal);
        btnSelecionarFoto.setForeground(Color.WHITE);
        btnSelecionarFoto.setFocusPainted(false);
        btnSelecionarFoto.setFont(fonteCampos);

        lblFotoPreview = new JLabel();
        lblFotoPreview.setPreferredSize(new Dimension(100, 100));
        lblFotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFotoPreview.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setIcon(new ImageIcon("icons/save.png"));
        btnSalvar.setBackground(verdePrincipal);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(fonteCampos);

        lblNome.setFont(fonteCampos);
        lblEmail.setFont(fonteCampos);
        lblCargo.setFont(fonteCampos);
        txtNome.setFont(fonteCampos);
        txtEmail.setFont(fonteCampos);
        txtCargo.setFont(fonteCampos);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(lblCargo);
        panel.add(txtCargo);
        panel.add(btnSelecionarFoto);
        panel.add(lblFotoPreview);
        panel.add(new JLabel());
        panel.add(btnSalvar);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        btnSelecionarFoto.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                fotoSelecionada = fileChooser.getSelectedFile();
                ImageIcon icon = new ImageIcon(fotoSelecionada.getAbsolutePath());
                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                lblFotoPreview.setIcon(new ImageIcon(img));
            }
        });

        btnSalvar.addActionListener(e -> {
            if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtCargo.getText().isEmpty() || fotoSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                new TelaLogin().setVisible(true); // Volta para a tela de login
                dispose(); // Fecha a tela de cadastro
            }
        });
    }
}
