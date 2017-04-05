package serie08.event;

import javax.swing.event.EventListenerList;

public class ResourceSupport {
    
    // ATTRIBUTS
    
    private static final ResourceListener[] EMPTY_ARRAY =
            new ResourceListener[0];

    private EventListenerList listenersList;
    private final Object owner;

    // CONSTRUCTEURS
    
    public ResourceSupport(Object owner) {
        this.owner = owner;
    }

    // REQUETES
    
    public synchronized ResourceListener[] getListeners() {
        if (listenersList == null) {
            return EMPTY_ARRAY;
        }
        return listenersList.getListeners(ResourceListener.class);
    }
    
    // COMMANDES
    
    public synchronized void add(ResourceListener listener) {
        if (listener == null) {
            return;
        }
        if (listenersList == null) {
            listenersList = new EventListenerList();
        }
        listenersList.add(ResourceListener.class, listener);
    }
    
    public synchronized void remove(ResourceListener listener) {
        if (listener == null || listenersList == null) {
            return;
        }
        listenersList.remove(ResourceListener.class, listener);
    }
    
    public void fireResourceLoaded(String d) {
        ResourceListener[] list = getListeners();
        if (list.length > 0) {
            ResourceEvent<String> e = new ResourceEvent<String>(owner, d);
            for (ResourceListener lst : list) {
                lst.resourceLoaded(e);
            }
        }
    }
    
    public void fireResourceSaved(String d) {
        ResourceListener[] list = getListeners();
        if (list.length > 0) {
            ResourceEvent<String> e = new ResourceEvent<String>(owner, d);
            for (ResourceListener lst : list) {
                lst.resourceSaved(e);
            }
        }
    }
    
    public void fireProgressUpdated(Integer n) {
        ResourceListener[] list = getListeners();
        if (list.length > 0) {
            ResourceEvent<Integer> e = new ResourceEvent<Integer>(owner, n);
            for (ResourceListener lst : list) {
                lst.progressUpdated(e);
            }
        }
    }
    
    public void fireErrorOccured(Exception exc) {
        ResourceListener[] list = getListeners();
        if (list.length > 0) {
            ResourceEvent<Exception> e =
                    new ResourceEvent<Exception>(owner, exc);
            for (ResourceListener lst : list) {
                lst.errorOccured(e);
            }
        }
    }
}
