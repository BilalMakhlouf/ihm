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
         * Le JTextField passé au constructeur est l'éditeur de cellules
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
         * Le constructeur de DefaultCellEditor a associé un EditorDelegate
         *  à l'éditeur de cellules mais cela ne nous conviendra pas, donc on
         *  l'élimine pour en faire un autre mieux adapté.
         */
        textField.removeActionListener(delegate);
        delegate = new EditorDelegate() {
            /*
             * C'est à travers cette méthode que l'on définit ce que l'on veut
             *  récupérer de l'éditeur de cellules.
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
     * C'est à travers cette méthode que l'on définit ce que l'on veut voir
     *  affiché dans l'éditeur de cellules au moment de l'édition.
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
                // rien : ordinal reste à 0
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
