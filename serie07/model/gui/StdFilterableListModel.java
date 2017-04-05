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
 * Mod�les de listes filtrables.
 * Un tel mod�le est constitu� de deux listes :
 * <ol>
 *   <li>Une liste initiale comprenant tous les �l�ments.</li>
 *   <li>Une liste filtr�e comprenant parmi les �l�ments initiaux ceux qui
 *       sont accept�s par le filtre.</li>
 * </ol>
 * Le mod�le, tel qu'il est pr�sent� par ListModel, donne acc�s � la liste
 *  filtr�e ; on rajoute ici les m�thodes n�cessaires pour acc�der � la liste
 *  initiale.
 * E est le type des �l�ments du mod�le ; le type des valeurs associ�es
 *  au filtre du mod�le est V.
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
     * Le filtre actuel de ce mod�le filtrable.
     */
	@Override
	public Filter<E, V> getFilter() {
		return filter;
	}
    
	/**
     * Le i�me �l�ment de la liste filtr�e.
     * @pre <pre>
     *     0 <= i < getSize() </pre>
     */
	@Override
	public E getElementAt(int i) {
		Contract.checkCondition(0 <= i && i < getSize());
		return list.get(i);
	}

	/**
     * Le nombre total d'�l�ments de la liste filtr�e.
     */
	@Override
	public int getSize() {
		return list.size();
	}

	/**
     * Le i�me �l�ment de la liste initiale.
     * @pre <pre>
     *     0 <= i < getUnfilteredSize() </pre>
     */
	@Override
	public E getUnfilteredElementAt(int i) {
		Contract.checkCondition(0 <= i && i < unfilteredList.size());
		return unfilteredList.get(i);
	}

	/**
     * Le nombre total d'�l�ments de la liste initiale.
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
     * Ajoute un �l�ment dans le mod�le.
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
     * Fixe le filtre de ce mod�le.
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
     * R�initialise le mod�le avec tous les �l�ments de c.
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
