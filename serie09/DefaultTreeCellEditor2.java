package serie09;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;


public class DefaultTreeCellEditor2 extends DefaultTreeCellEditor {
    public DefaultTreeCellEditor2(JTree t, DefaultTreeCellRenderer r,
            TreeCellEditor e) {
        super(t, r, e);
    }
    /**
     * Apparemment, cette m�thode ne configure pas son renderer lorsqu'elle
     *  s'ex�cute, c'est pourquoi il nous faut le faire nous-m�me... Beug ?
     */
    @Override
    public Component getTreeCellEditorComponent(JTree t, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row) {
        renderer.getTreeCellRendererComponent(t, value, isSelected,
                expanded, leaf, row, false);
        return super.getTreeCellEditorComponent(t, value, isSelected,
                expanded, leaf, row);
    }
}
