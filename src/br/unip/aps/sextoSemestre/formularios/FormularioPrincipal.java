package br.unip.aps.sextoSemestre.formularios;


import br.unip.aps.sextoSemestre.utilidades.DescompactarDlls;
import br.unip.aps.sextoSemestre.utilidades.RegistrarDLLsBiometria;
import java.awt.EventQueue;
import javax.swing.JOptionPane;
import org.jdesktop.layout.GroupLayout;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTabbedPane;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import lombok.Getter;

public class FormularioPrincipal extends JFrame{
    private JDesktopPane painelInterno;
    private JMenu jM_arquivo;
    private JMenu jM_equipe;
    private JMenuBar jMB_barra;
    private JMenuItem jMI_cadastroUsuario;    
    private JTabbedPane jTP_abas;
    @Getter
    public static DescompactarDlls dlls = DescompactarDlls.build();
    public FormularioPrincipal() {
        
        RegistrarDLLsBiometria.build(dlls.getDllStorage().getAbsolutePath());
        
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void initComponents() {
        jTP_abas = new JTabbedPane();
        painelInterno = new JDesktopPane();
        jMB_barra = new JMenuBar();
        jM_arquivo = new JMenu();
        jMI_cadastroUsuario = new JMenuItem();
        jM_equipe = new JMenu();        
        setDefaultCloseOperation(3);
        setTitle("Autentica\u00e7\u00e3o Biometrica");
        jTP_abas.setTabPlacement(3);
        painelInterno.setBackground(new Color(204, 204, 0));
        jTP_abas.addTab("Janelas", painelInterno);
        jM_arquivo.setText("Arquivo");
        jMI_cadastroUsuario.setText("Cadastro Usuario");
        jMI_cadastroUsuario.addActionListener(this::jMI_cadastroUsuarioActionPerformed);
        jM_arquivo.add(jMI_cadastroUsuario);
        jMB_barra.add(jM_arquivo);
        jM_equipe.setText("Equipe");
        jM_equipe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               jMI_nomesActionPerformed();
            }
            
        });        
        jMB_barra.add(jM_equipe);
        setJMenuBar(jMB_barra);
        final GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(1).add(jTP_abas, -1, 400, 32767));
        layout.setVerticalGroup(layout.createParallelGroup(1).add(jTP_abas, -1, 279, 32767));
        pack();
    }
    
    private void jMI_cadastroUsuarioActionPerformed(final ActionEvent evt) {
        painelInterno.add(new FormularioUsuario()).setVisible(true);
    }
    
    private void jMI_nomesActionPerformed() {
        StringBuilder membros = new StringBuilder()
            .append("Aldemir de Almeida - RA 6724183 - (aldemir-10@hotmail.com)\n")
            .append("David Welling - RA A646HJ6 - (david_ckwelling@hotmail.com)\n")
            .append("Denis Benjamim -  RA 6327699 - (denis.benjamim@gmail.com)\n")
            .append("Denis Dorow - RA A63IHI2 - (denisdj2008@gmail.com)")           
            ;
        JOptionPane.showMessageDialog(rootPane, membros.toString(),"Alunos", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(final String[] args) {
        EventQueue.invokeLater(() -> {
            new FormularioPrincipal().setVisible(true);
        });
    }
}
