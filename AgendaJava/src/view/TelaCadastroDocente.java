package view;

import controller.DocenteController;
import model.Docente;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class TelaCadastroDocente extends JFrame {

    private final DocenteController controller = new DocenteController();
    private JLabel lblFotoPreview;
    private File fotoSelecionada;

    public TelaCadastroDocente() {
        setTitle("Cadastro de Docente");
        setSize(520, 440);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);
        Color verde = new Color(60, 179, 113);

        JLabel lblTitulo = new JLabel("Cadastro de Docente");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNome  = new JLabel("Nome:");
        JTextField txtNome  = new JTextField(20);
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);
        JLabel lblCargo = new JLabel("Cargo:");
        JTextField txtCargo = new JTextField(20);

        JButton btnSelecionarFoto = IconLoader.button("Selecionar Foto", "/icons/photo.png", 20, verde, Color.WHITE);
        JButton btnSalvar         = IconLoader.button("Salvar",          "/icons/save.png",  20, verde, Color.WHITE);
        JButton btnVoltar         = IconLoader.button("Voltar",          "/icons/restore.png",20, verde, Color.WHITE);

        lblNome.setFont(fonteCampos);
        lblEmail.setFont(fonteCampos);
        lblCargo.setFont(fonteCampos);
        txtNome.setFont(fonteCampos);
        txtEmail.setFont(fonteCampos);
        txtCargo.setFont(fonteCampos);

        lblFotoPreview = new JLabel("100x100", SwingConstants.CENTER);
        lblFotoPreview.setPreferredSize(new Dimension(100, 100));
        lblFotoPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblFotoPreview.setOpaque(true);
        lblFotoPreview.setBackground(Color.WHITE);

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
        panel.add(btnVoltar);
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
                lblFotoPreview.setText("");
            }
        });

        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String email = txtEmail.getText().trim();
                String cargo = txtCargo.getText().trim();

                Docente d = controller.criar(nome, email, cargo, fotoSelecionada);
                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!\nID: " + d.getId());
                new TelaConsultaDocente().setVisible(true);
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar docente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }
}
