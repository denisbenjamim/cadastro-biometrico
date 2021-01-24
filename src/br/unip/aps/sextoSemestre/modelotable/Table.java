package br.unip.aps.sextoSemestre.modelotable;

import java.util.Collection;

public interface Table<Type> {
    
    /**
     * Adiciona uma nova linha na tabela
     * @param value Bean que representa a nova linha
     */
    public void addRow(Type value);
    
    /**
     * Adiciona uma coleção do <Type> a tabela
     * @param rows Coleção de tipo <Type> 
     */
    public void addRows(Collection<Type> rows);    
    public void setValueAt(Type value, int rowIndex);    
    public Type getValue(int rowIndex);    
    public void removeValue(Type value);    
    public void removeValueByRow(int rowIndex);    
    public void removeRow(int rowIndex);    
    public void removeAllRows();    
    public void replaceRows(Collection<Type> linhas);    
    public Collection<Type> getRows();
    public void reloadRowsInBackground(Runnable thread, boolean daemon);
}
