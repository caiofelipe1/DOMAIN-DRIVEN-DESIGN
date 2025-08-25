package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TelaConsultaDocente extends JFrame {
    private DefaultListModel<String> listaModel;
    private JList<String> listaDocentes;
    private ArrayList<String> docentes; // Simulação de dados

    public TelaConsultaDocente() {
        setTitle("Consulta de Docentes");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font fonteTitulo = new Font("SansSerif", Font.BOLD, 18);
        Font fonteCampos = new Font("SansSerif", Font.PLAIN, 14);
        Color verdePrincipal = new Color(60, 179, 113);

        JLabel lblTitulo = new JLabel("Docentes Cadastrados");
        lblTitulo.setFont(fonteTitulo);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        listaModel = new DefaultListModel<>();
        docentes = new ArrayList<>();

        // Simulação de dados
        docentes.add("Maria - Professora");
        docentes.add("João - Coordenador");
        docentes.add("Ana - Diretora");

        for (String d : docentes) {
            listaModel.addElement(d);
        }

        listaDocentes = new JList<>(listaModel);
        listaDocentes.setFont(fonteCampos);
        JScrollPane scrollPane = new JScrollPane(listaDocentes);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setIcon(new ImageIcon("icons/edit.png"));
        btnEditar.setBackground(verdePrincipal);
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setFont(fonteCampos);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setIcon(new ImageIcon("icons/delete.png"));
        btnExcluir.setBackground(verdePrincipal);
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFocusPainted(false);
        btnExcluir.setFont(fonteCampos);

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(btnEditar);
        botoesPanel.add(btnExcluir);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        btnEditar.addActionListener(e -> {
            String selecionado = listaDocentes.getSelectedValue();
            if (selecionado != null) {
                JOptionPane.showMessageDialog(this, "Editar: " + selecionado);
                // Aqui você pode abrir a tela de edição com os dados preenchidos
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um docente para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            String selecionado = listaDocentes.getSelectedValue();
            if (selecionado != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir " + selecionado + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listaModel.removeElement(selecionado);
                    JOptionPane.showMessageDialog(this, "Docente excluído com sucesso.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um docente para excluir.");
            }
        });
    }
}