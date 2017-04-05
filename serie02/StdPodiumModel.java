package serie02;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Implémentation standard de PodiumModel.
 */
public class StdPodiumModel<E> implements PodiumModel<E> {
	
	private EventListenerList listenerList;
	
	private transient ChangeEvent changeEvent;
	
    // ATTRIBUTS
    
    private List<E> data;
    private int capacity;

    // CONSTRUCTEURS
    
    public StdPodiumModel(List<E> init, int capacity) {
        if (init == null) {
            throw new AssertionError();
        }
        if (containsNullValue(init)) {
            throw new AssertionError();
        }
        
        this.capacity = capacity;
        data = new ArrayList<E>(init);
        listenerList = new EventListenerList();
        changeEvent = null;
    }
    
    public StdPodiumModel() {
        this(new ArrayList<E>(), 0);
    }

    // REQUETES
    
    public E bottom() {
        if (size() <= 0) {
            throw new AssertionError();
        }
        
        return data.get(0);
    }
    
    public E elementAt(int i) {
        if (i < 0 || i >= capacity()) {
            throw new AssertionError();
        }
        
        if (i < size()) {
            return data.get(i);
        } else {
            return null;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if ((o == null) || (o.getClass() != getClass())) {
            return false;
        }
        @SuppressWarnings("unchecked")
        StdPodiumModel<E> arg = (StdPodiumModel<E>) o;
        boolean result = (arg.capacity == capacity
                && data.size() == arg.data.size());
        for (int i = 0; result && (i < data.size()); i++) {
            result = data.get(i).equals(arg.data.get(i));
        }
        return result;
    }
    
    public int capacity() {
        return capacity;
    }
    
    public int size() {
        return data.size();
    }
    
    public E top() {
        if (size() <= 0) {
            throw new AssertionError();
        }
        
        return data.get(data.size() - 1);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + capacity;
        result = prime * result + data.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        String res = "[";
        for (int i = 0; i < size(); i++) {
            res += data.get(i) + "|";
        }
        return capacity + "/" + res + "]";
    }

    // COMMANDES
    
    public void addTop(E elem) {
        if (elem == null) {
            throw new AssertionError();
        }
        if (size() >= capacity()) {
            throw new AssertionError("la structure est pleine");
        }
        
        data.add(elem);
        fireStateChanged();
    }
    
    public void removeBottom() {
        if (size() <= 0) {
            throw new AssertionError("la structure est vide");
        }
        
        data.remove(0);
        fireStateChanged();
    }

	public void removeTop() {
        if (size() <= 0) {
            throw new AssertionError("la structure est vide");
        }
        
        data.remove(size() - 1);
        fireStateChanged();
	}

    // OUTILS
    
    private boolean containsNullValue(List<E> list) {
        for (E e : list) {
            if (e == null) {
                return true;
            }
        }
        return false;
    }
	
	public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }
	
	public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }
	
	public void fireStateChanged() {
        ChangeListener[] listeners = 
        		listenerList.getListeners(ChangeListener.class);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
        	if (changeEvent == null) {
                changeEvent = new ChangeEvent(this);
            }
            (listeners[i]).stateChanged(changeEvent);
        }
    }
}
