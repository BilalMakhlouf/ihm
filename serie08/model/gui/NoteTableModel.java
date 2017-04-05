package serie08.model.gui;

import java.util.List;

import javax.swing.table.TableModel;

/**
 * Le type des mod�les de donn�es pour les JTables de l'application.
 * @inv
 *     getColumnCount() == ColumnFeatures.values().length
 *     forall f:ColumnFeatures.values() :
 *         getColumnName(f.ordinal()).equals(f.header())
 *         getColumnClass(f.ordinal()).equals(f.type())
 *     getRowCount() >= 0
 *     forall i:[0..getRowCount()[, j:[0..getColumnCount()[ :
 *         isCellEditable(i, j) == (j != getColumnCount() - 1)
 *         getValueAt(i, j) != null
 *     forall i:[0..getRowCount()[ :
 *         getValueAt(i, POINTS).equals(
 *             (Double) getValueAt(i, COEF) * (Double) getValueAt(i, MARK)
 *         )
 */
public interface NoteTableModel extends TableModel {
    
    // ATTRIBUTS
    
    Double ZERO = new Double(0);
    int SUBJECT = ColumnFeatures.SUBJECT.ordinal();
    int COEF = ColumnFeatures.COEF.ordinal();
    int MARK = ColumnFeatures.MARK.ordinal();
    int POINTS = ColumnFeatures.POINTS.ordinal();
    
    // REQUETES
    
    /**
     * La classe des �l�ments situ�s dans les cellules de la colonne index.
     * @pre
     *     0 <= index < getColumnCount()
     */
    Class<?> getColumnClass(int index);
    
    /**
     * Le nombre de colonnes.
     */
    int getColumnCount();
    
    /**
     * L'ent�te de la colonne index.
     * @pre
     *     0 <= index < getColumnCount()
     */
    String getColumnName(int index);
    
    /**
     * Une ligne vide ("", ZERO, ZERO).
     */
    List<Object> getEmptyDataRow();
    
    /**
     * Le nombre de lignes.
     */
    int getRowCount();
    
    /**
     * La valeur de la cellule en (row, column).
     * @pre
     *     0 <= row < getRowCount()
     *     0 <= column < getColumnCount()
     */
    Object getValueAt(int row, int column);
    
    /**
     * Indique si cette cellule est �ditable.
     * @pre
     *     0 <= row < getRowCount()
     *     0 <= column < getColumnCount()
     */
    boolean isCellEditable(int row, int column);

    // COMMANDES
    
    /**
     * Ajoute une nouvelle ligne � la fin du mod�le.
     * @pre
     *     line != null
     *     line.size() == 3
     *     forall i:[0..ColumnFeatures.values().length - 2] :
     *         line.get(i) != null
     *         line.get(i).getClass() == ColumnFeatures.values()[i].type()
     * @post
     *     getRowCount() == old getRowCount() + 1
     *     les lignes du mod�le entre 0 et old getRowCount()-1 n'ont pas chang�
     *     line repr�sente la ligne du mod�le en getRowCount() - 1
     */
    void addRow(List<Object> line);
    
    /**
     * Supprime toutes les lignes dans le mod�le.
     * @post
     *     getRowCount() == 0
     */
    void clearRows();
    
    /**
     * Ins�re une nouvelle ligne dans le mod�le.
     * @pre
     *     line != null
     *     line.size() == 3
     *     forall i:[0..ColumnFeatures.values().length - 2] :
     *         line.get(i) != null
     *         line.get(i).getClass() == ColumnFeatures.values()[i].type()
     *     0 <= index && index <= getRowCount()
     * @post
     *     getRowCount() == old getRowCount() + 1
     *     les lignes du mod�le entre 0 et index - 1 n'ont pas chang�
     *     line repr�sente la ligne du mod�le en position index
     *     forall i:[index+1..getRowCount()[ :
     *         la ligne du mod�le en position i correspond � l'ancienne
     *          ligne du mod�le en position i - 1
     */
    void insertRow(List<Object> line, int index);
    
    /**
     * Retire la ligne en position index dans le mod�le.
     * @pre
     *     0 <= index && index < getRowCount()
     * @post
     *     getRowCount() == old getRowCount() - 1
     *     les lignes du mod�le entre 0 et index - 1 n'ont pas chang�
     *     forall i:[index..getRowCount()[ :
     *         la ligne du mod�le en position i correspond � l'ancienne
     *          ligne du mod�le en position i + 1
     */
    void removeRow(int index);
    
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
    void setValueAt(Object value, int row, int column);
}
