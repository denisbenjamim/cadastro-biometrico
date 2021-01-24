package br.unip.aps.sextoSemestre.modelotable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class TableImpl<Type> extends AbstractTableModel implements Table<Type>{
    
    private final String[] columns ;
    public final List<Type> rows = new LinkedList();
    
    public TableImpl(String[] columns) {
        this.columns = columns;
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public int getRowCount() {
        return rows.size();
    }
    
    @Override
    public int getColumnCount() {
        return columns.length;
    }
    
    @Override
    public void addRow(Type value) {
        rows.add(value);
        int last = getRowCount() - 1;
        fireTableRowsInserted(last, last);
    }
    
     @Override
    public void addRows(Collection<Type> rows) {
        int oldSize = getRowCount();
        rows.addAll(rows);
        fireTableRowsInserted(oldSize, getRowCount() - 1);
    }
    
    @Override
    public Type getValue(int rowIndex) {
        return rows.get(rowIndex);
    }
    
    @Override
    public void removeValue(Type value) {
        removeRow(rows.indexOf(value));
    }
    
    @Override
    public void removeValueByRow(int rowIndex) {
        removeRow(rowIndex);
    }      
    
    @Override
    public void removeRow(int rowIndex) {
        rows.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }  
    
    @Override
    public void removeAllRows(){
        if (!rows.isEmpty()) {
            fireTableRowsDeleted(0, rows.size() - 1);
            rows.clear();
        }
    }
    
    @Override
    public void replaceRows(Collection<Type> values) {
        removeAllRows();
        if(values.isEmpty()) return;
        
        values.forEach(this::addRow);
    }
    
     @Override
    public List<Type> getRows() {
        return rows;
    }
    
    @Override 
    public void reloadRowsInBackground(Runnable runnable, boolean daemon) {
        Thread segmento = new Thread(runnable);
        segmento.setDaemon(daemon);
        segmento.start();
    }
}
