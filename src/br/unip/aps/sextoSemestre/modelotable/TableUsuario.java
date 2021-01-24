package br.unip.aps.sextoSemestre.modelotable;

import br.unip.aps.sextoSemestre.bean.Usuario;

public class TableUsuario extends TableImpl<Usuario> {
    
    public TableUsuario(String[] colunas) {
        super(colunas);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario =  rows.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return usuario.getNome();           
            case 1: return usuario.getCPF();            
            default: return null;            
        }
    }
    
    @Override
    public void setValueAt(Usuario usuario, int rowIndex) {
        Usuario usuarioUpdate = rows.get(rowIndex);
        
        usuarioUpdate.setCPF(usuario.getCPF());
        usuarioUpdate.setFoto(usuario.getFoto());
        usuarioUpdate.setImpressaoDigital(usuario.getImpressaoDigital());
        usuarioUpdate.setNome(usuario.getNome());
       
        for(int index = 0; index < getColumnCount(); ++index) {
            fireTableRowsUpdated(rowIndex, index);
        }
    }
}