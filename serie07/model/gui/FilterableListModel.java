package serie07.model.gui;

import java.util.Collection;

import javax.swing.ListModel;

import serie07.model.filters.Filter;
import serie07.model.filters.Filterable;


/**
 * Modèles de listes filtrables.
 * Un tel modèle est constitué de deux listes :
 * <ol>
 *   <li>Une liste initiale comprenant tous les éléments.</li>
 *   <li>Une liste filtrée comprenant parmi les éléments initiaux ceux qui
 *       sont acceptés par le filtre.</li>
 * </ol>
 * Le modèle, tel qu'il est présenté par ListModel, donne accès à la liste
 *  filtrée ; on rajoute ici les méthodes nécessaires pour accéder à la liste
 *  initiale.
 * E est le type des éléments du modèle ; le type des valeurs associées
 *  au filtre du modèle est V.
 * @inv <pre>
 *     0 <= getSize() <= getUnfilteredSize()
 *     getFilter() == null ==> getSize() == getUnfilteredSize()
 *     forall i:[0..getUnfilteredSize()[ :
 *         getFilter().isValid(getUnfilteredElementAt(i))
 *             <==> exists j:[0..getSize()[ :
 *                      getElementAt(j) == getUnfilteredElementAt(i) </pre>
 */
public interface FilterableListModel<E extends Filterable<V>, V>
        extends ListModel {
    
    // REQUETES
    
    /**
     * Le filtre actuel de ce modèle filtrable.
     */
    Filter<E, V> getFilter();
    
    /**
     * Le ième élément de la liste filtrée.
     * @pre <pre>
     *     0 <= i < getSize() </pre>
     */
    E getElementAt(int i);
    
    /**
     * Le nombre total d'éléments de la liste filtrée.
     */
    int getSize();
    
    /**
     * Le ième élément de la liste initiale.
     * @pre <pre>
     *     0 <= i < getUnfilteredSize() </pre>
     */
    E getUnfilteredElementAt(int i);
    
    /**
     * Le nombre total d'éléments de la liste initiale.
     */
    int getUnfilteredSize();

    // COMMANDES
    
    /**
     * Ajoute un élément dans le modèle.
     * @pre <pre>
     *     element != null </pre>
     * @post <pre>
     *     getUnfilteredSize() == old getUnfilteredSize() + 1
     *     getUnfilteredElementAt(getUnfilteredSize() - 1) == element </pre>
     */
    void addElement(E element);
    
    /**
     * Fixe le filtre de ce modèle.
     * @post <pre>
     *     getFilter() == filter </pre>
     */
    void setFilter(Filter<E, V> filter);
    
    /**
     * Réinitialise le modèle avec tous les éléments de c.
     * @pre <pre>
     *     c != null </pre>
     * @post <pre>
     *     getUnfilteredSize() == c.getSize()
     *     forall e:E :
     *         c.contains(e)
     *             <==> exists i:[0..getUnfilteredSize()[ :
     *                     getUnfilteredElementAt(i) == e </pre>
     */
    void setElements(Collection<E> c);
}
