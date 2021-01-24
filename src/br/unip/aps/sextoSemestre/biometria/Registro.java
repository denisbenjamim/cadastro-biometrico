// 
// Decompiled by Procyon v0.5.36
// 

package br.unip.aps.sextoSemestre.biometria;

import java.awt.Component;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import com.digitalpersona.onetouch.ui.swing.DPFPEnrollmentVetoException;
import com.digitalpersona.onetouch.ui.swing.DPFPEnrollmentEvent;
import com.digitalpersona.onetouch.ui.swing.DPFPEnrollmentListener;
import java.util.Collection;
import java.util.EnumSet;
import com.digitalpersona.onetouch.ui.swing.DPFPEnrollmentControl;
import java.awt.Frame;
import java.util.Iterator;
import com.digitalpersona.onetouch._impl.DPFPTemplateFactoryImpl;
import br.unip.aps.sextoSemestre.bean.ImpressaoDigital;
import java.util.Set;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import java.util.EnumMap;
import javax.swing.JDialog;

public class Registro extends JDialog
{
    private EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
    private Set<ImpressaoDigital> colecao;
    
    private void loadTemplates(final EnumMap<DPFPFingerIndex, DPFPTemplate> templates, final Set<ImpressaoDigital> colecao) {
        for (final ImpressaoDigital id : colecao) {
            final DPFPTemplate data = new DPFPTemplateFactoryImpl().createTemplate(id.getTemplate());
            templates.put(id.getDescricaoDedo(), data);
        }
    }
    
    public Registro(final Frame owner, final int maxCount, final String reasonToFail, final EnumMap<DPFPFingerIndex, DPFPTemplate> templates, final Set<ImpressaoDigital> colecao) {
        super(owner, true);
        this.loadTemplates(this.templates = templates, this.colecao = colecao);
        this.setTitle("Registro de Impress\u00e3o Digital");
        final DPFPEnrollmentControl enrollmentControl = new DPFPEnrollmentControl();
        final EnumSet<DPFPFingerIndex> fingers = EnumSet.noneOf(DPFPFingerIndex.class);
        fingers.addAll(templates.keySet());
        enrollmentControl.setEnrolledFingers(fingers);
        enrollmentControl.setMaxEnrollFingerCount(maxCount);
        enrollmentControl.addEnrollmentListener(new DPFPEnrollmentListener() {
            @Override
            public void fingerDeleted(final DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                }
                Registro.this.templates.remove(e.getFingerIndex());
                Registro.this.colecao.remove(new ImpressaoDigital(e.getFingerIndex(), Registro.this.templates.get(e.getFingerIndex()).serialize()));
            }
            
            @Override
            public void fingerEnrolled(final DPFPEnrollmentEvent e) throws DPFPEnrollmentVetoException {
                if (reasonToFail != null) {
                    throw new DPFPEnrollmentVetoException(reasonToFail);
                }
                Registro.this.templates.put(e.getFingerIndex(), e.getTemplate());
                Registro.this.colecao.add(new ImpressaoDigital(e.getFingerIndex(), Registro.this.templates.get(e.getFingerIndex()).serialize()));
            }
        });
        this.getContentPane().setLayout(new BorderLayout());
        final JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Registro.this.setVisible(false);
            }
        });
        final JPanel bottom = new JPanel();
        bottom.add(closeButton);
        this.add(enrollmentControl, "Center");
        this.add(bottom, "Last");
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
