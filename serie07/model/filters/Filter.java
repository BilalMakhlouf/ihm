package serie07.model.filters;

import java.beans.PropertyChangeListener;
import java.util.List;


/**
 * Définit les filtres pour les listes d'éléments filtrables de type E,
 *  basés sur des valeurs de type V.
 * @inv <pre>
 *     getValue() != null
 *     filter(list) != null && filter(list) != list
 *     forall e:E :
 *         list.contains(e) && isValid(e) <==> filter(list).contains(e) </pre>
 */
public interface Filter<E extends Filterable<V>, V> {
    
    // REQUETES
    
    /**
     * Retourne, à partir de list, une nouvelle liste constituée des valeurs
     *  filtrées selon ce filtre lorsqu'il est basé sur la valeur getValue().
     * @pre <pre>
     *     list != null </pre>
     */
    List<E> filter(List<E> list);
    
    /**
     * La valeur associée à ce filtre.
     */
    V getValue();
    
    /**
     * Un tableau de tous les écouteurs de changement de la propriété value.
     */
    PropertyChangeListener[] getValueChangeListeners();
    
    /**
     * Indique si l'élément e est accepté par ce filtre lorsqu'il est basé
     *  sur la valeur getValue().
     * @pre <pre>
     *     e != null
     *     e.filtrableValue() != null </pre>
     */
    boolean isValid(E e);
    
    // COMMANDES
    
    /**
     * Enregistre un écouteur de changement de la propriété value.
     */
    void addValueChangeListener(PropertyChangeListener lst);
    
    /**
     * Retire un écouteur de changement de la propriété value.
     */
    void removeValueChangeListener(PropertyChangeListener lst);
    
    /**
     * Change la valeur associée à ce filtre.
     * @pre <pre>
     *     value != null </pre>
     * @post <pre>
     *     getValue() == value </pre>
     */
    void setValue(V value);
}
