package serie08.model.application;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.regex.Pattern;

import serie08.model.gui.NoteTableModel;

public interface NoteSheetModel {
    
    // ATTRIBUTS
    
    char COMMENT_CHAR = '#';
    String FIELD_SEP = "\t";
    Pattern LINE_PAT = Pattern.compile("^[^" + FIELD_SEP + "]*(" + FIELD_SEP
            + "\\d+\\.?\\d*){2}(" + FIELD_SEP + ".*)?$");

    // REQUETES
    
    /**
     * La moyenne des notes du modèle, tenant compte des coefficients.
     * @pre
     *     m != null
     * @post
     *     m.getRowCount() > 0
     *         ==> result ==
     *                 getPoints(m)
     *                 / sum(i:[0..m.getRowCount()[, m.getValueAt(i, COEF))
     *     m.getRowCount() == 0
     *         ==> result == Double.NaN
     */
    double getMean(NoteTableModel m);
    
    /**
     * Le nombre de points correspondants aux notes stockées dans le modèle,
     *  calculé comme la somme des (coef * note).
     * @pre
     *     m != null
     * @post
     *     result == sum(i:[0..getRowCount()[, m.getValueAt(i, POINTS))
     */
    double getPoints(NoteTableModel m);

    // COMMANDES
    
    /**
     * Installe un PropertyChangeListener utilisable sur les (pseudo-)propriétés
     *  row, saved, progress et error.
     */
    void addPropertyChangeListener(String prop, PropertyChangeListener lst);
    
    /**
     * Désinstalle le PropertyChangeListener pour la propriété indiquée.
     */
    void removePropertyChangeListener(String prop, PropertyChangeListener lst);
    
    /**
     * Charge un fichier de notes.
     * Au cours du chargement, des modifications de valeur pour les
     *  (pseudo-)propriétés row, progress et error auront lieu.<br />
     * <em>Les concrétisations de cette méthode doivent garantir que :
     * <ul>
     *   <li>elles ne surchargent pas EDT</li>
     *   <li>les mises à jour du modèle se font sur EDT</li>
     * </ul>
     * </em>
     * @pre
     *     f != null
     * @post
     *     le modèle contient les données (lignes de f reconnues par LINE_PAT)
     */
    void loadFile(final File f);
    
    /**
     * Enregistre un fichier de notes.
     * Au cours de l'enregistrement, des modifications de valeur pour les
     *  (pseudo-)propriétés saved, progress et error auront lieu.<br />
     * <em>Les concrétisations de cette méthode doivent garantir que :
     * <ul>
     *   <li>elles ne surchargent pas EDT</li>
     *   <li>les mises à jour du modèle se font sur EDT</li>
     * </ul>
     * </em>
     * @pre
     *     f != null
     * @post
     *     f est un fichier texte contenant toutes les données du modèle 
     *     f commence par :
     *         COMMENT_CHAR + " "
     *          + ColumnFeatures.SUBJECT.header() + FIELD_SEP
     *          + ColumnFeatures.COEF.header() + FIELD_SEP
     *          + ColumnFeatures.MARK.header()
     *     suivi d'une ligne vierge
     *     suivi de getRowCount() lignes reconnues par LINE_PAT
     */
    void saveFile(final File f, NoteTableModel m);
}
