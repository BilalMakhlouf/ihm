package serie07.event;

import javax.swing.event.EventListenerList;

public class ResourceLoaderSupport<R> {
    
    // ATTRIBUTS
    
    private EventListenerList listenersList;
    private final Object owner;

    // CONSTRUCTEURS
    
    public ResourceLoaderSupport(Object owner) {
        this.owner = owner;
    }

    // REQUETES
    
    @SuppressWarnings("unchecked")
    public synchronized ResourceLoaderListener<R>[]
            getResourceLoaderListeners() {
        if (listenersList == null) {
            return (ResourceLoaderListener<R>[]) new ResourceLoaderListener[0];
        }
        return listenersList.getListeners(ResourceLoaderListener.class);
    }
    
    public synchronized void addResourceLoaderListener(
            ResourceLoaderListener<R> listener) {
        if (listener == null) {
            return;
        }
        if (listenersList == null) {
            listenersList = new EventListenerList();
        }
        listenersList.add(ResourceLoaderListener.class, listener);
    }
    
    public synchronized void removeResourceLoaderListener(
            ResourceLoaderListener<R> listener) {
        if (listener == null || listenersList == null) {
            return;
        }
        listenersList.remove(ResourceLoaderListener.class, listener);
    }
    
    // COMMANDES
    
    public void fireResourceLoaded(R d) {
        ResourceLoaderListener<R>[] list = getResourceLoaderListeners();
        if (list.length > 0) {
            ResourceLoaderEvent<R> e = new ResourceLoaderEvent<R>(owner, d);
            for (ResourceLoaderListener<R> lst : list) {
                lst.resourceLoaded(e);
            }
        }
    }
}
