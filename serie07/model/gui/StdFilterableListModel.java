package serie07.model.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import serie07.model.filters.Filter;
import serie07.model.filters.Filterable;
import util.Contract;

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
public class StdFilterableListModel<E extends Filterable<V>, V> 
	extends AbstractListModel 
	implements FilterableListModel<E, V> {

	private Filter<E, V> filter;
	
	private List<E> list;
	private List<E> unfilteredList;
	private PropertyChangeListener pcl;
	
	public StdFilterableListModel(Filter<E, V> fil) {
		filter = fil;
		list = new ArrayList<E>();
		unfilteredList = new ArrayList<E>();
		pcl = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				filterList();
			}
			
		};
	}
	
	//REQUETES
	
	/**
     * Le filtre actuel de ce modèle filtrable.
     */
	@Override
	public Filter<E, V> getFilter() {
		return filter;
	}
    
	/**
     * Le ième élément de la liste filtrée.
     * @pre <pre>
     *     0 <= i < getSize() </pre>
     */
	@Override
	public E getElementAt(int i) {
		Contract.checkCondition(0 <= i && i < getSize());
		return list.get(i);
	}

	/**
     * Le nombre total d'éléments de la liste filtrée.
     */
	@Override
	public int getSize() {
		return list.size();
	}

	/**
     * Le ième élément de la liste initiale.
     * @pre <pre>
     *     0 <= i < getUnfilteredSize() </pre>
     */
	@Override
	public E getUnfilteredElementAt(int i) {
		Contract.checkCondition(0 <= i && i < unfilteredList.size());
		return unfilteredList.get(i);
	}

	/**
     * Le nombre total d'éléments de la liste initiale.
     */
	@Override
	public int getUnfilteredSize() {
		return unfilteredList.size();
	}

	// COMMANDES
	
	private void filterList() {
		list = filter.filter(unfilteredList);
		this.fireContentsChanged(this.list, 0, list.size());
	}
    
    /**
     * Ajoute un élément dans le modèle.
     * @pre <pre>
     *     element != null </pre>
     * @post <pre>
     *     getUnfilteredSize() == old getUnfilteredSize() + 1
     *     getUnfilteredElementAt(getUnfilteredSize() - 1) == element </pre>
     */
	@Override
	public void addElement(E element) {
		Contract.checkCondition(element != null);
		unfilteredList.add(element);
	}

	/**
     * Fixe le filtre de ce modèle.
     * @post <pre>
     *     getFilter() == filter </pre>
     */
	@Override
	public void setFilter(Filter<E, V> filter) {
		Filter<E, V> old = this.filter;
		
		this.filter = filter;
		
		if (old != null) {
			old.removeValueChangeListener(pcl);
		}
		
		this.filter.addValueChangeListener(pcl);
	}

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
	@Override
	public void setElements(Collection<E> c) {
		Contract.checkCondition(c != null);
		while (unfilteredList.size() != 0) {
			unfilteredList.remove(0);
		}
		unfilteredList.addAll(c);
	}


}
