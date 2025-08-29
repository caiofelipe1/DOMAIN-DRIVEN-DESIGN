package view;

import controller.DocenteController;
import model.Docente;
import util.BackupUtil;
import util.IconLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class TelaConsultaDocente extends JFrame {

    private final DocenteController controller = new DocenteController();

    private DefaultListModel<Docente> listaModel;
    private JList<Docente> listaDocentes;

    public TelaConsultaDocente() {
        setTitle("Consulta de Docentes");
        setSize(640, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);
        Color verde = new Color(60, 179, 113);

        JLabel lblTitulo = new JLabel("Docentes Cadastrados");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        listaModel = new DefaultListModel<>();
        listaDocentes = new JList<>(listaModel);
        listaDocentes.setFont(fonteCampos);
        listaDocentes.setFixedCellHeight(56); // altura confortável para foto 40x40
        listaDocentes.setCellRenderer(new DocenteRenderer());

        JScrollPane scrollPane = new JScrollPane(listaDocentes);

        JButton btnNovo    = IconLoader.button("Novo",      "/icons/add.png",     20, verde, Color.WHITE);
        JButton btnEditar  = IconLoader.button("Editar",    "/icons/edit.png",    20, verde, Color.WHITE);
        JButton btnExcluir = IconLoader.button("Excluir",   "/icons/delete.png",  20, verde, Color.WHITE);
        JButton btnBackup  = IconLoader.button("Backup",    "/icons/backup.png",  20, verde, Color.WHITE);
        JButton btnRestore = IconLoader.button("Restaurar", "/icons/restore.png", 20, verde, Color.WHITE);
        JButton btnSair    = IconLoader.button("Sair",      "/icons/delete.png",  20, verde, Color.WHITE);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botoesPanel.add(btnNovo);
        botoesPanel.add(btnEditar);
        botoesPanel.add(btnExcluir);
        botoesPanel.add(btnBackup);
        botoesPanel.add(btnRestore);
        botoesPanel.add(btnSair);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        carregarLista();

        // Ações
        btnNovo.addActionListener(e -> {
            new TelaCadastroDocente().setVisible(true);
            dispose();
        });

        btnEditar.addActionListener(e -> editarSelecionado());
        btnExcluir.addActionListener(e -> excluirSelecionado());

        btnBackup.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Salvar backup (.zip)");
            int r = fc.showSaveDialog(this);
            if (r == JFileChooser.APPROVE_OPTION) {
                File out = fc.getSelectedFile();
                if (!out.getName().toLowerCase().endsWith(".zip")) {
                    out = new File(out.getParentFile(), out.getName() + ".zip");
                }
                try {
                    BackupUtil.criarBackup("data", out);
                    JOptionPane.showMessageDialog(this, "Backup criado em:\n" + out.getAbsolutePath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Falha no backup: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRestore.addActionListener(e -> {
            int conf = JOptionPane.showConfirmDialog(this,
                    "Restauração vai sobrescrever os dados atuais. Continuar?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            if (conf != JOptionPane.YES_OPTION) return;

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Escolher arquivo de backup (.zip)");
            int r = fc.showOpenDialog(this);
            if (r == JFileChooser.APPROVE_OPTION) {
                try {
                    BackupUtil.restaurarBackup("data", fc.getSelectedFile());
                    JOptionPane.showMessageDialog(this, "Backup restaurado!");
                    carregarLista();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Falha ao restaurar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });
    }

    /* ------------ Métodos auxiliares de CRUD/Lista ------------ */

    private void carregarLista() {
        listaModel.clear();
        List<Docente> todos = controller.listar();
        todos.forEach(listaModel::addElement);
    }

    private void excluirSelecionado() {
        Docente sel = listaDocentes.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecione um docente.");
            return;
        }
        int conf = JOptionPane.showConfirmDialog(this, "Excluir " + sel.getNome() + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            controller.excluir(sel.getId());
            carregarLista();
            JOptionPane.showMessageDialog(this, "Docente excluído.");
        }
    }

    private void editarSelecionado() {
        Docente sel = listaDocentes.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Selecione um docente.");
            return;
        }

        JTextField txtNome  = new JTextField(sel.getNome());
        JTextField txtEmail = new JTextField(sel.getEmail());
        JTextField txtCargo = new JTextField(sel.getCargo());
        JButton btnFoto     = new JButton("Trocar foto", IconLoader.loadScaled("/icons/photo.png", 18));
        final File[] novaFoto = { null };

        btnFoto.addActionListener(ev -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                novaFoto[0] = fc.getSelectedFile();
            }
        });

        JPanel p = new JPanel(new GridLayout(4,2,8,8));
        p.add(new JLabel("Nome:"));  p.add(txtNome);
        p.add(new JLabel("Email:")); p.add(txtEmail);
        p.add(new JLabel("Cargo:")); p.add(txtCargo);
        p.add(new JLabel("Foto:"));  p.add(btnFoto);

        int r = JOptionPane.showConfirmDialog(this, p, "Editar Docente", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            try {
                controller.atualizar(sel.getId(),
                        txtNome.getText().trim(),
                        txtEmail.getText().trim(),
                        txtCargo.getText().trim(),
                        novaFoto[0]);
                carregarLista();
                JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /* ------------ Renderer: foto + nome + cargo ------------ */

    private static class DocenteRenderer implements ListCellRenderer<Docente> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Docente> list,
                                                      Docente docente,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(10, 0));
            panel.setOpaque(true);

            // Foto 40x40 (ou placeholder)
            JLabel lblFoto = new JLabel();
            lblFoto.setPreferredSize(new Dimension(48, 48));
            lblFoto.setHorizontalAlignment(SwingConstants.CENTER);
            lblFoto.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

            ImageIcon fotoIcon = carregarFoto(docente.getCaminhoFoto(), 40);
            if (fotoIcon != null) {
                lblFoto.setIcon(fotoIcon);
            } else {
                lblFoto.setText("📷");
            }

            // Nome + Cargo (duas linhas)
            JLabel lblNome = new JLabel(docente.getNome());
            lblNome.setFont(lblNome.getFont().deriveFont(Font.BOLD, 14f));
            JLabel lblCargo = new JLabel(docente.getCargo());
            lblCargo.setFont(lblCargo.getFont().deriveFont(Font.PLAIN, 12f));
            lblCargo.setForeground(new Color(80, 80, 80));

            JPanel texto = new JPanel(new GridLayout(2,1));
            texto.setOpaque(false);
            texto.add(lblNome);
            texto.add(lblCargo);

            // seleção
            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                lblNome.setForeground(list.getSelectionForeground());
                lblCargo.setForeground(list.getSelectionForeground());
            } else {
                panel.setBackground(list.getBackground());
                lblNome.setForeground(list.getForeground());
                // lblCargo já está com cinza
            }

            panel.add(lblFoto, BorderLayout.WEST);
            panel.add(texto, BorderLayout.CENTER);
            return panel;
        }

        /** Carrega e redimensiona a foto do docente a partir de um caminho de arquivo. */
        private static ImageIcon carregarFoto(String caminho, int lado) {
            try {
                if (caminho == null || caminho.isBlank()) return null;
                File f = new File(caminho);
                if (!f.exists()) return null;
                ImageIcon base = new ImageIcon(caminho);
                Image img = base.getImage().getScaledInstance(lado, lado, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
