package br.unip.aps.sextoSemestre.formularios;

import de.humatic.dsj.DSJUtils;
import java.beans.PropertyChangeEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import de.humatic.dsj.DSJException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import de.humatic.dsj.DSEnvironment;
import br.unip.aps.sextoSemestre.utilidades.DescompactarDlls;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JPanel;
import de.humatic.dsj.DSFilterInfo;
import java.util.List;
import de.humatic.dsj.DSCapture;
import java.beans.PropertyChangeListener;
import javax.swing.JDialog;

public class FormCamera extends JDialog implements PropertyChangeListener{
    private static DSCapture players;
    private List<DSFilterInfo> dispositivos;
    private JPanel paineldoPlayer;
    private JButton aceitar;
    private JButton tirar;
    private JButton cancelar;
   
    private String pathDLL;
    private BufferedImage foto;
    
    private boolean autoSelecionaDLL() {
        if(!System.getProperty("os.name").toLowerCase().contains("windows"))
            return false;
        
        setTitle("Seu SO " + System.getProperty("os.name") + " possui a Arquitetura " + System.getProperty("os.arch") + "  o usuario me execultando \u00e9 " + System.getProperty("user.name"));
        try {
            DescompactarDlls descompacta = FormularioPrincipal.getDlls();
            if ("amd64".equals(System.getProperty("os.arch"))) 
                pathDLL = String.format("%s%sdsj-amd64.dll", descompacta.getDllStorage().getPath(), File.separator);            
            else 
                pathDLL = String.format("%s%sdsj-x86.dll", descompacta.getDllStorage().getPath(), File.separator);
            
            DSEnvironment.setDLLPath(new File(pathDLL).getAbsolutePath());
            return true;
        }
        catch (UnsatisfiedLinkError ex) {
            JOptionPane.showMessageDialog(null, "O Arquivo \"dsj.dll\" N\u00e3o Foi Encontrado Em\n" + pathDLL, "Aten\u00e7\u00e3o", 0);
            ex.printStackTrace();
            return false;
        }
    }
    
    private void carregaDispositivosDeVideo() {
        DSFilterInfo[][] lista = DSCapture.queryDevices(8);
        for(DSFilterInfo dsfi : Arrays.asList(lista[0])) {
            if (dsfi.getName().equals("none")) continue;
            
            dispositivos.add(dsfi);
        }
    }
    
    public FormCamera(BufferedImage foto) {
        this();
        this.foto = foto;
    }
    
    public FormCamera() {
        dispositivos = new LinkedList<>();
        setDefaultCloseOperation(2);
        if (autoSelecionaDLL()) {
            carregaDispositivosDeVideo();
            Object dp = null;
            try {
                if (dispositivos.size() > 1) {
                    String[] nome = new String[dispositivos.size() + 1];
                    nome[0] = "Selecione um Dispositivo";
                    for (int i = 1; i <= dispositivos.size(); ++i) {
                        nome[i] = dispositivos.get(i - 1).getName();
                    }
                    dp = JOptionPane.showInputDialog(null, "Foi encontrado o total de " + dispositivos.size() + " dispositivos, selecione um para continuar.", "Mensagem do Sistema", 2, null, nome, null);
                }
                for (DSFilterInfo dsfi : dispositivos) {
                    if (dp != null && dsfi.getName().equals(dp)) {
                        players = new DSCapture(0, dsfi, false, DSFilterInfo.doNotRender(), null);
                    }
                    else {
                        players = new DSCapture(0, dsfi, false, DSFilterInfo.doNotRender(), null);
                    }
                }
                players.setSize(640, 480);
                players.stop();
            }
            catch (DSJException e) {
                JOptionPane.showMessageDialog(null, "N\u00e3o Foi Poss\u00edvel Iniciar a WebCam. Poss\u00edveis Causas:\n- O Aplicativo J\u00e1 Esta Sendo Executado (Gerenciador de Tarefas -> Processos -> javaw.exe)\n- A WebCam Est\u00e1 Desconectada\n- O Windows N\u00e3o Reconheceu a WebCam", "Aten\u00e7\u00e3o", 0);
            }
        }
        (aceitar = new JButton("Aceitar")).setEnabled(false);
        aceitar.addActionListener((ActionEvent e) -> {
            players.stop();
            players.dispose();
            dispose();
        });
        (tirar = new JButton("Tirar")).addActionListener((ActionEvent e) -> {
            cancelar.setEnabled(true);
            aceitar.setEnabled(true);
            tirar.setEnabled(false);
            players.stop();
            setFoto(players.getImage());
        });
        (cancelar = new JButton("Cancelar")).addActionListener((ActionEvent e) -> {
            cancelar.setEnabled(false);
            aceitar.setEnabled(false);
            tirar.setEnabled(true);
            setFoto(null);
            tirar.grabFocus();
            players.play();
        });
       
        (paineldoPlayer = new JPanel(new GridLayout(1, 3))).add(tirar);
        paineldoPlayer.add(aceitar);
        paineldoPlayer.add(cancelar);
        setLayout(new BorderLayout());
        add("Center", players.asComponent());
        add("South", paineldoPlayer);
        setDefaultCloseOperation(2);
        setSize(640, 480);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
            
            @Override
            public void windowActivated(WindowEvent e) {
                players.play();
            }
        });
        setAlwaysOnTop(true);
        setModal(true);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new FormCamera();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DSJUtils.getEventType(evt);
    }
    
    public BufferedImage getFoto() {
        return foto;
    }
    
    public void setFoto(BufferedImage foto) {
        this.foto = foto;
    }
}
