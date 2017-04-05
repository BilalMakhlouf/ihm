package serie09;

import static serie09.GenCellRenderer.FONT_NAME;
import static serie09.GenCellRenderer.FONT_SIZE;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;


public class GenCellEditor extends DefaultCellEditor {
    
    // CONSTANTES
    
    public static final char FIELD_SEP = ',';

    private static final Gender[] genders = Gender.values();

    // CONSTRUCTEURS
    
    public GenCellEditor() {
        /*
         * Le JTextField pass� au constructeur est l'�diteur de cellules
         *  (l'attribut editorComponent du DefaultCellEditor).
         */
        super(new JTextField() {
            {
                setBorder(UIManager.getBorder("Tree.editorBorder"));
                setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
            }
        });
        final JTextField textField = (JTextField) editorComponent;
        /*
         * Le constructeur de DefaultCellEditor a associ� un EditorDelegate
         *  � l'�diteur de cellules mais cela ne nous conviendra pas, donc on
         *  l'�limine pour en faire un autre mieux adapt�.
         */
        textField.removeActionListener(delegate);
        delegate = new EditorDelegate() {
            /*
             * C'est � travers cette m�thode que l'on d�finit ce que l'on veut
             *  r�cup�rer de l'�diteur de cellules.
             */
            @Override
            public Object getCellEditorValue() {
                return explode(textField.getText());
            }
        };
        textField.addActionListener(delegate);
    }

    // REQUETES
    
    /*
     * C'est � travers cette m�thode que l'on d�finit ce que l'on veut voir
     *  affich� dans l'�diteur de cellules au moment de l'�dition.
     */
    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean isSelected,
            boolean expanded,
            boolean leaf, int row) {
        JTextField textField = (JTextField) super.getTreeCellEditorComponent(
                tree, value, isSelected, expanded, leaf, row);
        Person p = (Person) ((DefaultMutableTreeNode) value).getUserObject();
        textField.setText(implode(p));
        return textField;
    }

    // OUTILS
    
    private Person explode(String value) {
        int ordinal = 0;
        String name = value;
        String[] parts = value.split(String.valueOf(FIELD_SEP));
        if (parts.length > 1) {
            try {
                String val = parts[parts.length - 1].trim();
                ordinal = Integer.valueOf(val);
            } catch (NumberFormatException e) {
                // rien : ordinal reste � 0
            }
            if (ordinal >= 0 && ordinal < genders.length) {
                String s = parts[0];
                for (int i = 1; i < parts.length - 1; i++) {
                    s += FIELD_SEP + parts[i];
                }
                name = s;
            }
        }
        return new Person(name, genders[ordinal]);
    }
    
    private String implode(Person p) {
        return p.getName() + FIELD_SEP + p.getGender().ordinal();
    }
}
