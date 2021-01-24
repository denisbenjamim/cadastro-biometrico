package br.unip.aps.sextoSemestre.formularios;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

import javax.swing.ImageIcon;
import br.unip.aps.sextoSemestre.utilidades.Criptografia;
import br.unip.aps.sextoSemestre.bean.ImpressaoDigital;
import br.unip.aps.sextoSemestre.bean.Usuario;
import br.unip.aps.sextoSemestre.modelotable.Table;
import java.awt.Dimension;
import java.awt.Toolkit;
import org.jdesktop.layout.GroupLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import br.unip.aps.sextoSemestre.modelotable.TableUsuario;
import java.util.HashSet;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.image.BufferedImage;
import java.util.Set;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import java.util.EnumMap;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class FormularioUsuario extends JInternalFrame {

    private final EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    private Set<ImpressaoDigital> impressoesDigitais;
    private final Criptografia<Usuario> criptografia;
    private BufferedImage imagemCan;
    private final Table<Usuario> tabelaUsuario;
    private JButton jBCadastrar;
    private JButton jB_novo;
    private JButton jBFoto;
    private JButton jB_adicionarBiometria;
    private JLabel jL_Quadro;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JScrollPane jScrollPane1;
    private JTextField jTF_CPF;
    private JTextField jTF_nome;
    private JTabbedPane jTP_abas;
    private JTable jTableUsuario;

    public FormularioUsuario() {
        templates = new EnumMap<>(DPFPFingerIndex.class);
        impressoesDigitais = new HashSet<>();
        criptografia = new Criptografia();
        imagemCan = null;
        initComponents();
        tabelaUsuario = (Table) jTableUsuario.getModel();
    }

    private void initComponents() {
        jTP_abas = new JTabbedPane();
        jPanel1 = new JPanel();
        jTF_nome = new JTextField();
        jTF_CPF = new JTextField();
        jL_Quadro = new JLabel();
        jBFoto = new JButton();
        jB_adicionarBiometria = new JButton();
        jPanel3 = new JPanel();
        jBCadastrar = new JButton();
        jB_novo = new JButton();
        jPanel2 = new JPanel();
        jScrollPane1 = new JScrollPane();
        jTableUsuario = new JTable(new TableUsuario(new String[]{"Nome", "CPF"}));
        setClosable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Autentica\u00e7\u00e3o");
        jTP_abas.setTabPlacement(3);
        jTP_abas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                jTP_mouseClicked(evt);
            }
        });
        jTF_nome.setBorder(BorderFactory.createTitledBorder("Nome Usuario:"));
        jTF_CPF.setBorder(BorderFactory.createTitledBorder("CPF Usuario:"));
        jL_Quadro.setToolTipText("");
        jL_Quadro.setBorder(BorderFactory.createEtchedBorder());
        jBFoto.setFont(new Font("Tahoma", 1, 11));
        jBFoto.setText("Adicionar Foto");
        jBFoto.addActionListener(this::jBFotoActionPerformed);
        jB_adicionarBiometria.setFont(new Font("Tahoma", 3, 18));
        jB_adicionarBiometria.setForeground(new Color(0, 102, 0));
        jB_adicionarBiometria.setText("Adicionar Dados Biometricos");
        jB_adicionarBiometria.addActionListener(this::jBAdicionarBiometriaActionPerformed);
        jPanel3.setLayout(new GridLayout(1, 0));
        jBCadastrar.setFont(new Font("Tahoma", 1, 11));
        jBCadastrar.setText("Cadastrar");
        jBCadastrar.addActionListener(this::jBCadastrarActionPerformed);
        jPanel3.add(jBCadastrar);
        jB_novo.setFont(new Font("Tahoma", 1, 11));
        jB_novo.setText("Novo");
        jB_novo.addActionListener(this::jBNovo);
        jPanel3.add(jB_novo);
        final GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(1).add(jPanel1Layout.createSequentialGroup().addContainerGap().add(jPanel1Layout.createParallelGroup(1).add(jPanel1Layout.createSequentialGroup().add(jPanel1Layout.createParallelGroup(1).add(jTF_CPF).add(jB_adicionarBiometria, -1, 296, 32767).add(jTF_nome)).add(10, 10, 10).add(jPanel1Layout.createParallelGroup(1, false).add(jBFoto, -1, -1, 32767).add(jL_Quadro, -1, -1, 32767))).add(jPanel3, -1, -1, 32767)).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(1).add(jPanel1Layout.createSequentialGroup().addContainerGap().add(jPanel1Layout.createParallelGroup(1, false).add(jPanel1Layout.createSequentialGroup().add(jTF_nome, -2, -1, -2).addPreferredGap(0).add(jTF_CPF, -2, -1, -2).addPreferredGap(0).add(jB_adicionarBiometria, -1, -1, 32767)).add(jPanel1Layout.createSequentialGroup().add(jL_Quadro, -2, 103, -2).addPreferredGap(0).add(jBFoto))).addPreferredGap(0, 37, 32767).add(jPanel3, -2, -1, -2).addContainerGap()));
        jTP_abas.addTab("Dados", jPanel1);
        jTableUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent evt) {
                jTableUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableUsuario);
        final GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(1).add(jPanel2Layout.createSequentialGroup().addContainerGap().add(jScrollPane1, -1, 421, 32767).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(1).add(jPanel2Layout.createSequentialGroup().addContainerGap().add(jScrollPane1, -1, 192, 32767).addContainerGap()));
        jTP_abas.addTab("Cadastrados", jPanel2);
        final GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(1).add(jTP_abas));
        layout.setVerticalGroup(layout.createParallelGroup(1).add(jTP_abas));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 452) / 2, (screenSize.height - 265) / 2, 452, 265);
    }

    private void jBFotoActionPerformed(ActionEvent evt) {
        FormCamera fotoPainel = new FormCamera();
        imagemCan = fotoPainel.getFoto();
        if (imagemCan == null) {
            return;
        }

        jL_Quadro.setIcon(resizeImagemIconToLabel());
    }

    private ImageIcon resizeImagemIconToLabel() {
        return new ImageIcon(imagemCan.getScaledInstance(jL_Quadro.getWidth(), jL_Quadro.getHeight(), 100));
    }

    private void jBAdicionarBiometriaActionPerformed(ActionEvent evt) {
        new br.unip.aps.sextoSemestre.biometria.Registro(null, 10, null, templates, impressoesDigitais).setVisible(true);
    }

    private void jBCadastrarActionPerformed(ActionEvent evt) {
        Usuario usuario = Usuario.builder()
                .CPF(jTF_CPF.getText())
                .nome(jTF_nome.getText())
                .impressaoDigital(impressoesDigitais)
                .foto(convertImageIconToByteArray())
                .build();
        criptografia.salvarComoArquivoCriptografado(usuario, usuario.getNome() + " - " + usuario.getCPF());
        JOptionPane.showMessageDialog(rootPane, "Cadastrado com sucesso!");
        jBNovo(null);
    }

    private void jTP_mouseClicked(MouseEvent evt) {

        if (jTP_abas.getSelectedIndex() == 1 && tabelaUsuario.getRows().isEmpty()) {
            for (File arquivo : criptografia.getPastaArquivos().listFiles()) {
                tabelaUsuario.addRow(criptografia.recuperarArquivoCriptografado(arquivo));
            }
        }

    }

    private void jTableUsuarioMouseClicked(final MouseEvent evt) {
        Usuario usuario = tabelaUsuario.getValue(jTableUsuario.getSelectedRow());
        jTF_CPF.setText(usuario.getCPF());
        jTF_nome.setText(usuario.getNome());
        impressoesDigitais = usuario.getImpressaoDigital();
        jL_Quadro.setIcon(convertByteArrayToImageIcon(usuario.getFoto()));
        jTP_abas.setSelectedIndex(0);
    }

    private void jBNovo(ActionEvent actionEvent) {
        jTF_CPF.setText("");
        jTF_nome.setText("");
        impressoesDigitais = null;
        jL_Quadro.setIcon(null);
    }

    private byte[] convertImageIconToByteArray() {
        if (imagemCan != null) {
            try (ByteArrayOutputStream bytesImg = new ByteArrayOutputStream()) {
                ImageIO.write(imagemCan, "jpg", bytesImg);
                bytesImg.flush();
                return bytesImg.toByteArray();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    private ImageIcon convertByteArrayToImageIcon(byte[] dadosImagem) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(dadosImagem)) {
            imagemCan = ImageIO.read(bis);
            if (imagemCan != null) {
                return resizeImagemIconToLabel();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
