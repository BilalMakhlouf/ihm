package serie08.model.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import square.util.Contract;

@SuppressWarnings("serial")
public class DefaultNoteTableModel 
	extends AbstractTableModel implements NoteTableModel {

	private static final ColumnFeatures[] COL_FEATS = ColumnFeatures.values();
	
	private List<List<Object>> data;
	
	public DefaultNoteTableModel() {
		data = new ArrayList<List<Object>>();
		this.addRow(this.getEmptyDataRow());
	}
	
	
	/**
     * La classe des éléments situés dans les cellules de la colonne index.
     * @pre
     *     0 <= index < getColumnCount()
     */
    public Class<?> getColumnClass(int index) {
    	Contract.checkCondition(0 <= index && index < getColumnCount());
    	return COL_FEATS[index].type();
    }
	
	@Override
	public int getColumnCount() {
		return (data.size() == 0) ?  data.size() : data.get(0).size();
	}
	
	/**
     * L'entête de la colonne index.
     * @pre
     *     0 <= index < getColumnCount()
     */
	@Override
	public String getColumnName(int index) {
		Contract.checkCondition(0 <= index && index < getColumnCount());
    	return COL_FEATS[index].header();
	}
    
	/**
     * Une ligne vide ("", ZERO, ZERO).
     */
	@Override
	public List<Object> getEmptyDataRow() {
		List<Object> res = new ArrayList<Object>();
		res.add("");
		res.add(ZERO);
		res.add(ZERO);
		return res;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return data.get(row).get(column);
	}
	
	/**
     * Indique si cette cellule est éditable.
     * @pre
     *     0 <= row < getRowCount()
     *     0 <= column < getColumnCount()
     */
    public boolean isCellEditable(int row, int column) {
    	Contract.checkCondition(0 <= row && row < getRowCount());
    	Contract.checkCondition(0 <= column && column < getColumnCount());
    	return column != (getColumnCount() - 1);
    }

	@Override
	public void addRow(List<Object> line) {
		data.add(line);
		calculatePoints(getRowCount() - 1);
		super.fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	@Override
	public void clearRows() {
		int size = data.size();
		for (int i = 0; i < size; i++) {
			data.remove(0);
		}
		super.fireTableRowsDeleted(0, (size == 0) ? 0 : size - 1);
	}

	@Override
	public void insertRow(List<Object> line, int index) {
		data.add(index, line);
		calculatePoints(index);
		super.fireTableRowsInserted(index, index);
	}

	@Override
	public void removeRow(int index) {
		data.remove(index);
		super.fireTableRowsDeleted(index, index);
	}
	
	private void calculatePoints(int row) {
		if (data.get(row).size() < 4) {
			data.get(row).add(0.0);
		}
		data.get(row).set(3, 
				(Double) data.get(row).get(1) * (Double) data.get(row).get(2));
	}
	
	/**
     * @pre
     *     value == null || value.getClass() == getColumnClass(column)
     *     0 <= row < getRowCount()
     *     0 <= column < getColumnCount() - 1
     * @post
     *     value == null
     *         ==> getValueAt(row, column).equals(
     *                 ColumnFeatures.values()[column].defaultValue())
     *     value != null
     *         ==> getValueAt(row, column).equals(value)
     */
    public void setValueAt(Object value, int row, int column) {
    	Contract.checkCondition(value == null 
    			|| value.getClass() == getColumnClass(column));
    	Contract.checkCondition(0 <= row && row < getRowCount());
    	Contract.checkCondition(0 <= column && column < getColumnCount() - 1);
    	if (value == null) {
    		data.get(row).set(column, COL_FEATS[column].defaultValue());
    	} else {
    		data.get(row).set(column, value);
    	}
    	calculatePoints(row);
    	super.fireTableCellUpdated(row, column);
    	super.fireTableCellUpdated(row, 3);
    }

}
