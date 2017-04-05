package serie07.model.gui;

import java.util.HashSet;
import java.util.Set;

import serie07.model.filters.Filter;
import serie07.model.filters.Filterable;

public class MarkableFilterableListModel<E extends Filterable<V>, V> 
	extends StdFilterableListModel<E, V> {

	private Set<E> marked;
	
	public MarkableFilterableListModel(Filter fil) {
		super(fil);
		marked = new HashSet<E>();
	}
	
	public boolean isMarked(E e) {
		return marked.contains(e);
	}
	
	public void toggleMark(E e, int pos) {
		if (isMarked(e)) {
			marked.remove(e);
		} else {
			marked.add(e);
		}
		this.fireContentsChanged(this, pos, pos);
	}

	
	
}
